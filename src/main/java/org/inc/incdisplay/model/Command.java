package org.inc.incdisplay.model;

import lombok.Getter;

@Getter
public enum Command {
    TURN_OFF_DISPLAY("Turn OFF","export DISPLAY=:0;xset dpms force off"),
    TURN_ON_DISPLAY("Turn ON", "export DISPLAY=:0;xset dpms force on");

    private String name;
    private String value;

    Command(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
