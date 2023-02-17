import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

abstract class MavenPomBuilderExtension {

    abstract val description: Property<String>

    abstract val siblings: ListProperty<String>

    abstract val excludeGroups: ListProperty<String>

    abstract val excludeNames: ListProperty<String>

    abstract val excludeVersions: ListProperty<String>

    abstract val includeStartsWith: ListProperty<String>
}