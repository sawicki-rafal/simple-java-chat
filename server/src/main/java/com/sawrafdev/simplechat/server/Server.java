package com.sawrafdev.simplechat.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private final ClientRegistry clientRegistry = ClientRegistry.getInstance();

    public Server(int port) {
        this.port = port;
    }

    public void listen() {
        initialize();
        runLoop();
    }

    public void initialize() {
        try {
            serverSocket = new ServerSocket(port);
            log.info("Server initialized");
        } catch (IOException e) {
            log.error("Error during server initialization {}", e.getMessage());
        }
    }

    private void runLoop() {
        while (true) {
            try {
                log.info("Waiting for a new client ...");
                Socket clientSocket = serverSocket.accept();
                log.info("New client accepted");
                registerNewClient(clientSocket);
                startClientListener(clientSocket);

            } catch (IOException e) {
                log.error("Error when accepting new client {}", e.getMessage());
            }
        }
    }

    private void startClientListener(Socket clientSocket) {
        Thread t = new Thread(new ClientHandler(clientSocket, this::broadcast));
        t.start();
    }

    private void registerNewClient(Socket clientSocket) throws IOException {
        PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream());
        clientRegistry.add(clientWriter);
    }

    private void broadcast(String message) {
        clientRegistry.getAllClients().forEach(client -> {
            client.println(message);
            client.flush();
        });
    }

}
