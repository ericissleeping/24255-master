package config.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;

import config.core.hardware.CachedMotor;

public class PinPoint {

    public GoBildaPinpointDriver pinPoint;

    public PinPoint(HardwareMap hardwareMap, Telemetry telemetry) {

        pinPoint = hardwareMap.get(GoBildaPinpointDriver.class, "pp");
    }

    public void resetImu(){
        pinPoint.resetPosAndIMU();
    }



}
