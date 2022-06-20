package entity;

import lombok.Getter;
import valueObject.Money;
import valueObject.ProductId;


@Getter
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public void updateWithConfirmedNameAndPrice(Product restaurantProduct) {
        this.name = restaurantProduct.getName();
        this.price = restaurantProduct.getPrice();
    }
}
