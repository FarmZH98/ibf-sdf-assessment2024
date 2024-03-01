
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class App {

    public static List<List<String>> globalPokemonLists = new ArrayList<>();
    public static Map<Integer, List<String>> globalPokemonStackMap = new HashMap<>();
    public static void main(String[] args) throws Exception {

        // Run Your Code here
        String initialFileName = args[0];
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
        
        printHeader();
        while (true) {
            printMenu();
            String input = cons.readLine("Enter your selection >");
            if (input.equals("q")) {
                return;
            } else if (input.equals("1")) {
                String stackNumRaw = cons.readLine("Display the list of unique Pokemon in stack >\n");
                int stackNum = Integer.parseInt(stackNumRaw);
                printUniquePokemonStack(stackNum);
                pressAnyKeyToContinue();
            } else if (input.equals("2")) {
                String pokemon = cons.readLine("Search for the next occurrence of 5 stars Pokemon in all stacks based on entered Pokemon > \n");
                printNext5StarsPokemon(pokemon);
                pressAnyKeyToContinue();
            } else if (input.equals("3")) {
                String newPokemonStack = cons.readLine("Create a new Pokemon stack and save to a new file >\n");
                String fileNameToSave = cons.readLine("Enter a filename to save (e.g. path/filename.csv) >\n");
                savePokemonStack(newPokemonStack, fileNameToSave);
                pressAnyKeyToContinue();
            } else if (input.equals("4")) {
                printPokemonCardCount();
                pressAnyKeyToContinue();
            } else if (input.equals("clear")) {
                clearConsole();
            } else {
                System.out.println("Invalid input, please try again.");
            }
            
        }

    }

    public static void clearConsole() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Task 1
    public static void pressAnyKeyToContinue() { //this is not in task 1??
        // your code here
        Console cons = System.console();
        cons.readLine("Press any key to continue..."); //must still enter....
    }

    // Task 1
    public static void printHeader() {

        // Task 1 - your code here
        System.out.println("Welcome to Pokemon Gaole Legend 4 Rush 2");
        System.out.println("");

    }

    public static void printMenu() {
        
        System.out.println("(1) View unique list of Pokemon in the selected stack");
        System.out.println("(2) Find next 5 stars Pokemon occurrence");
        System.out.println("(3) Create new Pokemon stack and save (append) to csv file");
        System.out.println("(4) Print distinct Pokemon and cards count");
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
        //added some error handling to ensure the pokemon name and number of cards per stack is correct (this is not asked in exam paper)
        String[] pokemonsArray = pokemonStack.split(",");
        if(pokemonsArray.length < 50) { 
            System.err.println("Error in input stack: Each stack should contain 50 cards. Please try again.");
            return;
        } 
        for(int i=0; i<pokemonsArray.length; ++i) {
            String[] pokemonAttr = pokemonsArray[i].split(" ");
            if(!(pokemonAttr[0].equals("1*") || pokemonAttr[0].equals("2*") || pokemonAttr[0].equals("3*") || pokemonAttr[0].equals("4*") || pokemonAttr[0].equals("5*") || pokemonAttr[0].equals("L*"))) {
                System.err.println("Error in pokemon name: Pokemon should be of the following format: <1-5>* <PokemonName>");
                System.err.println("Pokemon with error: " + pokemonsArray[i] + ". Please try again.");
                return;
            }
        }

        FileService fileService = new FileService();
        fileService.writeAsCSV(pokemonStack, filename);
    }

    // Task 2
    public static void printUniquePokemonStack(Integer stack) {
        // Task 2 - your code here
        if(stack > 8 || stack < 1) {
            System.err.println("Error: Stack number should be of a value between 1 to 8.");
            return;
        }
        List<String> pokemonStackToPrint = globalPokemonStackMap.get(stack);

        //filter using LinkedHashSet for maintain stability
        LinkedHashSet<String> stackHash = new LinkedHashSet<>();
        for(int i=0; i<pokemonStackToPrint.size(); ++i) {
            stackHash.add(pokemonStackToPrint.get(i));
        }

        //print unique list
        int index = 1;
        for(String item : stackHash) {
            System.out.println(index + " ==> " + item);
            index++;
        }
    }

    // Task 2
    public static void printNext5StarsPokemon(String enteredPokemon) {
        // Task 2 - your code here
        for(int i = 0; i<globalPokemonLists.size(); ++i) {
            System.out.println("Set " + (i+1));
            List<String> pokemonStack = globalPokemonLists.get(i);

            int pokemonFoundIndex = -1;
            int fiveStarPokemonFoundIndex = -1;
            for(int j=0; j<pokemonStack.size(); ++j) {
                if(pokemonStack.get(j).equals(enteredPokemon)) {
                    pokemonFoundIndex = j;
                    
                } else if(pokemonFoundIndex > -1 && pokemonStack.get(j).startsWith("5*")) {
                    fiveStarPokemonFoundIndex = j;
                    break;
                }
            }

            if (pokemonFoundIndex == -1 && fiveStarPokemonFoundIndex == -1) {
                System.out.println(enteredPokemon + " not found in this set");
            } else if (pokemonFoundIndex > -1 && fiveStarPokemonFoundIndex == -1) {
                System.out.println("No 5 stars Pokemon found subsequently in the stack.");
            } else if (pokemonFoundIndex > -1 && fiveStarPokemonFoundIndex > -1 ) {
                System.out.println(pokemonStack.get(fiveStarPokemonFoundIndex) + ">>>" + (fiveStarPokemonFoundIndex - pokemonFoundIndex) + " cards to go.");
            }
        }
    }

    // Task 2
    public static void printPokemonCardCount() {
        // Task 2 - your code here

        //use of LinkedHashMap as it is able to stable sort. In this case, the program will print out pokemon with earlier initial occurrence first, if the count is same
        //Initial use of HashMap has been commented out, as it is not stable sorted
        LinkedHashMap<String, Integer> pokemonLinkedHashMap = new LinkedHashMap<>();
        for(int i = 0; i<globalPokemonLists.size(); ++i) {
            List<String> pokemonStack = globalPokemonLists.get(i);

            for(int j=0; j<pokemonStack.size(); ++j) {
                String currentPokemon = pokemonStack.get(j);
                if(pokemonLinkedHashMap.containsKey(currentPokemon)) {
                    pokemonLinkedHashMap.put(currentPokemon, pokemonLinkedHashMap.get(currentPokemon) + 1);
                } else {
                    pokemonLinkedHashMap.put(currentPokemon, 0);
                }
            }
        }

        //https://stackoverflow.com/questions/12184378/sorting-linkedhashmap 
        //https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values=> Sorting below was referred from StackOverflow
        LinkedHashMap<String, Integer> sortedLinkedHashMap = pokemonLinkedHashMap.entrySet().stream() //set to stream
                    .sorted(Entry.<String, Integer>comparingByValue().reversed()) //reverse order
                    .limit(10)
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, //
                                            (e1, e2) -> e1, LinkedHashMap::new));

        Integer index = 0;
        for (String key : sortedLinkedHashMap.keySet()) {
            System.out.println("Pokemon " + (++index) + ": " + key + ", Cards Count: " + sortedLinkedHashMap.get(key));
        }

        // HashMap <String, Integer> pokemonsHashMap = new HashMap<>();
        // for(int i = 0; i<globalPokemonLists.size(); ++i) {
        //     List<String> pokemonStack = globalPokemonLists.get(i);

        //     for(int j=0; j<pokemonStack.size(); ++j) {
        //         String currentPokemon = pokemonStack.get(j);
        //         if(pokemonsHashMap.containsKey(currentPokemon)) {
        //             pokemonsHashMap.put(currentPokemon, pokemonsHashMap.get(currentPokemon) + 1);
        //         } else {
        //             pokemonsHashMap.put(currentPokemon, 0);
        //         }
        //     }
        // }

        // HashMap<String, Integer> sortedMap = 
        //                     pokemonsHashMap.entrySet().stream() //set to stream
        //                     .sorted(Entry.<String, Integer>comparingByValue().reversed()) //reverse order
        //                     .collect(Collectors.toMap(Entry::getKey, Entry::getValue, //
        //                                             (e1, e2) -> e1, LinkedHashMap::new));
        // int index = 0;

        // for (String key : sortedMap.keySet()) {
        //     System.out.println("Pokemon " + (++index) + ": " + key + ", Cards Count: " + sortedMap.get(key));
        //     if(index == 10) break;
        // }
    }

}
