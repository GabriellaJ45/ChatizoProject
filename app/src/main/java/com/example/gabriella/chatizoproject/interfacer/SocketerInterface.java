package com.example.gabriella.chatizoproject.interfacer;

/**
 * Created by Joyce Amore on 4/30/2017.
 */

public interface SocketerInterface {
    public String sendHttpRequest(String params);
    public int startListening(int port);
    public void stopListening();
    public void exit();
    public int getListeningPort();
}
