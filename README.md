# Beacon CLI [![Build Status](https://travis-ci.org/mcupak/beacon-cli.svg?branch=develop)](https://travis-ci.org/mcupak/beacon-cli) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/mcupak/beacon-cli/develop/LICENSE)
Java CLI client for accessing Beacon.

## Building
- Prerequisites: Java 8, Maven 3.
- Dependencies:
  - [Beacon Schemas](https://github.com/ga4gh/beacon-team)
  - [Beacon Client](https://github.com/mcupak/beacon-client)
- Building with Maven - in the root of the project, execute `mvn package`.

## Usage
### Beacon
To load information on a beacon, type in your console:
```
java -jar path/to/cli/cli.jar -u http://yourbeacon.com/ info
```

Each command has help message. For the beacon command, it will look similar to the following:
```
java -jar path/to/cli/cli.jar info --help

Description: Gets beacon information.
```