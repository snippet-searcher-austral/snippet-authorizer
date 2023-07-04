package com.example.authorizer.authorization.dto

import com.example.authorizer.authorization.AccessType

data class CreateAuthorizationDTO(
    val userId: String,
    val type: AccessType,
    val snippetId: String
)