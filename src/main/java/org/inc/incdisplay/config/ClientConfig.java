package org.inc.incdisplay.config;

import java.util.Map;

import lombok.Data;
import org.inc.incdisplay.model.DisplayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client-config")
@Data
public class ClientConfig {

    private int timeout;
    private Map<String, DisplayClient> clients;

}
