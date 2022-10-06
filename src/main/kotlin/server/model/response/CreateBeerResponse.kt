package server.model.response

import kotlinx.serialization.Serializable

@Serializable
class CreateBeerResponse(
    val beerId: Int
)