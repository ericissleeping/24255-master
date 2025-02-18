package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RobotHardware {

    public GoBildaPinpointDriver pinPoint;
    private double theta, power, turn, realTheta;

    private static final double INTAKE_ON_POWER = -1.0;
    private static final double INTAKE_REVERSE_POWER = 1.0;
    private static final double INTAKE_OFF_POWER = 0.0;
    private static final double INTAKE_ON_POS = 0.92;  //收集时的角度
    private static final double INTAKE_INIT_POS = 0.2;



    //intake大臂
    private static final double INTAKE_ROTATE_PREPARE_POS = .572;
    private static final double INTAKE_ROTATE_COLLECT_POS = .683;
    private static final double INTAKE_ROTATE_AVOID_POS = .192;
    private static final double INTAKE_ROTATE_INIT_POS = .131;

//    private static final double INTAKE_ROTATE_PREPARE_POS = .856;
//    private static final double INTAKE_ROTATE_COLLECT_POS = .939;
//    private static final double INTAKE_ROTATE_AVOID_POS = .440;
//    private static final double INTAKE_ROTATE_INIT_POS = .361;

    //intake 小臂
    private static final double INTAKE_FLIP_PREPARE_POS = .207;
    private static final double INTAKE_FLIP_COLLECT_POS = .302;
    private static final double INTAKE_FLIP_AVOID_POS = .779;
    private static final double INTAKE_FLIP_INIT_POS = 1.0;


    //旋转
    private static final double INTAKE_CLAW_HOR_POS = .073;
    private static final double INTAKE_CLAW_VER_POS = .412;


    //爪子开合
    private static final double INTAKE_CLAW_OPEN_POS = .533;
    private static final double INTAKE_CLAW_CLOSE_POS = .231;



    private static final double SPECIMEN_CLAW_OPEN_POS = 0.410;
    private static final double SPECIMEN_CLAW_CLOSE_POS = 0.755;
    private static final double LIFT_ROTATE_INIT_POS = 0.363;
    private static final double LIFT_ROTATE_RELEASE_POS = 0.8;
    private static final double LEFT_LOCK_ON = 0.522;
    private static final double LEFT_LOCK_OFF =  0.766;
    private static final double RIGHT_LOCK_ON = 0.710;
    private static final double RIGHT_LOCK_OFF = 0.311;

    private static final int LIFT_UP_LIMIT = 1580;
    private static final int LIFT_DOWN_LIMIT = 0;
    private static final int LIFT_MAX_VELOCITY = 2600;
    private static final double LIFT_GRAVITY_FF_VOLT = 0.9;
    private static final int EXTEND_OUT_LIMIT_POS = -250;


    private HardwareMap hw;

    private TrapezoidProfile liftFile;
    private int liftTargetPos;

    private static final TrapezoidProfile.Constraints liftConstraint
            = new TrapezoidProfile.Constraints(2400, 8800);

    private final PIDController liftController = new PIDController(0.004, 0.0, 0.0);

    private DcMotorEx leftFront;

    private DcMotorEx leftRear;

    private DcMotorEx rightFront;

    private DcMotorEx rightRear;

    public DcMotorEx extendMotor;

    private DcMotorEx liftRight;

    private DcMotorEx liftLeft;

//    private CRServo intakeServo;

    private Servo intakeRotateServo;

    private Servo intakeFlipServo;

    private Servo intakeClawRotateServo;

    private Servo intakeClaw;

    private Servo liftRotateServo;

    private Servo specimenClawServo;
    private Servo leftLock;
    private Servo rightLock;

    private DigitalChannel extendLimit;

    private DigitalChannel liftLimit;

    private VoltageSensor voltageSensor;

    private ScheduledExecutorService executor;

    public RobotHardware(HardwareMap hardwareMap) {
        this.hw = hardwareMap;
    }

    public void hardwareInit() {

        executor = Executors.newScheduledThreadPool(5);

        leftFront = hw.get(DcMotorEx.class, "leftFront");
        leftRear = hw.get(DcMotorEx.class, "leftRear");
        rightFront = hw.get(DcMotorEx.class, "rightFront");
        rightRear = hw.get(DcMotorEx.class, "rightRear");

        pinPoint = hw.get(GoBildaPinpointDriver.class, "pp");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extendMotor = hw.get(DcMotorEx.class, "extend");
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftRight = hw.get(DcMotorEx.class, "liftRight");
        liftLeft = hw.get(DcMotorEx.class, "liftLeft");
        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


//        intakeServo = hw.get(CRServo.class, "intake");

        intakeRotateServo = hw.get(Servo.class, "intakeRotate"); //大臂

        intakeFlipServo = hw.get(Servo.class, "intakeFlip"); //小臂

        intakeClawRotateServo = hw.get(Servo.class, "clawRotate"); //翻转角度

        intakeClaw = hw.get(Servo.class, "intakeClaw"); //爪子



        liftRotateServo = hw.get(Servo.class, "liftRotate"); //框子

        specimenClawServo = hw.get(Servo.class, "specimenClaw");

        leftLock = hw.get(Servo.class, "leftLock");

        rightLock = hw.get(Servo.class, "rightLock");

        extendLimit = hw.get(DigitalChannel.class, "extendLimit");

        liftLimit = hw.get(DigitalChannel.class, "liftLimit");

        voltageSensor = hw.voltageSensor.iterator().next();
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

    public void intakePrepare(boolean ver) {
        intakeRotateServo.setPosition(INTAKE_ROTATE_PREPARE_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_PREPARE_POS);
        if (ver) intakeClawRotateServo.setPosition(INTAKE_CLAW_VER_POS);
        else intakeClawRotateServo.setPosition(INTAKE_CLAW_HOR_POS);
    }

    public void intakeCollect(boolean ver) {
        intakeRotateServo.setPosition(INTAKE_ROTATE_COLLECT_POS);
        intakeFlipServo.setPosition(INTAKE_FLIP_COLLECT_POS);
        if (ver) intakeClawRotateServo.setPosition(INTAKE_CLAW_VER_POS);
        else intakeClawRotateServo.setPosition(INTAKE_CLAW_HOR_POS);
    }

    public void setClawOpen() {
        intakeClaw.setPosition(INTAKE_CLAW_OPEN_POS);
    }

    public void setClawClose() {
        intakeClaw.setPosition(INTAKE_CLAW_CLOSE_POS);
    }

    public void collect(boolean ver) {
        setClawOpen();
        executor.schedule(() -> intakeCollect(ver), 100, TimeUnit.MILLISECONDS);
        executor.schedule(this::setClawClose, 350, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intakePrepare(ver), 550, TimeUnit.MILLISECONDS);
    }

    public void trans() {
        intakeTrans();
        executor.schedule(this::setClawOpen, 350, TimeUnit.MILLISECONDS);
        executor.schedule(this::intakeAvoid, 450, TimeUnit.MILLISECONDS);
    }

    public void openClawAvoidBox() {
        setClawOpen();
        executor.schedule(this::intakeAvoid, 200, TimeUnit.MILLISECONDS);
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
        double feedforward = targetVel / LIFT_MAX_VELOCITY + LIFT_GRAVITY_FF_VOLT / batteryVoltage;
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
//        if (extendMotor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER){
//            extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        }
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

    public void setLiftPower(double power) {

        int liftPos = this.getLiftPos();
        //编码器限位
        if ((liftPos < LIFT_DOWN_LIMIT && power < 0) || (liftPos > LIFT_UP_LIMIT && power > 0)) {

            liftRight.setPower(0.0);
            liftLeft.setPower(0.0);
        } else {
            liftRight.setPower(power);
            liftLeft.setPower(power);
        }
    }

    //intake 开关
//    public void setIntakeOn() {
//        intakeServo.setPower(INTAKE_ON_POWER);
//    }
//
//    public void setIntakeReverse() {
//        intakeServo.setPower(INTAKE_REVERSE_POWER);
//    }
//
//    public void setIntakeOff() {
//        intakeServo.setPower(INTAKE_OFF_POWER);
//    }

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

    public double getBatteryVoltage() {
        return voltageSensor.getVoltage();
    }


    //[sa , sb] -> [ta, tb]
    public double mapNumTo(double sa, double sb, double ta, double tb, double input) {
        double factor = (tb - ta) / (sb - sa);
        return ta + (input - sa) * factor;
    }

    public void setDriveBasePower(double axial, double lateral, double yaw) {
        double max;
        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftRearPower = axial - lateral + yaw;
        double rightRearPower = axial + lateral - yaw;

        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftRearPower));
        max = Math.max(max, Math.abs(rightRearPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftRearPower /= max;
            rightRearPower /= max;
        }

        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftRearPower);
        rightRear.setPower(rightRearPower);

    }

    public double getHeading() {
        return Math.toDegrees(pinPoint.getHeading());
    }

    public void driveFieldOriented(double y, double x, double rx, double p) {
        pinPoint.update();
        theta = Math.atan2(y, x) * 180 / Math.PI;
        power = Math.hypot(x, y);
        turn = rx;

        realTheta = (360 - pinPoint.getPosition().getHeading(AngleUnit.DEGREES)) + theta;

        double sin = Math.sin((realTheta * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((realTheta * (Math.PI / 180)) - (Math.PI / 4));
        double maxSinCos = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFrontPower = (power * cos / maxSinCos + turn);
        double rightFrontPower = (power * sin / maxSinCos - turn);
        double leftBackPower = (power * sin / maxSinCos + turn);
        double rightBackPower = (power * cos / maxSinCos - turn);

        leftFront.setPower(leftFrontPower * p);
        rightFront.setPower(rightFrontPower * p);
        leftRear.setPower(leftBackPower * p);
        rightRear.setPower(rightBackPower * p);
    }

}
