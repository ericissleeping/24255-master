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
public class TestOpMode extends OpMode {

    private DcMotorEx rotateMotor;
    private DcMotorEx extendMotor;

    private CRServo intakeServo;
    private Servo intakeRotateServo;

    private final double INTAKE_ROTATE_POS = 0.34;
    private final double INIT_ROTATE_POS = 0.0;

    private ElapsedTime keyPressTime = new ElapsedTime();
    private boolean intakeOn = false;
    @Override
    public void init() {

        rotateMotor = hardwareMap.get(DcMotorEx.class,"rotate");
        extendMotor = hardwareMap.get(DcMotorEx.class, "extend");
        intakeServo = hardwareMap.get(CRServo.class, "intake");
        intakeRotateServo = hardwareMap.get(Servo.class, "intakeRotate");

        keyPressTime.reset();

    }

    @Override
    public void loop() {
        double rotatePower = -gamepad1.left_stick_y;
        telemetry.addData("power", rotatePower);
        rotateMotor.setPower(rotatePower);

        double extendPower = gamepad1.right_stick_x;
        telemetry.addData("extendPower", extendPower);
        extendMotor.setPower(extendPower);

        if (gamepad1.x && keyPressTime.seconds() > 0.2){
            intakeOn = !intakeOn;
            keyPressTime.reset();
        }
        if (intakeOn){
            intakeServo.setPower(1.0);
            intakeRotateServo.setPosition(INTAKE_ROTATE_POS);
        }else {
            intakeServo.setPower(0.0);
            intakeRotateServo.setPosition(INIT_ROTATE_POS);
        }
        //rotate
        double servoIntakePower = gamepad1.left_stick_y;
        telemetry.addData("servoIntakePower", servoIntakePower);
//        intakeRotateServo.setPosition(servoIntakePower);
    }
}
