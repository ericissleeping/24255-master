package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import config.core.Robot;

public class restExtend extends CommandBase {
    private final Robot robot;

    public restExtend(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        robot.getE().changeMode();
    }
}
