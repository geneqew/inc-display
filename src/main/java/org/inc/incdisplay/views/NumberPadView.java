package org.inc.incdisplay.views;

import java.util.Optional;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.inc.incdisplay.model.Broadcaster;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_ERROR;
import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;

@PageTitle("Number Display")
@Route(value = "number-pad", layout = MainLayout.class)
public class NumberPadView extends VerticalLayout {

    private final Span numberDisplay;

    public NumberPadView() {
        setSizeFull();
        setPadding(true);

        numberDisplay = new Span("");
        numberDisplay.setWidthFull();
        numberDisplay.setHeight("80px");
        numberDisplay.getStyle()
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("font-size", "clamp(16px, 17vw, 80px)");


        HorizontalLayout displayPanel = new HorizontalLayout();
        displayPanel.setWidthFull();
        displayPanel.addAndExpand(numberDisplay);
        add(displayPanel);

        HorizontalLayout sendLayout = new HorizontalLayout();
        sendLayout.setWidthFull();
        sendLayout.addAndExpand(createShowButton());
        add(sendLayout);

        HorizontalLayout horizon1 = new HorizontalLayout();
        horizon1.setWidthFull();
        horizon1.addAndExpand(createNumpadButton("7"), createNumpadButton("8"), createNumpadButton("9"));
        add(horizon1);

        HorizontalLayout horizon2 = new HorizontalLayout();
        horizon2.setWidthFull();
        horizon2.addAndExpand(createNumpadButton("4"), createNumpadButton("5"), createNumpadButton("6"));
        add(horizon2);

        HorizontalLayout horizon3 = new HorizontalLayout();
        horizon3.setWidthFull();
        horizon3.addAndExpand(createNumpadButton("1"), createNumpadButton("2"), createNumpadButton("3"));
        add(horizon3);

        HorizontalLayout horizon4 = new HorizontalLayout();
        horizon4.setWidthFull();
        horizon4.addAndExpand(createNumpadButton("0"));

        Button clearButton = createClearButton();
        horizon4.addAndExpand(clearButton);
        horizon4.setFlexGrow(2, clearButton);
        add(horizon4);


        HorizontalLayout horizon5 = new HorizontalLayout();
        horizon5.setWidthFull();
        horizon5.addAndExpand(numpadInfoButton());
        add(horizon5);
    }

    private Button numpadInfoButton() {
        Button infoButton = new Button(new Icon(VaadinIcon.INFO));
        infoButton.addThemeVariants(ButtonVariant.LUMO_ICON);

        Popover popover = new Popover();
        popover.setTarget(infoButton);
        popover.setModal(true, true);

        Paragraph content = new Paragraph("""
            Tap the number you wish to display, then tap SHOW to display it on all available monitors.\n
            Tap CLEAR to make a correction.\n
            Tapping SHOW with a blank content sets the display to empty\n""");
        content.getStyle().set("white-space", "pre-wrap");
        popover.add(content);

        return infoButton;
    }

    protected Button createNumpadButton(String label) {
        Button button = new Button(label);
        button.setHeight("60px");
        button.addClickListener(e -> handleButtonClick(label));
        return button;
    }

    protected Button createShowButton() {
        Button sendButton = new Button("SHOW");
        sendButton.setHeight("60px");
        sendButton.addThemeVariants(LUMO_PRIMARY);
        sendButton.addClickListener(e -> handleShowButton());

        return sendButton;
    }

    protected Button createClearButton() {
        Button clearButton = new Button("CLEAR");
        clearButton.setHeight("60px");
        clearButton.addThemeVariants(LUMO_PRIMARY, LUMO_ERROR);
        clearButton.addClickListener(e -> handleClearButton());
        return clearButton;
    }

    private void handleClearButton() {
        numberDisplay.setText("");
    }

    private void handleShowButton() {
        Broadcaster.broadcast(numberDisplay.getText());

        String content = Optional.ofNullable(numberDisplay.getText())
                .filter(StringUtils::isNotEmpty)
                .map(s -> String.format("%s sent to display(s)", s))
                .orElse("Screen Cleared");

        Notification notification = Notification.show(content);
        notification.setDuration(2000);
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void handleButtonClick(String label) {
        String newContent = numberDisplay.getText() + label;
        numberDisplay.setText(newContent);
    }
}
