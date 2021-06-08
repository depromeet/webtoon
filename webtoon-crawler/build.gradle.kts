dependencies {
    api(project(":webtoon-common"))
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("org.jsoup:jsoup")
}

tasks.jar { enabled = true }
