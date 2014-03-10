package com.hally.service;

import com.hally.pojo.IvrConfigData;

import java.util.List;

/**
 * function description.
 * <p/>
 * <p><h2>Change History</h2>
 * <p/>
 * 14-3-8 | hallywang | created
 * <p/>
 * </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
public interface IConfigDataService  extends IBaseService<IvrConfigData, Integer> {

    public List<IvrConfigData> listValid(String operateId,String serviceId);


}
