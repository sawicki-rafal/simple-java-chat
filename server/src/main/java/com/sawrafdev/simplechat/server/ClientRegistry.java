package com.sawrafdev.simplechat.server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ClientRegistry {

    private final List<PrintWriter> clients = new ArrayList<>();


    public void add(PrintWriter newClient) {
        clients.add(newClient);
    }

    public Collection<PrintWriter> getAllClients() {
        return Collections.unmodifiableCollection(clients);
    }

    private static final class InstanceHolder {
        private static final ClientRegistry instance = new ClientRegistry();
    }

    public static ClientRegistry getInstance() {
        return InstanceHolder.instance;
    }
}
