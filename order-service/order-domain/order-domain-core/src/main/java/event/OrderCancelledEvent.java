package event;

import entity.Order;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class OrderCancelledEvent extends OrderEvent<Order> {

    public OrderCancelledEvent(Order order, ZonedDateTime zonedDateTime) {
        super(order, zonedDateTime);
    }
}
