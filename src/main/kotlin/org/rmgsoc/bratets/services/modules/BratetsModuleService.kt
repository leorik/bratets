package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.TelegramUser
import org.rmgsoc.bratets.models.TextMessage
import org.rmgsoc.bratets.models.db.Bro
import org.rmgsoc.bratets.models.db.BroRepository
import org.rmgsoc.bratets.models.db.BroMessage
import org.rmgsoc.bratets.models.db.BroMessageRepository
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
        val broMessageRepository: BroMessageRepository
) : TelegramTextMessageHandler {

    companion object {
        private const val BRATSY_ROUND_DURATION_IN_MINS: Long = 5
        private const val BRATSY_LITERAL = "братцы"
    }

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun setup() {
        logger.trace("Bratets module init")

        telegramConnectorService.addTextMessageHandler(this)

        logger.trace("Bratets module init done")
    }



    override fun isRelevant(text: String): Boolean {
        logger.trace("Got message \"$text\" for relevance check")

        if (text.length < BRATSY_LITERAL.length) {
            logger.trace("Message \"$text\" is not relevant for bratets module")

            return false
        }

        logger.debug("Message \"$text\" is relevant for bratets module, proceeding")

        return normalize(text) == BRATSY_LITERAL
    }

    override fun processMessage(message: TextMessage) {
        val lastMessage = broMessageRepository.findFirstByOrderByTimeDesc()

        if (lastMessage == null ||
                lastMessage.time.plus(BRATSY_ROUND_DURATION_IN_MINS, ChronoUnit.MINUTES).isBefore(Instant.now())) {

            telegramConnectorService.sendTextMessageToChat(message.chatId, "Братцы")
        }

        persistBratsyMessage(message)
    }

    private fun normalize(text: String) : String {
        return text.substring(0..5).toLowerCase()
    }

    private fun persistBratsyMessage(message: TextMessage) {
        val authorBro = retrieveOrCreateAuthorBro(message.author)

        val dbMessage = BroMessage(
                telegramId = message.telegramId,
                author = authorBro,
                time = Instant.now()
        )

        broMessageRepository.save(dbMessage)
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