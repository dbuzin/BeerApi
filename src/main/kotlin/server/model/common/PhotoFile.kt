package server.model.common

import kotlinx.serialization.Serializable

@Serializable
class PhotoFile(
    val name: String,
    val body: ByteArray
)