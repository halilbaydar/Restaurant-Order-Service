import dto.create.CreateOrderCommand;
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
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistsOrder(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.toOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        orderRepository.save(order);
        return orderCreatedEvent;
    }

    protected Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.toRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            throw new OrderDomainException(RESTAURANT_NOT_FOUND);
        }
        return optionalRestaurant.get();
    }

    protected void checkCustomer(UUID costumerId) {
        if (!customerRepository.existsById(costumerId)) {
            throw new OrderDomainException(CUSTOMER_NOT_FOUND);
        }
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
