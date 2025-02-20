package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import config.core.Robot;

public class clawOpen extends CommandBase{
    private final Robot robot;

    public clawOpen(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        robot.getI().clawOpen();
    }
}
