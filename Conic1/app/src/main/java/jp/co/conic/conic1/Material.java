package jp.co.conic.conic1;

public class Material {
    private int id;
    private String name;
    private float shear;
    private float clearance;

    public Material(int id, String name, float shear, float clearance) {
        this.id = id;
        this.name = name;
        this.shear = shear;
        this.clearance = clearance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getShear() {
        return shear;
    }

    public float getClearance() {
        return clearance;
    }
}
