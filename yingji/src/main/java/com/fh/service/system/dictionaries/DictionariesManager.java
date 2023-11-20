package com.fh.service.system.dictionaries;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;

/** 
 * 说明： 数据字典接口类
 * 创建人：FH Q313596790
 * 创建时间：2015-12-16
 * @version
 */
public interface DictionariesManager{

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
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception;

	/**通过名字获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listSubDictByParentId(String parentId) throws Exception;

	/**
	 * 通过ID获取其子级列表(pd类)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSubDictByParentIdPd(PageData pd) throws Exception;

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSelectTree(PageData pd) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listAllDict(String parentId) throws Exception;
	
	/**列表(全部，用于生成ID下拉树结构) 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionaries> listAllForIdSelectTree(String parentId)throws Exception;
	
	/**
	 * 通过英文名称获取下级
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> listChildrenByEN(String NAME_EN, boolean isBianmaKey) throws Exception;
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFromTbs(PageData pd)throws Exception;
	
}

