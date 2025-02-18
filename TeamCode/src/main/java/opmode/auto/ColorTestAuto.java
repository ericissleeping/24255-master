package opmode.auto;



import config.commands.Bucket;
import config.commands.Detect;
import config.commands.Transfer;
import config.commands.clawCollect;
import config.core.Alliance;


import config.core.OpModeCommand;
import config.core.Robot;
import config.core.paths.SamplePath;
import config.subsystems.commands.ExtendDetect;
import config.subsystems.commands.ExtendZero;

import com.pedropathing.commands.FollowPath;

import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name = "ColorTestAuto")
public class ColorTestAuto extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SamplePath.startPose);
        r.intakeInit();
        r.outtakeInit();
        r.extendInit();
    }

    @Override
    public void start() {
        schedule(
                new RunCommand(r::aPeriodic),
                new ParallelRaceGroup(
                        new Detect(r),
                        new ExtendDetect(r),
                        new WaitCommand(4000)
                )
        );
    }

    @Override
    public void loop() {
        super.loop();
        r.aPeriodic();
    }

    @Override
    public void stop() {
        super.stop();
        r.stop();
    }
}
