package server.util

import io.ktor.http.content.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import server.model.common.PhotoFile
import server.model.exceptions.InvalidReviewException
import server.model.request.CreateReviewRequest

private const val REQUEST_NAME = "Review"

suspend fun MultiPartData.convert(): Pair<CreateReviewRequest, List<PhotoFile>> {
    val photoFiles = ArrayList<PhotoFile>()
    var request: CreateReviewRequest? = null
    forEachPart { part ->
        when (part) {
            is PartData.FormItem -> {
                if (part.name?.equals(REQUEST_NAME, true) == true) {
                    request = Json.decodeFromString(part.value)
                } else {
                    throw InvalidReviewException()
                }
            }
            is PartData.FileItem -> {
                val fileName = part.originalFileName as String
                val fileBytes = part.streamProvider().readBytes()
                photoFiles.add(
                    PhotoFile(
                        name = fileName,
                        body = fileBytes
                    )
                )
            }
            else -> {}
        }
        part.dispose()
    }
    request?.let {
        return Pair(
            it,
            photoFiles.toList()
        )
    } ?: throw InvalidReviewException()
}