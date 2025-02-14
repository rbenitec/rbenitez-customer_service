package service.customer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import service.customer.api.ClientesApiDelegate;
import service.customer.entity.Customer;
import service.customer.model.RequestBodyCrearUsuario;
import service.customer.model.StatusResponseData;
import service.customer.model.StatusResponseOKDelete;
import service.customer.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.customer.service.CustomerService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ClientesApiDelegate {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<ResponseEntity<StatusResponseOKDelete>> delete(String id, String xUserId, String xConsumerId, String xTransactionId, String xCallerId, ServerWebExchange exchange) {

        return ClientesApiDelegate.super.delete(id, xUserId, xConsumerId, xTransactionId, xCallerId, exchange);
    }

    @Override
    public Mono<ResponseEntity<StatusResponseData>> obtenercliente(String id, String xUserId, String xConsumerId, String xTransactionId, String xCallerId, ServerWebExchange exchange) {
        return ClientesApiDelegate.super.obtenercliente(id, xUserId, xConsumerId, xTransactionId, xCallerId, exchange);
    }

    @Override
    public Mono<ResponseEntity<StatusResponseData>> save(String xUserId, String xConsumerId, String xTransactionId, String xCallerId, Mono<RequestBodyCrearUsuario> requestBodyCrearUsuario, ServerWebExchange exchange) {
        return requestBodyCrearUsuario
                .map(requestBody -> {
                    Customer customer = new Customer();
                    customer.setCustomerType(requestBody.getCustomerType());
                    customer.setDni(requestBody.getDni());
                    customer.setNames(requestBody.getNames());
                    customer.setLastName(requestBody.getLastName());
                    customer.setPhone(requestBody.getPhone());
                    customer.setAddress(requestBody.getAddress());
                    customer.setCreatedDate(LocalDateTime.now().toString());
                    customer.setCreatedDate(LocalDateTime.now().toString());
                    return customer;
                })
                .flatMap(customerRepository::save)
                .map(customer -> {
                    StatusResponseData statusResponseData = new StatusResponseData();
                    statusResponseData.setCustomerType(customer.getCustomerType());
                    statusResponseData.setAddress(customer.getAddress());
                    statusResponseData.setDateCreated(customer.getCreatedDate());
                    statusResponseData.setDni(customer.getDni());
                    statusResponseData.setDateUpdate(customer.getUpdateDate());
                    statusResponseData.setEmail(customer.getEmail());
                    statusResponseData.setId(customer.getId());
                    statusResponseData.setLastName(customer.getLastName());
                    statusResponseData.setNames(customer.getNames());
                    statusResponseData.setPhone(customer.getPhone());
                    return statusResponseData;
                })
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @Override
    public Mono<ResponseEntity<StatusResponseData>> update(String id, String xUserId, String xConsumerId, String xTransactionId, String xCallerId, Mono<RequestBodyCrearUsuario> requestBodyCrearUsuario, ServerWebExchange exchange) {
        return ClientesApiDelegate.super.update(id, xUserId, xConsumerId, xTransactionId, xCallerId, requestBodyCrearUsuario, exchange);
    }
    /*

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> save(Customer customerDto) {
        return customerRepository.save(customerDto);
    }

    @Override
    public Flux<Customer> findClientByAge(String dni) {
        return customerRepository.findAll().filter(k -> k.getDni().equals(dni));
    }
/*
    @Override
    public Mono<CustomerDto> update(CustomerDto customerDto) {
        return customerRepository.save(customerDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return customerRepository.deleteById(id);
    }

 */


}
