
import com.google.common.collect.Iterables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roshanalwis on 9/4/17.
 */

 // TODO:
//  -loadMoreRecipes


public class Crawler {

    public static void addRecipes(List<String> recipeLinks, List<WebElement> recipes) {
        // todo: remove hack
        int hack =0;
        for(WebElement element : recipes){
            hack++;
            if(hack >= 10){
                break;
            }
            if (element.getAttribute("href").contains("przepisy")) {
                recipeLinks.add(element.getAttribute("href"));
            }
        }
    }
    public static void loadMoreRecipes(WebDriver driver,  int maxPageLoads) throws InterruptedException {
        // zrob ponizsze kroki az nie bedzie widoczny "zaladuj wiecej" lub x sekund?// Znajdz Webelemnt "zaladuj wiecej"
        // Click znaleziony webelement
        // poczekaj ???
        //driver.get(url);
        WebElement loadMore = driver.findElement(By.xpath("//*[@id=\"LoadMore\"]"));

        WebElement cookies = driver.findElement(By.xpath("//button[@class=\"cbar-button technical\"]"));
        cookies.click();

        while(loadMore.isDisplayed()) {
            loadMore.click();

            synchronized (driver) {
                driver.wait(1000);
            }

            loadMore = driver.findElement(By.xpath("//*[@id=\"LoadMore\"]"));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = "https://www.jadlonomia.com/rodzaj_dania/dania-glowne/";

        System.setProperty("webdriver.chrome.whitelistedIps", "");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments("--safebrowsing-disable-download-protection");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-setuid-sandbox");
        chromeOptions.addArguments("--no-first-run");
        chromeOptions.addArguments("--safebrowsing-disable-auto-update ");
        chromeOptions.addArguments("--disable-background-networking");
        chromeOptions.addArguments("--headless", "--enable-logging", "--window-size=1920x1080");
        chromeOptions.addArguments("--no-proxy-server");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--dns-prefetch-disable");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--force-device-scale-factor=1");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);

        // Maximize browser window
        driver.manage().window().maximize();

        // Navigate to URL
        driver.get(url);
        loadMoreRecipes(driver, 2);

        // Find the table element using xpath
        // First element matching. How to find all of them? Collect in a list
        WebElement recipe = driver.findElement(By.xpath("//article[@itemtype=\"http://schema.org/Recipe\"]"));
        WebElement recipeParent = driver.findElement(By.xpath("//article[@itemtype=\"http://schema.org/Recipe\"]/.."));

                // Go through each major version
        List<WebElement> recipes = recipeParent.findElements(By.xpath("//a[@itemprop=\"url\"]"));


        List<String> recipeLinks = new ArrayList<>();

        System.out.println(recipe);
        System.out.println(recipe.getAttribute("itemtype"));

        addRecipes(recipeLinks, recipes);

//        for(String element : recipeLinks){
//            System.out.println(element);
//        }

        ArrayList<Recipe> recipeList = new ArrayList<>();

        for(String another_url : recipeLinks){
           recipeList.add(new Recipe(another_url, driver));
        }

        int randomRecipeIndex = (int)(Math.random()*recipeList.size());

        Recipe object = recipeList.get(randomRecipeIndex);

        object.printTitle(object.getUrl(), object.getDriver());

        printData(object.getIngredients(), object);
        printData(object.getPreparation(), object);
        printData(object.getAdditionalInfo(), object);

        // Close driver
        driver.quit();
    }

    public static void printData(ArrayList<String> dataList, Recipe object) {
        for(int i = 0; i < dataList.size(); i++) {
            System.out.println((i + 1) + ". " + dataList.get(i));
        }
        System.out.print("\n");
    }
}
