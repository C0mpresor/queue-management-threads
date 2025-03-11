package businesslogic;
import gui.*;
import model.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;



public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxProcessingTime ;
    public int minProcessingTime ;
    public int minArrivalTime ;
    public int maxArrivalTime ;
    public int nrOfServers;
    public int nrOfClients ;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private List<Task> generatedTasks = new ArrayList<>();

    private SimulationFrame frame;


    public SimulationManager(int nrOfClients, int nrOfServers, int minProcessingTime, int maxProcessingTime, int minArrivalTime, int maxArrivalTime, int timeLimit){
        this.nrOfClients = nrOfClients;
        this.nrOfServers = nrOfServers;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.timeLimit = timeLimit;

        generateNRandomTasks();
        scheduler = new Scheduler(nrOfServers,200);
        scheduler.changeStrategy(selectionPolicy);

    }

    public int getProcessingTime(){
        return minProcessingTime + (int)(Math.random() * ((maxProcessingTime - minProcessingTime) + 1));
    }
    public int getArrivalTime(){
        return minArrivalTime + (int)(Math.random() * ((maxArrivalTime - minArrivalTime) + 1));
    }




    private void generateNRandomTasks(){

        for(int i = 1 ; i <= nrOfClients; i++){
            Task t = new Task(i, getArrivalTime(), getProcessingTime());
            generatedTasks.add(t);
        }
        Collections.sort(generatedTasks, new TaskComparator());
    }

    @Override
    public void run() {
        int currentTime = 0;
        int waitingTimeCounter = 0;
        int maxQueueTotal = -1;
        List <Integer> recordedTimes = new ArrayList<>();
        int nrClients = generatedTasks.size();
        double avgWaitingTime;





        try  {
            PrintWriter myWriter = new PrintWriter("output.txt");

            while (currentTime <= timeLimit) {
                Iterator<Task> i = generatedTasks.iterator();

                while(i.hasNext()){
                    Task task = i.next();
                    if(task.getArrivalTime() == currentTime){
                        scheduler.dispachTask(task);
                        i.remove();
                    }
                }
                int currentQSize = 0;
                for(Server sr : scheduler.getServers()){
                    currentQSize += sr.getTasks().size();
                }
                if(currentQSize >= maxQueueTotal){
                    if(currentQSize > maxQueueTotal){
                        recordedTimes.removeAll(recordedTimes);
                    }
                    maxQueueTotal = currentQSize;
                    recordedTimes.add(currentTime);
                }
                myWriter.write("\nCurrent time:" + currentTime  + "\n");
                System.out.println("Current time:" + currentTime  + "\n");

                myWriter.write("\nWaiting clients: ");
                for(Task t : generatedTasks){
                    myWriter.write(t.toString() + "; ");

                }
                List <Server> servers = scheduler.getServers();

                for(Server s : servers){

                    if(s.getTasks().isEmpty()){
                        myWriter.write("\nQueue " + s.getID() +": closed");
                    }else{
                        myWriter.write("\nQueue " + s.getID() +": ");
                        Task task = s.getTasks().getFirst();
                        if(task.getServiceTime().get() > 0){
                            task.getServiceTime().decrementAndGet();
                        }
                    }
                    for(Task t : s.getTasks()){
                        if(t.getServiceTime().get()>0)
                            myWriter.write(t.toString()+ ";");
                        waitingTimeCounter++;
                    }
                }
                myWriter.write("\n");
                ///calculate peak hour total wait time
                try {
                    Thread.sleep(1000); // wait for an interval of 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime++;
            }

            avgWaitingTime = (double)waitingTimeCounter / nrClients;
            myWriter.write("\nAVG. WAITING TIME IS: " + avgWaitingTime);

            myWriter.write("\nPEAK HOUR IS AT TIME: " + recordedTimes.toString());
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured (FILE)");
            e.printStackTrace();
        }
    }




}
