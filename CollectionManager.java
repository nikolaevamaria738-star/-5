package manager;
/**
 * Управляет коллекцией маршрутов: загрузка, сохранение, генерация ID.
 * Обеспечивает целостность данных и уникальность идентификаторов.
 */
import io.CsvReader;
import io.CsvWriter;
import model.Route;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private final List<Route> collection = new ArrayList<>();
    private final IdGenerator idGenerator = new IdGenerator();
    private final CsvReader csvReader = new CsvReader();
    private final CsvWriter csvWriter = new CsvWriter();
    private final LocalDateTime initializationDate;
    private String filePath;

    /**
     * Создаёт менеджер коллекции с указанными зависимостями.
     * @param //collection список маршрутов для управления
     * @param //idGenerator генератор уникальных идентификаторов
     * @param //csvReader объект для чтения маршрутов из CSV-файла
     */

    public CollectionManager() {
        this.initializationDate = LocalDateTime.now();
    }

    //геттеры
    public List<Route> getCollection() {
        return collection;
    }
    public String getFilePath() {
        return filePath;
    }
    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public long getNextId() {
        return idGenerator.getNextId();
    }

    /**
     * Загружает коллекцию маршрутов из указанного файла.
     * Очищает текущую коллекцию, читает данные и обновляет генератор ID,
     * чтобы новые маршруты получали уникальные идентификаторы.
     * @param filePath путь к файлу для загрузки (например, "data.csv")
     * @throws IOException если файл не найден, недоступен или повреждён
     */

    public void loadFromFile(String filePath) throws IOException {
        this.filePath = filePath;
        collection.clear();
        List<Route> loaded = csvReader.read(filePath);
        collection.addAll(loaded);
        long maxId = collection.stream().mapToLong(Route::getId).max().orElse(0L);
        idGenerator.setNextId(maxId + 1);
    }

    /**
     * Сохраняет текущую коллекцию маршрутов в файл, указанный при последней загрузке.
     * Если файл ещё не был задан, используется путь по умолчанию.
     * @throws IOException если запись в файл невозможна (нет прав, диск переполнен и т.д.)
     * @see #loadFromFile(String)
     */

    public void saveToFile(String filePath) throws IOException {
        this.filePath = filePath;
        csvWriter.write(filePath, collection);
    }

    /**
     * Добавляет новый маршрут в коллекцию и присваивает ему уникальный ID.
     * @param route маршрут для добавления (без ID или с временным ID)
     * @return добавленный маршрут с корректным ID
     */
    public void add(Route route) {
        collection.add(route);
    }

    /**
     * Обновляет существующий маршрут в коллекции по его уникальному идентификатору.
     * Если маршрут с указанным ID найден, он заменяется новым объектом.
     * Идентификатор нового маршрута устанавливается равным исходному ID,
     * чтобы сохранить ссылочную целостность.
     *
     * @param id        уникальный идентификатор маршрута для обновления
     * @param newRoute  новый объект маршрута, который заменит существующий
     * @return          {@code true}, если маршрут был успешно обновлён;
     *                  {@code false}, если маршрут с указанным ID не найден
     * @throws NullPointerException если {@code newRoute} равен {@code null}
     */

    public boolean update(long id, Route newRoute) {
        int index = indexOfId(id);
        if (index == -1) return false;
        newRoute.setId(id);
        collection.set(index, newRoute);
        return true;
    }

    public boolean removeById(long id) {
        return collection.removeIf(r -> r.getId() == id);
    }

    public void clear() {
        collection.clear();
    }

    public void reorder() {
        Collections.reverse(collection);
    }

    public void sort() {
        collection.sort(Comparator.comparingLong(Route::getId));
    }

    public long countByDistance(double distance) {
        return collection.stream().filter(r -> Math.abs(r.getDistance() - distance) < 1e-9).count();
    }
    //элементы с distance < заданного
    public long countLessThanDistance(double distance) {
        return collection.stream().filter(r -> r.getDistance() < distance).count();
    }
    //поля distance в возрастающем порядке
    public List<Double> getFieldAscendingDistance() {
        return collection.stream().map(Route::getDistance).sorted().collect(Collectors.toList());
    }

    public int indexOfId(long id) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public Route findById(long id) {
        return collection.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public boolean existsById(long id) {
        return indexOfId(id) != -1;
    }

    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }
}