package com.example.authorizer.authorization

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthorizationRepository: JpaRepository<Authorization, UUID> {
    fun findByUserId(userId: String): List<Authorization>

}