package dev.dbuzin.database.model

import database.model.Beer


data class ComplexReview(
    val id: String = "",
    val beer: Beer?,
    val author: String,
    val reviewText: String,
    val date: Long,
    val photos: List<String>,
    val rate:Float
)
