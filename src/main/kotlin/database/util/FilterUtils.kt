package database.util

import common.model.Filter
import common.model.Sort

fun getFilterQuery(
    tableName: String,
    filter: Filter,
    sort: Sort?
): String {
    return if (sort == null) {
        "SElECT * FROM $tableName WHERE ${filter.field} ${filter.operation} ${filter.value}"
    } else {
        "SElECT * FROM $tableName WHERE ${filter.field} ${filter.operation} ${filter.value} ORDER BY ${sort.field} ${sort.direction}"
    }
}