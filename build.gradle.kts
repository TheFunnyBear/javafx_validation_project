plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.kosolapova.javafx.validation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()  // ОБЯЗАТЕЛЬНО: JavaFX лежит в Maven Central
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

javafx {
    version = "21"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml"
    )
    // platform = "win"  // опционально: укажите платформу (win/mac/linux)
}

application {
    mainClass.set("com.kosolapova.javafx.validation.Main")  // полный путь к вашему Main-классу
}

tasks.test {
    useJUnitPlatform()
}
