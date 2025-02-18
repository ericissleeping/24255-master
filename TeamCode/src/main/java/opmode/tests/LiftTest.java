package opmode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(group = "TeleOp", name = "Hang Test")
public class LiftTest extends OpMode {

    DcMotor rightL = null;
    DcMotor leftL = null;
    @Override
    public void init() {
//        rightL = hardwareMap.get(DcMotor.class, "rightLift");
//        leftL = hardwareMap.get(DcMotor.class, "leftLift");
//
//        rightL.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftL.setDirection(DcMotorSimple.Direction.FORWARD);
//        rightL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rightL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rightL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        leftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightL = hardwareMap.get(DcMotorEx.class, "liftRight");
        leftL = hardwareMap.get(DcMotorEx.class, "liftLeft");
        leftL.setDirection(DcMotorSimple.Direction.REVERSE);
        leftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        telemetry.addData("Init Complete", true);
        telemetry.update();
    }

    @Override
    public void loop() {

        leftL.setPower(gamepad2.left_stick_y);
        rightL.setPower(gamepad2.left_stick_y);
        leftL.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        rightL.setPower(gamepad1.right_trigger-gamepad1.left_trigger);

        telemetry.addData("Right Lift Position: ", rightL.getCurrentPosition());
        telemetry.update();
    }
}
