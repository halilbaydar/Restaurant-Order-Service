package ports.output.repository;

import entity.Customer;
import repository.ParentRepository;

import java.util.UUID;

public interface CustomerRepository extends ParentRepository<Customer, UUID> {
}
