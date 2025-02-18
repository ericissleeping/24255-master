package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import config.core.Robot;

public class ExtendZero extends CommandBase {
    private final Robot robot;

    public ExtendZero(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        robot.getE().toZero();
    }
}
