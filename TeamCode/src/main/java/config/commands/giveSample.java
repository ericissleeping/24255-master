package config.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.util.Timer;

import config.core.Robot;

public class giveSample extends CommandBase {
    private final Robot robot;

    private int state = 0;
    private Timer timer = new Timer();

    public giveSample(Robot robot) {
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
                robot.getE().toFull();
                setState(2);
                break;
            case 2:
                if (timer.getElapsedTimeSeconds()>0.4){
                    robot.getI().clawOpen();
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

