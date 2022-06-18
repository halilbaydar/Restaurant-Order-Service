package entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity<ID> {
    private ID id;
}
