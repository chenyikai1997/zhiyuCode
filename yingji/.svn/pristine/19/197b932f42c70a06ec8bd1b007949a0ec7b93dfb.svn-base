package com.fh.service.backend.rectifyinfo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.backend.rectifyinfo.RectifyInfoManager;
import com.fh.util.PageData;

/** 
 * 说明： 整改信息
 * 创建人：FH Q313596790
 * 创建时间：2023-06-21
 * @version
 */
@Service("rectifyinfoService")
public class RectifyInfoService implements RectifyInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(List<PageData> list)throws Exception{
		dao.save("RectifyInfoMapper.batchSave", list);
	}
	
	/**批量新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RectifyInfoMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("RectifyInfoMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RectifyInfoMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("RectifyInfoMapper.datalistPage", page);
		for (PageData data : list) {
			String IMG_URL = data.getString("IMG_URL");
			if (StringUtils.isNotEmpty(IMG_URL)) {
				String IMG_ARR[] = IMG_URL.split(",");
				data.put("IMG_ARR", IMG_ARR);
				data.put("IMG_1", IMG_ARR[0]);
			}

			String REPORTING_FILE_URL = data.getString("REPORTING_FILE");
			if (StringUtils.isNotEmpty(REPORTING_FILE_URL)) {
				String REPORTING_FILE_ARR[] = REPORTING_FILE_URL.split(",");
				data.put("REPORTING_FILE_ARR", REPORTING_FILE_ARR);
				data.put("IMG_1", REPORTING_FILE_ARR[0]);
			}
		}
		return list;
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("RectifyInfoMapper.listAll", pd);
		for (PageData data : list) {
			String IMG_URL = data.getString("IMG_URL");
			if (StringUtils.isNotEmpty(IMG_URL)) {
				String IMG_ARR[] = IMG_URL.split(",");
				data.put("IMG_ARR", IMG_ARR);
				data.put("IMG_1", IMG_ARR[0]);
			}
			String REPORTING_FILE_URL = data.getString("REPORTING_FILE");
			if (StringUtils.isNotEmpty(REPORTING_FILE_URL)) {
				String REPORTING_FILE_ARR[] = REPORTING_FILE_URL.split(",");
				data.put("REPORTING_FILE_ARR", REPORTING_FILE_ARR);
				data.put("IMG_1", REPORTING_FILE_ARR[0]);
			}
		}
		return list;
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		PageData data = (PageData)dao.findForObject("RectifyInfoMapper.findById", pd);
		String IMG_URL = null;
		String REPORTING_FILE_URL = null;
		if(data == null){
			return data;
		}
		if(StringUtils.isNotEmpty(data.getString("IMG_URL"))){
			IMG_URL = data.getString("IMG_URL");
		}

		if(StringUtils.isNotEmpty(data.getString("REPORTING_FILE"))){
			REPORTING_FILE_URL = data.getString("REPORTING_FILE");
		}

		if (StringUtils.isNotEmpty(IMG_URL)) {
			String IMG_ARR[] = IMG_URL.split(",");
			data.put("IMG_ARR", IMG_ARR);
			data.put("IMG_1", IMG_ARR[0]);
		}

		if (StringUtils.isNotEmpty(REPORTING_FILE_URL)) {
			String REPORTING_FILE_ARR[] = REPORTING_FILE_URL.split(",");
			data.put("REPORTING_FILE_ARR", REPORTING_FILE_ARR);
			data.put("IMG_1", REPORTING_FILE_ARR[0]);
		}
		
		return data;
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd)throws Exception{
		PageData data = (PageData)dao.findForObject("RectifyInfoMapper.findByNumber", pd);
		String IMG_URL = null;
		String REPORTING_FILE_URL = null;
		if(data == null){
			return data;
		}
		if(StringUtils.isNotEmpty(data.getString("IMG_URL"))){
			IMG_URL = data.getString("IMG_URL");
		}

		if(StringUtils.isNotEmpty(data.getString("REPORTING_FILE"))){
			REPORTING_FILE_URL = data.getString("REPORTING_FILE");
		}

		if (StringUtils.isNotEmpty(IMG_URL)) {
			String IMG_ARR[] = IMG_URL.split(",");
			data.put("IMG_ARR", IMG_ARR);
			data.put("IMG_1", IMG_ARR[0]);
		}

		if (StringUtils.isNotEmpty(REPORTING_FILE_URL)) {
			String REPORTING_FILE_ARR[] = REPORTING_FILE_URL.split(",");
			data.put("REPORTING_FILE_ARR", REPORTING_FILE_ARR);
			data.put("IMG_1", REPORTING_FILE_ARR[0]);
		}

		return data;
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception{
		dao.delete("RectifyInfoMapper.deleteAll", pd);
	}

	/**批量提交
	 * @param pd
	 * @throws Exception
	 */
	public void submitAll(PageData pd)throws Exception{
		dao.delete("RectifyInfoMapper.submitAll", pd);
	}
	
	/**统计总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData tongjiAll(PageData pd)throws Exception{
		PageData tongjiAll = (PageData)dao.findForObject("RectifyInfoMapper.tongjiAll", pd);
		
		return tongjiAll;
	}
	
	/**查询截至当天的数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData tongjiUntil(PageData pd)throws Exception{
		PageData tongjiAll = (PageData)dao.findForObject("RectifyInfoMapper.tongjiUntil", pd);
		
		return tongjiAll;
	}
	
	/**
	 * 按隐患类别分类统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongjiClassify(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RectifyInfoMapper.tongjiClassify", pd);
	}
	
	/**
	 * 按隐患因素统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongjiFactor(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RectifyInfoMapper.tongjiFactor", pd);
	}
	
	/**
	 * 按单位统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongjiByOrg(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RectifyInfoMapper.tongjiByOrg", pd);
	}

	/**
	 * 按单位统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData tongjiRectifyMap(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RectifyInfoMapper.tongjiRectifyMap", pd);
	}

	/**
	 * 按rectifyinfo_stage统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> tongjiByStage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RectifyInfoMapper.tongjiByStage", pd);
	}

	/**
	 * 按项目id统计隐患
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData tongjiByProject(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RectifyInfoMapper.tongjiByProject", pd);
	}

	/**
	 * 按项目id进行隐患整改率统计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData tongjiByProjectOnPrecent(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RectifyInfoMapper.tongjiByProjectOnPrecent", pd);
	}

	/**
	 * 项目详情部分只获取对应字段
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForProject(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RectifyInfoMapper.listForProject", pd);
	}

	/**按简称查找
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByShort(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RectifyInfoMapper.findByShort", pd);
	}

	/**
	 * 获取总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RectifyInfoMapper.findCount", pd);
	}
}

