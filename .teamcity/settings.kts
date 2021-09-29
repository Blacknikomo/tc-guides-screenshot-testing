import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.1"

project {

    vcsRoot(HttpsGithubComBlacknikomoTcGuidesScreenshotTestingRefsHeadsMaster)

    buildType(ScreenshotTesting)

    features {
        feature {
            type = "Guide"
            param("guide", "{\"id\":\"screenshot-testing\",\"title\":\"Frontend: Screenshot testing\",\"tags\":[\"Sakura UI\",\"middle-level\",\"frontend\"],\"description\":\"Developing piece of software requires a well-established set of tests. Some of them are well known - E2E or unit tests. Some are more rare, e.g. structural tests. Screenshot testing helps to detect visual regressions across set of pages / components / stories. Here we will show, how TeamCity can help Web Developers to detect screenshot changes\",\"duration\":10,\"steps\":[{\"name\":\"Screenshots testing - Project\",\"text\":\"Usually, the frontend testing comprises many steps to be finished. Here is the example of the screenshots testing. We recommend you to have a look on a [repository behind the Project](https://github.com/Blacknikomo/tc-guides-screenshot-testing).\",\"selector\":\"[data-test=overview-header]\",\"requirements\":{\"context\":{\"pathname\":\"/project/LearningCenter_TcGuidesScreenshotTesting\"}}},{\"name\":\"Screenshots testing - Builds Configuration\",\"text\":\"There is a list of builds. Imagine, that you have a web application, and would love to make sure, it's rendered the same on each commit iteration. Everytime you commit changes, it triggers the build. Click on any failed (red) builds to expand it and then press 'next'.\",\"selector\":\"[data-test=overview-header]\",\"requirements\":{\"context\":{\"pathname\":\"/buildConfiguration/LearningCenter_TcGuidesScreenshotTesting_ScreenshotTesting\"}}},{\"name\":\"Screenshots testing - Report failed tests\",\"text\":\"We have predefined a couple of tests. One of them will be always failed. click on the 'Space page screenshot' and you'll see the reported screenshots. You can examine it more deeply in the Build Page. When you are ready to process - press Next\",\"selector\":\"[data-hint-container-id='test-details-hint']\",\"requirements\":{\"context\":{\"href\":\"/buildConfiguration/LearningCenter_TcGuidesScreenshotTesting_ScreenshotTesting#191850\"}}},{\"name\":\"Screenshots testing - Report failed tests\",\"text\":\"We have predefined a couple of tests. One of them will be always failed. click on the 'Space page screenshot' and you'll see the reported screenshots. You can examine it more deeply in the Build Page. When you are ready to process - press Next\",\"selector\":\"[data-hint-container-id='test-details-hint']\",\"requirements\":{\"context\":{\"href\":\"/buildConfiguration/LearningCenter_TcGuidesScreenshotTesting_ScreenshotTesting#191850\"}}},{\"name\":\"Screenshots testing - Failed Build Overview\",\"text\":\"Using the Build Overview Page you can investigate what issues failed the builds.\",\"selector\":\"[data-test='ring-tab']\",\"requirements\":{\"context\":{\"selector\":{\"buildTypeId\":\"LearningCenter_TcGuidesScreenshotTesting_ScreenshotTesting\",\"status\":\"failure\",\"state\":\"finished\",\"tag\":\"guide\"}}}}]}")
        }
    }

}

object ScreenshotTesting : BuildType({
    name = "Screenshot testing"

    artifactRules = "screenshots => screenshots"

    vcs {
        root(HttpsGithubComBlacknikomoTcGuidesScreenshotTestingRefsHeadsMaster)
    }

    steps {
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

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComBlacknikomoTcGuidesScreenshotTestingRefsHeadsMaster : GitVcsRoot({
    name = "TeamCity Guides - Screenshot testing"
    url = "https://github.com/Blacknikomo/tc-guides-screenshot-testing"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
})
