package zoo;

public class Bat extends Mammal {

    public Bat() {
        this.energy = 300;
    }

    public void fly() {
        this.energy -= 50;
        System.out.println("The bat is airborne.");
        displayEnergy();
    }

    public void eatHumans() {
        this.energy += 25;
        System.out.println("The bat is satisfied after eating humans.");
        displayEnergy();
    }

    public void attackTown() {
        this.energy -= 100;
        System.out.println("The bat is attacking a town.");
        displayEnergy();
    }
}
