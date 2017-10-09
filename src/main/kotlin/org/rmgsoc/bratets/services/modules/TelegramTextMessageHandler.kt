package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.TextMessage

interface TelegramTextMessageHandler {
    fun isRelevant(text: String) : Boolean

    fun processMessage(message: TextMessage)
}