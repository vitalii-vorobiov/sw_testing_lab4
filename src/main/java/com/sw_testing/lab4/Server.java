package com.sw_testing.lab4;

import com.sw_testing.lab4.copter.CoptersController;
import com.sw_testing.lab4.exceptions.*;
import com.sw_testing.lab4.remote_control.RemoteControlCommands;
import com.sw_testing.lab4.remote_control.RemoteControlsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@SpringBootApplication
@RestController
public class Server {
	public static final Logger logger = LogManager.getLogger(Server.class);
	private final RemoteControlsController remoteControlsController;
	private final CoptersController coptersController;

	public Server() {
		remoteControlsController = new RemoteControlsController();
		coptersController = new CoptersController();
		logger.info("Initializing resources");
	}

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
		logger.info("Starting Server");
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

	@GetMapping("/is-copter-connected")
	public String IsCopterConnected(@RequestParam(value = "remote-control-id", defaultValue = "") String remoteControlId, @RequestParam(value = "copter-id", defaultValue = "") String copterId) {
		try {
			var copter = coptersController.GetCopter(copterId);
			if (copter.IsConnected(remoteControlId)) {
				return String.format("Copter: %s is connected to RemoteControl: %s", copterId, remoteControlId);
			} else {
				return String.format("Copter: %s is not connected to RemoteControl: %s", copterId, remoteControlId);
			}
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
	public String MoveForward(@RequestParam(value = "id", defaultValue = "") String id) {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(id);
			remoteControl.SendCommand(RemoteControlCommands.MOVE_FORWARD);
			return remoteControl.GetCopter().GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/move-backward")
	public String MoveBackward(@RequestParam(value = "id", defaultValue = "") String id) {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(id);
			remoteControl.SendCommand(RemoteControlCommands.MOVE_BACKWARD);
			return remoteControl.GetCopter().GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/move-right")
	public String MoveRight(@RequestParam(value = "id", defaultValue = "") String id) {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(id);
			remoteControl.SendCommand(RemoteControlCommands.MOVE_RIGHT);
			return remoteControl.GetCopter().GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/move-left")
	public String MoveLeft(@RequestParam(value = "id", defaultValue = "") String id) {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(id);
			remoteControl.SendCommand(RemoteControlCommands.MOVE_LEFT);
			return remoteControl.GetCopter().GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/move-up")
	public String MoveUp(@RequestParam(value = "id", defaultValue = "") String id) {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(id);
			remoteControl.SendCommand(RemoteControlCommands.MOVE_UP);
			return remoteControl.GetCopter().GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/move-down")
	public String MoveDown(@RequestParam(value = "id", defaultValue = "") String id) throws RemoteControlNotConnectedException, CopterNotConnectedException, CopterOutOfRangeException {
		try {
			var remoteControl = remoteControlsController.GetRemoteControl(id);
			remoteControl.SendCommand(RemoteControlCommands.MOVE_DOWN);
			return remoteControl.GetCopter().GetPosition().toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
