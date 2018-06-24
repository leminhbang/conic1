package jp.co.conic.conic1;

public class CalculationHelper {

    private String tooling_type;
    private String drive_system;
    private String material;
    private float perimeter;
    private float thickness;

    ProductType station;

    public CalculationHelper(String tooling_type,
                             String drive_system,
                             String material,
                             float perimeter,
                             float thickness) {
        this.tooling_type = tooling_type;
        this.drive_system = drive_system;
        this.material = material;
        this.perimeter = perimeter;
        this.thickness = thickness;
    }

    public float getPunchingForce(float material, float perimeter, float thickness) {
        float result = (material * perimeter * thickness) / 1000;
        return result;
    }

    public float getDiameter(int position, float a, float b, int r) {
        if(position == 0) {
            return a * (float)3.14;
        }
        if(position == 1) {
            return (float)Math.sqrt(a*a + b*b);
        }
        if(position == 2) {
            float angle = 2 * r * (float)3.14;
            return (float)Math.sqrt((float)Math.exp(a - angle) + (float)Math.exp(b - angle)) + angle;
        }
        return -1;
    }

    public String getStation(int position, float a, float b, int r) {
        station = new ProductType(this.tooling_type, this.getDiameter(position, a, b, r));
        return station.getStation(this.tooling_type, this.getDiameter(position, a, b, r));
    }
}
