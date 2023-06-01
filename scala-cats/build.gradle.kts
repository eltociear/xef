@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    scala
    `maven-publish`
    signing
    alias(libs.plugins.semver.gradle)
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(projects.xefCore)
    implementation(libs.scala.lang)
    implementation(libs.cats.effect)
    testImplementation(libs.munit.core)
    testImplementation(libs.munit.cats.effect)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.withType<Test>().configureEach {
    useJUnit()
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-Wunused:all", "-Wvalue-discard")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            val scala3Suffix = "_3"
            from(components["java"])

            artifactId = base.archivesName.get() + scala3Suffix

            pom {
                name.set(project.properties["pom.name"]?.toString())
                description.set(project.properties["pom.description"]?.toString())
                url.set(project.properties["pom.url"]?.toString())

                licenses {
                    license {
                        name.set(project.properties["pom.license.name"]?.toString())
                        url.set(project.properties["pom.license.url"]?.toString())
                    }
                }

                developers {
                    developer {
                        id.set(project.properties["pom.developer.id"].toString())
                        name.set(project.properties["pom.developer.name"].toString())
                    }
                }

                scm {
                    url.set(project.properties["pom.smc.url"].toString())
                    connection.set(project.properties["pom.smc.connection"].toString())
                    developerConnection.set(project.properties["pom.smc.developerConnection"].toString())
                }
            }
        }
    }
}

signing {
    val isLocal = gradle.startParameter.taskNames.any { it.contains("publishToMavenLocal", ignoreCase = true) }
    val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
    val signingKey: String? = System.getenv("SIGNING_KEY")
    val signingPassphrase: String? = System.getenv("SIGNING_KEY_PASSPHRASE")

    isRequired = !isLocal
    useGpgCmd()
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassphrase)
    sign(publishing.publications)
}

spotless {
    scala {
        scalafmt("3.7.3").configFile(".scalafmt.conf").scalaMajorVersion("2.13")
    }
}