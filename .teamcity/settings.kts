import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

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

version = "2024.12"

project {
    description = "TestRootProject2"

    subProject(SubProject1)
    subProject(SubProject2)
    subProject(SubProject3)
}


object SubProject1 : Project({
    name = "SubProject_1"
    description = "TestSubProj_1"

    buildType(SubProject1_TestBuildConfigsSteps1)
})

object SubProject1_TestBuildConfigsSteps1 : BuildType({
    name = "Test_BuildConfigsSteps_1"
    description = "Test SOme COmmands in TeamcCity"

    params {
        select("env.barnchChoice", "first_option", label = "MAKE CHOICE!", description = "This filed intended for make choice of repo branch",
                options = listOf("first_option", "second_option", "third_option", "fourth_option"))
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "CreateFiles"
            id = "CreateFiles"
            workingDir = "/"
            scriptContent = """
                echo $TQ
                First line is test option choice is: %env.barnchChoice%
                Current Build is: %build.number%
                Current Build Branch is: %teamcity.build.branch%
                    $TQ > %build.number%_file_name.txt
            """.trimIndent()
        }
    }

    requirements {
        equals("teamcity.agent.name", "ip_172.17.0.1")
    }
})


object SubProject2 : Project({
    name = "SubProject_2"
    description = "TestSubProj_2"
})


object SubProject3 : Project({
    name = "SubProject_3"
    description = "TestSubProj_3"
})
