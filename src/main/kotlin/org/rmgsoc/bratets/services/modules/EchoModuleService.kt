package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.TextMessage
import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.springframework.stereotype.Service

@Service
class EchoModuleService(
        val telegramConnectorService: TelegramConnectorService
) : TelegramTextMessageHandler {

    fun setup() {
        telegramConnectorService.addTextMessageHandler(this)
    }

    override fun isRelevant(text: String): Boolean {
        return true
    }

    override fun processMessage(message: TextMessage) {
        telegramConnectorService.sendTextMessageToChat(message.chatId, message.text)
    }
}