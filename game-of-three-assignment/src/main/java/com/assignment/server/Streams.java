package com.assignment.server;

import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Streams implements Closeable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Streams.class);
	
    private PrintWriter output;
    private BufferedReader input;
    private ServerSocket serverSocket;
    private Socket socket;
    
    // Create a new server stream
    
    public Streams(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Create a channel to communicate on main server 
 
    public Sockets start() {
        LOGGER.debug("Starting ServerStream.");
        try {
            socket = serverSocket.accept();
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            LOGGER.debug("Starting StreamServer");
        } catch (IOException e) {
            LOGGER.error("Server could not run.", e);
            close();
        }
        return new Sockets(input, output);
    }
    
    // Close stream server
   
    public void close() {
        LOGGER.info("Stoping stream server.");
        try {
            input.close();
            output.close();
            socket.close();
            LOGGER.debug("Server stopped.");
        } catch (IOException ex) {
            throw new RuntimeException("IO exception while stopping the server", ex);
        }
    }

}
