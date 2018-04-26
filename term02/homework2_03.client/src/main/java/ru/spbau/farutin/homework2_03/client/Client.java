package ru.spbau.farutin.homework2_03.client;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple client for FTP server.
 */
public class Client {
    private String hostAddress;
    private int portNumber;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: gradlew run -Parg1=<host address>" +
                                                  "-Parg2=<port number>");
            return;
        }

        String hostAddress = args[0];
        int portNumber = Integer.parseInt(args[1]);

        Client client = new Client(hostAddress, portNumber);
        client.run(System.in, System.out);
    }

    /**
     * Constructs client which will request to the server specified by given parameters
     * @param hostAddress server address
     * @param portNumber server port number
     */
    public Client(@NotNull String hostAddress, int portNumber) {
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
    }

    /**
     * Opens socket and starts interacting with server.
     * @param inputStream user input stream
     * @param outputStream user output stream
     */
    public void run(@NotNull InputStream inputStream, @NotNull OutputStream outputStream) {
        try {
            InetAddress ipAddress = InetAddress.getByName(hostAddress);
            try (Socket socket = new Socket(ipAddress, portNumber)) {
                serverInteract(inputStream,
                               outputStream,
                               socket.getInputStream(),
                               socket.getOutputStream());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostAddress);
        }
    }

    /**
     * Redirects user requests to the server and server responses back to the user.
     * @param userIn user input stream
     * @param userOut user output stream
     * @param serverIn server input stream
     * @param serverOut server output stream
     * @throws IOException if any error while reading or writing
     */
    public void serverInteract(@NotNull InputStream userIn,
                               @NotNull OutputStream userOut,
                               @NotNull InputStream serverIn,
                               @NotNull OutputStream serverOut) throws IOException {
        try (
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(userIn));
                PrintStream stdOut = new PrintStream(userOut);
                DataInputStream in = new DataInputStream(serverIn);
                DataOutputStream out = new DataOutputStream(serverOut)
        ) {
            while (true) {
                String fromServer = in.readUTF();
                stdOut.println("Server: " + fromServer);

                if (fromServer.equals("quit")) {
                    return;
                }

                String fromUser = stdIn.readLine();

                out.writeUTF(fromUser);
                out.flush();
            }
        }
    }
}
