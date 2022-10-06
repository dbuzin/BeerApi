package server.model.common

import common.model.Filter
import common.model.Sort
import kotlinx.serialization.Serializable

@Serializable
class FilterBody(
    val filter: Filter,
    val sort: Sort?
)