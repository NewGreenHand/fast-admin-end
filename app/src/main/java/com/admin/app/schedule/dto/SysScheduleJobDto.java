package com.admin.app.schedule.dto;

import com.admin.app.schedule.entity.SysScheduleJobEntity;
import com.admin.core.basic.DtoConvert;

import lombok.Data;


/**
 * 定时任务(SysScheduleJob)表单数据
 *
 * @author fei
 * @since 2019-12-20 16:16:36
 */
@Data
public class SysScheduleJobDto implements DtoConvert<SysScheduleJobEntity> {
  /** spring bean名称 */
  private String beanName;
  /** 方法名 */
  private String methodName;
  /** 参数 */
  private String params;
  /** cron表达式 */
  private String cronExpression;
  /** 任务状态  0：暂停  1：正常 */
  private Boolean status;
  /** 备注 */
  private String remark;
}
