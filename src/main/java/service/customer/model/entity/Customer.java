package service.customer.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;
    private String customerType;
    private String dni;
    private String names;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String createdDate;
    private String updateDate;
}
