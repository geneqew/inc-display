package org.inc.incdisplay;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Push(transport = Transport.WEBSOCKET_XHR)
public class IncDisplayApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(IncDisplayApplication.class, args);
    }

}
