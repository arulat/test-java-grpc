package org.example.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.service.ProductServiceImpl;
import org.example.service.UserServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductServer {

    private Server server;

    private final Logger logger = Logger.getLogger(ProductServer.class.getName());

    public void startServer(){
        //create a grpc server
        try {
            logger.log(Level.INFO, "Product server starting on port 50052");
            server = ServerBuilder
                    .forPort(50052)
                    .addService(new ProductServiceImpl())
                    .build()
                    .start();
            logger.log(Level.INFO, "Product server started on port 50052");
            // add shutdown hook for stop server
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.log(Level.INFO, "Shutting down server");
                try {
                    ProductServer.this.stopServer();
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Error while shutting down server", e);
                }
            }));

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while starting server", e);
        }
    }

    public void stopServer() throws InterruptedException {
        if(server != null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if(server != null){
            server.awaitTermination();
        }
    }
}
