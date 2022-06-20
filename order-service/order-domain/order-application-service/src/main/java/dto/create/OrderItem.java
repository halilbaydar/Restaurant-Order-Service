package dto.create;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderItem {
    @NotNull(message = "0000")
    private final UUID productId;
    @NotNull(message = "0000")
    private final Integer quantity;
    @NotNull(message = "0000")
    private final BigDecimal price;
    @NotNull(message = "0000")
    private final BigDecimal subTotal;
}
