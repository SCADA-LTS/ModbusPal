package org.scadalts.modbuspal2.cli;

import lombok.Getter;

@Getter
public enum StopBitsType {

    ONE(1), ONE_HALF(2), TWO(3);

    private final int code;

    StopBitsType(int code) {
        this.code = code;
    }
}
