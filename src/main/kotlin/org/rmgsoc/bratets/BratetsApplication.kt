package org.rmgsoc.bratets

import org.rmgsoc.bratets.services.modules.BratetsModule
import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableAsync
class BratetsApplication

fun main(args: Array<String>) {
    SpringApplication.run(BratetsApplication::class.java, *args)
}


@Component
class ApplicationRunner(
    val telegramConnectorService: TelegramConnectorService,
    val modules : Set<BratetsModule>
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        modules.forEach {
            it.setup()
        }

        telegramConnectorService.run().join()
    }
}
