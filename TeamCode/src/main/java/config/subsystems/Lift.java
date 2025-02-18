package config.subsystems;

import static config.core.RobotConstants.BASKET_ROTATE_SCORE_POS;
import static config.core.RobotConstants.BASKET_ROTATE_TRANSFER_POS;
import static config.core.RobotConstants.LEFT_LOCK_OFF;
import static config.core.RobotConstants.LEFT_LOCK_ON;
import static config.core.RobotConstants.LIFT_SPECIMEN_END_POS;
import static config.core.RobotConstants.LIFT_SPECIMEN_START_POS;
import static config.core.RobotConstants.RIGHT_LOCK_OFF;
import static config.core.RobotConstants.RIGHT_LOCK_ON;
import static config.core.RobotConstants.SPECIMEN_CLAW_CLOSE_POS;
import static config.core.RobotConstants.SPECIMEN_CLAW_OPEN_POS;
import static config.core.RobotConstants.liftToHighBucket;
import static config.core.RobotConstants.liftToZero;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import config.core.hardware.CachedMotor;

/** @author Baron Henderson
 * @version 2.0 | 1/4/25
 */

public class Lift {
    public enum lockState {
        OPEN,CLOSE
    }
    public lockState lockState;

    private Telemetry telemetry;
    public CachedMotor rightLift, leftLift;
    public Servo rightLock;
    public Servo leftLock;
    public int pos;
    public PIDController pid;
    public boolean pidOn = false;
    public static int target;
    public static double p = 0.01, i = 0, d = 0, f = 0.005;

    private boolean liftManual=true;


    public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

//        rightLift = new CachedMotor(hardwareMap.get(DcMotor.class, "rightLift"));
//        leftLift = new CachedMotor(hardwareMap.get(DcMotor.class, "leftLift"));
//
//        rightLift.setDirection(DcMotor.Direction.FORWARD);
//        leftLift.setDirection(DcMotor.Direction.REVERSE);
//        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rightLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        leftLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightLift = new CachedMotor(hardwareMap.get(DcMotor.class, "liftRight"));
        leftLift = new CachedMotor(hardwareMap.get(DcMotor.class,"liftLeft"));
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLock = hardwareMap.get(Servo.class, "leftLock");
        rightLock = hardwareMap.get(Servo.class, "rightLock");

        pid = new PIDController(p, i, d);
    }
    public void setLockState(lockState state){
        if (state == lockState.OPEN){
            rightLock.setPosition(RIGHT_LOCK_ON);
            leftLock.setPosition(LEFT_LOCK_ON);
            this.lockState= lockState.OPEN;
        }
        else if (state == lockState.CLOSE){
            rightLock.setPosition(RIGHT_LOCK_OFF);
            leftLock.setPosition(LEFT_LOCK_OFF);
            this.lockState= lockState.CLOSE;
        }
    }

    public void setTarget(int b) {
        pidOn = true;
        target = b;
    }

    public void update() {
        if(pidOn && !liftManual) {
            pid.setPID(p, i, d);

            rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            double pid_output = pid.calculate(getPos(), target);
            double power = pid_output + f;

            if (getPos() < 50 && target < 50) {
                rightLift.setPower(0);
                leftLift.setPower(0);
            } else {
                rightLift.setPower(power);
                leftLift.setPower(power);
            }
        }
    }

    public void manual(double power) {
        if (liftManual){
            if ((pos < 0 && (power) < 0) || (pos > 1680 && (power) > 0)) {
                leftLift.setPower(0);
                rightLift.setPower(0);

            }
            else{
                leftLift.setPower(power);
                rightLift.setPower(power);
            }

        }

    }


    public void setPidOn(boolean state){
        pidOn=state;
    }

    public int getPos() {
        pos = leftLift.getPosition();
        return leftLift.getPosition();
    }

    public void init() {
        pid.setPID(p,i,d);
    }

    public void start() {
        target = 0;
    }

    public void toZero() {
        setTarget(liftToZero);
    }

    public void toHighBucket() {
        setTarget(liftToHighBucket);
    }

    public void toChamberStart() {
        setTarget(LIFT_SPECIMEN_START_POS);
    }

    public void toChamberEnd() {
        setTarget(LIFT_SPECIMEN_END_POS);
    }

    public boolean roughlyAtTarget() {
        return Math.abs(getPos() - target) < 50;
    }
    public void setLockOn(){
        setLockState(lockState.OPEN);
    }

    public void setLockOff(){
        setLockState(lockState.CLOSE);
    }

    public Lift.lockState getLockState() {
        return lockState;
    }

    public boolean isLiftManual() {
        return liftManual;
    }

    public void setLiftManual(boolean b){
        liftManual=b;
    }

    public void telemetry() {
        telemetry.addData("Lift Pos: ", getPos());
        telemetry.addData("Lift Target: ", target);
        telemetry.addData("Lock Status: ", getLockState());


    }

    public void periodic() {
        update();
        telemetry();
    }
}
