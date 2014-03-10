package com.hally.dao.ivr;

import com.hally.dao.base.hibernate4.BaseHibernateDao;
import com.hally.pojo.IvrUserLogs;
import org.springframework.stereotype.Repository;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-13 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Repository(value = "ivrUserLogsDao")
public class IvrUserLogsDaoImpl extends BaseHibernateDao<IvrUserLogs, Integer>
        implements IvrUserLogsDao {
}
