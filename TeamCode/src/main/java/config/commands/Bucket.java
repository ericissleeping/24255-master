package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class Bucket extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Bucket(Robot robot) {
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
                robot.getO().transfer();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 0.6) {
                    robot.getL().toHighBucket();
                    setState(3);
                }
                break;
            case 3:
                if (timer.getElapsedTimeSeconds() > 0.75) {
                    robot.getO().score();
                    setState(4);
                }
                break;
            case 4:
                if (timer.getElapsedTimeSeconds() > 0.6) {
                    robot.getO().transfer();
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

