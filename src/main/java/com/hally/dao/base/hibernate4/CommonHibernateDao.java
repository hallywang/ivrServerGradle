package com.hally.dao.base.hibernate4;


import com.hally.common.Constants;
import com.hally.dao.base.ICommonDao;
import com.hally.model.AbstractModel;
import com.hally.pagination.PageUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository("CommonHibernateDao")
public class CommonHibernateDao implements ICommonDao {


    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public <T extends AbstractModel> T save(T model) {
        getSession().save(model);
        return model;
    }

    public <T extends AbstractModel> void saveOrUpdate(T model) {
        getSession().saveOrUpdate(model);

    }

    public <T extends AbstractModel> void update(T model) {
        getSession().update(model);
    }

    public <T extends AbstractModel> void merge(T model) {
        getSession().merge(model);
    }

    public <T extends AbstractModel, PK extends Serializable> void delete(Class<T> entityClass, PK id) {
        getSession().delete(get(entityClass, id));
    }

    public <T extends AbstractModel> void deleteObject(T model) {
        getSession().delete(model);
    }

    public <T extends AbstractModel, PK extends Serializable> T get(Class<T> entityClass, PK id) {
        return (T) getSession().get(entityClass, id);

    }

    public <T extends AbstractModel> int countAll(Class<T> entityClass) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }


    @SuppressWarnings("unchecked")
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setProjection(Projections.rowCount());
        return criteria.list();
    }

    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass, int pn) {
        return listAll(entityClass, pn, Constants.DEFAULT_PAGE_SIZE);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass, int pn, int pageSize) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setFirstResult(PageUtil.getPageStart(pn, pageSize));
        return criteria.list();

    }

    @Override
    public <T extends AbstractModel> List<T> listByHql(String hql, Map<String,Object> params) {
        Query query = getSession().createQuery(hql);
        setParameters(query, params);

        List<T> results = query.list();
        return results;
    }

    protected void setParameters(Query query, Map<String, Object> paramMap) {
        if (paramMap != null && paramMap.size() > 0) {

            for (String s : paramMap.keySet()) {
                query.setParameter(s, paramMap.get(s));
            }


           /* for (int i = 0; i < paramMap.size(); i++) {
                if (paramMap[i] instanceof Date) {
                    //TODO 难道这是bug 使用setParameter不行？？
                    query.setTimestamp(i, (Date) paramMap[i]);
                } else {
                    query.setParameter(i, paramMap[i]);
                }
            }*/
        }
    }
}
