plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.devgor.actor-linker"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springAiVersion"] = "1.0.0-M6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.ai:spring-ai-ollama-spring-boot-starter")
	implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
	testImplementation("org.testcontainers:junit-jupiter:1.20.4")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	create("integrationTest") {
		kotlin.srcDir("src/integrationTest/kotlin")
		resources.srcDir("src/integrationTest/resources")
		compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
		runtimeClasspath += output + compileClasspath
	}
}

tasks.register<Test>("integrationTest") {
	description = "Runs integration tests."
	group = "verification"
	testClassesDirs = sourceSets["integrationTest"].output.classesDirs
	classpath = sourceSets["integrationTest"].runtimeClasspath
	shouldRunAfter("test")
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform()
}


