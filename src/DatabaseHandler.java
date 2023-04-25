import org.openqa.selenium.WebDriver;
import java.io.*;


public class DatabaseHandler {
    public void save(String url, WebDriver driver) {
        Recipe outputRecipe = new Recipe (url, driver);
        try {
            FileOutputStream recipeSaverDirectory = new FileOutputStream("recipeList.txt");
            ObjectOutputStream recipeSaverObject = new ObjectOutputStream(recipeSaverDirectory);
            recipeSaverObject.writeObject(outputRecipe);
            recipeSaverObject.flush();
            recipeSaverObject.close();
            System.out.println("Object stored successfully.");
        } catch (Exception e) {
            System.out.println("Error when saving a file.");
        }
    }

    public void load(String url, WebDriver driver) {
        Recipe inputRecipe = new Recipe (url, driver);
        try {
            FileInputStream recipeLoaderDirectory = new FileInputStream("recipe.txt");
            ObjectInputStream recipeLoaderObject = new ObjectInputStream(recipeLoaderDirectory);
            inputRecipe = (Recipe) recipeLoaderObject.readObject();

            recipeLoaderObject.close();
        } catch (Exception e) {
            System.out.println("Error when loading a file.");
        }
    }
}
