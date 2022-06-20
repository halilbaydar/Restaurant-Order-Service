package dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class OrderAddress {
    @NotBlank(message = "0000")
    private final String street;
    @NotBlank(message = "0000")
    private final String postalCode;
    @NotBlank(message = "0000")
    private final String city;
}
