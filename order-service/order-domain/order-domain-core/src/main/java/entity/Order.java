package entity;

import exception.OrderDomainException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import valueObject.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static constant.ErrorConstant.*;

@Getter
@Setter
public class Order extends AggregateRoot<OrderId> {

    private final CustomerId costumerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> orderItems;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;
    private OrderId orderId;

    public Order(OrderId orderId, CustomerId costumerId, RestaurantId restaurantId,
                 StreetAddress deliveryAddress, Money price, List<OrderItem> orderItems,
                 TrackingId trackingId, OrderStatus orderStatus, List<String> failureMessages) {
        super.setId(orderId);
        this.costumerId = costumerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = deliveryAddress;
        this.price = price;
        this.orderItems = orderItems;
        this.trackingId = trackingId;
        this.orderStatus = orderStatus;
        this.failureMessages = failureMessages;
    }

    public Order(CustomerId customerId, RestaurantId restaurantId,
                 StreetAddress orderAddressToStreetAddress, Money money,
                 List<OrderItem> orderItemsToOrderItemEntities) {
        super();
        this.costumerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = orderAddressToStreetAddress;
        this.price = money;
        this.orderItems = orderItemsToOrderItemEntities;
    }

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        this.trackingId = new TrackingId(UUID.randomUUID());
        this.orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void pay() {
        if (this.orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException(ORDER_NOT_IN_CORRECT_STATE);
        }
        this.orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (this.orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(ORDER_NOT_IN_CORRECT_STATE);
        }
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (this.orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(ORDER_NOT_IN_CORRECT_STATE);
        }
        this.orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null && failureMessages.size() > 0) {
            this.failureMessages.addAll(failureMessages
                    .stream().filter(message -> !message.isBlank()).collect(Collectors.toList()));
        }
        if (this.failureMessages == null && failureMessages != null) {
            this.failureMessages = failureMessages;
        }
    }

    public void cancel(List<String> failureMessages) {
        if (this.orderStatus == OrderStatus.CANCELLING || this.orderStatus == OrderStatus.PENDING) {
            throw new OrderDomainException(ORDER_NOT_IN_CORRECT_STATE);
        }
        this.orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem : this.orderItems) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money subTotal = this.orderItems.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!price.equals(subTotal)) {
            throw new OrderDomainException(TOTAL_PRICE_NOT_EQUAL_TO_SUB_TOTAL);
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isValidPrice()) {
            throw new OrderDomainException(ORDER_ITEM_PRICE_NOT_VALID);
        }
    }

    private void validateTotalPrice() {
        if (this.price == null || !this.price.isGreaterThanZero()) {
            throw new OrderDomainException(TOTAL_PRICE_MUST_BE_GRATER_THAN_ZERO);
        }
    }

    private void validateInitialOrder() {
        if (this.orderStatus != null && getId() != null) {
            throw new OrderDomainException(ORDER_NOT_IN_CORRECT_STATE);
        }
    }
}
