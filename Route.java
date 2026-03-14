package model;

import java.time.LocalDateTime;
import java.util.Objects;
/**
 * Представляет маршрут с уникальным идентификатором, координатами,
 * начальной и конечной точками, расстоянием и датой создания.
 */
public class Route implements Comparable<Route> {
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Location from;
    private Location to;
    private Double distance;
    /**
     * Создаёт новый маршрут с указанными параметрами.
     * @param id уникальный идентификатор
     * @param name название маршрута (не пустое)
     * @param coordinates координаты маршрута
     * @param creationDate дата создания
     * @param from начальная точка (может быть null)
     * @param to конечная точка (обязательна)
     * @param distance расстояние (> 0)
     */
    public Route(long id, String name, Coordinates coordinates,
                 LocalDateTime creationDate, Location from, Location to, Double distance) {

        if (id <= 0) {
            throw new IllegalArgumentException("Значение поля должно быть больше 0");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        if (name == null) {
            throw new IllegalArgumentException("Поле не может быть null");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Поле не может быть null");
        }
        if (creationDate == null) {
            throw new IllegalArgumentException("Поле не может быть null");
        }
        if (to == null) {
            throw new IllegalArgumentException("Поле не может быть null");
        }
        if (distance == null) {
            throw new IllegalArgumentException("Поле не может быть null");
        }
        if (distance <= 1.0) {
            throw new IllegalArgumentException("Значение поля должно быть больше 1.0");
        }

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public Location getFrom() { return from; }
    public Location getTo() { return to; }
    public Double getDistance() { return distance; }

    @Override
    public int compareTo(Route other) {
        return Long.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Route{id=%d, name='%s', coordinates=%s, creationDate=%s, from=%s, to=%s, distance=%.2f}",
                id, name, coordinates, creationDate, from, to, distance
        );
    }
}