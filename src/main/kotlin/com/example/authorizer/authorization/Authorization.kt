package com.example.authorizer.authorization

import jakarta.persistence.*

@Entity
data class Authorization(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: String,

    @Enumerated(EnumType.STRING)
    val access: AccessType
) {
    constructor() : this(0, "", AccessType.READ)
}

enum class AccessType {
    READ, WRITE, DELETE
}
