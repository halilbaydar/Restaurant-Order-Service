package event;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public abstract class OrderEvent<T> implements DomainEvent<T> {
    private final T item;
    private final ZonedDateTime zonedDateTime;

    public OrderEvent(T item, ZonedDateTime zonedDateTime) {
        this.item = item;
        this.zonedDateTime = zonedDateTime;
    }
}
