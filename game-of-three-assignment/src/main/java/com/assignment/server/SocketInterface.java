package com.assignment.server;

import java.util.Collection;
import java.util.stream.Stream;

public interface SocketInterface {
	
	// Send message to output.

    void send(String msg);

    void delayedMsg(String msg);

    // Broadcast the message to active socket listeners.
   
    void broadcast(String msg);

    // Start listening
   
    Stream<String> getStreamInput();

    /* Listens to the first message and blocks the thread 
     * until the message is received
     */
   
    String nextLine();
    
    // Check if there is data in the next stream

    boolean empty();

    // Consume available data in the stream
    
    void clear();

    // Link the active sockets for broadcasting
    
    void setActive(Collection<Sockets> activeSocketChannels);

}
