package org.rmgsoc.bratets.services.telegram

import org.rmgsoc.bratets.services.modules.TextMessageHandlerModule

interface TelegramConnectorService {
    fun run() : Thread

    fun addTextMessageHandler(handlerModule: TextMessageHandlerModule)

    fun sendTextMessageToChat(chatId: Long, message: String)
}