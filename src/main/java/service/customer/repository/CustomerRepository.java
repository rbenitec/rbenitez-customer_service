package service.customer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import service.customer.model.entity.Customer;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    //Flux<CustomerDto> findByAge(int age);

}
