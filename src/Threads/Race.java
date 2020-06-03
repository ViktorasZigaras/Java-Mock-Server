package Threads;

public class Race extends Thread {
    
    private String winner = "";
    
    public void run() {
        int countOfSnails = 8;
        Thread[] threads = new Thread[countOfSnails];
    
        for (int i = 0; i < countOfSnails; i++) {
            RaceSnail snail = new RaceSnail("racer-" + i, this);
            Thread thread = new Thread(snail);
            threads[i] = thread;
            thread.start();
        }
        
        for (Thread thread: threads) {
            try {thread.join();} catch (InterruptedException ex) {System.out.println(ex.getMessage());}
        }
        System.out.println("");
        System.out.println(this.winner);
    }
    
    public synchronized void declareWinner(String message) {
        if ("".equals(this.winner)) this.winner = message + " <<has won>>";
    }
    
}
