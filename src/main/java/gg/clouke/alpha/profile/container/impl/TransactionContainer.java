package gg.clouke.alpha.profile.container.impl;

import com.google.common.collect.Lists;
import gg.clouke.alpha.profile.Profile;
import gg.clouke.alpha.profile.container.AbstractContainer;
import gg.clouke.alpha.profile.container.PacketContainer;
import gg.clouke.alpha.provider.event.AlphaEvent;
import gg.clouke.alpha.provider.event.EventListener;
import gg.clouke.alpha.provider.event.events.tick.TickEndEvent;
import gg.clouke.alpha.provider.event.events.tick.TickStartEvent;
import gg.clouke.alpha.provider.network.impl.system.EPacket;
import gg.clouke.alpha.provider.network.impl.system.bound.server.CPacketConfirmTransaction;
import gg.clouke.alpha.util.functions.Condition;
import gg.clouke.alpha.util.functions.Dispatcher;
import gg.clouke.alpha.util.functions.Executable;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTransaction;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

/**
 * @author Clouke
 * @since 28.05.2022 23:51
 * © Alpha - All Rights Reserved
 */

@Getter
public final class TransactionContainer extends AbstractContainer implements PacketContainer, EventListener, Dispatcher<Executable> {

    private final Map<Short, List<Executable>> actions;
    private final Function<Short, Short> delays;
    private short pps, interval, ticks;
    private long startTime;

    public TransactionContainer(Profile profile) {
        super(profile);
        super.plugin.getEventProvider().subscribe(this);
        super.profile.getTrackedListeners().add(this);
        this.actions = new WeakHashMap<>();
        this.delays = tick -> tick == Short.MIN_VALUE ? -1 : --tick;
    }

    @Override
    public void handle(EPacket<?> packet) {
        if (packet instanceof CPacketConfirmTransaction) {
            final CPacketConfirmTransaction wrapper = (CPacketConfirmTransaction) packet;
            final short id = wrapper.getUid();

            Condition.of(actions.containsKey(id), () -> {
                actions.get(id).forEach(Executable::execute);
                actions.remove(id);
            });
        }

        updatePackets();
    }

    @Override
    public void onEvent(AlphaEvent event) {
        // Send two transactions per tick, remove TickEndEvent if you want to save bandwidth
        final boolean ticking = event instanceof TickStartEvent || event instanceof TickEndEvent;
        Condition.of(ticking, () -> {
            writePacket();
            this.actions.put(ticks, Lists.newArrayList());
            this.ticks = delays.apply(ticks);
        });
    }

    @Override
    public void dispatch(Executable executable) {
        writePacket();
        this.actions.put(ticks, Lists.newArrayList(executable));
        this.ticks = delays.apply(ticks);
    }

    private void updatePackets() { // Keep track of packets per second
        final long delta = System.currentTimeMillis() - startTime;
        if (delta >= 1000) {
            pps = interval;
            startTime = System.currentTimeMillis();
            interval = 1;
        } else {
            interval++;
        }
    }

    private void writePacket() {
        this.plugin.getPacketProvider().writePacket(profile, new PacketPlayOutTransaction(0, ticks, false));
    }
}
