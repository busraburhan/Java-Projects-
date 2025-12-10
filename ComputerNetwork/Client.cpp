//
// Created by alperen on 27.09.2023.
//

#include "Client.h"

Client::Client(string const& _id, string const& _ip, string const& _mac) {
    client_id = _id;
    client_ip = _ip;
    client_mac = _mac;
}

ostream &operator<<(ostream &os, const Client &client) {
    os << "client_id: " << client.client_id << " client_ip: " << client.client_ip << " client_mac: "
       << client.client_mac << endl;
    return os;
}

Client::~Client() {
    while (!temp_incoming_queue.empty()) {
        std::stack<Packet*> packetStack = temp_incoming_queue.front();
        temp_incoming_queue.pop();

        while (!packetStack.empty()) {
            Packet* packet = packetStack.top();
            delete packet;
            packetStack.pop();
        }
    }
    while (!temp_outgoing_queue.empty()) {
        std::stack<Packet*> packetStack = temp_outgoing_queue.front();
        temp_outgoing_queue.pop();

        while (!packetStack.empty()) {
            Packet* packet = packetStack.top();
            delete packet;
            packetStack.pop();
        }
    }



    // TODO: Free any dynamically allocated memory if necessary.
}

bool Client::isThisLastFrame(stack<Packet *> frame) {
    frame.pop();
    frame.pop();
    frame.pop();
    auto* app=dynamic_cast<ApplicationLayerPacket*>(frame.top());
    unsigned long size=app->message_data.size();
    if(app->message_data[size-1]=='.'||app->message_data[size-1]=='!'||app->message_data[size-1]=='?'){
        return true;
    }
    return false;
}

string Client::ReturnMessageFromDeep(stack<Packet *> frame) {
    frame.pop();
    frame.pop();
    frame.pop();

    return dynamic_cast<ApplicationLayerPacket*>(frame.top())->message_data;
}
