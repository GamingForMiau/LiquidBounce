/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(name = "MatrixHOP", description = "Matrix BHOP by Sonya_ADM", category = ModuleCategory.MOVEMENT)
public class MatrixHop extends Module {

    public final BoolValue Fast = new BoolValue("Fast", true);

    public final BoolValue Strafe = new BoolValue("Strafe", false);

    public final FloatValue Timer1MaxY = new FloatValue("Timer1MaxY", 0.22F, 0.01F, 0.42F);
    public final FloatValue Timer3MaxY = new FloatValue("Timer3MaxY", 0.22F, 0.01F, 0.42F);
    public final FloatValue Fastdelay = new FloatValue("Fastdelay", 25F, 1F, 150F);
    public final FloatValue Fasttimer = new FloatValue("Fasttimer", 6.0F, 1.0F, 15.0F);
    public final FloatValue Timer1 = new FloatValue("Timer1", 1.12F, 0.1F, 15.0F);
    public final FloatValue Timer2 = new FloatValue("Timer2", 1.0F, 0.1F, 45.0F);
    public final FloatValue Timer3 = new FloatValue("Timer3", 1.0F, 0.1F, 45.0F);
    public final FloatValue ElseTimer1 = new FloatValue("ElseTimer1", 1.0F, 0.1F, 15.0F);
    public final FloatValue ElseTimer2 = new FloatValue("ElseTimer2", 1.0F, 0.1F, 15.0F);
    public final FloatValue ElseTimer3 = new FloatValue("ElseTimer3", 1.0F, 0.1F, 15.0F);
    public final IntegerValue Timer1Delay = new IntegerValue("Timer1Delay", 2, 1, 150);
    public final IntegerValue Timer2Delay = new IntegerValue("Timer2Delay", 2, 1, 150);
    public final IntegerValue Timer3Delay = new IntegerValue("Timer3Delay", 2, 1, 150);

    @EventTarget
    public void onDisable() {
        mc.getTimer().setTimerSpeed(1);
        mc.getThePlayer().setSpeedInAir(0.02F);
    }

    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        if(mc.getThePlayer().getHurtTime() <= 0){
            if (mc.getGameSettings().getKeyBindForward().isKeyDown() && !mc.getThePlayer().isSneaking()) {
                mc.getThePlayer().setSprinting(true);
                if (mc.getThePlayer().getOnGround()){
                    if(!mc.getGameSettings().getKeyBindJump().isKeyDown()){
                        mc.getThePlayer().jump();
                    }
                    mc.getThePlayer().setMotionZ(mc.getThePlayer().getMotionZ() * 1.01D);
                    mc.getThePlayer().setMotionX(mc.getThePlayer().getMotionX() * 1.01D);
                }
                if(Fast.get()){
                    if(mc.getThePlayer().getTicksExisted() % Fastdelay.get() == 0){
                        mc.getTimer().setTimerSpeed(Fasttimer.get());
                    }else{
                        if(mc.getThePlayer().getMotionY() < Timer1MaxY.get()){
                            if(mc.getThePlayer().getTicksExisted() % Timer1Delay.get() == 0){
                                mc.getTimer().setTimerSpeed(Timer1.get());
                            }else{
                                mc.getTimer().setTimerSpeed(ElseTimer1.get());
                            }
                        }else if(mc.getThePlayer().getMotionY() > Timer3MaxY.get()){
                            if(mc.getThePlayer().getTicksExisted() % Timer3Delay.get() == 0){
                                mc.getTimer().setTimerSpeed(Timer3.get());
                            }else{
                                mc.getTimer().setTimerSpeed(ElseTimer3.get());
                            }
                        }else if(mc.getThePlayer().getTicksExisted() % Timer2Delay.get() == 0){
                            mc.getTimer().setTimerSpeed(Timer2.get());
                        }else{
                            mc.getTimer().setTimerSpeed(ElseTimer2.get());
                        }
                    }
                }else{

                    if(mc.getThePlayer().getMotionY() < Timer1MaxY.get()){
                        if(mc.getThePlayer().getTicksExisted() % Timer1Delay.get() == 0){
                            mc.getTimer().setTimerSpeed(Timer1.get());
                        }else{
                            mc.getTimer().setTimerSpeed(ElseTimer1.get());
                        }
                    }else if(mc.getThePlayer().getMotionY() > Timer3MaxY.get()){
                        if(mc.getThePlayer().getTicksExisted() % Timer3Delay.get() == 0){
                            mc.getTimer().setTimerSpeed(Timer3.get());
                        }else{
                            mc.getTimer().setTimerSpeed(ElseTimer3.get());
                        }
                    }else if(mc.getThePlayer().getTicksExisted() % Timer2Delay.get() == 0){
                        mc.getTimer().setTimerSpeed(Timer2.get());
                    }else{
                        mc.getTimer().setTimerSpeed(ElseTimer2.get());
                    }
                }
            }
        }
        if(Strafe.get()) {
            MovementUtils.strafe();
        }
    }
}
