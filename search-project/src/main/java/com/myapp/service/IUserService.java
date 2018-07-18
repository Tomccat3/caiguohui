package com.myapp.service;

import com.myapp.entity.User;
import com.myapp.web.dto.UserDTO;

/**  用户服务
 * @author cgh
 */
public interface IUserService {

    User findUserByName(String userName);

    ServiceResult<UserDTO> findById(Long userId);
}
