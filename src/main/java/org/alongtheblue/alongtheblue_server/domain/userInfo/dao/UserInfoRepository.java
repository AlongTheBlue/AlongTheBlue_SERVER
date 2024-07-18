package org.alongtheblue.alongtheblue_server.domain.userInfo.dao;

import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
