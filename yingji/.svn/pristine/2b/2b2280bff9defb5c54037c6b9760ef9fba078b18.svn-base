package com.fh.service.backend.morg;

import java.util.List;
import com.fh.entity.Page;
import com.fh.entity.backend.MOrg;
import com.fh.util.PageData;

/** 
 * 说明： 组织结构接口
 * 创建人：FH Q313596790
 * 创建时间：2023-06-26
 * @version
 */
public interface MOrgManager{

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

	/**二级机构风险点列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listFengXian(PageData pd)throws Exception;

	/**统计所有项目
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listProjectByArea(PageData pd)throws Exception;

	/**通过Name获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<MOrg> listByParentId(PageData pd) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<MOrg> listTree(String parentId,int ISORG) throws Exception;

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSelectTree(PageData pd) throws Exception;
	
	/**查重
	 * @param pd
	 * @throws Exception
	 */
	public PageData duplicate(PageData pd)throws Exception;

	/**恢复
	 * @param pd
	 * @throws Exception
	 */
	public void restore(PageData pd)throws Exception;

	/**
	 * 获取总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception;
	
}

