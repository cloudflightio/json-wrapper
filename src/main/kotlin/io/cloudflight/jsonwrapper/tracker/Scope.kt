package io.cloudflight.jsonwrapper.tracker

enum class Scope {
    Compile, Provided, Runtime, Test,

    /**
     * All dependencies that are required for compilation, i.e. annotation processors or dev-dependencies in npm modules
     */
    Development
}