package database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

object AccountsTable : UUIDTable() {
    val name = varchar("name", 255).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val reviewsCount = integer("reviews_count")
}

class AccountsEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AccountsEntity>(AccountsTable)

    var name by AccountsTable.name
    var email by AccountsTable.email
    var password by AccountsTable.password
    var reviewsCount by AccountsTable.reviewsCount
}