package dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import valueObject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderResponse {
    @NotNull(message = "0000")
    private final UUID orderTrackingId;
    @NotNull(message = "0000")
    private final OrderStatus orderStatus;
    private final List<String> failureMessages;
}
