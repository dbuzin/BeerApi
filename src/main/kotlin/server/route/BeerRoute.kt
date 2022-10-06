package server.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import server.controller.BeerController
import server.model.common.FilterBody
import server.model.request.CreateBeerRequest

fun Route.beerRoute(
    hostPath: String
) {
    val beerController by inject<BeerController>()

    post("$hostPath$CREATE_PATH") {
       val request = call.receive<CreateBeerRequest>()
       val response = beerController.createBeerObject(request)
       call.respond(HttpStatusCode.Created, response)
    }

    get("$hostPath$GET_ALL_PATH") {
        val response = beerController.getAllBeerList()
        call.respond(HttpStatusCode.OK, response)
    }

    post("$hostPath$GET_FILTERED_PATH") {
        val request = call.receive<FilterBody>()
        val response = beerController.getFilteredBeerList(request)
        call.respond(HttpStatusCode.OK, response)
    }

    get("$hostPath$FIND_PATH") {
        val barcode = call.parameters["barcode"]
        barcode?.let {
            val response = beerController.findBeerByBarcode(it.toLong())
            call.respond(HttpStatusCode.OK, response)
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}

private const val ENDPOINT = "/beer"

private const val CREATE_PATH = "$ENDPOINT/create"
private const val GET_ALL_PATH = "$ENDPOINT/all"
private const val GET_FILTERED_PATH = "$ENDPOINT/filtered"
private const val FIND_PATH = "$ENDPOINT/find"