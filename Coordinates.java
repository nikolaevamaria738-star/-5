package model;
/**
 * Представляет координаты маршрута: x (целое) и y (вещественное).
 */
public class Coordinates {
    private Long x; // не null
    private float y;
    /**
     * Создаёт объект координат.
     * @param x координата X (любое целое число)
     * @param y координата Y (любое вещественное число)
     */
    public Coordinates(Long x, float y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() { return x; }
    public float getY() { return y; }

    @Override
    public String toString() {
        return String.format("Coordinates{x=%d, y=%.2f}", x, y);
    }
}