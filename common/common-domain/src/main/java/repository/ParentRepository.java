package repository;

public interface ParentRepository<T, ID> {
    T save(T t);

    T findById(ID id);

    boolean existsById(ID id);
}
