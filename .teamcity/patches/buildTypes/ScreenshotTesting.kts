package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'ScreenshotTesting'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("ScreenshotTesting")) {
    expectSteps {
        script {
            name = "Prepare environment"
            scriptContent = """
                #!/bin/sh
                npm install
            """.trimIndent()
        }
        script {
            name = "Generate Screenshots"
            scriptContent = """
                #!/bin/sh
                npm run screenshots:ci
            """.trimIndent()
        }
        script {
            name = "Compare Screenshots"
            scriptContent = """
                #!/bin/sh
                npm run test
            """.trimIndent()
        }
    }
    steps {
        update<ScriptBuildStep>(0) {
            clearConditions()
            dockerImage = "node:lts"
        }
        update<ScriptBuildStep>(1) {
            clearConditions()
            dockerImage = "node:lts"
        }
        update<ScriptBuildStep>(2) {
            clearConditions()
            dockerImage = "node:lts"
        }
    }
}
