package database.model

data class Account(
    val name: String,
    val email: String,
    val password: String,
    val reviewsCount: Int = 0
)
