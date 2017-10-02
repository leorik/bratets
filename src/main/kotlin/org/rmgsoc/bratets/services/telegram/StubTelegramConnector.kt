package org.rmgsoc.bratets.services.telegram

import org.rmgsoc.bratets.services.modules.TextMessageHandlerModule
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import kotlin.concurrent.thread

@Service
@Profile("dev")
class StubTelegramConnector : TelegramConnectorService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun run() : Thread {
        logger.debug("Started")

        return thread { while(!Thread.interrupted()) { Thread.sleep(5000 ) } }
    }

    override fun addTextMessageHandler(handlerModule: TextMessageHandlerModule) {
        logger.debug("Added text message handlerModule: $handlerModule")
    }

    override fun sendTextMessageToChat(chatId: Long, message: String) {
        logger.debug("Sent message \"$message\" to chat $chatId")
    }
}