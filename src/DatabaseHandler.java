import java.io.*;

import java.util.ArrayList;

public class DatabaseHandler implements Serializable {
    private final String dbFileName = "recipeList.txt";
    private boolean isDBInitialized = false;


    public void save (Recipe outputRecipe) {
        if(!isDBInitialized){
            isDBInitialized = true;
            File recipeDirectory = new File(dbFileName);
            recipeDirectory.delete();
            initializeSave();
        }
        try {
            FileOutputStream recipeSaverDirectory = new FileOutputStream(dbFileName, true);
            ObjectOutputStream recipeSaverObject = new AppendingObjectOutputStream(recipeSaverDirectory);
            recipeSaverObject.writeObject(outputRecipe);
            recipeSaverObject.flush();
            recipeSaverObject.close();
            System.out.println("Object stored successfully.");
        } catch (Exception e) {
            System.out.println("Error when saving a file.");
        }
    }

    public void initializeSave() {
        try {
            FileOutputStream recipeSaverInitDirectory = new FileOutputStream(dbFileName);
            ObjectOutputStream recipeSaverInitObject = new ObjectOutputStream(recipeSaverInitDirectory);
            recipeSaverInitObject.close();

            System.out.println("Object created successfully.");
        } catch (Exception e) {
            System.out.println("Error when created a file.");
        }
    }

    public Recipe load() {
        try {
            FileInputStream recipeLoaderDirectory = new FileInputStream(dbFileName);
            ObjectInputStream recipeLoaderObject = new ObjectInputStream(recipeLoaderDirectory);
            Recipe inputRecipe = (Recipe) recipeLoaderObject.readObject();
            recipeLoaderObject.close();

            return inputRecipe;
        } catch (Exception e) {
            System.out.println("Error when loading a file.");
            return null;
        }
    }
    //todo: stworzyć w crawlerze porównywarkę url obiektów załadowanych z pliku;

    public ArrayList<Recipe> loadAll() {
        ArrayList<Recipe> objects = new ArrayList<>();

        try (FileInputStream fileIn = new FileInputStream(dbFileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            // read objects until end of file
            while (fileIn.available() > 0) {
                Recipe object = (Recipe) objectIn.readObject();
                objects.add(object);
            }

            System.out.println("Successfully read " + objects.size() + " objects from file.");

            return objects;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
