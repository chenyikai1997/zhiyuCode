package com.fh.service.backend.morg.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.backend.MOrg;
import com.fh.service.backend.morg.MOrgManager;
import com.fh.util.PageData;

/** 
 * 说明： 组织结构
 * 创建人：FH Q313596790
 * 创建时间：2023-06-26
 * @version
 */
@Service("morgService")
public class MOrgService implements MOrgManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MOrgMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MOrgMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MOrgMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MOrgMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("MOrgMapper.listAll", pd);
		return list;
	}

	/**二级机构风险点列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listFengXian(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MOrgMapper.listFengXian", pd);
	}

	/**统计所有项目
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listProjectByArea(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MOrgMapper.listProjectByArea", pd);
	}

	@Override
	public PageData findByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MOrgMapper.findByName", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MOrgMapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MOrg> listByParentId(PageData pd) throws Exception {
		return (List<MOrg>) dao.findForList("MOrgMapper.listByParentId", pd);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<MOrg> listTree(String parentId,int ISORG) throws Exception {
		PageData param = new PageData();
		param.put("PARENT_ID", parentId);
		//只查找组织或只查找项目
		param.put("ISORG", ISORG);
		List<MOrg> valueList = this.listByParentId(param);
		for(MOrg fhentity : valueList){
			fhentity.setTreeurl("morg/list.do?ORG_ID="+fhentity.getORG_ID()+"&ISORG="+fhentity.getISORG());
			fhentity.setSubMOrg(this.listTree(fhentity.getORG_ID(),fhentity.getISORG()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSelectTree(PageData pd) throws Exception {
		List <PageData> rList = new ArrayList<PageData>();
		
		List<MOrg> valueList = this.listByParentId(pd);
		for(MOrg fhentity : valueList){
			PageData paramPd = new PageData();
			paramPd.putAll(pd);
			paramPd.put("PARENT_ID", fhentity.getORG_ID());
			
			PageData aData = new PageData();
			aData.put("name", fhentity.getORG_NAME());
			aData.put("id", fhentity.getORG_ID());
			aData.put("parentId", fhentity.getPARENT_ID());
			rList.add(aData);
			List<PageData> next = this.listSelectTree(paramPd);
			rList.addAll(next);
		}
		return rList;
	}
	
	/**查重
	 * @param pd
	 * @throws Exception
	 */
	public PageData duplicate(PageData pd)throws Exception{

		return (PageData)dao.findForObject("MOrgMapper.duplicate", pd);
	}

	/**恢复
	 * @param pd
	 * @throws Exception
	 */
	public void restore(PageData pd)throws Exception{
		dao.update("MOrgMapper.restore", pd);
	}

	/**
	 * 获取总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MOrgMapper.findCount", pd);
	}
}

