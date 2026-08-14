package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.service.dto.DepartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {}
