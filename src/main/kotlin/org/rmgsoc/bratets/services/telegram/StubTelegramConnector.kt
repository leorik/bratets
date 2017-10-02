package org.rmgsoc.bratets.services.telegram

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("dev")
class StubTelegramConnector : TelegramConnectorService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun addTextMessageHandler(handler: TelegramTextMessageHandler) {
        logger.debug("Added text message handler: $handler")
    }

    override fun sendTextMessageToChat(chatId: Long, message: String) {
        logger.debug("Sent message \"$message\" to chat $chatId")
    }
}