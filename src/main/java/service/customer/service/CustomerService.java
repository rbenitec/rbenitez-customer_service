package service.customer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.customer.model.dto.RequestCustomerDto;
import service.customer.model.dto.ResponseCustomerDto;
import service.customer.model.dto.ResponseDeleteDto;

public interface CustomerService {
    Flux<ResponseCustomerDto> getAllCustomer();

    Mono<ResponseCustomerDto> saveCustomer(Mono<RequestCustomerDto> customerDto);

    Mono<ResponseCustomerDto> findClientById(String customerId);

    Mono<ResponseCustomerDto> updateCustomer(String customerId, Mono<RequestCustomerDto> customerDto);

    Mono<ResponseDeleteDto> deleteCustomer(String customerId);

}
