package org.example.client;

import io.grpc.Channel;
import org.example.proto.user.ProductRequest;
import org.example.proto.user.ProductServiceGrpc;

public class ProductClient {

    private ProductServiceGrpc.ProductServiceBlockingStub blockingStub;

    public ProductClient(Channel channel) {
        blockingStub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public String getProduct(ProductRequest request) {
        return blockingStub.getProduct(request).getName();
    }
}
