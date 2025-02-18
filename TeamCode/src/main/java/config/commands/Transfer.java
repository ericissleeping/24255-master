package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class Transfer extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public Transfer(Robot robot) {
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
                if (timer.getElapsedTimeSeconds() > 0.2) {
                    robot.getL().toZero();
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

