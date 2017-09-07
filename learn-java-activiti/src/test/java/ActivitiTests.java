import org.activiti.engine.*;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 测试
 *
 * @author zhhb
 */
public class ActivitiTests {

    @Test
    public void TestProcessEngine() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(engine.getName());
    }

    @Test
    public void TestIdentityService1() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = engine.getIdentityService();
        User user = identityService.newUser("zhhb");
        user.setEmail("zhanghuibin@le.com");
        user.setFirstName("huibin");
        user.setLastName("zhang");
        identityService.saveUser(user);
    }

    @Test
    public void TestIdentityService2() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = engine.getIdentityService();
        User user = identityService.createUserQuery().userId("zhhb").singleResult();
        user.setPassword("haha");
        identityService.saveUser(user);
    }

    @Test
    public void TestIdentityService3() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = engine.getIdentityService();
        System.out.println(identityService.checkPassword("zhhb", "hello"));
        System.out.println(identityService.checkPassword("zhhb", "haha2"));
        System.out.println(identityService.checkPassword("zhhb", "haha"));
    }

    @Test
    public void TestFormService() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        FormService formService = engine.getFormService();
        Object form = formService.getRenderedStartForm("10005");
//        formService.saveFormData(form);
    }

    @Test
    public void TestHistoryService() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = engine.getHistoryService();
        List<HistoricIdentityLink> historicIdentityLinks
                = historyService.getHistoricIdentityLinksForProcessInstance("10005");
        for (HistoricIdentityLink link : historicIdentityLinks) {
            System.out.println(link.getTaskId());
        }
//        formService.saveFormData(form);
    }

    @Test
    public void TestRuntimeService() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("bpoAdd");
    }

    @Test
    public void TestRepositoryService() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("BpoAdd.bpmn").deploy();
    }

    @Test
    public void TestManagementService() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ManagementService managementService = engine.getManagementService();
        managementService.createJobQuery();
    }

    @Test
    public void TestTaskService() {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = engine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();
        if (tasks.size() <= 0) {
            return;
        }
        for (Task task : tasks) {
            StringBuilder taskMessage = new StringBuilder("task");
            try {
                while (task != null) {
                    taskMessage.append(" -> ").append(task);
                    taskService.complete(task.getId());
                    task = taskService.createTaskQuery().executionId(task.getExecutionId()).singleResult();
                }
            } finally {
                System.out.println(taskMessage);
            }
        }
    }
}
