package Threads;

public class ThreadRace {
    
    public static void main(String[] args) {
        
        System.out.println("main starts");
        
        int countOfSnails = 8;
//        RaceSnail[] snails = new RaceSnail[countOfSnails];
        Thread[] threads = new Thread[countOfSnails];
    
        for (int i = 0; i < countOfSnails; i++) {
            RaceSnail snail = new RaceSnail("racer-" + i);
//            snails[i] = snail;
//            try {
                Thread thread = new Thread(snail);
                threads[i] = thread;
                thread.start();
//                thread.start();
//                snail.start();
//            } catch (InterruptedException ex) {
//                System.out.println(ex.getMessage());
//            }
        }
        
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        System.out.println("main is done");
    }
    
    public void declareWinner(String message) {
        System.out.println(message + " <<has won>>");
    }
    
}

// TODO

// 4. declare winner
// 6. style results - later
