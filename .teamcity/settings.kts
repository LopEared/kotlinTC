import jetbrains.buildServer.configs.kotlin.*

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

    buildType(SubProject1_TestCreateFileWithParametres)
})

object SubProject1_TestCreateFileWithParametres : BuildType({
    name = "Test Create File With parametres"
    description = "Test creting Parameters and files and CLI"
})


object SubProject2 : Project({
    name = "SubProject_2"
    description = "TestSubProj_2"
})


object SubProject3 : Project({
    name = "SubProject_3"
    description = "TestSubProj_3"
})
