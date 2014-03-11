package com.hally.service.impl;

import com.hally.dao.base.IBaseDao;
import com.hally.dao.base.ICommonDao;
import com.hally.pojo.IvrConfigData;
import com.hally.service.IConfigDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 14-3-8 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
@Service("configDataService")
public class ConfigDataServiceImpl extends BaseService<IvrConfigData, Integer>
        implements IConfigDataService {
    Logger logger = LoggerFactory.getLogger(ConfigDataServiceImpl.class);
    @Resource(name="CommonHibernateDao")
    private ICommonDao CommonHibernateDao;
    @Override
    public List<IvrConfigData> listValid(String operateId, String serviceId) {
        String hql = "from IvrConfigData where status=1 " +
                " and serviceId=:serviceId" +
                " and operateId=:operateId ";

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("serviceId", serviceId);
        paramsMap.put("operateId", operateId);

        return CommonHibernateDao.listByHql(hql, paramsMap);
    }

    @Override
    public void setBaseDao(IBaseDao<IvrConfigData, Integer> baseDao) {

    }
}
