package org.example.server;

import org.example.MyServlet;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private Map<Class,Servlet> servletMap = new HashMap<>();
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    System.out.println("accept 1 client");
                    //if not already load this servlet
                    // load class from String
                    String classNameFromConfiguration = "org.example.MyServlet";
                    Class<?> aClass = Class.forName(classNameFromConfiguration);
                    if(!servletMap.containsKey(aClass)) {
                        // new MyServlet()
                        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
                        Servlet servlet = (Servlet) declaredConstructor.newInstance();
                        servletMap.put(aClass,servlet);

                        Method doGet = aClass.getDeclaredMethod("doGet", Request.class, Response.class);
                        doGet.invoke(servlet,new Request(socket.getInputStream()),new Response(socket.getOutputStream()));

                        //call method goGet() of Servlet
                        servlet.doGet(new Request(socket.getInputStream()),new Response(socket.getOutputStream()));
                    }else{
                        Servlet servlet = servletMap.get(aClass);
                        //call method goGet() of Servlet
                        servlet.doGet(new Request(socket.getInputStream()),new Response(socket.getOutputStream()));
                    }



                    socket.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
