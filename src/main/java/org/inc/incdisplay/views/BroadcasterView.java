package org.inc.incdisplay.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.inc.incdisplay.model.Broadcaster;

@Route("broadcaster")
public class BroadcasterView extends Div {

    private VerticalLayout messages = new VerticalLayout();
    private Registration broadcasterRegistration;

    public BroadcasterView() {
        TextField messageField = new TextField();

        Button send = new Button("Send", e -> {
            Broadcaster.broadcast(messageField.getValue());
            messageField.setValue("");
        });
        messageField.addKeyPressListener(Key.ENTER, event -> send.click());

        HorizontalLayout sendBar = new HorizontalLayout(messageField, send);

        add(sendBar, messages);
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
