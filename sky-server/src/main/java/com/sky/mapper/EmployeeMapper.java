package com.sky.mapper;

import com.sky.dto.EmployeeDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert( "insert into employee (username, name, password, phone, sex, id_number, create_time, update_time, create_user, update_user) values (#{username}, #{name}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void addEmployee(Employee e);

    List<Employee> getEmployeeList(String name);
    @Select("select status from employee where id = #{id}")
    int getStatusById(Long id);
    @Update("update employee set status = #{status} where id = #{id}")
    void updateStatus(Long id, int status);

    void updateEmployee(Employee e);

    @Select ("select * from employee where id = #{id}")
    EmployeeDTO getEmployeeDetail(Long id);

}
