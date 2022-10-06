package server.model.common

import kotlinx.serialization.Serializable

@Serializable
class Country(
    val id: Int,
    val name: String
)