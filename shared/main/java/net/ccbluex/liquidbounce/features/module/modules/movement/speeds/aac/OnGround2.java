package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;

public class OnGround2 extends SpeedMode {
    public OnGround2() {
        super("OnGround2");
    }

    private final MSTimer test = new MSTimer();

    @Override
    public void onMotion() {
    }

    @Override
    public void onUpdate() {
        if(MovementUtils.isMoving()) {
            if(mc.getThePlayer().getOnGround()) {
                if (test.hasTimePassed(140L)) {
                    MovementUtils.strafe(2F);
                    mc.getThePlayer().setMotionY(mc.getThePlayer().getMotionY() - 2);
                    test.reset();
                } else {
                    MovementUtils.strafe(0F);
                }
            }
        }
    }

    @Override
    public void onMove(MoveEvent event) {
    }
}
