package com.zmy.demo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zmy.demo.bean.Remaining;

@Repository public interface RemainingMapper {
    /**
     * @param userId 员工号
     * @return 员工号为userId的员工的假期余额信息
     */
    Remaining selectByUserId(@Param("userId") String userId);

    /**
     * 根据remaining修改员工号为userId的员工的假期余额信息
     * @param userId
     * @param remaining
     * @return
     */
    void updateByUserId(
            @Param("userId") String userId,
            @Param("remaining") Remaining remaining
    );
}
