
import com.example.projectmanagementtool.Model.Task;
import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.PMTRepository;
import com.example.projectmanagementtool.Service.pmtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PMTRepositoryTest {

    private PMTRepository pmtRepository = new PMTRepository();

    @Test
    public void addTaskToDBTest() throws pmtException {
        // Initialize a dummy task owner and task
        User owner = new User(1, "Testuser", "testuser@mail.com", "123password", "Member");
        Task newTask = new Task(10, "Test task", "Test Description", 10, owner, "2023-05-26", 1, "TODO");

        // Add the task
        pmtRepository.addTaskToDB(newTask, owner.getId());

        // Retrieve the list of tasks from a specific subproject
        List<Task> tasks = pmtRepository.getTasksFromSubproject(1);

        // Check if the added task exists in the list
        boolean taskExists = tasks.stream().anyMatch(task -> task.getName().equals(newTask.getName()));

        assertTrue(taskExists);
    }

    @Test
    public void getTasksFromSubprojectTest() {
        // Assuming a subproject ID 1 exists and it has tasks

        // Retrieve the list of tasks from a specific subproject
        List<Task> tasks = pmtRepository.getTasksFromSubproject(1);

        // Check if the list is not empty
        assertFalse(tasks.isEmpty());
    }
}
