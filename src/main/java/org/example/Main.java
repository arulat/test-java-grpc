package org.example;

import org.example.server.ProductServer;
import org.example.server.UserServer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(UserServer.class.getName());

    public static void main(String[] args) {
        UserServer userServer = new UserServer();
        userServer.startServer();
        ProductServer productServer = new ProductServer();
        productServer.startServer();

        try {
            userServer.blockUntilShutdown();
            productServer.blockUntilShutdown();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error while blockUntilShutdown()", e);
        }

    }
}