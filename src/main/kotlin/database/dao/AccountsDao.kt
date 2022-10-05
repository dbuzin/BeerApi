package database.dao

import database.entity.AccountsEntity
import database.entity.AccountsTable
import database.model.Account
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.util.*

class AccountsDao {
    fun insert(account: Account) = transaction {
        AccountsEntity.new {
            this.name = account.name
            this.email = account.email
            this.password = BCrypt.hashpw(account.password, BCrypt.gensalt())
            this.reviewsCount = account.reviewsCount
        }.id.value.toString()
    }

    fun getByEmail(email: String) = transaction {
        AccountsEntity.find { AccountsTable.email eq email }.singleOrNull()
    }

    fun getById(accountId: String) = transaction {
        AccountsEntity.find { AccountsTable.id eq  UUID.fromString(accountId) }.singleOrNull()
    }
}