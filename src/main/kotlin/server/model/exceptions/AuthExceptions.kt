package server.model.exceptions

class InvalidEmailException(override val message: String = "Email doesn't matches regex!") : Throwable()
class AccountExistsException(override val message: String = "Account with this email already exists!") : Throwable()
class InvalidCredentialsException(override val message: String = "Invalid login or password!") : Throwable()
class InvalidTokenException(override val message: String = "Token is invalid!") : Throwable()