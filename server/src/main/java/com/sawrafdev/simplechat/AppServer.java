package com.sawrafdev.simplechat;

import com.sawrafdev.simplechat.server.Server;

public class AppServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        final Server server = new Server(PORT);
        server.listen();
    }
}
