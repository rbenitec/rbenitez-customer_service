package service.customer.service;

import service.customer.entity.Customer;
import service.customer.model.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
	Flux<Customer> findAll();
	Mono<Customer> save(Customer customerDto);

	Flux<Customer> findClientByAge(String dni);
	/*

	Mono<CustomerDto> update(CustomerDto customerDto);

	Mono<Void> delete(String id);

	 */
}
