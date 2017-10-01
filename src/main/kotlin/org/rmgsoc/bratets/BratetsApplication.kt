package org.rmgsoc.bratets

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@EnableAsync
class BratetsApplication

fun main(args: Array<String>) {
    SpringApplication.run(BratetsApplication::class.java, *args)
}
