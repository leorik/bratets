package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.TextMessage
import org.rmgsoc.bratets.models.db.BroMessageRepository
import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class BratetsAnalyticsModuleService(
        val telegramConnectorService: TelegramConnectorService,
        val broMessageRepository: BroMessageRepository
) : TelegramTextMessageHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        private val requestPattern : Regex = Regex(pattern = "\\s*(братец)[,.\\s]+(че)[,.\\s]+(как)\\s*",
                option = RegexOption.IGNORE_CASE)
    }

    @PostConstruct
    fun setup() {
        logger.trace("Bratets analytics module init")

        telegramConnectorService.addTextMessageHandler(this)

        logger.trace("Bratets analytics module init done")
    }

    override fun isRelevant(text: String): Boolean {
        return text.matches(requestPattern)
    }

    override fun processMessage(message: TextMessage) {
        val messages = broMessageRepository.findMessagesFromPeriodByChatId(-235423713, Instant.parse("2017-10-01T00:00:00.00Z"))

        telegramConnectorService.sendTextMessageToChat(message.chatId, "${messages.count()} total messages")
    }
}