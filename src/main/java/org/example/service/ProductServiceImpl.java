package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.proto.user.*;

public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {
    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductResponse.Builder builder = ProductResponse.newBuilder();
        builder.setName("testxxx");

        ProductResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
