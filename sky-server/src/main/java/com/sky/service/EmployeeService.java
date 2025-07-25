package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employee
     * @return
     */
    void addEmployee(EmployeeDTO employee);

    PageResult getEmployeeList(String name, Integer page, Integer pageSize);

    void updateStatus(Long id);

    void updateEmployee(EmployeeDTO employee);

    EmployeeDTO getEmployeeDetail(Long id);
}
