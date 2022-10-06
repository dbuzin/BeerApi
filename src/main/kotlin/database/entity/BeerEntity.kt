package database.entity

import org.jetbrains.exposed.dao.*

object BeerTable: IntIdTable() {
    val name = varchar("name", 255).uniqueIndex()
    val barcode = long("barcode").uniqueIndex()
    val strength = double("strength")
    val type = integer("type").references(BeerTypeTable.id)
    val rate = float("rate").nullable()
    val country = integer("country").references(CountriesTable.id).nullable()
    val reviews = text("reviews")
}

class BeerEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<BeerEntity>(BeerTable)

    var name by BeerTable.name
    var barcode by BeerTable.barcode
    var strength by BeerTable.strength
    var type by BeerTable.type
    var rate by BeerTable.rate
    var country by BeerTable.country
    var reviews by BeerTable.reviews
}