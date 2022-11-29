package com.example.client;

import com.example.client.proto.Person;
import com.example.client.proto.PersonServiceGrpc;
import com.example.client.proto.RequestBody;
import com.example.client.proto.ResponseBody;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lau wc7625716@163.com
 * Date: 2022/11/29
 * Description: No Description
 */
@RestController
public class PersonController {

    @GrpcClient("grpc-server")
    private PersonServiceGrpc.PersonServiceBlockingStub personServiceBlockingStub;

    @GetMapping("get")
    private List<Person> getPersonList(@RequestParam("nums") Integer nums) {
        ResponseBody persons = personServiceBlockingStub.getPersons(RequestBody.newBuilder().setNums(nums).build());
        return persons.getPersonList();
    }

}
