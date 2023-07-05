package com.example.authorizer.authorization

import com.example.authorizer.authorization.dto.CreateAuthorizationDTO
import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

const val baseUrl = "http://localhost:8081/snippet/"

data class Snippet(val content: String, val userId: String)

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(message: String) : RuntimeException(message)


@Service
class AuthorizationService(private val authorizationRepository: AuthorizationRepository) {

    private fun getSnippet(snippetId: String, bearerToken: String): Snippet {
        val url = URL(baseUrl + snippetId)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "Bearer $bearerToken")
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        connection.disconnect()
        val jsonResponse = JSONObject(response)
        val content = jsonResponse.getString("content")
        val userId = jsonResponse.getString("userId")
        return Snippet(content, userId)
    }
    fun createAuthorization(authorizationDTO: CreateAuthorizationDTO, auth0Id: String, bearerToken: String): AccessAuth {
        val snippet: Snippet = getSnippet(authorizationDTO.snippetId, bearerToken)
        if (snippet.userId != auth0Id) {
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