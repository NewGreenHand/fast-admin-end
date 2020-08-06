package com.admin.app.schedule.service.impl;

import com.admin.app.schedule.config.CronTaskRegistrar;
import com.admin.app.schedule.config.SchedulingRunnable;
import com.admin.app.schedule.entity.SysScheduleJobEntity;
import com.admin.app.schedule.repository.SysScheduleJobRepository;
import com.admin.app.schedule.service.SysScheduleJobService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.admin.core.basic.AbstractServiceImpl;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

/**
 * 定时任务(SysScheduleJob)表服务实现类
 *
 * @author fei
 * @since 2019-12-20 16:16:33
 */
@Slf4j
@Service
@Validated
public class SysScheduleJobServiceImpl extends AbstractServiceImpl<SysScheduleJobEntity, Long> implements SysScheduleJobService {
  private final SysScheduleJobRepository sysScheduleJobRepository;
  private final CronTaskRegistrar cronTaskRegistrar;

  @Autowired
  public SysScheduleJobServiceImpl(SysScheduleJobRepository sysScheduleJobRepository, CronTaskRegistrar cronTaskRegistrar) {
    super(sysScheduleJobRepository);
    this.sysScheduleJobRepository = sysScheduleJobRepository;
    this.cronTaskRegistrar = cronTaskRegistrar;
  }

  /**
   * 项目启动时，初始化定时器
   */
  @PostConstruct
  public void init(){
    // 获取所有开启的任务
    List<SysScheduleJobEntity> scheduleJobList = sysScheduleJobRepository.findAllByStatus(true);
    // 注册任务
    for(SysScheduleJobEntity job : scheduleJobList){
      // 创建任务
      SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getParams());
      cronTaskRegistrar.addCronTask(task, job.getCronExpression());
    }
  }

  @Override
  public SysScheduleJobEntity update(@Valid SysScheduleJobEntity entity) {
    // 创建任务
    SchedulingRunnable task = new SchedulingRunnable(entity.getBeanName(), entity.getMethodName(), entity.getParams());
    // 判断是开启/关闭任务
    if (entity.getStatus()) {
      cronTaskRegistrar.addCronTask(task, entity.getCronExpression());
    } else {
      cronTaskRegistrar.removeCronTask(task);
    }

    return super.update(entity);
  }

  @Override
  public SysScheduleJobEntity save(@Valid SysScheduleJobEntity entity) {
    // 添加任务
    SchedulingRunnable task = new SchedulingRunnable(entity.getBeanName(), entity.getMethodName(), entity.getParams());
    cronTaskRegistrar.addCronTask(task, entity.getCronExpression());

    return super.save(entity);
  }

  @Override
  public void delete(SysScheduleJobEntity entity) {
    // 移除任务
    SchedulingRunnable task = new SchedulingRunnable(entity.getBeanName(), entity.getMethodName(), entity.getParams());
    cronTaskRegistrar.removeCronTask(task);

    super.delete(entity);
  }
}
