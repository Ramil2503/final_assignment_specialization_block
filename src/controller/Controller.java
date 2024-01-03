package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Animal;
import model.AnimalType;
import model.homeAnimal;
//import view.View;
import view.ConsoleUI;

public class Controller {
    ConsoleUI view = new ConsoleUI();
    Scanner scanner = new Scanner(System.in);
    private List<Animal> animalList = new ArrayList<>();
    private static final String FILE_PATH = "animals.csv";

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

    private void loadAnimalsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Animal animal = new homeAnimal(data[0], AnimalType.valueOf(data[1]), data[2], data[3]);
                    animalList.add(animal);
                }
            }
        } catch (IOException e) {
            view.print("Error loading animals from file: " + e.getMessage());
        }
    }

    private void saveAnimalsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Animal animal : animalList) {
                writer.write(animal.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            view.print("Error saving animals to file: " + e.getMessage());
        }
    }
}
