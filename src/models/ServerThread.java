package models;

import models.databases.UserDatabase;
import models.databases.exceptions.DatabaseSaveException;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.net.Socket;

import static models.User.checkSyntax;

public class ServerThread extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String username = null;
    private OutputStream outputStream;

    public ServerThread(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleSocket();
        } catch (IOException | InterruptedException ignored) {
        }
    }

    private void handleSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(line);
            if (tokens != null && tokens.length > 0) {
                String command = tokens[0];
                if ("disconnect".equalsIgnoreCase(command)) {
                    disconnect();
                } else if ("login".equalsIgnoreCase(command)) {
                    login(tokens);
                } else if ("register".equalsIgnoreCase(command)) {
                    register(tokens);
                } else if ("message".equalsIgnoreCase(command)) {
                    String[] split_tokens = StringUtils.split(line, null, 3);
                    message(split_tokens);
                }
            }
        }
    }

    private void disconnect() {
        server.removeWorker(this);
        UserDatabase.getInstance().logOut(UserDatabase.getInstance().getUser(username));
        try {
            UserDatabase.getInstance().save();
        } catch (DatabaseSaveException e) {
            e.printStackTrace();
        }
        for (ServerThread worker : server.getWorkerList()) {
            if (!username.equals(worker.getUsername())) {
                worker.send("offline " + username + "\n");
            }
        }
        username = null;
    }

    private void login(String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String username = tokens[1];
            String password = tokens[2];

            if (UserDatabase.getInstance().logIn(username, password)) {
                try {
                    UserDatabase.getInstance().save();
                } catch (DatabaseSaveException e) {
                    e.printStackTrace();
                }
                this.username = username;
                for (ServerThread worker : server.getWorkerList()) {
                    if (worker.getUsername() != null) {
                        if (!username.equals(worker.getUsername())) {
                            send("online " + worker.getUsername() + "\n");
                        }
                    }
                    if (!username.equals(worker.getUsername())) {
                        worker.send("online " + this.username + "\n");
                    }
                }
                outputStream.write("ok login\n".getBytes());
                return;
            }
        }
        outputStream.write("error login\n".getBytes());
    }

    private void register(String[] tokens) {
        try {
            String firstname = tokens[1];
            String lastname = tokens[2];
            String username = tokens[3];
            String email = tokens[4];
            String password = tokens[5];
            if (checkSyntax(firstname, lastname, username, email, password)) {
                User user = new User(firstname, lastname, username, email, password);
                if (!UserDatabase.getInstance().checkExistingUser(user)) {
                    UserDatabase.getInstance().add(user);
                    try {
                        UserDatabase.getInstance().save();
                    } catch (DatabaseSaveException e) {
                        e.printStackTrace();
                    }
                    outputStream.write("ok register\n".getBytes());
                    return;
                }
            }
            outputStream.write("error register\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void message(String[] tokens) {
        String username = tokens[1];
        String content = tokens[2];
        for (ServerThread worker : server.getWorkerList()) {
            if (username.equalsIgnoreCase(worker.getUsername())) {
                worker.send("message " + this.username + " " + content + "\n");
            }
        }
    }

    private void send(String message) {
        if (username != null) {
            try {
                outputStream.write(message.getBytes());
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getUsername() {
        return username;
    }
}
