package ru.spbau.farutin.test2_03.network;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple client for FTP server.
 */
public class Client {
    private static Client instance;

    private String hostAddress;
    private int portNumber;

    public static void start(@NotNull String hostAddress, int portNumber) {
        instance = new Client(hostAddress, portNumber);
        //instance.run();
    }

    /**
     * Constructs client which will request to the server specified by given parameters
     * @param hostAddress server address
     * @param portNumber server port number
     */
    private Client(@NotNull String hostAddress, int portNumber) {
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
            String fromUser = "";

            while (true) {
                String fromServer = in.readUTF();

                if (fromUser.length() > 0 && fromUser.charAt(0) == '2'
                        && !fromServer.equals("failed reading file")) {
                    String result = save(fromUser.substring(2), fromServer);
                    stdOut.println(result);
                } else {
                    stdOut.println("Server: " + fromServer);
                }

                if (fromServer.equals("quit")) {
                    return;
                }

                fromUser = stdIn.readLine();

                out.writeUTF(fromUser);
                out.flush();
            }
        }
    }

    /**
     * Saves file.
     * @param path path where to save
     * @param fileData content in the following format:
     * <size: Long>\n<content: Bytes>\n
     * @return verdict
     */
    public @NotNull String save(@NotNull String path, @NotNull String fileData) {
        String result;

        try {
            String bytes = fileData.split("\n")[1];
            byte[] data = DatatypeConverter.parseHexBinary(bytes);

            File file = new File(path);
            File parent = file.getParentFile();
            if (!parent.getName().equals(".") && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("failed to create directories");
            }

            try (FileOutputStream output = new FileOutputStream(path)) {
                output.write(data);
            }

            result = "file saved";
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }
}
