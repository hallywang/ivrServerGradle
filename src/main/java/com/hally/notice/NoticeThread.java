package com.hally.notice;

import com.common.http.HttpUtil;
import com.hally.service.IChannelNoticeService;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 2014/4/11 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
public class NoticeThread extends Thread {
    Logger logger = LoggerFactory.getLogger(NoticeThread.class);

    private JoinPoint joinPoint;
    private String url;
    private Map<String, String> params;
    private HttpUtil httpUtil = new HttpUtil();

    public NoticeThread(JoinPoint jp, String url, Map<String, String> params) {
        this.joinPoint = jp;
        this.url = url;
        this.params = params;
    }

    public void run() {

        httpUtil.setConnectionTimeout(1000);
        httpUtil.setReadTimeout(10000);

        try {
            httpUtil.sendGet(url, params, null);
        } catch (IOException e1) {
            logger.error("error IOException : {}", e1.getMessage());
        } catch (Exception e) {
            logger.error("error exption : {}", e.getMessage());
        }

    }
}
