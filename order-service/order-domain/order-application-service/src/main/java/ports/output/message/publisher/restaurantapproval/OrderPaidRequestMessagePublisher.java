package ports.output.message.publisher.restaurantapproval;

import event.OrderPaidEvent;
import event.publisher.DomainEventPublisher;

public interface OrderPaidRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
