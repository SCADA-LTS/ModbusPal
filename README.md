# ModbusPal2
ModbusPal - a Java MODBUS simulator

fork: https://sourceforge.net/projects/modbuspal/

### Run with GUI:
```
java -jar ModbusPal2.jar
```

### Run without GUI (headless mode) with port 503:
```
java -jar ModbusPal2-2.0.0.jar -ng -tp 503
```

### Help:
```
Usage: <main class> [-hv] [-mm] [-ng] [-sc] [-sx] [-f=<projectFile>]
                    [-rf=<recordFile>] [-sb=<serialStopBits>]       
                    [-si=<serialPortId>] [-sp=<serialParity>]       
                    [-sr=<serialBaudRate>] [-t=<transportType>]     
                    [-tp=<tcpPortNumber>]                           
  -f, -loadFile, --project-file=<projectFile>

  -h, --help
  -mm, --master-mode
  -ng, -noGui, --no-gui

  -rf, --record-file=<recordFile>

  -sb, --serial-stop-bits=<serialStopBits>

  -sc, --serial-rts-cts

  -si, --serial-port-id=<serialPortId>

  -sp, --serial-parity=<serialParity>

  -sr, --serial-baud-rate=<serialBaudRate>

  -sx, --serial-xon-xoff

  -t, --transport-type=<transportType>

  -tp, --tcp-port-number=<tcpPortNumber>

  -v, --version
```

# ModbusPal +1.6c

ModbusPal - a Java MODBUS simulator

fork: https://sourceforge.net/projects/modbuspal/

# 1.7.0

-noGui
Add -noGui option in command and allow to run the tool in "headless" mode without gui;

-loadFile="/relative/path/to/file/config_xmpp"
Corrected relative paths for -loadFile option, for file config, with *.xmpp.

example for no gui:

java -jar ModbusPal.jar -loadFile="modbuspal_config.xmpp" -noGui

example:

java -jar ModbusPal.jar -loadFile="modbuspal_config.xmpp"

This will run the application in no gui mode, with the configuration loaded from a file;

