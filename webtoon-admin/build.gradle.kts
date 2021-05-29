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

node {
    download.set(true)
    version.set("14.17.0")
    nodeProjectDir.set(file("${project.projectDir}/src/front"))
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = true
    mainClass.set("com.depromeet.webtoon.admin.WebtoonAdminApplicationKt")
    archiveFileName.set("webtoon-admin.jar")
}

// TODO bootjar 구성시 함께 돌릴수 있도록 할것
tasks.register<YarnTask>("yarnUpgrade") {
    yarnCommand.set(listOf("upgrade"))
}

tasks.register<YarnTask>("buildFront") {
    dependsOn("yarnUpgrade")
    yarnCommand.set(listOf("build"))
}
