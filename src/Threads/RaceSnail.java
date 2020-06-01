package Threads;

public class RaceSnail extends Thread {
    
    private ThreadRace parent;
    private String nameOwn;
    private int distance = 0;
    private int milestoneCount = 1;
    private int maxDistance = 500;
    private int milestone = 100;
    private int minSpeed = 5;
    private int maxSpeed = 10;
    private int speed = maxSpeed - minSpeed;
    private int minSleep = 1; // ms
    private int maxSleep = 3; // ms
    private int sleep = maxSleep - minSleep; // ms
    
    public RaceSnail(String name/*, ThreadRace parent*/){
        this.nameOwn = name;
        this.parent = parent;
    }
    
    public String getNameOwn() {return this.nameOwn;}
    
    public int getDistance() {return this.distance;}
    
//    public void setDistance(int distance) {this.distance = distance;}
    
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
//        parent.declareWinner(message);
//        System.out.println(message + " <<has won>>");
    }
    
}
