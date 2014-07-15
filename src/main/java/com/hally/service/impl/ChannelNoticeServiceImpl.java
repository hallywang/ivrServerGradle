package com.hally.service.impl;

import com.hally.cache.ICacheService;
import com.hally.cache.IObjectCache;
import com.hally.common.Constants;
import com.hally.dao.base.ICommonDao;
import com.hally.pojo.IvrChannelNotice;
import com.hally.service.IChannelNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 2014/4/14 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
@Service("channelNoticeService")
public class ChannelNoticeServiceImpl implements IChannelNoticeService {

    Logger logger = LoggerFactory.getLogger(ChannelNoticeServiceImpl.class);
    @Resource(name = "CommonHibernateDao")
    private ICommonDao CommonHibernateDao;

    @Resource(name = "ehcacheService")
    private ICacheService cacheService;

    @Override
    public String getNoticeUrl(String serviceId, String callNumber) {

        IvrChannelNotice channelNotice = this.getNoticeInfo(serviceId, callNumber);

        if (channelNotice != null) return channelNotice.getNoticeUrl();
        return "";

    }

    @Override
    public IvrChannelNotice getNoticeInfo(String serviceId, String callNumber) {
        String hql = "from IvrChannelNotice where status=1 " +
                " and serviceId=:serviceId" +
                " and channelCode=:channelCode ";

        if (callNumber == null || callNumber.length() <= 9) {
            logger.error("callNumber:{} is ERROR,", callNumber);
            return null;
        }
        String channelCode = callNumber.substring(9, callNumber.length()); //125905431 001渠道号就是001，

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("serviceId", serviceId);
        paramsMap.put("channelCode", channelCode);
        IObjectCache cache = cacheService.getCache(Constants.CACHE_NAME_CHANNEL_NOTICE_INFO);

        String cacheKey = serviceId + "-" + channelCode;
        IvrChannelNotice channelNotice = (IvrChannelNotice) cache.get(cacheKey);

        if (channelNotice != null) {
            return channelNotice;
        }

        logger.info("cache is null, load {},{}, url from db", cacheKey, callNumber);

        List list = CommonHibernateDao.listByHql(hql, paramsMap);

        if (list != null && list.size() > 0) {
            IvrChannelNotice ivrChannelNotice = (IvrChannelNotice) list.get(0);
            cache.put(cacheKey, ivrChannelNotice);
            channelNotice = ivrChannelNotice;
        }


        return channelNotice;
    }
}
