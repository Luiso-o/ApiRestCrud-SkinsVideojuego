plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "Skin.VideoGame"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.4")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	implementation("org.yaml:snakeyaml:2.0")

	implementation("io.projectreactor:reactor-core:3.5.9")

	compileOnly("org.projectlombok:lombok:1.18.26")
	annotationProcessor("org.projectlombok:lombok:1.18.26")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.0.7")

	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("io.swagger:swagger-annotations:1.6.11")

	//Test
	testImplementation("junit:junit:4.13.2")
	testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")

	//Jacson
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
