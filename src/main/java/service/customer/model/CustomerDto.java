package service.customer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
public class CustomerDto {

    private String id;
    private String firstName;
    private String lastName;
    private int age;

}
