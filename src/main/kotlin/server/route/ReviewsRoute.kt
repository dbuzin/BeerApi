package server.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import server.controller.ReviewsController
import server.model.common.FilterBody
import server.util.convert

fun Route.reviewsRoute(
    hostPath: String
) {
    val reviewsController by inject<ReviewsController>()

    post("$hostPath$CREATE_PATH") {
        val multipartData = call.receiveMultipart()
        val requestFilesPair = multipartData.convert()
        val response = reviewsController.createReview(requestFilesPair.first, requestFilesPair.second)
        call.respond(HttpStatusCode.Created, response)
    }

    get("$hostPath$GET_ALL_PATH") {
        val response = reviewsController.getAllReviews()
        call.respond(HttpStatusCode.OK, response)
    }

    post("$hostPath$GET_FILTERED_PATH") {
        val request = call.receive<FilterBody>()
        val response = reviewsController.requestFilteredReviews(request)
        call.respond(HttpStatusCode.OK, response)
    }

    get("$hostPath$FIND_PATH") {
        val reviewId = call.parameters["review_id"]
        reviewId?.let {
            val response = reviewsController.getSingleReview(it)
            call.respond(HttpStatusCode.OK, response)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}

private const val ENDPOINT = "/reviews"

private const val CREATE_PATH = "$ENDPOINT/create"
private const val GET_ALL_PATH = "$ENDPOINT/all"
private const val GET_FILTERED_PATH = "$ENDPOINT/filtered"
private const val FIND_PATH = "$ENDPOINT/find"