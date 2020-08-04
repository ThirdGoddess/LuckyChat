package com.example.socketto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SockettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SockettoApplication.class, args);

        //启动服务
        Server server = new Server();
        server.startServer();
        
    }

}
