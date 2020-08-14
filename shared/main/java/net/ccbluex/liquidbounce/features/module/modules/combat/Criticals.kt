/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.network.play.client.CPacketPlayer

@ModuleInfo(name = "Criticals", description = "Automatically deals critical hits.", category = ModuleCategory.COMBAT)
class Criticals : Module() {

    val modeValue = ListValue("Mode", arrayOf("Packet", "NoGround", "Hop", "TPHop", "Jump", "LowJump", "Shit"), "Packet")
    val delayValue = IntegerValue("Delay", 0, 0, 500)
    val noGroundModeValue = ListValue("NoGroundMode", arrayOf("Normal", "Normal2", "NCP"), "Normal")
    val packetModeValue = ListValue("PacketMode", arrayOf("Normal", "Normal2", "Normal3", "OldNCP", "FakeJump"), "OldNCP")
    private val hurtTimeValue = IntegerValue("HurtTime", 10, 0, 10)

    val msTimer = MSTimer()

    override fun onEnable() {
        val thePlayer = mc.thePlayer ?: return

        if (noGroundModeValue.get().equals("Normal", ignoreCase = true) && modeValue.get().equals("NoGround", ignoreCase = true) || noGroundModeValue.get().equals("NCP", ignoreCase = true) && modeValue.get().equals("NoGround", ignoreCase = true)) {
            thePlayer.jump()
        }
    }

    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (classProvider.isEntityLivingBase(event.targetEntity)) {
            val thePlayer = mc.thePlayer ?: return
            val entity = event.targetEntity!!.asEntityLivingBase()

            if (!thePlayer.onGround || thePlayer.isOnLadder || thePlayer.isInWeb || thePlayer.isInWater ||
                    thePlayer.isInLava || thePlayer.ridingEntity != null || entity.hurtTime > hurtTimeValue.get() ||
                    LiquidBounce.moduleManager[Fly::class.java]!!.state || !msTimer.hasTimePassed(delayValue.get().toLong()))
                return

            val x = thePlayer.posX
            val y = thePlayer.posY
            val z = thePlayer.posZ

            when (modeValue.get().toLowerCase()) {
                "packet" -> {
                    packetModeValue.get()
                }
                "lowjump" -> thePlayer.motionY = 0.3425
                "shit" -> {
                    if (thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown && !MovementUtils.isMoving) {
                        thePlayer.motionY = 0.20
                    }
                }
                "hop" -> {
                    thePlayer.motionY = 0.1
                    thePlayer.fallDistance = 0.1f
                    thePlayer.onGround = false
                }

                "tphop" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.02, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.01, z, false))
                    thePlayer.setPosition(x, y + 0.01, z)
                }
                "jump" -> thePlayer.motionY = 0.42
            }

            when (packetModeValue.get().toLowerCase()) {
                "normal" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.0625, z, true))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 1.1E-5, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y, z, false))
                    thePlayer.onCriticalHit(entity)
                }
                "oldncp" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY + 0.1625, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY + 4.0E-6, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY + 1.0E-6, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(false))
                    thePlayer.onCriticalHit(entity)
                }
                "fakejump" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY + 0.42, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
                    thePlayer.onCriticalHit(entity)
                }
                "normal2" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.11, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.1100013579, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.0000013579, z, false))
                    thePlayer.onCriticalHit(entity)
                }
                "normal3" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY + 0.05000000074505806, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY + 0.012511000037193298, thePlayer.posZ, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
                }
            }

            when (noGroundModeValue.get().toLowerCase()) {
                "normal2" -> {
                    thePlayer.onCriticalHit(entity)
                }
            }

            msTimer.reset()
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val thePlayer = mc.thePlayer ?: return

        val packet = event.packet

        if (packet is CPacketPlayer && modeValue.get().equals("NoGround", ignoreCase = true) && noGroundModeValue.get().equals("Normal", ignoreCase = true))
            packet.onGround = false
        if(packet is CPacketPlayer && modeValue.get().equals("NoGround", ignoreCase = true) && noGroundModeValue.get().equals("NCP", ignoreCase = true) && !thePlayer.onGround && !thePlayer.isCollidedVertically && thePlayer.fallDistance < 2)
            packet.onGround = true
    }

    override val tag: String?
        get() = modeValue.get()
}

