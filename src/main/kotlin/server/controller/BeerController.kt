package server.controller

import database.dao.BeerDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import server.model.common.Beer
import server.model.common.FilterBody
import server.model.common.ListResponse
import server.model.request.CreateBeerRequest
import server.model.response.BeerSingleResponse
import server.model.response.CreateBeerResponse
import common.toBeer

interface BeerController {

    suspend fun createBeerObject(beer: CreateBeerRequest): CreateBeerResponse

    suspend fun findBeerByBarcode(barcode: Long): BeerSingleResponse

    suspend fun getAllBeerList(): ListResponse<Beer>

    suspend fun getFilteredBeerList(filterBody: FilterBody): ListResponse<Beer>
}

class BeerControllerImpl : BeerController, KoinComponent {

    private val beerDao by inject<BeerDao>()

    override suspend fun createBeerObject(beer: CreateBeerRequest): CreateBeerResponse {
        return CreateBeerResponse(
            beerId = beerDao.insert(
                beer.toBeer()
            )
        )
    }

    override suspend fun findBeerByBarcode(barcode: Long): BeerSingleResponse {
        return BeerSingleResponse(
            beer = beerDao.findByBarcode(barcode)
        )
    }

    override suspend fun getAllBeerList(): ListResponse<Beer> {
        return ListResponse(
            data = beerDao.getAll().toBeer()
        )
    }

    override suspend fun getFilteredBeerList(filterBody: FilterBody): ListResponse<Beer> {
        return ListResponse(
            data = beerDao.getFiltered(
                filter = filterBody.filter,
                sort = filterBody.sort
            ).toBeer()
        )
    }

}