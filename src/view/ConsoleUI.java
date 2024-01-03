package view;

import java.util.Scanner;

import controller.Controller;
import model.AnimalType;

public class ConsoleUI implements View {
    Scanner scanner = new Scanner(System.in);
    Controller controller = new Controller();

    public void print(String text) {
        System.out.println(text);
    }

    public void addNewAnimal() {
        String name;
        AnimalType animalType = null;
        String commands;
        print("Enter the animal name you want to add: ");
        name = scanner.nextLine();
        print("To which animal type this animals belongs to: ");
        print("""
                1. Home animal
                2. Pack animal
                """);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch(choice) {
            case 1:
                animalType = AnimalType.home_animal;
            case 2:
                animalType = AnimalType.pack_animal;
        }
        print("Write which commands this animal can do:");
        commands = scanner.nextLine();
        controller.addNewAnimal(name, animalType, commands);
    }
}
