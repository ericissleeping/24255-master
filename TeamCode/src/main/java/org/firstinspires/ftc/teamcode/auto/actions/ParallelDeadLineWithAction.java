package org.firstinspires.ftc.teamcode.auto.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class ParallelDeadLineWithAction implements Action {

    private Action liftAction;
    private Action endAction;

    public ParallelDeadLineWithAction (Action endAction, Action liftAction){
        this.endAction = endAction;
        this.liftAction = liftAction;
//        this.actions.add(endAction);
    }


    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//        actions.forEach( action -> run(telemetryPacket));
        liftAction.run(telemetryPacket);
        return this.endAction.run(telemetryPacket);
    }

    @Override
    public void preview(@NonNull Canvas fieldOverlay) {
        liftAction.preview(fieldOverlay);
        endAction.preview(fieldOverlay);
    }
}
