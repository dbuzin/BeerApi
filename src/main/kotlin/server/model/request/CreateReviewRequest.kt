package server.model.request

import kotlinx.serialization.Serializable

@Serializable
class CreateReviewRequest(
    val beerId: Int,
    val author: String,
    val reviewText: String,
    val rate: Float
)