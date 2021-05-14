dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")

    // TODO 크롤러는 별도로 분리할것??!!
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jsoup:jsoup")
    // flyway
    implementation("org.flywaydb:flyway-core")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    integrationTestImplementation("com.h2database:h2")
}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }
