package com.admin.app.schedule.controller;

import com.admin.app.schedule.entity.SysScheduleJobEntity;
import com.admin.app.schedule.service.SysScheduleJobService;
import com.admin.app.schedule.dto.SysScheduleJobDto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import com.admin.core.basic.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 定时任务(SysScheduleJob)表控制层
 *
 * @author fei
 * @since 2019-12-20 16:16:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sys/schedule/job")
public class SysScheduleJobController extends AbstractController<SysScheduleJobEntity, Long> {
  /** 服务对象 */
  private final SysScheduleJobService sysScheduleJobService;

  public SysScheduleJobController(SysScheduleJobService sysScheduleJobService) {
    super(sysScheduleJobService);
    this.sysScheduleJobService = sysScheduleJobService;
  }

  /**
   * 获取首页数据（分页）
   * @param entity 实体对象
   * @param pageable 分页参数
   * @return 用户集合
   */
  @GetMapping(value = "index", params = {"page"})
  public Page<SysScheduleJobEntity> findAll(SysScheduleJobEntity entity, @PageableDefault Pageable pageable) {
    return sysScheduleJobService.findAll(Example.of(entity, getMatcher()), pageable);
  }

  /**
   * 获取首页数据（不分页）
   * @param entity 实体对象
   * @return 用户集合
   */
  @GetMapping(value = "index")
  public List<SysScheduleJobEntity> index(SysScheduleJobEntity entity) {
    return sysScheduleJobService.findAll(Example.of(entity, getMatcher()));
  }

  /**
   * 保存一条记录
   * @param dto 表单数据
   */
  @PostMapping
  public void save(@RequestBody @Valid SysScheduleJobDto dto) {
    this.sysScheduleJobService.save(dto.convert(SysScheduleJobEntity.class));
  }

  /**
   * 根据 ID 更新一条记录
   * @param id ID
   * @param dto 表单数据
   */
  @PutMapping("{id}")
  public void updateById(@PathVariable @NotNull Long id, @RequestBody @Valid SysScheduleJobDto dto) {
    SysScheduleJobEntity sysScheduleJobEntity = sysScheduleJobService.findById(id);

    sysScheduleJobService.update(dto.pathProperties(sysScheduleJobEntity));
  }

  /**
   * 根据ID删除一条数据
   * @param id ID
   */
  @DeleteMapping("{id}")
  public void delete(@PathVariable @NotNull Long id) {
    sysScheduleJobService.delete(id);
  }
}
