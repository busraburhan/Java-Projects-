package org.example.demo1;


import java.util.HashMap;

public class Client {
    private String clientId;
    private String password;
    private HashMap<String, Ticket> registeredServers = new HashMap<>();
    public static HashMap<String, Client> allClients = new HashMap<>();

    public Client(String clientId, String password) {
        this.clientId = clientId;
        this.password = password;

    }
    public String accessToServer(String ServerId,String message) throws Exception {
        Server server = Server.allServers.get(ServerId);
        System.out.println("---------------");
        System.out.println(this.registeredServers);
        System.out.println("---------------");
        if (this.registeredServers.get(ServerId) != null) {
            if (this.registeredServers.get(ServerId).isValid()) {
                String decryptedSessionKey = KDC.decryptRSA(this.registeredServers.get(ServerId).getEncrypted(), this);

                return server.requestFromClient(this, decryptedSessionKey, message);
            } else {
                KDC.newTicket(this, server);
                return this.accessToServer(ServerId, message);
            }
        } else {
            return ("Client isn't registered with this server.\n");
        }
    }

    public String getClientId() {
        return clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setTicket(String serverId, Ticket ticket) {
        this.registeredServers.put(serverId, ticket); // Use 'put' to add or update a key-value pair
    }

    public boolean isRegistered(String ServerId) {
         return this.registeredServers.containsKey(ServerId);
    }

    public void setRegisteredServers (String serverId) {
        this.registeredServers.put(serverId, null);
    }

    public HashMap<String, Ticket> getRegisteredServers() {
        return registeredServers;
    }
}
