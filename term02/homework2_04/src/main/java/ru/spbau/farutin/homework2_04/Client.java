package ru.spbau.farutin.homework2_04;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Simple client for FTP server.
 */
public class Client {
    private static Client instance = null;
    private Socket socket = null;
    private DataInputStream serverIn = null;
    private DataOutputStream serverOut= null;

    /**
     * Getter for client instance.
     * @return client instance which can be null
     */
    public static @Nullable Client getInstance() {
        return instance;
    }

    /**
     * Constructs client which will request to the server specified by given parameters
     * @param hostAddress server address
     * @param portNumber server port number
     * @throws IOException if failed to connect to host
     */
    public static void initialize(@NotNull String hostAddress, int portNumber) throws IOException {
        instance = new Client(hostAddress, portNumber);
    }

    /**
     * Closes connection.
     * @throws IOException if failed to close
     */
    public void disconnect() throws IOException {
        serverOut.writeUTF("0");
        serverOut.flush();
        socket.close();
    }

    /**
     * Makes list query.
     * @param path path to the directory to list files in
     * @return directory content
     * @throws IOException if failed to interact with server
     */
    public @NotNull String list(@NotNull String path) throws IOException {
        serverOut.writeUTF("1 " + path);
        serverOut.flush();
        return serverIn.readUTF();
    }

    /**
     * Makes get query.
     * @param path path to the file to get
     * @return file bytes
     * @throws IOException if failed to interact with server
     */
    public @NotNull String get(@NotNull String path) throws IOException {
        serverOut.writeUTF("2 " + path);
        serverOut.flush();
        return serverIn.readUTF();
    }

    private Client(@NotNull String hostAddress, int portNumber) throws IOException {
        InetAddress ipAddress = InetAddress.getByName(hostAddress);
        socket = new Socket(ipAddress, portNumber);
        serverIn = new DataInputStream(socket.getInputStream());
        serverOut = new DataOutputStream(socket.getOutputStream());

        serverIn.readUTF();
    }
}
