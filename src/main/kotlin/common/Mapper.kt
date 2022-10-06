package common

import database.entity.AccountsEntity
import database.entity.BeerEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import database.model.Beer as DbBeer
import server.model.common.Account
import server.model.common.Beer
import server.model.common.BeerType
import server.model.common.Country
import server.model.request.CreateBeerRequest
import database.model.BeerType as DbBeerType
import database.model.Country as DbCountry

fun AccountsEntity.toServerAccount() = Account(
    id = id.value.toString(),
    email = email,
    name = name,
    password = password,
    reviewsCount = reviewsCount
)

fun CreateBeerRequest.toBeer() = DbBeer(
    name = name,
    barcode = barcode,
    type = type,
    strength = strength,
    country = country
)

fun BeerEntity.toBeer() = Beer(
    name = name,
    barcode = barcode,
    type = type,
    strength = strength,
    country = country,
    reviews = Json.decodeFromString(reviews)
)

fun List<DbBeer>.toBeer() = map {
    Beer(
        name = it.name,
        barcode = it.barcode,
        type = it.type,
        strength = it.strength,
        country = it.country,
        reviews = it.reviews
    )
}

fun List<DbBeerType>.toBeerType() = map {
    BeerType(
        id = it.id!!,
        name = it.name,
        description = it.description
    )
}

fun List<DbCountry>.toCountry() = map {
    Country(
        id = it.id,
        name = it.name
    )
}