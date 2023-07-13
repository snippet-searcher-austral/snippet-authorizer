package com.example.authorizer.authorization

import com.example.authorizer.authorization.dto.CreateAuthorizationDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.net.HttpURLConnection
import java.net.URL

data class Snippet(val content: String, val userId: String)

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(message: String) : RuntimeException(message)

@Service
class AuthorizationService(private val authorizationRepository: AuthorizationRepository) {

    private var baseUrl = System.getenv("SNIPPET_MANAGER_URL")

    private fun getSnippetOwner(snippetId: String, bearerToken: String): String {
        val url = URL("$baseUrl$snippetId/owner")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "Bearer $bearerToken")
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        connection.disconnect()
        return response
    }

    fun createAuthorization(authorizationDTO: CreateAuthorizationDTO, auth0Id: String, bearerToken: String): AccessAuth {
        val snippetOwnerId: String = getSnippetOwner(authorizationDTO.snippetId, bearerToken)
        if (snippetOwnerId != auth0Id) {
            throw ForbiddenException("403 Forbidden: User doesn't own the snippet")
        }
        val authorization = AccessAuth(
            userId = authorizationDTO.userId,
            access = authorizationDTO.type,
            snippetId = authorizationDTO.snippetId,
        )
        return authorizationRepository.save(authorization)
    }

    fun getAllUserAuthorizations(userId: String): List<AccessAuth> {
        return authorizationRepository.findByUserId(userId)
    }

    fun authorizationExists(authorizationDTO: CreateAuthorizationDTO): Boolean {
        return authorizationRepository.existsAccessAuthByUserIdAndAccessAndSnippetId(
            userId = authorizationDTO.userId,
            accessType = authorizationDTO.type,
            snippetId = authorizationDTO.snippetId
        )
    }
}