package database.dao

import common.model.Filter
import common.model.Sort
import common.toBeer
import database.entity.BeerEntity
import database.entity.BeerTable
import database.entity.ReviewEntity
import database.entity.ReviewTable
import database.model.Beer
import database.model.Review
import database.util.getFilterQuery
import dev.dbuzin.database.model.ComplexReview
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*
import kotlin.collections.ArrayList

class ReviewsDao {

    fun insert(review: Review) = transaction {
        val reviewId = ReviewEntity.new {
            this.author = UUID.fromString(review.author)
            this.beerId = review.beerId
            this.reviewText = review.reviewText
            this.date = review.date
            this.rate = review.rate
            this.photos = Json.encodeToString(review.photos)
        }.id.value
        BeerTable.update({ BeerTable.id eq review.beerId }) {
            it[rate] =
                (ReviewEntity.find { ReviewTable.beerId eq review.beerId }.map { reviewEntity -> reviewEntity.rate }
                    .sum().div(ReviewEntity.count()))
        }
        reviewId.toString()
    }

    fun getAll() = transaction {
        ReviewEntity.all().map {
            val beerEntity = BeerEntity.find { BeerTable.id eq it.beerId }.firstOrNull()
            ComplexReview(
                id = it.id.value.toString(),
                author = it.author.toString(),
                beer = beerEntity?.let { beer ->
                    Beer(
                        name = beer.name,
                        barcode = beer.barcode,
                        strength = beer.strength,
                        type = beer.type,
                        rate = beer.rate,
                        country = beer.country,
                        reviews = Json.decodeFromString(beer.reviews)
                    )
                },
                date = it.date,
                rate = it.rate,
                reviewText = it.reviewText,
                photos = Json.decodeFromString(it.photos)
            )
        }
    }

    fun getById(reviewId: String) = transaction {
        val review = ReviewEntity.find { ReviewTable.id eq UUID.fromString(reviewId) }.singleOrNull()
        review?.let {
            val beerEntity = BeerEntity.find { BeerTable.id eq it.beerId }.firstOrNull()
            ComplexReview(
                id = it.id.value.toString(),
                author = it.author.toString(),
                beer = beerEntity?.let { beer ->
                    Beer(
                        name = beer.name,
                        barcode = beer.barcode,
                        strength = beer.strength,
                        type = beer.type,
                        rate = beer.rate,
                        country = beer.country,
                        reviews = Json.decodeFromString(beer.reviews)
                    )
                },
                date = it.date,
                rate = it.rate,
                reviewText = it.reviewText,
                photos = Json.decodeFromString(it.photos)
            )
        }
    }

    fun getFiltered(
        filter: Filter,
        sort: Sort?
    ) = transaction {
        val reviewsList = ArrayList<ComplexReview>()
        if (sort == null) {
            exec(
                getFilterQuery(
                    tableName = ReviewTable.tableName,
                    filter = filter,
                    sort = null
                )
            ) {
                while (it.next()) {
                    val beerEntity = BeerEntity.find { BeerTable.id eq it.getInt("beerId") }.firstOrNull()
                    reviewsList.add(
                        ComplexReview(
                            id = it.getString("id"),
                            author = it.getString("author"),
                            beer = beerEntity?.let { beer ->
                                Beer(
                                    name = beer.name,
                                    barcode = beer.barcode,
                                    strength = beer.strength,
                                    type = beer.type,
                                    rate = beer.rate,
                                    country = beer.country,
                                    reviews = Json.decodeFromString(beer.reviews)
                                )
                            },
                            date = it.getLong("date"),
                            rate = it.getFloat("rate"),
                            reviewText = it.getString("reviewText"),
                            photos = Json.decodeFromString(it.getString("photos"))
                        )
                    )
                }
            }
        } else {
            exec(
                getFilterQuery(
                    tableName = ReviewTable.tableName,
                    filter = filter,
                    sort = sort
                )
            ) {
                while (it.next()) {
                    val beerEntity = BeerEntity.find { BeerTable.id eq it.getInt("beerId") }.firstOrNull()
                    reviewsList.add(
                        ComplexReview(
                            id = it.getString("id"),
                            author = it.getString("author"),
                            beer = beerEntity?.let { beer ->
                                Beer(
                                    name = beer.name,
                                    barcode = beer.barcode,
                                    strength = beer.strength,
                                    type = beer.type,
                                    rate = beer.rate,
                                    country = beer.country,
                                    reviews = Json.decodeFromString(beer.reviews)
                                )
                            },
                            date = it.getLong("date"),
                            rate = it.getFloat("rate"),
                            reviewText = it.getString("reviewText"),
                            photos = Json.decodeFromString(it.getString("photos"))
                        )
                    )
                }
            }
        }
        reviewsList.toList()
    }
}