package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    @Override
    public void addEmployee(EmployeeDTO employee) {
        Employee e = new Employee();
        BeanUtils.copyProperties(employee,e);
        e.setCreateTime(LocalDateTime.now());
        e.setUpdateTime(LocalDateTime.now());
        e.setStatus(StatusConstant.ENABLE);
        e.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //获取当前登录用户id
        Long id = BaseContext.getCurrentId();
        log.info("当前员工id：{}", id);
        e.setCreateUser(id);
        e.setUpdateUser(id);
        employeeMapper.addEmployee(e);
    }

    @Override
    public PageResult getEmployeeList(String name, Integer page, Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<Employee> list = employeeMapper.getEmployeeList(name);
        PageInfo<Employee> pageInfo = new PageInfo<>(list);
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

    @Override
    public void updateStatus(Long id){
        int status = employeeMapper.getStatusById(id);
        status = status == 1 ? 0 : 1;
        employeeMapper.updateStatus(id,status);
    }

    @Override
    public void updateEmployee(EmployeeDTO employee){
        Employee e = new Employee();
        BeanUtils.copyProperties(employee,e);
        employeeMapper.updateEmployee(e);
    }

    @Override
    public EmployeeDTO getEmployeeDetail(Long id){
        EmployeeDTO detail = employeeMapper.getEmployeeDetail(id);
        return detail;
    }
}
