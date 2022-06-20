package event;

import entity.Order;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class OrderCreatedEvent extends OrderEvent<Order> {
    public OrderCreatedEvent(Order order, ZonedDateTime zonedDateTime) {
        super(order, zonedDateTime);
    }
}
