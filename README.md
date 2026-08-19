# HandcraftedBits Parent POM

A parent POM used by HandcraftedBits Maven projects.

# Versioning

Versions are never written literally into a POM.  Instead, `<version>` is `${revision}${changelist}`, where:

* `revision` is the version number, e.g. `1.7.1`.
* `changelist` is the qualifier applied to it, normally `-SNAPSHOT`.

So an ordinary build of the development line produces `1.7.1-SNAPSHOT`, and `revision` only needs to be changed when a
new development line is started.  Because the number appears exactly once in the repository, merging between branches
does not produce version conflicts.

A build performed against a `release/*` tag takes its version from the tag instead:
[maven-git-versioning-extension](https://github.com/qoomon/maven-git-versioning-extension), configured in
`.mvn/maven-git-versioning-extension.xml`, overrides `revision` with the tag's version and clears `changelist`.  Checking
out tag `release/1.7.1` and running `mvn install` therefore produces `1.7.1` with no additional flags:

```
git checkout release/1.7.1
mvn -P release deploy
```

Note that tags are only considered when `HEAD` is detached, which is the case after checking a tag out.  A tag that
happens to point at the tip of a checked-out branch is ignored, so ordinary development builds always produce snapshots.

`flatten-maven-plugin` resolves these properties in the installed and deployed POM, using `resolveCiFriendliesOnly` mode
so that no other POM content is altered.  It is bound in this POM and is therefore inherited: projects using this parent
get correct behavior for both `pom` and `jar` packaging without configuring anything.

## Applying this to other projects

`maven-git-versioning-extension` is a core extension and **cannot be inherited from this POM**, since it must be loaded
before the parent is resolved.  A project that wants tag-based versioning has to copy `.mvn/extensions.xml` and
`.mvn/maven-git-versioning-extension.xml` into its own root directory and declare `revision` and `changelist` in its own
POM.  Only the root of a repository needs `.mvn`; modules within it are covered automatically.

No IDE configuration is required.  The extension is inert during normal development, so IntelliJ sees plain
`${revision}${changelist}`, which it resolves natively.

# Checking for dependency updates

`versions-maven-plugin` is pinned in `pluginManagement` and invoked from the command line.  **Two** goals are needed:

```
mvn versions:display-property-updates
mvn versions:display-plugin-updates
```

`display-property-updates` covers dependencies and plugins declared in the top-level `<build>` section, but not plugins
declared inside a profile, which is most of them here; `display-plugin-updates` covers those.  `versions:update-properties`
applies what the first goal finds, and has the same blind spot, so plugin versions inside profiles must be edited by hand.

`display-dependency-updates` is not useful for this project: nearly everything it reports is managed by the imported
`spring-boot-dependencies` BOM and is controlled by `version.dep.spring-boot` rather than by us.

Pre-release versions are filtered out by the `maven.version.ignore` pattern in `.mvn/maven.config`, which is applied
automatically.

## In this repository

This project contains no `src` directory, so none of the file-activated profiles below activate on their own and the
plugins they declare are invisible to the goals above.  Force them all on:

```
mvn -P release,checkstyle-suppress,code-coverage,integration-test,java-project,spotbugs-exclude,unit-test \
    versions:display-property-updates
```

Inheriting projects do not need this: their `src` directories activate the profiles normally, so only `-P release` is
needed to cover the release-only plugins.

# Profiles

## release

* **Active by default?**: no
* **Activated by**: flag
* **Purpose**: Used with the `deploy` goal to sign artifacts and publish them to
  [Maven Central](https://central.sonatype.com) via the
  [Central Publisher Portal](https://central.sonatype.org/publish/publish-portal-maven/).

Requires a `central` server entry in `~/.m2/settings.xml` holding a Central Portal user token (generated at
https://central.sonatype.com/account) — not your account password:

```xml
<server>
  <id>central</id>
  <username><!-- token username --></username>
  <password><!-- token password --></password>
</server>
```

Run with `mvn -P release deploy`.  Snapshot versions are uploaded to
https://central.sonatype.com/repository/maven-snapshots/; release versions are staged in the Portal and published
automatically once validation passes.

## update-copyright

* **Active by default?**: no
* **Activated by**: existence of `${basedir}/LICENSE` file
* **Purpose**: Applies copyright headers to Java, XML, and properties files.

### Snippets

Add additional source roots and/or file types:

```xml
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>license-maven-plugin</artifactId>
  <configuration>
    <roots>
      <root><!-- Additional source root --></root>
    <includes>
      <include><!-- Additional file type --></include>
    </includes>
  </configuration>
</plugin>
```

# Properties

## checkstyle.suppressions.location

* **Purpose**: Used to specify the location of the [Checkstyle](https://checkstyle.sourceforge.io/) suppressions file.
* **Default value**: `checkstyle-handcraftedbits-suppressions.xml`

## license.type

* **Purpose**: Controls the source code license used by `license-maven-plugin`.
* **Default value**: `ASL2`

## version.maven.minimum

* **Purpose**: Used to specify the minimum required Maven version.
* **Default value**: `3.6.3`
