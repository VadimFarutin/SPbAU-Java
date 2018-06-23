package ru.spbau.farutin.test2_03.network;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple FTP server.
 */
public class Server implements Runnable {
    private static Server instance;
    private ServerSocket serverSocket = null;
    private boolean isStopped = true;
    private int portNumber;

    public static void start(int portNumber) {
        instance = new Server(portNumber);
        Thread thread = new Thread(instance);
        thread.start();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String input = stdIn.readLine();
                if (input != null && input.equals("quit")) {
                    break;
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Constructs server which will listen to given port.
     * @param portNumber port to listen to
     */
    private Server(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Opens server socket and accepts clients, each in separate thread.
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(portNumber);
            isStopped = false;

            while (!isStopped) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket.getInputStream(),
                                                                    clientSocket.getOutputStream());
                    Thread thread = new Thread(clientHandler);
                    thread.setDaemon(true);
                    thread.start();
                } catch (IOException e) {
                    if (!isStopped) {
                        System.err.println("Exception caught when working with client socket.");
                        System.err.println(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Exception caught when trying to open server socket");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Closes server socket.
     */
    public static void stop() {
        instance.isStopped = true;

        try {
            instance.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Exception caught when trying to close server socket.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Class for interacting with client.
     */
    public static class ClientHandler implements Runnable {
        private InputStream clientIn = null;
        private OutputStream clientOut = null;

        /**
         * Constructs handler for client with given streams.
         * @param clientIn client input stream
         * @param clientOut client output stream
         */
        public ClientHandler(@NotNull InputStream clientIn,
                             @NotNull OutputStream clientOut) {
            this.clientIn = clientIn;
            this.clientOut = clientOut;
        }

        /**
         * Reads from and writes to client streams
         * until client is finished or socket is closed.
         */
        @Override
        public void run() {
            try (
                    DataInputStream in = new DataInputStream(clientIn);
                    DataOutputStream out = new DataOutputStream(clientOut)
            ) {
                Protocol protocol = new Protocol();
                String outputLine = protocol.processInput(null);
                out.writeUTF(outputLine);
                out.flush();

                while (true) {
                    String inputLine = in.readUTF();
                    outputLine = protocol.processInput(inputLine);
                    out.writeUTF(outputLine);
                    out.flush();

                    if (outputLine.equals("quit")) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Exception caught when trying to interact with client");
                System.err.println(e.getMessage());
            }
        }
    }
}
