package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class clawGrab extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public clawGrab(Robot robot) {
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
                robot.getI().hover();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds()>0.3){
                    robot.getI().clawOpen();
                    robot.getI().collect();
                    //robot.getI().clawClose();
                    setState(3);
                }
                break;
            case 3:
                if(timer.getElapsedTimeSeconds() > 0.35) {
                    robot.getI().clawClose();
                    setState(4);
                }
                break;
            case 4:
                if (timer.getElapsedTimeSeconds()>0.35){
                    robot.getI().hover();
                    setState(-1);
                }
                break;
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

