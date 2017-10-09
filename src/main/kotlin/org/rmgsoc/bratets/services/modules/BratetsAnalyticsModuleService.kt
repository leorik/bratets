package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.TextMessage
import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class BratetsAnalyticsModuleService(
        val telegramConnectorService: TelegramConnectorService
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

    }
}