package server.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import server.model.exceptions.AccountExistsException
import server.model.exceptions.InvalidCredentialsException
import server.model.exceptions.InvalidEmailException
import server.model.exceptions.InvalidTokenException
import server.model.response.ErrorResponse

fun StatusPagesConfig.authStatusPages() {
    exception<InvalidEmailException> { call, ex ->
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse(ex.message)
        )
    }
    exception<AccountExistsException> { call, ex ->
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse(ex.message)
        )
    }
    exception<InvalidCredentialsException> { call, ex ->
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse(ex.message)
        )
    }
    exception<InvalidTokenException> { call, ex ->
        call.respond(
            HttpStatusCode.Unauthorized,
            ErrorResponse(ex.message)
        )
    }
}