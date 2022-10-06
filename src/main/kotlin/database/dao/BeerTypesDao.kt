package database.dao

import database.entity.BeerTypeEntity
import database.entity.BeerTypeTable
import database.model.BeerType
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

class BeerTypesDao {

    fun insert(types: List<BeerType>) = transaction {
        BeerTypeTable.batchInsert(types) {
            this[BeerTypeTable.name] = it.name
            this[BeerTypeTable.description] = it.description
        }
    }

    fun getAllTypes() = transaction {
        BeerTypeEntity.all().map {
            BeerType(
                id = it.id.value,
                name = it.name,
                description = it.description
            )
        }
    }
}