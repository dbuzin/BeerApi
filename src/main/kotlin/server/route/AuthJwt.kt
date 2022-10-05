package server.route

import database.dao.AccountsDao
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import util.toServerAccount
import server.util.JwtProvider

fun AuthenticationConfig.jwtConfiguration(
    accountsDao: AccountsDao,
    jwtProvider: JwtProvider
) {
    jwt("jwt") {
        verifier(jwtProvider.getVerifier())
        realm = "https://www.reviewbeer.api.ru"
        validate {
            it.payload.getClaim("accountId").asString()?.let { accountId ->
                accountsDao.getById(accountId)?.toServerAccount()
            }
        }
    }
}