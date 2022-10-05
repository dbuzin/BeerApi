package server.controller

import database.dao.AccountsDao
import util.toServerAccount
import database.model.Account as DbAccount
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mindrot.jbcrypt.BCrypt
import server.model.exceptions.AccountExistsException
import server.model.exceptions.InvalidCredentialsException
import server.model.exceptions.InvalidEmailException
import server.model.exceptions.InvalidTokenException
import server.model.request.LoginRequest
import server.model.request.RefreshRequest
import server.model.request.RegistrationRequest
import server.model.response.RegistrationResponse
import server.model.response.TokenPairResponse
import server.util.JwtProvider
import server.util.isValidEmail

interface AuthController {
    suspend fun authenticate(login: LoginRequest): TokenPairResponse
    suspend fun registration(account: RegistrationRequest): RegistrationResponse
    suspend fun refreshToken(refreshRequest: RefreshRequest): TokenPairResponse
}

class AuthControllerImpl : AuthController, KoinComponent {

    private val accountsDao by inject<AccountsDao>()
    private val jwtProvider by inject<JwtProvider>()

    override suspend fun authenticate(login: LoginRequest): TokenPairResponse {
        val user = accountsDao.getByEmail(login.email)
        user?.let {
            val isValidPassword = BCrypt.checkpw(login.password, user.password)
            return if (isValidPassword) {
                jwtProvider.createTokenPair(user.toServerAccount())
            } else {
                throw InvalidCredentialsException()
            }
        } ?: throw InvalidCredentialsException()
    }

    override suspend fun registration(account: RegistrationRequest): RegistrationResponse {
        if (account.email.isValidEmail()) {
            try {
                val accountId = accountsDao.insert(
                    DbAccount(
                        name = account.name,
                        email = account.email,
                        password = account.password
                    )
                )
                return RegistrationResponse(
                    accountId = accountId
                )
            } catch (e: Exception) {
                e.printStackTrace()
                throw AccountExistsException()
            }
        } else {
            throw InvalidEmailException()
        }
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequest): TokenPairResponse {
        jwtProvider.verifyToken(refreshRequest.refreshToken)?.let { id ->
            accountsDao.getById(id)?.let { account ->
                return jwtProvider.createTokenPair(account.toServerAccount())
            } ?: throw InvalidTokenException()
        } ?: throw InvalidTokenException()
    }

}