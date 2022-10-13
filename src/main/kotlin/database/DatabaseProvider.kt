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
        Database.connect(hikariDev())
        FillingUtils.getAllCountries()
        transaction {
            SchemaUtils.create(
                AccountsTable,
                BeerTypeTable,
                CountriesTable,
                BeerTable,
                ReviewTable
            )
            beerTypesDao.insert(FillingUtils.getAllBeerTypes())
            countriesDao.insert(FillingUtils.getAllCountries())
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        val env = System.getenv()
        val user = env["SLACK_DB_USER"]
        val pass = env["SLACK_DB_PASS"]
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://$user:$pass@192.168.0.85:3306/slack_bot_db?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    private fun hikariDev(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}