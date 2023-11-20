package com.fh.controller.backend.morg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.common.WebConstant;
import com.fh.entity.backend.MOrg;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.backend.m_monitor_project.M_monitor_projectManager;
import com.fh.service.backend.m_project_id.M_project_idManager;
import com.fh.service.backend.m_water_project.M_water_projectManager;
import com.fh.service.backend.reservoir.ReservoirManager;
import com.fh.service.backend.typhoon.TyphoonManager;
import com.fh.service.backend.water.WaterManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.*;
import net.sf.json.JSONArray;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.backend.morg.MOrgManager;

import static jdk.nashorn.internal.objects.NativeString.substring;

/**
 * 说明：组织结构
 * 创建人：FH Q313596790
 * 创建时间：2023-06-26
 */
@Controller
@RequestMapping(value="/morg")
public class MOrgController extends BaseController {

	String menuUrl = "morg/list.do"; //菜单地址(权限用)
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="morgService")
	private MOrgManager morgService;

	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="waterService")
	private WaterManager waterService;
	@Resource(name="typhoonService")
	private TyphoonManager typhoonService;
	@Resource(name="reservoirService")
	private ReservoirManager reservoirService;
	@Resource(name="m_water_projectService")
	private M_water_projectManager m_water_projectService;
	@Resource(name="m_monitor_projectService")
	private M_monitor_projectManager m_monitor_projectService;
	@Resource(name="m_project_idService")
	private M_project_idManager m_project_idService;

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增MOrg");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData savePd = new PageData();

		//pd传入parentId，可以查找parent的层级然后加1
		PageData parentParam = new PageData();
		parentParam.put("ORG_ID",pd.getString("PARENT_ID"));
		parentParam = morgService.findById(parentParam);

		//顶级机构不填写
		if(parentParam != null){
			if(parentParam.get("ORG_LEVEL") == null){
				savePd.put("ORG_LEVEL", 2);
			}
			else{
				savePd.put("ORG_LEVEL",(Integer)parentParam.get("ORG_LEVEL")+1);
			}
		}

		savePd.put("ORG_ID",get32UUID());

		//循环存储项目和组织机构
		String ProjectOrg_ids = pd.getString("PROJECT_UNIT");
		if(null != ProjectOrg_ids && !"".equals(ProjectOrg_ids)){
			//分割RESERVOIR_ID字符串
			String[] ArrayDATA_IDS = ProjectOrg_ids.split(",");
			PageData projectParam = new PageData();

			projectParam.put("PROJECT_ID",savePd.getString("ORG_ID"));
			for(String IDS : ArrayDATA_IDS){
				projectParam.put("M_PROJECT_ORG",this.get32UUID());
				projectParam.put("ORG_ID",IDS);
				m_project_idService.save(projectParam);
			}
		}

		//循环存储水库和项目
		String DATA_IDS = pd.getString("RESERVOIR_ID");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			//分割RESERVOIR_ID字符串
			String[] ArrayDATA_IDS = DATA_IDS.split(",");
			PageData waterParam = new PageData();

			waterParam.put("PROJECT_ID",savePd.getString("ORG_ID"));
			for(String IDS : ArrayDATA_IDS){
				waterParam.put("M_WATER_PROJECT_ID",this.get32UUID());
				waterParam.put("WATER_ID",IDS);
				m_water_projectService.save(waterParam);
			}
		}

//		int level =  pd.getString("ORG_CODE").length()/2;
//		savePd.put("ORG_LEVEL", level);															//机构层级

		savePd.put("PARENT_ID",pd.getString("PARENT_ID"));									//上级id
		savePd.put("ORG_NAME_SHORT",pd.getString("ORG_NAME_SHORT"));						//机构简称
		savePd.put("ORG_NAME",pd.getString("ORG_NAME"));									//机构名称

		savePd.put("ORG_CODE",pd.getString("ORG_CODE"));									//机构名称
		savePd.put("ISORG",pd.get("ISORG"));													//区分机构和项目

		savePd.put("REMARK",pd.getString("REMARK"));										//简介

		savePd.put("ISDEL", 0);																	//是否删除
		savePd.put("SORT", pd.getString("SORT"));											//排序
		savePd.put("CREATER", Jurisdiction.getUsername());										//创建人
		savePd.put("CREATE_DATE", Tools.date2Str(new Date()));									//创建时间

		savePd.put("PROJECT_AREA",pd.getString("PROJECT_AREA"));						//项目所属区域
		System.out.println("存入的pd"+savePd);

		morgService.save(savePd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String ORG_ID) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除MOrg");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} 					//校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("ORG_ID", ORG_ID);
		PageData test = morgService.findById(pd);
		String errInfo = "success";
		PageData param = new PageData();
		param.put("PARENT_ID", ORG_ID);
		if(morgService.listByParentId(param).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			morgService.delete(test);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改MOrg");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//根据项目id删除对应水库和监控
		PageData deleteParam = new PageData();
		deleteParam.put("PROJECT_ID",pd.getString("ORG_ID"));
		//如果没有选择任何水库就不做删除
		if(pd.getString("RESERVOIR_ID") != null && !pd.getString("RESERVOIR_ID").equals("")){
			m_water_projectService.deleteByProject(deleteParam);
		}
		if(pd.getString("MONITOR_ID") != null && !pd.getString("MONITOR_ID").equals("")){
			m_monitor_projectService.deleteByProject(deleteParam);
		}
		if(pd.getString("PROJECT_UNIT") != null && !pd.getString("PROJECT_UNIT").equals("")){
			m_project_idService.deleteByProject(deleteParam);
		}


//		编辑的地方就不再修改层级
//		int level =  pd.getString("ORG_CODE").length()/2;
//		pd.put("ORG_LEVEL", level);
		pd.put("ISDEL", 0);	//是否删除
		pd.put("MODIFYER", Jurisdiction.getUserId());										//修改人
		pd.put("MODIFY_DATE", Tools.date2Str(new Date()));									//修改时间

		//循环存储项目和组织机构
		String ProjectOrg_ids = pd.getString("PROJECT_UNIT");
		if(null != ProjectOrg_ids && !"".equals(ProjectOrg_ids)){
			//分割RESERVOIR_ID字符串
			String[] ArrayDATA_IDS = ProjectOrg_ids.split(",");
			PageData projectParam = new PageData();

			projectParam.put("PROJECT_ID",pd.getString("ORG_ID"));
			for(String IDS : ArrayDATA_IDS){
				projectParam.put("M_PROJECT_ORG",this.get32UUID());
				projectParam.put("ORG_ID",IDS);
				m_project_idService.save(projectParam);
			}
		}

		//循环存储水库和项目
		String DATA_IDS = pd.getString("RESERVOIR_ID");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			//分割RESERVOIR_ID字符串
			String[] ArrayDATA_IDS = DATA_IDS.split(",");
			PageData waterParam = new PageData();

			waterParam.put("PROJECT_ID",pd.getString("ORG_ID"));
			for(String IDS : ArrayDATA_IDS){
				waterParam.put("M_WATER_PROJECT_ID",this.get32UUID());
				waterParam.put("WATER_ID",IDS);
				m_water_projectService.save(waterParam);
			}
		}

		//循环存储监控和项目
		String DATA_IDS2 = pd.getString("MONITOR_ID");
		if(null != DATA_IDS2 && !"".equals(DATA_IDS2)){
			//分割RESERVOIR_ID字符串
			String[] ArrayDATA_IDS = DATA_IDS2.split(",");
			PageData monitorParam = new PageData();

			monitorParam.put("PROJECT_ID",pd.getString("ORG_ID"));
			for(String IDS : ArrayDATA_IDS){
				monitorParam.put("M_MONITOR_PROJECT_ID",this.get32UUID());
				monitorParam.put("MONITOR_ID",IDS);
				m_monitor_projectService.save(monitorParam);
			}
		}
		//改成表中存储项目和组织机构关联，不用project_unit字段
		pd.remove("PROJECT_UNIT");
		morgService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表MOrg");

//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 	//校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();											//用于查询下级列表
		PageData pds = new PageData();											//存储keywords
		pd = this.getPageData();

		String keywords = pd.getString("keywords");						//关键词检索条件
		if(null != keywords && !"".equals(keywords)){							//获取keywords
			pd.put("keywords", keywords.trim());
			pds.put("keywords", keywords.trim());
		}

		String ORG_ID = null == pd.get("ORG_ID")?"":pd.get("ORG_ID").toString();//判断是否有org_id，如果没有说明是主页或者查询时
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			ORG_ID = pd.get("id").toString();
		}

		if(ORG_ID != null){
			pd.put("ORG_ID", ORG_ID);												//上级ID
		}

		page.setPd(pd);
		if(pd.getString("ISORG").equals("0")){

		}
		List<PageData>	varList = morgService.list(page);						//列出MOrg列表

		//根据isorg判断是读组织还是项目,用来下拉选择筛选所属单位
		String isOrg = pd.getString("ISORG");
		if(isOrg.equals("0")){
			mv.setViewName("backend/morg/morg_project_list");
		}
		else{
			mv.setViewName("backend/morg/morg_list");

		}

		//读取单位列表，用于选择项目所属单位
		PageData param = new PageData();
		param.put("ISORG",1);
		List<PageData> unitList = morgService.listAll(param);
		mv.addObject("unitList", unitList);

		PageData newPd = morgService.findById(pd);
		if(newPd == null){
			newPd = pd;
		}
		else{
			newPd.put("ISORG",isOrg);
		}

		mv.addObject("pds", pds);									//传入上级所有信息
		mv.addObject("pd", newPd);									//传入点击的机构的信息
		mv.addObject("ORG_ID", ORG_ID);							//上级ID,点击新增时会需要用到
		mv.addObject("varList", varList);							//列表信息
		mv.addObject("QX",Jurisdiction.getHC());					//按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String ORG_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		int isOrg = Integer.parseInt(pd.getString("ISORG"));
		try{
			//设置parentid
			JSONArray arr = JSONArray.fromObject(morgService.listTree("0",isOrg));

			String json = arr.toString();

			//name 节点显示的文本
			//id 节点标识属性
			//PId 节点parentid属性
			//children 得到该节点所有孩子节点，直接下级，若要得到所有下属层级节点，需要自己写递归得到
			json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes").replaceAll("hasMOrg", "checked").replaceAll("treeurl", "url").replaceAll("\r\n", "");;
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ISORG",pd.get("ISORG"));
			mv.addObject("pd", pd);
			mv.setViewName("backend/morg/morg_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pds = morgService.findById(pd);
		//没有点进组织架构时直接新增会传入空的orgId字符串，会导致空指针异常
		if(pds != null && StringUtils.isNotEmpty(pds.getString("ORG_CODE")) && !pds.getString("ORG_CODE").equals("")){
			pd.put("ORG_CODE",pds.getString("ORG_CODE"));
		}
		String ORG_ID = null == pd.get("ORG_ID")?"":pd.get("ORG_ID").toString();
		pd.put("ORG_ID", ORG_ID);					//上级ID
		mv.addObject("pds",pds);									//传入上级所有信息
		mv.addObject("pd",pd);										//传入上级的id和code
		//用ISORG判断是公司还是项目
		if(pd.getString("ISORG").equals(WebConstant.IS_YES)){
			mv.setViewName("backend/morg/morg_edit");
		}
		else{
			mv.setViewName("backend/morg/morg_project_edit");
		}

		//获取组织机构树
		List<MOrg> orgList = morgService.listTree("0",1);
		JSONArray arr = JSONArray.fromObject(orgList);
		String json = arr.toString();
		json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes").replaceAll("hasMOrg", "checked");
		mv.addObject("zTreeNodes", json);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
		JSONArray arr1 = JSONArray.fromObject(areaList);
		String zTreeNodes = (null == arr1? "":arr1.toString());
		zTreeNodes = zTreeNodes.replaceAll("NAME","name").replaceAll("DICTIONARIES_ID","id").replaceAll("PARENT_ID","parentId").replaceAll("subDict","nodes");
		mv.addObject("zTreeNodes2", zTreeNodes);

		//读取单位列表，用于选择项目所属单位
		PageData param = new PageData();
		param.put("ISORG",1);
		List<PageData> unitList = morgService.listAll(param);

		mv.addObject("areaList", areaList);
		mv.addObject("unitList", unitList);						//项目所属单位
		mv.addObject("msg", "save");
		return mv;
	}

	/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		pd = morgService.findById(pd);							//根据ID读取
		mv.addObject("pd", pd);

		PageData pds = new PageData();
		pds.put("ORG_ID",pd.get("PARENT_ID").toString());			//编码
		pds = morgService.findById(pds);                    		//上级内容
		mv.addObject("pds", pds);
		mv.addObject("ORG_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用

		//用ISORG判断是公司还是项目
		if((Integer)(pd.get("ISORG")) == 1){
			mv.setViewName("backend/morg/morg_edit");
		}
		else{
			mv.setViewName("backend/morg/morg_project_edit");
		}

		String USER_ID = Jurisdiction.getUserId();

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);

		if((Integer)pd.get("ISORG") == 0 && pd.getString("PROJECT_AREA") != null && !pd.getString("PROJECT_AREA").equals("")){
			readArea(areaList, pd.getString("PROJECT_AREA"));

			JSONArray arr1 = JSONArray.fromObject(areaList);
			String zTreeNodes = (null == arr1? "":arr1.toString());
			zTreeNodes = zTreeNodes.replaceAll("NAME","name").replaceAll("DICTIONARIES_ID","id").replaceAll("PARENT_ID","parentId").replaceAll("subDict","nodes").replaceAll("hasDict", "checked");
			mv.addObject("zTreeNodes2", zTreeNodes);
		}


		//读取单位列表，用于选择项目所属单位
		PageData param = new PageData();
//		param.put("ORG_LEVEL",2);
		List<PageData> unitList = morgService.listAll(param);

		//循环判断哪些组织机构被选中了
		param.put("USER_ID", USER_ID);
		List<MOrg> orgList = morgService.listTree("0",1);
		param.put("PROJECT_ID", pd.getString("ORG_ID"));
		List<PageData> exitOrgqx = m_project_idService.listAll(param);
		String exitOrgids = "";
		for (PageData org : exitOrgqx) {
			if (StringUtils.isNotEmpty(exitOrgids)) {
				exitOrgids += "," + org.getString("ORG_ID");
			} else {
				exitOrgids = org.getString("ORG_ID");
			}
		}
		readOrg(orgList, exitOrgids);

		JSONArray arr = JSONArray.fromObject(orgList);
		String json = arr.toString();
		json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes").replaceAll("hasMOrg", "checked");
		mv.addObject("zTreeNodes", json);


		//搜索与该项目相关的水库
		PageData waterParam = new PageData();
		waterParam.put("PROJECT_ID",pd.getString("ORG_ID"));
		List<PageData> waterList = m_water_projectService.listForName(waterParam);
		//循环把名字放入字符串就行
		String waterName = "";
		for(PageData temp : waterList){
			if(waterName.equals("")){
				waterName += temp.getString("RESERVOIR_NAME");
			}
			else{
				waterName += "," + temp.getString("RESERVOIR_NAME");
			}

		}
		mv.addObject("waterName", waterName);

		//搜索与该项目相关的水库
		PageData monitorParam = new PageData();
		monitorParam.put("PROJECT_ID",pd.getString("ORG_ID"));
		List<PageData> monitorList = m_monitor_projectService.listForName(monitorParam);
		//循环把名字放入字符串就行
		String monitorName = "";
		for(PageData temp : monitorList){
			if(monitorName.equals("")){
				monitorName += temp.getString("MONITOR_NAME");
			}
			else{
				monitorName += "," + temp.getString("MONITOR_NAME");
			}

		}
		mv.addObject("monitorName", monitorName);

		mv.addObject("areaList", areaList);
		mv.addObject("unitList", unitList);						//项目所属单位
		mv.addObject("msg", "edit");
		return mv;
	}

	/**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出MOrg到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("机构编码");	//1
		titles.add("机构名称");	//2
		titles.add("机构简称");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = morgService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ORG_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("ORG_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("ORG_NAME_SHORT"));	    //3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excelProject")
	public ModelAndView exportExcelProject() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出MOrg到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ISORG",0);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("项目名称");	//1
		titles.add("项目简称");	//1
		titles.add("项目编码");	//1
		titles.add("项目简介");	//1
		titles.add("所属单位");	//2
		titles.add("项目所属区域");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = morgService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ORG_NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("ORG_NAME_SHORT"));	    //1
			vpd.put("var3", varOList.get(i).getString("ORG_CODE"));	    //1
			vpd.put("var4", varOList.get(i).getString("REMARK"));	    //1
			PageData temp = new PageData();
			//循环获取相关公司
			temp.put("PROJECT_ID",varOList.get(i).getString("ORG_ID"));
			List<PageData> connectOrgList = m_project_idService.listAll(temp);
			String unitString = "";
			for(PageData tempPd : connectOrgList){
				if(unitString.equals("")){
					unitString += tempPd.getString("ORG_ID");
				}
				else{
					unitString += "," + tempPd.getString("ORG_ID");
				}
			}

			vpd.put("var5", unitString);	    									//2
			vpd.put("var6", varOList.get(i).getString("PROJECT_AREA"));	    //3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);

		//全部机构
		List<PageData> orgList = morgService.listAll(null);
		PageData orgMap = new PageData();
		for (PageData org : orgList) {
			orgMap.put(org.getString("ORG_ID"), org.getString("ORG_NAME"));
		}
		for(PageData temp : varList){
			if(temp.getString("var5") != null){
				String orgName = "";
				String[] str = temp.getString("var5").split(",");
				for(String tempStr : str){
					if(orgName.equals("")){
						orgName += orgMap.get(tempStr);
					}
					else{
						orgName += "," + orgMap.get(tempStr);
					}

				}
				temp.put("var5",orgName);
				if(orgName.equals("null") || orgName == null){
					temp.put("var5","");
				}

			}
		}

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllForIdSelectTree(hainanParam);

		//三层循环修改id为名字，可能耗时会长
		for(PageData temp : varList){
			for(Dictionaries dictionaries : areaList){
				if(temp.getString("var6") != null && temp.getString("var6").equals(dictionaries.getDICTIONARIES_ID())){
					temp.put("var6",dictionaries.getNAME());
				}
				if(dictionaries.getSubDict() != null){
					for(Dictionaries subDict : dictionaries.getSubDict()){
						if(temp.getString("var6") != null && temp.getString("var6").equals(subDict.getDICTIONARIES_ID())){
							temp.put("var6",subDict.getNAME());
						}
						if(subDict.getSubDict() != null){
							for(Dictionaries subSubDict : subDict.getSubDict()){
								if(temp.getString("var6") != null && temp.getString("var6").equals(subSubDict.getDICTIONARIES_ID())){
									temp.put("var6",subSubDict.getNAME());
								}
							}
						}
					}
				}

			}
		}
		ObjectExcelViewProjectExport erv = new ObjectExcelViewProjectExport();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
	) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "morg");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			/*List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());		//设置角色ID为随便第一个*/
			/**
			 * var0 :组织id
			 * var1 :机构名称
			 */

			HashMap<String,String> hashmap = new HashMap<>();
			List<PageData> list = morgService.listAll(pd);
			for(int i=0;i<list.size();i++){
				String org_code = list.get(i).getString("ORG_CODE");
				hashmap.put(org_code,list.get(i).getString("ORG_ID"));
			}
			for(int i=0;i<listPd.size();i++){
				String org_code = listPd.get(i).getString("var0");
				hashmap.put(org_code,this.get32UUID());
			}
			for(int i=0;i<listPd.size();i++){
				try{

					String parent_id = listPd.get(i).getString("var0");
					parent_id = parent_id.length()<=2 ? "0" : substring(parent_id,0,parent_id.length()-2); //截取上级code

					String org_code = listPd.get(i).getString("var0");

					pd.put("ORG_ID", hashmap.get(org_code));
					pd.put("SORT", i+1);															//排序
					pd.put("ISDEL", 0);																//是否删除
					pd.put("CREATER",Jurisdiction.getUserId());										//创建者
					pd.put("CREATE_DATE",Tools.date2Str(new Date()));								//创建时间
					pd.put("PARENT_ID",hashmap.get(parent_id));										//上级机构id截取
					pd.put("ORG_CODE",org_code);													//机构编码
					pd.put("ORG_NAME", listPd.get(i).getString("var1"));						//机构名称

					int level =  listPd.get(i).getString("var0").length()/2;
					pd.put("ORG_LEVEL", level);														//机构层级
					pd.put("ISORG",0);


					if(morgService.findById(pd) != null){
						continue;
					}

					morgService.save(pd);

				}catch (Exception e){
					PageData restorePd = new PageData();
					pd.put("ORG_ID", listPd.get(i).getString("var0"));
					morgService.restore(pd);
				}

			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**从EXCEL导入到数据库(项目)
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcelProject")
	public ModelAndView readExcelProject(
			@RequestParam(value="excel",required=false) MultipartFile file
	) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "projectXls"+Tools.date2Str(new Date(),"yyyyMMddHHmmss"));							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			/*List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());		//设置角色ID为随便第一个*/
			/**
			 * var0 :组织id
			 * var1 :机构名称
			 */

			List<PageData> orgList = morgService.listAll(null);
			PageData orgMap = new PageData();
			for (PageData org : orgList) {
				orgMap.put(org.getString("ORG_NAME"), org.getString("ORG_ID"));
			}

			PageData param = new PageData();
			param.put("ISORG",0);
			PageData projectMap = new PageData();
			List<PageData> projectList = morgService.listAll(param);
			for (PageData project : projectList) {
				projectMap.put(project.getString("ORG_NAME"), project.getString("ORG_ID"));
			}
			String alertText = "";
			for(int i=0;i<listPd.size();i++){
				try{

					pd.put("ORG_ID", this.get32UUID());
					pd.put("SORT", i+1);															//排序
					pd.put("ISDEL", 0);																//是否删除
					pd.put("CREATER",Jurisdiction.getUserId());										//创建者
					pd.put("CREATE_DATE",Tools.date2Str(new Date()));								//创建时间

					pd.put("ORG_NAME", listPd.get(i).getString("var0"));						//项目名称

					//项目所属机构
					if(listPd.get(i).getString("var4") != null && !listPd.get(i).getString("var4").equals("")){
						String var4 = listPd.get(i).getString("var4");
						String separator = "";
						String separators[] = {",","，","/",".","-"};
						for(String tempSeparator : separators){
							if(var4.contains(tempSeparator)){
								separator = tempSeparator;
							}
						}
						String[] varArray = var4.split(separator);
						for(String str : varArray){
							PageData temp = new PageData();
							temp.put("M_PROJECT_ORG",this.get32UUID());
							temp.put("PROJECT_ID",pd.getString("ORG_ID"));
							temp.put("ORG_ID",orgMap.get(str));

							m_project_idService.save(temp);
						}
					}

					PageData temp = new PageData();
					temp.put("NAME",listPd.get(i).getString("var5"));
					PageData areaParam = dictionariesService.findByName(temp);
					if(areaParam != null){
						pd.put("PROJECT_AREA", areaParam.getString("DICTIONARIES_ID"));					//项目地区
					}

					pd.put("ISORG",0);
					pd.put("PARENT_ID","0");													//默认顶级
					//简称
					if(listPd.get(i).getString("var1") != null && !listPd.get(i).getString("var1").equals("")){
						pd.put("ORG_NAME_SHORT",listPd.get(i).getString("var1"));
					}
					//编码
					if(listPd.get(i).getString("var2") != null && !listPd.get(i).getString("var2").equals("")){
						pd.put("ORG_CODE",listPd.get(i).getString("var2"));
					}
					//简介
					if(listPd.get(i).getString("var3") != null && !listPd.get(i).getString("var3").equals("")){
						pd.put("REMARK",listPd.get(i).getString("var3"));
					}

					morgService.save(pd);

				}catch (Exception e){
					pd.put("ORG_ID", listPd.get(i).getString("var0"));
					morgService.restore(pd);
				}

			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("backend/morg/uploadexcel");
		return mv;
	}


	/**打开上传EXCEL页面(项目)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcelProject")
	public ModelAndView goUploadExcelProject()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("backend/morg/uploadexcelProject");
		return mv;
	}

	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "morg.xls", "morg.xls");
		/*System.out.println("检查"+PathUtil.getClasspath());*/
	}

	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcelProject")
	public ModelAndView downExcelProject(HttpServletResponse response)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出RectifyInfo到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("项目名称");	//2
		titles.add("项目简称");	//11
		titles.add("项目编码");	//12
		titles.add("项目简介");	//13
		titles.add("所属单位");	//9
		titles.add("项目所属区域");	//10
		dataMap.put("titles", titles);

		//放入单位
		PageData param = new PageData();
		param.put("ISORG",1);
		List<PageData> allUnitList = morgService.listAll(param);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllForIdSelectTree(hainanParam);

		dataMap.put("areaList", areaList);
		dataMap.put("allUnitList", allUnitList);

		ObjectExcelViewProject erv = new ObjectExcelViewProject();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**
	 * 查询重复编码
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/duplicate")
	@ResponseBody
	public PageData duplicate()throws  Exception{

		PageData result = new PageData();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData verify = morgService.duplicate(pd);

		System.out.println("测试0"+pd);
		System.out.println("测试0:"+verify);

		String sign = "false";

		if(verify != null&&(verify.getString("ORG_CODE").equals(pd.getString("ORG_CODE")))){
			System.out.println("测试1"+verify.getString("ORG_CODE"));
			System.out.println("测试2"+pd.getString("ORG_CODE"));
			if(verify.getString("ORG_ID").equals(pd.getString("ORG_ID"))){
				sign = "Same";
			}
			result.put("sign",sign);
		}
		else{
			sign = "true";
			result.put("sign",sign);
		}

		System.out.println("测试3"+sign);
		return result;
	}


	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

	/**根据角色权限处理权限状态(递归处理)
	 * @param orgList：传入的总菜单
	 * @param exitOrgids：被勾选的部分
	 * @return
	 */
	public List<MOrg> readOrg(List<MOrg> orgList, String exitOrgids){
		for(int i=0;i<orgList.size();i++){
			orgList.get(i).setHasMOrg(exitOrgids.indexOf(orgList.get(i).getORG_ID()) != -1);
			this.readOrg(orgList.get(i).getSubMOrg(), exitOrgids);					//是：继续排查其子菜单
		}
		return orgList;
	}

	/**根据地区选择获取被选中地区
	 * @param areaList：传入的总菜单
	 * @param PROJECT_AREA：被勾选的部分
	 * @return
	 */
	public List<Dictionaries> readArea(List<Dictionaries> areaList,String PROJECT_AREA){
		for(int i=0;i<areaList.size();i++){
			if(PROJECT_AREA.indexOf(areaList.get(i).getDICTIONARIES_ID()) != -1){
				areaList.get(i).setHasDict(true);
			}

//			areaList.get(i).setHasMOrg(PROJECT_AREA.indexOf(areaList.get(i).getDICTIONARIES_ID()) != -1);
			this.readArea(areaList.get(i).getSubDict(), PROJECT_AREA);					//是：继续排查其子菜单
		}
		return areaList;
	}
}
