package models;

import models.databases.UserDatabase;
import models.databases.exceptions.DatabaseLoadException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final int serverPort;
    private final ArrayList<ServerThread> workerList = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            try {
                UserDatabase.getInstance().load();
            } catch (DatabaseLoadException e){
                e.printStackTrace();
            }
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread worker = new ServerThread(this, clientSocket);
                addWorker(worker);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWorker(ServerThread serverThread) {
        workerList.add(serverThread);
    }

    public void removeWorker(ServerThread serverThread) {
        workerList.remove(serverThread);
    }

    public ArrayList<ServerThread> getWorkerList() {
        return workerList;
    }
}
