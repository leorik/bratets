package org.rmgsoc.bratets.services.modules

interface TextMessageHandlerModule : BratetsModule {
    fun isRelevant(text: String) : Boolean

    fun processMessage(text: String, from: Long)
}

interface BratetsModule {
    fun setup()
}