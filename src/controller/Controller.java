package controller;

import java.util.Scanner;

import model.AnimalType;
//import view.View;
import view.ConsoleUI;

public class Controller {
    ConsoleUI view = new ConsoleUI();
    Scanner scanner = new Scanner(System.in);

    public void start() {
        int choice = 0;
        boolean work = true;
        while (work == true) {
            view.print("""
                    Main menu:
                    1. Add new animal
                    2. See all your animals
                    3. Edit you animals
                    4. Exit
                    """);
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice > 4 || choice < 0) {
                view.print("Enter right choice please (between 1 - 4)");
            } else {
                work = false;
            }
        }
        switch(choice) {
            case 1:
                view.addNewAnimal();
                break;
            case 2:
                seeAllAnimals();
                break;
            case 3:
                editAnimal();
                break;
            case 4:
                exit();
                break;
        }
    }

    public void addNewAnimal(String name, AnimalType animalType, String commands) {
        
    }

    public void seeAllAnimals() {

    }

    public void editAnimal() {

    }

    public void exit() {
        view.print("Exiting the program...");
    }
}
