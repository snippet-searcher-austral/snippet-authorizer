package com.example.authorizer.authorization

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/authorization")
class AuthorizationController {
    val authorizations = mutableMapOf<String, Authorization>(
        Pair("asd", Authorization(1, "1", AccessType.READ)),
        Pair("qwe", Authorization(2, "1", AccessType.WRITE)),
    )

    @GetMapping
    @ResponseStatus(OK)
    suspend fun listAuthorizations(): List<Authorization> = authorizations.values.toList()

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    suspend fun getAuthorization(@PathVariable id: String): Authorization {
        return authorizations[id] ?: throw ResponseStatusException(NOT_FOUND, "Authorization with id '$id' not found")
    }

    @GetMapping("/me")
    @ResponseStatus(OK)
    suspend fun myAuthorizations(authentication: Authentication): String {
        val auth0Id = authentication.name
        return "User Auth0 ID: $auth0Id"
    }

}