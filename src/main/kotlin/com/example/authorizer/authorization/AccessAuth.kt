package com.example.authorizer.authorization

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@Table(
    name = "access_auth",
    uniqueConstraints = [UniqueConstraint(columnNames = ["access", "snippetId", "userId"])]
)
@EntityListeners(AuditingEntityListener::class)
data class AccessAuth(
    @Id @GeneratedValue
    val id: UUID? = null,

    @Column(nullable = false)
    val userId: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val access: AccessType = AccessType.READ,

    @Column(nullable = false)
    val snippetId: String = "",
)

enum class AccessType {
    READ, WRITE, DELETE
}
