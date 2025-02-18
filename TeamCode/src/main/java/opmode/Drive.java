package opmode;

//import static config.core.Robot.autoEndPose;

import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import config.core.Alliance;
import config.core.Robot;

@TeleOp(name = "Drive")
public class Drive extends OpMode {

    Robot r;

    private final Pose startPose = new Pose(0,0,90);

    @Override
    public void init() {
        r = new Robot(hardwareMap, telemetry, gamepad1 , gamepad2, Alliance.BLUE, startPose);
    }

    @Override
    public void start() {
        r.tStart();
        r.intakeInit();
        r.outtakeInit();
        //r.extendInit();
    }

    @Override
    public void loop() {
        r.updateControls();
        r.tPeriodic();
    }
}
