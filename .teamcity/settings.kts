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
    description = "Test Hierarchy of TeamCity"

    subProject(SubProject2)
    subProject(SubProject1)
}


object SubProject1 : Project({
    name = "SubProject_1"
    description = "First Level Subproject_1"

    buildType(SubProject1_TestSomeCommands)
})

object SubProject1_TestSomeCommands : BuildType({
    name = "TestSomeCommands"
    description = "TestSomeCommands"

    params {
        select("env.TestParam_1", "first_option", label = "SHowLabel", description = "Test of variable choice",
                options = listOf("first_option", "second_option", "third_option"))
    }

    steps {
        script {
            name = "Create Text File"
            id = "Create_Text_File"
            workingDir = "/"
            scriptContent = "touch self_created_file.txt"
        }
        script {
            name = "Create Second File"
            id = "Create_Second_File"
            workingDir = "/"
            scriptContent = """echo "Test Note for Second reated file %env.TestParam_1%" >  second_created_file_%env.TestParam_1%.txt"""
        }
    }

    requirements {
        equals("teamcity.agent.name", "ip_172.17.0.1")
    }
})


object SubProject2 : Project({
    name = "SubProject_2"
    description = "First Level Subproject_2"
})
