package com.hally.service;



import com.hally.pagination.Page;

import java.util.List;
import java.util.Map;

public interface IBaseService<M extends java.io.Serializable, PK extends java.io.Serializable> {
    
    public M save(M model);

    public void saveOrUpdate(M model);
    
    public void update(M model);
    
    public void merge(M model);

    public void delete(PK id);

    public void deleteObject(M model);

    public M get(PK id);
    
    public int countAll();
    
    public List<M> listAll();
    
    public Page<M> listAll(int pn);
    
    public Page<M> listAll(int pn, int pageSize);

    public List<M> listByHql(String hql, Map params);

    public Page<M> pre(PK pk, int pn, int pageSize);
    
    public Page<M> next(PK pk, int pn, int pageSize);
    
    public Page<M> pre(PK pk, int pn);
    
    public Page<M> next(PK pk, int pn);
}
