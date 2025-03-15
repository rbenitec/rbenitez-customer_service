package service.customer.mapper;

import org.springframework.stereotype.Component;
import service.customer.model.dto.ResponseCustomerDto;
import service.customer.model.entity.Customer;

import java.util.function.Function;

@Component
public class MapperToResponseCustomer implements Function<Customer, ResponseCustomerDto> {

    @Override
    public ResponseCustomerDto apply(Customer customer) {
        return new ResponseCustomerDto(
                customer.getId(),
                customer.getCustomerType(),
                customer.getSubType(),
                customer.getDocumentType(),
                customer.getDocumentNumber(),
                customer.getNames(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getCreatedDate(),
                customer.getUpdateDate(),
                customer.getHasCreditCard()
        );
    }
}
