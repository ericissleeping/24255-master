package config.subsystems;

import static config.core.RobotConstants.*;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.arcrobotics.ftclib.command.*;


import org.firstinspires.ftc.robotcore.external.Telemetry;



public class Intake {

    public enum intakeState {
        AVOID, TRANSFER, HOVER,COLLECT, HOVERDETECT
    }
    public enum intakePivot{
        HOR,VER, DIA
    }
    public enum clawState{
        OPEN,CLOSE
    }



    public Servo intakeRotate;
    public Servo intakeFlip;
    public static Servo intakePivot;
    public Servo intakeClaw;
    public static intakeState intakeState;
    public intakePivot intakeDirection;
    public clawState clawState;
    ColorSensor color;
    private Telemetry telemetry;


    public Intake(HardwareMap hardwareMap, Telemetry telemetry) {
        intakeRotate = hardwareMap.get(Servo.class, "intakeRotate");
        intakeFlip = hardwareMap.get(Servo.class, "intakeFlip");
        intakePivot = hardwareMap.get(Servo.class, "clawRotate");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        color=hardwareMap.get(ColorSensor.class,"color");
        this.telemetry = telemetry;
    }

    public void setRotateState(intakeState state) {
        if (state == intakeState.TRANSFER) {
            intakeRotate.setPosition(INTAKE_ROTATE_TRANSFER_POS);
            intakeFlip.setPosition(INTAKE_FLIP_TRANSFER_POS);
            this.intakeState = intakeState.TRANSFER;
        }
        else if (state == intakeState.AVOID){
            intakeRotate.setPosition(INTAKE_ROTATE_AVOID_POS);
            intakeFlip.setPosition(INTAKE_FLIP_AVOID_POS);
            this.intakeState = intakeState.AVOID;
        }
        else if (state == intakeState.HOVER){
            intakeRotate.setPosition(INTAKE_ROTATE_HOVER_POS);
            intakeFlip.setPosition(INTAKE_FLIP_HOVER_POS);
            this.intakeState = intakeState.HOVER;
        }
        else if (state == intakeState.COLLECT){
            intakeRotate.setPosition(INTAKE_ROTATE_COLLECT_POS);
            intakeFlip.setPosition(INTAKE_FLIP_COLLECT_POS);
            this.intakeState = intakeState.COLLECT;
        }
        else if (state == intakeState.HOVERDETECT) {
            intakeRotate.setPosition(INTAKE_ROTATE_HOVER_DETECT_POS);
            intakeFlip.setPosition(INTAKE_FLIP_HOVER_DETECT_POS);
            this.intakeState = intakeState.COLLECT;
        }
    }

    public void setIntakePivot(intakePivot state){
        if (state == intakeDirection.HOR){
            intakePivot.setPosition(INTAKE_CLAW_HOR_POS);
            this.intakeDirection= intakeDirection.HOR;
        }
        else if (state==intakeDirection.VER){
            intakePivot.setPosition(INTAKE_CLAW_VER_POS);
            this.intakeDirection=intakeDirection.VER;
        }
        else if (state==intakeDirection.DIA){
            intakePivot.setPosition(INTAKE_CLAW_DIA_POS);
            this.intakeDirection=intakeDirection.DIA;
        }
    }

    public void setClawState(clawState state){
        if (state == clawState.OPEN){
            intakeClaw.setPosition(INTAKE_CLAW_OPEN_POS);
        }
        else if (state == clawState.CLOSE){
            intakeClaw.setPosition(INTAKE_CLAW_CLOSE_POS);
        }
    }

    public void transfer(){
        setRotateState(intakeState.TRANSFER);
        setIntakePivot(intakeDirection.HOR);
    }
    public void avoid(){
        setRotateState(intakeState.AVOID);
        setIntakePivot(intakeDirection.HOR);
    }
    public void hover(){
        setRotateState(intakeState.HOVER);
    }
    public void hoverDetect(){
        setRotateState(intakeState.HOVERDETECT);
    }
    public void collect(){
        setRotateState(intakeState.COLLECT);
    }
    public void clawHor(){
        setIntakePivot(intakeDirection.HOR);
    }
    public void clawVer(){
        setIntakePivot(intakeDirection.VER);
    }
    public void clawDia(){
        setIntakePivot(intakeDirection.DIA);
    }
    public void clawOpen(){
        setClawState(clawState.OPEN);
    }
    public void clawClose(){
        setClawState(clawState.CLOSE);
    }
    public intakeState getIntakeState(){
        return intakeState;
    }
    public intakePivot getIntakeDirection(){
        return intakeDirection;
    }
    public boolean detect(){
        if (color.red()>100){
            return true;
        }
        return false;

    }

    public void telemetry() {
        telemetry.addData("Intake State: ", intakeState);
        telemetry.addData("Intake Claw Direction", intakeDirection);
    }

    public void periodic() {
        telemetry();
    }
}
