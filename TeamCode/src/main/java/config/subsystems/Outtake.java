package config.subsystems;

import static config.core.RobotConstants.*;



import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.arcrobotics.ftclib.command.*;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Outtake {
    public enum basketState {
        TRANSFER, SCORE
    }

    public enum specimenClawState {
        OPEN, CLOSE
    }

    public Servo basketRotate;
    public Servo specimenClaw;
    public basketState basketState;
    public static specimenClawState specimenClawState;
    private Telemetry telemetry;

    public Outtake(HardwareMap hardwareMap, Telemetry telemetry) {
        basketRotate = hardwareMap.get(Servo.class, "liftRotate");;
        specimenClaw = hardwareMap.get(Servo.class, "specimenClaw");
        this.telemetry = telemetry;
    }

    public void setBasketState(basketState state){
        if (state == basketState.TRANSFER){
            basketRotate.setPosition(BASKET_ROTATE_TRANSFER_POS);
            this.basketState= basketState.TRANSFER;
        }
        else if (state==basketState.SCORE){
            basketRotate.setPosition(BASKET_ROTATE_SCORE_POS);
            this.basketState= basketState.SCORE;
        }
    }
    public void setSpecimenClawState(specimenClawState state){
        if (state == specimenClawState.OPEN){
            specimenClaw.setPosition(SPECIMEN_CLAW_OPEN_POS);
            this.specimenClawState= specimenClawState.OPEN;
        }
        else if (state == specimenClawState.CLOSE){
            specimenClaw.setPosition(SPECIMEN_CLAW_CLOSE_POS);
            this.specimenClawState= specimenClawState.CLOSE;
        }
    }
    public void transfer(){
        setBasketState(basketState.TRANSFER);
    }

    public void score(){
        setBasketState(basketState.SCORE);
    }

    public void openSpecimenClaw(){
        setSpecimenClawState(specimenClawState.OPEN);
    }
    public void closeSpecimenClaw(){
        setSpecimenClawState(specimenClawState.CLOSE);
    }

    public Outtake.specimenClawState getSpecimenClawState() {
        return specimenClawState;
    }

    public void telemetry() {
        telemetry.addData("Basket State: ", basketState);
    }

    public void periodic() {
        telemetry();
    }
}



