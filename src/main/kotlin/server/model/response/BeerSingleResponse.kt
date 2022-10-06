package server.model.response

import kotlinx.serialization.Serializable
import server.model.common.Beer

@Serializable
class BeerSingleResponse(
    val beer: Beer?
)