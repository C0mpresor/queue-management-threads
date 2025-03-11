package model;


import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private int ID;
    private int arrivalTime;
    private AtomicInteger serviceTime ;
    public Task(int ID, int arrivalTime, int serviceTime){
        this.arrivalTime  = arrivalTime;
        this.ID = ID;
        this.serviceTime=new AtomicInteger(serviceTime);
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime.set(serviceTime);
    }

    public String toString(){
        return "(" + this.ID + ", " + this.arrivalTime + ", " + this.serviceTime + ")";
    }



}
