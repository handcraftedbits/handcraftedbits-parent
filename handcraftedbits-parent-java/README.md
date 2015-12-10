# HandcraftedBits Parent POM

A parent POM used by HandcraftedBits Java projects.

# Profiles

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

* **Purpose**: Sets the minimum percentage of instructions that must be covered by unit tests.
* **Default value**: `0.00`
* **Acceptable values**: `0.00` - `1.00`

## coverage.skip

* **Purpose**: Controls whether or not code coverage metrics are collected.
* **Default value**: `false`

## javadoc.excludedPackages

* **Purpose**: Specifies which packages are excluded from Javadoc generation.
* **Default value**: `*.internal.*`

## javadoc.skip

* **Purpose**: Controls whether or not Javadocs are generated.
* **Default value**: `false`

## version.java

* **Purpose**: Specifies the Java compiler level to use.
* **Default value**: `1.7`