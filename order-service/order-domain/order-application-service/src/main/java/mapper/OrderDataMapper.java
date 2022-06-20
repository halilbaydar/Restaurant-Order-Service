package mapper;

import dto.create.CreateOrderCommand;
import dto.create.CreateOrderResponse;
import dto.create.OrderAddress;
import dto.create.OrderItem;
import entity.Order;
import entity.Product;
import entity.Restaurant;
import org.springframework.stereotype.Component;
import valueObject.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant toRestaurant(CreateOrderCommand createOrderCommand) {
        return new Restaurant(new RestaurantId(createOrderCommand.getRestaurantId()),
                createOrderCommand.getOrderItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId()))).collect(Collectors.toList()));
    }

    public Order toOrder(CreateOrderCommand createOrderCommand) {
        return new Order(new CustomerId(createOrderCommand.getCostumerId()),
                new RestaurantId(createOrderCommand.getRestaurantId()),
                orderAddressToStreetAddress(createOrderCommand.getOrderAddress()),
                new Money(createOrderCommand.getPrice()),
                orderItemsToOrderItemEntities(createOrderCommand.getOrderItems()));
    }

    private List<entity.OrderItem> orderItemsToOrderItemEntities(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItem ->
                new entity.OrderItem(
                        new Product(new ProductId(orderItem.getProductId())),
                        orderItem.getQuantity(),
                        new Money(orderItem.getPrice()),
                        new Money(orderItem.getSubTotal())
                )).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(UUID.randomUUID(), orderAddress.getStreet(),
                orderAddress.getPostalCode(), orderAddress.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order orderResult) {
        return CreateOrderResponse.builder()
                .orderTracingId(orderResult.getRestaurantId().getValue())
                .orderStatus(orderResult.getOrderStatus())
                .build();
    }
}
