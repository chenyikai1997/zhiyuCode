package com.fh.controller.frontend;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.entity.Picker;
import com.fh.entity.system.Dictionaries;
import com.fh.service.backend.m_fengxian.M_fengxianManager;
import com.fh.service.backend.m_monitor_project.M_monitor_projectManager;
import com.fh.service.backend.m_water_project.M_water_projectManager;
import com.fh.service.backend.morg.MOrgManager;
import com.fh.service.backend.rectifyinfo.RectifyInfoManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.WebConstant;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.backend.Monitor;
import com.fh.entity.backend.Monitors;
import com.fh.entity.backend.Project;
import com.fh.entity.backend.Projects;
import com.fh.entity.backend.Projects2;
import com.fh.service.backend.answer.AnswerManager;
import com.fh.service.backend.burst.BurstManager;
import com.fh.service.backend.city.CityManager;
import com.fh.service.backend.duty.DutyManager;
import com.fh.service.backend.file.FileManager;
import com.fh.service.backend.filetype.FiletypeManager;
import com.fh.service.backend.monitor.MonitorManager;
import com.fh.service.backend.monitortype.MonitortypeManager;
import com.fh.service.backend.notice.NoticeManager;
import com.fh.service.backend.project.ProjectManager;
import com.fh.service.backend.projectType.ProjectTypeManager;
import com.fh.service.backend.reservoir.ReservoirManager;
import com.fh.service.backend.risk.RiskManager;
import com.fh.service.backend.typhoon.TyphoonManager;
import com.fh.service.backend.water.WaterManager;
import com.fh.service.backend.weather.WeatherManager;
import com.fh.util.DateUtil;
import com.fh.util.GetCameraPreviewURL;
import com.fh.util.HttpRequest;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SimpleWeather;
import com.fh.util.Tools;
import com.hazelcast.util.StringUtil;

import cn.hutool.core.date.DateTime;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.title.TextTitle;
import java.awt.Font;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.awt.Color;

import org.jfree.ui.RectangleInsets;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 说明：前端页面控制类
 * 创建人：FH Q313596790
 * 创建时间：2016-06-03
 */
@Controller
@RequestMapping(value="/frontend")
public class FrontendController extends BaseController {

	String menuUrl = "loginimg/list.do"; //菜单地址(权限用)
	@Resource(name="typhoonService")
	private TyphoonManager typhoonService;
	@Resource(name="noticeService")
	private NoticeManager noticeService;
	@Resource(name="answerService")
	private AnswerManager answerService;
	@Resource(name="dutyService")
	private DutyManager dutyService;
	@Resource(name="reservoirService")
	private ReservoirManager reservoirService;
	@Resource(name="waterService")
	private WaterManager waterService;
	@Resource(name="projectTypeService")
	private ProjectTypeManager projectTypeService;
	@Resource(name="projectService")
	private ProjectManager projectService;
	@Resource(name="cityService")
	private CityManager cityService;
	@Resource(name="filetypeService")
	private FiletypeManager filetypeService;
	@Resource(name="fileService")
	private FileManager fileService;
	@Resource(name="monitortypeService")
	private MonitortypeManager monitortypeService;
	@Resource(name="monitorService")
	private MonitorManager monitorService;
	@Resource(name="weatherService")
	private WeatherManager weatherService;
	@Resource(name="riskService")
	private RiskManager riskService;
	@Resource(name="burstService")
	private BurstManager burstService;
	@Resource(name="rectifyinfoService")
	private RectifyInfoManager rectifyinfoService;
	@Resource(name="m_fengxianService")
	private M_fengxianManager m_fengxianService;
	@Resource(name="morgService")
	private MOrgManager morgService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="m_water_projectService")
	private M_water_projectManager m_water_projectService;
	@Resource(name="m_monitor_projectService")
	private M_monitor_projectManager m_monitor_projectService;
	/**
	 * 去首页
	 */
	@RequestMapping(value="/index.html")
	public ModelAndView index(HttpServletRequest request,Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("frontend/index");
		mv.addObject("pd",pd);
		return mv;
	}

	//取气象信息
	@SuppressWarnings("unused")
	private String getWeather() {
		String weatherStr="";
		try
		{
			weatherStr=SimpleWeather.queryWeather("海口");
			//读取当天气象信息
			PageData pd = new PageData();
			String nowDateStr=DateUtil.getDay();
			pd.put("WEATHER_DATE", nowDateStr);
			PageData weather =weatherService.findByDate(pd);

			if(StringUtil.isNullOrEmpty(weatherStr)) {
				if(weather!=null) {
					weatherStr=weather.getString("WEATHER_INFO");
				}
			}else {
				if(weather!=null) {
					//修改气象信息
					weather.put("WEATHER_INFO", weatherStr);	//气象信息
					String datestr=Tools.date2Str(new Date());
					weather.put("MODIFYER", "1");	//修改人
					weather.put("MODIFY_DATE", datestr);	//修改时间
					weatherService.edit(weather);
				}else {
					//保存气象信息
					weather = new PageData();
					weather.put("WEATHER_ID", this.get32UUID());	//主键
					weather.put("WEATHER_DATE", nowDateStr);	//气象日期
					weather.put("WEATHER_INFO", weatherStr);	//气象信息
					weather.put("SORT", "0");	//排序
					weather.put("ISDEL", "0");	//删除标志
					String datestr=Tools.date2Str(new Date());
					weather.put("CREATER", "1");	//创建人
					weather.put("CREATE_DATE", datestr);	//创建时间
					weather.put("MODIFYER", "1");	//修改人
					weather.put("MODIFY_DATE", datestr);	//修改时间
					weatherService.save(weather);
				}

			}

		}catch(Exception ex) {
			logBefore(logger, "取气象异常:"+ex.getMessage());
		}
		return  weatherStr;
	}
	//水情图生成方法
	@SuppressWarnings({ "unused", "deprecation" })
	private List<String> getLineData(PageData reservoir,PageData pd,String lastStart,String lastEnd) {
		List<String> DATA_LIST=new ArrayList<String>();
		try {
			pd.put("RESERVOIR_ID", reservoir.getString("RESERVOIR_ID"));
			pd.put("lastStart", lastStart);
			pd.put("lastEnd", lastEnd);
			List<PageData> waterList =waterService.listAll(pd);

			List<Double> WATER_DATA_LIST=new ArrayList<Double>();
			List<String> WATER_DATETIME_LIST=new ArrayList<String>();
			for(PageData water:waterList) {
				Double water_data_value=Double.parseDouble(water.get("WATER_DATA").toString());
				WATER_DATA_LIST.add(water_data_value);
				String water_datetimeStr=water.getString("WATER_DATETIME");
				String hours=String.valueOf(DateUtil.StrToDate(water_datetimeStr).getHours());
				WATER_DATETIME_LIST.add(hours);
			}
			String WATER_DATA_STR="";
			String WATER_DATETIME_STR="";
			if(WATER_DATA_LIST!=null) {

				JSONArray arrH = JSONArray.fromObject(WATER_DATA_LIST);
				WATER_DATA_STR=arrH.toString();
			}
			if(WATER_DATETIME_LIST!=null) {

				JSONArray arrH = JSONArray.fromObject(WATER_DATETIME_LIST);
				WATER_DATETIME_STR=arrH.toString();
			}
			DATA_LIST.add(WATER_DATA_STR);
			DATA_LIST.add(WATER_DATETIME_STR);

		}catch(Exception ex) {
			logBefore(logger, "生成水情图异常:"+ex.getMessage());
		}
		return DATA_LIST;
	}


	//水情图生成方法
	@SuppressWarnings({ "unused", "deprecation" })
	private String getLineGrap(PageData reservoir,PageData pd,HttpServletRequest request) {
		String graphURL ="";
		try
		{
			TimeSeries timeSeries=new TimeSeries("水情统计",Hour.class);
			//时间曲线数据集合
			TimeSeriesCollection lineDataset=new TimeSeriesCollection();
			String water_dateStr="";
			if(reservoir!=null) {
				//取水情数据
				pd.put("RESERVOIR_ID", reservoir.getString("RESERVOIR_ID"));
				List<PageData> waterList=waterService.listAll(pd);

				if(waterList!=null) {
					for(PageData water:waterList){
						//构造数据集合
						String water_datetimeStr=water.getString("WATER_DATETIME");
						SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date myDate = standardDateFormat.parse(water_datetimeStr);
						Day day = new Day(myDate);
						water_dateStr=DateUtil.getDay(myDate);
						int hourInt=DateUtil.StrToDate(water_datetimeStr).getHours();
						Hour hour1=new Hour(hourInt,day);
						Double water_data=Double.parseDouble(water.get("WATER_DATA").toString());
						timeSeries.add(hour1,water_data);
					}
					lineDataset.addSeries(timeSeries);
					JFreeChart chart=ChartFactory.createTimeSeriesChart("水位线","时间","水位",lineDataset,true,false,false);

					//获得时序图显示区引用
					XYPlot plot=(XYPlot)chart.getPlot();
					XYLineAndShapeRenderer xyAndShapeRenderer=(XYLineAndShapeRenderer)plot.getRenderer();

					//设置网格背景颜色
					plot.setBackgroundPaint(Color.white);

					//设置网格竖线颜色
					plot.setDomainGridlinePaint(Color.pink);

					//设置曲线图xy轴的距离
					plot.setAxisOffset(new RectangleInsets(0D,0D,0D,10D));

					//设置曲线是否显示数据点
					xyAndShapeRenderer.setBaseShapesVisible(true);

					//设置子标题
					TextTitle subTitle=new TextTitle(water_dateStr,new Font("黑体",Font.BOLD,12));
					chart.addSubtitle(subTitle);

					//设置曲线显示各数据点的值
					XYItemRenderer xyitem=plot.getRenderer();
					xyitem.setBaseItemLabelsVisible(true);
					xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_LEFT));

					xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
					xyitem.setBaseItemLabelFont(new Font("Dialog",1,10));
					plot.setRenderer(xyitem);

					//设置主标题
					chart.setTitle(new TextTitle(reservoir.getString("RESERVOIR_NAME")+"水位统计",new Font("隶书",Font.ITALIC,15)));

					//使用ServletUtilities将报表转换为图片
					//注意：保存的图片如果希望在浏览器中直接显示，必须使用saveChartAsPNG方法，而不能用saveChartAsJPEG
					HttpSession session=request.getSession(true);
					String fileName = ServletUtilities.saveChartAsPNG(chart, 700, 400,
							null, session);
					//通过JFreeChart提供的控制器，生成图片的绝对路径
					graphURL = request.getContextPath() + "/DisplayChart?filename=" + fileName;
				}

			}

		}catch(Exception ex) {
			logBefore(logger, "生成水情图异常:"+ex.getMessage());
		}
		return graphURL;
	}
	//项目图生成方法
	@SuppressWarnings("unused")
	private String getGrapData(PageData pd) {
		String PROJECT_STR="";
		try
		{
			List<PageData> projectList=projectTypeService.listAll(pd);
			List<String> PROJECT_LIST=new ArrayList<String>();
			if(projectList!=null) {
				for(PageData project:projectList){
					String projectName=project.getString("PROJECTTYPE_NAME");
					int projectNum=Integer.parseInt(project.get("PROJECTTYPE_NUM").toString());
					String projectColor=project.getString("PROJECTTYPE_COLOR");
					String project_value="{ value:"+ projectNum+", name: '"+projectName+"', itemStyle: { color: '"+projectColor+"' } }";
					PROJECT_LIST.add(project_value);
				}
				if(PROJECT_LIST!=null) {
					PROJECT_STR=StringUtils.join(PROJECT_LIST,",");
				}
			}
			PROJECT_STR="["+PROJECT_STR+"]";
		}catch(Exception ex) {
			logBefore(logger, "生成项目图异常:"+ex.getMessage());
		}
		return PROJECT_STR;

	}
	//项目图生成方法
	@SuppressWarnings("unused")
	private String getGrapPie(PageData pd,HttpServletRequest request) {
		String graphURL ="";
		try
		{
			//创建饼状图形报表的数据集对象
			DefaultPieDataset dataset = new DefaultPieDataset();
			//添加报表统计数据
			//参数1：数据分类信息，代表数据是哪一类的数据信息，比如初级程序员人数为10000人
			//参数2：该类的数值
			List<PageData> projectList=projectTypeService.listAll(pd);
			if(projectList!=null) {
				for(PageData project:projectList){
					String projectName=project.getString("PROJECT_NAME");
					int projectNum=Integer.parseInt(project.get("PROJECT_NUM").toString());
					dataset.setValue(projectName, projectNum);
				}
				//在内存中创建报表对象(此处使用的是工厂方法)
				//工厂方法参数介绍
				//参数1：报表的标题信息
				//参数2：数据集对象
				//参数6：是否生成图例
				//参数7：是否显示工具提示
				//参数8：是否生成URL
				JFreeChart chart = ChartFactory.createPieChart3D("项目分布图",dataset, true, false, false);

				PiePlot plot = (PiePlot) chart.getPlot();
				plot.setLabelFont(new Font("隶书", 0, 10));

				//没有数据的时候显示的内容
				plot.setNoDataMessage("无数据显示");

				//在饼图的各块中显示百分比
				plot.setCircular(true);
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));

				//使用ServletUtilities将报表转换为图片
				//注意：保存的图片如果希望在浏览器中直接显示，必须使用saveChartAsPNG方法，而不能用saveChartAsJPEG
				HttpSession session=request.getSession(true);
				String fileName = ServletUtilities.saveChartAsPNG(chart, 500, 300,
						null, session);
				//通过JFreeChart提供的控制器，生成图片的绝对路径
				graphURL = request.getContextPath() + "/DisplayChart?filename=" + fileName;
			}

		}catch(Exception ex) {
			logBefore(logger, "生成项目图异常:"+ex.getMessage());
		}
		return graphURL;

	}

	/**
	 * 去气象信息页面
	 */
	@RequestMapping(value="/weather.html")
	public ModelAndView weather(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去气象信息页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//pd = this.getPageData();
		mv.setViewName("frontend/weather");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}

		//查海控应急响应级别
		if(typhoon!=null) {
			pd.put("ANSWER_COMPANY", "海南控股");
			pd.put("lastStart", DateUtil.getTime());
			PageData answer =answerService.findByName(pd);
			mv.addObject("answer", answer);
		}
		//取天气
		String weatherStr=getWeather();
		mv.addObject("weatherStr", weatherStr);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去值班信息页面
	 */
//	@RequestMapping(value="/zhibanMsg.html")
//	public ModelAndView zhibanMsg(HttpServletRequest request,Page page) throws Exception{
//		logBefore(logger, "去值班信息页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		//pd = this.getPageData();
//		mv.setViewName("frontend/zhibanMsg");
//		//查当前台风
//		PageData typhoon =typhoonService.findByNow(pd);
//		mv.addObject("typhoon", typhoon);
//		if(typhoon!=null) {
//			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
//		}
//
//		//取所有值班人员
//		if(typhoon!=null) {
//			pd.put("limitStar", 0);
//			pd.put("limitEnd", 1000);
//			String dateNowStr=DateUtil.getTime();//当前时间
//			pd.put("lastStart", dateNowStr);
//			List<PageData> dutyList =dutyService.listAll(pd);
//			mv.addObject("dutyList", dutyList);
//			mv.addObject("pd", pd);
//		}
//		//String cameraUrl=GetCameraPreviewURL.GetCameraURL();
//		//logBefore(logger, "摄像头URL:"+cameraUrl);
//		return mv;
//	}
	/**
	 * 去值班信息页面-水情页面
	 */
	@RequestMapping(value="/yingji_shuiba.html")
	public ModelAndView yingji_shuiba(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去值班信息页面-水情页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/yingji_shuiba");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}

		//取所有水库
		List<PageData> reservoirList=reservoirService.listAll(pd);
		mv.addObject("reservoirList", reservoirList);
		//查第一个水库
		Double WATER_LEVEL=0.0;//警戒水位
		Double WATER_MAX=0.0;//最大水位
		PageData reservoir=new PageData();
		if(pd.getString("RESERVOIR_ID")==null) {
			if(reservoirList!=null) {
				reservoir=reservoirList.get(0);
				WATER_LEVEL=Double.valueOf(reservoir.get("WATER_LEVEL").toString());
				WATER_MAX=WATER_LEVEL+30;
			}
		}else {
			reservoir=reservoirService.findById(pd);
			WATER_LEVEL=Double.valueOf(reservoir.get("WATER_LEVEL").toString());
			WATER_MAX=WATER_LEVEL+30;
		}
		mv.addObject("reservoir", reservoir);
		//生成水情图表
		if(typhoon!=null) {
			String dateStr="";
			String lastStart=DateUtil.getDay()+" 00:00:00";
			dateStr=DateUtil.getDay()+"至";
			if(!StringUtil.isNullOrEmpty(pd.getString("DATE_ID"))) {
				lastStart=DateUtil.getAfterDayDate1(pd.getString("DATE_ID"));
				dateStr=lastStart+"至";
			}
			dateStr=dateStr+DateUtil.getDay();
			lastStart=lastStart+" 00:00:00";
			String lastEnd=DateUtil.getDay()+" 59:59:59";
			List<String> DATA_List=getLineData(reservoir,pd,lastStart,lastEnd);
			String WATER_DATA_STR="";
			String WATER_DATETIME_STR="";
			if(DATA_List!=null&&DATA_List.size()>0) {
				WATER_DATA_STR=DATA_List.get(0);
			}
			if(DATA_List!=null&&DATA_List.size()>1) {
				WATER_DATETIME_STR=DATA_List.get(1);
			}
			mv.addObject("lineChartDate", dateStr);
			mv.addObject("WATER_DATA_STR", WATER_DATA_STR);
			mv.addObject("WATER_LEVEL", WATER_LEVEL);
			mv.addObject("WATER_MAX", WATER_MAX);
			mv.addObject("WATER_DATETIME_STR", WATER_DATETIME_STR);
			//String graphURL1=getLineGrap(reservoir,pd,request);
			//mv.addObject("graphURL1", graphURL1);
		}
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去值班信息页面-项目页面
	 */
	@RequestMapping(value="/yingji_gongcheng.html")
	public ModelAndView yingji_gongcheng(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去值班信息页面-项目页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/yingji_gongcheng");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}

		//生成项目图表
		String PROJECT_STR=getGrapData(pd);
		mv.addObject("PROJECT_STR", PROJECT_STR);
		//所有项目数据
		List<Projects> projectsMap=new ArrayList<Projects>();

		List<PageData> projectTypeList=projectTypeService.listAll(pd);
		if(projectTypeList!=null) {
			for(PageData projectType:projectTypeList) {
				//添加项目分类
				String PROJECTTYPE_NAME=projectType.getString("PROJECTTYPE_NAME");
				String PROJECTTYPE_NUM=projectType.get("PROJECTTYPE_NUM").toString();
				String PROJECTTYPE_ID=projectType.getString("PROJECTTYPE_ID");
				Projects projects=new Projects();
				projects.setPROJECTTYPE_NAME(PROJECTTYPE_NAME);
				projects.setPROJECTTYPE_NUM(PROJECTTYPE_NUM);
				//添加项目列表
				PageData pdProject = new PageData();
				pdProject.put("PROJECTTYPE_ID", PROJECTTYPE_ID);
				List<PageData> projectList=projectService.listAll(pdProject);
				List<Project> pROJECT_LIST=new ArrayList<Project>();
				if(projectList!=null) {
					for(PageData project:projectList) {
						Project item=new Project();
						item.setPROJECT_NAME(project.getString("PROJECT_NAME"));
						pROJECT_LIST.add(item);
					}
				}

				projects.setPROJECT_LIST(pROJECT_LIST);
				//构造数据组
				projectsMap.add(projects);
			}
		}
		mv.addObject("projectsMap", projectsMap);

		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去值班信息页面-项目页面
	 */
	@RequestMapping(value="/yingji_map.html")
	public ModelAndView yingji_map(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去值班信息页面-项目页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/yingji_map");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}

		//所有项目数据
		String PROJECT_STR="";
		List<PageData> cityList=cityService.listAll(pd);
		if(cityList!=null) {
			for(PageData city:cityList) {

				String CITY_ID=city.getString("CITY_ID");
				String CITY_NAME=city.getString("CITY_NAME");//城市名称

				pd.put("CITY_ID", CITY_ID);
				List<PageData> projectList=projectService.listAll(pd);
				if(projectList!=null) {
					int countValue=projectList.size();
					String projectNameStr="";
					for(PageData project:projectList) {
						String PROJECT_NAME=project.getString("PROJECT_NAME");
						projectNameStr=projectNameStr+"'"+PROJECT_NAME+"',";
					}
					if(projectNameStr.length()>0) {
						projectNameStr=projectNameStr.substring(0,projectNameStr.length()-1);
					}
					PROJECT_STR=PROJECT_STR+"{ name:'"+ CITY_NAME+"', value: "+countValue+", item:["+projectNameStr+"]},";

				}

			}
		}

		mv.addObject("PROJECT_STR", PROJECT_STR);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去通知详细页面
	 */
	@RequestMapping(value="/detail.html")
	public ModelAndView detail(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去通知详细页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/detail");
		//查通知数据
		PageData notice =noticeService.findById(pd);
		mv.addObject("notice", notice);

		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去存储中心
	 */
	@RequestMapping(value="/changtaiMsg.html")
	public ModelAndView changtaiMsg(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去存储中心");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/changtaiMsg");
		//查通知数据
		if(!StringUtil.isNullOrEmpty(pd.getString("keywords2"))) {
			pd.put("keywords", pd.getString("keywords2"));
		}
		List<PageData> noticeList =noticeService.listAll(pd);
		mv.addObject("noticeList", noticeList);
		//查所有文件分类
		List<PageData> filetypeList=filetypeService.listAll(pd);
		mv.addObject("filetypeList", filetypeList);
		//查第一个分类
		if(pd.getString("FILETYPE_ID")==null) {
			if(filetypeList!=null) {
				PageData filetype=filetypeList.get(0);
				pd.put("FILETYPE_ID", filetype.getString("FILETYPE_ID"));
			}
		}
		//查第一个分类的文件
		if(!StringUtil.isNullOrEmpty(pd.getString("keywords1"))) {
			pd.put("keywords", pd.getString("keywords1"));
		}else {
			pd.put("keywords", "");
		}
		List<PageData> fileList=fileService.listAll(pd);
		mv.addObject("fileList", fileList);

		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去应急响应页面
	 */
	@RequestMapping(value="/yingji.html")
	public ModelAndView yingji(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去应急响应页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//pd = this.getPageData();
		mv.setViewName("frontend/yingji");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}

		//查海控应急响应级别
		if(typhoon!=null) {
			pd.put("ANSWER_COMPANY", "海南控股");
			pd.put("lastStart", DateUtil.getTime());
			PageData answer =answerService.findByName(pd);
			mv.addObject("answer", answer);
		}
		//查I级响应公司
		if(typhoon!=null) {
			pd.put("ANSWER_LEVEL", WebConstant.ANSWER_LEVEL_1);
			pd.put("lastStart", DateUtil.getTime());
			List<PageData> answerList1 =answerService.listAll(pd);
			mv.addObject("answerList1", answerList1);
		}
		//查II级响应公司
		if(typhoon!=null) {
			pd.put("ANSWER_LEVEL", WebConstant.ANSWER_LEVEL_2);
			pd.put("lastStart", DateUtil.getTime());
			List<PageData> answerList2 =answerService.listAll(pd);
			mv.addObject("answerList2", answerList2);
		}
		//查III级响应公司
		if(typhoon!=null) {
			pd.put("ANSWER_LEVEL", WebConstant.ANSWER_LEVEL_3);
			pd.put("lastStart", DateUtil.getTime());
			List<PageData> answerList3 =answerService.listAll(pd);
			mv.addObject("answerList3", answerList3);
		}
		//查IV级响应公司
		if(typhoon!=null) {
			pd.put("ANSWER_LEVEL", WebConstant.ANSWER_LEVEL_4);
			pd.put("lastStart", DateUtil.getTime());
			List<PageData> answerList4 =answerService.listAll(pd);
			mv.addObject("answerList4", answerList4);
		}
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去应急响应页面-应急防讯
	 */
	@RequestMapping(value="/yingji_fangxun.html")
	public ModelAndView yingji_fangxun(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去应急响应页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//pd = this.getPageData();
		mv.setViewName("frontend/yingji_fangxun");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}
		List<PageData> riskList =riskService.listAll(pd);
		mv.addObject("riskList", riskList);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去应急响应页面-突发事件
	 */
	@RequestMapping(value="/yingji_tufa.html")
	public ModelAndView yingji_tufa(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去应急响应页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//pd = this.getPageData();
		mv.setViewName("frontend/yingji_tufa");
		//查当前台风
		PageData typhoon =typhoonService.findByNow(pd);
		mv.addObject("typhoon", typhoon);
		if(typhoon!=null) {
			pd.put("TYPHOON_ID", typhoon.getString("TYPHOON_ID"));
		}
		List<PageData> burstList =burstService.listAll(pd);
		mv.addObject("burstList", burstList);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去视频播放
	 */
	@RequestMapping(value="/video.html")
	public ModelAndView video(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去通知详细页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/detail");
		//查视频数据
		List<Monitors> monitorsMap=new ArrayList<Monitors>();
		List<PageData> monitortypeList =monitortypeService.listAll(pd);
		if(monitortypeList!=null) {
			for(PageData monitortype:monitortypeList) {
				Monitors monitors=new Monitors();
				monitors.setMONITORTYPE_NAME(monitortype.getString("MONITORTYPE_NAME"));
				String MONITORTYPE_ID=monitortype.getString("MONITORTYPE_ID");
				pd.put("MONITORTYPE_ID", MONITORTYPE_ID);
				List<PageData> monitorList=monitorService.listAll(pd);
				if(monitorList!=null) {
					List<Monitor> mONITOR_LIST=new ArrayList<Monitor>();
					for(PageData item:monitorList) {
						Monitor monitor=new Monitor();
						monitor.setMONITOR_NAME(item.getString("MONITOR_NAME"));
						monitor.setMONITOR_URL(item.getString("MONITOR_URL"));
						mONITOR_LIST.add(monitor);
					}
					monitors.setMONITOR_LIST(mONITOR_LIST);
				}
				monitorsMap.add(monitors);
			}
		}
		mv.addObject("monitorsMap", monitorsMap);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去视频播放
	 */
	@RequestMapping(value="/play.html")
	public ModelAndView play(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去存储中心");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/playVideo");
		PageData monitor =monitorService.findById(pd);
		String MONITORTYPE_CODE="";
		if(monitor!=null) {
			String MONITOR_NAME=monitor.getString("MONITOR_NAME");
			String MONITOR_URL=monitor.getString("MONITOR_URL");
			pd.put("URL", MONITOR_URL);
			pd.put("CODE", monitor.getString("MONITOR_CODE"));
			MONITORTYPE_CODE=monitor.getString("MONITORTYPE_CODE");
			//能源
			if(WebConstant.SYS_1001.equals(MONITORTYPE_CODE)) {
				// mv.setViewName("frontend/playVideo"); 以前的视频播放地址，后改用萤石云

				if(MONITOR_URL.contains("ezopen")) {
					// 新的萤石云方式播放

					//取动态莹石云的accessToken
					String token_url="https://open.ys7.com/api/lapp/token/get";
					String param="appKey=ae0249ea4d324b60b3cb6bed4df3712b&appSecret=39034312644bc68e8e2d9b29991e7f59";

					if (MONITOR_NAME.indexOf("牛路岭") != -1) {
						// 牛路岭的萤石云监控是另外的参数
						param="appKey=4c96264b599448b99845dca68354376a&appSecret=f94798198bbafb3c1913f73b0ce6a259";
					}

					String tokenStr=HttpRequest.sendPost(token_url, param);
					if(!StringUtil.isNullOrEmpty(tokenStr)) {
						JSONObject jsonObject = JSONObject.fromObject(tokenStr);
						JSONObject data = jsonObject.getJSONObject("data");
						String accessToken=data.getString("accessToken");
						accessToken="accessToken="+accessToken;
						String URL1=pd.getString("URL");
						String[] urlList= URL1.split("&");
						String URL="";
						for(int i=0; i<urlList.length;i++) {
							if(urlList[i].contains("accessToken")) {
								urlList[i]=accessToken;
							}
						}
						for(String str:urlList) {
							URL=URL+str+"&";
						}
						if(!StringUtil.isNullOrEmpty(URL)) {
							URL=URL.substring(0,URL.length()-1);
						}
						pd.put("URL", URL);
					}

					mv.setViewName("frontend/playVideo2");
				} else {
					// 以前的播放地址
					mv.setViewName("frontend/playVideo");
				}
			}
			//业置
			if(WebConstant.SYS_1002.equals(MONITORTYPE_CODE)) {
				mv.setViewName("frontend/demo_for_iframe");
			}
			//水电
			if(WebConstant.SYS_1003.equals(MONITORTYPE_CODE)) {
				//取动态莹石云的accessToken
				String token_url="https://open.ys7.com/api/lapp/token/get";
				String param="appKey=d0a2e366fe064d8e83c5ea394635e4eb&appSecret=a4e88667310ee5ca36616532d63128d6";
				if(MONITOR_NAME.contains("迈湾")) {
					param="appKey=d0a2e366fe064d8e83c5ea394635e4eb&appSecret=a4e88667310ee5ca36616532d63128d6";
				}
				if(MONITOR_NAME.contains("天角潭")) {
					param="appKey=79b2640e291f4ab2ad1964e01bb15659&appSecret=69c5e06d9f30a7d4fe07b09b43238f6d";
				}
				if(MONITOR_NAME.contains("琼西北一标")) {
					param="appKey=22f1a03b19354b33a9b1786ccf3329d5&appSecret=ee9c4992ea6428b175fb190f3e6213ab";
				}
				if(MONITOR_NAME.contains("琼西北二标")) {
					param="appKey=ba1388e7e1e240ea98d7fa48693a1cf2&appSecret=fb5f99295488f1920cd951a97f278070";
				}
				if(MONITOR_NAME.contains("琼西北三标")) {
					param="appKey=ce9504f980924a17b808468faa8c9ba2&appSecret=c214731ffb5f514c6953c62a9ddae286";
				}
				String tokenStr=HttpRequest.sendPost(token_url, param);
				if(!StringUtil.isNullOrEmpty(tokenStr)) {
					JSONObject jsonObject = JSONObject.fromObject(tokenStr);
					JSONObject data = jsonObject.getJSONObject("data");
					String accessToken=data.getString("accessToken");
					accessToken="accessToken="+accessToken;
					String URL1=pd.getString("URL");
					String[] urlList= URL1.split("&");
					String URL="";
					for(int i=0; i<urlList.length;i++) {
						if(urlList[i].contains("accessToken")) {
							urlList[i]=accessToken;
						}
					}
					for(String str:urlList) {
						URL=URL+str+"&";
					}
					if(!StringUtil.isNullOrEmpty(URL)) {
						URL=URL.substring(0,URL.length()-1);
					}
					pd.put("URL", URL);
				}

				mv.setViewName("frontend/playVideo2");
			}
		}
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去关于我们页面
	 */
	@RequestMapping(value="/about2.html")
	public void about(HttpServletRequest request,HttpServletResponse resp,Page page) throws Exception{
		logBefore(logger, "去关于我们页面");
		resp.sendRedirect("https://153.0.150.42:4430/portal/ui/index");
	}
	/**
	 * 去关于我们页面
	 */
	@RequestMapping(value="/about1.html")
	public ModelAndView about(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去关于我们页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/about");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去Demo1
	 */
	@RequestMapping(value="/demo_for_iframe.html")
	public ModelAndView demoIframe1(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去关于我们页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/demo_for_iframe");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去Demo2
	 */
	@RequestMapping(value="/demo_embedded_for_iframe.html")
	public ModelAndView demoIframe2(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去关于我们页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData monitor =monitorService.findById(pd);
		if(monitor!=null) {
			pd.put("CODE", monitor.getString("MONITOR_CODE"));
		}
		mv.setViewName("frontend/demo_embedded_for_iframe");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去Demo2
	 */
	@RequestMapping(value="/demo_embedded_for_iframe1.html")
	public ModelAndView demoIframe4(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去关于我们页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData monitor =monitorService.findById(pd);
		if(monitor!=null) {
			pd.put("CODE", monitor.getString("MONITOR_CODE"));
		}
		mv.setViewName("frontend/playMax/demo_embedded_for_iframe");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去Demo3
	 */
	@RequestMapping(value="/playVideohk.html")
	public ModelAndView demoIframe3(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去关于我们页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/playVideohk");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去视频播放
	 */
	@RequestMapping(value="/playMax.html")
	public ModelAndView playMax(HttpServletRequest request,Page page) throws Exception{
		logBefore(logger, "去存储中心");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("frontend/playVideo");
		PageData monitor =monitorService.findById(pd);
		String MONITORTYPE_CODE="";
		if(monitor!=null) {
			String MONITOR_NAME=monitor.getString("MONITOR_NAME");
			pd.put("URL", monitor.getString("MONITOR_URL"));
			pd.put("CODE", monitor.getString("MONITOR_CODE"));
			MONITORTYPE_CODE=monitor.getString("MONITORTYPE_CODE");
			//能源
			if(WebConstant.SYS_1001.equals(MONITORTYPE_CODE)) {
				mv.setViewName("frontend/playMax/playVideo");
			}
			//置业
			if(WebConstant.SYS_1002.equals(MONITORTYPE_CODE)) {
				mv.setViewName("frontend/playMax/demo_for_iframe");
			}
			//水电
			if(WebConstant.SYS_1003.equals(MONITORTYPE_CODE)) {
				//取动态莹石云的accessToken
				String token_url="https://open.ys7.com/api/lapp/token/get";
				String param="appKey=d0a2e366fe064d8e83c5ea394635e4eb&appSecret=a4e88667310ee5ca36616532d63128d6";
				if(MONITOR_NAME.contains("迈湾")) {
					param="appKey=d0a2e366fe064d8e83c5ea394635e4eb&appSecret=a4e88667310ee5ca36616532d63128d6";
				}
				if(MONITOR_NAME.contains("天角潭")) {
					param="appKey=79b2640e291f4ab2ad1964e01bb15659&appSecret=69c5e06d9f30a7d4fe07b09b43238f6d";
				}
				if(MONITOR_NAME.contains("琼西北一标")) {
					param="appKey=22f1a03b19354b33a9b1786ccf3329d5&appSecret=ee9c4992ea6428b175fb190f3e6213ab";
				}
				if(MONITOR_NAME.contains("琼西北二标")) {
					param="appKey=ba1388e7e1e240ea98d7fa48693a1cf2&appSecret=fb5f99295488f1920cd951a97f278070";
				}
				if(MONITOR_NAME.contains("琼西北三标")) {
					param="appKey=ce9504f980924a17b808468faa8c9ba2&appSecret=c214731ffb5f514c6953c62a9ddae286";
				}
				String tokenStr=HttpRequest.sendPost(token_url, param);
				if(!StringUtil.isNullOrEmpty(tokenStr)) {
					JSONObject jsonObject = JSONObject.fromObject(tokenStr);
					JSONObject data = jsonObject.getJSONObject("data");
					String accessToken=data.getString("accessToken");
					accessToken="accessToken="+accessToken;
					String URL1=pd.getString("URL");
					String[] urlList= URL1.split("&");
					String URL="";
					for(int i=0; i<urlList.length;i++) {
						if(urlList[i].contains("accessToken")) {
							urlList[i]=accessToken;
						}
					}
					for(String str:urlList) {
						URL=URL+str+"&";
					}
					if(!StringUtil.isNullOrEmpty(URL)) {
						URL=URL.substring(0,URL.length()-1);
					}
					pd.put("URL", URL);
				}

				mv.setViewName("frontend/playMax/playVideo2");
			}
		}
		mv.addObject("pd", pd);
		return mv;
	}

//	public PageData getFengXianByDate(){
//		PageData pd = new PageData();
//		Calendar calendar = Calendar.getInstance();
//
//
//
//		int year = calendar.get(Calendar.YEAR);
//		// 获取当前月份（月份从0开始，所以需要加1）
//		int month = calendar.get(Calendar.MONTH)+1;
//		int quarter = ((month-1)/3)+1;
//
//		//获取季度第一天和最后一天
//		Date startDayOfQuarter = FrontendController.getStartDayOfQuarter(2023,4);
//		Date lastDayOfQuarter = FrontendController.getLastDayOfQuarter(2023,4);
//
//		//格式化时期
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String startQuarter = dateFormat.format(startDayOfQuarter);
//		String endQuarter = dateFormat.format(lastDayOfQuarter);
//		return pd;
//	}



	//

	//获取季度第一天
	public static Date getStartDayOfQuarter(int year, int quarter) {

		int startMonth = (quarter - 1) * 3;

		// 根据月获取开始时间
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, startMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	//获取季度最后一天
	public static Date getLastDayOfQuarter(int year, int quarter) {

		int lastMonth = quarter * 3 - 1;

		// 根据月获取开始时间
		// 根据月获取开始时间
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, lastMonth);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();

	}

	//获取前n月的日期
	public static String getPrevMonthDate(Date date,int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - n);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	//首页驾驶舱顶部信息
	@RequestMapping(value = "/indexTopData")
	@ResponseBody
	public PageData indexTopData() throws Exception {
		PageData pd = this.getPageData();
		pd.put("ISORG",0);

		Date date = new Date();
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String now = getFirstDayOfYear(year);
		String prev = getFirstDayOfYear(year-1);

		pd.put("date",now);
		PageData projectList = morgService.findCount(pd);
		pd.put("NO_RECTIFY_STAGE",1);
		PageData rectifyInfoList = rectifyinfoService.findCount(pd);
		PageData fengXianList = m_fengxianService.findCount(pd);

		pd.put("date",prev);
		PageData prevProjectList = morgService.findCount(pd);
		PageData prevRectifyInfoList = rectifyinfoService.findCount(pd);
		PageData prevFengXianList = m_fengxianService.findCount(pd);

		pd.put("projectList",projectList);
		pd.put("rectifyInfoList",rectifyInfoList);
		pd.put("fengXianList",fengXianList);
		pd.put("prevProjectList",prevProjectList);
		pd.put("prevRectifyInfoList",prevRectifyInfoList);
		pd.put("prevFengXianList",prevFengXianList);


		return pd;
	}

	@RequestMapping(value = "/listProjectByArea")
	@ResponseBody
	public List<PageData> listProjectByArea() throws Exception {
		PageData pd = this.getPageData();
		//传到前端的数据
		List<PageData> resultList = new ArrayList<>();

		//获取海南地区
		String hainanParam = "2ba8e6d0fd944983aa19b781c6b53477";
		List<Dictionaries> areaList = dictionariesService.listSubDictByParentId(hainanParam);
		//填充子list
		for(Dictionaries dictionaries : areaList){
			String parentId = dictionaries.getDICTIONARIES_ID();
			PageData result = new PageData();
			PageData param = new PageData();
			param.put("PROJECT_AREA",parentId);
			//属于一级区域的项目，后面二级区域的项目也加入到这个list中
			List<PageData> projectList = morgService.listProjectByArea(param);

			//二级区域，如果没有二级区域那么subList为0
			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
			//如果有二级区域就都查出来然后加到一级区域
			if(subList != null && subList.size() != 0){
				for(Dictionaries subdictionaries : subList){
					PageData subParam = new PageData();
					subParam.put("PROJECT_AREA",subdictionaries.getDICTIONARIES_ID());
					List<PageData> subProjectList = morgService.listProjectByArea(subParam);
					//如果该二级区域有项目就加入到一级区域的项目list
					if(subProjectList != null && subProjectList.size() != 0){
						projectList.addAll(subProjectList);
					}

				}
			}
			dictionaries.setSubDict(subList);

			//全部二级区域找完后将一级区域的list和名字加入pagedata
			result.put("name",dictionaries.getBZ());
			result.put("list",projectList);
			resultList.add(result);
		}



		return resultList;
	}

	@RequestMapping(value = "/mapTotal")
	@ResponseBody
	public PageData mapTotal() throws Exception {
		PageData pd = this.getPageData();
		//传到前端的总数的数据
		pd.put("ISORG",0);
		List<PageData> projectList = morgService.listAll(pd);
		//不包括未提交的
		pd.put("NO_RECTIFY_STAGE",1);
		List<PageData> rectifyList = rectifyinfoService.listAll(pd);
		List<PageData> fengXianList = m_fengxianService.listAll(pd);

		pd.put("projectTotal",projectList.size());
		pd.put("rectifyTotal",rectifyList.size());
		pd.put("fengXianTotal",fengXianList.size());

		return pd;
	}

	/*按地区划分统计风险点
	 * */
	@RequestMapping(value="/getFengXianByArea")
	@ResponseBody
	public List<PageData> getFengXianByArea() throws Exception{

		//获取海南地区
		String hainanParam = "2ba8e6d0fd944983aa19b781c6b53477";
		List<Dictionaries> areaList = dictionariesService.listSubDictByParentId(hainanParam);
		//填充子list
		for(Dictionaries dictionaries : areaList){
			String parentId = dictionaries.getDICTIONARIES_ID();
			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
			dictionaries.setSubDict(subList);
		}

		//先获取一级区域的数据
		List<PageData> list = new ArrayList<>();
		for(Dictionaries dictionaries : areaList){
			PageData pd = new PageData();
			pd.put("DICTIONARIES_ID",dictionaries.getDICTIONARIES_ID());
			pd.put("NAME",dictionaries.getNAME());
			PageData subList = m_fengxianService.listFengXianByArea(pd);
			if(subList != null){
				pd.put("ALL_RISK",subList.get("全部风险点"));
				pd.put("MAJOR_RISK",subList.get("重大风险"));
				pd.put("MORE_RISK",subList.get("较大风险"));
				pd.put("NORMAL_RISK",subList.get("一般风险"));
				pd.put("LOW_RISK",subList.get("低风险"));
			}
			else{
				pd.put("ALL_RISK",0);
				pd.put("MAJOR_RISK",0);
				pd.put("MORE_RISK",0);
				pd.put("NORMAL_RISK",0);
				pd.put("LOW_RISK",0);
			}

//			pd.put("data",subList);

			//二级区域
			List<Dictionaries> subDictionaries =  dictionaries.getSubDict();
			List<PageData> secondAreaList = new ArrayList<>();
			//统计二级区域的风险点数量，显示在点击区域地图展开的地方
			for(Dictionaries secondDictionaries : subDictionaries){
				PageData param = new PageData();
				param.put("DICTIONARIES_ID",secondDictionaries.getDICTIONARIES_ID());
				param.put("NAME",secondDictionaries.getNAME());
				PageData secondSubList = m_fengxianService.listFengXianByArea(param);
				if(secondSubList != null){
					//先转成long再转成string
					if(secondSubList.get("全部风险点") != null){
						pd.put("ALL_RISK",Long.parseLong(String.valueOf(pd.get("ALL_RISK")))+Long.parseLong(String.valueOf(secondSubList.get("全部风险点"))) );
					}
					if(secondSubList.get("重大风险") != null){
						pd.put("MAJOR_RISK",Long.parseLong(String.valueOf(pd.get("MAJOR_RISK")))+Long.parseLong(String.valueOf(secondSubList.get("重大风险"))) );
					}
					if(secondSubList.get("较大风险") != null){
						pd.put("MORE_RISK",Long.parseLong(String.valueOf(pd.get("MORE_RISK")))+Long.parseLong(String.valueOf(secondSubList.get("较大风险"))) );
					}
					if(secondSubList.get("一般风险") != null){
						pd.put("NORMAL_RISK",Long.parseLong(String.valueOf(pd.get("NORMAL_RISK")))+Long.parseLong(String.valueOf(secondSubList.get("一般风险"))) );
					}
					if(secondSubList.get("低风险") != null){
						pd.put("LOW_RISK",Long.parseLong(String.valueOf(pd.get("LOW_RISK")))+Long.parseLong(String.valueOf(secondSubList.get("低风险"))) );
					}
				}

				secondAreaList.add(secondSubList);
			}
			pd.put("sublist",secondAreaList);
			list.add(pd);
		}

		return list;
	}

	/*按地区划分统计风险点
	 * */
	@RequestMapping(value="/getRectifyByArea")
	@ResponseBody
	public List<PageData> getRectifyByArea() throws Exception{
		//获取海南地区
		String hainanParam = "2ba8e6d0fd944983aa19b781c6b53477";
		List<Dictionaries> areaList = dictionariesService.listSubDictByParentId(hainanParam);
		//填充子list
		for(Dictionaries dictionaries : areaList){
			String parentId = dictionaries.getDICTIONARIES_ID();
			List<Dictionaries> subList = dictionariesService.listSubDictByParentId(parentId);
			dictionaries.setSubDict(subList);
		}

		//先获取一级区域的数据
		List<PageData> list = new ArrayList<>();
		for(Dictionaries dictionaries : areaList){
			PageData pd = new PageData();
			pd.put("DICTIONARIES_ID",dictionaries.getDICTIONARIES_ID());
			pd.put("NAME",dictionaries.getNAME());
			PageData subList = rectifyinfoService.tongjiRectifyMap(pd);
			if(subList != null){
				pd.put("value",subList.get("value"));
				pd.put("MORE",subList.get("MORE"));
				pd.put("NORMAL",subList.get("NORMAL"));
			}
			else{
				pd.put("value",0);
				pd.put("MORE",0);
				pd.put("NORMAL",0);
			}

//			pd.put("data",subList);

			//二级区域
			List<Dictionaries> subDictionaries =  dictionaries.getSubDict();
			List<PageData> secondAreaList = new ArrayList<>();
			//统计二级区域的风险点数量，显示在点击区域地图展开的地方
			for(Dictionaries secondDictionaries : subDictionaries){
				PageData param = new PageData();
				param.put("DICTIONARIES_ID",secondDictionaries.getDICTIONARIES_ID());
				param.put("NAME",secondDictionaries.getNAME());
				PageData secondSubList = rectifyinfoService.tongjiRectifyMap(param);
				if(secondSubList != null){
					//先转成long再转成string
					if(secondSubList.get("MORE") != null){
						pd.put("MORE",Long.parseLong(String.valueOf(pd.get("MORE")))+Long.parseLong(String.valueOf(secondSubList.get("MORE"))) );
					}
					if(secondSubList.get("NORMAL") != null){
						pd.put("NORMAL",Long.parseLong(String.valueOf(pd.get("NORMAL")))+Long.parseLong(String.valueOf(secondSubList.get("NORMAL"))) );
					}
					if(secondSubList.get("value") != null){
						pd.put("value",Long.parseLong(String.valueOf(pd.get("value")))+Long.parseLong(String.valueOf(secondSubList.get("value"))) );
					}
				}

				secondAreaList.add(secondSubList);
			}
			pd.put("sublist",secondAreaList);
			list.add(pd);
		}

		return list;
	}

	@RequestMapping(value="enterProject")
	public ModelAndView enterProject() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		/*传入org_id
		获取指定项目的隐患和风险的列表
		* */
		PageData fengXianParam = new PageData();
		fengXianParam.put("THIRD_UNIT",pd.getString("ORG_ID"));
		List<PageData> fengXianList = m_fengxianService.listAll(fengXianParam);

		//传入的是org_id,这里要改成project_ID
		PageData monitorParam = new PageData();
		monitorParam.put("PROJECT_ID",pd.getString("ORG_ID"));
		List<PageData> monitorList = m_monitor_projectService.listAll(monitorParam);

		//传入的是org_id,这里要改成project_name
		PageData rectifyParam = new PageData();
		rectifyParam.put("PROJECT_NAME",pd.getString("ORG_ID"));
		List<PageData> rectifyInfoList = rectifyinfoService.listAll(rectifyParam);
//		String[][] str1 = rectifyInfoList.toArray(new String[rectifyInfoList.size()][rectifyInfoList.get(0).size()]);

		String hainanParam = "1";
		List<Dictionaries> areaList = dictionariesService.listAllForIdSelectTree(hainanParam);

		//危险源
		PageData hazardParam = new PageData();
		List<PageData> hazardList = m_fengxianService.listAll(hazardParam);
		//事故类型
		Map<String, String> accidentTypeMap = dictionariesService.listChildrenByEN(WebConstant.ACCIDENT_TYPE, true);
		List<Object> accidentTypeJsonList =mapToJson(accidentTypeMap);
		//风险等级
		Map<String, String> riskLevelMap = dictionariesService.listChildrenByEN(WebConstant.RISK_LEVEL, true);
		List<Object> riskLevelJsonList =mapToJson(riskLevelMap);

		pd = morgService.findById(pd);

		mv.setViewName("frontend/enterProject");
		mv.addObject("pd",pd);
		mv.addObject("rectifyInfoList",rectifyInfoList);
		mv.addObject("fengXianList",fengXianList);
		mv.addObject("monitorList",monitorList);
		mv.addObject("classifyMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_CLASSIFY, true));
		mv.addObject("levelMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_LEVEL, true));
		mv.addObject("factorMap", dictionariesService.listChildrenByEN(WebConstant.HIDDEN_DANGER_FACTOR, true));
		mv.addObject("hazardList", hazardList);
		mv.addObject("areaList", areaList);
		mv.addObject("accidentTypeMap", accidentTypeJsonList);
		mv.addObject("riskLevelMap", riskLevelJsonList);
		//组织机构和项目的信息
		PageData param = new PageData();
		param.put("ISORG",1);
		mv.addObject("orgList", morgService.listAll(param));
		param.put("ISORG",0);
		mv.addObject("projectList", morgService.listAll(param));
		return mv;
	}

	//项目二级页面根据项目id获取相关水库然后获取每个水库的后5条水情图
	@RequestMapping(value="waterProject")
	@ResponseBody
	public List<List<PageData>> waterProject() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		//根据项目找到对应的水库
		List<PageData> waterProjectList = m_water_projectService.listForName(pd);

		//存放不同水库水情数据的list
		List<List<PageData>> waterResultList = new ArrayList<>();
		//先放入水库信息
		waterResultList.add(waterProjectList);
		//根据关联的水库数量进行循环
		for(PageData reservoir : waterProjectList){

			PageData waterParam = new PageData();
			waterParam.put("RESERVOIR_ID",reservoir.getString("RESERVOIR_ID"));
			List<PageData> tempList = waterService.listAll(waterParam);

			waterResultList.add(tempList);
		}

		return waterResultList;
	}


	@RequestMapping(value = "getFirstDayOfYear")
	public String getFirstDayOfYear(int year){

		// 创建一个Calendar对象，并设置为当前年的1月1日
		Calendar firstDayOfYear = Calendar.getInstance();
		firstDayOfYear.set(year, Calendar.JANUARY, 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(firstDayOfYear.getTime());

		return dateStr;
	}

	/**封装隐患整改map转json对象的函数
	 * @param
	 * @throws Exception
	 */
	public List<Object> mapToJson(Map<String, String> rectifyMap){
		List<Picker> rectify =new ArrayList<>();
		List<Object> PickerJsonList =new ArrayList<>();
		for(Map.Entry<String,String> entry:rectifyMap.entrySet()){
			Picker picker = new Picker();			//自定义的类，只有编码和名字两个属性
			picker.setBIANMA(entry.getKey());		//分别拿到编码和名字
			picker.setNAME(entry.getValue());
			Object PickerJson = com.alibaba.fastjson.JSONObject.toJSON(picker);//转化为json对象存入
			rectify.add(picker);
			PickerJsonList.add(PickerJson);
		};
		return PickerJsonList;
	}


	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
