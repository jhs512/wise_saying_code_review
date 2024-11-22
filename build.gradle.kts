plugins {
    id("java")
}

group = "com.ll"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Jackson 의존성 추가
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
}

tasks.test {
    useJUnitPlatform()
}