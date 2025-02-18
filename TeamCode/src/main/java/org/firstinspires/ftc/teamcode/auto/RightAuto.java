//package org.firstinspires.ftc.teamcode.auto;
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.roadrunner.AccelConstraint;
//import com.acmerobotics.roadrunner.Arclength;
//import com.acmerobotics.roadrunner.CompositeVelConstraint;
//import com.acmerobotics.roadrunner.InstantAction;
//import com.acmerobotics.roadrunner.NullAction;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.Pose2dDual;
//import com.acmerobotics.roadrunner.PosePath;
//import com.acmerobotics.roadrunner.ProfileAccelConstraint;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.SleepAction;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.VelConstraint;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.RobotHardware;
//import org.firstinspires.ftc.teamcode.auto.actions.ParallelDeadLineWithAction;
//import org.firstinspires.ftc.teamcode.constants.Constants;
//
//@Autonomous
//public class RightAuto extends LinearOpMode {
//
//    private final AccelConstraint accelConstraint = new ProfileAccelConstraint(-30,30);
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        //结构初始化
//        RobotHardwareAuto robot = new RobotHardwareAuto(hardwareMap);
//        robot.hardwareInit();
//
//        //底盘初始化，出发点位置定义
//        Pose2d beginPose = new Pose2d(17.25, -64.25, Math.toRadians(-90));
//        MecanumDriveSlow drive = new MecanumDriveSlow(hardwareMap, beginPose);
//
//        //TODO:结构复位
//        //关闭夹爪
//        robot.closeSpecimenClaw();
//        robot.intakeAvoid();
//        //重设Lift、extend编码器值
//        robot.resetLiftPos();
//        robot.resetExtendPos();
//        robot.liftRotateToInitPos();
//        robot.setLockOn();
//
//
//        //等待开始
//        waitForStart();
//
//        //移动到挂杆位置
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        drive.actionBuilder(beginPose)
//                                .strafeToSplineHeading(new Vector2d(12, -37.5), Math.toRadians(-90))
//                                .build(),
//                        robot.liftAction(Constants.LIFT_SPECIMEN_START_POS)
//                )
//
//        );
//        //挂specimen
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(12, -34.5), Math.toRadians(-90))
//                                        .build(),
//                                robot.liftAction(Constants.LIFT_SPECIMEN_END_POS)
//                        )
////                        new InstantAction(robot::openSpecimenClaw),
////                        new SleepAction(0.2)
//                )
//
//
//        );
//
//        //移动到吸取第一块
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .afterTime(1.5, new InstantAction(() -> robot.extendToPos(-350)))
//                                        .strafeToSplineHeading(new Vector2d(32, -38), Math.toRadians(17))
//
//                                        .build(),
//                                new SequentialAction(
//                                        new InstantAction(robot::setClawOpen),
//                                        robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
//
//                                )
//
//                        )
//                )
//        );
//        //夹取第一个
//        Actions.runBlocking(
//                new SequentialAction(
//
//                        new InstantAction(robot::intakeCollect),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::setClawClose),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::intakePrepare),
//                        new SleepAction(0.1)
//                )
//        );
//
////
//        //放置第一块
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(35, -42), Math.toRadians(-41))
//                                        .build(),
//                                new SleepAction(0.1)
//                        ),
//                        robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.1)
//                )
//        );
//
//
//
//        //移动到吸取第2块
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(40, -38), Math.toRadians(15))
//                                        .build(),
//                                new InstantAction(robot::setClawOpen)
//
//                        )
//                )
//        );
//
//        //夹取第2个
//
//        Actions.runBlocking(
//                new SequentialAction(
//
//                        new InstantAction(robot::intakeCollect),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::setClawClose),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::intakePrepare),
//                        new SleepAction(0.1)
//                )
//        );
//
//////
////        //放置第2块
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(39, -40), Math.toRadians(-40))
//                                        .build(),
//                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
//                        ),
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.1)
//                )
//
//        );
//
//
//
//
//
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .splineTo(new Vector2d(35, -38), Math.toRadians(-40))
////                                        .build(),
////                                new SleepAction(0.2)
////                        ),
////                        robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
////                )
////        );
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(robot::setClawOpen),
////                        new SleepAction(0.5)
////                )
////        );
//
//////        吸取第二块
////        Actions.runBlocking(
////                new SequentialAction(
////                        new ParallelDeadLineWithAction(
////                                drive.actionBuilder(drive.pose)
////                                        .afterTime(1.5, new InstantAction(() -> robot.extendToPos(-350)))
////                                        .splineTo(new Vector2d(35, -38), Math.toRadians(-45))
////
////                                        .build(),
////                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
////                        )
//////                        new InstantAction(robot::setClawOpen),
//////                        new SleepAction(0.3),
//////                        new InstantAction(robot::intakeCollect),
//////                        new SleepAction(0.5),
//////                        new InstantAction(robot::setClawClose),
//////                        new SleepAction(0.350),
//////                        new InstantAction(robot::intakePrepare),
//////                        new SleepAction(0.550)
////                )
////        );
////        //放置第二块
////        Actions.runBlocking(
////                new SequentialAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(35, -38), Math.toRadians(-45))
////                                .build(),
////
////                        new SleepAction(0.1),
////                        new InstantAction(robot::setClawOpen),
////                        new SleepAction(0.3)
////                )
////
////        );
////
//////
////
//////
//        //移动到第一个标本拿取位置
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeAvoid),
//                        new InstantAction(() -> robot.extendToPos(0)),
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(52, -65), Math.toRadians(90))
//                                        .build(),
//                                new InstantAction(robot::openSpecimenClaw)
//                        ),
//
//                new SleepAction(0.1),
//                new InstantAction(robot::closeSpecimenClaw),
//                new SleepAction(0.1)
//                )
//
//        );
//
////
//
//        //移动到第一个标本放置位置
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        drive.actionBuilder(drive.pose)
//                                .strafeToSplineHeading(new Vector2d(16, -36.5),Math.toRadians(-90))
//                                .build(),
//                        robot.liftAction(Constants.LIFT_SPECIMEN_START_POS)
//                )
//        );
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                new SequentialAction(
//                                        drive.actionBuilder(drive.pose)
//                                                .strafeToSplineHeading(new Vector2d(15, -35.5), Math.toRadians(-90))
//                                                .build(),
//                                        new SleepAction(0.1)
//                                ),
//                                robot.liftAction(Constants.LIFT_SPECIMEN_END_POS)
//                        ),
//                        new InstantAction(robot::openSpecimenClaw)
//                )
//
//
//        );
//
//
//
//        //移动到第二个标本位置
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeAvoid),
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(50, -64), Math.toRadians(90))
//                                        .build(),
//                                new SequentialAction(
//                                        robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS),
//                                        new InstantAction(robot::openSpecimenClaw)
//                                )
//
//                        ),
//                        new SleepAction(0.1),
//                        new InstantAction(robot::closeSpecimenClaw),
//                        new SleepAction(0.2)
//                )
//
//        );
//
//        //移动到第二个标本放置位置
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        drive.actionBuilder(drive.pose)
//                                .strafeToSplineHeading(new Vector2d(18, -37.2),Math.toRadians(-90))
//                                .build(),
//                        robot.liftAction(Constants.LIFT_SPECIMEN_START_POS)
//                )
//        );
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(18, -36), Math.toRadians(-90))
//                                        .build(),
//                                robot.liftAction(Constants.LIFT_SPECIMEN_END_POS)
//                        ),
//                        new InstantAction(robot::openSpecimenClaw)
//                )
//
//
//        );
//
//        //移动到第3个标本位置
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeAvoid),
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(51, -63.5), Math.toRadians(90))
//                                        .build(),
//                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
//                        ),
//                        new InstantAction(robot::openSpecimenClaw),
//                        new SleepAction(0.1),
//                        new InstantAction(robot::closeSpecimenClaw),
//                        new SleepAction(0.1)
//                )
//
//        );
//
//        //移动到第3个标本放置位置
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        drive.actionBuilder(drive.pose)
//                                .strafeToSplineHeading(new Vector2d(20, -37),Math.toRadians(-90))
//                                .build(),
//                        robot.liftAction(Constants.LIFT_SPECIMEN_START_POS)
//                )
//        );
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelDeadLineWithAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(20, -35.5), Math.toRadians(-90))
//                                        .build(),
//                                robot.liftAction(Constants.LIFT_SPECIMEN_END_POS+170)
//                        ),
//                        new InstantAction(robot::openSpecimenClaw)
//
//                )
//
//
//        );
//
//
////        //park
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(28,-50), Math.toRadians(-40))
////                                .build(),
////                        robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
////                )
////        );
//
//    }
//}
