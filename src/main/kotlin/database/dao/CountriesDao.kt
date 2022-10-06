package database.dao

import database.entity.CountriesEntity
import database.entity.CountriesTable
import database.model.Country
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

class CountriesDao {

    fun insert(countries: List<String>) = transaction {
        CountriesTable.batchInsert(countries) { name ->
            this[CountriesTable.name] = name
        }
    }

    fun getAllCountries() = transaction {
        CountriesEntity.all().map {
            Country(
                id = it.id.value,
                name = it.name
            )
        }
    }
}