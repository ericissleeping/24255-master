package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class specimenScore extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public specimenScore(Robot robot) {
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
                robot.getL().toChamberStart();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds() > 2.5) {
                    robot.getL().toChamberEnd();
                    setState(3);
                }
                break;
            case 3:
                if (timer.getElapsedTimeSeconds() > 0.2) {
                    robot.getO().openSpecimenClaw();
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

