 # JSON Wrappers for Kotlin

[![License](https://img.shields.io/badge/License-Apache_2.0-green.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.cloudflight.json/json-wrapper.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.cloudflight.json/json-wrapper)

This library contains Data Transfer Objects (DTOs) for popular JSON formats:

* `angular.json`
* `package.json`
* `package-lock.json`

Additionally, it contains mapping DTOs for the [License Gradle Plugin](https://github.com/cloudflightio/license-gradle-plugin) for re-use 
in other software products.

Each DTO comes with a deserializer based on Kotlin Serialization, i.e.:

````kotlin
val angular: Angular = Angular.readFromFile(File("angular.json"))
````