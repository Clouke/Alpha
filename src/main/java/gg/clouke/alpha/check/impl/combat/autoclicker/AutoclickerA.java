package gg.clouke.alpha.check.impl.combat.autoclicker;

import gg.clouke.alpha.check.AbstractCheck;
import gg.clouke.alpha.check.BaseCheck;
import gg.clouke.alpha.profile.Profile;
import gg.clouke.alpha.provider.network.impl.system.EPacket;
import gg.clouke.alpha.provider.network.impl.system.bound.server.CPacketAnimation;
import gg.clouke.alpha.provider.network.impl.system.bound.server.CPacketFlying;

/**
 * @author Lucanius
 * @since June 01, 2022.
 * Alpha - All Rights Reserved.
 */
@BaseCheck(name = "Autoclicker", type = "A", maxVl = 50)
public class AutoclickerA extends AbstractCheck {

    private int count, cps;

    public AutoclickerA(Profile profile) {
        super(profile);
    }

    @Override
    public void handle(EPacket<?> packet) {
        if (packet instanceof CPacketFlying) {
            if (++count >= 20) {
                if (cps >= 15) {
                    if (++buffer >= 10) {
                        alert("CPS: " + cps);
                    }
                } else {
                    buffer = 0;
                }

                count = cps = 0;
            }
        } else if (packet instanceof CPacketAnimation) {
            ++cps;
        }
    }
}
