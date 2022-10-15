import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    //Main program,
    public static void main(String[] args) throws FileNotFoundException {
        // File to Be Used
        String fileName= "src/file.txt";

        // Dont Want to Use Arguments as Input? Uncomment Line below and add name to program
        // int argss = 20;

        System.out.println("What Hash Program do You want To Run?");
        System.out.println("Robin \t Linear \t Chained \t Exit:");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        switch (answer.toLowerCase()) {
            case "robin" -> {
                System.out.println("\nHash Robin Hood Function: \n");
                robinHood.main(fileName, args);
                System.out.println("\nEnd Of Robin Hood Function \n");
            }
            case "linear" -> {
                System.out.println("\nHash Linear Function: \n");
                hashLinear.main(fileName, args);
                System.out.println("\nEnd Of Linear Function \n");
            }
            case "chained" -> {
                System.out.println("\nHash Hash Chained Function: \n");
                hashChained.main(fileName, args);
                System.out.println("\nEnd Of Hash Chained Function \n");
            }
            case "exit" -> System.exit(0);
            default -> System.out.println("Not Valid Option. Exiting...");
        }

    }
}
