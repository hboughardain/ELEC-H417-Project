package models;

import controllers.MessengerController;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {

    private static Client instance;
    private final String serverName;
    private final int serverPort;
    private OutputStream serverOutputStream;
    private BufferedReader serverBufferedReader;

    private UserStatusListener userStatusListener;
    private MessageListener messageListener;

    public static Client getInstance(){
        if (instance == null){
            instance = new Client("192.168.1.23", 2023);
        }
        return instance;
    }

    private Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public void start(MessengerController messengerController) {
        this.userStatusListener = messengerController;
        this.messageListener = messengerController;
        instance.connect();
    }

    public void connect() {
        try {
            Socket socket = new Socket(serverName, serverPort);
            this.serverOutputStream = socket.getOutputStream();
            this.serverBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) throws IOException {
        serverOutputStream.write(("login " + username + " " + hash(password) + "\n").getBytes());
        String response = serverBufferedReader.readLine();
        if ("ok login".equalsIgnoreCase(response)) {
            startReadLoop();
            return true;
        }
        return false;
    }

    private void startReadLoop() {
        Thread t = new Thread(this::readLoop);
        t.start();
    }

    private void readLoop() {
        try {
            while (true) {
                String line;
                while ((line = serverBufferedReader.readLine()) != null) {
                    String[] tokens = StringUtils.split(line);
                    if (tokens != null && tokens.length > 0) {
                        String command = tokens[0];
                        if ("online".equalsIgnoreCase(command)) {
                            online(tokens);
                        } else if ("offline".equalsIgnoreCase(command)) {
                            offline(tokens);
                        } else if ("message".equalsIgnoreCase(command)) {
                            String[] tokensMsg = StringUtils.split(line, null, 3);
                            onMessage(tokensMsg);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean register(String firstname, String lastname, String username, String email, String password) {
        try {
            String command = "register " + firstname + " " + lastname + " " + username + " " + email + " " + hash(password) + "\n";
            serverOutputStream.write(command.getBytes());
            String response = serverBufferedReader.readLine();
            if ("ok register".equalsIgnoreCase(response)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void message(String username, String content) throws IOException {
        serverOutputStream.write(("message " + username + " " + content + "\n").getBytes());
    }

    public void disconnect() throws IOException {
        serverOutputStream.write("disconnect\n".getBytes());
        this.connect();
    }

    private void online(String[] tokens) {
        String username = tokens[1];
        userStatusListener.online(username);
    }

    private void offline(String[] tokens) {
        String username = tokens[1];
        userStatusListener.offline(username);
    }

    private void onMessage(String[] tokens) {
        String username = tokens[1];
        String content = tokens[2];
        messageListener.onMessage(username, content);
    }

    private String hash(String input) {
        // inspired from this website: https://www.baeldung.com/sha-256-hashing-java
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(input.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(String.format("%02x", b & 0xff));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface UserStatusListener {
        void online(String username);
        void offline(String username);
    }

    public interface MessageListener {
        void onMessage(String username, String content);
    }
}
