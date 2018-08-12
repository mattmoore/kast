import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
}

group = "com.mattmoore"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

tasks {
  "test"(Test::class) {
    useJUnitPlatform()
  }
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.github.cretz.kastree:kastree-ast-jvm:0.1.0")
    compile("com.github.cretz.kastree:kastree-ast-psi:0.1.0")
    compile("com.github.cretz.kastree:kastree-ast-common:0.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testRuntime("org.junit.platform:junit-platform-console:1.2.0")
}
