import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val serialization_version: String by project
val koin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val hikari_version: String by project
val h2_version: String by project
val mysql_connection_version: String by project
val bcrypt_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization").version("1.7.10")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.dbuzin"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("dev.dbuzin.ApplicationKt")
}

repositories {
    mavenLocal()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")

    implementation("org.jetbrains.exposed:exposed:$exposed_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("mysql:mysql-connector-java:$mysql_connection_version")

    implementation("org.mindrot:jbcrypt:$bcrypt_version")

    implementation("io.insert-koin:koin-ktor:$koin_version")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}