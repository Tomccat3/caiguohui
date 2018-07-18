package com.myapp.repository;

import com.myapp.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 角色数据
 * @author cgh
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findRolesByUserId(long userId);
}
