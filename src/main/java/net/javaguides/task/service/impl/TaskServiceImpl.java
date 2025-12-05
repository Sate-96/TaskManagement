package net.javaguides.task.service.impl;

import net.javaguides.task.dto.TaskDto;
import net.javaguides.task.entity.Task;
import net.javaguides.task.exception.ResourceNotFoundException;
import net.javaguides.task.repository.TaskRepository;
import net.javaguides.task.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto addTask(TaskDto taskDto) {
        // convert task dto to task entity
        Task task = modelMapper.map(taskDto, Task.class);

        // save task entity into db
        Task savedTask = taskRepository.save(task);

        // convert saved task entity to task dto
        TaskDto savedTaskDto = modelMapper.map(savedTask, TaskDto.class);

        return savedTaskDto;
    }

    @Override
    public TaskDto getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id:" + id));
        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with id: " + id)
        );

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());

        Task updatedTask = taskRepository.save(task); // update

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto completeTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setCompleted(Boolean.TRUE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public TaskDto inCompleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setCompleted(Boolean.FALSE);

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }
}
