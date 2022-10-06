package database.dao

import common.model.Filter
import common.model.Sort
import database.entity.BeerEntity
import database.entity.BeerTable
import database.model.Beer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import common.toBeer

class BeerDao {

    fun insert(beer: Beer) = transaction {
        BeerEntity.new {
            this.name = beer.name
            this.barcode = beer.barcode
            this.strength = beer.strength
            this.type = beer.type
            this.rate = beer.rate
            this.country = beer.country
            this.reviews = Json.encodeToString(beer.reviews)
        }.id.value
    }

    fun findByBarcode(barcode: Long) = transaction {
        BeerEntity.find { BeerTable.barcode eq barcode }.firstOrNull()?.toBeer()
    }

    fun getAll() = transaction {
        BeerEntity.all().map {
            Beer(
                name = it.name,
                barcode = it.barcode,
                strength = it.strength,
                type = it.type,
                rate = it.rate,
                country = it.country,
                reviews = Json.decodeFromString(it.reviews)
            )
        }
    }

    fun getFiltered(
        filter: Filter,
        sort: Sort?
    ) = transaction {
        val beerList = ArrayList<Beer>()
        if (sort == null) {
            exec("SElECT * FROM ${BeerTable.tableName} WHERE ${filter.field} ${filter.operation} ${filter.value}") {
                while (it.next()) {
                    beerList.add(
                        Beer(
                            name = it.getString("name"),
                            barcode = it.getLong("barcode"),
                            strength = it.getDouble("strength"),
                            type = it.getInt("type"),
                            rate = it.getFloat("rate"),
                            country = it.getInt("country"),
                            reviews = Json.decodeFromString(it.getString("reviews"))
                        )
                    )
                }
            }
        } else {
            exec("SElECT * FROM ${BeerTable.tableName} WHERE ${filter.field} ${filter.operation} ${filter.value} ORDER BY ${sort.field} ${sort.direction}") {
                while (it.next()) {
                    beerList.add(
                        Beer(
                            name = it.getString("name"),
                            barcode = it.getLong("barcode"),
                            strength = it.getDouble("strength"),
                            type = it.getInt("type"),
                            rate = it.getFloat("rate"),
                            country = it.getInt("country"),
                            reviews = Json.decodeFromString(it.getString("reviews"))
                        )
                    )
                }
            }
        }
        beerList.toList()
    }
}