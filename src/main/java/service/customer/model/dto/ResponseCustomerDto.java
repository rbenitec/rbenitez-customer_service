package service.customer.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCustomerDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("customerType")
    private String customerType;
    @JsonProperty("subType")
    private String subType;
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
    @JsonProperty("dateCreated")
    private String dateCreated;
    @JsonProperty("dateUpdate")
    private String dateUpdate;
    @JsonProperty("hasCreditCard")
    private Boolean hasCreditCard;
}
