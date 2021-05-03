dependencies {
    implementation(project(":webtoon-core"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
}
tasks.jar {
    enabled = false
    archiveClassifier.set("library")
}

tasks.bootJar {
    enabled = true
    mainClass.set("com.depromeet.webtoon.batch.WebtoonBatchApplicationKt")
    archiveFileName.set("webtoon-batch.jar")
}
