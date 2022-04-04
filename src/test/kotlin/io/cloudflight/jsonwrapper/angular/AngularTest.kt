package io.cloudflight.jsonwrapper.angular

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class AngularTest {

    @Test
    fun parseAngular() {
        val angular: Angular = Angular.readFromFile(File("src/test/resources/angular/angular.json"))
        assertNotNull(angular.projects)
        assertEquals(1, angular.projects.size)
        assertTrue(angular.projects.containsKey("frontend"))
        val frontend: Angular.Project = angular.projects.getValue("frontend")
        assertEquals("src", frontend.sourceRoot)
    }

    @Test
    fun parseAngularCore() {
        val angular: Angular = Angular.readFromFile(File("src/test/resources/angular/angular-core.json"))
        assertNotNull(angular.projects)
        assertEquals(16, angular.projects.size)
        angular.getSourceRootsOfAllProjects().first()
    }

    /**
     * an angular.json file can also be entirely empty
     */
    @Test
    fun parseAngularDesignerAiBridge() {
        val angular: Angular = Angular.readFromFile(File("src/test/resources/angular/angular-designer-ai-bridge.json"))
        assertTrue(angular.projects.isEmpty())
    }

    @Test
    fun angularMultiProjectsOneSource() {
        val angular: Angular = Angular.readFromFile(File("src/test/resources/angular/myProject-angular.json"))
        assertNotNull(angular.projects)
        assertEquals(2, angular.projects.size)
        assertEquals(1, angular.getSourceRootsOfAllProjects().size)
        assertEquals("src", angular.getSourceRootsOfAllProjects().first())
    }

    @Test
    fun emptySourceRoot() {
        assertTrue(
            Angular(
                mapOf(
                    "project1" to Angular.Project(sourceRoot = null),
                    "project2" to Angular.Project(sourceRoot = "")
                )
            ).getSourceRootsOfAllProjects().isEmpty()
        )
    }
}