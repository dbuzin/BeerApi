package common.model

import kotlinx.serialization.Serializable

@Serializable
data class Filter(
    val field: String,
    val value: String,
    val operation: String
)
