package di

import org.koin.dsl.module
import server.controller.AuthController
import server.controller.AuthControllerImpl

object ControllersInjection {
    val koinBeans = module {
        single<AuthController> { AuthControllerImpl() }
    }
}