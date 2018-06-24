package com.blackwell;

import com.blackwell.entity.GameObject;
import com.blackwell.entity.Player;
import com.blackwell.entity.GameObjectList;

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
    private final GameObjectList players = new GameObjectList();

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
        for(GameObject o : players)
            tcpConnection.sendGameObject(o);
        //sendToAllClient("Client connected: " + tcpConnection);

        System.out.println(tcpConnection + " connected");
    }

    @Override
    public void onGameObjectReceive(TCPConnection tcpConnection, GameObject gameObject) {
        if (gameObject instanceof  Player) {
            System.out.println("Receive player: " + gameObject);
            players.add(gameObject);

            for (TCPConnection tcp : connections)
                tcp.sendGameObject(gameObject);
        }
    }


    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove( tcpConnection);
        System.out.println(tcpConnection +" disconnected");
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception ex) {
        System.out.printf("TCPConnection exception: %s;%n", ex);
    }

}
