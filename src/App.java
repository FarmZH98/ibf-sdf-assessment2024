
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App {

    public static List<List<String>> globalPokemonLists = new ArrayList<>();
    public static Map<Integer, List<String>> globalPokemonStackMap = new HashMap<>();
    public static void main(String[] args) throws Exception {

        // Run Your Code here
        String initialFileName = args[0];
        printHeader();
        Console cons = System.console();
        FileService fileService = new FileService();
        List<String> rawPokemonList = fileService.ReadCSV(initialFileName);
        
        //add into global pokemon list
        for(String item : rawPokemonList) {
            //System.out.println(item);
            List<String> dummyList = Arrays.asList(item.split(","));
            globalPokemonLists.add(dummyList);
            //System.out.println(dummyList); //testing purpose
        }
        
        //add into global Map - key: pokemon stack value, value: List of string containing pokemons in the stack
        for(int i=0; i<globalPokemonLists.size(); ++i) {
            globalPokemonStackMap.put(i+1, globalPokemonLists.get(i));
        }
        

        String input = cons.readLine("Enter your selection >").toLowerCase();
        if (input.equals("q")) {
            return;
        } else if (input.equals("4")) {
            String newPokemonStack = cons.readLine("Create a new Pokemon stack and save to a new file >\n");
            String fileNameToSave = cons.readLine("Enter a filename to save (e.g. path/filename.csv) >\n");
            fileService.writeAsCSV(newPokemonStack, fileNameToSave);
        }   
    
        
        
        
    }

    public static void clearConsole() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Task 1
    public static void pressAnyKeyToContinue() {
        // your code here
    }

    // Task 1
    public static void printHeader() {

        // Task 1 - your code here
        System.out.println("Welcome to Gaole Legend 4 Rush 2");
        System.out.println("(1) View the list of Pokemon in the selected stack");
        System.out.println("(2) View unique list of Pokemon in the selected stack");
        System.out.println("(3) Find next 5 stars Pokemon occurrence");
        System.out.println("(4) Create new Pokemon stack and save (append) to csv file");
        
        System.out.println("(q) to exit the program");
    }

    // Task 1
    public static void printExitMessage() {

        // Task 1 - your code here
        System.out.println("Thank you for using the program...");
        System.out.println("Hope to see you soon...");
    }

    // Task 1
    public static void savePokemonStack(String pokemonStack, String filename) {

        // Task 1 - your code here
    }

    // Task 2
    public static void printUniquePokemonStack(Integer stack) {
        // Task 2 - your code here
    }

    // Task 2
    public static void printNext5StarsPokemon(String enteredPokemon) {
        // Task 2 - your code here

    }

    // Task 2
    public static void printPokemonCardCount() {
        // Task 2 - your code here
    }

}
