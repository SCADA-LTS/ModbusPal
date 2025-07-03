package org.scadalts.modbuspal2.cli;

import lombok.Getter;

@Getter
public enum BaudRateType {
    //_110(110), _300(300), _1200(1200), _2400(2400), _4800(4800),
    _9600(9600), _19200(19200), _38400(38400), _57600(57600), _115200(115200);
    //, _230400(230400), _460800(460800), _921600(921600);

    private final int rate;

    BaudRateType(int rate) {
        this.rate = rate;
    }
}
