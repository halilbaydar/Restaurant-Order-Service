package constant;

import lombok.Getter;

@Getter
public enum ErrorConstant {
    ORDER_NOT_IN_CORRECT_STATE("Order is not in correct state for initialization"),
    TOTAL_PRICE_MUST_BE_GRATER_THAN_ZERO("Total price must be grater than zero"),
    TOTAL_PRICE_NOT_EQUAL_TO_SUB_TOTAL("Total price is not equal to sub total"),
    ORDER_ITEM_PRICE_NOT_VALID("Order item price is not valid"),
    RESTAURANT_NOT_ACTIVE("Restaurant is not active"),
    CUSTOMER_NOT_FOUND("Customer not found"),
    RESTAURANT_NOT_FOUND("Restaurant not found"),


    ;
    private final String message;

    ErrorConstant(String message) {
        this.message = message;
    }
}
