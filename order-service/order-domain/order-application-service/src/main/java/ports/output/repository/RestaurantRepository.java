package ports.output.repository;

import entity.Restaurant;
import repository.ParentRepository;
import valueObject.RestaurantId;

import java.util.Optional;

public interface RestaurantRepository extends ParentRepository<Restaurant, RestaurantId> {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
