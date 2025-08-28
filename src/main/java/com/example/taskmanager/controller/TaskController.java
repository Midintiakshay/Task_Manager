package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Task> tasks = taskService.getTasksByUser(user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "dashboard";
    }

    @PostMapping("/task")
    public String addTask(@ModelAttribute Task task, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        task.setUser(user);
        taskService.saveTask(task);
        return "redirect:/dashboard";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/task/toggle/{id}")
    public String toggleTask(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            taskService.saveTask(task);
        }
        return "redirect:/dashboard";
    }
}
