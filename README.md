# HandcraftedBits Parent POM

A parent POM used by HandcraftedBits Maven projects.

# Profiles

## release

* **Active by default?**: no
* **Activated by**: flag
* **Purpose**: Used with `deploy` goal to sign artifacts and push to [Nexus](https://oss.sonatype.org).

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
