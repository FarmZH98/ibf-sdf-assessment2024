import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileService {
    
    //this function returns raw list, where each element contains the entire row of pokemons in csv file
    //main program needs to handle the filtering if needed
    public List<String> ReadCSV(String fullPathFilename) {
        // Task 1 - your code here
        List<String> pokemonList = new ArrayList<>();

            //get csv file data and create persons
            try {
                BufferedReader br = new BufferedReader(new FileReader(fullPathFilename));
                String temp = br.readLine(); 

                while(temp != null) {
                    pokemonList.add(temp);
                    temp = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("You have provided a wrong file/path name. Please try again.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        return pokemonList;
    }

    public void writeAsCSV(String pokemons, String fullPathFilename) {
        // Task 1 - your code here
        try {
            File fileToWrite = new File(fullPathFilename);
            if(!fileToWrite.exists()) {
                fileToWrite.createNewFile();
            }

            FileWriter fw = new FileWriter(fileToWrite, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(pokemons + "\n");

            bw.flush();
            bw.close();
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            System.err.println("An IOException occured.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An Exception occured.");
            e.printStackTrace();
        }
    }
}
