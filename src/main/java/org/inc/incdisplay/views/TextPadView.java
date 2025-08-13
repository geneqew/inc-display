package org.inc.incdisplay.views;

import java.util.Optional;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.lang3.StringUtils;
import org.inc.incdisplay.model.Broadcaster;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_ERROR;
import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;


@PageTitle("Text Display")
@Route(value = "text-pad", layout = MainLayout.class)
public class TextPadView extends VerticalLayout {

    private VerticalLayout messages = new VerticalLayout();
    private Registration broadcasterRegistration;

    public TextPadView() {
        setSizeFull();
        setPadding(true);

        createMainContent();
    }

    private void createMainContent() {
        VerticalLayout mainContent = new VerticalLayout();

        TextArea textArea = new TextArea();
        textArea.setWidthFull();
        textArea.setLabel("Content to display");
        textArea.getStyle()
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("font-size", "clamp(12px, 10vw, 40px)");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidthFull();
        buttons.setPadding(true);

        Button clearButton = new Button("CLEAR");
        clearButton.setHeight("60px");
        clearButton.addThemeVariants(LUMO_PRIMARY, LUMO_ERROR);
        clearButton.addClickListener(e -> {
            textArea.setValue("");
        });
        buttons.addAndExpand(clearButton);

        Button showButton = new Button("SHOW");
        showButton.setHeight("60px");
        showButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        showButton.addClickListener(e -> {
            Broadcaster.broadcast(textArea.getValue());

            String content = Optional.ofNullable(textArea.getValue())
                    .filter(StringUtils::isNotEmpty)
                    .map(s -> String.format("'%s' \n sent to display(s)", s))
                    .orElse("Screen Cleared");

            Notification notification = Notification.show(content);
            notification.setDuration(2000);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        });
        buttons.addAndExpand(showButton);

        textArea.addKeyPressListener(Key.ENTER, event -> showButton.click());

        mainContent.add(textArea, buttons);
        add(mainContent);
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(newMessage -> {
            ui.access(() -> messages.add(new Span(newMessage)));
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }
}
