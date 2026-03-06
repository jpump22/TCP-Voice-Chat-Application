
package project2;

import java.io.*;
import java.io.BufferedReader;
import java.net.*;
import javax.sound.sampled.*;

public class project2 {
    public static void main(String[] args) {
        try {
            // Ask user whether to run as server or client
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Run as server or client? (Enter 'server' or 'client')");
            String role = reader.readLine();

            if ("server".equalsIgnoreCase(role)) {
                runServer();
            } else if ("client".equalsIgnoreCase(role)) {
                runClient();
            } else {
                System.out.println("Invalid input. Please enter 'server' or 'client'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected!");

        startAudioExchange(clientSocket, true);
    }

    private static void runClient() throws Exception {
        System.out.println("Enter the server's IP address:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String serverIp = reader.readLine();

        Socket socket = new Socket(serverIp, 5000);
        System.out.println("Connected to the server!");

        startAudioExchange(socket, false);
    }

    private static void startAudioExchange(Socket socket, boolean isServer) {
        try {
            // Open audio capture and playback lines
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
            TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
            SourceDataLine speakers = AudioSystem.getSourceDataLine(format);

            microphone.open(format);
            speakers.open(format);

            microphone.start();
            speakers.start();

            // Input and output streams for sending and receiving audio
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // Threads for audio sending and receiving
            Thread sendThread = new Thread(() -> sendAudio(microphone, out));
            Thread receiveThread = new Thread(() -> receiveAudio(speakers, in));

            sendThread.start();
            receiveThread.start();

            sendThread.join();
            receiveThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendAudio(TargetDataLine microphone, OutputStream out) {
        try {
            byte[] buffer = new byte[1024];
            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveAudio(SourceDataLine speakers, InputStream in) {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                speakers.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}