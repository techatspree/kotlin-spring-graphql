import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    val kotlinVersion = "1.3.11"

    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "2.1.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "de.akquinet.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    jcenter()
}

val graphqlKickstartVersion = "5.10.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("mysql:mysql-connector-java")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.4.2")

    compile("com.graphql-java-kickstart:graphql-spring-boot-starter:$graphqlKickstartVersion")
    runtime("com.graphql-java-kickstart:altair-spring-boot-starter:$graphqlKickstartVersion")
    testCompile("com.graphql-java-kickstart:graphql-spring-boot-starter-test:$graphqlKickstartVersion") {
        exclude(module = "mockito-core")
    }
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testCompile("com.h2database:h2:1.4.199")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

sourceSets {
    create("test-integration") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val testIntegrationImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

configurations["test-integrationRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
    val testcontainersVersion = "1.9.1"
    testIntegrationImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter-test:$graphqlKickstartVersion")
    testIntegrationImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testIntegrationImplementation("org.testcontainers:mariadb:$testcontainersVersion")
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["test-integration"].output.classesDirs
    classpath = sourceSets["test-integration"].runtimeClasspath
    shouldRunAfter("test")
}

tasks.check { dependsOn(integrationTest) }
