package database.model

data class Beer(
    val name: String,
    val barcode: Long,
    val strength: Double,
    val type: Int,
    val rate: Float? = null,
    val country: Int? = null,
    val reviews: List<String> = emptyList()
)
