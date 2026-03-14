package model;
/**
 * Представляет географическую точку с трёхмерными координатами и названием.
 */
public class Location {
    private double x;
    private float y;
    private float z;
    private String name; // Длина ≤ 203, не null
    /**
     * Создаёт объект местоположения.
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     * @param name название точки (не пустое)
     */
    public Location(double x, float y, float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public double getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("Location{x=%.2f, y=%.2f, z=%.2f, name='%s'}", x, y, z, name);
    }
}