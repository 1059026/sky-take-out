package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value="员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    @ApiOperation(value="新增员工")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employee){
        employeeService.addEmployee(employee);
        return Result.success();
    }

    /**
     *  员工分页查询
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value="员工分页查询")
    public Result<PageResult> getEmployeeList (String name,Integer page,Integer pageSize){
        PageResult res = employeeService.getEmployeeList(name,page,pageSize);
        return Result.success(res);
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value="员工禁用状态修改")
    public Result<String> updateStatus(Long id){
        employeeService.updateStatus(id);
        return Result.success("操作成功");
    }

    @PutMapping
    @ApiOperation(value="员工信息修改")
    public Result<String> updateEmployee(@RequestBody EmployeeDTO employee){
        employeeService.updateEmployee(employee);
        return Result.success("操作成功");
    }

    @GetMapping("/{id}")
    @ApiOperation(value="员工信息查询")
    public Result<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        EmployeeDTO employee = employeeService.getEmployeeDetail(id);
        return Result.success(employee);
    }
}
