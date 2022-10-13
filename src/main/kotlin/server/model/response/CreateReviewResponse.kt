package server.model.response

import kotlinx.serialization.Serializable

@Serializable
class CreateReviewResponse(
    val reviewId: String
)