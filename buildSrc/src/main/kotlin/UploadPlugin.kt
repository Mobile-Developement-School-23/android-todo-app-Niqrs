
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

interface UploadPluginExtension {
    val maxSizeMb: Property<Float>
}

class UploadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw IllegalStateException("android plugin not found!")

        val extension = project.extensions.create<UploadPluginExtension>("uploading")

        androidComponents.onVariants { variant ->
            val variantName = variant.name.capitalize()
            val apk = variant.artifacts.get(SingleArtifact.APK)
            val apkInfoFile = project.layout.buildDirectory.file("apk-info.txt")

            project.tasks.register<ValidateSizeTask>("validateApkSizeFor$variantName") {
                apkDir.set(apk)
                extension.maxSizeMb.orNull?.let {
                    maxSizeMb.set(extension.maxSizeMb)
                }
                infoFile.set(apkInfoFile)
            }

            project.tasks.register<UploadTask>("uploadApkFor$variantName") {
                dependsOn("validateApkSizeFor$variantName")

                apkDir.set(apk)
                apkInfo.set(apkInfoFile)
            }
        }
    }
}
