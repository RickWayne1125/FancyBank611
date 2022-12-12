package Utils;

public interface DAO<T> {
    void create(T t);

//    T read(String id);

    void update(T t);

    void delete(T t);
}
