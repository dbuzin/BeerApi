package server.model.exceptions

class ReviewNotFoundException(override val message: String = "Review with this id doesn't exists!") : Throwable()
class InvalidReviewException(override val message: String = "Invalid request body!") : Throwable()