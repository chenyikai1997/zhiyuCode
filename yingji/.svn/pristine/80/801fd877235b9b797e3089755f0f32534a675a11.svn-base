package com.fh.service.backend.m_fengxian;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 风险接口
 * 创建人：FH Q313596790
 * 创建时间：2023-09-28
 * @version
 */
public interface M_fengxianManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**风险管理点击后的表单
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listByAreaAndLevel(PageData pd)throws Exception;

	/**按地区划分统计风险点
	 * @param pd
	 * @throws Exception
	 */
	public PageData listFengXianByArea(PageData pd)throws Exception;

	/**驾驶舱顶部统计数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData tongJiFengXian(PageData pd)throws Exception;

	/**驾驶舱右边统计风险等级
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> tongJiFengXianLevel(PageData pd)throws Exception;

	/**驾驶舱右上角统计可能导致的事故类型
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> tongJiAccident(PageData pd)throws Exception;

	/**驾驶舱右下角趋势图
	 * @param pd
	 * @throws Exception
	 */
	public PageData tongJiFengXianByDate(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**风险二级页面
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> fengXianErJi(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**按简称查找
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByShort(PageData pd)throws Exception;

	/**
	 * 获取总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception;
	
}

