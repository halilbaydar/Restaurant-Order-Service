package ports.output.repository;

import entity.Order;
import repository.ParentRepository;
import valueObject.OrderId;
import valueObject.TrackingId;

import java.util.Optional;

public interface OrderRepository extends ParentRepository<Order, OrderId> {

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
