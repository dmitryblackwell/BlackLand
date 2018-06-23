package com.blackwell;

import com.blackwell.entity.Player;

public interface TCPConnectionListener {
    int PORT = 8129;

    void onConnectionReady(TCPConnection tcpConnection); // Your connection is ready
    void onReceivePlayer(TCPConnection tcpConnection, Player player); // your connection is receiving string
    void onDisconnect(TCPConnection tcpConnection); //disconnect
    void onException(TCPConnection tcpConnection, Exception ex); //something went wrong

}
