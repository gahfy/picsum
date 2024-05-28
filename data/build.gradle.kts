import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.detekt)
    checkstyle
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    reports {
        csv.required = false
        xml.required = false
        html.required = true
    }
}
tasks.jacocoTestReport.dependsOn(tasks.test)

tasks.build.dependsOn(tasks.jacocoTestReport)

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation(libs.junit)
    implementation(libs.coroutines)
    testImplementation(libs.coroutines.test)
}