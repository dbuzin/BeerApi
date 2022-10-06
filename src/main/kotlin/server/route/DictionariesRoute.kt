package server.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import server.controller.DictionariesController

fun Route.dictionariesRoute(
    hostPath: String
) {
    val dictionariesController by inject<DictionariesController>()

    get("$hostPath$BEER_TYPES_PATH") {
        val response = dictionariesController.getBeerTypesList()
        call.respond(HttpStatusCode.OK, response)
    }

    get("$hostPath$COUNTRIES_PATH") {
        val response = dictionariesController.getCountriesList()
        call.respond(HttpStatusCode.OK, response)
    }
}

private const val ENDPOINT = "/dictionaries"

private const val BEER_TYPES_PATH = "$ENDPOINT/types"
private const val COUNTRIES_PATH = "$ENDPOINT/countries"