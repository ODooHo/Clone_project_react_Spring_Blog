plugins {
	kotlin("jvm") version "1.8.10"
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.dooho"
version = "0.0.1-SNAPSHOT"


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation(group = "io.jsonwebtoken", name = "jjwt", version = "0.9.1")
	implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
	implementation("io.awspring.cloud:spring-cloud-starter-aws:2.4.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}



tasks.test {
	useJUnitPlatform()
}
