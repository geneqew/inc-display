package org.inc.incdisplay.model;

import lombok.Getter;

@Getter
public enum Command {
    TURN_OFF_DISPLAY("export DISPLAY=:0;xset dpms force off"),
    TURN_ON_DISPLAY("export DISPLAY=:0;xset dpms force on");

    private final String value;

    Command(String value) {
        this.value = value;
    }

}
