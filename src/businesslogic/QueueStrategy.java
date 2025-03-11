package businesslogic;

import model.Server;
import model.Task;

import java.util.List;

public class QueueStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        int minTasks = 201;
        Server minTaskServer = null;
        for(Server i : servers){
            if(i.getTasks().size() < minTasks ){
                minTasks = i.getTasks().size();
                minTaskServer = i;
            }
        }
        minTaskServer.addTask(task);
    }
}
