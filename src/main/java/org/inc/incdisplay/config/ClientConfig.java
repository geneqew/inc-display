package org.inc.incdisplay.config;

import java.util.Collection;
import java.util.List;

import lombok.Data;
import org.inc.incdisplay.model.DisplayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client-config")
@Data
public class ClientConfig {

    private int timeout;
    private List<DisplayClient> displayClients;

    public Collection<String> getClientNames() {
        return getDisplayClients().stream().map(DisplayClient::getName).toList();
    }

}
