package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import config.core.Robot;

public class ExtendDetect extends CommandBase {
    private final Robot robot;

    public ExtendDetect(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        robot.getE().toDetect();
    }
}
