package cn.nonocast.service;

import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import cn.nonocast.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

	@Cacheable(cacheNames="taskCache", key="'task:'.concat(#user.email)")
    public List<Task> findByUser(User user) {
        List<Task> result;
        result = taskRepository.findByBelongsTo(user);
        return result;
    }

	@CacheEvict(cacheNames="taskCache", key="'task:'.concat(#task.belongsTo.email)")
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@CacheEvict(cacheNames="taskCache", key="'task:'.concat(#task.belongsTo.email)")
	public void delete(Task task) {
		taskRepository.delete(task);
	}

	public Task findOne(Long id) {
		return taskRepository.findOne(id);
	}
}
