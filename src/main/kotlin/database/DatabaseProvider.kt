package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import database.dao.BeerTypesDao
import database.dao.CountriesDao
import database.entity.*
import database.util.FillingUtils
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseProvider: KoinComponent {

    private val beerTypesDao by inject<BeerTypesDao>()
    private val countriesDao by inject<CountriesDao>()

    fun init() {
        Database.connect(hikari())
        FillingUtils.getAllCountries()
        transaction {
            SchemaUtils.create(
                AccountsTable,
                BeerTypeTable,
                CountriesTable,
                BeerTable,
                ReviewTable
            )
            if ((BeerTypeEntity.count() < 1) && (CountriesEntity.count() < 1)) {
                beerTypesDao.insert(FillingUtils.getAllBeerTypes())
                countriesDao.insert(FillingUtils.getAllCountries())
            }
        }
    }

    private fun hikari(devMode: Boolean = false): HikariDataSource {
        val env = System.getenv()
        val user = env["BEER_DB_USER"]
        val pass = env["BEER_DB_PASS"]
        val config = HikariConfig().apply {
            driverClassName = if (devMode) DEV_DRIVER else PROD_DRIVER
            jdbcUrl = getDbUrl(
                user = user,
                pass = pass
            )
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    private fun getDbUrl(
        user: String? = null,
        pass: String? = null
    ): String {
        return if (user != null && pass != null) {
            "jdbc:mysql://$user:$pass@localhost:3306/$DB_NAME?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"
        } else {
            "jdbc:h2:mem:test"
        }
    }

    private companion object {
        const val DEV_DRIVER = "org.h2.Driver"
        const val PROD_DRIVER = "com.mysql.cj.jdbc.Driver"
        const val DB_NAME = "beer_review_db"
    }
}