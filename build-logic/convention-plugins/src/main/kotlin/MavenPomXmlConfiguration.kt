import org.gradle.api.Project
import org.gradle.api.XmlProvider
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.extra

internal fun Project.configurePomXmlWithDependencies(
    xmlProvider: XmlProvider,
    pomBuilderExtension: MavenPomBuilderExtension,
) {

    val siblingDependencies = pomBuilderExtension.siblings.getOrElse(listOf<String>())
    val excludeGroups = pomBuilderExtension.excludeGroups.getOrElse(listOf<String>())
    val excludeNames = pomBuilderExtension.excludeNames.getOrElse(listOf<String>())
    val excludeVersions = pomBuilderExtension.excludeVersions.getOrElse(listOf<String>())
    val includeStartsWithNames = pomBuilderExtension.includeStartsWith.getOrElse(listOf<String>())

    // include sibling dependencies
    val dependencies = xmlProvider.asNode().appendNode("dependencies")
    siblingDependencies.forEach {
        val dependency = dependencies.appendNode("dependency")
        dependency.appendNode("groupId", extra["artifactGroupId"])
        dependency.appendNode("artifactId", it)
        dependency.appendNode("version", extra["artifactVersionName"])
    }

    val visited = arrayListOf<Dependency>()

    // include any transitive dependencies
    configurations.getByName("implementation").allDependencies.forEach { depenency ->
        if (!visited.contains(depenency)) {
            visited.add(depenency)
            appendDependencyNodeToPomXml(
                depenency,
                dependencies,
                excludeGroups,
                excludeNames,
                includeStartsWithNames,
                excludeVersions
            )
        }
    }
    configurations.getByName("api").allDependencies.forEach { depenency ->
        if (!visited.contains(depenency)) {
            visited.add(depenency)
            appendDependencyNodeToPomXml(
                depenency,
                dependencies,
                excludeGroups,
                excludeNames,
                includeStartsWithNames,
                excludeVersions
            )
        }
    }
}

private fun Project.appendDependencyNodeToPomXml(
    dependency: Dependency,
    dependencies: groovy.util.Node,
    excludeGroups: List<String>,
    excludeNames: List<String>,
    includedStartsWithNames: List<String>,
    excludeVersions: List<String>
) {

    if (dependency.group == null
        || excludeGroups.contains(dependency.group)
        || excludeNames.contains(dependency.name)
        || !includedStartsWithNames.contains(dependency.name)
        || excludeVersions.contains(dependency.version)) {

        println(
            "ignoring dependency ${dependency.group}:${dependency.name}:${dependency.version}"
        )
    } else {
        val dependencyNode = dependencies.appendNode("dependency")
        dependencyNode.appendNode("groupId", dependency.group)
        println(dependency.group)
        println(dependency.version)
        println(dependency.name)
        dependencyNode.appendNode("artifactId", dependency.name)
        dependencyNode.appendNode("version", dependency.version)
    }
}