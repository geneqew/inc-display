package org.inc.incdisplay.model;

import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;

public enum AppTheme {
    DARK("Dark", VaadinIcon.MOON),
    LIGHT("Light", VaadinIcon.SUN_O);

    @Getter
    private String name;
    @Getter
    private VaadinIcon icon;

    AppTheme(String name, VaadinIcon icon) {
        this.name = name;
        this.icon = icon;
    }
}
