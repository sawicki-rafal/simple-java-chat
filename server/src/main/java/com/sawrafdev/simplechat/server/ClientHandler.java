package com.sawrafdev.simplechat.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

@Slf4j
public class ClientHandler implements Runnable {
    BufferedReader reader;
    Socket socket;
    Consumer<String> broadcast;


    public ClientHandler(Socket clientSocket, Consumer<String> broadcast) {
        try {
            socket = clientSocket;
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isReader);
            this.broadcast = broadcast;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        log.info("ClientHandler started {}", Thread.currentThread().getName());
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                System.out.println("read::: " + message);
                broadcast.accept(message);
            }
        } catch (IOException e) {
            log.error("Problem while reading messages {}", e.getMessage());
        }
        log.info("ClientHandler stopped {}", Thread.currentThread().getName());
    }
}
