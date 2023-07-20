
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class ValidateSizeTask : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @TaskAction
    fun validate() {
        val apkFile = apkDir.getApk()
        val maxSize = 9f //MB size
        val sizeMb = (apkFile.length() / 1024f / 1024f)
        if (sizeMb > maxSize) {
            val api = TelegramApi(HttpClient(CIO))
            val properties = gradleLocalProperties(project.rootProject.rootDir)
            val token = properties.getProperty("tg-token")
            val chatId = properties.getProperty("tg-chat-id")
            val message = "Apk file is too big. \n" +
                    "size: $sizeMb MB \n" +
                    "maxSize: $maxSize MB"

            runBlocking {
                api.sendMessage(message, token, chatId)
            }
            throw Exception(message)
        }

        println("Apk size:$sizeMb MB")
    }
}