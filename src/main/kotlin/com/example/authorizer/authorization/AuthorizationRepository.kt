package com.example.authorizer.authorization

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthorizationRepository: JpaRepository<AccessAuth, UUID> {
    fun findByUserId(userId: String): List<AccessAuth>

    fun existsAccessAuthByUserIdAndAccessAndSnippetId(userId: String, accessType: AccessType, snippetId: String): Boolean

}