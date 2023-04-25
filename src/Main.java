public class Main {
    public static void main(String[] args) throws InterruptedException {
        Crawler crawler  = new Crawler();
        DatabaseHandler databaseHandler = new DatabaseHandler();
        crawler.run();
    }
}
