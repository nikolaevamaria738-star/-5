package manager;
/**
 * Генератор уникальных идентификаторов для маршрутов.
 * Гарантирует, что каждый новый ID больше всех предыдущих.
 */
public class IdGenerator {
    /**
     * Генерирует и возвращает следующий уникальный идентификатор.
     * @return новый ID
     */
    private long nextId = 1;

    public synchronized long getNextId() {
        return nextId++;
    }

    public void setNextId(long id) {
        this.nextId = id;
    }
}