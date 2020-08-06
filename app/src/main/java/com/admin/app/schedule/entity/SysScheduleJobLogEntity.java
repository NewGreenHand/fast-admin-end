package com.admin.app.schedule.entity;

import com.admin.core.basic.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 定时任务日志(SysScheduleJobLog)实体类
 *
 * @author fei
 * @since 2019-12-20 16:16:36
 */
@Entity
@Table(name = "sys_schedule_job_log")
public class SysScheduleJobLogEntity extends AbstractEntity implements Serializable {
  private static final long serialVersionUID = -74276166563785647L;
  /** 任务id */
  private Long jobId;
  /** spring bean名称 */
  private String beanName;
  /** 方法名 */
  private String methodName;
  /** 参数 */
  private String params;
  /** 任务状态    0：失败    1：成功 */
  private Boolean status;
  /** 失败信息 */
  private String error;
  /** 耗时(单位：毫秒) */
  private Integer times;

  @Basic
  @Column(name="job_id")
  public Long getJobId() {
      return jobId;
  }

  public void setJobId(Long jobId) {
      this.jobId = jobId;
  }

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
  @Column(name="status")
  public Boolean getStatus() {
      return status;
  }

  public void setStatus(Boolean status) {
      this.status = status;
  }

  @Basic
  @Column(name="error")
  public String getError() {
      return error;
  }

  public void setError(String error) {
      this.error = error;
  }

  @Basic
  @Column(name="times")
  public Integer getTimes() {
      return times;
  }

  public void setTimes(Integer times) {
      this.times = times;
  }

}
