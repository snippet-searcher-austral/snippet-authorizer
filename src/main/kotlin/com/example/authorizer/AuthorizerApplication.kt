package com.example.authorizer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class AuthorizerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizerApplication>(*args)
}

@RestController
class AuthorizationController {
    @GetMapping("/")
    fun index(@RequestParam("name") name: String) = "Hello, $name!, this is a test!"
}

