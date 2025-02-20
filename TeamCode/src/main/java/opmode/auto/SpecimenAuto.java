package opmode.auto;



import config.commands.Bucket;
import config.commands.Detect;
import config.commands.PreloadBucket;
import config.commands.Transfer;
import config.commands.clawAvoid;
import config.commands.clawCollect;
import config.commands.clawGrab;
import config.commands.clawTransfer;
import config.commands.specimenGet;
import config.commands.specimenScore;
import config.commands.specimenScorePreload;
import config.core.Alliance;


import config.core.OpModeCommand;
import config.core.Robot;
import config.core.paths.SamplePath;
import config.core.paths.SpecimenPath;
import config.subsystems.commands.ExtendDetect;
import config.subsystems.commands.ExtendZero;
import config.subsystems.commands.clawOpen;
import config.subsystems.commands.clawTurnHor;
import config.subsystems.commands.restExtend;

import com.pedropathing.commands.FollowPath;

import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name = "Specimen Auto")
public class SpecimenAuto extends OpModeCommand {
    Robot r;

    @Override
    public void initialize() {
        r = new Robot(hardwareMap, telemetry, Alliance.BLUE, SpecimenPath.startPose);
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
                                new FollowPath(r.getF(), SpecimenPath.preload(), true, 1),
                                new specimenScorePreload(r)
                        ),
                        new ParallelDeadlineGroup(
                                new FollowPath(r.getF(), SpecimenPath.grabSample1(), true, 0.8),
                                new Transfer(r),
                                new clawTurnHor(r),
                                new ExtendZero(r)
                        ),
                        new clawGrab(r),
                        new FollowPath(r.getF(), SpecimenPath.giveSample1(), true, 0.8),
                        new ParallelRaceGroup(
                                new clawOpen(r),
                                new WaitCommand(100)
                        ),
                        new ParallelRaceGroup(
                                new FollowPath(r.getF(), SpecimenPath.grabSample2(), true, 0.8),
                                new ExtendZero(r)
                        ),
                        new clawGrab(r),
                        new FollowPath(r.getF(), SpecimenPath.giveSample2(), true, 0.8),
                        new ParallelRaceGroup(
                                new clawOpen(r),
                                new WaitCommand(100)
                        ),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.getSpecimen1(), true, 1),
                                new clawAvoid(r)
                        ),
                        new specimenGet(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.scoreSpecimen1(), true, 0.7),
                                new specimenScore(r)
                        ),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.getSpecimen2(), true, 0.7),
                                new Transfer(r)
                        ),
                        new specimenGet(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.scoreSpecimen2(), true, 0.7),
                                new specimenScore(r)
                        ),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.getSpecimen3(), true, 0.6),
                                new Transfer(r)
                        ),
                        new specimenGet(r),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.scoreSpecimen3(), true, 0.7),
                                new specimenScore(r)
                        ),
                        new ParallelCommandGroup(
                                new FollowPath(r.getF(), SpecimenPath.finalPath(), true, 1),
                                new Transfer(r)
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
