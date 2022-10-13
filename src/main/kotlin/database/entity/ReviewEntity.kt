package database.entity

import org.jetbrains.exposed.dao.*
import java.util.UUID

object ReviewTable : UUIDTable() {
    val beerId = integer("beerId").references(BeerTable.id)
    val author = uuid("author").references(AccountsTable.id)
    val reviewText = text("reviewText")
    val date = long("date")
    val photos = text("photos") // as json list
    val rate = float("rate")
}

class ReviewEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<ReviewEntity>(ReviewTable)

    var beerId by ReviewTable.beerId
    var author by ReviewTable.author
    var reviewText by ReviewTable.reviewText
    var date by ReviewTable.date
    var photos by ReviewTable.photos
    var rate by ReviewTable.rate
}