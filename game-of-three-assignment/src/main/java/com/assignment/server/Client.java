package com.assignment.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    
    // Start the client on a port

    public Sockets start(String serverIp, int serverPort) {
        LOGGER.info("Connecting to server ip: {}, server port: {}.", serverIp, serverPort);
        try {
            socket = new Socket(serverIp, serverPort);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            LOGGER.error("Could not start the client.");
            stop();
        }

        return new Sockets(input, output);
    }

    // Stop the current server
   
    public void stop() {
        LOGGER.info("Stopping the server");
        try {
            input.close();
            output.close();
            socket.close();
            LOGGER.debug("Stopped the server");
        } catch (IOException ex) {
            throw new RuntimeException("Could not close the connection", ex);
        }
    }

}
