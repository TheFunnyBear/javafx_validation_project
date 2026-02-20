plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.kosolapova.javafx.validation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.jfoenix:jfoenix:9.0.10")
    implementation("org.openjfx:javafx-controls:17.0.1")
    implementation("org.openjfx:javafx-graphics:17.0.1")
    implementation("org.openjfx:javafx-fxml:17.0.1")
    implementation("org.jfree:jfreechart:1.5.4")



    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


javafx {
    version = "21"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics"
    )
}


application {
    mainClass.set("com.kosolapova.javafx.validation.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.run {
    classpath += configurations["runtimeClasspath"]
}

tasks.named<JavaExec>("run") {
    jvmArgs = listOf(
        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
        "--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED",
        "--add-exports=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED",
        "--add-exports=javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED"
    )
}


tasks.jar {
    manifest {
        attributes(mapOf(
            "Main-Class" to "com.kosolapova.javafx.validation.Main"
        ))
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val configurationsRuntimeClasspath = configurations["runtimeClasspath"]
    configurationsRuntimeClasspath.filter { !it.isDirectory() }.forEach { file ->
        from(zipTree(file))
    }
    configurationsRuntimeClasspath.filter { it.isDirectory() }.forEach { dir ->
        from(dir)
    }
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}

