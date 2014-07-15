package com.hally.notice;

import com.common.http.HttpUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
    private String httpMethod;

    public NoticeThread(JoinPoint jp, String url, Map<String, String> params, String httpMethod) {
        this.joinPoint = jp;
        this.url = url;
        this.params = params;
        this.httpMethod = httpMethod;
    }

    public void run() {

        httpUtil.setConnectionTimeout(30000);
        httpUtil.setReadTimeout(30000);

        try {

            if (httpMethod == null) httpMethod = "get";

            String resp = "";

            if (httpMethod.equalsIgnoreCase("get")) {
                resp=  httpUtil.sendGet(url, params, null);
            } else {
                resp=  httpUtil.sendPost(url, params, null);
            }
            logger.info("resp:{},url:{},params:{}",resp,url,params);

        } catch (IOException e1) {
            logger.error("error IOException : {},url:{},params:{}", e1.getMessage(),url,params);
        } catch (Exception e) {
            logger.error("error exption : {},url:{},params:{}", e.getMessage(),url,params);
        }

    }
}
