package server.module

import database.DatabaseProvider
import database.dao.AccountsDao
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.slf4j.event.Level
import server.route.authRoute
import server.route.beerRoute
import server.route.dictionariesRoute
import server.route.jwtConfiguration
import server.statuspages.authStatusPages
import server.util.JwtProvider

fun Application.mainModule() {
    val databaseProvider by inject<DatabaseProvider>()
    val accountsDao by inject<AccountsDao>()
    val jwtProvider by inject<JwtProvider>()

    databaseProvider.init()

    install(CallLogging) {
        level = Level.DEBUG
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }
    install(Authentication) {
        jwtConfiguration(
            accountsDao = accountsDao,
            jwtProvider = jwtProvider
        )
    }
    install(StatusPages) {
        authStatusPages()
    }

    routing {
        authRoute(HOST_PATH)
        authenticate("jwt") {
            beerRoute(HOST_PATH)
            dictionariesRoute(HOST_PATH)
        }
    }
}

private const val HOST_PATH = "/api/v1"