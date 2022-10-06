package common.model

import kotlinx.serialization.Serializable

@Serializable
data class Sort(
    val field: String,
    val direction: String
)
