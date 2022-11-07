package dev.dbuzin

import database.DatabaseProvider
import di.ControllersInjection
import di.DatabaseInjection
import di.ServiceInjection
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import server.module.mainModule
import server.util.JwtProvider
import server.util.JwtProviderImpl

fun main(args: Array<String>) {
    embeddedServer(
        factory = Netty,
        port = 80
    ) {
        module {
            install(Koin) {
                modules(
                    module {
                        single { DatabaseProvider() }
                        single<JwtProvider> { JwtProviderImpl }
                    },
                    DatabaseInjection.koinBeans,
                    ControllersInjection.koinBeans,
                    ServiceInjection.koinBeans
                )
            }
        }
        mainModule()
    }.start(wait = true)
}