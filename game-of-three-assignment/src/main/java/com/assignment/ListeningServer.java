package com.assignment;

import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.app.appcontroller.entities.UserInputDetails;
import com.assignment.app.appcontroller.instructions.InstructionController;
import com.assignment.app.maingame.gamedata.Service;
import com.assignment.server.ActiveSockets;
import com.assignment.server.SocketInterface;
import com.assignment.server.Sockets;
import com.assignment.server.Streams;

public class ListeningServer implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListeningServer.class);

    private ServerSocket socket;
    private ThreadLocal<ActiveSockets> activeSockets;
    private ThreadLocal<Service> service;

    // Start listening the socket
 
    public ListeningServer(ServerSocket socket,
                          ThreadLocal<ActiveSockets> activeSockets,
                          ThreadLocal<Service> service) {
        this.socket = socket;
        this.activeSockets = activeSockets;
        this.service = service;
    }

    // Run app logic
 
    @Override
    public void run() {
        try (Streams streams = new Streams(socket)) {
            Sockets sockets = streams.start();
            activeSockets.get().register(Thread.currentThread().getName(), sockets);
            sockets.activeSocketChannels(activeSockets.get().getActiveSockets());

            //setup application controller
            InstructionController instructionController = new InstructionController(service.get(), (SocketInterface) sockets, new UserInputDetails());

            //listen input stream
            sockets.send("connected");
            sockets.getStreamInput()
                    .peek(instructionController)
                    .filter(msg -> msg.equals("EXIT"))
                    .findAny();

            LOGGER.debug("ListeningServer is closing");
        } catch (Exception ex) {
            LOGGER.error("ListeningServer {} closed with exception.", ex);
        }
    }

}
