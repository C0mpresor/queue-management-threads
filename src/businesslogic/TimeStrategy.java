package businesslogic;

import model.Server;
import model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        int minTime = servers.get(0).getWaitingPeriod().get();
        Server minTaskServer = servers.get(
                0
        );
        for(Server i : servers){
            if(i.getWaitingPeriod().get() < minTime){
                minTime = i.getWaitingPeriod().get();
                minTaskServer = i;
            }
        }
        minTaskServer.addTask(task);
    }
}
