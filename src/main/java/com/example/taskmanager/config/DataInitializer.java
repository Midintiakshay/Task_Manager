package com.example.taskmanager.config;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(TaskRepository taskRepository) {
        return args -> {
            // Sample tasks
            Task task1 = new Task();
            task1.setTitle("Sample Task 1");
            task1.setDescription("Description for task 1");
            task1.setCompleted(false);
            task1.setDueDate(LocalDate.now());

            Task task2 = new Task();
            task2.setTitle("Sample Task 2");
            task2.setDescription("Description for task 2");
            task2.setCompleted(false);
            task2.setDueDate(LocalDate.now());

            taskRepository.saveAll(Arrays.asList(task1, task2));
        };
    }
}
