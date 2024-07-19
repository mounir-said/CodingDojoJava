package zoo;

public class Gorilla extends Mammal {

    public void throwSomething() {
        this.energy -= 5;
        System.out.println("The gorilla has thrown something.");
        displayEnergy();
    }

    public void eatBananas() {
        this.energy += 10;
        System.out.println("The gorilla is satisfied after eating bananas.");
        displayEnergy();
    }

    public void climb() {
        this.energy -= 10;
        System.out.println("The gorilla has climbed a tree.");
        displayEnergy();
    }
}
