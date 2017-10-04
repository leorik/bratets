package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.TelegramUser
import org.rmgsoc.bratets.models.TextMessage
import org.rmgsoc.bratets.models.db.Bro
import org.rmgsoc.bratets.models.db.BroRepository
import org.rmgsoc.bratets.models.db.BroResponseRepository
import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.annotation.PostConstruct

@Service
class BratetsModuleService(
        val telegramConnectorService: TelegramConnectorService,
        val broRepository: BroRepository,
        val broResponseRepository: BroResponseRepository
) : TelegramTextMessageHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    private var lastBrattsyTime = Instant.EPOCH

    @PostConstruct
    fun setup() {
        logger.trace("Bratets module init")

        telegramConnectorService.addTextMessageHandler(this)

        logger.trace("Bratets module init done")
    }

    private val BratsyLiteral = "братцы"

    override fun isRelevant(text: String): Boolean {
        logger.trace("Got message \"$text\" for relevance check")

        if (text.length < BratsyLiteral.length) {
            logger.trace("Message \"$text\" is not relevant for bratets module")

            return false
        }

        logger.debug("Message \"$text\" is relevant for bratets module, proceeding")

        return normalize(text) == BratsyLiteral
    }

    override fun processMessage(message: TextMessage) {
        val authorBro = retrieveOrCreateAuthorBro(message.author)

        if (lastBrattsyTime.plus(2, ChronoUnit.MINUTES).isBefore(Instant.now())) {
            telegramConnectorService.sendTextMessageToChat(message.chatId, "Братцы")

            lastBrattsyTime = Instant.now()
        }
    }

    private fun normalize(text: String) : String {
        return text.substring(0..5).toLowerCase()
    }

    private fun retrieveOrCreateAuthorBro(author: TelegramUser) : Bro {
        val bro = broRepository.getByTelegramId(author.id)

        if (bro != null) return bro

        val createdBro = Bro(
                telegramId = author.id,
                name = author.handle,
                since = Instant.now()
        )

        broRepository.save(createdBro)


        return createdBro
    }
}