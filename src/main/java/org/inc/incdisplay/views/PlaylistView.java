package org.inc.incdisplay.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.inc.incdisplay.model.Playlist;

@PageTitle("Playlist")
@Route(value = "playlist", layout = MainLayout.class)
public class PlaylistView extends VerticalLayout {

    private String currentDisplay;

    public PlaylistView() {

        TextField listInput = new TextField();


    }
}
