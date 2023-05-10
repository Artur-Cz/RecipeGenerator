import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe implements Serializable {

    transient String ingredientsXpath = "//*[@id=\"RecipeCard\"]/p";
    transient String preparationXpath = "//*[@id=\"RecipeCard\"]/div[@class=\"hyphenate\"]/ol";
    transient String additionalInfoXpath = "//*[@id=\"RecipeCard\"]/div[@class=\"hyphenate\"]/ul";
    transient String titleXpath = "/html/head/title";

    private final String url;
    //TODO: Extract driver to separate entity?
    transient final WebDriver driver;
    private final ArrayList<String> ingredients;
    private final ArrayList<String> preparation;
    private final ArrayList<String> additionalInfo;

    public Recipe(String url, WebDriver driver) {
        this.url = url;
        this.driver = driver;
        this.ingredients = new ArrayList<>();
        this.preparation = new ArrayList<>();
        this.additionalInfo = new ArrayList<>();

        parseUrlToInformation(url, driver, ingredientsXpath, this.ingredients);
        parseUrlToInformation(url, driver, preparationXpath, this.preparation);
        parseUrlToInformation(url, driver, additionalInfoXpath, this.additionalInfo);
    }
    private void parseUrlToInformation(String url, WebDriver driver, String xpath, ArrayList<String> stringContent) {
        driver.get(url);
        List<WebElement> urlContent = driver.findElements(By.xpath(xpath));

        for(WebElement element : urlContent){
            stringContent.addAll(Arrays.asList(element.getText().split("\n")));
        }
    }

    public void printTitle(String url, WebDriver driver) {
        driver.get(url);

        String recipeTitle = driver.getTitle();
        String[] titleDivider = recipeTitle.split(" » ");
        System.out.println(titleDivider[0].toUpperCase() + "\n");
    }

    public ArrayList<String> getIngredients(){
        return ingredients;
    }

    public ArrayList<String> getPreparation() {
        return preparation;
    }

    public ArrayList<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public String getUrl() {
        return url;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
