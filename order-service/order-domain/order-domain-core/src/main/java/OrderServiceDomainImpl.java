import entity.Order;
import entity.Product;
import entity.Restaurant;
import event.OrderCancelledEvent;
import event.OrderCreatedEvent;
import event.OrderPaidEvent;
import exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static constant.ErrorConstant.RESTAURANT_NOT_ACTIVE;

public class OrderServiceDomainImpl implements OrderDomainService {

    public static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getOrderItems().parallelStream().forEach(orderItem -> {
            restaurant.getProducts().parallelStream().forEach(restaurantProduct -> {
                Product product = orderItem.getProduct();
                if (product.equals(restaurantProduct)) {
                    product.updateWithConfirmedNameAndPrice(restaurantProduct);
                }
            });
        });
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive())
            throw new OrderDomainException(RESTAURANT_NOT_ACTIVE);
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
    }
}
