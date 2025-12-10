#ifndef NETWORK_H
#define NETWORK_H

#include <vector>
#include <iostream>
#include "Packet.h"
#include "Client.h"
#include <fstream>

using namespace std;

class Network {
public:
    Network();
    vector<string> splitLine(string line, char splitCharacter);
    ~Network();

    // Executes commands given as a vector of strings while utilizing the remaining arguments.
    void process_commands(vector<Client> &clients, vector<string> &commands, int message_limit, const string &sender_port,
                     const string &receiver_port);

    // Initialize the network from the input files.
    vector<Client> read_clients(string const &filename);
    void read_routing_tables(vector<Client> & clients, string const &filename);
    vector<string> read_commands(const string &filename); 
};

#endif  // NETWORK_H
