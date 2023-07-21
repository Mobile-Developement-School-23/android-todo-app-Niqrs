
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentDisposition
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

class TelegramApi(
    private val httpClient: HttpClient
) {

    suspend fun uploadFile(file: File, fileName: String, message: String, token: String, chatId: String) {
        val response = httpClient.post("https://api.telegram.org/bot$token/sendDocument") {
            parameter("chat_id", chatId)
            parameter("caption", message)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("document", file.readBytes(), Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "${ContentDisposition.Parameters.FileName}=$fileName"
                            )
                        })
                    }
                )
            )
        }
        println("CODE = ${response.status.value}")
    }

    suspend fun sendMessage(message: String, token: String, chatId: String) {
        val response = httpClient.post("https://api.telegram.org/bot$token/sendMessage") {
            parameter("chat_id", chatId)
            parameter("text", message)
        }
        println("CODE = ${response.status.value}")
    }
}