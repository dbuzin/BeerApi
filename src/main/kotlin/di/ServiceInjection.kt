package di

import org.koin.dsl.module
import server.service.image.UploadClient

object ServiceInjection {
    val koinBeans = module {
        single { UploadClient() }
    }
}