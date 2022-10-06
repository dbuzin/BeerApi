package database.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object CountriesTable: IntIdTable() {
    val name = varchar("name", 255).uniqueIndex()
}

class CountriesEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<CountriesEntity>(CountriesTable)

    var name by CountriesTable.name
}