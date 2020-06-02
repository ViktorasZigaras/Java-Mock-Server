package Threads;

public class RaceSnail extends Thread {
    
    private final Race parent;
    private final String nameOwn;
    private int distance = 0;
    private int milestoneCount = 1;
    private final int maxDistance = 500;
    private final int milestone = 100;
    private final int minSpeed = 5;
    private final int maxSpeed = 10;
    private final int speed = maxSpeed - minSpeed;
    private final int minSleep = 1; // ms
    private final int maxSleep = 3; // ms
    private final int sleep = maxSleep - minSleep; // ms
    
    public RaceSnail(String name, Race parent){
        this.nameOwn = name;
        this.parent = parent;
    }
    
    // currently unused
    public String getNameOwn() {return this.nameOwn;}
    public int getDistance() {return this.distance;}

    @Override
    public void run() {
        while (this.distance < this.maxDistance) {
            this.distance += (int) (Math.random() * speed + minSpeed);
            if (this.distance - this.milestone * this.milestoneCount > 0) {
                this.milestoneCount++;
                System.out.println(this.nameOwn + " now travelled: " + this.distance);
            }
            try {
                Thread.sleep((int) (Math.random() * sleep + minSleep));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        String message = this.nameOwn + " done, travelled: " + this.distance;
        System.out.println(message);
        parent.declareWinner(message);
    }
    
}
