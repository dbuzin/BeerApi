package di

import database.dao.AccountsDao
import org.koin.dsl.module

object DatabaseInjection {
    val koinBeans = module {
        single { AccountsDao() }
    }
}