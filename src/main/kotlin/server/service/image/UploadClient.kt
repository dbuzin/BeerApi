package server.service.image

import com.uploadcare.api.Client
import com.uploadcare.upload.FileUploader
import com.uploadcare.urls.Urls
import server.model.common.PhotoFile

class UploadClient {
    private val env = System.getenv()
    private val publicKey = env["CARE_PUBLIC"]
    private val secretKey = env["CARE_PRIVATE"]

    private val client = Client(publicKey, secretKey)

    private fun uploadImage(photoFile: PhotoFile): String {
        val fileUploader = FileUploader(client, photoFile.body, photoFile.name)
        val uploaded = fileUploader.upload()
        return Urls.cdn(uploaded.cdnPath()).toString()
    }

    fun uploadImages(photoFiles: List<PhotoFile>): List<String> {
        val urls = ArrayList<String>()
        photoFiles.forEach {
            urls.add(uploadImage(it))
        }
        return urls
    }
}