package server.controller

import common.toDataBaseReview
import common.toReview
import common.toReviews
import database.dao.ReviewsDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import server.model.common.FilterBody
import server.model.common.ListResponse
import server.model.common.PhotoFile
import server.model.common.Review
import server.model.exceptions.ReviewNotFoundException
import server.model.request.CreateReviewRequest
import server.model.response.CreateReviewResponse
import server.model.response.ReviewSingleResponse
import server.service.image.UploadClient

interface ReviewsController {

    suspend fun createReview(review: CreateReviewRequest, photoFiles: List<PhotoFile>): CreateReviewResponse

    suspend fun getAllReviews(): ListResponse<Review>

    suspend fun getSingleReview(reviewId: String): ReviewSingleResponse

    suspend fun requestFilteredReviews(filterBody: FilterBody): ListResponse<Review>
}

class ReviewsControllerImpl : ReviewsController, KoinComponent {

    private val reviewsDao by inject<ReviewsDao>()
    private val uploadClient by inject<UploadClient>()

    override suspend fun createReview(review: CreateReviewRequest, photoFiles: List<PhotoFile>): CreateReviewResponse {
        val urls = uploadClient.uploadImages(photoFiles)
        val reviewId = reviewsDao.insert(review.toDataBaseReview(urls))
        return CreateReviewResponse(
            reviewId = reviewId
        )
    }

    override suspend fun getAllReviews(): ListResponse<Review> {
        val reviews = reviewsDao.getAll().toReviews()
        return ListResponse(
            data = reviews
        )
    }

    override suspend fun getSingleReview(reviewId: String): ReviewSingleResponse {
        val review = reviewsDao.getById(reviewId)?.toReview() ?: throw ReviewNotFoundException()
        return ReviewSingleResponse(
            review = review
        )
    }

    override suspend fun requestFilteredReviews(filterBody: FilterBody): ListResponse<Review> {
        val reviews = reviewsDao.getFiltered(
            filter = filterBody.filter,
            sort = filterBody.sort
        ).toReviews()
        return ListResponse(
            data = reviews
        )
    }
}