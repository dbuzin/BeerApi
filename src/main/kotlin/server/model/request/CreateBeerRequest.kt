package server.model.request

import kotlinx.serialization.Serializable

@Serializable
class CreateBeerRequest(
    val name: String,
    val barcode: Long,
    val strength: Double,
    val type: Int,
    val country: Int? = null
)