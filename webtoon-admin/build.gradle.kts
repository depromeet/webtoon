import com.github.gradle.node.yarn.task.YarnTask

plugins {
    id("com.github.node-gradle.node") version "3.0.1"
}

apply {
    plugin("com.github.node-gradle.node")
}

dependencies {
    implementation(project(":webtoon-core"))
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation(testFixtures(project(":webtoon-core")))
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    dependsOn("buildFront")

    enabled = true
    mainClass.set("com.depromeet.webtoon.admin.WebtoonAdminApplicationKt")
    archiveFileName.set("webtoon-admin.jar")
}

tasks.register<YarnTask>("buildFront") {
    workingDir.set(file("src/front"))
    yarnCommand.set(listOf("build"))
}
