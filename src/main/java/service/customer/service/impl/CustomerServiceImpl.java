package service.customer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.customer.exception.BusinessException;
import service.customer.mapper.MapperToCustomer;
import service.customer.mapper.MapperToResponseCustomer;
import service.customer.model.dto.RequestCustomerDto;
import service.customer.model.dto.ResponseCustomerDto;
import service.customer.model.dto.ResponseDeleteDto;
import service.customer.repository.CustomerRepository;
import service.customer.service.CustomerService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final MapperToResponseCustomer mapperToResponseCustomer;
    private final MapperToCustomer mapperToCustomer;

    @Override
    public Flux<ResponseCustomerDto> getAllCustomer() {
        return customerRepository.findAll()
                .map(mapperToResponseCustomer)
                .onErrorMap(throwable -> new BusinessException("[getAllCustomer] - Error in the process of getting all customers", throwable.getMessage()));
    }

    @Override
    public Mono<ResponseCustomerDto> saveCustomer(Mono<RequestCustomerDto> requestCustomerDto) {
        return requestCustomerDto
                .map(mapperToCustomer)
                .flatMap(customerRepository::save)
                .switchIfEmpty(Mono.error(new BusinessException("saveCustomer", "Error saving customer to database")))
                .map(mapperToResponseCustomer)
                .onErrorMap(ex -> new BusinessException("[saveCustomer]: Error in the process of saving a customer", ex.getMessage()));
    }

    public Mono<ResponseCustomerDto> saveCustomerFunctional(Mono<RequestCustomerDto> requestCustomerDto) {
        // Validación del tipo de cliente
        return requestCustomerDto
                .flatMap(requestCustomer -> {
                    if (!"personal".equalsIgnoreCase(requestCustomer.getCustomerType()) &&
                            !"empresarial".equalsIgnoreCase(requestCustomer.getCustomerType())) {
                        return Mono.error(new BusinessException("saveCustomer", "Tipo de cliente no válido."));
                    }

                    // Validación de subtipos
                    if ("VIP".equalsIgnoreCase(requestCustomer.getSubType()) &&
                            !"personal".equalsIgnoreCase(requestCustomer.getCustomerType())) {
                        return Mono.error(new BusinessException("saveCustomer", "El perfil VIP solo aplica para clientes personales."));
                    }

                    if ("PYME".equalsIgnoreCase(requestCustomer.getSubType()) &&
                            !"empresarial".equalsIgnoreCase(requestCustomer.getCustomerType())) {
                        return Mono.error(new BusinessException("saveCustomer", "El perfil PYME solo aplica para clientes empresariales."));
                    }

                    // Validación de tarjeta de crédito para clientes VIP y PYME
                    if (("VIP".equalsIgnoreCase(requestCustomer.getSubType()) ||
                            "PYME".equalsIgnoreCase(requestCustomer.getSubType())) &&
                            !requestCustomer.isHasCreditCard()) {
                        return Mono.error(new BusinessException("",
                                "El cliente " + requestCustomer.getSubType() + " debe tener una tarjeta de crédito activa."
                        ));
                    }
                    return customerRepository.save(mapperToCustomer.apply(requestCustomer))
                            .switchIfEmpty(Mono.error(new BusinessException("saveCustomer", "Error saving customer to database")))
                            .map(mapperToResponseCustomer)
                            .onErrorMap(ex -> new BusinessException("[saveCustomer]: Error in the process of saving a customer", ex.getMessage()));

                });

//        return requestCustomerDto
//                .map(mapperToCustomer)
//                .flatMap(customerRepository::save)
//                .switchIfEmpty(Mono.error(new BusinessException("saveCustomer", "Error saving customer to database")))
//                .map(mapperToResponseCustomer)
//                .onErrorMap(ex -> new BusinessException("[saveCustomer]: Error in the process of saving a customer", ex.getMessage()));
    }

    @Override
    public Mono<ResponseCustomerDto> findClientById(String customerId) {
        return customerRepository.findById(customerId)
                .doOnSuccess(customer -> log.info("Customer found: {}", customer))
                .map(mapperToResponseCustomer)
                .onErrorMap(throwable -> new BusinessException("[saveCustomer]: error in the process of obtaining a client by its id", throwable.getMessage()));
    }

    @Override
    public Mono<ResponseCustomerDto> updateCustomer(String customerId, Mono<RequestCustomerDto> requestCustomerDto) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new BusinessException("updateCustomer", "Client with id" + customerId + "does not exist")))
                .flatMap(customer -> requestCustomerDto
                        .map(mapperToCustomer)
                        .flatMap(customerUpdate -> {
                            customerUpdate.setId(customerId);
                            return customerRepository.save(customerUpdate);
                        })
                        .map(mapperToResponseCustomer))
                .onErrorMap(ex -> new BusinessException("[updateCustomer]: Error in the process of update a customer", ex.getMessage()));
    }

    @Override
    public Mono<ResponseDeleteDto> deleteCustomer(String customerId) {
        return customerRepository.deleteById(customerId)
                .then(Mono.just(ResponseDeleteDto.builder()
                        .message("The client was successfully deleted, with id: ".concat(customerId))
                        .build()))
                .onErrorResume(error -> Mono.just(ResponseDeleteDto.builder()
                        .message("Error deleting customer with id: "
                                .concat(customerId)
                                .concat(" - error: ".concat(error.getMessage()))
                        )
                        .build()));
    }

    /*
    private final CustomerRepository customerRepository;

    @Override
    public Mono<ResponseEntity<ResponseDelete>> deleteCustomer(String customerId, String xUserId, String xConsumerId, String xTransactionId, String xCallerId, ServerWebExchange exchange) {
        return CustomerApiDelegate.super.deleteCustomer(customerId, xUserId, xConsumerId, xTransactionId, xCallerId, exchange);
    }

    @Override
    public Mono<ResponseEntity<ResponseCustomerDto>> getCustomerById(String customerId, String xUserId, String xConsumerId, String xTransactionId, String xCallerId, ServerWebExchange exchange) {
        return CustomerApiDelegate.super.getCustomerById(customerId, xUserId, xConsumerId, xTransactionId, xCallerId, exchange);
    }

    @Override
    public Mono<ResponseEntity<ResponseCustomerDto>> saveCustomer(String xUserId, String xConsumerId, String xTransactionId, String xCallerId, Mono<RequestCustomerDto> requestCustomerDto, ServerWebExchange exchange) {
        return requestCustomerDto
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
                    ResponseCustomerDto statusResponseData = new ResponseCustomerDto();
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
    public Mono<ResponseEntity<ResponseCustomerDto>> updateCustomerById(String customerId, String xUserId, String xConsumerId, String xTransactionId, String xCallerId, Mono<RequestCustomerDto> requestCustomerDto, ServerWebExchange exchange) {
        return CustomerApiDelegate.super.updateCustomerById(customerId, xUserId, xConsumerId, xTransactionId, xCallerId, requestCustomerDto, exchange);
    }
    /*
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
