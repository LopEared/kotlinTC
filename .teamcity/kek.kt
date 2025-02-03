import jetbrains.buildServer.configs.kotlin.*

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
