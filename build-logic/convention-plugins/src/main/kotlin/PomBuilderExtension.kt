import org.gradle.api.provider.Property

abstract class PomBuilderExtension {

    abstract val description: Property<String>

    abstract val siblings: Property<List<String>>

    abstract val excludes: Property<List<String>>
}