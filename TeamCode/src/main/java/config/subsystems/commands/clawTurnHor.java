package config.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import config.core.Robot;

public class clawTurnHor extends CommandBase{
    private final Robot robot;

    public clawTurnHor(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void initialize() {
        robot.getI().clawVer();
    }
}
