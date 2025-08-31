package co.com.crediya.auth.r2dbc.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.com.crediya.auth.model.role.Role;
import co.com.crediya.auth.r2dbc.role.entity.RoleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapperStandard {
  RoleEntity toData(Role role);

  Role toEntity(RoleEntity entity);
}
