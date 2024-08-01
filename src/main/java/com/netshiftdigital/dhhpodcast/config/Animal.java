package com.netshiftdigital.dhhpodcast.config;

class Animal {
    String color;
    int weight;

    public Animal(String color, int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.color = color;
        this.weight = weight;
        System.out.println("Initializing Animal (color: " + color + ", weight: " + weight + ")");
    }

    public void makeSound() {
        System.out.println("Generic animal sound");
    }
}

class Fish extends Animal {
    String finType;

    public Fish(String finType) throws IllegalArgumentException {
        super("red", 10); // Call superclass constructor with color and weight
        this.finType = finType;
        System.out.println("Initializing Fish (fin type: " + finType + ")");
    }

    @Override // Override makeSound from Animal
    public void makeSound() {
        System.out.println("Bloop bloop!");
    }
}

class Eagle extends Animal {
    int wingspan;

    public Eagle(String color, int weight, int wingspan) throws IllegalArgumentException {
        super(color, weight); // Call superclass constructor with color and weight
        if (wingspan <= 0) {
            throw new IllegalArgumentException("Wingspan must be positive");
        }
        this.wingspan = wingspan;
        System.out.println("Initializing Eagle (wingspan: " + wingspan + ")");
    }

    @Override // Override makeSound from Animal
    public void makeSound() {
        System.out.println("Caw!");
    }
}

 class AnimalDemo {
    public static void main(String[] args) {
        try {
//            Fish nemo = new Fish("orange", 2, "dorsal"); // Valid fish
//            nemo.makeSound(); // Prints "Bloop bloop!"

            Animal animal = new Animal("red", 40);
            animal.makeSound();
            Fish fish = new Fish("sweet");

//            Eagle liberty = new Eagle("brown", 5, 8); // Valid eagle
//            liberty.makeSound(); // Prints "Caw!"
//
//            // Invalid weight for Animal
//            new Animal("black", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
