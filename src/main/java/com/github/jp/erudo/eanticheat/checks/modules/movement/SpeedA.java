package com.github.jp.erudo.eanticheat.checks.modules.movement;

import com.github.jp.erudo.eanticheat.checks.Check;
import com.github.jp.erudo.eanticheat.checks.CheckInterface;
import com.github.jp.erudo.eanticheat.checks.CheckType;
import com.github.jp.erudo.eanticheat.event.EACEvent;
import com.github.jp.erudo.eanticheat.event.events.packetevents.FlyingPacketEvent;
import com.github.jp.erudo.eanticheat.utils.user.User;

public class SpeedA extends Check implements CheckInterface {

	private double max = 0.29;
    private double lastDeltaXZ;

	public SpeedA(String checkName, String mode, CheckType type, boolean enabled) {
		super(checkName, mode, type, enabled);
	}

	@Override
	public void onHandle(User u, EACEvent e) {
		if(e instanceof FlyingPacketEvent) {
			if(((FlyingPacketEvent) e).isHasMoved()) {
				max *= u.getPlayer().getWalkSpeed() / 0.2;
				max += u.getSpeedPotionEffectLevel() % max;

				double deltaXZ = u.getDeltaXZ();
				double lastDeltaXZ = this.lastDeltaXZ;
				lastDeltaXZ = deltaXZ;

				double diff = Math.abs(deltaXZ - lastDeltaXZ);

				if(diff == 0 && deltaXZ > max && !u.getPlayer().getAllowFlight()) {
					this.flag(u,"definitely speed, diff: " + diff);
				}
			}
		}
	}

}
