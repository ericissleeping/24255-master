package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.auto.actions.ExtendAction;
import org.firstinspires.ftc.teamcode.auto.actions.LiftAction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class RobotHardwareAuto {

    private static final double INTAKE_ON_POWER = -0.9;
    private static final double INTAKE_REVERSE_POWER = 1.0;
    private static final double INTAKE_OFF_POWER = 0.0;
    private static final double INTAKE_ON_POS = 0.922;  //收集时的角度
    private static final double INTAKE_INIT_POS = 0.2;

    private static final double INTAKE_ROTATE_PREPARE_POS = .856;
    private static final double INTAKE_ROTATE_COLLECT_POS = .939;
    private static final double INTAKE_ROTATE_AVOID_POS = .440;
    private static final double INTAKE_ROTATE_INIT_POS = .361;


    private static final double INTAKE_FLIP_PREPARE_POS = .042;
    private static final double INTAKE_FLIP_COLLECT_POS = .077;
    private static final double INTAKE_FLIP_AVOID_POS = .380;
    private static final double INTAKE_FLIP_INIT_POS = .754;

    private static final double INTAKE_CLAW_HOR_POS = .773;
    private static final double INTAKE_CLAW_VER_POS = .459;

    private static final double INTAKE_CLAW_OPEN_POS = .694;
    private static final double INTAKE_CLAW_CLOSE_POS = .407;


    private static final double SPECIMEN_CLAW_OPEN_POS = 0.25;
    private static final double SPECIMEN_CLAW_CLOSE_POS = 0.76;

    private static final double LIFT_ROTATE_INIT_POS = 0.565;
    private static final double LIFT_ROTATE_RELEASE_POS = 1.0;

    private static final double LEFT_LOCK_ON = 0.4;
    private static final double LEFT_LOCK_OFF = 0.7;
    private static final double RIGHT_LOCK_ON = 1.0;
    private static final double RIGHT_LOCK_OFF = 0.75;

    private static final int LIFT_UP_LIMIT = -1000;
    private static final int LIFT_DOWN_LIMIT = 0;
    private static final int LIFT_MAX_VELOCITY = 2600;
    private static final double LIFT_GRAVITY_FF_VOLT = -0.9;
    private static final double LIFT_FRICTION_FF_VOLT = 0.25;
    private static final int EXTEND_OUT_LIMIT_POS = -400;


    private HardwareMap hw;

    private TrapezoidProfile liftFile;
    private int liftTargetPos;

    private static final TrapezoidProfile.Constraints liftConstraint
            = new TrapezoidProfile.Constraints(2400, 8800);

    private final PIDController liftController = new PIDController(0.004, 0.0, 0.0);

    private DcMotorEx extendMotor;

    private DcMotorEx liftRight;

    private DcMotorEx liftLeft;

    private CRServo intakeServo;

    //private ServoImplEx intakeRotateServo;

    private Servo liftRotateServo;

    private Servo intakeRotateServo;

    private Servo intakeFlipServo;

    private Servo intakeClawRotateServo;

    private Servo intakeClaw;


    private Servo specimenClawServo;

    private DigitalChannel extendLimit;

    private DigitalChannel liftLimit;

    private VoltageSensor voltageSensor;

    private Servo leftLock;
    private Servo rightLock;

    private ScheduledExecutorService executor;

    public RobotHardwareAuto(HardwareMap hardwareMap) {
        this.hw = hardwareMap;
    }

    public void hardwareInit() {

        extendMotor = hw.get(DcMotorEx.class, "extend");
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftRight = hw.get(DcMotorEx.class, "liftRight");
        liftLeft = hw.get(DcMotorEx.class, "liftLeft");
        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //intakeServo = hw.get(CRServo.class, "intake");

        intakeRotateServo = hw.get(Servo.class, "intakeRotate");

        intakeFlipServo = hw.get(Servo.class, "intakeFlip");

        intakeClawRotateServo = hw.get(Servo.class, "clawRotate");

        intakeClaw = hw.get(Servo.class, "intakeClaw");

        liftRotateServo = hw.get(Servo.class, "liftRotate");

        specimenClawServo = hw.get(Servo.class, "specimenClaw");

        extendLimit = hw.get(DigitalChannel.class, "extendLimit");

        liftLimit = hw.get(DigitalChannel.class, "liftLimit");

        voltageSensor = hw.voltageSensor.iterator().next();


        leftLock = hw.get(Servo.class, "leftLock");

        rightLock = hw.get(Servo.class, "rightLock");
    }


    public void intakeTrans() {
        intakeRotateServo.setPosition(INTAKE_ROTATE_INIT_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_INIT_POS);
        intakeClawRotateServo.setPosition(INTAKE_CLAW_HOR_POS);
    }

    public void intakeAvoid() {
        intakeRotateServo.setPosition(INTAKE_ROTATE_AVOID_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_AVOID_POS);
        intakeClawRotateServo.setPosition(INTAKE_CLAW_HOR_POS);
    }

    public void intakePrepare() {
        intakeRotateServo.setPosition(INTAKE_ROTATE_PREPARE_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_PREPARE_POS);
        intakeClawRotateServo.setPosition(INTAKE_CLAW_HOR_POS);
    }

    public void intakeCollect() {
        intakeRotateServo.setPosition(INTAKE_ROTATE_COLLECT_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_COLLECT_POS);
        intakeClawRotateServo.setPosition(INTAKE_CLAW_VER_POS);
    }

    public void intakeCollectHor() {
        intakeRotateServo.setPosition(INTAKE_ROTATE_COLLECT_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_COLLECT_POS);
        intakeClawRotateServo.setPosition(INTAKE_CLAW_HOR_POS);
    }



    public void setClawOpen() {
        intakeClaw.setPosition(INTAKE_CLAW_OPEN_POS);
    }

    public void setClawClose() {
        intakeClaw.setPosition(INTAKE_CLAW_CLOSE_POS);
    }

    public void collect() {
        setClawOpen();
        executor.schedule(() -> intakeCollect(), 100, TimeUnit.MILLISECONDS);
        executor.schedule(this::setClawClose, 350, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intakePrepare(), 550, TimeUnit.MILLISECONDS);
    }

    public void setLockOn(){
        leftLock.setPosition(LEFT_LOCK_ON);
        rightLock.setPosition(RIGHT_LOCK_ON);
    }

    public void setLockOff(){
        leftLock.setPosition(LEFT_LOCK_OFF);
        rightLock.setPosition(RIGHT_LOCK_OFF);
    }

    public double getGravityFF() {
        return LIFT_GRAVITY_FF_VOLT / this.getBatteryVoltage();
    }

    public void generateLiftMotionFile(int targetPos) {
        this.liftTargetPos = targetPos;
        TrapezoidProfile.State targetState = new TrapezoidProfile.State(targetPos, 0);
        TrapezoidProfile.State currentState = new TrapezoidProfile.State(this.getLiftPos(), this.getLiftVel());
        this.liftFile = new TrapezoidProfile(liftConstraint, targetState, currentState);
    }

    public void followLiftMotionFile(double motionTime) {
        TrapezoidProfile.State state = this.liftFile.calculate(motionTime);
        double targetPos = state.position;
        double targetVel = state.velocity;
        double batteryVoltage = this.getBatteryVoltage();
        double frictionFf = Math.copySign(LIFT_FRICTION_FF_VOLT,targetVel) / batteryVoltage;
        double feedforward = targetVel / LIFT_MAX_VELOCITY + LIFT_GRAVITY_FF_VOLT / batteryVoltage + frictionFf;
        double posPidOut = liftController.calculate(this.getLiftPos(), targetPos);
        double out = feedforward + posPidOut;
        liftLeft.setPower(out);
        liftRight.setPower(out);
    }

    public void holdLiftToPosition() {
        double feedforward = LIFT_GRAVITY_FF_VOLT / this.getBatteryVoltage();
        double posPidOut = liftController.calculate(this.getLiftPos(), this.liftTargetPos);
        double out = feedforward + posPidOut;
        liftLeft.setPower(out);
        liftRight.setPower(out);
    }

    public boolean isLiftDown() {
        return this.liftTargetPos == 0;
    }

    public void setLiftIdle() {
        this.setLiftPower(0.0);
    }

    public boolean isMotionFileFinished(double motionTime) {
        return liftFile.isFinished(motionTime);
    }

    public boolean getExtendLimitStatus() {
        return !extendLimit.getState();
    }

    public boolean getLiftLimitStatus() {
        return !liftLimit.getState();
    }

    public void setIntakeExtendPower(double power) {
        int extendPos = this.getExtendPos();
        if ((this.getExtendLimitStatus() && power > 0.0) || (extendPos < EXTEND_OUT_LIMIT_POS && power < 0.0)) {
            extendMotor.setPower(0.0);
        } else {
            extendMotor.setPower(power);
        }
    }

    public void resetLiftPos() {
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetExtendPos(){
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setLiftPower(double power) {

        int liftPos = this.getLiftPos();
        //编码器限位
        if ((liftPos < LIFT_UP_LIMIT && power < 0) || (liftPos > LIFT_DOWN_LIMIT && power > 0)) {

            liftRight.setPower(0.0);
            liftLeft.setPower(0.0);
        } else {
            liftRight.setPower(power);
            liftLeft.setPower(power);
        }
    }

    //intake 开关


    public void liftRotateToReleasePos() {
        liftRotateServo.setPosition(LIFT_ROTATE_RELEASE_POS);
    }

    public void liftRotateToInitPos() {
        liftRotateServo.setPosition(LIFT_ROTATE_INIT_POS);
    }

    //intake旋转角度
    public void rotateToIntakePos() {
        intakeRotateServo.setPosition(INTAKE_ON_POS);
    }

    public void rotateIntakeToTransferPos() {
        intakeRotateServo.setPosition(INTAKE_INIT_POS);
    }

    public int getLiftPos() {
        return liftLeft.getCurrentPosition();
    }

    public double getLiftVel() {
        return liftLeft.getVelocity();
    }

    public int getExtendPos() {
        return extendMotor.getCurrentPosition();
    }

    public void openSpecimenClaw() {
        specimenClawServo.setPosition(SPECIMEN_CLAW_OPEN_POS);
    }

    public void closeSpecimenClaw() {
        specimenClawServo.setPosition(SPECIMEN_CLAW_CLOSE_POS);
    }

    public void extendToPos(int targetPos) {
        extendMotor.setTargetPosition(targetPos);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(1.0);
    }

    public void extendIn(){
        if (this.getExtendLimitStatus()){
            extendMotor.setPower(0.0);
        }else {
            extendMotor.setPower(0.8);
        }
    }

    public void setExtendMotorModeToRunWithoutEncoder(){
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double getBatteryVoltage() {
        return voltageSensor.getVoltage();
    }


    //[sa , sb] -> [ta, tb]
    public double mapNumTo(double sa, double sb, double ta, double tb, double input) {
        double factor = (tb - ta) / (sb - sa);
        return ta + (input - sa) * factor;
    }

    public Action liftAction(int targetPos) {
        return new LiftAction(this, targetPos);
    }

    public Action extendInAction(){
        return new ExtendAction(this);
    }

}
