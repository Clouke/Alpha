package gg.clouke.alpha.provider.event;

import gg.clouke.alpha.util.annotations.DoNotMock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 28.05.2022 23:00
 * © Alpha - All Rights Reserved
 */

@DoNotMock("Subscribe using EventProvider.get().subscribe(...);")
public class EventBus implements EventProvider {

    private static final List<EventListener> listeners = new ArrayList<>();

    @Override
    public EventProvider subscribe(EventListener listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public EventProvider unsubscribe(EventListener listener) {
        listeners.remove(listener);
        return this;
    }

    @Override
    public void publish(AlphaEvent event) {
        listeners.forEach(listener -> listener.onEvent(event));
    }

    @Override
    public <T extends AbstractEvent> void publish(Class<T> event) {
        listeners.forEach(listener -> listener.onEvent(event));
    }

}