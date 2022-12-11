package Utils;

public interface DAO<T> {
    public void create(T t);

    public T read(String id);

    public void update(T t);

    public void delete(T t);
}
