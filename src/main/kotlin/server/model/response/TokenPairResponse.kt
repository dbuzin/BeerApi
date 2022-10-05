package server.model.response

import kotlinx.serialization.Serializable

@Serializable
class TokenPairResponse(
    val accessToken: String,
    val refreshToken: String
)