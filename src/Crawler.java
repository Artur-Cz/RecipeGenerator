
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roshanalwis on 9/4/17.
 */

 // TODO:
//  -loadMoreRecipes


public class Crawler {
    private static final String recipeListUrl = "https://www.jadlonomia.com/rodzaj_dania/dania-glowne/";

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

    public static void clickElement(WebDriver driver, WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    public static void loadMoreRecipes(WebDriver driver,  int maxPageLoads) throws InterruptedException {
        // zrob ponizsze kroki az nie bedzie widoczny "zaladuj wiecej" lub x sekund?// Znajdz Webelemnt "zaladuj wiecej"
        // Click znaleziony webelement
        // poczekaj ???
        //driver.get(url);
        WebElement loadMore = driver.findElement(By.xpath("//*[@id=\"LoadMore\"]"));

        WebElement cookies = driver.findElement(By.xpath("//button[@class=\"cbar-button technical\"]"));
        clickElement(driver, cookies);

        while(loadMore.isDisplayed()) {
            clickElement(driver, loadMore);

            synchronized (driver) {
                driver.wait(1000);
            }

            loadMore = driver.findElement(By.xpath("//*[@id=\"LoadMore\"]"));
        }
    }

    public static void run() throws InterruptedException {
        System.setProperty("webdriver.chrome.whitelistedIps", "");

        ChromeOptions chromeOptions = new ChromeOptions();
        setChromeOptions(chromeOptions);

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get(recipeListUrl);
        loadMoreRecipes(driver, 2);
        parseRecipes(driver);
        

//        WebElement recipe = driver.findElement(By.xpath("//article[@itemtype=\"http://schema.org/Recipe\"]"));
//        WebElement recipeParent = driver.findElement(By.xpath("//article[@itemtype=\"http://schema.org/Recipe\"]/.."));
//
//        List<WebElement> recipes = recipeParent.findElements(By.xpath("//a[@itemprop=\"url\"]"));
//
//
//        List<String> recipeLinks = new ArrayList<>();
//
//        System.out.println(recipe);
//        System.out.println(recipe.getAttribute("itemtype"));
//
//        addRecipes(recipeLinks, recipes);

    //    int i=0;
    //    for(String element : recipeLinks){
    //         i++;
    //        System.out.println(i+element);
    //    }

//        ArrayList<Recipe> recipeList = new ArrayList<>();
//
//        for(String another_url : recipeLinks){
//           recipeList.add(new Recipe(another_url, driver));
//        }

//todo randomizer do osobnej klasy
        
//        int randomRecipeIndex = (int)(Math.random()*recipeList.size());
//
//        Recipe object = recipeList.get(randomRecipeIndex);
//
//        object.printTitle(object.getUrl(), object.getDriver());
//
//        printData(object.getIngredients());
//        printData(object.getPreparation());
//        printData(object.getAdditionalInfo());

        // Close driver
        driver.quit();
    }
//todo: print do osobnej klasy
    
//    public static void printData(ArrayList<String> dataList) {
//        for(int i = 0; i < dataList.size(); i++) {
//            System.out.println((i + 1) + ". " + dataList.get(i));
//        }
//        System.out.print("\n");
//    }

    private static void setChromeOptions(ChromeOptions chromeOptions) {
        // TODO: Check which options are needed
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
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
    }

    private static void parseRecipes(WebDriver driver) {
        WebElement recipe = driver.findElement(By.xpath("//article[@itemtype=\"http://schema.org/Recipe\"]"));
        WebElement recipeParent = driver.findElement(By.xpath("//article[@itemtype=\"http://schema.org/Recipe\"]/.."));
        DatabaseHandler saver = new DatabaseHandler();

        List<WebElement> recipes = recipeParent.findElements(By.xpath("//a[@itemprop=\"url\"]"));


        List<String> recipeLinks = new ArrayList<>();

        System.out.println(recipe);
        System.out.println(recipe.getAttribute("itemtype"));

        addRecipes(recipeLinks, recipes);

        ArrayList<Recipe> recipeList = new ArrayList<>();

        for(String another_url : recipeLinks){
            recipeList.add(new Recipe(another_url, driver));
        }
        System.out.println(recipeList.get(recipeList.size()-1).getAdditionalInfo());
        saver.save(recipeList.get(0).getUrl(), recipeList.get(0).getDriver());

//        for (Recipe value : recipeList) {
//            saver.save(value.getUrl(), value.getDriver());
//            saver.load(value.getUrl(), value.getDriver());
//        }

    }
}
