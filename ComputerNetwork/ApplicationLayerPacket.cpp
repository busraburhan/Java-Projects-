#include "ApplicationLayerPacket.h"

ApplicationLayerPacket::ApplicationLayerPacket(int _layer_ID, const string &_sender_ID, const string &_receiver_ID, const string& _message_data) : Packet(_layer_ID) {
    sender_ID = _sender_ID;
    receiver_ID = _receiver_ID;
    message_data = _message_data;
}

void ApplicationLayerPacket::print() {
    std::cout<<"Sender ID: "<< this->sender_ID<<", Receiver ID: "<< this->receiver_ID<<endl;
    std::cout<< "Message chunk carried: "<<"\""<<this->message_data<<"\""<<endl;

    // TODO: Override the virtual print function from Packet class to additionally print layer-specific properties.
}

ApplicationLayerPacket::~ApplicationLayerPacket() {
    // TODO: Free any dynamically allocated memory if necessary.
}
