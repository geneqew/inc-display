package org.inc.incdisplay.config;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The broadcaster configuration properties
 */
@Configuration
@ConfigurationProperties(prefix = "broadcaster-config")
@Data
public class BroadcasterConfig {

    private String defaultDisplay;
    private boolean useKeyWhenNotPresent;
    private Map<String, String> shortCodes;

    public String getDisplayByKey(String key) {
        return Optional.ofNullable(key).map(getShortCodes()::get).orElse(key);
    }
}
