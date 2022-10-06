package di

import org.koin.dsl.module
import server.controller.AuthController
import server.controller.AuthControllerImpl
import server.controller.BeerController
import server.controller.BeerControllerImpl

object ControllersInjection {
    val koinBeans = module {
        single<AuthController> { AuthControllerImpl() }
        single<BeerController> { BeerControllerImpl() }
    }
}