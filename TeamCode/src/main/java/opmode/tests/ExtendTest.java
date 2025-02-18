package opmode.tests;

import static config.core.RobotConstants.extendFull;
import static config.core.RobotConstants.extendZero;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "TeleOp", name = "Extend Test")
@Config
public class ExtendTest extends OpMode {

    public DcMotorEx extendMotor;



    @Override
    public void init() {
        extendMotor = hardwareMap.get(DcMotorEx.class, "extend");
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        double extendPower = (gamepad1.left_trigger - gamepad1.right_trigger) * 0.45;
        extendMotor.setPower(extendPower);

        telemetry.addData("Extend Position: ", extendMotor.getCurrentPosition());
        telemetry.update();

    }
}
