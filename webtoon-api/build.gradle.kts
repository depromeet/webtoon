dependencies {
    implementation(project(":webtoon-core"))
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // jwt
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:3.16.0")

    // swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    integrationTestImplementation("com.h2database:h2")
    testImplementation(testFixtures(project(":webtoon-core")))
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = true
    mainClass.set("com.depromeet.webtoon.api.WebtoonApiApplicationKt")
    archiveFileName.set("webtoon-api.jar")
}
