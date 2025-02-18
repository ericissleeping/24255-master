package org.firstinspires.ftc.teamcode.auto.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;

import org.firstinspires.ftc.teamcode.auto.RobotHardwareAuto;

public class ExtendAction implements Action {
    private RobotHardwareAuto robot;
    private double beginTs = -1;

    public ExtendAction(RobotHardwareAuto robot) {
        this.robot = robot;
        robot.setExtendMotorModeToRunWithoutEncoder();
    }

    @Override
    public void preview(@NonNull Canvas fieldOverlay) {
        Action.super.preview(fieldOverlay);
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        double t;
        if (beginTs < 0) {
            beginTs = Actions.now();
            t = 0;
        } else {
            t = Actions.now() - beginTs;
        }
        robot.extendIn();
        return !robot.getExtendLimitStatus() || t < 1.3;
    }
}
