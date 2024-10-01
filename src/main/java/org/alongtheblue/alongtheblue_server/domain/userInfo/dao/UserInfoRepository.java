package org.alongtheblue.alongtheblue_server.domain.userInfo.dao;

import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query("SELECT u FROM UserInfo u WHERE u.uid = :uid ")
    Optional<UserInfo> findByUid(String uid);
}
