package server.model.common

import io.ktor.server.auth.*

class Account(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val reviewsCount: Int = 0
) : Principal