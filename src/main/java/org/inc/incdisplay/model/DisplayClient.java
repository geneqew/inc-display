package org.inc.incdisplay.model;

import java.io.ByteArrayOutputStream;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;

import static org.inc.incdisplay.model.Command.TURN_OFF_DISPLAY;
import static org.inc.incdisplay.model.Command.TURN_ON_DISPLAY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DisplayClient {

    private String name;
    private VaadinIcon icon;
    private String ip;
    private String host;
    @Builder.Default
    private int port = 22;
    @Builder.Default
    private String username = "pi";
    @Builder.Default
    private String password = "pi";


    public void turnOffDisplay() {
        execute(this, TURN_OFF_DISPLAY.getValue());
    }

    public void turnOnDisplay() {
        execute(this, TURN_ON_DISPLAY.getValue());
    }

    protected String execute(DisplayClient displayClient, String command) {
        String result = null;

        // Create and start the SSH client
        try (SshClient sshClient = SshClient.setUpDefaultClient()) {
            sshClient.start();

            // Connect to the remote server
            ConnectFuture connectFuture = sshClient.connect(displayClient.getUsername(), displayClient.getIp(), displayClient.getPort());
            connectFuture.await();
            try (ClientSession session = connectFuture.getSession(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                session.addPasswordIdentity(displayClient.getPassword());
                session.auth().verify();

                // Execute the command
                session.executeRemoteCommand(command);
                result = outputStream.toString();
            }
        } catch (Exception e) {
            log.error("failed to execute command", e);
        }

        return result;
    }

}
