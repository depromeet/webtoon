dependencies {
    implementation(project(":webtoon-core"))
    implementation(project(":webtoon-common"))
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // security::jwt
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.auth0:java-jwt:3.16.0")

    // swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(testFixtures(project(":webtoon-core")))

    integrationTestImplementation("com.h2database:h2")
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = true
    mainClass.set("com.depromeet.webtoon.api.WebtoonApiApplicationKt")
    archiveFileName.set("webtoon-api.jar")
}
