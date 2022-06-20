package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import valueObject.Money;
import valueObject.OrderId;
import valueObject.OrderItemId;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderItem extends BaseEntity<OrderItemId> {
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;
    private OrderId orderId;

    public OrderItem(Product product, int quantity, Money price, Money subTotal) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    public boolean isValidPrice() {
        return this.price.isGreaterThanZero() &&
                this.price.equals(product.getPrice()) &&
                this.price.multiply(this.quantity).equals(this.subTotal);
    }

    protected void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }
}
