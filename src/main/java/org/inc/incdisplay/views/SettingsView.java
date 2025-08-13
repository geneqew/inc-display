package org.inc.incdisplay.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox.AutoExpandMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.inc.incdisplay.config.ClientConfig;
import org.inc.incdisplay.model.AppTheme;
import org.inc.incdisplay.model.DisplayClient;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@Slf4j
public class SettingsView extends VerticalLayout {

    ClientConfig clientConfig;

    public SettingsView(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        setAlignItems(FlexComponent.Alignment.START); // horizontal alignment of children
        setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        add(monitorSettings());
        add(divider());
        add(themeSettings());
    }

    protected Div divider() {
        Div div = new Div();
        div.setWidthFull();
        div.setHeight("2px");
        div.getStyle().set("background-color", "var(--lumo-contrast-20pct)");
        return div;
    }

    protected Component themeSettings() {
        VerticalLayout themeSettingsLayout = new VerticalLayout();
        themeSettingsLayout.setPadding(true);
        themeSettingsLayout.setSpacing(true);
        themeSettingsLayout.setWidthFull();

        H4 h4 = new H4("Theme");
        h4.setWidthFull();
        themeSettingsLayout.add(h4);

        RadioButtonGroup<AppTheme> themeGroup = new RadioButtonGroup<>();
        themeGroup.setItems(AppTheme.values());
        themeGroup.setRenderer(new ComponentRenderer<Component, AppTheme>(appTheme -> {
            HorizontalLayout row = new HorizontalLayout();
            row.setSizeFull();
            row.setPadding(true);
            row.add(appTheme.getIcon().create(), new Span(appTheme.getName()));
            return row;
        }));
        themeGroup.addValueChangeListener(event -> {
            AppTheme value = event.getValue();
            UI.getCurrent().getElement().removeAttribute("theme");
            UI.getCurrent().getElement().setAttribute("theme", value.name().toLowerCase());

        });
        themeSettingsLayout.add(themeGroup);

        return themeSettingsLayout;
    }

    protected Component monitorSettings() {
        VerticalLayout monitorSettingsLayout = new VerticalLayout();
        monitorSettingsLayout.setPadding(true);
        monitorSettingsLayout.setSpacing(true);
        monitorSettingsLayout.setWidthFull();

        H4 h4 = new H4("Display");
        h4.setWidthFull();
        monitorSettingsLayout.add(h4);

        MultiSelectComboBox<DisplayClient> displayListBox = new MultiSelectComboBox<>();
        displayListBox.setItems(clientConfig.getDisplayClients());
        displayListBox.setRequired(true);
        displayListBox.setItemLabelGenerator(DisplayClient::getName);
        displayListBox.setAutoExpand(AutoExpandMode.BOTH);
        displayListBox.setWidthFull();
        displayListBox.setRenderer(new ComponentRenderer<Component, DisplayClient>(display -> {
            HorizontalLayout row = new HorizontalLayout();
            row.setAlignItems(FlexComponent.Alignment.CENTER);
            row.add(display.getIcon().create());
            row.add(display.getName());
            return row;
        }));
        monitorSettingsLayout.add(displayListBox);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();

        Button onButton = new Button("ON");
        onButton.setIcon(VaadinIcon.CHECK.create());
        onButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        onButton.addClickListener(event -> {
        });

        Button offButton = new Button("OFF");
        offButton.setIcon(VaadinIcon.POWER_OFF.create());
        offButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_WARNING);

        horizontalLayout.addAndExpand(onButton, offButton);
        monitorSettingsLayout.add(horizontalLayout);

        return monitorSettingsLayout;
    }
}
