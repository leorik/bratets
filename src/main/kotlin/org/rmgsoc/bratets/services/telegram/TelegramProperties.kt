package org.rmgsoc.bratets.services.telegram

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "bot")
@PropertySource("classpath:telegram.properties")
data class TelegramProperties (
        var key : String? = null,
        var name : String? = null
)