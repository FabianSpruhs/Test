package de.haegerconsulting.haegermanagement.business.loginUser;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeService.class })
public interface LoginUserMapper {

    @Mapping(source = "employee.id", target = "employeeID")
    LoginUserDTO loginUserToLoginUserDTO(LoginUser loginUser);

    @Mapping(source = "employeeID", target = "employee")
    @Mapping(target = "id", ignore = true)
    LoginUser loginUserDTOToLoginUser(LoginUserDTO loginUserDTO);
}
