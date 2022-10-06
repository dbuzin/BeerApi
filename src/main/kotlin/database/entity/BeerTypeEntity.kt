package database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object BeerTypeTable : IntIdTable() {
    val name = varchar("name", 255).uniqueIndex()
    val description = text("description")
}

class BeerTypeEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<BeerTypeEntity>(BeerTypeTable)

    var name by BeerTypeTable.name
    var description by BeerTypeTable.description
}