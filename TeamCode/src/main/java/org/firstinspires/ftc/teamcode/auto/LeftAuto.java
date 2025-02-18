//package org.firstinspires.ftc.teamcode.auto;
//
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.InstantAction;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.SleepAction;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.auto.actions.ParallelDeadLineWithAction;
//import org.firstinspires.ftc.teamcode.constants.Constants;
//
//@Autonomous
//public class LeftAuto extends LinearOpMode {
//    RobotHardwareAuto robot;
//    private Vector2d basketVector2d = new Vector2d(-56, -56.5);
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        //结构初始化
//        robot = new RobotHardwareAuto(hardwareMap);
//        robot.hardwareInit();
//
//        //底盘初始化，出发点位置定义
//        Pose2d beginPose = new Pose2d(-41.25, -64.25, Math.toRadians(90));
//        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
//
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
//        //移动到basket
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(beginPose)
//                                        .strafeToSplineHeading(new Vector2d(-64, -55), Math.toRadians(40))
//                                        .build(),
//                                new SleepAction(0.4)
//
//                        ),
//                        new SequentialAction(
//                                new SleepAction(0.5),
//                                robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
//
//                        )
//                )
//
//        );
//
//        //投篮
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::liftRotateToReleasePos),
//                        new SleepAction(0.6)
//                )
//        );
////
//        //移动到吸取第一块
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-60, -43), Math.toRadians(90))
//                                        .build(),
//                                new SleepAction(0.7)
//
//                        ),
//                        new SequentialAction(
//                                new InstantAction(robot::liftRotateToInitPos),
//                                robot.liftAction(Constants.LIFT_INTAKE_SAMPLE_POS)
//                        )
//
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.3),
//                        new InstantAction(robot::intakeCollectHor),
//                        new SleepAction(0.3),
//                        new InstantAction(robot::setClawClose),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::intakePrepare),
//                        new SleepAction(0.2)
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeTrans),
//                        new SleepAction(0.7),
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.7),
//                        new InstantAction(robot::intakeAvoid),
//                        new SleepAction(0.2)
//                )
//        );
////        移动到basket
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-65, -56), Math.toRadians(40))
//                                        .build(),
//                                new SleepAction(0.7)
//                        ),
//                        robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
//                )
//        );
//        //shoot
//        Actions.runBlocking(
//            new SequentialAction(
//                    new InstantAction(robot::liftRotateToReleasePos),
//                    new SleepAction(0.6)
//            )
//        );
//
//
////        //grab second sample
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-70, -43), Math.toRadians(90))
//                                        .build(),
//                                new SleepAction(0.7)
//
//                        ),
//                        new SequentialAction(
//                                new InstantAction(robot::liftRotateToInitPos),
//                                robot.liftAction(Constants.LIFT_INTAKE_SAMPLE_POS)
//                        )
//
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.3),
//                        new InstantAction(robot::intakeCollectHor),
//                        new SleepAction(0.3),
//                        new InstantAction(robot::setClawClose),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::intakePrepare),
//                        new SleepAction(0.2)
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeTrans),
//                        new SleepAction(0.7),
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.7),
//                        new InstantAction(robot::intakeAvoid),
//                        new SleepAction(0.3)
//                )
//        );
////        移动到basket
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-65, -56), Math.toRadians(40))
//                                        .build(),
//                                new SleepAction(0.7)
//                        ),
//                        robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
//                )
//        );
//        //shoot
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::liftRotateToReleasePos),
//                        new SleepAction(0.5)
//                )
//        );
//
//
//// grab third
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.3),
//                        new InstantAction(robot::intakePrepare),
//                        new SleepAction(0.3)
//                )
//        );
//
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-72, -40), Math.toRadians(117))
//                                        .build(),
//                                new SleepAction(0.7)
//
//                        ),
//                        new SequentialAction(
//                                new InstantAction(robot::liftRotateToInitPos),
//                                robot.liftAction(Constants.LIFT_INTAKE_SAMPLE_POS)
//                        )
//
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeCollectHor),
//                        new SleepAction(0.3),
//                        new InstantAction(robot::setClawClose),
//                        new SleepAction(0.2),
//                        new InstantAction(robot::intakePrepare),
//                        new SleepAction(0.2)
//                )
//        );
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::intakeTrans),
//                        new SleepAction(0.7),
//                        new InstantAction(robot::setClawOpen),
//                        new SleepAction(0.7),
//                        new InstantAction(robot::intakeAvoid),
//                        new SleepAction(0.2)
//                )
//        );
////        移动到basket
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-65, -56), Math.toRadians(40))
//                                        .build(),
//                                new SleepAction(0.7)
//                        ),
//                        robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
//                )
//        );
//        //shoot
//        Actions.runBlocking(
//                new SequentialAction(
//                        new InstantAction(robot::liftRotateToReleasePos),
//                        new SleepAction(0.6)
//                )
//        );
//
//        Actions.runBlocking(
//                new ParallelDeadLineWithAction(
//                        new SequentialAction(
//                                drive.actionBuilder(drive.pose)
//                                        .strafeToSplineHeading(new Vector2d(-68, -40), Math.toRadians(90))
//                                        .build(),
//                                new SleepAction(0.7)
//
//                        ),
//                        new SequentialAction(
//                                new InstantAction(robot::liftRotateToInitPos),
//                                new SleepAction(0.5),
//                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
//                        )
//
//                )
//        );
//
//
//
//
//
//
////        //grab third
////
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(new Vector2d(-67, -56), Math.toRadians(109))
////                                        .build(),
////                                new SleepAction(0.5)
////
////                        ),
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToInitPos),
////                                robot.liftAction(Constants.LIFT_INTAKE_SAMPLE_POS),
////                                new SleepAction(0.3)
////                        )
////
////                )
////        );
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(robot::setClawOpen),
////                        new SleepAction(0.3),
////                        new InstantAction(robot::intakePrepare),
////                        new SleepAction(0.3),
////                        new InstantAction(() -> robot.extendToPos(-370)),
////                        new SleepAction(1),
////                        new InstantAction(robot::intakeCollectHor),
////                        new SleepAction(0.3),
////                        new InstantAction(robot::setClawClose),
////                        new SleepAction(0.2),
////                        new InstantAction(robot::intakePrepare),
////                        new SleepAction(0.2)
////                )
////        );
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(() -> robot.extendToPos(60)),
////                        new SleepAction(0.3),
////                        new InstantAction(robot::intakeTrans),
////                        new SleepAction(0.5),
////                        new InstantAction(robot::setClawOpen),
////                        new SleepAction(0.5),
////                        new InstantAction(robot::intakeAvoid),
////                        new SleepAction(0.5)
////                )
////        );
//////        移动到basket
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(new Vector2d(-64, -55), Math.toRadians(38))
////                                        .build(),
////                                new SleepAction(1)
////                        ),
////                        robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////                )
////        );
////        //shoot
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(robot::liftRotateToReleasePos),
////                        new SleepAction(1)
////                )
////        );
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(new Vector2d(-64, -58), Math.toRadians(103))
////                                        .build(),
////                                new SleepAction(0.5)
////
////                        ),
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToInitPos),
////                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS),
////                                new SleepAction(0.7)
////                        )
////
////                )
////        );
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(() -> robot.extendToPos(-350)),
////                        new SleepAction(1.3),
////                        new InstantAction(robot::setClawOpen),
////                        new SleepAction(0.3),
////                        new InstantAction(robot::intakeCollectHor),
////                        new SleepAction(0.2),
////                        new InstantAction(robot::setClawClose),
////                        new SleepAction(0.2),
////                        new InstantAction(robot::intakePrepare),
////                        new SleepAction(0.2)
////                )
////        );
//////        移动到basket
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(new Vector2d(-64, -55), Math.toRadians(40))
////                                        .build(),
////                                new SleepAction(1.0)
////
////                        ),
////                        new SequentialAction(
////                                new InstantAction(() -> robot.extendToPos(0)),
////                                new SleepAction(0.5),
////                                new InstantAction(robot::intakeTrans),
////                                new SleepAction(0.5),
////                                new InstantAction(robot::setClawOpen),
////                                new SleepAction(0.5),
////                                new InstantAction(robot::intakeAvoid),
////                                new SleepAction(1)
////                        )
////                )
////        );
////        //shoot
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToReleasePos),
////                                new SleepAction(0.7)
////                        ),
////                        robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////                )
////        );
//
////        //grab third sample
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(-64, -58), Math.toRadians(100))
////                                .build(),
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToInitPos),
////                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS),
////                                new SleepAction(1)
////                        )
////
////                )
////        );
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(() -> robot.extendToPos(-350)),
////                        new SleepAction(1.3),
////                        new InstantAction(robot::setClawOpen),
////                        new SleepAction(0.3),
////                        new InstantAction(robot::intakeCollectHor),
////                        new SleepAction(0.2),
////                        new InstantAction(robot::setClawClose),
////                        new SleepAction(0.2),
////                        new InstantAction(robot::intakePrepare),
////                        new SleepAction(0.2)
////                )
////        );
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(-64, -55), Math.toRadians(40))
////                                .build(),
////                        new SequentialAction(
////                                new InstantAction(() -> robot.extendToPos(0)),
////                                new SleepAction(0.5),
////                                new InstantAction(robot::intakeTrans),
////                                new SleepAction(0.5),
////                                new InstantAction(robot::setClawOpen),
////                                new SleepAction(0.5),
////                                new InstantAction(robot::intakeAvoid)
////                        )
////                )
////        );
////        //shoot
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToReleasePos),
////                                new SleepAction(0.7)
////                        ),
////                        robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////                )
////        );
//
//
//
//
////orign
//
//
//;
////
////        //移动到basket
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(basketVector2d, Math.toRadians(45))
////                                        .build(),
////                                new SleepAction(1.0)
////                        ),
////
////                        new SequentialAction(
////                                robot.extendInAction(),
////                                new InstantAction(robot::setIntakeReverse),
////                                new SleepAction(1.2),
////                                new InstantAction(robot::setIntakeOff),
////                                robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////                        )
////                )
////
////        );
////
////        //投篮
////        Actions.runBlocking(this.dropToBasketAction());
////
////        //移动到第二块收集位置
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(-42,-36),Math.toRadians(150))
////                                        .build(),
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToInitPos),
////                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
////                        )
////
////                )
////
////        );
////
////        //第二块收集
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(robot::rotateToIntakePos),
////                        new InstantAction(robot::setIntakeOn),
////                        new SleepAction(0.2),
////                        new InstantAction(() -> robot.extendToPos(-250)),
////                        new SleepAction(1.3),
////                        new InstantAction(robot::setIntakeOff),
////                        new InstantAction(robot::rotateIntakeToTransferPos)
////                )
////        );
////
////        //第二块移动
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(new Vector2d(-56, -57.5), Math.toRadians(45))
////                                        .build(),
////                                new SleepAction(1.5)
////                        ),
////                        new SequentialAction(
////                                robot.extendInAction(),
////                                new InstantAction(robot::setIntakeReverse),
////                                new SleepAction(1.0),
////                                robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////                        )
////                )
////
////        );
////
////        //第二块投篮
////        Actions.runBlocking(this.dropToBasketAction());
////
////        //移动到第三块收集
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(-52,-36),Math.toRadians(150))
////                                .build(),
////                        new SequentialAction(
////                                new InstantAction(robot::liftRotateToInitPos),
////                                robot.liftAction(Constants.LIFT_INTAKE_SPECIMEN_POS)
////                        )
////
////                )
////
////        );
////
////        //第三块收集
////        Actions.runBlocking(
////                new SequentialAction(
////                        new InstantAction(robot::rotateToIntakePos),
////                        new InstantAction(robot::setIntakeOn),
////                        new SleepAction(0.2),
////                        new InstantAction(() -> robot.extendToPos(-250)),
////                        new SleepAction(1.3),
////                        new InstantAction(robot::setIntakeOff),
////                        new InstantAction(robot::rotateIntakeToTransferPos)
////                )
////        );
////
////        //第三块移动
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        new SequentialAction(
////                                drive.actionBuilder(drive.pose)
////                                        .strafeToSplineHeading(new Vector2d(-56, -57.5), Math.toRadians(45))
////                                        .build(),
////                                new SleepAction(1.5)
////                        ),
////                        new SequentialAction(
////                                robot.extendInAction(),
////                                new InstantAction(robot::setIntakeReverse),
////                                new SleepAction(1.0),
////                                new InstantAction(robot::setIntakeOff),
////                                robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////                        )
////                )
////
////        );
////
////        //第三块投篮
////        Actions.runBlocking(this.dropToBasketAction());
////
////        Actions.runBlocking(
////                new ParallelDeadLineWithAction(
////                        drive.actionBuilder(drive.pose)
////                                .strafeToSplineHeading(new Vector2d(-30,-3),Math.toRadians(180))
////                                .strafeToSplineHeading(new Vector2d(-24,-3),Math.toRadians(180))
////                                .build(),
////                        robot.liftAction(Constants.LIFT_ASCENT_START_POS)
////                )
////
////        );
////
////        Actions.runBlocking(
////                robot.liftAction(Constants.LIFT_ASCENT_END_POS)
////        );
////    }
////
////    public Action dropToBasketAction() {
////        return new ParallelDeadLineWithAction(
////                new SequentialAction(
////                        new SleepAction(0.2),
////                        new InstantAction(robot::liftRotateToReleasePos),
////                        new SleepAction(1.0),
////                        new InstantAction(robot::liftRotateToInitPos)
////                ),
////                robot.liftAction(Constants.LIFT_BASKET_HIGH_POS)
////        );
//    }
//
//}
