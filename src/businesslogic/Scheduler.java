package businesslogic;


import model.*;

import java.util.ArrayList;
import java.util.List;


public class Scheduler {

    private int maxNoServers;
    private int maxTasksPerServer = 200;
    private Strategy strategy;

    private static List<Server> servers = new ArrayList<>();
    public Scheduler(int maxNoServers, int maxTasksPerServer){
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;



        for(int i = 0; i < this.maxNoServers; i ++){
            Server server = new Server(i+1);
            servers.add(server);
        }
        for(Server i : servers){
            Thread thread = new Thread(i);
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new TimeStrategy();
        }else if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new QueueStrategy();
        }
    }

    public void dispachTask(Task newTask){
        strategy.addTask(servers,newTask);
    }

    public List<Server> getServers() {
        return servers;
    }
}
