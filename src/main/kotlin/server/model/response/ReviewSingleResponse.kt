package server.model.response

import kotlinx.serialization.Serializable
import server.model.common.Review

@Serializable
class ReviewSingleResponse(
    val review: Review
)