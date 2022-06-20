import dto.create.CreateOrderCommand;
import dto.create.CreateOrderResponse;
import entity.Order;
import entity.Restaurant;
import event.OrderCreatedEvent;
import exception.OrderDomainException;
import mapper.OrderDataMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ports.output.repository.CustomerRepository;
import ports.output.repository.OrderRepository;
import ports.output.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

import static constant.ErrorConstant.CUSTOMER_NOT_FOUND;
import static constant.ErrorConstant.RESTAURANT_NOT_FOUND;

@Component
public class OrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;
    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     OrderRepository orderRepository,
                                     CustomerRepository customerRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper,
                                     ApplicationDomainEventPublisher applicationDomainEventPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCostumerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.toOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order orderResult = orderRepository.save(order);
        applicationDomainEventPublisher.publishEvent(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderResult);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.toRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            throw new OrderDomainException(RESTAURANT_NOT_FOUND);
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID costumerId) {
        if (!customerRepository.existsById(costumerId)) {
            throw new OrderDomainException(CUSTOMER_NOT_FOUND);
        }
    }
}
