package com.example.authorizer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthorizerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizerApplication>(*args)
}

@RestController
class AuthorizationController {
    @GetMapping("/")
    fun index(@RequestParam("name") name: String) = "Hello, $name!, this is a health test!"
}

