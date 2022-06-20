package dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import valueObject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    @NotNull(message = "0000")
    private final UUID orderTracingId;
    @NotNull(message = "0000")
    private final OrderStatus orderStatus;
    @NotNull(message = "0000")
    private final String message;
}
