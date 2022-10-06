package server.model.common

import kotlinx.serialization.Serializable

@Serializable
class ListResponse<T>(
    val data: List<T>
)