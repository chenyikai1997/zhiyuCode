package com.fh.service.backend.rectifyinfo;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 整改信息接口
 * 创建人：FH Q313596790
 * 创建时间：2023-06-21
 * @version
 */
public interface RectifyInfoManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(List<PageData> list)throws Exception;
	
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
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过流水号获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception;

	/**批量提交
	 * @param pd
	 * @throws Exception
	 */
	public void submitAll(PageData pd)throws Exception;
	
	/**
	 * 统计总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData tongjiAll(PageData pd)throws Exception;
	
	/**查询截至当天的数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData tongjiUntil(PageData pd)throws Exception;
	
	/**
	 * 按隐患类别分类统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> tongjiClassify(PageData pd)throws Exception;
	
	/**
	 * 按隐患因素统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> tongjiFactor(PageData pd)throws Exception;

	/**
	 * 按单位统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> tongjiByOrg(PageData pd)throws Exception;

	/**
	 * 应急平台首页第二张隐患的地图
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData tongjiRectifyMap(PageData pd)throws Exception;

	/**
	 * 按rectifyinfo_stage统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongjiByStage(PageData pd)throws Exception;

	/**
	 * 按项目id统计隐患
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData tongjiByProject(PageData pd)throws Exception;

	/**
	 * 按项目id进行隐患整改率统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData tongjiByProjectOnPrecent(PageData pd)throws Exception;


	/**
	 * 项目详情部分只获取对应字段
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForProject(PageData pd)throws Exception;

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

