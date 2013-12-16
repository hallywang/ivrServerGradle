package com.hally.service;

import com.hally.pojo.IvrBlackUser;

import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-6 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
public interface IBlackUserService
        extends IBaseService<IvrBlackUser, Integer> {
    public List<IvrBlackUser> getByMobile(String mobile);

}
