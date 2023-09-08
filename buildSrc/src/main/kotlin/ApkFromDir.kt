import org.gradle.api.file.DirectoryProperty

fun DirectoryProperty.getApk() =
    get().asFileTree.files
        .first { it.extension == "apk" }