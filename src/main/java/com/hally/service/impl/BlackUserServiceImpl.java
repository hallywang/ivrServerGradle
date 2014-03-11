package com.hally.service.impl;

import com.hally.cache.ICacheService;
import com.hally.cache.IObjectCache;
import com.hally.common.Constants;
import com.hally.dao.ivr.IvrBlackUserDao;
import com.hally.dao.base.IBaseDao;
import com.hally.pojo.IvrBlackUser;
import com.hally.service.IBlackUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-6 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Service("blackUserService")

public class BlackUserServiceImpl extends BaseService<IvrBlackUser, Integer>
        implements IBlackUserService {


    Logger logger = LoggerFactory.getLogger(BlackUserServiceImpl.class);
    /**
     * 注入DAO
     */

    private IvrBlackUserDao ivrBlackUserDao;

    @Resource(name = "ehcacheService")
    private ICacheService cacheService;

    @Resource(name = "ivrBlackUserDao")
    public void setBaseDao(IBaseDao<IvrBlackUser, Integer> ivrBlackUserDao) {
        this.baseDao = ivrBlackUserDao;
        this.ivrBlackUserDao = (IvrBlackUserDao) ivrBlackUserDao;
    }

    @Override
    public List<IvrBlackUser> getByMobile(String mobile) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        return ivrBlackUserDao.listByHql("from IvrBlackUser where msisdn=:mobile ", params);
    }

    @Override
    public void initBlackUserToCache() {
        IObjectCache cache = cacheService.getCache(Constants.CACHE_NAME_BLACK_USER);
        logger.info("=init= blackUserList to cache begin");
        long start = System.currentTimeMillis();
        cache.cleanCache();
        List list = ivrBlackUserDao.loadAllValidUsers();
        String cacheKey;
        for (Object blackUser1 : list) {
            IvrBlackUser blackUser = (IvrBlackUser) blackUser1;
            cacheKey = blackUser.getMsisdn() + "-" + blackUser.getScope();
            cache.put(cacheKey, blackUser);
        }
        long end = System.currentTimeMillis();
        logger.info("=init= blackUserList to cache sucess,cost:{} s,total:{} users", (end - start) / 1000, list.size());
    }
}
