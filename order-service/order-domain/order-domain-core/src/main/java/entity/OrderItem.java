package entity;

import lombok.Getter;
import valueObject.Money;
import valueObject.OrderId;
import valueObject.OrderItemId;

@Getter
public class OrderItem extends BaseEntity<OrderItemId> {
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;
    private OrderId orderId;

    protected OrderItem(Product product, int quantity, Money price, Money subTotal) {
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
