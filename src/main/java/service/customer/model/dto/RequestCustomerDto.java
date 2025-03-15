package service.customer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestCustomerDto {
    @JsonProperty("customerType")
    private String customerType; //PERSONAL O EMPRESARIAL
    @JsonProperty("subType")
    private String subType; //VIP, PYME (si aplica)
    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("documentNumber")
    private String documentNumber;
    @JsonProperty("names")
    private String names;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("hasCreditCard")
    private boolean hasCreditCard;
}
