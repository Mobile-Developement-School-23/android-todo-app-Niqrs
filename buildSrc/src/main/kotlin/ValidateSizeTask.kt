
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class ValidateSizeTask : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty
    @get:Input
    @get:Optional
    abstract val maxSizeMb: Property<Float>
    @get:OutputFile
    abstract val infoFile: RegularFileProperty

    @TaskAction
    fun validate() {
        val apkFile = apkDir.getApk()
        val maxSize = maxSizeMb.getOrElse(Float.MAX_VALUE)
        val sizeMb = (apkFile.length() / 1024f / 1024f)

        if (sizeMb > maxSize) {
            val properties = gradleLocalProperties(project.rootProject.rootDir)
            val token = properties.getProperty("tg-token")
            val chatId = properties.getProperty("tg-chat-id")

            val message = "Apk file is too big. \n" +
                    "size: $sizeMb MB \n" +
                    "maxSize: $maxSize MB"

            runBlocking {
                val api = TelegramApi(HttpClient(CIO))
                api.sendMessage(message, token, chatId)
            }
            throw Exception(message)
        }

        infoFile.get().asFile.writeText("File size: $sizeMb MB")
        println("File size: $sizeMb MB")
    }
}
