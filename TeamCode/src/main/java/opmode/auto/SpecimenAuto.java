package opmode.auto;



import config.commands.Bucket;
import config.commands.Detect;
import config.commands.PreloadBucket;
import config.commands.Transfer;
import config.commands.clawCollect;
import config.commands.clawTransfer;
import config.core.Alliance;


import config.core.OpModeCommand;
import config.core.Robot;
import config.core.paths.SamplePath;
import config.core.paths.SpecimenPath;
import config.subsystems.commands.ExtendDetect;
import config.subsystems.commands.ExtendZero;

import com.pedropathing.commands.FollowPath;

import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name = "Specimen Auto")
public class SpecimenAuto extends OpModeCommand {
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
                new SequentialCommandGroup(
                        new FollowPath(r.getF(), SpecimenPath.preload(), true, 0.8),
                        new WaitCommand(500),
                        new FollowPath(r.getF(), SpecimenPath.grabSample1(), true, 0.8),
                        new WaitCommand(500),
                        new FollowPath(r.getF(), SpecimenPath.giveSample1(), true, 0.8),
                        new WaitCommand(500),
                        new FollowPath(r.getF(), SpecimenPath.grabSample2(), true, 0.8),
                        new WaitCommand(500),
                        new FollowPath(r.getF(), SpecimenPath.giveSample2(), true, 0.8),
                        new WaitCommand(500),
                        new FollowPath(r.getF(), SpecimenPath.getSpecimen1(), true, 0.8),
                        new WaitCommand(500),
                        new FollowPath(r.getF(), SpecimenPath.scoreSpecimen1(), true, 0.8),
                        new WaitCommand(500)
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
