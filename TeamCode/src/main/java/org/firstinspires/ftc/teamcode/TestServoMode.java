package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp
public class TestServoMode extends OpMode {
    private Servo testServo;

    private final double INTAKE_ROTATE_POS = 0.34;
    private final double INIT_ROTATE_POS = 0.0;

    private ElapsedTime keyPressTime = new ElapsedTime();
    private boolean intakeOn = false;
    @Override
    public void init() {
        testServo = hardwareMap.get(Servo.class, "testServo");

        keyPressTime.reset();

    }

    @Override
    public void loop() {
        //rotate
        double power = gamepad1.left_stick_x;
        testServo.setPosition(power);
        telemetry.addData("power", power);
//        intakeRotateServo.setPosition(servoIntakePower);
    }
}
