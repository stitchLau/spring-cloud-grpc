package com.example.server.grpc;

import com.example.server.proto.GreeterReply;
import com.example.server.proto.GreeterServiceGrpc;
import com.example.server.proto.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Lau wc7625716@163.com
 * Date: 2022/11/29
 * Description: No Description
 */

@GrpcService
public class GreeterServiceImpl extends GreeterServiceGrpc.GreeterServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<GreeterReply> responseObserver) {
        String name = request.getName();
        GreeterReply reply = GreeterReply.newBuilder().setMsg("hello " + name).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
