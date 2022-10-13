package database.model


data class Review(
    val id: String = "",
    val beerId: Int,
    val author: String,
    val reviewText: String,
    val date: Long,
    val photos: List<String>,
    val rate:Float
)