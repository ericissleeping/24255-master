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
import config.subsystems.commands.ExtendDetect;
import config.subsystems.commands.ExtendZero;

import com.pedropathing.commands.FollowPath;

import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name = "Sample Auto Stable")
public class SampleAutoStable extends OpModeCommand {
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
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.preload(), true, 0.8),
                                new PreloadBucket(r)
                        ),
                        new WaitCommand(200),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample1(), true, 0.8),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
                        ),
                        new WaitCommand(500),
                        new clawCollect(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.scoreSample1(), true, 0.8),
                                new Bucket(r)
                        ),
                        new WaitCommand(200),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample2(), true, 0.8),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
                        ),
                        new WaitCommand(500),
                        new clawCollect(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.scoreSample2(), true, 0.8),
                                new Bucket(r)
                        ),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.grabSample3(), true, 0.8),
                                new Transfer(r)
                        ),
                        new WaitCommand(500),
                        new clawCollect(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.scoreSample3(), true, 0.8),
                                new Bucket(r)
                        ),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample4(), true, 0.8),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
                        ),
                        new WaitCommand(1000),
                        new ParallelRaceGroup(
                                new Detect(r),
                                new WaitCommand(5000)
                        )
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
