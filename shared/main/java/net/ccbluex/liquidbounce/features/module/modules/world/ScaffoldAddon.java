package net.ccbluex.liquidbounce.features.module.modules.world;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;

@ModuleInfo(name = "ScaffoldAddon", description = "idk????", category = ModuleCategory.WORLD)
public class ScaffoldAddon extends Module {

    public static final FloatValue calcForward1Value = new FloatValue("RotationStrafeCalcForward", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcStrafe1Value = new FloatValue("RotationStrafeCalcStrafe", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcForward2Value = new FloatValue("RotationStrafeCalcForward2", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcStrafe2Value = new FloatValue("RotationStrafeCalcStrafe2", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcForward3Value = new FloatValue("RotationStrafeCalcForward3", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcStrafe3Value = new FloatValue("RotationStrafeCalcStrafe3", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcForward4Value = new FloatValue("RotationStrafeCalcForward4", 0.5F, 0.3F, 0.8F);
    public static final FloatValue calcStrafe4Value = new FloatValue("RotationStrafeCalcStrafe4", 0.5F, 0.3F, 0.8F);

    @Override
    public void onEnable() {
        LiquidBounce.moduleManager.getModule("ScaffoldAddon").setState(false);
    }
}
