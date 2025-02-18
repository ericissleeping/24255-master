package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.enums.LiftStatus;

@TeleOp
public class TeleOpModeNew extends OpMode {

    private RobotHardware robot;

    private static double DRIVE_BASE_SLOW_RATE = 0.1;
    private static double TURN_SLOW_RATE = 0.7;

    private LiftStatus liftStatus = LiftStatus.IDLE;

    private final Gamepad currentGamepad1 = new Gamepad();
    private final Gamepad currentGamepad2 = new Gamepad();
    private final Gamepad previousGamepad1 = new Gamepad();
    private final Gamepad previousGamepad2 = new Gamepad();

    private final ElapsedTime pressKeyTime = new ElapsedTime();

    private final ElapsedTime liftMotionTime = new ElapsedTime();

    private boolean isIntakeOn;

    private boolean isChangeTo;

    private boolean isOpenClaw;

    private boolean intakeVer;

    private double extendTime;

    private double maxLiftVel;

    private int liftTargetPos;

    private boolean intakeRotate;

    private boolean intakeTrans;

    private boolean moveSlowly;

    private boolean lockIntake =false;

    private boolean isLock = true;

    @Override
    public void init() {
        pressKeyTime.reset();
        robot = new RobotHardware(hardwareMap);
        robot.hardwareInit();
        //TODO: 比赛时移除，仅在自动开始前reset
        robot.resetLiftPos();
        robot.setLockOn();
//        robot.rotateIntakeToTransferPos();
        robot.intakeTrans();
        robot.liftRotateToInitPos();
    }

    @Override
    public void loop() {

        this.copyGamePadValue();

//        double runTime = getRuntime();

        //限位测试
        boolean extendLimitStatus = robot.getExtendLimitStatus();
        telemetry.addData("extendLimitStatus", extendLimitStatus);

        boolean liftLimitStatus = robot.getLiftLimitStatus();
        telemetry.addData("liftLimitStatus", liftLimitStatus);

        // 底盘移动
        this.driveBaseTeleOp();

        if (gamepad1.back) robot.pinPoint.resetPosAndIMU();

        //lock
        this.lockLiftControl();






        //lift
        if (gamepad2.dpad_up) {
            this.liftTargetPos = Constants.LIFT_BASKET_HIGH_POS;
            liftStatus = LiftStatus.GENERATE_FILE;
        } else if (gamepad2.dpad_down) {
            this.liftTargetPos = Constants.TeleOpConstant.LIFT_DOWN_POS;
            liftStatus = LiftStatus.GENERATE_FILE;
        } else if (gamepad2.dpad_left) {
            this.liftTargetPos = Constants.LIFT_SPECIMEN_START_POS;
            liftStatus = LiftStatus.GENERATE_FILE;
        } else if (gamepad2.dpad_right) {
            isOpenClaw = true;
            this.liftTargetPos = Constants.LIFT_SPECIMEN_END_POS;
            liftStatus = LiftStatus.GENERATE_FILE;
        } else if (Math.abs(currentGamepad2.left_stick_y) > 0.1 && Math.abs(previousGamepad2.left_stick_y) < 0.1) {
            liftStatus = LiftStatus.DRIVER_CONTROL;
        }

        this.liftControl();

        telemetry.addData("isOpenClaw", isOpenClaw);

        if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper){
            isIntakeOn = !isIntakeOn;
        }

        //打印位置、速度
        double liftPos = robot.getLiftPos();
        double liftVel = robot.getLiftVel();
        telemetry.addData("liftPos", liftPos);
        telemetry.addData("liftVel", liftVel);
        //获取最大速度
        if (Math.abs(liftVel) > maxLiftVel) {
            maxLiftVel = Math.abs(liftVel);
        }
        telemetry.addData("maxLiftVel", maxLiftVel);




        //rotate claw
        if (intakeRotate){
            if (currentGamepad1.b && !previousGamepad1.b) {
                intakeVer = !intakeVer;
                robot.intakePrepare(intakeVer);
            }
        }




        if (currentGamepad1.left_bumper && !previousGamepad1.left_bumper) {
            if (intakeRotate) {
                robot.intakeTrans();
                TURN_SLOW_RATE=0.7;
                intakeRotate=!intakeRotate;
            } else {
                robot.openClawAvoidBox();
            }
        }

        if (intakeRotate && gamepad1.x) {
            robot.setClawOpen();
        }

        if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
            if (!intakeRotate) {
                robot.intakePrepare(intakeVer);
                TURN_SLOW_RATE=0.2;
                intakeRotate=!intakeRotate;
            } else {
                robot.collect(intakeVer);
            }
        }

        //intake
//        if (gamepad1.left_bumper) {
//            isIntakeOn = false;
//            robot.setIntakeReverse();
//        } else if (isIntakeOn) {
//            robot.setIntakeOn();
//        } else {
//            robot.setIntakeOff();
//        }

        //extend

        if (currentGamepad1.a && !previousGamepad1.a){
            lockIntake=!lockIntake;
            isChangeTo = true;
        }
        if (lockIntake){
            robot.extendToPos(80);
        }
        else{
            if (isChangeTo){
                robot.extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                isChangeTo = false;
            }
            double extendPower = (gamepad1.left_trigger - gamepad1.right_trigger) * 0.45;
            telemetry.addData("extendPower", extendPower);
            robot.setIntakeExtendPower(extendPower);

            moveSlowly = extendPower < -0.3;

            int extendPos = robot.getExtendPos();
            telemetry.addData("extendPos", extendPos);
        }



        //标本爪子开合
        if (gamepad2.x) {
            robot.openSpecimenClaw();
        } else if (gamepad2.y) {
            robot.closeSpecimenClaw();
        }

        //盒子翻转
        if (gamepad2.left_bumper) {
            robot.liftRotateToReleasePos();
        } else if (gamepad2.right_bumper) {
            robot.liftRotateToInitPos();
        }

        telemetry.addData("liftStatus", liftStatus);


    }

    void liftControl() {

        switch (liftStatus) {

            case GENERATE_FILE:
                robot.generateLiftMotionFile(liftTargetPos);
                liftMotionTime.reset();
                liftStatus = LiftStatus.FOLLOW_FILE;
                break;

            case FOLLOW_FILE:
                double t = liftMotionTime.seconds();
                robot.followLiftMotionFile(t);
                if (robot.isMotionFileFinished(t)) {
                    if (robot.isLiftDown()) {
                        liftStatus = LiftStatus.IDLE;
                    } else {
                        liftStatus = LiftStatus.HOLD_POSITION;
                    }
                }
                break;

            case HOLD_POSITION:
                robot.holdLiftToPosition();
                if (isOpenClaw){
                    robot.openSpecimenClaw();
                    isOpenClaw = false;
                }
                break;

            case IDLE:
                robot.setLiftIdle();
                break;

            case DRIVER_CONTROL:
                double liftPower = gamepad2.left_stick_y;  // - robot.getGravityFF()
                telemetry.addData("liftPower", liftPower);
                robot.setLiftPower(-liftPower);
                break;
        }

    }

    void driveBaseTeleOp() {
        double x = -gamepad1.left_stick_y;
        double y = -gamepad1.left_stick_x;
        double rot = -gamepad1.right_stick_x * TURN_SLOW_RATE;  // * TURN_SLOW_RATE

        //平方输入
//        x = Math.copySign(x * x, x);
//        y = Math.copySign(y * y, y);
//        rot = Math.copySign(rot * rot, rot);

        if (moveSlowly) {
            x *= DRIVE_BASE_SLOW_RATE;
            y *= DRIVE_BASE_SLOW_RATE;
            rot *= DRIVE_BASE_SLOW_RATE;
        }


//        robot.setDriveBasePower(x, y, rot);
        robot.driveFieldOriented(y, x, rot, 0.97);
    }

    void copyGamePadValue() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);
    }

    void lockLiftControl(){
        if (currentGamepad2.a && !previousGamepad2.a){
            isLock = !isLock;
        }
        if (isLock){
            robot.setLockOn();
        }else {
            robot.setLockOff();
        }
        telemetry.addData("isLock", isLock);
    }

}
