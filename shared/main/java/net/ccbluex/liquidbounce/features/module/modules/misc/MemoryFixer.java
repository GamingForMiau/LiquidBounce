package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;

@ModuleInfo(name = "MemoryFix", description = "Fixes known memory bugs.", category = ModuleCategory.MISC)
public class MemoryFixer extends Module {

    private final MSTimer timer = new MSTimer();

    @EventTarget
    public void onUpdate(UpdateEvent event){
        if(timer.hasTimePassed(3000L)) {
            Runtime.getRuntime().gc();
            System.gc();
            timer.reset();
        }
    }
}
