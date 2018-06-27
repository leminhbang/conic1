package jp.co.conic.conic1;

public class CalculationHelper {

    private String tooling_type;
    private String drive_system;
    private float material;
    private int figure;
    private float dimension_a;
    private float dimension_b;
    private float angle_r;
    private float thickness;

    ProductType station;

    public CalculationHelper(String tooling_type,
                             String drive_system,
                             float material,
                             int figure,
                             float dimension_a,
                             float dimension_b,
                             float angle_r,
                             float thickness) {
        this.tooling_type = tooling_type;
        this.drive_system = drive_system;
        this.material = material;
        this.figure = figure;
        this.dimension_a = dimension_a;
        this.dimension_b = dimension_b;
        this.angle_r = angle_r;
        this.thickness = thickness;
    }

    public float getPerimeter() {
        if(this.figure == 0) {
            return this.dimension_a * (float)3.14;
        }
        if(this.figure == 1 || this.figure == 2) {
            return (this.dimension_a + this.dimension_b) * 2;
        }
        return -1;
    }

    public float getClearance() {
        return -1;
    }

    public float getPunchingForce() {
        return (this.material * this.getPerimeter() * this.thickness) / 1000;
    }

    public float getDiameter() {
        if(this.figure == 0) {
            return this.dimension_a * (float)3.14;
        }
        if(this.figure == 1) {
            return (float)Math.sqrt(this.dimension_a*this.dimension_a + this.dimension_b*this.dimension_b);
        }
        if(this.figure == 2) {
            float angle = 2 * this.angle_r * (float)3.14;
            return (float)Math.sqrt((float)Math.exp(this.dimension_a - angle) + (float)Math.exp(this.dimension_b - angle)) + angle;
        }
        return -1;
    }

    public String getStation() {
        station = new ProductType(this.tooling_type, this.getDiameter());
        return station.getStation(this.tooling_type, this.getDiameter());
    }
}
