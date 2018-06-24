package com.blackwell;

import com.blackwell.entity.GameObject;
import com.blackwell.entity.Player;

public interface TCPConnectionListener {
    int PORT = 8129;

    void onConnectionReady(TCPConnection tcpConnection); // Your connection is ready
    void onGameObjectReceive(TCPConnection tcpConnection, GameObject gameObject);
    void onDisconnect(TCPConnection tcpConnection); //disconnect
    void onException(TCPConnection tcpConnection, Exception ex); //something went wrong

}
