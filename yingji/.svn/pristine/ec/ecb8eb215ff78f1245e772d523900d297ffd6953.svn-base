package com.fh.service.backend.m_fengxian.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.backend.m_fengxian.M_fengxianManager;

/** 
 * 说明： 风险
 * 创建人：FH Q313596790
 * 创建时间：2023-09-28
 * @version
 */
@Service("m_fengxianService")
public class M_fengxianService implements M_fengxianManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("M_fengxianMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("M_fengxianMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("M_fengxianMapper.edit", pd);
	}


	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.listAll", pd);
	}

	/**风险管理点击后的表单
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listByAreaAndLevel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.listByAreaAndLevel", pd);
	}

	/**按地区划分统计风险点
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData listFengXianByArea(PageData pd)throws Exception{
		return (PageData)dao.findForObject("M_fengxianMapper.listFengXianByArea", pd);

	}

	/**驾驶舱顶部统计数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData tongJiFengXian(PageData pd)throws Exception{
		return (PageData)dao.findForObject("M_fengxianMapper.tongJiFengXian", pd);
	}

	/**驾驶舱右边统计风险等级
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongJiFengXianLevel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.tongJiFengXianLevel", pd);
	}

	/**驾驶舱右上角统计可能导致的事故类型
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongJiAccident(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.tongJiAccident", pd);
	}

	/**驾驶舱右下角趋势图
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData tongJiFengXianByDate(PageData pd)throws Exception{
		return (PageData)dao.findForObject("M_fengxianMapper.tongJiFengXianByDate", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("M_fengxianMapper.findById", pd);
	}

	/**风险二级页面
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> fengXianErJi(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.fengXianErJi", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("M_fengxianMapper.deleteAll", ArrayDATA_IDS);
	}

	/**按简称查找
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByShort(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("M_fengxianMapper.findByShort", pd);
	}

	/**
	 * 获取总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("M_fengxianMapper.findCount", pd);
	}
	
}

