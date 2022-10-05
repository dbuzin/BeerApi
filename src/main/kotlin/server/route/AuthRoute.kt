package server.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import server.controller.AuthController
import server.model.request.LoginRequest
import server.model.request.RefreshRequest
import server.model.request.RegistrationRequest

fun Route.authRoute(
    hostPath: String
) {
    val authController by inject<AuthController>()

    post("$hostPath$AUTH_PATH") {
        val request = call.receive<LoginRequest>()
        val response = authController.authenticate(request)
        call.respond(HttpStatusCode.OK, response)
    }

    post("$hostPath$REGISTER_PATH") {
        val request = call.receive<RegistrationRequest>()
        val response = authController.registration(request)
        call.respond(HttpStatusCode.Created, response)
    }

    post("$hostPath$REFRESH_PATH") {
        val request = call.receive<RefreshRequest>()
        val response = authController.refreshToken(request)
        call.respond(HttpStatusCode.OK, response)
    }
}

private const val ENDPOINT = "/auth"

private const val AUTH_PATH = "$ENDPOINT/signin"
private const val REGISTER_PATH = "$ENDPOINT/registration"
private const val REFRESH_PATH = "$ENDPOINT/refresh"