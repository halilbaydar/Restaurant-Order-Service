import event.OrderCreatedEvent;
import event.publisher.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDomainEventPublisher implements ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishEvent(OrderCreatedEvent domainEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
