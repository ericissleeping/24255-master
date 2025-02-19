package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class Detect extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Detect(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        setState(1);
    }

    @Override
    public void execute() {
        switch (state) {
            case 1:
                robot.getI().hoverDetect();
                robot.getI().clawHor();
                setState(2);
                break;
            case 2:
                if (robot.getI().detect()){
                    setState(3);
                }
                else{
                    setState(1);
                }
            case 3:
                if (timer.getElapsedTimeSeconds()>0.6){
                    robot.getI().clawOpen();
                    robot.getI().collect();
                    //robot.getI().clawClose();
                    setState(4);
                }
                break;
            case 4:
                if(timer.getElapsedTimeSeconds() > 0.35) {
                    robot.getI().clawClose();
                    setState(5);
                }
                break;
            case 5:
                if (timer.getElapsedTimeSeconds()>0.35){
                    robot.getI().hover();
                    setState(6);
                }
                break;
            case 6:
                if (timer.getElapsedTimeSeconds()>0.35){
                    robot.getI().transfer();
                    robot.getI().clawVer();
                    setState(7);
                }
                break;
            case 7:
                if (timer.getElapsedTimeSeconds()>0.4){
                    robot.getI().clawOpen();
                    setState(8);
                }
                break;
            case 8:
                if (timer.getElapsedTimeSeconds()>0.3){
                    robot.getI().avoid();
                    setState(-1);
                }
        }
    }
    @Override
    public boolean isFinished() {
        return state == -1;
    }

    public void setState(int x) {
        state = x;
        timer.resetTimer();
    }

}

