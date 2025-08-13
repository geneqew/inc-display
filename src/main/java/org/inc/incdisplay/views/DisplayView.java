package org.inc.incdisplay.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.inc.incdisplay.config.BroadcasterConfig;
import org.inc.incdisplay.model.Broadcaster;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Display")
@Route(value = "display")
public class DisplayView extends VerticalLayout {

    @Autowired
    private BroadcasterConfig broadcasterConfig;
    private final Span content;
    private Registration broadcasterRegistration;

    private static final String DEFAULT_FONT = "calc(10px + 17vw)";
    private static final String NUMERIC_FONT = "calc(10px + 50vw)";

    public DisplayView() {
        content = new Span();
        add(content);

        setSizeFull();
        getStyle().set("overflow", "hidden");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        content.getStyle().set("font-size", DEFAULT_FONT);
        getStyle().set("background-color", "#000000")
                .set("color", "#FFFFFF");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(newMessage -> {
            ui.access(() -> {
                String contentToDisplay = broadcasterConfig.getDisplayByKey(newMessage);
                content.setText(contentToDisplay);
                setContentStyle(contentToDisplay);
            });
        });
    }

    private void setContentStyle(String newMessage) {
        if (newMessage.length() <= 3) {
            content.getElement().getStyle().set("font-size", NUMERIC_FONT);
        } else {
            content.getElement().getStyle().set("font-size", DEFAULT_FONT);
        }
    }


    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

}
