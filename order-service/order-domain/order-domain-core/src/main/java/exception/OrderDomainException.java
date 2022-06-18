package exception;

import constant.ErrorConstant;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDomainException(ErrorConstant errorConstant) {
        super(errorConstant.getMessage());
    }
}
