package com.hally.dao.ivr;


import com.hally.dao.base.IBaseDao;
import com.hally.pojo.IvrBlackUser;

import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-6 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
public interface  IvrBlackUserDao extends IBaseDao<IvrBlackUser,Integer> {

    public List<IvrBlackUser> loadAllValidUsers();//获取所有状态为1，有效的用户
}
