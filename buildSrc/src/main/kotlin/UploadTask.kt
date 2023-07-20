
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class UploadTask : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @TaskAction
    fun upload() {
        val api = TelegramApi(HttpClient(CIO))
        val properties = gradleLocalProperties(project.rootProject.rootDir)
        val token = properties.getProperty("tg-token")
        val chatId = properties.getProperty("tg-chat-id")

        val apkFile = apkDir.getApk()
        runBlocking {
            api.uploadFile(apkFile, token, chatId)
        }
    }
}

