package businesslogic;

import model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getArrivalTime() - o2.getArrivalTime();
    }
}
