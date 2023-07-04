package com.example.authorizer

import com.example.authorizer.authorization.AccessAuth
import com.example.authorizer.authorization.AuthorizationService
import com.example.authorizer.authorization.dto.CreateAuthorizationDTO
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.bind.annotation.*

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
    suspend fun createAuthorization(@RequestBody authorizationDTO: CreateAuthorizationDTO): AccessAuth {
        return authorizationService.createAuthorization(authorizationDTO)
    }

}

