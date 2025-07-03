package org.scadalts.modbuspal2.cli;

import lombok.Getter;

@Getter
public enum ParityType {

    NONE(0), ODD(1), EVEN(2);

    ///, MARK(3), SPACE(4);

    private final int code;

    ParityType(int code) {
        this.code = code;
    }
}
