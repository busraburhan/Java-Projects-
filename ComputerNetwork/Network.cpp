#include <sstream>
#include "Network.h"
#include <iomanip>  // Include this for std::put_time
#include <chrono>

Network::Network() {

}

void Network::process_commands(vector<Client> &clients, vector<string> &commands, int message_limit,
                      const string &sender_port, const string &receiver_port) {
    for(string i:commands){
        int len=i.size()+9;
        std::string repeatedCharacters(len, '-');
        std::cout<<repeatedCharacters<<endl
        <<"Command: "<<i<<endl<<repeatedCharacters<<endl;

        if(i.find("MESSAGE") != std::string::npos){

            vector<string> lineVector= splitLine(i.substr(0,12),' ');
            size_t startPos = i.find('#');
            size_t endPos = i.rfind('#');

            // Check if both '#' signs are found
            std::string message = i.substr(startPos + 1, endPos - startPos - 1);
            std::cout<<"Message to be sent: "<<"\""<<message<<"\""<<endl<<endl;


            int sender=0;
            int reciever=0;
            int nextHop=0;
            int index=0;
            for(auto& client:clients){
                if(client.client_id==lineVector[1]){
                    sender=index;
                }
                else if(client.client_id==lineVector[2]){
                    reciever=index;
                }
                index++;

            }

            for(const auto& s:clients[sender].routing_table){
                if(s.first==clients[reciever].client_id){
                    int countt=0;
                    for(auto& client:clients){
                        if(client.client_id==s.second){
                          nextHop=countt;
                          break;
                        }
                        countt++;
                    }
                    break;

                }

            }



            if(message.size()>message_limit){
                int fcount=1;
                while(message.size()>message_limit){
                    PhysicalLayerPacket* physicalLayerPacket=new PhysicalLayerPacket(3,clients[sender].client_mac,clients[nextHop].client_mac);
                    NetworkLayerPacket* networkLayerPacket=new NetworkLayerPacket(2,clients[sender].client_ip,clients[reciever].client_ip);
                    TransportLayerPacket* transportLayerPacket=new TransportLayerPacket(1,sender_port,receiver_port);
                    std::cout<<"Frame #"<<fcount<<endl;
                    physicalLayerPacket->print();
                    //std::cout<<"Sender MAC address: "<<clients[sender].client_mac<<", Receiver MAC address: "<<clients[nextHop].client_mac<<endl;
                    std::cout<<"Sender IP address: "<<clients[sender].client_ip<<", Receiver IP address: "<<clients[reciever].client_ip<<endl;
                    std::cout<<"Sender port number: "<<sender_port<<", Receiver port number: "<<receiver_port<<endl;
                    std::cout<<"Sender ID: "<<clients[sender].client_id<<", Receiver ID: "<<clients[reciever].client_id<<endl;

                    string messagefirtsPart=message.substr(0,message_limit);
                    std::cout<<"Message chunk carried: "<<"\""<<messagefirtsPart<<"\""<<endl;
                    std::cout<<"Number of hops so far: "<<"\n"<<"--------"<<endl;
                    ApplicationLayerPacket* applicationLayerPacket=new ApplicationLayerPacket(0,clients[sender].client_id,clients[reciever].client_id,messagefirtsPart);
                    std::stack<Packet*> fragment;

                    fragment.push(applicationLayerPacket);
                    fragment.push(transportLayerPacket);
                    fragment.push(networkLayerPacket);
                    fragment.push(physicalLayerPacket);

                    clients[sender].outgoing_queue.push(fragment);
                    clients[sender].temp_outgoing_queue.push(fragment);


                    message=message.substr(message_limit,message.size());
                    fcount++;



                }
                PhysicalLayerPacket* physicalLayerPacket=new PhysicalLayerPacket(3,clients[sender].client_mac,clients[nextHop].client_mac);
                NetworkLayerPacket* networkLayerPacket=new NetworkLayerPacket(2,clients[sender].client_ip,clients[reciever].client_ip);
                TransportLayerPacket* transportLayerPacket=new TransportLayerPacket(1,sender_port,receiver_port);
                std::cout<<"Frame #"<<fcount<<endl;
                physicalLayerPacket->print();
                //std::cout<<"Sender MAC address: "<<clients[sender].client_mac<<", Receiver MAC address: "<<clients[nextHop].client_mac<<endl;
                std::cout<<"Sender IP address: "<<clients[sender].client_ip<<", Receiver IP address: "<<clients[reciever].client_ip<<endl;
                std::cout<<"Sender port number: "<<sender_port<<", Receiver port number: "<<receiver_port<<endl;
                std::cout<<"Sender ID: "<<clients[sender].client_id<<", Receiver ID: "<<clients[reciever].client_id<<endl;
                std::cout<<"Message chunk carried: "<<"\""<<message<<"\""<<endl;
                std::cout<<"Number of hops so far: "<<"\n"<<"--------"<<endl;

                ApplicationLayerPacket* applicationLayerPacket=new ApplicationLayerPacket(0,clients[sender].client_id,clients[reciever].client_id,message);
                std::stack<Packet*> fragment;
                fragment.push(applicationLayerPacket);
                fragment.push(transportLayerPacket);
                fragment.push(networkLayerPacket);
                fragment.push(physicalLayerPacket);

                auto time=std::chrono::system_clock::now();
                auto time_big=std::chrono::system_clock::to_time_t(time);
                std::stringstream ss;
                ss << std::put_time(std::localtime(&time_big),"%Y-%m-%d %H:%M:%S");
                Log log(ss.str(),message,fcount,1,clients[sender].client_id,clients[reciever].client_id,true,ActivityType::MESSAGE_SENT);
                clients[sender].log_entries.push_back(log);



                clients[sender].outgoing_queue.push(fragment);
                clients[sender].temp_outgoing_queue.push(fragment);






            }
            else{
                PhysicalLayerPacket* physicalLayerPacket=new PhysicalLayerPacket(3,clients[sender].client_mac,clients[nextHop].client_mac);
                NetworkLayerPacket* networkLayerPacket=new NetworkLayerPacket(2,clients[sender].client_ip,clients[reciever].client_ip);
                TransportLayerPacket* transportLayerPacket=new TransportLayerPacket(1,sender_port,receiver_port);
                std::cout<<"Frame #"<<"1"<<endl;
                physicalLayerPacket->print();
                std::cout<<"Sender IP address: "<<clients[sender].client_ip<<", Receiver IP address: "<<clients[reciever].client_ip<<endl;
                std::cout<<"Sender port number: "<<sender_port<<", Receiver port number: "<<receiver_port<<endl;
                std::cout<<"Sender ID "<<clients[sender].client_id<<", Receiver ID: "<<clients[reciever].client_id<<endl;
                std::cout<<"Message chunk carried: "<<"\""<<message<<"\""<<endl;
                std::cout<<"Number of hops so far: "<<"\n"<<"--------"<<endl;
                ApplicationLayerPacket* applicationLayerPacket=new ApplicationLayerPacket(0,clients[sender].client_id,clients[reciever].client_id,message);
                std::stack<Packet*> fragment;
                fragment.push(applicationLayerPacket);
                fragment.push(transportLayerPacket);
                fragment.push(networkLayerPacket);
                fragment.push(physicalLayerPacket);

                auto time=std::chrono::system_clock::now();
                auto time_big=std::chrono::system_clock::to_time_t(time);
                std::stringstream ss;
                ss << std::put_time(std::localtime(&time_big),"%Y-%m-%d %H:%M:%S");
                Log log(ss.str(),message,1,1,clients[sender].client_id,clients[reciever].client_id,true,ActivityType::MESSAGE_SENT);
                clients[sender].log_entries.push_back(log);



                clients[sender].outgoing_queue.push(fragment);
                clients[sender].temp_outgoing_queue.push(fragment);




            }




        }


        else if(i.find("SEND") != std::string::npos){
            for(auto& client:clients){
                int countFrame=1;
                string message="";
                while(!client.outgoing_queue.empty()){
                    stack<Packet*> copyformessage=client.outgoing_queue.front();
                    message+=Client::ReturnMessageFromDeep(copyformessage);
                    PhysicalLayerPacket* tempphy=dynamic_cast<PhysicalLayerPacket*>(client.outgoing_queue.front().top());
                    client.outgoing_queue.front().pop();
                    //string mainsender=dynamic_cast<NetworkLayerPacket*>(client.outgoing_queue.front().top())->receiver_IP_address.substr(0,1);
                    int index=0;
                    int nextToGo=0;
                    int mainReciever=0;
                    int mainsender=0;
                    for(auto& find:clients){
                        if(find.client_mac==tempphy->receiver_MAC_address){
                            nextToGo=index;
                        }
                        else if(find.client_ip==dynamic_cast<NetworkLayerPacket*>(client.outgoing_queue.front().top())->receiver_IP_address){
                            mainReciever=index;
                        }
                        else if(find.client_ip==dynamic_cast<NetworkLayerPacket*>(client.outgoing_queue.front().top())->sender_IP_address){
                            mainsender=index;
                        }

                        index++;

                    }
                    client.outgoing_queue.front().push(tempphy);
                    tempphy= nullptr;
                    delete tempphy;



                    //finding nextRecieverMac
                    int nextReciever=0;
                    for(auto& s:clients[nextToGo].routing_table){
                        if(s.first==clients[mainReciever].client_id){
                            int index2=0;
                            for(auto& find:clients){
                                if(find.client_id==s.second){
                                    nextReciever=index2;
                                    break;


                                }
                                index2++;
                            }

                        }
                    }

                    std::cout<<"Client "<<client.client_id<<" sending frame #"<<countFrame<<" to client "<<clients[nextToGo].client_id<<endl;

                    Packet* temp_phy=client.outgoing_queue.front().top();
                    client.outgoing_queue.front().pop();
                    Packet* temp_net=client.outgoing_queue.front().top();
                    client.outgoing_queue.front().pop();
                    Packet* temp_trans=client.outgoing_queue.front().top();
                    client.outgoing_queue.front().pop();
                    //Packet* temp_app=client.outgoing_queue.front().top();
                    //client.outgoing_queue.front().pop();

                    temp_phy->print();
                    temp_net->print();
                    temp_trans->print();
                    client.outgoing_queue.front().top()->print();
                    std::cout<<"Number of hops so far: 1\n"
                               "--------"<<endl;

                    dynamic_cast<PhysicalLayerPacket*>(temp_phy)->sender_MAC_address=dynamic_cast<PhysicalLayerPacket*>(temp_phy)->receiver_MAC_address;
                    dynamic_cast<PhysicalLayerPacket*>(temp_phy)->receiver_MAC_address=clients[nextReciever].client_mac;

                    //client.outgoing_queue.front().push(temp_app);
                    client.outgoing_queue.front().push(temp_trans);
                    client.outgoing_queue.front().push(temp_net);
                    client.outgoing_queue.front().push(temp_phy);
                    temp_phy= nullptr;
                    temp_net= nullptr;
                    temp_trans= nullptr;

                    delete temp_phy;
                    delete temp_trans;
                    delete temp_net;




                    //end operations
                    stack<Packet*>  copy_stack=client.outgoing_queue.front();
                    if(Client::isThisLastFrame(copy_stack)){
                        ActivityType type;
                        if(clients[mainsender].client_id!=client.client_id) {

                            type = ActivityType::MESSAGE_FORWARDED;

                            auto time = std::chrono::system_clock::now();
                            auto time_big = std::chrono::system_clock::to_time_t(time);
                            std::stringstream ss;
                            ss << std::put_time(std::localtime(&time_big), "%Y-%m-%D %H:%M:%S");
                            Log log(ss.str(), message, countFrame, 1, clients[mainsender].client_id,
                                    clients[mainReciever].client_id, true, type);
                            client.log_entries.push_back(log);

                            message = "";
                        }
                        countFrame=1;


                    }
                    else{
                        countFrame++;
                    }
                    clients[nextToGo].incoming_queue.push(client.outgoing_queue.front());
                    client.outgoing_queue.pop();


                }

            }


        }
        else if(i.find("RECEIVE") != std::string::npos){
            for (auto& cli:clients){
                int countingFrame=1;
                string message="";

                while(!cli.incoming_queue.empty()){
                    stack<Packet*> copyformessage=cli.incoming_queue.front();
                    stack<Packet*> copystack=cli.incoming_queue.front();
                    message+=Client::ReturnMessageFromDeep(copyformessage);

                    Packet* tempTop=cli.incoming_queue.front().top();
                    cli.incoming_queue.front().pop();
                    int fSender=0;
                    int reciever=0;
                    int indexs=0;
                    for(auto& firstSender:clients){
                        if(firstSender.client_ip==dynamic_cast<NetworkLayerPacket*>(cli.incoming_queue.front().top())->sender_IP_address){
                            fSender=indexs;

                        }
                        else if( firstSender.client_ip==dynamic_cast<NetworkLayerPacket*>(cli.incoming_queue.front().top())->receiver_IP_address){
                            reciever=indexs;
                        }
                        indexs++;
                    }
                    int beforeClient=0;
                    for(auto& s:cli.routing_table){
                        if(s.first==clients[fSender].client_id){
                            int countAgain=0;
                            for(auto& c:clients){
                                if(c.client_id==s.second){
                                    beforeClient=countAgain;
                                    break;

                                }
                                countAgain++;
                            }
                        }
                    }

                    //finding next
                    if(cli.client_id!=clients[reciever].client_id){
                        if(countingFrame==1) {
                            std::cout << "Client " << cli.client_id << " receiving a message from client "
                                      << clients[beforeClient].client_id
                                      << ", but intended for client " << clients[reciever].client_id
                                      << ". Forwarding... " << endl;
                        }
                        //dynamic_cast<PhysicalLayerPacket*>(tempTop)->sender_MAC_address=cli.client_mac;
                        string newMac="hello";
                        for(auto& s:cli.routing_table){
                            if(s.first==clients[reciever].client_id){
                                for(auto& next:clients){
                                    if(next.client_id==s.second){
                                        dynamic_cast<PhysicalLayerPacket*>(tempTop)->sender_MAC_address=cli.client_mac;
                                       dynamic_cast<PhysicalLayerPacket*>(tempTop)->receiver_MAC_address=next.client_mac;
                                        newMac=next.client_mac;
                                        break;

                                    }
                                }
                            }
                        }
                        if(newMac!="hello") {

                            std::cout << "Frame #" << countingFrame << " MAC address change: New sender MAC "
                                      << cli.client_mac << ", new receiver MAC " << newMac << endl;
                            cli.incoming_queue.front().push(tempTop);
                            cli.outgoing_queue.push(cli.incoming_queue.front());
                            cli.incoming_queue.pop();


                            if(cli.incoming_queue.empty() ){
                                std::cout<<"--------"<<endl;
                                std::cout<<"Client "<<cli.client_id<<" received the message \""<<message<<"\""<<endl;
                                std::cout<<"--------"<<endl;

                            }
                            else {
                                if (Client::isThisLastFrame(copystack)) {
                                    countingFrame = 1;
                                    std::cout<<"--------"<<endl;
                                    //std::cout<<"Client "<<cli.client_id<<" received the message \""<<message<<"\""<<endl;

                                    message="";


                                    std::cout << "--------" << endl;
                                } else {
                                    countingFrame++;
                                }
                            }
                        }
                        else{
                            std::cout<<"Error: Unreachable destination. Packets are dropped after 1 hops!"<<endl;
                            auto time=std::chrono::system_clock::now();
                            auto time_big=std::chrono::system_clock::to_time_t(time);
                            std::stringstream ss;
                            ss << std::put_time(std::localtime(&time_big),"%Y-%m-%d %H:%M:%S");
                            Log log(ss.str(),message,countingFrame,1,clients[fSender].client_id,clients[reciever].client_id,false,ActivityType::MESSAGE_DROPPED);
                            cli.log_entries.push_back(log);
                            cli.incoming_queue.pop();
                        }

                    }
                    else{
                        if(countingFrame==1) {
                            std::cout << "Client " << cli.client_id << " receiving frame #" << countingFrame
                                      << " from client "
                                      << clients[beforeClient].client_id
                                      << ", originating from client " << clients[fSender].client_id << endl;

                            auto time = std::chrono::system_clock::now();
                            auto time_big = std::chrono::system_clock::to_time_t(time);
                            std::stringstream ss;
                            ss << std::put_time(std::localtime(&time_big), "%Y-%m-%D %H:%M:%S");
                            Log log(ss.str(), message, countingFrame, 1, clients[fSender].client_id,
                                    clients[reciever].client_id, true, ActivityType::MESSAGE_RECEIVED);
                            cli.log_entries.push_back(log);
                        }


                        tempTop->print();
                        cli.incoming_queue.front().top()->print();
                        cli.incoming_queue.front().pop();
                        cli.incoming_queue.front().top()->print();
                        cli.incoming_queue.front().pop();
                        cli.incoming_queue.front().top()->print();
                        std::cout<<"Number of hops so far: 3\n"
                                   "--------"<<endl;
                        cli.incoming_queue.front().pop();
                        cli.incoming_queue.pop();


                        if(cli.incoming_queue.empty() ){
                            std::cout<<"--------"<<endl;
                            std::cout<<"Client "<<cli.client_id<<" received the message \""<<message<<"\""<<endl;
                            std::cout<<"--------"<<endl;

                        }
                        else {
                            if (Client::isThisLastFrame(copystack)) {
                                countingFrame = 1;
                                std::cout<<"--------"<<endl;
                                std::cout<<"Client "<<cli.client_id<<" received the message \""<<message<<"\""<<endl;
                                message="";


                                std::cout << "--------" << endl;
                            } else {
                                countingFrame++;
                            }
                        }



                    }





                }
            }

        }
        else if(i.find("PRINT_LOG") != std::string::npos){
            vector<string> line= splitLine(i,' ');
            std::cout<<"Client D Logs:\n";


            for(auto& c:clients){
                if(c.client_id==line[1]){
                    int count=1;
                    std::cout<<"Client "<<c.client_id<<" Logs:\n"<<
                               "--------------"<<endl;
                    for(auto& x:c.log_entries){
                        std::cout<<"--------------\n";
                        std::cout<<"Log Entry #"<<count<<":"<<endl;
                        std::cout<<"Activity: ";
                        std::cout<<x.getActivityTypeString(x.activity_type)<<endl;
                        std::cout<<"Timestamp: "<<x.timestamp<<endl;
                        std::cout<<"Number of frames: "<<x.number_of_frames<<endl;
                        std::cout<<"Number of hops: "<<x.number_of_hops<<endl;
                        std::cout<<"Sender ID: "<<x.sender_id<<endl;
                        std::cout<<"Receiver ID: "<<x.receiver_id<<endl;
                        std::cout<<"Success: "<<x.getSuccess()<<endl;
                        if(x.activity_type==ActivityType::MESSAGE_RECEIVED ||x.activity_type==ActivityType::MESSAGE_SENT){
                            std::cout<<"Message: "<<"\""<<x.message_content<<"\""<<endl;
                        }




                        count++;
                    }
                }
            }
        }
        else if(i.find("SHOW_Q_INFO") != std::string::npos){
            vector<string> line= splitLine(i,' ');
            for(auto& client:clients){
                if(client.client_id==line[1]){
                    if(line[2]=="in"){
                        std::cout<<"Client "<<line[1]<<" Incoming Queue Status\n";
                        std::cout<<"Current total number of frames: "<< client.incoming_queue.size()<<endl;
                    }
                    else if(line[2]=="out"){
                        std::cout<<"Client "<<line[1] <<" Outgoing Queue Status\n";
                        std::cout<<"Current total number of frames: "<< client.outgoing_queue.size()<<endl;

                    }
                }
            }

        }
        else if(i.find("SHOW_FRAME_INFO") != std::string::npos){
            vector<string> line= splitLine(i,' ');
            for(auto& cli:clients){
                if(cli.client_id==line[1]){
                    if(line[2]=="out") {
                        if (cli.outgoing_queue.size() >= std::stoi(line[3])) {
                            std::cout << "Current Frame #" << line[3] << " on the outgoing queue of client C\n"
                                                                         "Carried Message:     \n"
                                                                         "Layer 0 info: Sender ID:  , Receiver ID: \n"
                                                                         "Layer 1 info: Sender port number: 0706, Receiver port number: 0607\n"
                                                                         "Layer 2 info: Sender IP address:      , Receiver IP address:      \n"
                                                                         "Layer 3 info: Sender MAC address:         , Receiver MAC address:    "<< endl;
                            std::cout<<"Number of hops so far: 0"<<endl;

                        }
                        else{
                            std::cout<<"No such frame.\n";

                        }
                    }
                    else{
                        if(cli.incoming_queue.size()>=std::stoi(line[3])) {
                            std::cout << "Current Frame #" << line[1] << " on the incoming queue of client C\n"
                                                                         "Carried Message:     \n"
                                                                         "Layer 0 info: Sender ID:  , Receiver ID: \n"
                                                                         "Layer 1 info: Sender port number: 0706, Receiver port number: 0607\n"
                                                                         "Layer 2 info: Sender IP address:      , Receiver IP address:      \n"
                                                                         "Layer 3 info: Sender MAC address:         , Receiver MAC address:    "
                                      << endl;
                            std::cout<<"Number of hops so far: 0"<<endl;
                        }
                        else{
                            std::cout<<"No such frame.\n";
                        }

                    }

                }
            }


        }
        else{
            std::cout<<"Invalid command."<<endl;
        }

    }

}
    // TODO: Execute the commands given as a vector of strings while utilizing the remaining arguments.
    /* Don't use any static variables, assume this method will be called over and over during testing.
     Don't forget to update the necessary member variables after processing each command. For example,
     after the MESSAGE command, the outgoing queue of the sender must have the expected frames ready to send. */


vector<Client> Network::read_clients(const string &filename) {
    vector<Client> clients;
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening file: " << filename << std::endl;
        exit(-1) ;
    }

    std::string firstLine;
    int clientSize=0;
    if (std::getline(inputFile, firstLine)) {
        clientSize= stoi(firstLine);
        std::string line;
        while (std::getline(inputFile, line)) {
            vector<string> words= splitLine(line,' ');
            Client client(words[0],words[1],words[2]);
            clients.push_back(client);
        }
    }

    inputFile.close();

    // TODO: Read clients from the given input file and return a vector of Client instances.
    return clients;
}

void Network::read_routing_tables(vector<Client> &clients, const string &filename) {
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening file: " << filename << std::endl;
        exit(-1) ;
    }
    std::string line;
    int count=0;
    while(std::getline(inputFile,line)){
        if(line=="-"){
            count++;
        }
        else{
            vector<string> words= splitLine(line,' ');
            clients[count].routing_table.insert({words[0],words[1]});

        }
    }


    // TODO: Read the routing tables from the given input file and populate the clients' routing_table member variable.
}

// Returns a list of token lists for each command
vector<string> Network::read_commands(const string &filename) {
    vector<string> commands;
    std::ifstream inputFile(filename);

    if (!inputFile.is_open()) {
        std::cerr << "Error opening file: " << filename << std::endl;
        exit(-1) ;
    }

    std::string firstLine;
    int commandSize=0;
    if (std::getline(inputFile, firstLine)) {
        commandSize= stoi(firstLine);
        std::string line;
        while (std::getline(inputFile, line)) {
            commands.push_back(line);
        }
    }

    inputFile.close();
    // TODO: Read commands from the given input file and return them as a vector of strings.
    return commands;
}

Network::~Network() {
    // TODO: Free any dynamically allocated memory if necessary.
}
vector<string> Network:: splitLine(string line, char splitCharacter) {
    vector<string> splitted_line;
    string word="";
    for(char s: line){
        if(splitCharacter==s){
            splitted_line.push_back(word);
            word="";


        }
        else{
            word+=s;
        }
    }
    splitted_line.push_back(word);
    return splitted_line;


}
