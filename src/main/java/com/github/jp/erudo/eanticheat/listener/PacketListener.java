package com.github.jp.erudo.eanticheat.listener;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.event.EACEvent;
import com.github.jp.erudo.eanticheat.event.EACListener;
import com.github.jp.erudo.eanticheat.event.Listen;
import com.github.jp.erudo.eanticheat.event.events.PacketEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.AttackEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.ChatEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.DigEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.EntityActionEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.EntityInteractEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.FlyingPacketEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.PingEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.PlaceEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.SwingEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.TeleportEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.VelocityEvent;
import com.github.jp.erudo.eanticheat.utils.user.User;

import io.github.erudo.eac.protocol.api.Packet;
import io.github.erudo.eac.protocol.packet.in.WrappedInBlockDigPacket;
import io.github.erudo.eac.protocol.packet.in.WrappedInBlockPlacePacket;
import io.github.erudo.eac.protocol.packet.in.WrappedInChatPacket;
import io.github.erudo.eac.protocol.packet.in.WrappedInEntityActionPacket;
import io.github.erudo.eac.protocol.packet.in.WrappedInFlyingPacket;
import io.github.erudo.eac.protocol.packet.in.WrappedInUseEntityPacket;
import io.github.erudo.eac.protocol.packet.out.WrappedOutPositionPacket;
import io.github.erudo.eac.protocol.packet.out.WrappedOutVelocityPacket;

public class PacketListener implements EACListener {
	@Listen
    public void onPacket(PacketEvent e) {
        User user = EAC.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {

            user.getMovementProcessor().update(e.getPacket(), e.getType());

            user.getLagProcessor().update(e.getPacket(), e.getType());

            user.getCombatProcessor().update(e.getPacket(), e.getType());

            //Wrap Packet in Event
            EACEvent checkEvent = e;
            if(e.getType().equalsIgnoreCase(Packet.Client.FLYING) || e.getType().equalsIgnoreCase(Packet.Client.LOOK)
                    || e.getType().equalsIgnoreCase(Packet.Client.POSITION)
                    || e.getType().equalsIgnoreCase(Packet.Client.POSITION_LOOK)) {
                WrappedInFlyingPacket packet = new WrappedInFlyingPacket(e.getPacket(), user.getPlayer());
                checkEvent = new FlyingPacketEvent(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(),
                        packet.getPitch(),
                        packet.isGround(),
                        packet.isPos(),
                        packet.isLook());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)) {
                checkEvent = new SwingEvent();
            }else if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
                WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
                if(packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK) {
                    checkEvent = new AttackEvent(packet.getId(), packet.getEntity());
                }else if(packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.INTERACT
                        || packet.getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.INTERACT_AT) {
                    checkEvent = new EntityInteractEvent(packet.getId(), packet.getEntity());
                }else checkEvent = e;
            }else if(e.getType().equalsIgnoreCase(Packet.Client.CHAT)) {
                WrappedInChatPacket packet = new WrappedInChatPacket(e.getPacket(), e.getPlayer());
                checkEvent = new ChatEvent(packet.getMessage());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.BLOCK_DIG)) {
                WrappedInBlockDigPacket packet = new WrappedInBlockDigPacket(e.getPacket(), e.getPlayer());
                checkEvent = new DigEvent(packet.getPosition(), packet.getDirection(), packet.getAction());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.ENTITY_ACTION)) {
                WrappedInEntityActionPacket packet = new WrappedInEntityActionPacket(e.getPacket(), e.getPlayer());
                checkEvent = new EntityActionEvent(packet.getAction());
            }else if(e.getType().equalsIgnoreCase(Packet.Client.KEEP_ALIVE)) {
                checkEvent = new PingEvent();
            }else if(e.getType().equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {
                WrappedInBlockPlacePacket packet = new WrappedInBlockPlacePacket(e.getPacket(), e.getPlayer());
                checkEvent = new PlaceEvent(packet.getPosition(), packet.getItemStack());
            }else if(e.getType().equalsIgnoreCase(Packet.Server.POSITION)) {
                WrappedOutPositionPacket packet = new WrappedOutPositionPacket(e.getPacket(), e.getPlayer());
                checkEvent = new TeleportEvent(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
            }else if(e.getType().equalsIgnoreCase(Packet.Server.ENTITY_VELOCITY)) {
                WrappedOutVelocityPacket packet = new WrappedOutVelocityPacket(e.getPacket(), e.getPlayer());
                if(packet.getId() == e.getPlayer().getEntityId()) {
                    checkEvent = new VelocityEvent(packet.getId(), packet.getX(), packet.getY(), packet.getZ());
                }
            }

            EACEvent finalCheckEvent = checkEvent;
            user.getCheckList().forEach(check -> check.onHandle(user, finalCheckEvent));
        }
    }
}
