package valueObject;

import lombok.Getter;

@Getter
public abstract class BaseId<T> {
    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }
}
