package server.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import server.model.common.Account
import server.model.response.TokenPairResponse
import java.util.*

interface JwtProvider {
    fun createTokenPair(account: Account): TokenPairResponse
    fun verifyToken(token: String): String?
    fun getVerifier(): JWTVerifier
}

//TODO: make config
object JwtProviderImpl: JwtProvider {

    private const val SECRET = "dev_secret"
    private const val ISSUER = "https://www.reviewbeer.api.ru"
    private const val ACCESS_LIFETIME: Long = 3600000L * 24L // 24h
    private const val REFRESH_LIFETIME: Long = 3600000L * 24L * 30L // 30 days
    private val algorithm = Algorithm.HMAC512(SECRET)

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .build()

    override fun createTokenPair(account: Account): TokenPairResponse {
        return TokenPairResponse(
            accessToken = generateToken(account, getLifeTime()),
            refreshToken = generateToken(account, getLifeTime(REFRESH_LIFETIME))
        )
    }

    override fun verifyToken(token: String): String? {
        return verifier.verify(token).claims["accountId"]?.asString()
    }

    override fun getVerifier(): JWTVerifier = verifier

    private fun generateToken(
        account: Account,
        expiration: Date
    ) = JWT.create()
        .withSubject("Authentication")
        .withIssuer(ISSUER)
        .withClaim("accountId", account.id)
        .withClaim("email", account.email)
        .withExpiresAt(expiration)
        .sign(algorithm)

    private fun getLifeTime(lifetime: Long = ACCESS_LIFETIME) = Date(System.currentTimeMillis() + lifetime)
}