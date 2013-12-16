package com.hally.pagination;


import com.hally.common.Constants;

/**
 * ��ҳ�����Ļ��������ڼ���Page��
 */
public interface IPageContext<E> {

    /**
     * Ĭ���趨ÿҳ��ʾ��¼��Ϊ10
     */
    public static final int DEFAULT_PAGE_SIZE = Constants.DEFAULT_PAGE_SIZE;

    /**
     * ������ҳ��.
     *
     * @return
     */
    public int getPageCount();


    /**
     * ���� Page ����.
     *
     * @param index
     * @return
     */
    public Page<E> getPage(int index);

    /**
     * ÿҳ��ʾ�ļ�¼����
     *
     * @return
     */
    public int getPageSize();

    /**
     * �����ܼ�¼��
     *
     * @return
     */
    public int getTotal();

}
