package service.customer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@Document(collection = "CustomerDto")
public class CustomerDto {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int age;

}
