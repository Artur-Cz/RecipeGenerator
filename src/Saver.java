import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Saver {
    public void saving() {
        try {
            BufferedWriter recipeWriter = new BufferedWriter(new FileWriter("recipeList.txt"));
            recipeWriter.write("gówno");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    
}
