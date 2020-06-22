package com.github.jp.erudo.eanticheat.processor;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.utils.user.User;

import io.github.erudo.eac.protocol.api.Packet;
import io.github.erudo.eac.protocol.packet.in.WrappedInUseEntityPacket;
import io.github.erudo.eac.protocol.packet.out.WrappedOutVelocityPacket;
import lombok.Setter;

@Setter
public class CombatProcessor {
	private User user;

    public void update(Object packet, String type) {
        if (user != null) {

            if (type.equalsIgnoreCase(Packet.Server.ENTITY_VELOCITY)) {
                WrappedOutVelocityPacket wrappedOutVelocityPacket = new WrappedOutVelocityPacket(packet, user.getPlayer());

                if (wrappedOutVelocityPacket.getId() == user.getPlayer().getEntityId()) {
                    user.setLastVelocity(System.currentTimeMillis());

                    double velocityX = wrappedOutVelocityPacket.getX();
                    double velocityY = wrappedOutVelocityPacket.getY();
                    double velocityZ = wrappedOutVelocityPacket.getZ();

                    double horizontal = Math.hypot(velocityX, velocityZ);
                    double vertical = Math.pow(velocityY + 2.0, 2.0) * 5.0;

                    user.setHorizontalVelocity(horizontal);
                    user.setVerticalVelocity(vertical);

                    if (user.isOnGround() && user.getPlayer().getLocation().getY() % 1.0 == 0.0) {
                        user.setVerticalVelocity(velocityY);
                    }
                }
            }
            if (type.equalsIgnoreCase(Packet.Client.USE_ENTITY)) {

                WrappedInUseEntityPacket wrappedInUseEntityPacket = new WrappedInUseEntityPacket(packet, user.getPlayer());

                if (wrappedInUseEntityPacket.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK && wrappedInUseEntityPacket.getEntity() != null) {

                    if (wrappedInUseEntityPacket.getEntity() instanceof Player) {
                        User attackedUser = EAC.userManager.getUser(wrappedInUseEntityPacket.getEntity().getUniqueId());
                        if (attackedUser != null) user.setTargetUser(attackedUser);
                    }

                    if (user.getLastEntityAttacked() != null) {
                        if (user.getLastEntityAttacked() != wrappedInUseEntityPacket.getEntity()) {
                            user.constantEntityTicks = 0;
                        } else {
                            user.constantEntityTicks++;
                        }
                    }

                    if (wrappedInUseEntityPacket.getEntity() instanceof Player || wrappedInUseEntityPacket.getEntity() instanceof Villager) {
                        user.setLastEntityAttacked(wrappedInUseEntityPacket.getEntity());
                        user.setLastUseEntityPacket(System.currentTimeMillis());
                    }
                }
            }
        }
    }
}
