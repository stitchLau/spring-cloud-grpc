package com.example.server.grpc;

import com.example.server.Nuwa;
import com.lau.grpc.PersonServiceGrpc;
import com.lau.grpc.RequestBody;
import com.lau.grpc.ResponseBody;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Lau wc7625716@163.com
 * Date: 2022/11/29
 * Description: No Description
 */

@GrpcService
public class PersonServiceImpl extends PersonServiceGrpc.PersonServiceImplBase {

    @Override
    public void getPersons(RequestBody request, StreamObserver<ResponseBody> responseObserver) {
        int nums = request.getNums();
        ResponseBody.Builder respBuilder = ResponseBody.newBuilder();
        if (nums >= 0) {
            for (int i = 0; i < nums; i++) {
                respBuilder.addPerson(Nuwa.getInstance().newPerson());
            }
        }
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }
}
