plugins {
    kotlin("plugin.serialization") version "1.7.20"
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    signing
}

description = "Kotlin Wrappers for popular JSON formats"
group = "io.cloudflight.json"

autoConfigure {
    java {
        languageVersion.set(JavaLanguageVersion.of(11))
        vendorName.set("Cloudflight")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.compileKotlin.configure {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.io.path.ExperimentalPathApi"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/cloudflightio/json-wrapper")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                inceptionYear.set("2022")
                organization {
                    name.set("Cloudflight")
                    url.set("https://cloudflight.io")
                }
                developers {
                    developer {
                        id.set("klu2")
                        name.set("Klaus Lehner")
                        email.set("klaus.lehner@cloudflight.io")
                    }
                }
                scm {
                    connection.set("scm:ggit@github.com:cloudflightio/json-wrapper.git")
                    developerConnection.set("scm:git@github.com:cloudflightio/json-wrapper.git")
                    url.set("https://github.com/cloudflightio/json-wrapper")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getenv("MAVEN_USERNAME"))
            password.set(System.getenv("MAVEN_PASSWORD"))
        }
    }
}

signing {
    setRequired {
        System.getenv("PGP_SECRET") != null
    }
    useInMemoryPgpKeys(System.getenv("PGP_SECRET"), System.getenv("PGP_PASSPHRASE"))
    sign(publishing.publications.getByName("maven"))
}