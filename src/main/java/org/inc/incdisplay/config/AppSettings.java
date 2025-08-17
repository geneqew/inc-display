package org.inc.incdisplay.config;

import lombok.Data;
import org.inc.incdisplay.model.AppTheme;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app-settings")
@Data
public class AppSettings {

    private AppTheme defaultTheme;

}
