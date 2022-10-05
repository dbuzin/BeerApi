package server.model.response

import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(
    val message: String
)