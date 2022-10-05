package server.model.response

import kotlinx.serialization.Serializable

@Serializable
class RegistrationResponse(
    val accountId: String
)