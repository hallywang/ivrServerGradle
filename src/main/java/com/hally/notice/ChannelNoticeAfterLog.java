package com.hally.notice;

import com.common.util.StringUtil;
import com.common.util.TimeUtil;
import com.hally.pojo.IvrChannelNotice;
import com.hally.pojo.IvrUserLogs;
import com.hally.service.IChannelNoticeService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        IvrChannelNotice noticeInfo = channelNoticeService.getNoticeInfo(servceId, callNumber);

        if (noticeInfo == null) {
            logger.info("callNumber:{} this channel has not config notice URL", callNumber);
            return;
        }

        if (!"0601".equals(userLogs.getOperateId())) {  //不是挂机，不通知
            logger.info("operateId:{},callNumber:{},operateId is not 0601,do not notice", userLogs.getOperateId(), callNumber);
            return;
        }

        String url = noticeInfo.getNoticeUrl();

        if (url == null || "".equals(url)) {
            logger.info("callNumber:{}  notice URL is null,check the config", callNumber);
            return;
        }

        String httpMethod = noticeInfo.getHttpMethod();
        if (httpMethod == null) httpMethod = "get";
        String timeFormat = noticeInfo.getTimeFormat();
        if (timeFormat == null || "".equals(timeFormat)) {
            timeFormat = "yyyy-MM-dd HH:mm:ss";
        }

        url = StringUtils.replace(url, "{msisdn}", userLogs.getMsisdn());
        url = StringUtils.replace(url, "{callNumber}", userLogs.getCallNumber());
        url = StringUtils.replace(url, "{startTime}", TimeUtil.getDate(userLogs.getCallTime(), timeFormat));
        url = StringUtils.replace(url, "{endTime}", TimeUtil.getDate(userLogs.getEndTime(), timeFormat));
        url = StringUtils.replace(url, "{feetime}", userLogs.getCallSecond().toString());
        url = StringUtils.replace(url, "{fee}", String.valueOf(userLogs.getFee()));
        url = StringUtils.replace(url, "{linkId}", System.currentTimeMillis() + "");


        Map<String, String> params = StringUtil.paramsToMap(url);

        String urlNoparams = url.substring(0, url.indexOf("?"));

        NoticeThread noticeThread = new NoticeThread(jp, urlNoparams, params, httpMethod);
        executor.submit(noticeThread);

        logger.info("operateId:{},callNumber:{},url:{},params:{},httpmethod:{}",
                userLogs.getOperateId(), callNumber, urlNoparams, params, httpMethod);
    }
}
