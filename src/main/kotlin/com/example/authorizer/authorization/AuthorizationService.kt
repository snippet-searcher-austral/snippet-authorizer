package com.example.authorizer.authorization

import com.example.authorizer.authorization.dto.CreateAuthorizationDTO
import org.springframework.stereotype.Service
@Service
class AuthorizationService(private val authorizationRepository: AuthorizationRepository) {
        fun createAuthorization(authorizationDTO: CreateAuthorizationDTO): AccessAuth {
            val authorization = AccessAuth(
                userId = authorizationDTO.userId,
                access = authorizationDTO.type,
                snippetId = authorizationDTO.snippetId,
            )
            return authorizationRepository.save(authorization)
        }
}