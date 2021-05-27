apply {
    plugin("org.flywaydb.flyway")
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt::jpa")

    // TODO 크롤러는 별도로 분리할것??!!
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jsoup:jsoup")
    implementation("io.swagger:swagger-annotations:1.5.20")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    integrationTestImplementation("com.h2database:h2")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }
