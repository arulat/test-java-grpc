package org.example.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.client.ProductClient;
import org.example.proto.user.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {


    public static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public void getUserDetails(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        String productName = this.getProduct(ProductRequest.newBuilder().setUsername(request.getUsername()).build());

        UserResponse.Builder builder = UserResponse.newBuilder();
        builder.setId(1);
        builder.setUsername("test");
        builder.setName(productName);
        builder.setAge(1);
        builder.setGender(Gender.MALE);

        UserResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private String getProduct(ProductRequest request) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50052").usePlaintext().build();
        ProductClient client = new ProductClient(channel);
        String response = client.getProduct(request);

        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error while shutting down channel", e);
        }

        return response;
    }
}
