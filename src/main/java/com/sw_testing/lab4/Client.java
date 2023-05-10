package com.sw_testing.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootApplication
public class Client {
    private RestTemplate restTemplate;

    public Client() {
        restTemplate = new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Test
    public void Test1() {
        // Creates RemoteControl and Copter. Tests their connection by ID

        String remoteControlId = restTemplate.getForEntity("http://localhost:8080/create-remote-control", String.class).getBody();
        String copterId = restTemplate.getForEntity("http://localhost:8080/create-copter", String.class).getBody();
        String connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/connect?remote-control-id=%s&copter-id=%s", remoteControlId, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("RemoteControl: %s successfully connected to Copter: %s", remoteControlId, copterId)
        );
    }

    @Test
    public void Test2() {
        // Creates RemoteControl. Tests its connection to a defunct drone

        String copterId = "fake-copter-id";
        String remoteControlId = restTemplate.getForEntity("http://localhost:8080/create-remote-control", String.class).getBody();
        String connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/connect?remote-control-id=%s&copter-id=%s", remoteControlId, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("There is no Copter with ID: %s", copterId)
        );
    }

    @Test
    public void Test3() {
        // Creates RemoteControl and Copter. Connects them and test copter forward movement

        String remoteControlId = restTemplate.getForEntity("http://localhost:8080/create-remote-control", String.class).getBody();
        String copterId = restTemplate.getForEntity("http://localhost:8080/create-copter", String.class).getBody();
        String connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/connect?remote-control-id=%s&copter-id=%s", remoteControlId, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("RemoteControl: %s successfully connected to Copter: %s", remoteControlId, copterId)
        );
        String position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-forward?id=%s", remoteControlId),
                String.class
        ).getBody();
        System.out.println(position);
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 15, 0, 0)
        );
    }

    @Test
    public void Test4() {

    }

    @Test
    public void Test5() {

    }

    @Test
    public void Test6() {

    }

    @Test
    public void Test7() {

    }

    @Test
    public void Test8() {

    }

    @Test
    public void Test9() {

    }

    @Test
    public void Test10() {

    }
}
