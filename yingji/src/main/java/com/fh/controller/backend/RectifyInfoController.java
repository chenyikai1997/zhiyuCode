package com.fh.controller.backend;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.system.user.UserManager;
import com.fh.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.WebConstant;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.backend.morg.MOrgManager;
import com.fh.service.backend.rectifyinfo.RectifyInfoManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.fhlog.FHlogManager;

import net.sf.json.JSONArray;

/**
 * 说明：整改信息
 * 创建人：FH Q313596790
 * 创建时间：2023-06-21
 */
@Controller
@RequestMapping(value="/rectifyinfo")
public class RectifyInfoController extends BaseController {

	@Autowired
	private StringRedisTemplate redisTemplate;

	String menuUrl = "rectifyinfo/list.do"; //菜单地址(权限用)
	@Resource(name="rectifyinfoService")
	private RectifyInfoManager rectifyinfoService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="morgService")
	private MOrgManager morgService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="userService")
	private UserManager userService;

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"新增RectifyInfo");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String now = Tools.date2Str(new Date());
		String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");

		pd.put("RECTIFY_ID", this.get32UUID());	//主键
		pd.put("SORT", "0");	//顺序
		pd.put("ISDEL", "0");	//删除
		pd.put("CREATER", Jurisdiction.getUserId());	//创建人
		pd.put("CREATE_DATE", now);	//创建时间
		pd.put("COMMIT_TIME", now);	//发现时间

		// 默认未提交
		pd.put("IS_SUBMITE", WebConstant.IS_NO);

		//隐患流水号
		PageData tempParam = new PageData();
		tempParam.put("ORG_ID",pd.getString("ORG_ID"));
		tempParam = morgService.findById(tempParam);
		String rectifyNumber = getRectifyNumber(tempParam.getString("ORG_NAME_SHORT"));
		pd.put("RECTIFYINFO_NUMBER",rectifyNumber);

		if (WebConstant.IS_YES.equals(pd.getString("IS_COMPLETE"))) {
			pd.put("COMPLETE_TIME", nowDay);
		}

		// 默认未提交
		pd.put("IS_SUBMITE", WebConstant.IS_NO);

		rectifyinfoService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 用ajax提交数据保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveByAjax")
	@ResponseBody
	public PageData saveByAjax() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"saveByAjax新增RectifyInfo");

		PageData result = new PageData();

		String sign = "no";

		try {
			PageData pd = this.getPageData();

			String now = Tools.date2Str(new Date());
			String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");

			pd.put("RECTIFY_ID", this.get32UUID());	//主键
			pd.put("SORT", "0");	//顺序
			pd.put("ISDEL", "0");	//删除
			pd.put("CREATER", Jurisdiction.getUserId());	//创建人
			pd.put("CREATE_DATE", now);	//创建时间

			String COMMIT_TIME = nowDay;
			int month = Integer.parseInt(COMMIT_TIME.substring(5, 7));
			pd.put("COMMIT_TIME", COMMIT_TIME);
			pd.put("YEAR", COMMIT_TIME.substring(0, 4));
			pd.put("MONTH", COMMIT_TIME.substring(5, 7));
			pd.put("DAY", COMMIT_TIME.substring(8, 10));
			pd.put("QUARTER", (month + 2) / 3);

			if (WebConstant.IS_YES.equals(pd.getString("IS_COMPLETE"))) {
				pd.put("COMPLETE_TIME", nowDay);
			}

			// 默认未提交
			pd.put("IS_SUBMITE", WebConstant.IS_NO);
			pd.put("REPORTING_FILE", "");

			//隐患流水号
			PageData tempParam = new PageData();
			tempParam.put("ORG_ID",pd.getString("ORG_ID"));
			tempParam = morgService.findById(tempParam);
			String rectifyNumber = getRectifyNumber(tempParam.getString("ORG_NAME_SHORT"));
			pd.put("RECTIFYINFO_NUMBER",rectifyNumber);

			rectifyinfoService.save(pd);

			sign = "yes";
		} catch (Exception e) {
			// TODO: handle exception
			sign = "no";
			logger.error("saveByAjax新增RectifyInfo出错", e);
		}
		result.put("sign", sign);
		return result;
	}

	/**提交(提交后状态搞成rectify_stage = 2)
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/submit")
	public void submit(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"提交RectifyInfo");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData data = rectifyinfoService.findById(pd);

		String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");
		String COMMIT_TIME = nowDay;
		int month = Integer.parseInt(COMMIT_TIME.substring(5, 7));
		data.put("COMMIT_TIME", COMMIT_TIME);
		data.put("YEAR", COMMIT_TIME.substring(0, 4));
		data.put("MONTH", COMMIT_TIME.substring(5, 7));
		data.put("DAY", COMMIT_TIME.substring(8, 10));
		data.put("QUARTER", (month + 2) / 3);

		//未提交->待整改
		data.put("RECTIFY_STAGE", 2);

		data.put("MODIFYER", Jurisdiction.getUserId());	//修改人
		data.put("MODIFY_DATE", Tools.date2Str(new Date()));	//修改时间
		rectifyinfoService.edit(data);

		out.write("success");
		out.close();
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"删除RectifyInfo");
		/*if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限*/
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MODIFYER", Jurisdiction.getUserId());	//修改人
		pd.put("MODIFY_DATE", Tools.date2Str(new Date()));	//修改时间
		rectifyinfoService.delete(pd);
		out.write("success");
		out.close();
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"修改RectifyInfo");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MODIFYER", Jurisdiction.getUserId());	//修改人
		pd.put("MODIFY_DATE", Tools.date2Str(new Date()));	//修改时间

		String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");
		String IS_COMPLETE_OLD = pd.getString("IS_COMPLETE_OLD");
		String IS_COMPLETE = pd.getString("IS_COMPLETE");
		if (WebConstant.IS_NO.equals(IS_COMPLETE_OLD) && WebConstant.IS_YES.equals(IS_COMPLETE)) {
			pd.put("COMPLETE_TIME", nowDay);
		}

		rectifyinfoService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.addObject("msg", "list");

		getListData(page, pd, mv);

		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listSecondUnit")
	public ModelAndView listSecondUnit(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//控制对应公司在所有隐患中看不到属于自己的但是没有提交的隐患
		String userId = Jurisdiction.getUserId();
		//获取roleName
		User userRole = userService.getUserAndRoleById(userId);
		//如果是admin就跳转一级页面
		if(!userId.equals("1") && !userRole.getRole().getROLE_NAME().equals("应急部")){
			pd.put("ALL_SUBMIT_FLAG",1);
		}

		mv.addObject("msg", "listSecondUnit");

		getListData(page, pd, mv);

		mv.setViewName("backend/rectifyinfo/rectifyinfo_list_secondUnit");

		return mv;
	}


	/**
	 * 获取列表的数据
	 * @throws Exception
	 */
	public void getListData(Page page, PageData pd, ModelAndView mv) throws Exception{
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		//如果要查找逾期的
		String overTime = pd.getString("OVER_TIME");
		String NOW = null;
		if (StringUtils.isNotEmpty(overTime)) {
			pd.put("OVER_TIME", Tools.date2Str(new Date(), "yyyy-MM-dd"));
		}
		else if(StringUtils.isNotEmpty(NOW)){
			NOW = Tools.date2Str(new Date(), "yyyy-MM-dd");
			pd.put("NOW", NOW);
		}


		//不同阶段的状态
		String RECTIFY_STAGE = pd.getString("RECTIFY_STAGE");
		if (StringUtils.isNotEmpty(RECTIFY_STAGE)) {
			pd.put("RECTIFY_STAGE", RECTIFY_STAGE);
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		// 默认显示已提交的数据
//		if (StringUtils.isEmpty(pd.getString("IS_SUBMITE"))) {
//			pd.put("IS_SUBMITE", WebConstant.IS_YES);
//		}
		PageData param = new PageData();
		param.put("ISORG",1);

		page.setPd(pd);
		List<PageData>	varList = rectifyinfoService.list(page);	//列出RectifyInfo列表

		mv.setViewName("backend/rectifyinfo/rectifyinfo_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getDefaultMap());
		mv.addObject("orgList", morgService.listAll(param));
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(param));
	}

	/**列表，未提交
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list-notSubmite")
	public ModelAndView listNotSubmite(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo，未提交");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("RECTIFY_STAGE", "1"); // 未提交
		mv.addObject("msg", "list-notSubmite");

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		//如果要查找逾期的
		String overTime = pd.getString("OVER_TIME");
		if (StringUtils.isNotEmpty(overTime)) {
			String NOW = Tools.date2Str(new Date(), "yyyy-MM-dd");
			pd.put("NOW", NOW);
			pd.put("OVER_TIME", overTime);
		}


		//不同阶段的状态
		String RECTIFY_STAGE = pd.getString("RECTIFY_STAGE");
		if (StringUtils.isNotEmpty(RECTIFY_STAGE)) {
			pd.put("RECTIFY_STAGE", RECTIFY_STAGE);
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
			pd.put("SUBMIT_FLAG",1);
		}

		// 默认显示已提交的数据
//		if (StringUtils.isEmpty(pd.getString("IS_SUBMITE"))) {
//			pd.put("IS_SUBMITE", WebConstant.IS_YES);
//		}
		PageData param = new PageData();
		param.put("ISORG",1);


		page.setPd(pd);
		List<PageData>	varList = rectifyinfoService.list(page);	//列出RectifyInfo列表

		mv.setViewName("backend/rectifyinfo/rectifyinfo_noSubmit");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getDefaultMap());
		mv.addObject("orgList", morgService.listAll(param));
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(param));

		return mv;
	}

	/**列表，已逾期
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list-overtime")
	public ModelAndView listOvertime(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo，已逾期");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
			pd.put("SUBMIT_FLAG",1);
		}

		pd.put("OVER_TIME", "1"); // 已逾期
		pd.put("RECTIFY_STAGE", "2"); // 已逾期
		mv.addObject("msg", "list-overtime");

		getListData(page, pd, mv);
		mv.setViewName("backend/rectifyinfo/rectifyinfo_overtime");
		return mv;
	}

	/**列表，已完成
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list-isComplete")
	public ModelAndView listIsComplete(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo，已完成");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();



		pd.put("IS_COMPLETE", "1"); // 已完成
		mv.addObject("msg", "list-isComplete");

		getListData(page, pd, mv);
		return mv;
	}

	/**列表，已完成
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list-overtimeIsComplete")
	public ModelAndView overtimeIsComplete(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo，已完成");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("OVER_TIME", "1"); // 已逾期
		pd.put("RECTIFY_STAGE", "4");
		mv.addObject("msg", "list-overtimeIsComplete");

		pd.remove("QX");

		getListData(page, pd, mv);
		mv.setViewName("backend/rectifyinfo/rectifyinfo_overtimeCompleted");
		return mv;
	}

	/**列表，已完成
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list-notComplete")
	public ModelAndView listNotComplete(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo，未完成");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("IS_COMPLETE", "0"); // 未完成
		mv.addObject("msg", "list-notComplete");

		getListData(page, pd, mv);
		return mv;
	}

	/**列表，已完成
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list-overtimeNotComplete")
	public ModelAndView listOvertimeNotComplete(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo，未完成");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("OVER_TIME", "1"); // 已逾期
		pd.put("RECTIFY_STAGE", "3");
		mv.addObject("msg", "list-notComplete");

		getListData(page, pd, mv);
		mv.setViewName("backend/rectifyinfo/rectify_overtimeNoComplete");
		return mv;
	}

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddPage")
	public ModelAndView goAddPage()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		param.put("ISORG", "1");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
		JSONArray arr1 = JSONArray.fromObject(morgService.listSelectTree(param));
		mv.addObject("zTreeNodes", (null == arr1? "":arr1.toString()));

		//项目列表
		PageData projectParam = new PageData();
		projectParam.put("ISORG",0);
		List<PageData> projectList = morgService.listAll(projectParam);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllForIdSelectTree(hainanParam);
		JSONArray arr = JSONArray.fromObject(areaList);
		String zTreeNodes = (null == arr? "":arr.toString());
		zTreeNodes = zTreeNodes.replaceAll("NAME","name").replaceAll("DICTIONARIES_ID","id").replaceAll("PARENT_ID","parentId").replaceAll("subDict","nodes");
		mv.addObject("zTreeNodes", zTreeNodes);
		//填充子list
//		for(Dictionaries dictionaries : areaList){
//			String parentId = dictionaries.getDICTIONARIES_ID();
//			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
//			dictionaries.setSubDict(subList);
//		}

//		String json = arr.toString();
//		json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes");
//		mv.addObject("zTreeNodes", json);

		mv.setViewName("backend/rectifyinfo/rectifyinfo_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		mv.addObject("orgList", morgService.listAll(param));
		mv.addObject("projectList", projectList);
		mv.addObject("areaList", areaList);
		return mv;
	}

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
		JSONArray arr = JSONArray.fromObject(morgService.listSelectTree(param));
		mv.addObject("zTreeNodes", (null == arr? "":arr.toString()));

		if(pd.getString("RECTIFY_STAGE").equals("1")){
			mv.setViewName("backend/rectifyinfo/rectifyInfo_edit_snc");
		}
		else if(pd.getString("RECTIFY_STAGE").equals("2")){
			mv.setViewName("backend/rectifyinfo/rectifyInfo_edit_sc");
		}
		else{
			mv.setViewName("backend/rectifyinfo/rectifyinfo_edit");
		}

		PageData tempParam = new PageData();
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(tempParam));

		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		mv.addObject("orgList", morgService.listAll(param));		//组织机构列表
		return mv;
	}

	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData httpPd = new PageData();
		httpPd = this.getPageData();
		PageData pd = rectifyinfoService.findById(httpPd);	//根据ID读取

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
//		JSONArray arr = JSONArray.fromObject(morgService.listSelectTree(param));
//		mv.addObject("zTreeNodes", (null == arr? "":arr.toString()));

		if(httpPd.getString("RECTIFY_STAGE") != null){
			if(httpPd.getString("RECTIFY_STAGE").equals("2")){
				mv.setViewName("backend/rectifyinfo/rectifyInfo_edit_snc");
			}
			else if(httpPd.getString("RECTIFY_STAGE").equals("3")){
				mv.setViewName("backend/rectifyinfo/rectifyInfo_edit_sc");
			}
			else{
				mv.setViewName("backend/rectifyinfo/rectifyinfo_edit");
			}
		}
		else{
			mv.setViewName("backend/rectifyinfo/rectifyinfo_edit");
		}

		//获取全部
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllForIdSelectTree(hainanParam);
		JSONArray arr = JSONArray.fromObject(areaList);
		String zTreeNodes = (null == arr? "":arr.toString());
		zTreeNodes = zTreeNodes.replaceAll("NAME","name").replaceAll("DICTIONARIES_ID","id").replaceAll("PARENT_ID","parentId").replaceAll("subDict","nodes");
		mv.addObject("zTreeNodes", zTreeNodes);

		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		PageData tempParam = new PageData();
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(param));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		param.put("ISORG",1);
		mv.addObject("orgList", morgService.listAll(param));		//组织机构列表
		mv.addObject("areaList", areaList);
		return mv;
	}

	/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditSecondUnit")
	public ModelAndView goEditSecondUnit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData httpPd = new PageData();
		httpPd = this.getPageData();
		PageData pd = rectifyinfoService.findById(httpPd);	//根据ID读取

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
		JSONArray arr = JSONArray.fromObject(morgService.listSelectTree(param));
		mv.addObject("zTreeNodes", (null == arr? "":arr.toString()));

		//如果是待整改
		if((Integer) pd.get("RECTIFY_STAGE") == 2){
			mv.setViewName("backend/rectifyinfo/rectifyInfo_edit_snc");
		}
		//如果是待完成
		else if ((Integer) pd.get("RECTIFY_STAGE") == 3){
			mv.setViewName("backend/rectifyinfo/rectifyInfo_edit_sc");
		}
		else{
			mv.setViewName("backend/rectifyinfo/rectifyinfo_edit_secondUnit");
		}

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);

		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		PageData tempParam = new PageData();
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(tempParam));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		param.put("ISORG",1);
		mv.addObject("orgList", morgService.listAll(param));		//组织机构列表
		mv.addObject("areaList", areaList);
		return mv;
	}

	/**待提交，去隐患排查的修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditNoSubmit")
	public ModelAndView goEditNoSubmit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		param.put("ISORG", "1");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
//		JSONArray arr = JSONArray.fromObject(morgService.listSelectTree(param));
//		mv.addObject("zTreeNodes", (null == arr? "":arr.toString()));

		//项目列表
		PageData projectParam = new PageData();
		projectParam.put("ISORG",0);
		List<PageData> projectList = morgService.listAll(projectParam);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllForIdSelectTree(hainanParam);
		JSONArray arr = JSONArray.fromObject(areaList);
		String zTreeNodes = (null == arr? "":arr.toString());
		zTreeNodes = zTreeNodes.replaceAll("NAME","name").replaceAll("DICTIONARIES_ID","id").replaceAll("PARENT_ID","parentId").replaceAll("subDict","nodes");
		mv.addObject("zTreeNodes", zTreeNodes);

//		String json = arr.toString();
//		json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes");
//		mv.addObject("zTreeNodes", json);

		mv.setViewName("backend/rectifyinfo/rectifyinfo_noSubmit_edit");
		mv.addObject("msg", "edit");
		pd = rectifyinfoService.findById(pd);
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		mv.addObject("orgList", morgService.listAll(param));
		mv.addObject("projectList", projectList);
		mv.addObject("areaList", areaList);
		return mv;
	}

	/**逾期未整改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditOvertime")
	public ModelAndView goEditOvertime()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		param.put("ISORG", "1");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
		JSONArray arr = JSONArray.fromObject(morgService.listSelectTree(param));
		mv.addObject("zTreeNodes", (null == arr? "":arr.toString()));

		//项目列表
		PageData projectParam = new PageData();
		projectParam.put("ISORG",0);
		List<PageData> projectList = morgService.listAll(projectParam);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);

//		String json = arr.toString();
//		json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes");
//		mv.addObject("zTreeNodes", json);

		mv.setViewName("backend/rectifyinfo/rectifyinfo_overtime_edit");
		mv.addObject("msg", "edit");
		pd = rectifyinfoService.findById(pd);
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		mv.addObject("orgList", morgService.listAll(param));
		mv.addObject("projectList", projectList);
		mv.addObject("areaList", areaList);
		return mv;
	}

	/**逾期未完成
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditOvertimeNoComplete")
	public ModelAndView goEditOvertimeNoComplete()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取部门树
		PageData param = new PageData();
		param.put("PARENT_ID", "0");
		param.put("ISORG", "1");
		String USER_ID = Jurisdiction.getUserId();
		if (!"1".equals(USER_ID)) {
			param.put("USER_ID", USER_ID);
		}
		JSONArray arr = JSONArray.fromObject(morgService.listSelectTree(param));
		mv.addObject("zTreeNodes", (null == arr? "":arr.toString()));

		//项目列表
		PageData projectParam = new PageData();
		projectParam.put("ISORG",0);
		List<PageData> projectList = morgService.listAll(projectParam);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);

//		String json = arr.toString();
//		json = json.replaceAll("ORG_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("ORG_NAME", "name").replaceAll("subMOrg", "nodes");
//		mv.addObject("zTreeNodes", json);

		mv.setViewName("backend/rectifyinfo/rectifyinfo_overtimeNoComplete_edit");
		mv.addObject("msg", "edit");
		pd = rectifyinfoService.findById(pd);
		mv.addObject("pd", pd);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		mv.addObject("orgList", morgService.listAll(param));
		mv.addObject("projectList", projectList);
		mv.addObject("areaList", areaList);
		return mv;
	}

	/**去完成页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goComplete")
	public ModelAndView goComplete()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = rectifyinfoService.findById(pd);	//根据ID读取

		mv.setViewName("backend/rectifyinfo/rectifyinfo_complete");
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getNoYesMap());
		mv.addObject("msg", "complete");
		mv.addObject("pd", pd);
		return mv;
	}

	/**完成
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/complete")
	public ModelAndView complete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"complete完成RectifyInfo");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		PageData data = rectifyinfoService.findById(pd);

		pd.put("MODIFYER", Jurisdiction.getUserId());	//修改人
		pd.put("MODIFY_DATE", Tools.date2Str(new Date()));	//修改时间

		String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");
		String IS_COMPLETE = WebConstant.IS_YES;
		pd.put("IS_COMPLETE", IS_COMPLETE);
		pd.put("COMPLETE_TIME", nowDay);

		data.putAll(pd);

		rectifyinfoService.edit(data);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}


	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除RectifyInfo");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("array", ArrayDATA_IDS);
			pd.put("MODIFYER", Jurisdiction.getUserId());	//修改人
			pd.put("MODIFY_DATE", Tools.date2Str(new Date()));	//修改时间
			rectifyinfoService.deleteAll(pd);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/submitAll")
	@ResponseBody
	public Object submitAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除RectifyInfo");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("array", ArrayDATA_IDS);
			pd.put("MODIFYER", Jurisdiction.getUserId());	//修改人
			pd.put("MODIFY_DATE", Tools.date2Str(new Date()));	//修改时间
			rectifyinfoService.submitAll(pd);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 上传文件
	 */
	@RequestMapping(value="/uploadFile")
	@ResponseBody
	public PageData uploadFile(
			@RequestParam(value="id-input-file-3",required=false) MultipartFile fileArr[],
			@RequestParam(value="IMG_URL",required=false) String IMG_URL
		) throws Exception{
		logger.info("开始上传文件。。。");
		PageData pd = new PageData();

		String sign = "no";
		String ffile = DateUtil.getDays(), fileName = "";
		for (MultipartFile file : fileArr) {
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;	//文件上传路径
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());			//执行上传
				if (StringUtils.isEmpty(IMG_URL)) {
					IMG_URL = Const.FILEPATHIMG + ffile + "/" + fileName;
				} else {
					IMG_URL += "," + Const.FILEPATHIMG + ffile + "/" + fileName;
				}
				sign = "yes";

				FileUtil.calculateRotation(filePath+"/"+fileName);

			}
		}



		pd.put("FILE_PATH",IMG_URL);	//路径
		pd.put("sign", sign);
		return pd;
	}

	/**
	 * 上传整改文件
	 */
	@RequestMapping(value="/uploadFileRreporting")
	@ResponseBody
	public PageData uploadFileRreporting(
			@RequestParam(value="id-input-file-3",required=false) MultipartFile fileArr[],
			@RequestParam(value="REPORTING_FILE",required=false) String REPORTING_FILE
	) throws Exception{
		logger.info("开始上传文件。。。");
		PageData pd = new PageData();

		String sign = "no";
		String ffile = DateUtil.getDays(), fileName = "";
		for (MultipartFile file : fileArr) {
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;	//文件上传路径
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());			//执行上传
				if (StringUtils.isEmpty(REPORTING_FILE)) {
					REPORTING_FILE = Const.FILEPATHIMG + ffile + "/" + fileName;
				} else {
					REPORTING_FILE += "," + Const.FILEPATHIMG + ffile + "/" + fileName;
				}
				sign = "yes";
			}
		}

		pd.put("FILE_PATH",REPORTING_FILE);	//路径
		pd.put("sign", sign);
		return pd;
	}

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"导出RectifyInfo到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("安全隐患编码");	//2
		titles.add("组织结构");	//2
		titles.add("文件编号");	//9
		titles.add("项目名称");	//2
		titles.add("隐患情况");	//10
		titles.add("隐患类别");	//12
		titles.add("隐患级别");	//13
		titles.add("隐患因素");	//14
		titles.add("整改期限");	//16
		titles.add("所属区域");	//17
		titles.add("填报人");	//17
		titles.add("填报单位");	//18
		titles.add("上传图片");	//18
		titles.add("整改措施");	//17
		titles.add("责任人");	//18
		titles.add("责任单位");	//18
		dataMap.put("titles", titles);

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		pd.put("NOW",Tools.date2Str(new Date(),"yyyy-MM-dd"));

		//机构list
		PageData param = new PageData();
		List<PageData> orgList = morgService.listAll(param);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
		//填充子list
//		for(Dictionaries dictionaries : areaList){
//			String parentId = dictionaries.getDICTIONARIES_ID();
//			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
//			dictionaries.setSubDict(subList);
//		}

		pd.put("RECTIFY_STAGE",2);

		if(pd.getString("OVER_TIME") != null &&!pd.getString("OVER_TIME").equals("")){
			pd.remove("NOW");
			pd.put("OVER_TIME",Tools.date2Str(new Date(),"yyyy-MM-dd"));
		}

		List<PageData> varOList = rectifyinfoService.listAll(pd);

		//如果pd带有selectId就只下载被选中的部分
		String selectId = pd.getString("selectId");
		if(selectId != null && !selectId.equals("")){
			List<PageData> tempList = new LinkedList<>();
			String[] strArrays = selectId.split(",");
			for(String str : strArrays){
				PageData temp = new PageData();
				temp.put("RECTIFY_ID",str);
				temp = rectifyinfoService.findById(temp);
				tempList.add(temp);
			}
			//替换var0List
			varOList = tempList;
		}

		List<PageData> varList = new ArrayList<PageData>();

		Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true);
		Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true);
		Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true);
		Map<String, String> YesNoMap = WebConstant.getDefaultMap();

		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("RECTIFYINFO_NUMBER"));	    //2
			vpd.put("var2", varOList.get(i).getString("ORG_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("FILE_CODE"));	    //9

			String var4 = varOList.get(i).getString("PROJECT_NAME");
			for(PageData temp : orgList){
				if(var4 != null && !var4.equals("") && var4.equals(temp.getString("ORG_ID"))){
					vpd.put("var4", temp.getString("ORG_NAME"));	    //2
				}
			}
			vpd.put("var5", varOList.get(i).getString("HIDDEN_DANGER_INFO"));	    //10

			String var6 = varOList.get(i).getString("HIDDEN_DANGER_CLASSIFY");
			vpd.put("var6", classifyMap.get(var6));	    //12
			String var7 = varOList.get(i).getString("HIDDEN_DANGER_LEVEL");
			vpd.put("var7", levelMap.get(var7));	    //13
			String var8 = varOList.get(i).getString("HIDDEN_DANGER_FACTOR");
			vpd.put("var8", factorMap.get(var8));	    //14

			vpd.put("var9", varOList.get(i).getString("PLAN_COMPLETE_TIME"));	    //16

			String var10 = varOList.get(i).getString("RECTIFYINFO_AREA");
			for(Dictionaries dicts : areaList){
				if(dicts.getDICTIONARIES_ID().equals(var10)){
					vpd.put("var10", dicts.getNAME());
				}
				for(Dictionaries subDicts : dicts.getSubDict()){
					if(subDicts.getDICTIONARIES_ID().equals(var10)){
						vpd.put("var10", subDicts.getNAME());
					}
					for(Dictionaries subSubDicts : subDicts.getSubDict()){
						if(subSubDicts.getDICTIONARIES_ID().equals(var10)){
							vpd.put("var10", subSubDicts.getNAME());
						}
					}
				}
			}

			vpd.put("var11", varOList.get(i).getString("REPORTING_PERSON_FIRST"));	    //17
			String var12 = varOList.get(i).getString("REPORTING_UNIT_FIRST");
			for(PageData temp : orgList){
				if(var12 != null && !var12.equals("") && var12.equals(temp.getString("ORG_ID"))){
					vpd.put("var12", temp.getString("ORG_NAME"));	    //2
				}
			}
			vpd.put("var13", varOList.get(i).getString("IMG_URL"));	    //17

			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**导出到excel(未完成)
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excelSNC")
	public ModelAndView exportExcelSNC() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"导出RectifyInfo到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("安全隐患编码");	//2
		titles.add("组织结构");	//2
		titles.add("文件编号");	//9
		titles.add("项目名称");	//2
		titles.add("隐患情况");	//10
		titles.add("隐患类别");	//12
		titles.add("隐患级别");	//13
		titles.add("隐患因素");	//14
		titles.add("整改期限");	//16
		titles.add("所属区域");	//17
		titles.add("填报人");	//17
		titles.add("填报单位");	//18
		titles.add("上传图片");	//18
		titles.add("整改措施");	//17
		titles.add("责任人");	//18
		titles.add("责任单位");	//18
		titles.add("完成时间");	//18
		titles.add("整改投入(元)");	//17
		titles.add("填报单位");	//18
		titles.add("填报人");	//18
		titles.add("附件");	//18
		dataMap.put("titles", titles);

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		pd.put("NOW",Tools.date2Str(new Date(),"yyyy-MM-dd"));

		pd.put("RECTIFY_STAGE",3);

		//TODO 隐患权限控制

		//机构list
		PageData param = new PageData();
		List<PageData> orgList = morgService.listAll(param);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
		//填充子list
//		for(Dictionaries dictionaries : areaList){
//			String parentId = dictionaries.getDICTIONARIES_ID();
//			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
//			dictionaries.setSubDict(subList);
//		}

		if(pd.getString("OVER_TIME") != null &&!pd.getString("OVER_TIME").equals("")){
			pd.remove("NOW");
			pd.put("OVER_TIME",Tools.date2Str(new Date(),"yyyy-MM-dd"));
		}

		List<PageData> varOList = rectifyinfoService.listAll(pd);

		//如果pd带有selectId就只下载被选中的部分
		String selectId = pd.getString("selectId");
		if(selectId != null && !selectId.equals("")){
			List<PageData> tempList = new LinkedList<>();
			String[] strArrays = selectId.split(",");
			for(String str : strArrays){
				PageData temp = new PageData();
				temp.put("RECTIFY_ID",str);
				temp = rectifyinfoService.findById(temp);
				tempList.add(temp);
			}
			//替换var0List
			varOList = tempList;
		}

		List<PageData> varList = new ArrayList<PageData>();

		Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true);
		Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true);
		Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true);
		Map<String, String> YesNoMap = WebConstant.getDefaultMap();

		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("RECTIFYINFO_NUMBER"));	    //2
			vpd.put("var2", varOList.get(i).getString("ORG_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("FILE_CODE"));	    //9

			String var4 = varOList.get(i).getString("PROJECT_NAME");
			for(PageData temp : orgList){
				if(var4 != null && !var4.equals("") && var4.equals(temp.getString("ORG_ID"))){
					vpd.put("var4", temp.getString("ORG_NAME"));	    //2
				}
			}
			vpd.put("var5", varOList.get(i).getString("HIDDEN_DANGER_INFO"));	    //10

			String var6 = varOList.get(i).getString("HIDDEN_DANGER_CLASSIFY");
			vpd.put("var6", classifyMap.get(var6));	    //12
			String var7 = varOList.get(i).getString("HIDDEN_DANGER_LEVEL");
			vpd.put("var7", levelMap.get(var7));	    //13
			String var8 = varOList.get(i).getString("HIDDEN_DANGER_FACTOR");
			vpd.put("var8", factorMap.get(var8));	    //14

			vpd.put("var9", varOList.get(i).getString("PLAN_COMPLETE_TIME"));	    //16

			String var10 = varOList.get(i).getString("RECTIFYINFO_AREA");
			for(Dictionaries dicts : areaList){
				if(dicts.getDICTIONARIES_ID().equals(var10)){
					vpd.put("var10", dicts.getNAME());
				}
				for(Dictionaries subDicts : dicts.getSubDict()){
					if(subDicts.getDICTIONARIES_ID().equals(var10)){
						vpd.put("var10", subDicts.getNAME());
					}
					for(Dictionaries subSubDicts : subDicts.getSubDict()){
						if(subSubDicts.getDICTIONARIES_ID().equals(var10)){
							vpd.put("var10", subSubDicts.getNAME());
						}
					}
				}
			}

			vpd.put("var11", varOList.get(i).getString("REPORTING_PERSON_FIRST"));	    //17

			String var12 = varOList.get(i).getString("REPORTING_UNIT_FIRST");
			for(PageData temp : orgList){
				if(var12 != null && !var12.equals("") && var12.equals(temp.getString("ORG_ID"))){
					vpd.put("var12", temp.getString("ORG_NAME"));	    //2
				}
			}

			vpd.put("var13", varOList.get(i).getString("IMG_URL"));	    //17

			vpd.put("var14", varOList.get(i).getString("RECTIFY_MEASURES"));	    //17

			vpd.put("var15", varOList.get(i).getString("PERSON_RESPONSIBLE"));	    //17

			String var16 = varOList.get(i).getString("RESPONSIBLE_UNIT");
			for(PageData temp : orgList){
				if(var16 != null && !var16.equals("") && var16.equals(temp.getString("ORG_ID"))){
					vpd.put("var16", temp.getString("ORG_NAME"));	    //2
				}
			}

			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**导出到excel(已完成)
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excelSC")
	public ModelAndView exportExcelSC() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(), "yyyy-MM-dd")+"导出RectifyInfo到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("安全隐患编码");	//2
		titles.add("组织结构");	//2
		titles.add("文件编号");	//9
		titles.add("项目名称");	//2
		titles.add("隐患情况");	//10
		titles.add("隐患类别");	//12
		titles.add("隐患级别");	//13
		titles.add("隐患因素");	//14
		titles.add("整改期限");	//16
		titles.add("所属区域");	//17
		titles.add("填报人");	//17
		titles.add("填报单位");	//18
		titles.add("上传图片");	//18
		titles.add("整改措施");	//17
		titles.add("责任人");	//18
		titles.add("责任单位");	//18
		titles.add("完成时间");	//18
		titles.add("整改投入(元)");	//17
		titles.add("填报单位");	//18
		titles.add("填报人");	//18
		titles.add("附件");	//18
		dataMap.put("titles", titles);

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		pd.put("NOW",Tools.date2Str(new Date(),"yyyy-MM-dd"));

		if(pd.getString("tag").equals("sc")){
			pd.put("RECTIFY_STAGE",4);
		}


		if(pd.getString("OVER_TIME") != null &&!pd.getString("OVER_TIME").equals("")){
			pd.remove("NOW");
			pd.put("OVER_TIME",Tools.date2Str(new Date(),"yyyy-MM-dd"));
		}

		//机构list
		PageData param = new PageData();
		List<PageData> orgList = morgService.listAll(param);

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
		//填充子list
//		for(Dictionaries dictionaries : areaList){
//			String parentId = dictionaries.getDICTIONARIES_ID();
//			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
//			dictionaries.setSubDict(subList);
//		}

		List<PageData> varOList = rectifyinfoService.listAll(pd);

		//如果pd带有selectId就只下载被选中的部分
		String selectId = pd.getString("selectId");
		if(selectId != null && !selectId.equals("")){
			List<PageData> tempList = new LinkedList<>();
			String[] strArrays = selectId.split(",");
			for(String str : strArrays){
				PageData temp = new PageData();
				temp.put("RECTIFY_ID",str);
				temp = rectifyinfoService.findById(temp);
				tempList.add(temp);
			}
			//替换var0List
			varOList = tempList;
		}

		List<PageData> varList = new ArrayList<PageData>();

		Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true);
		Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true);
		Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true);
		Map<String, String> YesNoMap = WebConstant.getDefaultMap();

		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("RECTIFYINFO_NUMBER"));	    //2
			vpd.put("var2", varOList.get(i).getString("ORG_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("FILE_CODE"));	    //9

			String var4 = varOList.get(i).getString("PROJECT_NAME");
			for(PageData temp : orgList){
				if(var4 != null && !var4.equals("") && var4.equals(temp.getString("ORG_ID"))){
					vpd.put("var4", temp.getString("ORG_NAME"));	    //2
				}
			}
			vpd.put("var5", varOList.get(i).getString("HIDDEN_DANGER_INFO"));	    //10

			String var6 = varOList.get(i).getString("HIDDEN_DANGER_CLASSIFY");
			vpd.put("var6", classifyMap.get(var6));	    //12
			String var7 = varOList.get(i).getString("HIDDEN_DANGER_LEVEL");
			vpd.put("var7", levelMap.get(var7));	    //13
			String var8 = varOList.get(i).getString("HIDDEN_DANGER_FACTOR");
			vpd.put("var8", factorMap.get(var8));	    //14

			vpd.put("var9", varOList.get(i).getString("PLAN_COMPLETE_TIME"));	    //16

			String var10 = varOList.get(i).getString("RECTIFYINFO_AREA");
			for(Dictionaries dicts : areaList){
				if(dicts.getDICTIONARIES_ID().equals(var10)){
					vpd.put("var10", dicts.getNAME());
				}
				for(Dictionaries subDicts : dicts.getSubDict()){
					if(subDicts.getDICTIONARIES_ID().equals(var10)){
						vpd.put("var10", subDicts.getNAME());
					}
					for(Dictionaries subSubDicts : subDicts.getSubDict()){
						if(subSubDicts.getDICTIONARIES_ID().equals(var10)){
							vpd.put("var10", subSubDicts.getNAME());
						}
					}
				}
			}

			vpd.put("var11", varOList.get(i).getString("REPORTING_PERSON_FIRST"));	    //17
			String var12 = varOList.get(i).getString("REPORTING_UNIT_FIRST");
			for(PageData temp : orgList){
				if(var12 != null && !var12.equals("") && var12.equals(temp.getString("ORG_ID"))){
					vpd.put("var12", temp.getString("ORG_NAME"));	    //2
				}
			}

			vpd.put("var13", varOList.get(i).getString("IMG_URL"));	    //17

			vpd.put("var14", varOList.get(i).getString("RECTIFY_MEASURES"));	    //17

			vpd.put("var15", varOList.get(i).getString("PERSON_RESPONSIBLE"));	    //17

			String var16 = varOList.get(i).getString("RESPONSIBLE_UNIT");
			for(PageData temp : orgList){
				if(var16 != null && !var16.equals("") && var16.equals(temp.getString("ORG_ID"))){
					vpd.put("var16", temp.getString("ORG_NAME"));	    //2
				}
			}

			vpd.put("var17", varOList.get(i).getString("COMPLETE_TIME"));	    //完成时间

			vpd.put("var18", varOList.get(i).getString("RECTIFY_INVESTMENT"));	    //整改投入

			//填报单位
			String var19 = varOList.get(i).getString("REPORTING_UNIT");
			for(PageData temp : orgList){
				if(var19 != null && !var19.equals("") && var19.equals(temp.getString("ORG_ID"))){
					vpd.put("var19", temp.getString("ORG_NAME"));	    //2
				}
			}

			vpd.put("var20", varOList.get(i).getString("REPORTING_PERSON"));	    //填报人

			vpd.put("var21", varOList.get(i).getString("REPORTING_FILE"));	    //17


			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("backend/rectifyinfo/uploadexcel");
		return mv;
	}

	/**打开上传EXCEL页面(未整改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcelSNC")
	public ModelAndView goUploadExcelSNC()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("backend/rectifyinfo/uploadexcelSNC");
		return mv;
	}

	/**打开上传EXCEL页面(未完成)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcelSC")
	public ModelAndView goUploadExcelSC()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("backend/rectifyinfo/uploadexcelSC");
		return mv;
	}

	/**打开上传EXCEL页面(历史数据)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcelHis")
	public ModelAndView goUploadExcelHis()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("backend/rectifyinfo/uploadexcelHis");
		return mv;
	}

	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Rectifyinfo.xls", "Rectifyinfo.xls");
	}

	/**下载模版(提供下拉框)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcelTest")
	public ModelAndView downExcelTest(HttpServletResponse response)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出RectifyInfo到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("组织结构");	//2
		titles.add("文件编号");	//9
		titles.add("项目名称");	//2
		titles.add("隐患情况");	//10
		titles.add("隐患类别");	//12
		titles.add("隐患级别");	//13
		titles.add("隐患因素");	//14
		titles.add("整改期限");	//16
		titles.add("所属区域");	//17
		titles.add("填报人");	//17
		titles.add("填报单位");	//18
		titles.add("上传图片");	//18
		dataMap.put("titles", titles);

		//放入对应的二级单位和三级机构或项目
		PageData param = new PageData();
		param.put("ISORG",1);
		List<PageData> allUnitList = morgService.listAll(param);
		param.put("ORG_LEVEL",2);
		List<PageData> secondUnitList = morgService.listAll(param);

		param.put("ORG_LEVEL",3);
		List<PageData> thirdUnitList = morgService.listAll(param);

		param.put("ISORG",0);
		param.put("ORG_LEVEL","");
		List<PageData> projectList = morgService.listAll(param);

		dataMap.put("secondUnitList", secondUnitList);
		dataMap.put("thirdUnitList", thirdUnitList);
		dataMap.put("projectList", projectList);


		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
		//填充子list
//		for(Dictionaries dictionaries : areaList){
//			String parentId = dictionaries.getDICTIONARIES_ID();
//			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
//			dictionaries.setSubDict(subList);
//		}

		dataMap.put("areaList", areaList);

		Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true);
		Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true);
		Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true);

		dataMap.put("classifyMap", classifyMap);
		dataMap.put("levelMap", levelMap);
		dataMap.put("factorMap", factorMap);
		dataMap.put("allUnitList", allUnitList);

		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**待整改提交模板
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcelSNC")
	public ModelAndView downExcelSNC(HttpServletResponse response)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出RectifyInfo到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("组织结构");	//2
		titles.add("文件编号");	//9
		titles.add("项目名称");	//2
		titles.add("隐患情况");	//10
		titles.add("隐患类别");	//12
		titles.add("隐患级别");	//13
		titles.add("隐患因素");	//14
		titles.add("整改期限");	//16
		titles.add("所属区域");	//17
		titles.add("填报人");	//17
		titles.add("填报单位");	//18
		titles.add("上传图片");	//18
		titles.add("整改措施");	//18
		titles.add("责任人");	//18
		titles.add("责任单位");	//18
		dataMap.put("titles", titles);

		//放入对应的二级单位和三级机构或项目
		PageData param = new PageData();
		param.put("ISORG",1);
		List<PageData> allUnitList = morgService.listAll(param);
		param.put("ORG_LEVEL",2);
		List<PageData> secondUnitList = morgService.listAll(param);

		param.put("ORG_LEVEL",3);
		List<PageData> thirdUnitList = morgService.listAll(param);

		param.put("ISORG",0);
		param.put("ORG_LEVEL","");
		List<PageData> projectList = morgService.listAll(param);

		dataMap.put("secondUnitList", secondUnitList);
		dataMap.put("thirdUnitList", thirdUnitList);
		dataMap.put("projectList", projectList);


		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);

		dataMap.put("areaList", areaList);

		Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true);
		Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true);
		Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true);

		dataMap.put("classifyMap", classifyMap);
		dataMap.put("levelMap", levelMap);
		dataMap.put("factorMap", factorMap);
		dataMap.put("allUnitList", allUnitList);

		ObjectExcelViewSNC erv = new ObjectExcelViewSNC();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**待完成提交模板
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcelSC")
	public ModelAndView downExcelSC(HttpServletResponse response)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出RectifyInfo到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("组织结构");	//2
		titles.add("文件编号");	//9
		titles.add("项目名称");	//2
		titles.add("隐患情况");	//10
		titles.add("隐患类别");	//12
		titles.add("隐患级别");	//13
		titles.add("隐患因素");	//14
		titles.add("整改期限");	//16
		titles.add("所属区域");	//17
		titles.add("第一填报人");	//17
		titles.add("第一填报单位");	//18
		titles.add("上传图片");	//18
		titles.add("整改措施");	//18
		titles.add("责任人");	//18
		titles.add("责任单位");	//18
		titles.add("完成时间");	//18
		titles.add("整改投入(元)");	//18
		titles.add("填报单位");	//18
		titles.add("填报人");	//18
		titles.add("附件");	//18
		dataMap.put("titles", titles);

		//放入对应的二级单位和三级机构或项目
		PageData param = new PageData();
		param.put("ISORG",1);
		List<PageData> allUnitList = morgService.listAll(param);
		param.put("ORG_LEVEL",2);
		List<PageData> secondUnitList = morgService.listAll(param);

		param.put("ORG_LEVEL",3);
		List<PageData> thirdUnitList = morgService.listAll(param);

		param.put("ISORG",0);
		param.put("ORG_LEVEL","");
		List<PageData> projectList = morgService.listAll(param);

		dataMap.put("secondUnitList", secondUnitList);
		dataMap.put("thirdUnitList", thirdUnitList);
		dataMap.put("projectList", projectList);


		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		//获取海南地区
		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);

		dataMap.put("areaList", areaList);

		Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true);
		Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true);
		Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true);

		dataMap.put("classifyMap", classifyMap);
		dataMap.put("levelMap", levelMap);
		dataMap.put("factorMap", factorMap);
		dataMap.put("allUnitList", allUnitList);

		ObjectExcelViewSC erv = new ObjectExcelViewSC();
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
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入整改信息到数据库");
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(),"yyyy-MM-dd")+"从EXCEL导入整改信息到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "rectifyinfoexcel"+ Tools.date2Str(new Date(),"yyyyMMddHHmmss"));							//执行上传
			List<PageData> listPd = (List)objectExcelReadPic.readExcel(filePath, fileName, 0, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			// 准备用到的Map
			Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, false);
			Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, false);
			Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, false);

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

			//获取海南地区
			String hainanParam = "1";
			List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
			PageData areaMap = new PageData();
			for (Dictionaries area : areaList) {
				if(area.getSubDict() != null){
					for(Dictionaries subArea : area.getSubDict()){
						areaMap.put(subArea.getNAME(), subArea.getDICTIONARIES_ID());
						if(subArea.getSubDict() != null){
							for(Dictionaries subSubArea : subArea.getSubDict()){
								areaMap.put(subSubArea.getNAME(), subSubArea.getDICTIONARIES_ID());
							}
						}
					}
				}
				areaMap.put(area.getNAME(), area.getDICTIONARIES_ID());
			}
			//新增提示警告，每次查询的结果如果是null就写入字符串，最后alert出来
			String allAlertText = "";
			int successNum = 0;
			int fallNum = 0;
			List<PageData> addList = new ArrayList<PageData>();
			for(int i=0;i<listPd.size();i++){
				String alertText = "";
				// 默认的字段
				String now = Tools.date2Str(new Date());
				String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");

				PageData data = new PageData();

				data.put("RECTIFY_ID", this.get32UUID());	//主键
				data.put("SORT", "0");	//顺序
				data.put("ISDEL", "0");	//删除
				data.put("CREATER", Jurisdiction.getUserId());	//创建人
				data.put("CREATE_DATE", nowDay);	//创建时间

				if (WebConstant.IS_YES.equals(data.getString("IS_COMPLETE"))) {
					data.put("COMPLETE_TIME", nowDay);
				}

				// 导入的字段,如果有隐患id，就全部往后挪，根据listPd每个pd中的edit判断
				// 组织机构
				String var1 = listPd.get(i).getString("var1");
				if(var1 != null && !var1.equals("")){
					data.put("ORG_ID", orgMap.get(var1));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第B列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第B列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第A列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第A列第" + (i+3) + "行数据不匹配，请修改";
						}
					}

				}
				// 文件编号
				String var2 = listPd.get(i).getString("var2");
				data.put("FILE_CODE", var2);


				// 项目名称
				String var3 = listPd.get(i).getString("var3");
				if(var3 != null && !var3.equals("")){
					data.put("PROJECT_NAME", projectMap.get(var3));
				}
//				else{
//					if(listPd.get(i).getString("edit") != null){
//						if(alertText.equals("")){
//							alertText += "第D列第" + (i+3) + "行数据不匹配，请修改";
//						}
//						else{
//							alertText += "\n第D列第" + (i+3) + "行数据不匹配，请修改";
//						}
//					}
//					else{
//						if(alertText.equals("")){
//							alertText += "第C列第" + (i+3) + "行数据不匹配，请修改";
//						}
//						else{
//							alertText += "\n第C列第" + (i+3) + "行数据不匹配，请修改";
//						}
//					}
//
//				}
				// 隐患情况
				data.put("HIDDEN_DANGER_INFO", listPd.get(i).getString("var4"));
				// 隐患类别
				String var5 = listPd.get(i).getString("var5");
				if(var5 != null && !var5.equals("")){
					data.put("HIDDEN_DANGER_CLASSIFY", classifyMap.get(listPd.get(i).getString("var5")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第E列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第E列第" + (i+3) + "行数据不匹配，请修改";
						}
					}

				}
				// 隐患级别
				String var6 = listPd.get(i).getString("var6");
				if(var6 != null && !var6.equals("")){
					data.put("HIDDEN_DANGER_LEVEL", levelMap.get(listPd.get(i).getString("var6")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
						}
					}

				}
				// 隐患因素
				String var7 = listPd.get(i).getString("var7");
				if(var7 != null && !var7.equals("")){
					data.put("HIDDEN_DANGER_FACTOR", factorMap.get(listPd.get(i).getString("var7")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第H列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
						}
					}

				}
				//整改期限
				String var8 = listPd.get(i).getString("var8");
				if (StringUtils.isNotEmpty(var8)) {
					String PLAN_COMPLETE_TIME = ""; // 默认为空
					PLAN_COMPLETE_TIME = makeTimeForm(var8);
					data.put("PLAN_COMPLETE_TIME", PLAN_COMPLETE_TIME);
				}
				// 所属区域
				String var9 = listPd.get(i).getString("var9");
				if(var9 != null && !var9.equals("")){
					data.put("RECTIFYINFO_AREA", areaMap.get(listPd.get(i).getString("var9")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第J列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第J列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第I列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第I列第" + (i+3) + "行数据不匹配，请修改";
						}
					}

				}
				// 填报单位
				data.put("REPORTING_PERSON_FIRST", listPd.get(i).getString("var10"));
				// 填报人
				String var11 = listPd.get(i).getString("var11");
				if(var11 != null && !var11.equals("")){
					data.put("REPORTING_UNIT_FIRST",orgMap.get(listPd.get(i).getString("var11")));
				}
				if(StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
					data.put("IMG_URL",listPd.get(i).getString("var12"));
				}

				// 计划完成时间

				// 上报时间
//				String var10 = listPd.get(i).getString("var10");
				String COMMIT_TIME = ""; // 默认为空
				data.put("COMMIT_TIME", Tools.date2Str(new Date(),"yyyy-MM-dd"));
				COMMIT_TIME = makeTimeForm(data.getString("COMMIT_TIME"));
				int month = Integer.parseInt(COMMIT_TIME.substring(5, 7));
				data.put("YEAR", COMMIT_TIME.substring(0, 4));
				data.put("MONTH", COMMIT_TIME.substring(5, 7));
				data.put("DAY", COMMIT_TIME.substring(8, 10));
				data.put("QUARTER", (month + 2) / 3);

				// 默认未提交
				data.put("RECTIFY_STAGE", "1");
				data.put("REPORTING_FILE","");
				data.put("SORT", i);
//				addList.add(data);

				//根据机构名称找到对应简称
				PageData tempParam = new PageData();
				tempParam.put("ORG_ID",data.getString("ORG_ID"));
				tempParam = morgService.findById(tempParam);
				String rectifyNumber = getRectifyNumber(tempParam.getString("ORG_NAME_SHORT"));
				data.put("RECTIFYINFO_NUMBER", rectifyNumber);

				if(alertText.equals("")){
					rectifyinfoService.save(data);
					successNum++;
					allAlertText += alertText;
				}
				else{
					fallNum++;
				}
			}


			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
			mv.addObject("alertText",allAlertText);
			mv.addObject("successNum",successNum);
			mv.addObject("fallNum",fallNum);
		}
		mv.setViewName("save_result_fengxian");
		return mv;
	}

	/**从EXCEL导入到数据库(待整改)
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcelSNC")
	public ModelAndView readExcelSNC(
			@RequestParam(value="excel",required=false) MultipartFile file
	) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入整改信息到数据库");
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(),"yyyy-MM-dd")+"从EXCEL导入整改信息到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "rectifyinfoexcel"+ Tools.date2Str(new Date(),"yyyyMMddHHmmss"));							//执行上传
			List<PageData> listPd = (List)objectExcelReadPic.readExcel(filePath, fileName, 0, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			// 准备用到的Map
			Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, false);
			Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, false);
			Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, false);

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

			//获取海南地区
			String hainanParam = "1";
			List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
			PageData areaMap = new PageData();
			for (Dictionaries area : areaList) {
				if(area.getSubDict() != null){
					for(Dictionaries subArea : area.getSubDict()){
						areaMap.put(subArea.getNAME(), subArea.getDICTIONARIES_ID());
						if(subArea.getSubDict() != null){
							for(Dictionaries subSubArea : subArea.getSubDict()){
								areaMap.put(subSubArea.getNAME(), subSubArea.getDICTIONARIES_ID());
							}
						}
					}
				}
				areaMap.put(area.getNAME(), area.getDICTIONARIES_ID());
			}
			//新增提示警告，每次查询的结果如果是null就写入字符串，最后alert出来,隐患需要进行修改，所以加个修改的计数
			String allAlertText = "";
			int successNum = 0;
			int successNumEdit = 0;
			int fallNum = 0;
			List<PageData> addList = new ArrayList<PageData>();
			for(int i=0;i<listPd.size();i++){
				String alertText = "";
				int accidentTag = 0;
				// 默认的字段
				String now = Tools.date2Str(new Date());
				String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");

				PageData data = new PageData();

				data.put("SORT", "0");	//顺序
				data.put("ISDEL", "0");	//删除
				data.put("CREATER", Jurisdiction.getUserId());	//创建人
				data.put("CREATE_DATE", now);	//创建时间

				if (WebConstant.IS_YES.equals(data.getString("IS_COMPLETE"))) {
					data.put("COMPLETE_TIME", nowDay);
				}

				// 导入的字段

				//隐患id
				String var0 = listPd.get(i).getString("var0");
				if(var0 != null && !var0.equals("")){
					data.put("RECTIFYINFO_NUMBER", var0);
				}
				else{
					data.put("RECTIFYINFO_NUMBER", "");	//主键
				}

				// 组织机构
				String var1 = listPd.get(i).getString("var1");
				if(var1 != null && !var1.equals("")){
					data.put("ORG_ID", orgMap.get(var1));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第A列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第A列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第B列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第B列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}

				}

				// 文件编号
				String var2 = listPd.get(i).getString("var2");
				data.put("FILE_CODE", var2);


				// 项目名称
				String var3 = listPd.get(i).getString("var3");
				if(var3 != null && !var3.equals("")){
					data.put("PROJECT_NAME", projectMap.get(var3));
				}

//				else{
//					if(listPd.get(i).getString("edit") != null){
//						if(alertText.equals("")){
//							alertText += "第D列第" + (i+3) + "行数据不匹配，请修改";
//							accidentTag = 1;
//						}
//						else{
//							alertText += "\n第D列第" + (i+3) + "行数据不匹配，请修改";
//							accidentTag = 1;
//						}
//					}
//					else{
//						if(alertText.equals("")){
//							alertText += "第C列第" + (i+3) + "行数据不匹配，请修改";
//							accidentTag = 1;
//						}
//						else{
//							alertText += "\n第C列第" + (i+3) + "行数据不匹配，请修改";
//							accidentTag = 1;
//						}
//					}
//
//				}
				// 隐患情况
				data.put("HIDDEN_DANGER_INFO", listPd.get(i).getString("var4"));
				// 隐患类别
				String var5 = listPd.get(i).getString("var5");
				if(var5 != null && !var5.equals("")){
					data.put("HIDDEN_DANGER_CLASSIFY", classifyMap.get(listPd.get(i).getString("var5")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";

						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第E列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第E列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				// 隐患级别
				String var6 = listPd.get(i).getString("var6");
				if(var6 != null && !var6.equals("")){
					data.put("HIDDEN_DANGER_LEVEL", levelMap.get(listPd.get(i).getString("var6")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}

				}
				// 隐患因素
				String var7 = listPd.get(i).getString("var7");
				if(var7 != null && !var7.equals("")){
					data.put("HIDDEN_DANGER_FACTOR", factorMap.get(listPd.get(i).getString("var7")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
							alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第H列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}

				}
				//整改期限
				String var8= listPd.get(i).getString("var8");
				if (StringUtils.isNotEmpty(var8)) {
					String PLAN_COMPLETE_TIME = ""; // 默认为空
					PLAN_COMPLETE_TIME = makeTimeForm(var8);
					data.put("PLAN_COMPLETE_TIME", PLAN_COMPLETE_TIME);
				}
				// 所属区域
				String var9 = listPd.get(i).getString("var9");
				if(var9 != null && !var9.equals("")){
					data.put("RECTIFYINFO_AREA", areaMap.get(listPd.get(i).getString("var9")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第I列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第I列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第J列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
						else{
							alertText += "\n第J列第" + (i+3) + "行数据不匹配，请修改";
							accidentTag = 1;
						}
					}

				}

				// 填报人
				data.put("REPORTING_PERSON_FIRST", listPd.get(i).getString("var10"));
				// 填报单位
				String var11 = listPd.get(i).getString("var11");
				if(var11 != null && !var11.equals("")){
					data.put("REPORTING_UNIT_FIRST",orgMap.get(listPd.get(i).getString("var11")));
				}

				if(StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
					data.put("IMG_URL",listPd.get(i).getString("var12"));
				}

				//整改措施
				data.put("RECTIFY_MEASURES", listPd.get(i).getString("var13"));

				//责任人
				data.put("PERSON_RESPONSIBLE", listPd.get(i).getString("var14"));
				//责任单位
				String var15 = listPd.get(i).getString("var15");
				if(var15 != null && !var15.equals("")){
					data.put("RESPONSIBLE_UNIT",orgMap.get(listPd.get(i).getString("var15")));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第P列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第P列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第O列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第O列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}



				// 计划完成时间

				// 上报时间
//				String var10 = listPd.get(i).getString("var10");
				String COMMIT_TIME = Tools.date2Str(new Date(),"yyyy-MM-dd"); // 默认为空
				data.put("COMMIT_TIME", COMMIT_TIME);
				COMMIT_TIME = makeTimeForm(COMMIT_TIME);
				int month = Integer.parseInt(COMMIT_TIME.substring(5, 7));
				data.put("YEAR", COMMIT_TIME.substring(0, 4));
				data.put("MONTH", COMMIT_TIME.substring(5, 7));
				data.put("DAY", COMMIT_TIME.substring(8, 10));
				data.put("QUARTER", (month + 2) / 3);

				// 默认未提交
				data.put("RECTIFY_STAGE", "3");
				data.put("REPORTING_FILE","");

				if(alertText.equals("")){

					PageData duplicateParam = new PageData();
					duplicateParam.put("RECTIFYINFO_NUMBER",data.getString("RECTIFYINFO_NUMBER"));

					PageData resultParam = rectifyinfoService.findByNumber(duplicateParam);

					data.put("SORT", i);
					if(resultParam != null && accidentTag == 0){
						if(!StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
							data.put("IMG_URL",resultParam.getString("IMG_URL"));
						}
						//如果有对应隐患就把隐患id放入data
						data.put("RECTIFY_ID",resultParam.getString("RECTIFY_ID"));
						rectifyinfoService.edit(data);
						successNumEdit++;
					}
					else if(accidentTag == 0){
						//如果有流水号就直接放入
						if(duplicateParam.getString("RECTIFYINFO_NUMBER") != null && !duplicateParam.getString("RECTIFYINFO_NUMBER").equals("")){
						}
						else{
							//根据机构名称找到对应简称
							PageData tempParam = new PageData();
							tempParam.put("ORG_ID",data.getString("ORG_ID"));
							tempParam = morgService.findById(tempParam);
							String rectifyNumber = getRectifyNumber(tempParam.getString("ORG_NAME_SHORT"));
							data.put("RECTIFYINFO_NUMBER", rectifyNumber);
						}

						data.put("RECTIFY_ID",this.get32UUID());
						rectifyinfoService.save(data);
						successNum++;
					}
					else{
						fallNum++;
					}

				}
				else{
					fallNum++;
				}
				allAlertText += alertText;
			}


			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
			mv.addObject("alertText",allAlertText);
			mv.addObject("successNum",successNum);
			mv.addObject("successNumEdit",successNumEdit);
			mv.addObject("fallNum",fallNum);
		}
		mv.setViewName("save_result_edit");
		return mv;
	}

	/**从EXCEL导入到数据库(待整改)
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcelSC")
	public ModelAndView readExcelSC(
			@RequestParam(value="excel",required=false) MultipartFile file
	) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入整改信息到数据库");
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(),"yyyy-MM-dd")+"从EXCEL导入整改信息到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "rectifyinfoexcel"+ Tools.date2Str(new Date(),"yyyyMMddHHmmss"));							//执行上传
			List<PageData> listPd = (List)objectExcelReadPic.readExcel(filePath, fileName, 0, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			// 准备用到的Map
			Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, false);
			Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, false);
			Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, false);

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

			//获取海南地区
			String hainanParam = "1";
			List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
			PageData areaMap = new PageData();
			for (Dictionaries area : areaList) {
				if(area.getSubDict() != null){
					for(Dictionaries subArea : area.getSubDict()){
						areaMap.put(subArea.getNAME(), subArea.getDICTIONARIES_ID());
						if(subArea.getSubDict() != null){
							for(Dictionaries subSubArea : subArea.getSubDict()){
								areaMap.put(subSubArea.getNAME(), subSubArea.getDICTIONARIES_ID());
							}
						}
					}
				}
				areaMap.put(area.getNAME(), area.getDICTIONARIES_ID());
			}
			//新增提示警告，每次查询的结果如果是null就写入字符串，最后alert出来

			String allAlertText = "";
			int successNum = 0;
			int successNumEdit = 0;
			int fallNum = 0;
			List<PageData> addList = new ArrayList<PageData>();
			for(int i=0;i<listPd.size();i++){
				String alertText = "";
				int accidentTag = 0;
				// 默认的字段
				String now = Tools.date2Str(new Date());
				String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");

				PageData data = new PageData();

				//隐患id
				String var0 = listPd.get(i).getString("var0");
				if(var0 != null && !var0.equals("")){
					data.put("RECTIFYINFO_NUMBER", var0);
				}
				else{
					data.put("RECTIFYINFO_NUMBER","");	//主键
				}
				data.put("SORT", "0");	//顺序
				data.put("ISDEL", "0");	//删除
				data.put("CREATER", Jurisdiction.getUserId());	//创建人
				data.put("CREATE_DATE", now);	//创建时间

				if (WebConstant.IS_YES.equals(data.getString("IS_COMPLETE"))) {
					data.put("COMPLETE_TIME", nowDay);
				}

				// 导入的字段

//				String var0 = listPd.get(i).getString("var0");
//				data.put("RECTIFY_ID", var0);
				// 组织机构
				String var1 = listPd.get(i).getString("var1");
				if(var1 != null && !var1.equals("")){
					if(orgMap.get(var1) != null){
						data.put("ORG_ID", orgMap.get(var1));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第B列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第B列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第A列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第A列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第B列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第B列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第A列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第A列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}

				// 文件编号
				String var2 = listPd.get(i).getString("var2");
				data.put("FILE_CODE", var2);

				// 项目名称
				String var3 = listPd.get(i).getString("var3");
				if(projectMap.get(var3) != null){
					data.put("PROJECT_NAME", projectMap.get(var3));
				}
				else{
					data.put("PROJECT_NAME", "");
				}
//				if(var3 != null && !var3.equals("")){
//					if(projectMap.get(var3) != null){
//						data.put("PROJECT_NAME", projectMap.get(var3));
//					}
//					else{
//						if(listPd.get(i).getString("edit") != null){
//							if(alertText.equals("")){
//								alertText += "第D列第" + (i+3) + "行数据不匹配，请修改";
//							}
//							else{
//								alertText += "\n第D列第" + (i+3) + "行数据不匹配，请修改";
//							}
//						}
//						else{
//							if(alertText.equals("")){
//								alertText += "第C列第" + (i+3) + "行数据不匹配，请修改";
//							}
//							else{
//								alertText += "\n第C列第" + (i+3) + "行数据不匹配，请修改";
//							}
//						}
//						accidentTag = 1;
//					}

//				}
//				else{
//					if(listPd.get(i).getString("edit") != null){
//						if(alertText.equals("")){
//							alertText += "第D列第" + (i+3) + "行数据不匹配，请修改";
//						}
//						else{
//							alertText += "\n第D列第" + (i+3) + "行数据不匹配，请修改";
//						}
//					}
//					else{
//						if(alertText.equals("")){
//							alertText += "第C列第" + (i+3) + "行数据不匹配，请修改";
//						}
//						else{
//							alertText += "\n第C列第" + (i+3) + "行数据不匹配，请修改";
//						}
//					}
//					accidentTag = 1;
//				}
				// 隐患情况
				data.put("HIDDEN_DANGER_INFO", listPd.get(i).getString("var4"));
				// 隐患类别
				String var5 = listPd.get(i).getString("var5");
				if(var5 != null && !var5.equals("")){
					if(classifyMap.get(listPd.get(i).getString("var5")) != null){
						data.put("HIDDEN_DANGER_CLASSIFY", classifyMap.get(listPd.get(i).getString("var5")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第E列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第E列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第E列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第E列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				// 隐患级别
				String var6 = listPd.get(i).getString("var6");
				if(var6 != null && !var6.equals("")){
					if(levelMap.get(listPd.get(i).getString("var6")) != null){
						data.put("HIDDEN_DANGER_LEVEL", levelMap.get(listPd.get(i).getString("var6")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				// 隐患因素
				String var7 = listPd.get(i).getString("var7");
				if(var7 != null && !var7.equals("")){
					if(factorMap.get(listPd.get(i).getString("var7")) != null){
						data.put("HIDDEN_DANGER_FACTOR", factorMap.get(listPd.get(i).getString("var7")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第H列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第H列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				//整改期限
				String var8 = listPd.get(i).getString("var8");
				if (StringUtils.isNotEmpty(var8)) {
					String PLAN_COMPLETE_TIME = ""; // 默认为空
					PLAN_COMPLETE_TIME = makeTimeForm(var8);
					data.put("PLAN_COMPLETE_TIME", PLAN_COMPLETE_TIME);
				}
				// 所属区域
				String var9 = listPd.get(i).getString("var9");
				if(var9 != null && !var9.equals("")){
					if(areaMap.get(listPd.get(i).getString("var9")) != null){
						data.put("RECTIFYINFO_AREA", areaMap.get(listPd.get(i).getString("var9")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第J列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第J列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第I列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第I列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第J列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第J列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第I列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第I列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}

				// 填报人
				data.put("REPORTING_PERSON_FIRST", listPd.get(i).getString("var10"));
				// 填报单位
				String var11 = listPd.get(i).getString("var11");
				if(var11 != null && !var11.equals("")){
					data.put("REPORTING_UNIT_FIRST",orgMap.get(listPd.get(i).getString("var11")));
				}

				//上传图片
				if(StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
					data.put("IMG_URL",listPd.get(i).getString("var12"));
				}

				//整改措施
				data.put("RECTIFY_MEASURES", listPd.get(i).getString("var13"));

				//责任人
				data.put("PERSON_RESPONSIBLE", listPd.get(i).getString("var14"));
				//责任单位
				String var15 = listPd.get(i).getString("var15");
				if(var15 != null && !var15.equals("")){
					if(orgMap.get(listPd.get(i).getString("var15")) != null){
						data.put("RESPONSIBLE_UNIT",orgMap.get(listPd.get(i).getString("var15")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第P列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第P列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第O列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第O列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第P列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第P列第" + (i+3) + "行数据为空，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第O列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第O列第" + (i+3) + "行数据为空，请修改";
						}
					}
					accidentTag = 1;
				}

				//完成时间
				String var16 = listPd.get(i).getString("var16");
				//判断是不是正常的时间格式
				if(var16.contains("-")){
					data.put("COMPLETE_TIME", listPd.get(i).getString("var16"));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第R列第" + (i+3) + "行完成时间格式不对，请修改";
						}
						else{
							alertText += "\n第R列第" + (i+3) + "行完成时间格式不对，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第Q列第" + (i+3) + "行完成时间格式不对，请修改";
						}
						else{
							alertText += "\n第Q列第" + (i+3) + "行完成时间格式不对，请修改";
						}
					}
					accidentTag = 1;
				}


				//整改投入
				data.put("RECTIFY_INVESTMENT", listPd.get(i).getString("var17"));

				//填报单位
				String var18 = listPd.get(i).getString("var18");
				if(var18 != null && !var18.equals("")){
					if(orgMap.get(listPd.get(i).getString("var18")) != null){
						data.put("REPORTING_UNIT",orgMap.get(listPd.get(i).getString("var18")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第S列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第S列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第R列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第R列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第S列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第S列第" + (i+3) + "行数据为空，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第R列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第R列第" + (i+3) + "行数据为空，请修改";
						}
					}
					accidentTag = 1;
				}
				//填报人
				data.put("REPORTING_PERSON", listPd.get(i).getString("var19"));

				//上传整改图片
				if(StringUtils.isNotEmpty(listPd.get(i).getString("var20"))){
					data.put("REPORTING_FILE",listPd.get(i).getString("var20"));
				}
				else{
					data.put("REPORTING_FILE","");
				}


				// 计划完成时间

				// 上报时间
//				String var10 = listPd.get(i).getString("var10");
				String COMMIT_TIME = Tools.date2Str(new Date(),"yyyy-MM-dd"); // 默认为空
				data.put("COMMIT_TIME", COMMIT_TIME);
				COMMIT_TIME = makeTimeForm(COMMIT_TIME);
				int month = Integer.parseInt(COMMIT_TIME.substring(5, 7));
				data.put("YEAR", COMMIT_TIME.substring(0, 4));
				data.put("MONTH", COMMIT_TIME.substring(5, 7));
				data.put("DAY", COMMIT_TIME.substring(8, 10));
				data.put("QUARTER", (month + 2) / 3);

				// 默认已完成
				data.put("RECTIFY_STAGE", "4");

				data.put("SORT", i);
				if(alertText.equals("")){

					PageData duplicateParam = new PageData();
					duplicateParam.put("RECTIFYINFO_NUMBER",data.getString("RECTIFYINFO_NUMBER"));

					PageData resultParam = rectifyinfoService.findByNumber(duplicateParam);

					data.put("SORT", i);
					if(resultParam != null && accidentTag == 0){
						if(!StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
							data.put("IMG_URL",resultParam.getString("IMG_URL"));
						}
						//如果有对应隐患就把隐患id放入data
						data.put("RECTIFY_ID",resultParam.getString("RECTIFY_ID"));
						rectifyinfoService.edit(data);
						successNumEdit++;
					}
					else if(accidentTag == 0){
						//如果有流水号就直接放入
						if(duplicateParam.getString("RECTIFYINFO_NUMBER") != null && !duplicateParam.getString("RECTIFYINFO_NUMBER").equals("")){
						}
						else{
							//根据机构名称找到对应简称
							PageData tempParam = new PageData();
							tempParam.put("ORG_ID",data.getString("ORG_ID"));
							tempParam = morgService.findById(tempParam);
							String rectifyNumber = getRectifyNumber(tempParam.getString("ORG_NAME_SHORT"));
							data.put("RECTIFYINFO_NUMBER", rectifyNumber);
						}


						data.put("RECTIFY_ID",this.get32UUID());
						rectifyinfoService.save(data);
						successNum++;
					}
					else{
						fallNum++;
					}

				}
				else{
					fallNum++;
					allAlertText += alertText;
				}
			}


			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
			mv.addObject("alertText",allAlertText);
			mv.addObject("successNumEdit",successNumEdit);
			mv.addObject("successNum",successNum);
			mv.addObject("fallNum",fallNum);
		}
		mv.setViewName("save_result_edit");
		return mv;
	}

	/**从EXCEL导入到数据库(历史数据)
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcelHistory")
	public ModelAndView readExcelHistory(
			@RequestParam(value="excel",required=false) MultipartFile file
	) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入整改信息到数据库");
		logBefore(logger, Jurisdiction.getUsername()+Tools.date2Str(new Date(),"yyyy-MM-dd")+"从EXCEL导入整改信息到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "rectifyinfoexcel"+ Tools.date2Str(new Date(),"yyyyMMddHHmmss"));							//执行上传
			List<PageData> listPd = (List)objectExcelReadPic.readExcel(filePath, fileName, 0, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			// 准备用到的Map
			Map<String, String> classifyMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, false);
			Map<String, String> levelMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, false);
			Map<String, String> factorMap = dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, false);

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

			//获取海南地区
			String hainanParam = "1";
			List<Dictionaries> areaList = dictionariesService.listAllDict(hainanParam);
			PageData areaMap = new PageData();
			for (Dictionaries area : areaList) {
				if(area.getSubDict() != null){
					for(Dictionaries subArea : area.getSubDict()){
						areaMap.put(subArea.getNAME(), subArea.getDICTIONARIES_ID());
						if(subArea.getSubDict() != null){
							for(Dictionaries subSubArea : subArea.getSubDict()){
								areaMap.put(subSubArea.getNAME(), subSubArea.getDICTIONARIES_ID());
							}
						}
					}
				}
				areaMap.put(area.getNAME(), area.getDICTIONARIES_ID());
			}
			//新增提示警告，每次查询的结果如果是null就写入字符串，最后alert出来

			String allAlertText = "";
			int successNum = 0;
			int successNumEdit = 0;
			int fallNum = 0;
			List<PageData> addList = new ArrayList<PageData>();
			for(int i=0;i<listPd.size();i++){
				String alertText = "";
				int accidentTag = 0;
				// 默认的字段
				String now = Tools.date2Str(new Date());
				String nowDay = Tools.date2Str(new Date(), "yyyy-MM-dd");

				PageData data = new PageData();

				//隐患id
				String var0 = listPd.get(i).getString("var0");
				if(var0 != null && !var0.equals("")){
					data.put("RECTIFYINFO_NUMBER", var0);
				}
				else{
					data.put("RECTIFYINFO_NUMBER","");	//主键
				}
				data.put("SORT", "0");	//顺序
				data.put("ISDEL", "0");	//删除
				data.put("CREATER", Jurisdiction.getUserId());	//创建人
				data.put("CREATE_DATE", now);	//创建时间

				if (WebConstant.IS_YES.equals(data.getString("IS_COMPLETE"))) {
					data.put("COMPLETE_TIME", nowDay);
				}

				// 导入的字段

//				String var0 = listPd.get(i).getString("var0");
//				data.put("RECTIFY_ID", var0);
				// 组织机构
				String var1 = listPd.get(i).getString("var1");
				if(var1 != null && !var1.equals("")){
					if(orgMap.get(var1) != null){
						data.put("ORG_ID", orgMap.get(var1));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第B列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第B列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第A列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第A列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第B列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第B列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第A列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第A列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}

				// 文件编号
				String var2 = listPd.get(i).getString("var2");
				data.put("FILE_CODE", var2);

				// 项目名称
				String var3 = listPd.get(i).getString("var3");
				if(projectMap.get(var3) != null){
					data.put("PROJECT_NAME", projectMap.get(var3));
				}
				else{
					data.put("PROJECT_NAME", "");
				}
//				if(var3 != null && !var3.equals("")){
//					if(projectMap.get(var3) != null){
//						data.put("PROJECT_NAME", projectMap.get(var3));
//					}
//					else{
//						if(listPd.get(i).getString("edit") != null){
//							if(alertText.equals("")){
//								alertText += "第D列第" + (i+3) + "行数据不匹配，请修改";
//							}
//							else{
//								alertText += "\n第D列第" + (i+3) + "行数据不匹配，请修改";
//							}
//						}
//						else{
//							if(alertText.equals("")){
//								alertText += "第C列第" + (i+3) + "行数据不匹配，请修改";
//							}
//							else{
//								alertText += "\n第C列第" + (i+3) + "行数据不匹配，请修改";
//							}
//						}
//						accidentTag = 1;
//					}
//
//				}
//				else{
//					if(listPd.get(i).getString("edit") != null){
//						if(alertText.equals("")){
//							alertText += "第D列第" + (i+3) + "行数据不匹配，请修改";
//						}
//						else{
//							alertText += "\n第D列第" + (i+3) + "行数据不匹配，请修改";
//						}
//					}
//					else{
//						if(alertText.equals("")){
//							alertText += "第C列第" + (i+3) + "行数据不匹配，请修改";
//						}
//						else{
//							alertText += "\n第C列第" + (i+3) + "行数据不匹配，请修改";
//						}
//					}
//					accidentTag = 1;
//				}
				// 隐患情况
				data.put("HIDDEN_DANGER_INFO", listPd.get(i).getString("var4"));
				// 隐患类别
				String var5 = listPd.get(i).getString("var5");
				if(var5 != null && !var5.equals("")){
					if(classifyMap.get(listPd.get(i).getString("var5")) != null){
						data.put("HIDDEN_DANGER_CLASSIFY", classifyMap.get(listPd.get(i).getString("var5")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第E列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第E列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第E列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第E列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				// 隐患级别
				String var6 = listPd.get(i).getString("var6");
				if(var6 != null && !var6.equals("")){
					if(levelMap.get(listPd.get(i).getString("var6")) != null){
						data.put("HIDDEN_DANGER_LEVEL", levelMap.get(listPd.get(i).getString("var6")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第F列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第F列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				// 隐患因素
				String var7 = listPd.get(i).getString("var7");
				if(var7 != null && !var7.equals("")){
					if(factorMap.get(listPd.get(i).getString("var7")) != null){
						data.put("HIDDEN_DANGER_FACTOR", factorMap.get(listPd.get(i).getString("var7")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第H列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第H列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第H列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第G列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第G列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}
				//整改期限
				String var8 = listPd.get(i).getString("var8");
				if (StringUtils.isNotEmpty(var8)) {
					String PLAN_COMPLETE_TIME = ""; // 默认为空
					PLAN_COMPLETE_TIME = makeTimeForm(var8);
					data.put("PLAN_COMPLETE_TIME", PLAN_COMPLETE_TIME);
				}
				// 所属区域
				String var9 = listPd.get(i).getString("var9");
				if(var9 != null && !var9.equals("")){
					if(areaMap.get(listPd.get(i).getString("var9")) != null){
						data.put("RECTIFYINFO_AREA", areaMap.get(listPd.get(i).getString("var9")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第J列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第J列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第I列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第I列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第J列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第J列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第I列第" + (i+3) + "行数据不匹配，请修改";
						}
						else{
							alertText += "\n第I列第" + (i+3) + "行数据不匹配，请修改";
						}
					}
					accidentTag = 1;
				}

				// 填报人
				data.put("REPORTING_PERSON_FIRST", listPd.get(i).getString("var10"));
				// 填报单位
				String var11 = listPd.get(i).getString("var11");
				if(var11 != null && !var11.equals("")){
					data.put("REPORTING_UNIT_FIRST",orgMap.get(listPd.get(i).getString("var11")));
				}

				//上传图片
				if(StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
					data.put("IMG_URL",listPd.get(i).getString("var12"));
				}

				//整改措施
				data.put("RECTIFY_MEASURES", listPd.get(i).getString("var13"));

				//责任人
				data.put("PERSON_RESPONSIBLE", listPd.get(i).getString("var14"));
				//责任单位
				String var15 = listPd.get(i).getString("var15");
				if(var15 != null && !var15.equals("")){
					if(orgMap.get(listPd.get(i).getString("var15")) != null){
						data.put("RESPONSIBLE_UNIT",orgMap.get(listPd.get(i).getString("var15")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第P列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第P列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第O列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第O列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第P列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第P列第" + (i+3) + "行数据为空，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第O列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第O列第" + (i+3) + "行数据为空，请修改";
						}
					}
					accidentTag = 1;
				}

				//完成时间
				String var16 = listPd.get(i).getString("var16");
				//判断是不是正常的时间格式
				if(var16.contains("-")){
					data.put("COMPLETE_TIME", listPd.get(i).getString("var16"));
				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第Q列第" + (i+3) + "行完成时间格式不对，请修改";
						}
						else{
							alertText += "\n第Q列第" + (i+3) + "行完成时间格式不对，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第P列第" + (i+3) + "行完成时间格式不对，请修改";
						}
						else{
							alertText += "\n第P列第" + (i+3) + "行完成时间格式不对，请修改";
						}
					}
					accidentTag = 1;
				}


				//整改投入
				data.put("RECTIFY_INVESTMENT", listPd.get(i).getString("var17"));

				//填报单位
				String var18 = listPd.get(i).getString("var18");
				if(var18 != null && !var18.equals("")){
					if(orgMap.get(listPd.get(i).getString("var18")) != null){
						data.put("REPORTING_UNIT",orgMap.get(listPd.get(i).getString("var18")));
					}
					else{
						if(listPd.get(i).getString("edit") != null){
							if(alertText.equals("")){
								alertText += "第S列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第S列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						else{
							if(alertText.equals("")){
								alertText += "第R列第" + (i+3) + "行数据不匹配，请修改";
							}
							else{
								alertText += "\n第R列第" + (i+3) + "行数据不匹配，请修改";
							}
						}
						accidentTag = 1;
					}

				}
				else{
					if(listPd.get(i).getString("edit") != null){
						if(alertText.equals("")){
							alertText += "第S列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第S列第" + (i+3) + "行数据为空，请修改";
						}
					}
					else{
						if(alertText.equals("")){
							alertText += "第R列第" + (i+3) + "行数据为空，请修改";
						}
						else{
							alertText += "\n第R列第" + (i+3) + "行数据为空，请修改";
						}
					}
					accidentTag = 1;
				}
				//填报人
				data.put("REPORTING_PERSON", listPd.get(i).getString("var19"));

				//上传整改图片
				if(StringUtils.isNotEmpty(listPd.get(i).getString("var20"))){
					data.put("REPORTING_FILE",listPd.get(i).getString("var20"));
				}
				else{
					data.put("REPORTING_FILE","");
				}


				// 计划完成时间

				// 上报时间
//				String var10 = listPd.get(i).getString("var10");
				String COMMIT_TIME = Tools.date2Str(new Date(),"yyyy-MM-dd"); // 默认为空
				data.put("COMMIT_TIME", COMMIT_TIME);
				COMMIT_TIME = makeTimeForm(COMMIT_TIME);
				int month = Integer.parseInt(COMMIT_TIME.substring(5, 7));
				data.put("YEAR", COMMIT_TIME.substring(0, 4));
				data.put("MONTH", COMMIT_TIME.substring(5, 7));
				data.put("DAY", COMMIT_TIME.substring(8, 10));
				data.put("QUARTER", (month + 2) / 3);

				// 默认已完成
				data.put("RECTIFY_STAGE", "4");

				data.put("SORT", i);
				if(alertText.equals("")){

					PageData duplicateParam = new PageData();
					duplicateParam.put("RECTIFY_ID",data.getString("RECTIFY_ID"));

					PageData resultParam = rectifyinfoService.findByNumber(duplicateParam);

					data.put("SORT", i);
					if(resultParam != null && accidentTag == 0){
						if(!StringUtils.isNotEmpty(listPd.get(i).getString("var12"))){
							data.put("IMG_URL",resultParam.getString("IMG_URL"));
						}
						//如果有对应隐患就把隐患id放入data
						data.put("RECTIFY_ID",resultParam.getString("RECTIFY_ID"));
						rectifyinfoService.edit(data);
						successNumEdit++;
					}
					else if(accidentTag == 0){
						if(duplicateParam.getString("RECTIFYINFO_NUMBER") != null && !duplicateParam.getString("RECTIFYINFO_NUMBER").equals("")){
						}
						else{
							//根据机构名称找到对应简称
							PageData tempParam = new PageData();
							tempParam.put("ORG_ID",data.getString("ORG_ID"));
							tempParam = morgService.findById(tempParam);
							String rectifyNumber = getHistoryRectifyNumber(tempParam.getString("ORG_NAME_SHORT"));
							data.put("RECTIFYINFO_NUMBER", rectifyNumber);
						}

						data.put("RECTIFY_ID",this.get32UUID());

						rectifyinfoService.save(data);
						successNum++;
					}
					else{
						fallNum++;
					}

				}
				else{
					fallNum++;
				}
				allAlertText += alertText;
			}


			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
			mv.addObject("alertText",allAlertText);
			mv.addObject("successNumEdit",successNumEdit);
			mv.addObject("successNum",successNum);
			mv.addObject("fallNum",fallNum);
		}
		mv.setViewName("save_result_edit");
		return mv;
	}

	/**
	 * 处理时间
	 * @return
	 */
	@RequestMapping(value="/doTime")
	@ResponseBody
	public PageData doTime() throws Exception{
		List<PageData> list = rectifyinfoService.listAll(null);

		for (PageData data : list) {
			String COMPLETE_TIME = data.getString("COMPLETE_TIME");// 完成时间
			String COMMIT_TIME = data.getString("COMMIT_TIME"); // 提交时间
			if (StringUtils.isNotEmpty(COMPLETE_TIME) && StringUtils.isEmpty(COMMIT_TIME)) {
				COMPLETE_TIME = makeTimeForm(makeTimeForm(COMPLETE_TIME));

				int month = Integer.parseInt(COMPLETE_TIME.substring(5, 7));
				data.put("COMPLETE_TIME", COMPLETE_TIME);
				data.put("COMMIT_TIME", COMPLETE_TIME);
				data.put("YEAR", COMPLETE_TIME.substring(0, 4));
				data.put("MONTH", COMPLETE_TIME.substring(5, 7));
				data.put("DAY", COMPLETE_TIME.substring(8, 10));
				data.put("QUARTER", (month + 2) / 3);
				rectifyinfoService.edit(data);
			}
		}

		return null;
	}

	/**
	 * 日期时间格式化
	 * @param time
	 * @return
	 */
	public String makeTimeForm(String time) {
		time = time.replace("/", "-").replace(".", "-").replace("年", "-").replace("月", "-").replace("日", "-");
		try {
			String timeArray[] = time.split("-");
			if (timeArray[1].length() < 2) {
				timeArray[1] = "0" + timeArray[1];
			}
			if (timeArray[2].length() < 2) {
				timeArray[2] = "0" + timeArray[2];
			}
			time = timeArray[0] + "-" + timeArray[1] + "-" + timeArray[2];
		} catch (Exception e) {
			// TODO: handle exception

		}

		return time;
	}

	/*应急部人员填写页面
	*TODO
	* */
	@RequestMapping(value = "goAddRectifyInfo")
	public ModelAndView goAddRectifyInfo(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.addObject("msg", "list");

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		String overTime = pd.getString("OVER_TIME");
		if (StringUtils.isNotEmpty(overTime)) {
			String NOW = Tools.date2Str(new Date(), "yyyy-MM-dd");
			pd.put("NOW", NOW);
			pd.put("OVER_TIME", overTime);
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		// 默认显示已提交的数据
		if (StringUtils.isEmpty(pd.getString("IS_SUBMITE"))) {
			pd.put("IS_SUBMITE", WebConstant.IS_YES);
		}


		page.setPd(pd);
		List<PageData>	varList = rectifyinfoService.list(page);	//列出RectifyInfo列表

		mv.setViewName("backend/rectifyinfo/rectifyinfo_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getDefaultMap());
		mv.addObject("orgList", morgService.listAll(null));

		return mv;
	}

	/*二级机构填写整改时的对应信息
	 *
	 * */
	@RequestMapping(value = "goRectifyInfoSNC")
	public ModelAndView goRectifyInfoSNC(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		mv.addObject("msg", "list");

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String USER_ID = Jurisdiction.getUserId();
		User userRole = userService.getUserAndRoleById(USER_ID);
		if (!USER_ID.equals("1") && !userRole.getRole().getROLE_NAME().equals("应急部")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		//按照现在时间查询
		pd.put("NOW",Tools.date2Str(new Date(), "yyyy-MM-dd"));
		pd.put("RECTIFY_STAGE",2);
		page.setPd(pd);
		List<PageData>	varList = rectifyinfoService.list(page);	//列出RectifyInfo列表

		PageData param = new PageData();
		param.put("ISORG",1);

		mv.setViewName("backend/rectifyinfo/rectifyInfo_SecondNoComplete");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getDefaultMap());
		mv.addObject("orgList", morgService.listAll(param));
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(param));

		return mv;
	}

	/*二级机构填写整改后的信息
	 *
	 * */
	@RequestMapping(value = "goRectifyInfoSC")
	public ModelAndView goRectifyInfoSC(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RectifyInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String start = pd.getString("start");
		if (StringUtils.isNotEmpty(start)) {
			pd.put("lastStart", start);
		}

		String end = pd.getString("end");
		if (StringUtils.isNotEmpty(end)) {
			pd.put("lastEnd", end);
		}

		mv.addObject("msg", "list");

		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		String USER_ID = Jurisdiction.getUserId();
		if (!USER_ID.equals("1")) {
			// 除了admin，其他的根据权限过滤
			pd.put("USER_ID_QX", USER_ID);
		}

		//按照现在时间查询
		pd.put("NOW",Tools.date2Str(new Date(), "yyyy-MM-dd"));
		pd.put("RECTIFY_STAGE",3);
		page.setPd(pd);
		List<PageData>	varList = rectifyinfoService.list(page);	//列出RectifyInfo列表

		PageData param = new PageData();
		param.put("ISORG",1);


		mv.setViewName("backend/rectifyinfo/rectifyInfo_SecondCompleted");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("YesNoMap", WebConstant.getDefaultMap());
		mv.addObject("orgList", morgService.listAll(param));
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(param));

		return mv;
	}

	//刷新隐患每日流水号
	//定时在每天0点整刷新
	public void refreshRectifyNumber(){
		redisTemplate.opsForValue().set("rectify_number1",0+"");
	}

	public String getRectifyNumber(String orgName){
		ValueOperations valueOperations = redisTemplate.opsForValue();
		String number = "";
		//流水号开头的日期
		String date = Tools.date2Str(new Date(),"yyyyMMdd");

		//公司简称和安全隐患简称部分
		String Name = judgeOrgName.OrgNameShort(orgName) + "YH";

		//每日流水号
		String numberString = (String) valueOperations.get("rectify_number1");
		if(numberString == null){
			redisTemplate.opsForValue().set("rectify_number1",0+"");
		}


		long rectify_number = redisTemplate.opsForValue().increment("rectify_number1", 1);

		//超过9999设为0,然后时间向后一天
		if(rectify_number >= 9999){
			rectify_number = 1;
			redisTemplate.opsForValue().set("rectify_number1",0+"");
		}

		numberString = String.valueOf(rectify_number);

		//补零
		int zeroLength = 3;
		while(numberString.length() <= zeroLength){
			numberString = "0" + numberString;
		}

		number = date + Name + numberString;

		return number;
	}

	Date historyDate = new Date("2023/01/01 00:00:00");

	public String getHistoryRectifyNumber(String orgName){
		ValueOperations valueOperations = redisTemplate.opsForValue();
		String number = "";
		//流水号开头的日期

		//公司简称和安全隐患简称部分
		String Name = judgeOrgName.OrgNameShort(orgName) + "YH";

		//每日流水号
		String numberString = (String) valueOperations.get("rectify_number_his1");
		if(numberString == null){
			redisTemplate.opsForValue().set("rectify_number_his1",0+"");
		}


		long rectify_number = redisTemplate.opsForValue().increment("rectify_number_his1", 1);

		//超过9999设为0,然后时间向后一天
		if(rectify_number >= 9999){
			rectify_number = 1;
			redisTemplate.opsForValue().set("rectify_number_his1",0+"");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(historyDate);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			historyDate = calendar.getTime();

		}

		numberString = String.valueOf(rectify_number);

		//补零
		int zeroLength = 3;
		while(numberString.length() <= zeroLength){
			numberString = "0" + numberString;
		}

		number = Tools.date2Str(historyDate,"yyyyMMdd") + Name + numberString;

		return number;
	}

	@RequestMapping(value = "insertHisRectify")
	public void insertHisFengXian() throws Exception {
		PageData pd = this.getPageData();
//		pd.put("org_name_short","海控贸易");
		List<PageData> list = rectifyinfoService.findByShort(pd);

		for(PageData temp : list){
			String rectify_number = getHistoryRectifyNumber(temp.getString("org_name_short"));
			if(temp.getString("RECTIFYINFO_NUMBER") == null || temp.getString("RECTIFYINFO_NUMBER").equals("")){
				temp.put("RECTIFYINFO_NUMBER",rectify_number);
			}
			rectifyinfoService.edit(temp);


		}

	}


	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
