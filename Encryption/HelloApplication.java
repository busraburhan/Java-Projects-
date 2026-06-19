package org.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HelloApplication extends Application {

    public KDC kdc = new KDC();
    private static final String LOG_FILE_PATH = "src/log.txt";


    @Override
    public void start(Stage primaryStage) throws Exception {

        Label clientLabel = new Label("Client ID:");
        TextField clientField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label serverLabel = new Label("Server ID:");
        TextField serverField = new TextField();
        Label messageLabel = new Label("Message:");
        TextField messageField = new TextField();
        TextArea logArea = new TextArea();
        logArea.setEditable(false);

        Button registerButton = new Button("Register");
        Button loginButton = new Button("LogIn");
        Button communicateButton = new Button("Communicate with Server");

        VBox root = new VBox(10,
                new HBox(10, clientLabel, clientField),
                new HBox(10, passwordLabel, passwordField),
                new HBox(10, serverLabel, serverField),
                new HBox(10, messageLabel, messageField),
                new HBox(10, registerButton, loginButton, communicateButton),
                new Label("Logs:"),
                logArea
        );
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kerberos Hybrid System");
        primaryStage.show();


        registerButton.setOnAction(e -> {

            String clientId = clientField.getText();
            String password = passwordField.getText();
            String serverId = serverField.getText();

            if (clientId.isEmpty() || password.isEmpty()) {
                logArea.appendText("Client ID or Password cannot be empty.\n");
                writeLog("Client ID or Password cannot be empty.\n");
                return;
            }
            //Client yoksa
            if(Client.allClients.containsKey(clientId) ){
                Client client = Client.allClients.get(clientId);
                if(client.getRegisteredServers.containsKey(serverId)){
                    System.out.println("client already registered to this server");
                    logArea.appendText("client already registered to this server");

                }
                else{
                    if(Server.allServers.containsKey(serverId)){
                        Server server =Server.allServers.get(serverId);
                        client.setRegisteredServers(serverId);
                        server.setRegisteredClients(clientId);
                        logArea.appendText("Client Registered Successfully");
                        writeLog("Client Registered Successfully");
                    }
                    else{
                        Server server = new Server(serverId);
                        Server.allServers.put(serverId, server);
                        logArea.appendText("Client Registered Successfully");
                        writeLog("Client Registered Successfully");
                        client.setRegisteredServers(serverId);
                        server.setRegisteredClients(clientId);


                    }
                }

            }
            else{
                Client client = new Client(clientId, password);
                kdc.registerClient(clientId);
                Client.allClients.put(clientId,client);
                if(client.getRegisteredServers.containsKey(serverId)){
                    System.out.println("client already registered to this server");
                }
                else{
                    if(Server.allServers.containsKey(serverId)){
                        Server server =Server.allServers.get(serverId);
                        client.setRegisteredServers(serverId);
                        server.setRegisteredClients(clientId);
                        logArea.appendText("Client Registered Successfully");
                        writeLog("Client Registered Successfully");
                    }
                    else{
                        Server server = new Server(serverId);
                        Server.allServers.put(serverId, server);
                        kdc.registerServer(server)
                        logArea.appendText("Client Registered Successfully");
                        writeLog("Client Registered Successfully");

                        client.setRegisteredServers(serverId);
                        server.setRegisteredClients(clientId);


                    }
                }

            }


        });


        loginButton.setOnAction(e -> {
            logArea.appendText("Authenticating client.\n");
            writeLog("Authenticating client.\n");

            String clientId = clientField.getText();
            String password = passwordField.getText();
            String serverId = serverField.getText();


            if (clientId.isEmpty() || password.isEmpty()) {
                logArea.appendText("Client ID or Password cannot be empty.\n");
                writeLog("Client ID or Password cannot be empty.\n");
                return;
            }
            // Verify credentials with KDC
            if (kdc.authenticateClient(clientId, password, serverId)) {
                logArea.appendText("Authentication successful! Ticket granted.\n");
                writeLog("Authentication successful! Ticket granted.\n");
                // Generate session key and ticket here (you can expand as needed)
            } else {
                logArea.appendText("Error: Invalid client credentials.\n");
                writeLog("Error: Invalid client credentials.\n");
            }
        });


        communicateButton.setOnAction(e -> {

            String clientId = clientField.getText();
            String password = passwordField.getText();
            String serverId = serverField.getText();
            String message = messageField.getText();

            if (!Client.allClients.containsKey(clientId)) {
                logArea.appendText("Client isn't registered.\n");
                writeLog("Client isn't registered.");

            } else {
                logArea.appendText("Server: Communication established with the client.\n");
                writeLog("Server: Communication established with the client.\n");

                if (clientId.isEmpty() || password.isEmpty()) {
                    logArea.appendText("Client ID or Password cannot be empty.\n");
                    writeLog("Client ID or Password cannot be empty.\n");
                }
                try {
                    logArea.appendText(Client.allClients.get(clientId).accessToServer(serverId, message));
                    writeLog(Client.allClients.get(clientId).accessToServer(serverId, message));

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

    }


    // Writes a log message to log.txt
    public static void writeLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
