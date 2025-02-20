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
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name = "Sample Auto")
public class SampleAuto extends OpModeCommand {
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
                                new FollowPath(r.getF(), SamplePath.preload(), true, 1),
                                new PreloadBucket(r)
                        ),
                        new WaitCommand(200),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample1(), true, 1),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
                        ),
                        new clawCollect(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.scoreSample1(), true, 1),
                                new Bucket(r)
                        ),
                        new WaitCommand(200),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample2(), true, 1),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
                        ),
                        new clawCollect(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.scoreSample2(), true, 1),
                                new Bucket(r)
                        ),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.grabSample3(), true, 1),
                                new Transfer(r)
                        ),
                        new clawCollect(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SamplePath.scoreSample3(), true, 1),
                                new Bucket(r)
                        ),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample4(), true, 1),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
                        ),
                        new ParallelRaceGroup(
                                new FollowPath(r.getF(),SamplePath.detectSample4(),true,0.2),
                                new SequentialCommandGroup(
                                        new Detect(r),
                                        new FollowPath(r.getF(), new PathBuilder()
                                                .addPath(new BezierLine(new Point(r.getF().getPose()),new Point(r.getF().getPose())))
                                                .setLinearHeadingInterpolation(90, 90)
                                                .setZeroPowerAccelerationMultiplier(2)
                                                .build(), true, 0.8),
                                        new clawCollect(r)
                                )
                        ),
                        new WaitCommand(700),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), new PathBuilder()
                                        .addPath(new BezierCurve(new Point(r.getF().getPose()),new Point(new Pose(58,108)),new Point(new Pose(17, 124))))
                                        .setLinearHeadingInterpolation(90, 135)
                                        .setZeroPowerAccelerationMultiplier(2)
                                        .build(), true, 0.8),
                                new Bucket(r)
                        ),
                        new ParallelRaceGroup(
                                new ParallelCommandGroup(
                                        new FollowPath(r.getF(), SamplePath.grabSample4(), true, 1),
                                        new Transfer(r)
                                ),
                                new ExtendZero(r)
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
