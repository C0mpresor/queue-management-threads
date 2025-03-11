package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod = new AtomicInteger(0); // Initialize waitingPeriod

    private int maxTasksPerServer = 200;
    private int ID;

    public Server(int ID) {
        this.tasks = new LinkedBlockingDeque<Task>();
        this.waitingPeriod = new AtomicInteger(0);
        this.ID = ID;
    }


    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public void run() {
        while (true) {
            try{
                Task newTask = tasks.peek();
                if (newTask != null ) {
                    if(newTask.getServiceTime().get() == 0){
                        Thread.sleep(newTask.getServiceTime().get() * 1000);
                        tasks.take();
                        waitingPeriod.getAndAdd(-(newTask.getServiceTime().get()));
                    }
                }
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.getAndAdd(newTask.getServiceTime().get());
    }


    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    public String toString(){
        StringBuilder res = new StringBuilder();
        for(Task i : tasks){

                res.append(i.toString() + " ");
        }
        return res.toString();
    }


    public int getID() {
        return ID;
    }



}
