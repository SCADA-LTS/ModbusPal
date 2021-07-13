ModbusPal v1.6c - RC release

This release of ModbusPal v1.6c is a release candidate version.

Added features:
- Updated to jython 2.7.0
- Added a docker builder
- Added optional command line arguments for the executable JAR:
    -loadFile - Loads a previously saved file at launch. Provide the absolute path.
    -portNumber - An IP port number to connect this current configuration.
    -help - A command line help message to display the command line arguments.

Known limitations:
- "Tuning" features are not documented yet
- "Chart" feature is not documented yet
- Javadoc very incomplete

How to build (Using Docker)
--------------------------------------
docker build -t modbuspal-builder .
docker run --name modbuspal-builder modbuspal-builder
docker cp modbuspal-builder:/usr/src/app/dist/ModbusPal.jar .
docker cp modbuspal-builder:/usr/src/app/dist/modbuspal-help.zip .
docker cp modbuspal-builder:/usr/src/app/dist/modbuspal-javadoc.zip .

