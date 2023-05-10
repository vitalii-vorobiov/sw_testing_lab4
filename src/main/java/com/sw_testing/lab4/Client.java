package com.sw_testing.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootApplication
public class Client {
    private RestTemplate restTemplate;

    @BeforeClass
    public void Initialize() {
        restTemplate = new RestTemplate();
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
        // Creates RemoteControl and Copter. Connects them. Creates new RemoteControl and tests its connection to the copter

        String remoteControlId1 = restTemplate.getForEntity("http://localhost:8080/create-remote-control", String.class).getBody();
        String remoteControlId2 = restTemplate.getForEntity("http://localhost:8080/create-remote-control", String.class).getBody();
        String copterId = restTemplate.getForEntity("http://localhost:8080/create-copter", String.class).getBody();

        // Connection of RemoteControl1 to the copter
        String connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/connect?remote-control-id=%s&copter-id=%s", remoteControlId1, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("RemoteControl: %s successfully connected to Copter: %s", remoteControlId1, copterId)
        );

        // Connection of RemoteControl2 to the copter
        connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/connect?remote-control-id=%s&copter-id=%s", remoteControlId2, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("The Copter: %s is already connected to another RemoteControll", copterId)
        );
    }

    @Test
    public void Test3() {
        // Creates RemoteControl and Copter. Tests their connection by ID. Tests deletion of both objects.

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

        // Check Copter connection
        connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/is-copter-connected?remote-control-id=%s&copter-id=%s", remoteControlId, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("Copter: %s is connected to RemoteControl: %s", copterId, remoteControlId)
        );

        // Delete RemoteControl
        connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/delete-remote-control?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                "true"
        );

        // Check Copter connection
        connectionResponse = restTemplate.getForEntity(
                String.format("http://localhost:8080/is-copter-connected?remote-control-id=%s&copter-id=%s", remoteControlId, copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                connectionResponse,
                String.format("Copter: %s is not connected to RemoteControl: %s", copterId, remoteControlId)
        );
    }

    @Test
    public void Test4() {
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
    public void Test5() {
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
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 15, 0, 0)
        );
    }

    @Test
    public void Test6() {
        // Creates RemoteControl and Copter. Connects them and test copter movement in all 3 axis

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
        // Get initial position
        String position = restTemplate.getForEntity(
                String.format("http://localhost:8080/get-copter-position?id=%s", copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );

        // Move Forward
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-forward?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 15, 0, 0)
        );

        // Move Backward
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-backward?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );

        // Move Left
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-left?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, -15, 0)
        );

        // Move Right
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-right?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 15)
        );

        // Move Down
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-down?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );
    }

    @Test
    public void Test7() {
        // Creates RemoteControl and Copter. Connects them and test Copter UP movement beyond 100

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
        // Get initial position
        String position = restTemplate.getForEntity(
                String.format("http://localhost:8080/get-copter-position?id=%s", copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 15)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 30)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 45)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 60)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 75)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 90)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                "Your copter will be out of range after moving to new position"
        );
    }

    @Test
    public void Test8() {
        // Creates RemoteControl and Copter. Connects them and test Copter DOWN movement beyond 0

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
        // Get initial position
        String position = restTemplate.getForEntity(
                String.format("http://localhost:8080/get-copter-position?id=%s", copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );

        // Move Down
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-down?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                "Your copter will be out of range after moving to new position"
        );
    }

    @Test
    public void Test9() {
        // Creates RemoteControl and Copter. Connects them and test Copter movement over 3 axis until it gets beyond max distance

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
        // Get initial position
        String position = restTemplate.getForEntity(
                String.format("http://localhost:8080/get-copter-position?id=%s", copterId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 0, 0, 0)
        );

        // Move Forward
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-forward?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 15, 0, 0)
        );

        // Move Forward
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-forward?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, 0, 0)
        );

        // Move Left
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-left?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -15, 0)
        );

        // Move Left
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-left?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -30, 0)
        );

        // Move Left
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-left?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -45, 0)
        );

        // Move Left
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-left?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -60, 0)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -60, 15)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -60, 30)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -60, 45)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                String.format("X: %s, Y: %s, Z: %s", 30, -60, 60)
        );

        // Move Up
        position = restTemplate.getForEntity(
                String.format("http://localhost:8080/move-up?id=%s", remoteControlId),
                String.class
        ).getBody();
        Assert.assertEquals(
                position,
                "Your copter will be out of range after moving to new position"
        );
    }

    // Data provider
    @Test
    public void Test10() {
        // Creates RemoteControl and Copter at several locations. Connects them and test Copter movement over 3 axis until it gets beyond max distance
    }
}
