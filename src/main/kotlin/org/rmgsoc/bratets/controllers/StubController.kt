package org.rmgsoc.bratets.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class StubController {
    @RequestMapping("/")
    fun index() : String {
        return "stub"
    }
}