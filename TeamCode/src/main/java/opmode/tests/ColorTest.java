package opmode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp
public class ColorTest extends LinearOpMode {
    ColorSensor color;

    @Override
    public void runOpMode(){
        color=hardwareMap.get(ColorSensor.class,"color");

        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Red", color.red());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Green", color.green());
            telemetry.update();


        }
    }
}
