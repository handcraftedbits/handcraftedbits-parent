# HandcraftedBits Parent POM (Java)

A parent POM used by HandcraftedBits Java projects.

# Profiles

## `checkstyle-suppress`

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/main/codequality/checkstyle-suppressions.xml` file
* **Purpose**: Uses that file as the Checkstyle suppressions file.

## `code-coverage`

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/main/java` directory
* **Purpose**: Combines unit and integration test coverage results and creates a combined code coverage report.  Also fails the build if the minimum
  code coverage amount (instructions and/or branches) is not met.

## `integration-test`

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/it` directory
* **Purpose**: Adds `src/it/java` and `src/it/resources` as test sources, adds test dependencies (JUnit Jupiter, JUnit Pioneer, AssertJ, Mockito),
  organizes imports, and runs integration tests with `maven-failsafe-plugin`.

## `java-project`

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/main/java` directory
* **Purpose**: Sets and enforces the Java compiler level, adds optional `provided` dependencies and annotation processors (Lombok, MapStruct,
  Immutables, SpotBugs annotations), and configures source formatting, import organization, [Checkstyle](https://checkstyle.sourceforge.io/), and
  [SpotBugs](https://spotbugs.github.io/).

## `release`

* **Active by default**: no
* **Activated by**: flag
* **Purpose**: Attaches source and Javadoc JARs.

## `spotbugs-exclude`

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/main/codequality/spotbugs-exclude.xml` file
* **Purpose**: Uses that file as the SpotBugs exclusion filter.

## `unit-test`

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/test/java` directory
* **Purpose**: Adds test dependencies (JUnit Jupiter, JUnit Pioneer, AssertJ, Mockito), organizes imports, and runs unit tests with
  `maven-surefire-plugin`.

# Properties

## `checkstyle.suppressions.location`

* **Purpose**: Used to specify the location of the Checkstyle suppressions file.
* **Default value**: `checkstyle-handcraftedbits-suppressions.xml`

## `coverage.branch.minimum`

* **Purpose**: Sets the minimum percentage of branches that must be covered by unit and/or integration tests.
* **Default value**: `0.00`
* **Acceptable values**: `0.00` - `1.00`

## `coverage.check.excludePattern`

* **Purpose**: Specifies an additional class pattern to exclude from code coverage enforcement.  Test and integration
  test classes are always excluded.
* **Default value**:

## `coverage.minimum`

* **Purpose**: Sets the minimum percentage of instructions that must be covered by unit and/or integration tests.
* **Default value**: `0.00`
* **Acceptable values**: `0.00` - `1.00`

## `coverage.report.excludePattern`

* **Purpose**: Specifies an additional class pattern to exclude from the code coverage report.
* **Default value**:

## `javadoc.excludedPackages`

* **Purpose**: Specifies which packages are excluded from Javadoc generation.
* **Default value**: `*.internal.*`

## `javadoc.skip`

* **Purpose**: Controls whether or not Javadocs are generated.
* **Default value**: `false`

## `test.args.extra`

* **Purpose**: Allows for extra arguments to be passed to `maven-surefire-plugin` and `maven-failsafe-plugin`.
* **Default value**:

## `version.java`

* **Purpose**: Specifies the Java compiler level to use, and the minimum required JDK version.
* **Default value**: `25`

# Miscellaneous

* Add custom Javadoc links to
  [maven-javadoc-plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/) with:

  ```xml
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <configuration>
          <links combine.children="append">
            <link>...</link>
            <link>...</link>
          </links>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ```
