plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("com.gradle.build-scan").version("2.1")
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.21")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    compile("no.tornado:tornadofx:1.7.19")
    // Use Monocle for headless testfx
    testCompile("org.testfx:openjfx-monocle:8u76-b04")
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
