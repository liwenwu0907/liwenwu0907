package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class FlowableTest {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    // 员工id
    public static final String yuangongId = "yuangongID_3";


    // 组长id
    public static final String zuzhangId = "zuzhangId_3";

    // 经理id
    public static final String manageId = "manageId_3";

    @Test
    void contextLoads() {
    }


    /**
     * 开启一个请假流程
     */
    @Test
    void askForLeave() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("leaveTask", yuangongId);
        // 开启流程的key，就是流程定义文件里 process 标签的id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ask_for_leave", map);
        // 设置一些参数
        runtimeService.setVariable(processInstance.getId(), "name", "javaboy");
        runtimeService.setVariable(processInstance.getId(), "reason", "休息一下");
        runtimeService.setVariable(processInstance.getId(), "days", 10);
        log.info("创建请假流程 processId：{}", processInstance.getId());

    }

    /**
     * 员工提交请假
     */
    @Test
    void submitToZuZhang() {
        // 员工查找到自己的任务，然后提交给组长审批
        List<Task> list = taskService.createTaskQuery().taskAssignee(yuangongId).orderByTaskId().desc().list();
        for (Task task: list) {
            log.info("任务 ID：{}；任务处理人：{}；任务是否挂起：{}", task.getId(), task.getAssignee(), task.isSuspended());
            Map<String, Object> map = new HashMap<>();
            //提交给组长的时候，需要指定组长的 id
            map.put("zuzhangTask", zuzhangId);
            taskService.complete(task.getId(), map);
        }

    }

    /**
     * 组长批准请假
     */
    @Test
    void zuZhangApprove() {
        List<Task> list = taskService.createTaskQuery().taskAssignee(zuzhangId).orderByTaskId().desc().list();
        for (Task task: list) {
            if ("组长审批".equals(task.getName())) {
                log.info("任务 ID：{}；任务处理人：{}；任务是否挂起：{}", task.getId(), task.getAssignee(), task.isSuspended());
                Map<String, Object> map = new HashMap<>();
                //提交给组长的时候，需要指定组长的 id
                map.put("manageTask", manageId);
                map.put("checkResult", "通过");
                map.put("zuzhangTask", zuzhangId);

                try {
                    taskService.complete(task.getId(), map);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("组长审批失败{} {}", task.getId(), task.getAssignee());
                }

            }
        }

    }

    /**
     * 经理审批通过
     */
    @Test
    void managerApprove() {
        List<Task> list = taskService.createTaskQuery().taskAssignee(manageId).orderByTaskId().desc().list();
        for (Task task: list) {
            log.info("经理 {} 在审批 {} 任务", task.getAssignee(), task.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("checkResult", "通过");
            taskService.complete(task.getId(), map);
        }
    }

    /**
     * 经理审批不通过
     */
    @Test
    void managerNotApprove() {
        List<Task> list = taskService.createTaskQuery().taskAssignee(manageId).orderByTaskId().desc().list();
        for (Task task: list) {
            log.info("经理 {} 在审批 {} 任务", task.getAssignee(), task.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("checkResult", "拒绝");
            taskService.complete(task.getId(), map);
        }
    }


}
