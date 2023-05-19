package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class HttpServer {
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    System.out.println("accept 1 client");
                    BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String inputFromClientLine1 = inputStreamReader.readLine();
                    //GET http://localhost:8080/api/student/1
                    String method = inputFromClientLine1.split(" ")[0];
                    String resourceUrl = inputFromClientLine1.split(" ")[1];
                    System.out.println("method:" + method);
                    System.out.println("resourceUrl:" + resourceUrl);
                    //response back to client

                    // load Servlet class from classpath (zzz.war)
                    // run method doGet(request,response)
                    outputStreamWriter.write("HTTP/1.1 200");
                    outputStreamWriter.newLine();
                    outputStreamWriter.newLine();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputStreamWriter.write("<html><body>Hello</body></html>");
                    outputStreamWriter.flush();
                    socket.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
