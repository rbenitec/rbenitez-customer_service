package service.customer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;
    private String customerType; // PERSONAL O EMPRESARIAL
    private String subType; // VIP, PYME (si aplica)
    private String documentType;
    private String documentNumber;
    private String names;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String createdDate;
    private String updateDate;
    private Boolean hasCreditCard;
}
