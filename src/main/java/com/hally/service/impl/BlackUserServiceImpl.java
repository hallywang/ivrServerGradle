package com.hally.service.impl;

import com.hally.dao.IvrBlackUserDao;
import com.hally.dao.base.IBaseDao;
import com.hally.pojo.IvrBlackUser;
import com.hally.service.IBlackUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-6 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Service("ivrBlackUserService")

public class BlackUserServiceImpl extends BaseService<IvrBlackUser, Integer>
        implements IBlackUserService {

    /**
     * ×¢ÈëDAO
     */

    private IvrBlackUserDao ivrBlackUserDao;

    @Resource(name = "ivrBlackUserDao")
    public void setBaseDao(IBaseDao<IvrBlackUser, Integer> ivrBlackUserDao) {
        this.baseDao = ivrBlackUserDao;
        this.ivrBlackUserDao = (IvrBlackUserDao) ivrBlackUserDao;
    }

    @Override
    public List<IvrBlackUser> getByMobile(String mobile) {

        List list = ivrBlackUserDao.listByHql("from IvrBlackUser where msisdn=? ", mobile);

        return list;

    }


}
