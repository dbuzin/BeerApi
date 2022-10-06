package server.controller

import common.toBeerType
import common.toCountry
import database.dao.BeerTypesDao
import database.dao.CountriesDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import server.model.common.BeerType
import server.model.common.Country
import server.model.common.ListResponse

interface DictionariesController {

    suspend fun getCountriesList() : ListResponse<Country>

    suspend fun getBeerTypesList() : ListResponse<BeerType>
}

class DictionariesControllerImpl: DictionariesController, KoinComponent {

    private val beerTypesDao by inject<BeerTypesDao>()
    private val countriesDao by inject<CountriesDao>()

    override suspend fun getCountriesList(): ListResponse<Country> {
        return ListResponse(
            data = countriesDao.getAllCountries().toCountry()
        )
    }

    override suspend fun getBeerTypesList(): ListResponse<BeerType> {
        return ListResponse(
            data = beerTypesDao.getAllTypes().toBeerType()
        )
    }
}