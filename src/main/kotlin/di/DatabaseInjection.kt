package di

import database.dao.*
import org.koin.dsl.module

object DatabaseInjection {
    val koinBeans = module {
        single { AccountsDao() }
        single { BeerDao() }
        single { BeerTypesDao() }
        single { CountriesDao() }
        single { ReviewsDao() }
    }
}