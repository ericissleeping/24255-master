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
                    setState(-1);
                }
                else{
                    setState(1);
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

