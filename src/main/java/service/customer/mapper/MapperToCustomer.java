package service.customer.mapper;

import org.springframework.stereotype.Component;
import service.customer.model.dto.RequestCustomerDto;
import service.customer.model.entity.Customer;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class MapperToCustomer implements Function<RequestCustomerDto, Customer> {
    @Override
    public Customer apply(RequestCustomerDto requestCustomerDto) {
        return Customer.builder()
                .customerType(requestCustomerDto.getCustomerType())
                .dni(requestCustomerDto.getDni())
                .names(requestCustomerDto.getNames())
                .lastName(requestCustomerDto.getLastName())
                .address(requestCustomerDto.getAddress())
                .phone(requestCustomerDto.getPhone())
                .email(requestCustomerDto.getEmail())
                .createdDate(LocalDateTime.now().toString())
                .updateDate(LocalDateTime.now().toString())
                .build();
    }
}
