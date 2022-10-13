package di

import org.koin.dsl.module
import server.controller.*

object ControllersInjection {
    val koinBeans = module {
        single<AuthController> { AuthControllerImpl() }
        single<BeerController> { BeerControllerImpl() }
        single<DictionariesController> { DictionariesControllerImpl() }
        single<ReviewsController> { ReviewsControllerImpl() }
    }
}