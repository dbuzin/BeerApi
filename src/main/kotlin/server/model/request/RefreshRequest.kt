package server.model.request

import kotlinx.serialization.Serializable

@Serializable
class RefreshRequest(
    val refreshToken: String
)