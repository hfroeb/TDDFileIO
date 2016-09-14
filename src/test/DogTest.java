import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by halleyfroeb on 9/14/16.
 */
public class DogTest {
    public static Scanner scanner = new Scanner(System.in);
    public static Dog dog = new Dog();

    public static void main(String[] args) throws IOException {

        try {
            dog = loadAttributes();

            System.out.println("Loaded saved Dog attributes.");
            System.out.println("[1] Enter new Dog [2] Update Dog attributes");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    dog.enterAttributes();
                    saveAttributes();
                    break;
                case 2:
                    dog.updateAttributes();
                    saveAttributes();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved Dogs, Entering new Dog.");
            dog.enterAttributes();
            saveAttributes();
        }
        dog = loadAttributes();

    }

    public static void saveAttributes() throws IOException {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(dog);

        File f = new File("dogs.json");
        FileWriter fw = new FileWriter(f);
        fw.write(json);
        fw.close();
    }

    public static Dog loadAttributes() {
        File f = new File("dogs.json");
        Scanner scanner = null;
        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("oops, parsing failed!");
        }
        scanner.useDelimiter("\\Z");
        String contents = scanner.next();
        System.out.println(contents);
        scanner.close();
        JsonParser parser = new JsonParser();
        return parser.parse(contents, Dog.class);
    }
}

