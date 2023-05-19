package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("192.168.31.217",8080));
        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        outputStreamWriter.write("Hello");
        outputStreamWriter.newLine();
        outputStreamWriter.flush();
        String s = inputStreamReader.readLine();
        System.out.println(s);
        socket.close();
    }
}
