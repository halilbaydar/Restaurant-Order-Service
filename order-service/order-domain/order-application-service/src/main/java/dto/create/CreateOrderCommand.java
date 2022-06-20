package dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {
    @NotNull(message = "0000")
    private final UUID costumerId;
    @NotNull(message = "0000")
    private final UUID restaurantId;
    @NotNull(message = "0000")
    private final BigDecimal price;
    @NotNull(message = "0000")
    private final List<OrderItem> orderItems;
    @NotNull(message = "0000")
    private final OrderAddress orderAddress;
}
