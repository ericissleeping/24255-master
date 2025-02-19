package config.core;

import static config.core.Opmode.*;
import static config.subsystems.Intake.intakePivot.HOR;
import static config.subsystems.Intake.intakeState.AVOID;
import static config.subsystems.Intake.intakeState.COLLECT;
import static config.subsystems.Intake.intakeState.HOVER;
import static config.subsystems.Intake.intakeState.TRANSFER;
import static config.subsystems.Lift.lockState.OPEN;

import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

import config.pedro.constants.FConstants;
import config.pedro.constants.LConstants;
import config.subsystems.Extend;
import config.subsystems.Intake;
import config.subsystems.Lift;
import config.subsystems.Outtake;
import config.subsystems.PinPoint;


public class Robot {
    private HardwareMap h;
    private Telemetry t;
    private Gamepad g1a, g2a, g1, g2, p1, p2;
    private Alliance a;
    private Follower f;
    private Lift l;
    private Extend e;
    private Intake i;
    private Outtake o;
    private PinPoint pp;
    public Timer iTimer,tTimer,lTimer;
    public int iState=0, tState=0,lstate=0;

    public double baseSpeed = 1;
    private double turnSpeed=0.4;
    private boolean returnBack=false;
    private boolean lockIntake=false;
    private boolean liftManual=true;


    private Opmode op = TELEOP;

    public static Pose autoEndPose = new Pose(0,0,0);

    public Robot(HardwareMap h, Telemetry t, Gamepad g1a, Gamepad g2a, Alliance a, Pose startPose) {
        this.op = TELEOP;
        this.h = h;
        this.t = t;
        this.g1a = g1a;
        this.g2a = g2a;
        this.a = a;

        this.g1 = new Gamepad();
        this.g2 = new Gamepad();
        this.p1 = new Gamepad();
        this.p2 = new Gamepad();

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        l = new Lift(this.h,this.t);
        e = new Extend(this.h,this.t);
        i= new Intake(this.h,this.t);
        o=new Outtake(this.h,this.t);
        pp=new PinPoint(this.h,this.t);

        iTimer=new Timer();
        tTimer=new Timer();
        lTimer=new Timer();


        List<LynxModule> allHubs = h.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    public Robot(HardwareMap h, Telemetry t, Alliance a, Pose startPose) {
        this.op = AUTONOMOUS;
        this.h = h;
        this.t = t;
        this.a = a;

        Constants.setConstants(FConstants.class, LConstants.class);

        f = new Follower(this.h);
        f.setStartingPose(startPose);

        e = new Extend(this.h,this.t);
        l = new Lift(this.h,this.t);
        i = new Intake(this.h,this.t);
        o = new Outtake(this.h,this.t);


        List<LynxModule> allHubs = h.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    public void tPeriodic() {
        updateControls();
        collect();
        hover();
        transfer();
        endSpecimen();

//        t.addData("path", f.getCurrentPath());
//        f.telemetryDebug(t);
// ?????????????
        l.periodic();
        e.periodic();
        i.periodic();
        o.periodic();
        f.update();
        t.update();
    }

    public void aPeriodic() {
        collect();
        transfer();

        l.periodic();
        e.periodic();
        i.periodic();
        o.periodic();
        f.update();
        t.update();
    }



    public void tStart() {
        intakeInit();
        outtakeInit();
        f.startTeleopDrive();
    }

    public void stop() {
        autoEndPose = f.getPose();
    }



    public void updateControls() {
        p1.copy(g1);
        p2.copy(g2);
        g1.copy(g1a);
        g2.copy(g2a);

        //hover and collect

        if(g1.right_bumper && !p1.right_bumper){
            if (i.getIntakeState() == AVOID || i.getIntakeState() == TRANSFER){
                startHover();
                if (returnBack){
                    i.clawHor();
                }
                else{
                    i.clawVer();
                }
            }
            else if (i.getIntakeState() == HOVER){
                startCollect();
            }
        }

        //transfer and avoid

        if (g1.left_bumper && !p1.left_bumper){
            if (i.getIntakeState() == HOVER){
                if (i.getIntakeDirection()==HOR){
                    returnBack=true;
                }
                else{
                    returnBack=false;
                }
                i.transfer();
                i.clawVer();
            }
            else if (i.getIntakeState() == TRANSFER){
                startTransfer();
            }
        }

        //turn claw direction
        if (g1.y && !p1.y){
            if (i.getIntakeDirection() == HOR){
                i.clawVer();
            }
            else{
                i.clawHor();
            }
        }

        //extend lock
        if (g1.b && !p1.b){
            lockIntake=!lockIntake;
        }
        if (lockIntake){
            e.extendToPos(300);
        }
        else{
            if (i.getIntakeState() == HOVER || i.getIntakeState() == COLLECT){
                baseSpeed=0.4;
                turnSpeed=0.15;
                e.manualSlowly(g1.right_trigger,g1.left_trigger);//extend control
            }
            else{
                if (g2.right_trigger>0){
                    baseSpeed=0.2;
                    turnSpeed=0.2;
                }
                else{
                    baseSpeed=1;
                    turnSpeed=0.4;
                }
                e.manual(g1.right_trigger,g1.left_trigger);//extend control
            }
        }

        //reset pp
        if (g1.back && !p1.back){
            pp.resetImu();
        }
        //claw status
        if (g1.x && !p1.x){
            if (i.getClawState() == Intake.clawState.OPEN){
                i.clawClose();
            }
            else{
                i.clawOpen();
            }
        }



        l.manual(-g2.left_stick_y);//lift control



        if  (Math.abs(g2.left_stick_y) > 0.1 && Math.abs(p2.left_stick_y) < 0.1){
            l.setLiftManual(true);
        }

        l.manual(-g2.left_stick_y);//lift control

        if (g2.dpad_left && !p2.dpad_left){
            l.setLiftManual(false);
            l.toChamberStart();
        }
        if (g2.dpad_right && !p2.dpad_right){
            l.setLiftManual(false);
            startEndSpecimen();
        }
        if (g2.dpad_down && !p2.dpad_down){
            l.setLiftManual(false);
            l.toZero();
        }









        //basket
        if (g2.left_bumper){
            o.score();
        }
        if (g2.right_bumper){
            o.transfer();
        }

        //specimen claw
        if (g2.a && !p2.a){
            if (o.getSpecimenClawState()== Outtake.specimenClawState.CLOSE){
                o.openSpecimenClaw();
            }
            else{
                o.closeSpecimenClaw();
            }
        }


        //Lock Control
        if (g2.y && !p2.y){
            if (l.getLockState()== OPEN){
                l.setLockOff();
            }
            else{
                l.setLockOn();
            }
        }





        f.setTeleOpMovementVectors( -g1.left_stick_y*baseSpeed,  -g1.left_stick_x*baseSpeed, -g1.right_stick_x*turnSpeed, false);//chassis control

    }

    private void collect(){
        switch(iState){
            case 1:
                i.clawOpen();
                i.collect();
                setIntakeState(2);
                break;
            case 2:
                if(iTimer.getElapsedTimeSeconds() > 0.25) {
                    i.clawClose();
                    setIntakeState(3);
                }
                break;
            case 3:
                if(iTimer.getElapsedTimeSeconds() > 0.3) {
                    i.hover();
                    setIntakeState(0);
                }
                break;
        }
    }

    private void transfer(){
        switch (tState){
            case 1:
                i.clawOpen();
                setTransferState(2);
                break;
            case 2:
                if (tTimer.getElapsedTimeSeconds()>0.3){
                    i.avoid();
                    setTransferState(0);
                }
                break;
        }
    }

    private void hover(){
        switch (iState){
            case 4:
                i.hover();
                setIntakeState(5);
                break;
            case 5:
                if (iTimer.getElapsedTimeSeconds()>0.4){
                    i.clawOpen();
                    setIntakeState(-1);
                }
                break;
        }
    }

    public void endSpecimen(){
        switch (lstate){
            case 1:
                l.toChamberEnd();
                setSpecimenState(2);
                break;
            case 2:
                if (lTimer.getElapsedTimeSeconds()>0.3){
                    o.openSpecimenClaw();
                    setSpecimenState(0);
                }
                break;
        }
    }

    public void setIntakeState(int x){
        iState=x;
        iTimer.resetTimer();
    }
    public void setTransferState(int x){
        tState=x;
        tTimer.resetTimer();

    }

    public void setSpecimenState(int x){
        lstate=x;
        lTimer.resetTimer();

    }

    public void startCollect(){
        setIntakeState(1);
    }
    public void startTransfer(){
        setTransferState(1);
    }
    public void startHover(){
        setIntakeState(4);
    }

    public void startEndSpecimen(){
        setSpecimenState(1);
    }

    public void intakeInit(){
        i.avoid();
        i.clawHor();
        i.clawOpen();
    }

    public void outtakeInit(){
        o.transfer();
        o.closeSpecimenClaw();
    }

    public void extendInit(){
        e.toZero();
    }

    public Telemetry getT() {
        return t;
    }

    public Follower getF() {
        return f;
    }

    public Intake getI() {
        return i;
    }

    public Lift getL() {
        return l;
    }
    public Extend getE(){
        return e;
    }

    public Outtake getO() {
        return o;
    }
}
