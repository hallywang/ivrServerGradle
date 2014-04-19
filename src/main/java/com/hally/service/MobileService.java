package com.hally.service;

import com.hally.cache.ICacheService;
import com.hally.cache.IObjectCache;
import com.hally.common.Constants;
import com.hally.dao.base.ICommonDao;
import com.hally.pojo.MobileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 2014/4/17 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */

@Service("mobileService")
public class MobileService {
    Logger logger = LoggerFactory.getLogger(MobileService.class);

    @Resource(name = "ehcacheService")
    private ICacheService cacheService;

    @Resource(name = "CommonHibernateDao")
    private ICommonDao CommonHibernateDao;

    /**
     * 获取省份
     * @param userMobile
     * @return
     */
    public String getProvName(String userMobile) {

        String areaInfo = getAreaInfo(userMobile);

        if (areaInfo != null) {
            String[] infos = areaInfo.split(";");
            String[] provInfo = infos[0].split(":");

            return provInfo[1];
        }


        return null;
    }

    /**
     *
     * @param userMobile
     * @return
     */
    public String getCityName(String userMobile) {
        String areaInfo = getAreaInfo(userMobile);

        if (areaInfo != null) {
            String[] infos = areaInfo.split(";");
            String[] cityInfo = infos[1].split(":");

            return cityInfo[1];
        }
        return null;
    }


    /**
     * 获取地区信息
     *
     * @param userMobile 手机号
     * @return 25:江苏;025:南京
     */
    private String getAreaInfo(String userMobile) {
        if (userMobile == null || userMobile.length() < 11) return null;
        String mobilePrefix = userMobile.substring(0, 7);
        IObjectCache cache = cacheService.getCache(Constants.CACHE_NAME_MOBILE_AREA);

        String areaInfo = (String) cache.get(mobilePrefix);

        if (areaInfo != null && !"".equals(areaInfo)) {
            return areaInfo;
        }

        String hql = "from MobileInfo where mobilePrefix=:mobilePrefix";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("mobilePrefix", mobilePrefix);

        List list = CommonHibernateDao.listByHql(hql, paramsMap);

        if (list != null && list.size() > 0) {
            MobileInfo mobileInfo = (MobileInfo) list.get(0);
            areaInfo = mobileInfo.getProvinceId() + ":" + mobileInfo.getProvinceName() + ";" + mobileInfo.getCityId() + ":" +
                    mobileInfo.getCityName();
            cache.put(mobilePrefix, areaInfo);
        }

        logger.info("load mobile:{} area info:{} from db and put into eternal cache", userMobile, areaInfo);

        return areaInfo;
    }
}
