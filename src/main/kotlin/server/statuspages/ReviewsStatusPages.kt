package server.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import server.model.exceptions.InvalidReviewException
import server.model.exceptions.ReviewNotFoundException
import server.model.response.ErrorResponse

fun StatusPagesConfig.reviewsStatusPages() {
    exception<ReviewNotFoundException> { call, ex ->
        call.respond(
            HttpStatusCode.NotFound,
            ErrorResponse(ex.message)
        )
    }
    exception<InvalidReviewException> { call, ex ->
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse(ex.message)
        )
    }
}