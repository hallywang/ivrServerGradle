package com.hally.service;

import com.hally.pojo.IvrChannelNotice;

import java.util.List;

/**
 * function description.
 * <p/>
 * <p><h2>Change History</h2>
 * <p/>
 * 2014/4/14 | hallywang | created
 * <p/>
 * </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
public interface IChannelNoticeService {

    public String getNoticeUrl(String serviceId,String callNumber);
    public IvrChannelNotice getNoticeInfo(String serviceId,String callNumber);


}
