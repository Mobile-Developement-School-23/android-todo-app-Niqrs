
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class UploadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw IllegalStateException("android plugin not found!")


        androidComponents.onVariants { variant ->
            val variantName = variant.name.capitalize()
            val apk = variant.artifacts.get(SingleArtifact.APK)

            val validate = project.tasks.register<ValidateSizeTask>("validateApkSizeFor$variantName") {
                apkDir.set(apk)
            }

            val upload = project.tasks.register<UploadTask>("uploadApkFor$variantName") {
                dependsOn(validate.name)
                apkDir.set(apk)
            }
        }
    }
}
