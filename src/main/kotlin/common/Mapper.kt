package common

import database.entity.AccountsEntity
import database.entity.BeerEntity
import database.entity.ReviewEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import server.model.common.*
import database.model.Beer as DbBeer
import server.model.request.CreateBeerRequest
import server.model.request.CreateReviewRequest
import database.model.BeerType as DbBeerType
import database.model.Country as DbCountry
import database.model.Review as DbReview

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

fun CreateReviewRequest.toDataBaseReview(urls: List<String>) = DbReview(
    author = author,
    beerId = beerId,
    date = System.currentTimeMillis(),
    photos = urls,
    rate = rate,
    reviewText = reviewText
)
fun ReviewEntity.toReview() = Review(
    id = id.value.toString(),
    author = author.toString(),
    beerId = beerId,
    date = date,
    photos = Json.decodeFromString(photos),
    rate = rate,
    reviewText = reviewText
)

fun List<DbReview>.toReviews() = map {
    Review(
        id = it.id,
        author = it.author,
        beerId = it.beerId,
        date = it.date,
        photos = it.photos,
        rate = it.rate,
        reviewText = it.reviewText
    )
}