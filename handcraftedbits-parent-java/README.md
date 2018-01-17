# HandcraftedBits Parent POM

A parent POM used by HandcraftedBits Java projects.

# Profiles

## code-coverage

* **Active by default**: yes
* **Deactivated by**: flag
* **Purpose**: Combines unit and integration test coverage results and creates a combined code coverage report.  Also
  fails the build if the minimum code coverage amount is not met.

## integration-test

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/it/java` directory
* **Purpose**: Adds TestNG dependency and configures integration test code coverage.

## java-project

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/main/java` directory
* **Purpose**: Sets Java compiler level, configures Checkstyle, Findbugs, Javadoc, and source plugins.

## unit-test

* **Active by default**: no
* **Activated by**: existence of `${basedir}/src/test/java` directory
* **Purpose**: Adds TestNG dependency and configures unit test code coverage.

# Properties

## coverage.minimum

* **Purpose**: Sets the minimum percentage of instructions that must be covered by unit and/or integration tests.
* **Default value**: `0.00`
* **Acceptable values**: `0.00` - `1.00`

## coverage.skip

* **Purpose**: Controls whether or not code minimum code coverage is enforced.
* **Default value**: `false`

## javadoc.excludedPackages

* **Purpose**: Specifies which packages are excluded from Javadoc generation.
* **Default value**: `*.internal.*`

## javadoc.skip

* **Purpose**: Controls whether or not Javadocs are generated.
* **Default value**: `false`

## version.java

* **Purpose**: Specifies the Java compiler level to use.
* **Default value**: `1.8`
