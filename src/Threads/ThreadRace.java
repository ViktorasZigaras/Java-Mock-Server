package Threads;

public class ThreadRace {
    
    public static void main(String[] args) {
        
        System.out.println("main starts");
        
        Race race = new Race();
        race.start();
        try {
            race.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("main is done");
    }
    
}