package database.dao

import common.model.Filter
import common.model.Sort
import database.entity.BeerTable
import database.entity.ReviewEntity
import database.entity.ReviewTable
import database.model.Review
import database.util.getFilterQuery
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
            Review(
                id = it.id.value.toString(),
                author = it.author.toString(),
                beerId = it.beerId,
                date = it.date,
                rate = it.rate,
                reviewText = it.reviewText,
                photos = Json.decodeFromString(it.photos)
            )
        }
    }

    fun getById(reviewId: String) = transaction {
        ReviewEntity.find { ReviewTable.id eq UUID.fromString(reviewId) }.singleOrNull()
    }

    fun getFiltered(
        filter: Filter,
        sort: Sort?
    ) = transaction {
        val reviewsList = ArrayList<Review>()
        if (sort == null) {
            exec(
                getFilterQuery(
                    tableName = ReviewTable.tableName,
                    filter = filter,
                    sort = null
                )
            ) {
                while (it.next()) {
                    reviewsList.add(
                        Review(
                            id = it.getString("id"),
                            author = it.getString("author"),
                            beerId = it.getInt("beerId"),
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
                    reviewsList.add(
                        Review(
                            id = it.getString("id"),
                            author = it.getString("author"),
                            beerId = it.getInt("beerId"),
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