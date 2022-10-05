package server.model.request

import kotlinx.serialization.Serializable

@Serializable
class LoginRequest(
    val email: String,
    val password: String
)
