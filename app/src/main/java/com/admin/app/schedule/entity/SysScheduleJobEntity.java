package com.admin.app.schedule.entity;

import com.admin.core.basic.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 定时任务(SysScheduleJob)实体类
 *
 * @author fei
 * @since 2019-12-20 16:16:27
 */
@Entity
@Table(name = "sys_schedule_job")
public class SysScheduleJobEntity extends AbstractEntity implements Serializable {
  private static final long serialVersionUID = 346272683167593607L;
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


  @Basic
  @Column(name="bean_name")
  public String getBeanName() {
      return beanName;
  }

  public void setBeanName(String beanName) {
      this.beanName = beanName;
  }

  @Basic
  @Column(name="method_name")
  public String getMethodName() {
      return methodName;
  }

  public void setMethodName(String methodName) {
      this.methodName = methodName;
  }

  @Basic
  @Column(name="params")
  public String getParams() {
      return params;
  }

  public void setParams(String params) {
      this.params = params;
  }

  @Basic
  @Column(name="cron_expression")
  public String getCronExpression() {
      return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
      this.cronExpression = cronExpression;
  }

  @Basic
  @Column(name="status")
  public Boolean getStatus() {
      return status;
  }

  public void setStatus(Boolean status) {
      this.status = status;
  }

  @Basic
  @Column(name="remark")
  public String getRemark() {
      return remark;
  }

  public void setRemark(String remark) {
      this.remark = remark;
  }

}
