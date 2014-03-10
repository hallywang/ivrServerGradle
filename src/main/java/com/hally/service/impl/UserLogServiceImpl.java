package com.hally.service.impl;

import com.hally.dao.ivr.IvrUserLogsDao;
import com.hally.dao.base.IBaseDao;
import com.hally.pojo.IvrUserLogs;
import com.hally.service.IUserLogsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-13 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Service("userLogsService")
public class UserLogServiceImpl extends BaseService<IvrUserLogs, Integer>
        implements IUserLogsService {


    private IvrUserLogsDao ivrUserLogsDao;

    @Override
    @Resource(name = "ivrUserLogsDao")
    public void setBaseDao(IBaseDao<IvrUserLogs, Integer> ivrUserLogsDao) {
        this.baseDao = ivrUserLogsDao;
        this.ivrUserLogsDao = (IvrUserLogsDao) ivrUserLogsDao;

    }
}
