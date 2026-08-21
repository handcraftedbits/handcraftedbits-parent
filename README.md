# HandcraftedBits Parent POM

A parent POM used by HandcraftedBits Maven projects.

# Profiles

## `release`

* **Active by default?**: no
* **Activated by**: flag
* **Purpose**: Used with the `deploy` goal to verify project conventions, sign artifacts, and publish them to
  [Maven Central](https://central.sonatype.com) via the [Central Publisher Portal](https://central.sonatype.org/publish/publish-portal-maven/).

Requires a `central` server entry in `~/.m2/settings.xml` holding a Central Portal user token (generated at https://central.sonatype.com/account):

```xml
<server>
  <id>central</id>
  <username><!-- token username --></username>
  <password><!-- token password --></password>
</server>
```

Artifacts are signed using the BouncyCastle signer, which does not use a local GPG installation.  The armored private key must be provided via the
`MAVEN_GPG_KEY` environment variable, along with its passphrase in `MAVEN_GPG_PASSPHRASE`:

```shell
export MAVEN_GPG_KEY="$(gpg --armor --export-secret-keys <key ID>)"
export MAVEN_GPG_PASSPHRASE=<passphrase>
```

Run with `mvn -P release deploy`.  Snapshot versions are uploaded to https://central.sonatype.com/repository/maven-snapshots/; release versions are
staged in the Portal and published automatically once validation passes.

## `update-copyright`

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
    </roots>
    <includes>
      <include><!-- Additional file type --></include>
    </includes>
  </configuration>
</plugin>
```

# Properties

## `license.type`

* **Purpose**: Controls the source code license used by `license-maven-plugin`.
* **Default value**: `ASL2`

## `version.maven.minimum`

* **Purpose**: Used to specify the minimum required Maven version.
* **Default value**: `3.8.4`

# Miscellaneous

* Use [versions-maven-plugin](https://www.mojohaus.org/versions/versions-maven-plugin/) to check for updates:

  ```shell
  mvn versions:display-property-updates
  ```

  Use `versions:update-properties` to apply the available updates, and `versions:display-plugin-updates` to check the plugins that are not driven by
  a property (along with the minimum required Maven version).
