package org.rmgsoc.bratets.services.telegram

interface TelegramTextMessageHandler {
    fun isRelevant(text: String) : Boolean

    fun processMessage(text: String, from: Long)
}