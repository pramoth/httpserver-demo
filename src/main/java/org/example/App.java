package org.example;

import org.example.server.HttpServer;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.start();
    }

}
