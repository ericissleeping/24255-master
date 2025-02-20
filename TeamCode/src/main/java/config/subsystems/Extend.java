package config.subsystems;

import static config.core.RobotConstants.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import config.core.hardware.CachedMotor;

/** @author Baron Henderson
 * @version 2.0 | 1/4/25
 */

public class Extend {
    private MultipleTelemetry telemetry;

    public DcMotorEx extendMotor;
    private double pos = 0;

    public Extend(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        extendMotor = hardwareMap.get(DcMotorEx.class, "extend");
        extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void manual(double left, double right) {
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (pos < -290 && (right - left) < 0)  {
            extendMotor.setPower(0);

        }
        else{
            extendMotor.setPower(1*(right - left));
        }
    }

    public void manualSlowly(double left, double right) {
        if (pos < -290 && (right - left) < 0)  {
            extendMotor.setPower(0);

        }
        else{
            extendMotor.setPower(0.5*(right - left));
        }
    }

    public int getPos() {
        pos = extendMotor.getCurrentPosition();
        return extendMotor.getCurrentPosition();
    }

    public void extendToPos(int targetPos) {
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setTargetPosition(targetPos);
        extendMotor.setPower(1);
    }
    public void extendToPosSlowly(int targetPos) {
        extendMotor.setTargetPosition(targetPos);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(0.1);
    }

    public void toZero(){
        extendToPos(50);
    }

    public void toFull(){
        extendToPos(-300);
    }

    public void toDetect(){
        extendToPosSlowly(-300);
    }

    public void changeMode(){
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }



    public void telemetry() {
        telemetry.addData("Extend Pos: ", getPos());
    }

    public void periodic() {
        telemetry();
    }
}