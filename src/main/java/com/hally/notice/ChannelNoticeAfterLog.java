package com.hally.notice;

import com.common.util.TimeUtil;
import com.hally.pojo.IvrUserLogs;
import com.hally.service.IChannelNoticeService;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 2014/4/11 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */

@Service("channelNoticeAfterLog")
public class ChannelNoticeAfterLog {
    Logger logger = LoggerFactory.getLogger(ChannelNoticeAfterLog.class);
    @Resource
    IChannelNoticeService channelNoticeService;
    private static ExecutorService executor = new ThreadPoolExecutor(
            10,
            15,
            2000,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(12),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public void doAfter(JoinPoint jp, IvrUserLogs userLogs) {

        String callNumber = userLogs.getCallNumber();
        String servceId = userLogs.getServiceId();
        String url = channelNoticeService.getNoticeUrl(servceId, callNumber);

        if (url != null && !"".equals(url)) {

            if ("0601".equals(userLogs.getOperateId())) { // 挂机才通知

                Map<String, String> params = new LinkedHashMap<String, String>();


                params.put("phone", userLogs.getMsisdn());
                params.put("port", userLogs.getCallNumber());
                params.put("link_id", System.currentTimeMillis() + "");
                params.put("startTime", TimeUtil.getDate(userLogs.getCallTime(), "yyyy-MM-dd HH:mm:ss"));
                params.put("endTime", TimeUtil.getDate(userLogs.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
                params.put("feetime", userLogs.getCallSecond().toString());
                params.put("fee", String.valueOf(userLogs.getFee()));
                NoticeThread noticeThread = new NoticeThread(jp, url, params);
                executor.submit(noticeThread);

                logger.info("operateId:{},callNumber:{},url:{},params:{}", userLogs.getOperateId(),callNumber, url, params);
            } else {
                logger.info("operateId:{},callNumber:{},operateId is not 0601,do not notice", userLogs.getOperateId(),callNumber);
            }

        } else {
            logger.info("callNumber:{} this channel has not config notice URL", callNumber);
        }

    }
}
