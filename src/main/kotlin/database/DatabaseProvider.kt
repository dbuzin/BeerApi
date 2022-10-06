package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import database.dao.BeerTypesDao
import database.entity.AccountsTable
import database.entity.BeerTable
import database.entity.BeerTypeTable
import database.entity.CountriesTable
import database.model.BeerType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseProvider: KoinComponent {

    private val beerTypesDao by inject<BeerTypesDao>()

    private val types = listOf(
        BeerType(
            name = "lager",
            description = ""
        ),
        BeerType(
            name = "pilsner",
            description = ""
        ),
        BeerType(
            name = "staut",
            description = ""
        ),
        BeerType(
            name = "goze",
            description = ""
        ),
        BeerType(
            name = "kriek",
            description = ""
        )
    )
    fun init() {
        Database.connect(hikariDev())
        transaction {
            SchemaUtils.create(
                AccountsTable,
                BeerTypeTable,
                CountriesTable,
                BeerTable
            )
            beerTypesDao.insert(types)
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