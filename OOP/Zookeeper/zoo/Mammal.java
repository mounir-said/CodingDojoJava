package zoo;

public class Mammal {
    protected int energy;

    public Mammal() {
        this.energy = 100;
    }

    public int displayEnergy() {
        System.out.println("Energy level: " + this.energy);
        return this.energy;
    }
}
