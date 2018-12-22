package com.admin.user.controller;

import com.admin.user.model.CurrentUser;
import com.admin.user.dto.DepartmentDto;
import com.admin.user.entity.SysDepartment;
import com.admin.user.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 部门表
 * @author fei
 * @date 2018/10/13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sys/department")
public class DepartmentController {
  private final DepartmentService departmentService;

  private static final ExampleMatcher EXAMPLE_MATCHER;
  static {
    EXAMPLE_MATCHER = ExampleMatcher
      .matchingAll()
      .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
      .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
      ;
  }

  @Autowired
  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  /**
   * 获取首页数据
   * @param name 部门名称
   * @return 异常类型集合
   */
  @GetMapping("index")
  public List<SysDepartment> index(String name) {
    SysDepartment sysDepartment = new SysDepartment();
    sysDepartment.setName(StringUtils.hasLength(name) ? name : null);
    sysDepartment.setCode(CurrentUser.getCompanyCode());

    return departmentService.findAll(Example.of(sysDepartment, EXAMPLE_MATCHER));
  }

  /**
   * 所有有效的部门集合
   * @return 部门集合
   */
  @GetMapping("all_valid")
  public List<SysDepartment> allValid() {
    SysDepartment sysDepartment = new SysDepartment();
    sysDepartment.setCode(CurrentUser.getCompanyCode());

    return departmentService.findAll(Example.of(sysDepartment, EXAMPLE_MATCHER));
  }


  /**
   * 获取当前用户的有效部门树
   * @return 部门树集合
   */
  @GetMapping("valid_tree")
  public List<SysDepartment> validTree() {
    SysDepartment department = departmentService.validTree();
    List<SysDepartment> result = new LinkedList<>();
    result.add(department);

    return result;
  }


  @PostMapping
  public SysDepartment save(@Valid @RequestBody DepartmentDto dto) {
    SysDepartment workModel = dto.convert(SysDepartment.class);
    workModel.setEnabled(dto.getEnabled().value());

    return departmentService.save(workModel);
  }

  @PutMapping("{id}")
  public SysDepartment update(@NotNull @PathVariable Integer id, @Valid @RequestBody DepartmentDto dto) {
    SysDepartment workModel = departmentService.findByPk(id);

    BeanUtils.copyProperties(dto, workModel);
    workModel.setEnabled(dto.getEnabled().value());

    return departmentService.update(workModel);
  }

  @DeleteMapping("/{id}")
  public String delete(@NotNull @PathVariable Integer id) {
    departmentService.delete(id);

    return "删除成功";
  }

  /**
   * 获取单个异常信息
   * @param id 异常ID
   * @return 异常对象
   */
  @GetMapping("/{id}")
  public SysDepartment findOne(@NotNull @PathVariable Integer id) {
    return departmentService.findByPk(id);
  }

}
