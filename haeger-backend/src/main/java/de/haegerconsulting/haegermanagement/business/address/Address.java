package de.haegerconsulting.haegermanagement.business.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Address {

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @Min(10000)
    @Max(99999)
    @NotNull
    private int postcode;

    @NotBlank
    private String city;

}
