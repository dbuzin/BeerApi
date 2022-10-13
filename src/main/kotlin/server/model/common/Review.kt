package server.model.common
import kotlinx.serialization.Serializable

@Serializable
class Review(
    val id: String = "",
    val beerId: Int,
    val author: String,
    val reviewText: String,
    val date: Long,
    val photos: List<String>,
    val rate:Float
)