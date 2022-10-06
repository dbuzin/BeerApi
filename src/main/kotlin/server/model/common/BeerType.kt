package server.model.common

import kotlinx.serialization.Serializable

@Serializable
class BeerType(
    val id: Int,
    val name: String,
    val description: String
)