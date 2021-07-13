# ModbusPal +1.6c
ModbusPal - a Java MODBUS simulator

fork: https://sourceforge.net/projects/modbuspal/

# 1.7.0

-noGui
Add -noGui option in command and allow to run the tool in "headless" mode without gui;

-loadFile="/relative/path/to/file/config_xmpp"
Corrected relative paths for -loadFile option, for file config, with *.xmpp.

example:
java -jar ModbusPal.jar -loadFile="config.xmpp" -noGui
This will run the application in no gui mode, with the configuration loaded from a file;

