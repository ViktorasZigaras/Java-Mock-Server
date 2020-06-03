package Threads;

public class RaceSnail extends Thread {
    
    private Race parent;
    private String nameOwn;
    private int distance = 0;
    private int milestoneCount = 1;
    private final int MAX_DISTANCE = 500;
    private final int MILESTONE = 100;
    private final int MIN_SPEED = 5;
    private final int MAX_SPEED = 10;
    private final int SPEED = MAX_SPEED - MIN_SPEED;
    private final int MIN_SLEEP = 1; // ms
    private final int MAX_SLEEP = 3; // ms
    private final int SLEEP = MAX_SLEEP - MIN_SLEEP; // ms
    
    public RaceSnail(String name, Race parent){
        this.nameOwn = name;
        this.parent = parent;
    }
    
    @Override
    public void run() {
        while (this.distance < this.MAX_DISTANCE) {
            this.distance += (int) (Math.random() * this.SPEED + this.MIN_SPEED);
            if (this.distance - this.MILESTONE * this.milestoneCount > 0) {
                this.milestoneCount++;
                System.out.println(this.nameOwn + " now travelled: " + this.distance);
            }
            try {Thread.sleep((int) (Math.random() * this.SLEEP + this.MIN_SLEEP));} 
            catch (InterruptedException ex) {System.out.println(ex.getMessage());}
        }
        String message = this.nameOwn + " done, travelled: " + this.distance;
        System.out.println(message);
        parent.declareWinner(message);
    }
    
}
