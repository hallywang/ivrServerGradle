package com.hally.service.impl;

import com.hally.cache.ICacheService;
import com.hally.cache.IObjectCache;
import com.hally.common.Constants;
import com.hally.dao.base.IBaseDao;
import com.hally.dao.base.ICommonDao;
import com.hally.dao.ivr.IvrBlackUserDao;
import com.hally.pojo.IvrBlackUser;
import com.hally.service.IBlackUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @Resource(name = "CommonHibernateDao")
    private ICommonDao CommonHibernateDao;

    @Resource(name = "ehcacheService")
    private ICacheService cacheService;

    @Resource(name = "ivrBlackUserDao")
    public void setBaseDao(IBaseDao<IvrBlackUser, Integer> ivrBlackUserDao) {
        this.baseDao = ivrBlackUserDao;
        this.ivrBlackUserDao = (IvrBlackUserDao) ivrBlackUserDao;
    }

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

    @Override
    public boolean isBlackUser(String mobile, String serviceId) {

        String hql = "from IvrBlackUser where status=1 " +
                " and msisdn=:mobile and (scope=0 or scope=:serviceId)";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("serviceId", serviceId);
        paramsMap.put("mobile", mobile);

        List list = CommonHibernateDao.listByHql(hql, paramsMap);
        return list != null && list.size() > 0;

    }
}


/*ObjectEhCache cache = ehcacheService.getCache(Constants.CACHE_NAME_BLACK_USER);
String cacheKey = userMobile + "-" + serviceId;  //黑名单号码-业务代码作为key，全局为0
IvrBlackUser blackUser = (IvrBlackUser) cache.get(cacheKey);
if (blackUser != null) {
        logger.info("黑名单用户:{}, serviceId:{}", userMobile, serviceId);
        flag = "9"; //限制接入
        blockTip = spId + MyConfigurer.getContextProperty("blockTip");
        } else {
        cacheKey = userMobile + "-0"; //全局黑名单
        blackUser = (IvrBlackUser) cache.get(cacheKey);
        if (blackUser != null) {
        logger.info("全局黑名单用户:{}, serviceId:{}", userMobile, serviceId);
        flag = "9"; //限制接入
        blockTip = spId + MyConfigurer.getContextProperty("blockTip");
        }

        }*/
