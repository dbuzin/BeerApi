package di

import database.dao.AccountsDao
import database.dao.BeerDao
import database.dao.BeerTypesDao
import org.koin.dsl.module

object DatabaseInjection {
    val koinBeans = module {
        single { AccountsDao() }
        single { BeerDao() }
        single { BeerTypesDao() }
    }
}