package entity;

import lombok.Getter;
import valueObject.RestaurantId;

import java.util.List;

@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;
    private boolean active;

    public Restaurant(RestaurantId restaurantId, List<Product> products) {
        super.setId(restaurantId);
        this.products = products;
    }
}
