package com.blackwell;

import com.blackwell.entity.Player;

import java.io.*;
import java.net.Socket;

public class TCPConnection{
    private static final String CHARSET = "UTF-8";

    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddress, int port) throws IOException {
        this(eventListener, new Socket(ipAddress, port));
    }



    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        rxThread = new Thread(() -> {
            try {
                eventListener.onConnectionReady(TCPConnection.this);
                while (!Thread.currentThread().isInterrupted()){
                    eventListener.onReceivePlayer(TCPConnection.this, (Player) in.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                eventListener.onException(TCPConnection.this, e);
            }finally {
                eventListener.onDisconnect(TCPConnection.this);
            }
        });

        rxThread.start();
    }

    public synchronized void sendPlayer(Player p){
        try {
            //System.out.println("Connection player in: " + p);
            out.reset();
            out.writeObject(p);
            out.flush();
            //System.out.println("Connection player out: " + p);
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }


    private void disconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return String.format("TCPConnection: %s: %s;", socket.getInetAddress(), socket.getPort());
    }
}
