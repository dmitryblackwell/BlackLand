package com.blackwell;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server implements TCPConnectionListener {

    public static void main(String[] args) {
        // Getting your IP.
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("Server IP address : " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        new Server();
    }

    private final List<TCPConnection> connections = new ArrayList<>();
    private List<String> players = new ArrayList<>();
    private Server(){
        System.out.println("Server running...");
        try( ServerSocket serverSocket = new ServerSocket(PORT) ){

            while (true){
                try{
                    new TCPConnection(this, serverSocket.accept());
                }catch (IOException e) {
                    System.out.printf("TCPConnection exception: %s;", e);
                }
            }

        }catch (IOException e){
            System.out.println("Server stopped");
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        for(String p : players)
            tcpConnection.sendString(p);
        //sendToAllClient("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        players.add(value);
        sendToAllClient(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove( tcpConnection);
        sendToAllClient(">>> Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception ex) {
        System.out.printf("TCPConnection exception: %s;%n", ex);
    }

    private void sendToAllClient(String value){
        System.out.printf("sendToAllClient: %s;%n", value);

//        Pattern p = Pattern.compile("@\\w+\\b");
//        Matcher m = p.matcher(value);
//        boolean wasSentByName = false;
//        while (m.find()) {
//            for (TCPConnection connection : connections) {
//                System.out.println(m.group() + "_" + connection.getName());
//                if (m.group().equals("@" + connection.getName())) {
//                    connection.sendString(value);
//                    wasSentByName = true;
//                }
//            }
//        }
//        if (!wasSentByName)


        for(TCPConnection tcp : connections)
            tcp.sendString(value);

    }
}
