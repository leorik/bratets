package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("echo")
class EchoModuleService(
        val telegramConnectorService: TelegramConnectorService
) : TextMessageHandlerModule {

    override fun setup() {
        telegramConnectorService.addTextMessageHandler(this)
    }

    override fun isRelevant(text: String): Boolean {
        return true
    }

    override fun processMessage(text: String, from: Long) {
        telegramConnectorService.sendTextMessageToChat(from, text)
    }
}