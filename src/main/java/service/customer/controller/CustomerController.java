package service.customer.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.customer.model.dto.RequestCustomerDto;
import service.customer.model.dto.ResponseCustomerDto;
import service.customer.model.dto.ResponseDeleteDto;
import service.customer.service.CustomerService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    //uri: "mongodb+srv://rbenitec:rjbc2095@cluster0.zaixl.mongodb.net/MongoPractica?retryWrites=true&w=majority"

    private final CustomerService customerService;

    /**
     * Operation by created Customer.
     *
     * @param customerDto
     * @return ResponseCustomerDto
     */
    @PostMapping()
    Mono<ResponseEntity<ResponseCustomerDto>> createdCustomer(@RequestBody Mono<RequestCustomerDto> customerDto) {

        return customerService.saveCustomer(customerDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body);
    }

    /**
     * Operation by get all customer.
     *
     * @return ResponseCustomerDto
     */
    @GetMapping()   // Mostrar todos los clientes
    public Mono<ResponseEntity<List<ResponseCustomerDto>>> getAllCustomer() {
        log.info("Get all customer");
        return customerService.getAllCustomer().collectList()
                .map(ResponseEntity.status(HttpStatus.OK)::body);
    }

    /**
     * Operation for customer by id.
     *
     * @param customerId
     * @return
     */

    @GetMapping("/{customerId}")
    public Mono<ResponseEntity<ResponseCustomerDto>> getCustomerById(@PathVariable("customerId") String customerId) {
        log.info("Get customer by id: {} ", customerId);
        return customerService.findClientById(customerId)
                .map(ResponseEntity.status(HttpStatus.OK)::body);
    }

    /**
     * Operation by update Customer.
     *
     * @param customerId
     * @param customerDto
     * @return
     */
    @PutMapping("/{customerId}")
    Mono<ResponseEntity<ResponseCustomerDto>> updateCustomer(@PathVariable("customerId") String customerId,
                                                             @RequestBody Mono<RequestCustomerDto> customerDto) {
        return customerService.updateCustomer(customerId, customerDto)
                .map(ResponseEntity.status(HttpStatus.OK)::body);
    }

    /**
     * Operation by delete Customer.
     *
     * @param customerId
     * @return
     */
    @DeleteMapping("/{customerId}")
    Mono<ResponseEntity<ResponseDeleteDto>> deleteCustomer(@PathVariable("customerId") String customerId) {
        return customerService.deleteCustomer(customerId)
                .map(ResponseEntity.status(HttpStatus.OK)::body);
    }

}
