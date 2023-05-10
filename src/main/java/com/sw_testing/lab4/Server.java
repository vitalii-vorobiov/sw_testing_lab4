package com.sw_testing.lab4;

import com.sw_testing.lab4.copter.Copter;
import com.sw_testing.lab4.copter.CoptersController;
import com.sw_testing.lab4.exceptions.*;
import com.sw_testing.lab4.remote_control.RemoteControl;
import com.sw_testing.lab4.remote_control.RemoteControlCommands;
import com.sw_testing.lab4.remote_control.RemoteControlsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.NoSuchObjectException;

@SpringBootApplication
@RestController
public class Server {
	private final RemoteControlsController remoteControlsController;
	private final CoptersController coptersController;

	public Server() {
		remoteControlsController = new RemoteControlsController();
		coptersController = new CoptersController();
	}

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@GetMapping("/create-remote-control")
	public String CreateRemoteControl(@RequestParam(value = "x", defaultValue = "0") String x, @RequestParam(value = "y", defaultValue = "0") String y, @RequestParam(value = "z", defaultValue = "0") String z) {
		try {
			var xInt = Integer.parseInt(x);
			var yInt = Integer.parseInt(y);
			var zInt = Integer.parseInt(z);

			return remoteControlsController.CreateRemoteControl(xInt, yInt, zInt);
		} catch (NumberFormatException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/create-copter")
	public String CreateCopter(@RequestParam(value = "x", defaultValue = "0") String x, @RequestParam(value = "y", defaultValue = "0") String y, @RequestParam(value = "z", defaultValue = "0") String z) {
		try {
			var xInt = Integer.parseInt(x);
			var yInt = Integer.parseInt(y);
			var zInt = Integer.parseInt(z);

			return coptersController.CreateCopter(xInt, yInt, zInt);
		} catch (NumberFormatException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/delete-remote-control")
	public boolean DeleteRemoteControl(@RequestParam(value = "id", defaultValue = "") String id) {
		return remoteControlsController.DeleteRemoteControl(id);
	}

	@GetMapping("/delete-copter")
	public boolean DeleteCopter(@RequestParam(value = "id", defaultValue = "") String id) {
		return coptersController.DeleteCopter(id);
	}

	@GetMapping("/connect")
	public String Connect(@RequestParam(value = "remote-control-id", defaultValue = "") String remoteControlId, @RequestParam(value = "copter-id", defaultValue = "") String copterId) {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(remoteControlId);
			var copter = coptersController.GetCopter(copterId);
			remoteControl.ConnectCopter(copter);
			return String.format("RemoteControl: %s successfully connected to Copter: %s", remoteControlId, copterId);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/get-copter-position")
	public String GetCopterPosition(@RequestParam(value = "id", defaultValue = "") String id) {
		try {
			var copter = coptersController.GetCopter(id);
			return copter.GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/move-forward")
	public String MoveForward(@RequestParam(value = "id", defaultValue = "") String id) throws RemoteControlNotConnectedException, CopterNotConnectedException, CopterOutOfRangeException {
		try {

		} catch (Exception e) {

		}
		var remoteControl = remoteControlsController.GetRemoteControl(id);
		remoteControl.SendCommand(RemoteControlCommands.MOVE_FORWARD);
		return remoteControl.GetCopter().GetPosition().toString();
	}

//	@GetMapping("/move-backward")
//	public String MoveBackward() {
//		remoteControll.SendCommand(RemoteControlCommands.MOVE_BACKWARD);
//		return copter.GetPosition().toString();
//	}
//
//	@GetMapping("/move-right")
//	public String MoveRight() {
//		remoteControll.SendCommand(RemoteControlCommands.MOVE_RIGHT);
//		return copter.GetPosition().toString();
//	}
//
//	@GetMapping("/move-left")
//	public String MoveLeft() {
//		remoteControll.SendCommand(RemoteControlCommands.MOVE_LEFT);
//		return copter.GetPosition().toString();
//	}
//
//	@GetMapping("/move-up")
//	public String MoveUp() {
//		try {
//			remoteControll.SendCommand(RemoteControlCommands.MOVE_UP);
//			return copter.GetPosition().toString();
//		} catch (Exception e) {
//			return e.getMessage();
//		}
//	}
//
//	@GetMapping("/move-down")
//	public String MoveDown() throws RemoteControlNotConnectedException, CopterNotConnectedException, CopterOutOfRangeException {
//		remoteControll.SendCommand(RemoteControlCommands.MOVE_DOWN);
//		return copter.GetPosition().toString();
//	}
}
