package config.core;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {

    // Outtake
    public static double outtakeGrabClose = 0.04;
    public static double outtakeGrabOpen = 0.2;
    public static double outtakeRotateTransfer = 0.265; //.775
    public static double outtakeRotateSpecimenGrab180 = .56;
    public static double outtakeRotateSpecimenGrab0 = 0.56;
    public static double outtakeRotateLeftScore = 0.8;
    public static double outtakeRotateRightScore = 0.32;
    public static double outtakeRotateLeftSpecimenScore180 = 0.68;
    public static double outtakeRotateRightSpecimenScore180 = .2;
    public static double outtakeRotateLeftSpecimenScore0 = 0.68;
    public static double outtakeRotateRightSpecimenScore0 = .2;
    public static double outtakePivotTransfer= 0.8; //0
    public static double outtakePivotScore = 0.53;
    public static double outtakePivotSpecimenGrab180 = 0.97;
    public static double outtakePivotSpecimenScore180 = 0.41;

    public static double outtakePivotSpecimenGrab0 = 0.97;
    public static double outtakePivotSpecimenScore0 = 0.41;

    // Intake

    //rotate
    public static double INTAKE_ROTATE_TRANSFER_POS = .054;
    public static final double INTAKE_ROTATE_COLLECT_POS = .635;
    public static final double INTAKE_ROTATE_AVOID_POS = .128;
    public static final double INTAKE_ROTATE_HOVER_POS = .527;
    public static final double INTAKE_ROTATE_HOVER_DETECT_POS = .621;
    //flip
    public static final double INTAKE_FLIP_TRANSFER_POS = 0.768;
    public static final double INTAKE_FLIP_HOVER_POS = .214;
    public static final double INTAKE_FLIP_COLLECT_POS = .282;
    public static final double INTAKE_FLIP_AVOID_POS = .638;
    public static final double INTAKE_FLIP_HOVER_DETECT_POS = .626;
    //claw pivot
    public static final double INTAKE_CLAW_HOR_POS = .412;
    public static final double INTAKE_CLAW_VER_POS = .073;
    public static final double INTAKE_CLAW_DIA_POS = 0.604;
    //claw grab
    public static final double INTAKE_CLAW_OPEN_POS = .533;
    public static final double INTAKE_CLAW_CLOSE_POS = .231;

    //Basket
    public static final double BASKET_ROTATE_TRANSFER_POS = 0.363;
    public static final double BASKET_ROTATE_SCORE_POS = 0.8;
    //specimen claw
    public static final double SPECIMEN_CLAW_OPEN_POS = 0.410;
    public static final double SPECIMEN_CLAW_CLOSE_POS = 0.755;




    // Lift Positions
    public static int liftToZero = 0;
    public static int liftToHighBucket = 1680;
    public static final int LIFT_SPECIMEN_START_POS = 1150;
    public static final int LIFT_SPECIMEN_END_POS = 870;
    public static int liftToTransfer = 200;
    public static int liftToPark = 0;

    // Extend Positions
    public static double extendZero = 0.525;
    public static double extendFull = .3;

    //Lock Positions
    public static final double LEFT_LOCK_ON = 0.522;
    public static final double LEFT_LOCK_OFF =  0.766;
    public static final double RIGHT_LOCK_ON = 0.710;
    public static final double RIGHT_LOCK_OFF = 0.311;

}