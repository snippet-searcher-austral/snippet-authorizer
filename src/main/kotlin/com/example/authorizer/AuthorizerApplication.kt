package com.example.authorizer

import com.example.authorizer.authorization.AccessAuth
import com.example.authorizer.authorization.AuthorizationService
import com.example.authorizer.authorization.dto.CreateAuthorizationDTO
import jdk.jshell.Snippet
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.util.UUID

fun main(args: Array<String>) {
    runApplication<AuthorizationController>(*args)
}

@SpringBootApplication
@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@EnableJpaAuditing
class AuthorizationController(private val authorizationService: AuthorizationService) {

    @GetMapping("/")
    fun index(): String = "Hello. This is a health test!"

    @PostMapping("/new")
    suspend fun createAuthorization(@RequestBody authorizationDTO: CreateAuthorizationDTO, authentication: Authentication, @RequestHeader("Authorization") authorizationHeader: String): AccessAuth {
        val auth0Id: String = (authentication.principal as Jwt).subject
        return authorizationService.createAuthorization(authorizationDTO, auth0Id, authorizationHeader.substring(7))
    }

    @GetMapping("/me")
    suspend fun getAllUserAuthorizations(authentication: Authentication): List<AccessAuth> {
        val auth0Id: String = (authentication.principal as Jwt).subject
        return authorizationService.getAllUserAuthorizations(auth0Id)
    }

    @PostMapping("/isAuthorized")
    suspend fun checkAuthorizationExists(@RequestBody authorizationDTO: CreateAuthorizationDTO): Boolean {
        return authorizationService.authorizationExists(authorizationDTO)
    }


}

