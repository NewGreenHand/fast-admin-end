package com.admin.app.schedule.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 添加定时任务注册类，用来增加、删除定时任务。
 * @author fei
 * @since 2019/12/20 16:19
 */
@Component
public class CronTaskRegistrar implements DisposableBean, InitializingBean {

  private final Map<Runnable, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>(16);

  private final TaskScheduler taskScheduler;

  public CronTaskRegistrar(TaskScheduler taskScheduler) {
    this.taskScheduler = taskScheduler;
  }

  /**
   * 新增定时任务
   * @param task 待执行任务
   * @param cronExpression cron表达式
   */
  public void addCronTask(Runnable task, String cronExpression) {
    addCronTask(new CronTask(task, cronExpression));
  }

  public void addCronTask(CronTask cronTask) {
    if (cronTask != null) {
      Runnable task = cronTask.getRunnable();
      if (this.scheduledTasks.containsKey(task)) {
        removeCronTask(task);
      }

      this.scheduledTasks.put(task, scheduleCronTask(cronTask));
    }
  }

  /**
   * 移除定时任务
   * @param task 待执行任务
   */
  public void removeCronTask(Runnable task) {
    ScheduledFuture<?> scheduledTask = this.scheduledTasks.remove(task);
    if (scheduledTask != null) {
      scheduledTask.cancel(true);
    }
  }

  public ScheduledFuture<?> scheduleCronTask(CronTask cronTask) {
    return this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
  }

  public TaskScheduler getScheduler() {
    return this.taskScheduler;
  }

  @Override
  public void destroy() {
    for (ScheduledFuture<?> task : this.scheduledTasks.values()) {
      task.cancel(true);
    }
    this.scheduledTasks.clear();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // 任务初始化
  }
}
