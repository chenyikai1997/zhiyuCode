<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>应急信息</title>
  <link rel="stylesheet" href="<%=basePath%>/frontend/css/reset.css">
  <link rel="stylesheet" href="<%=basePath%>/frontend/css/index.css">
</head>
<body>
<!-- 1. 顶部 -->
<header>
  <div class="header-title">海南控股重点监督风险管控系统</div>
</header>

<!-- 2. 内容 -->
<div class="container">
  <!-- 左侧公司列表 -->
  <div class="left-content">
    <div class="title">二级公司风险点统计</div>
    <div class="company-list">
<%--      <div class="company-item">--%>
<%--        <img class="logo" src="<%=basePath%>/frontend/images/company-logo.png" alt="">--%>
<%--        <div class="detail" ID="secondUnit">--%>
<%--          <div class="name">海南机场集团有限公司</div>--%>
<%--          <div class="point-content">--%>
<%--            <div id="great" class="point">--%>
<%--              <i></i>--%>
<%--              <span class="num">162</span>--%>
<%--            </div>--%>
<%--            <div id="larger" class="point">--%>
<%--              <i></i>--%>
<%--              <span class="num">233</span>--%>
<%--            </div>--%>
<%--            <div id="common" class="point">--%>
<%--              <i></i>--%>
<%--              <span class="num">412</span>--%>
<%--            </div>--%>
<%--            <div id="low" class="point">--%>
<%--              <i></i>--%>
<%--              <span class="num">312</span>--%>
<%--            </div>--%>
<%--          </div>--%>
<%--        </div>--%>
<%--      </div>--%>

    </div>
    <div class="legend">
      <div id="great" class="point">
        <i></i>
        <span>重大风险</span>
      </div>
      <div id="larger" class="point">
        <i></i>
        <span>较大风险</span>
      </div>
      <div id="common" class="point">
        <i></i>
        <span>一般风险</span>
      </div>
      <div id="low" class="point">
        <i></i>
        <span>低风险</span>
      </div>
    </div>
  </div>
  <!-- 中间地图模块 -->
  <div class="center-content">
    <div class="fengxian-num">
      <div class="all-num">
        风险总数
        <span id="counter"></span>
      </div>
      <div class="zhongda-num">
        重大风险
        <span id="zhongda"></span>
      </div>
      <div class="jiaoda-num">
        较大风险
        <span id="jiaoda"></span>
      </div>
    </div>
    <div id="map" class="map"></div>
  </div>
  <!-- 右侧图表模块 -->
  <div class="right-content">
    <!-- 饼状图 -->
    <div class="pie-chart">
      <div class="title">风险等级</div>
      <div id="pieChart"></div>
    </div>
    <!-- 线状图 -->
    <div class="line-chart">
      <div class="title">风险总数趋势</div>
      <div id="lineChart"></div>
    </div>
    <!-- 柱状图 -->
    <div class="bar-chart">
      <div class="title">可能导致的事故类型</div>
      <div id="barChart"></div>
    </div>
  </div>
</div>

<script src="<%=basePath%>/frontend/js/jquery.js"></script>
<script src="<%=basePath%>/frontend/js/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>/frontend/js/flexible.js"></script>
<script src="<%=basePath%>/frontend/js/echarts.min.js"></script>
<script src="<%=basePath%>/frontend/js/echarts-gl.min.js"></script>
<script src="<%=basePath%>/frontend/js/echarts-wordcloud.min.js"></script>
<%--<script src="<%=basePath%>/frontend/js/fengXianCharts.js"></script>--%>
<script>
  $(function (){
    console.log("测试2314123")
  })

  $(document).ready(function () {
    peiChart();
    mapChart();
    barCharts();
    lineChart();
    secondUnit();

    function peiChart(){
      //先请求数据
      var peiData;
      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getTongJiFengXianLevel",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          peiData = res;
        },
      })

      var zdData;
      if(peiData[0] != null){
        zdData = peiData[0].total;
      }
      var jdData;
      if(peiData[1] != null){
        jdData = peiData[1].total;
      }
      var ybData;
      if(peiData[2] != null){
        ybData = peiData[2].total;
      }
      var dfxData;
      if(peiData[3] != null){
        dfxData = peiData[3].total;
      }


      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#pieChart'))
      // 2. 指定配置项和数据
      const colorList1 = [ 'rgb(255,0,0,1.000)','', 'rgb(255,192,0,1.000)','', 'rgb(255,255,0,1.000)','', 'rgb(0,176,240,1.000)',]
      const colorList2 =  [ 'rgb(255,0,0, .5)','', 'rgb(255,192,0, .5)','', 'rgb(255,255,0, .5)', '','rgb(0,176,240,.5)',]
      let total = 0
      let dataList = []
      const moduleContent = {
        '重大风险': zdData, '较大风险': jdData, '一般风险': ybData ,'低风险': dfxData
      }
      let sum = 0
      const chartdata = []
      for (const i in moduleContent) {
        chartdata.push({
          name: i,
          value: moduleContent[i] || 1
        })
        sum += Number(moduleContent[i] || 0)
      }
      total = sum
      dataList = chartdata
      const data1 = []
      chartdata.forEach((item) => {
        const _item = { ...item }
        if (!_item.value) {
          _item.value = sum / 100
        }
        data1.push(_item, {
          name: '',
          value: sum / 100,
          label: { show: false },
          itemStyle: {
            color: 'transparent'
          }
        })
      })

      let option = {
        legend: {
          left: 'left',
          top: 'center',
          width: '5%',
          textStyle:{
            color: '#fff'
          }
        },
        backgroundColor: '#061d6e84',
        title: {
          text: total,
          left: 'center',
          top: '35%',
          itemGap: 10,
          textStyle: {
            color: '#fff',
            fontSize: '35',
            fontWeight: 500
          },
          subtext: '共计',
          subtextStyle: {
            color: 'rgba(255,255,255,0.5)',
            fontSize: '20',
            fontWeight: 600
          }
        },
        tooltip: {
          trigger: 'item',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter(params) {
            if(params.data.name){
              return params.name + ':' + params.data.value
            }
          },
          // formatter: "{b}: {c} "+" | "+"{d}%",
          textStyle: {
            fontSize: 18,
            color: 'rgba(255,255,255,0.8)'
          },
          borderColor: 'rgba(255,255,255,0.9)',
          backgroundColor: 'rgba(255,255,255,0.5)',
          extraCssText: 'box-shadow: 2px 2px 4px 0px rgba(255,255,255,0.5);'
        },
        series: [
          {
            type: 'pie',
            radius: ['50%', '72%'],
            center: ['50%', '50%'],
            minAngle: 1,
            labelLine: {
              show: false
            },
            label: {
              show: false,
            },
            itemStyle: {
              normal: {
                color: function (params) {
                  return colorList1[params.dataIndex]
                }
              }
            },
            data: data1,
            z: 666
          },
          {
            type: 'pie',
            radius: ['80%', '85%'],
            center: ['50%', '50%'],
            hoverAnimation: false,
            minAngle: 1,
            emphasis: { scale: false },
            label: {
              show: false
            },
            itemStyle: {
              normal: {
                color: function (params) {
                  return colorList2[params.dataIndex]
                }
              }
            },
            data: data1,
            z: 1
          },
        ]
      }

      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });

    }

    // 地图
    function mapChart(){

      var myChart = echarts.init(document.getElementById('map'));

      var allData;

      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getFengXianByArea",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          allData = res;
          console.log(allData)
        },
      })

      // 使用异步请求获取海南省的地图数据
      $.get('<%=basePath%>/frontend/hainan.json', function (hainanJson) {
        // 注册地图数据
        echarts.registerMap('hainan', hainanJson);

        // 设置地图的配置项和数据
        var option = {
          title: {
            text: '',
            left: 'center'
          },
          // 悬浮框
          tooltip: {
            padding: 0,
            position: [10, 90],
            // 鼠标是否可以进入悬浮框
            enterable: true,
            // 触发方式 mousemove, click, none, mousemove|click
            triggerOn: 'click',
            // item 图形触发， axis 坐标轴触发， none 不触发
            trigger: 'item',
            // 浮层隐藏的延迟
            hideDelay: 100,
            // 背景色
            backgroundColor: 'rgba(0,60,255,.7)',
            formatter: function (params) {
              for(var i = 0;i<allData.length;i++){
                if(params.name== allData[i].NAME && params.name=='海口市'){
                  return `
                                        <div class="tooltip-box">
                                            <div class="title">
                                                <div class="location">${allData[0].NAME}</div>
                                                <div class="total">风险统计<span>${allData[0].tongJi}</span>个</div>
                                            </div>
                                            <div class="detail">
                                                <div class="tab-container" id="typhoonContainer">
                                                    <div class="tab tabActive" data-tab="area1">
                                                        <span>龙华区</span>
                                                    </div>
                                                    <div class="tab" data-tab="area2">
                                                        <span>秀英区</span>
                                                    </div>
                                                    <div class="tab" data-tab="area3">
                                                        <span>琼山区</span>
                                                    </div>
                                                    <div class="tab" data-tab="area4">
                                                        <span>美兰区</span>
                                                    </div>
                                                </div>
                                                <div class="tab-content" id="typhoonContent">
                                                    <div class="content active" id="area1">
                                                        <div class="list">
                                                        <div class="item">
                                                            <p>风险等级</p>
                                                            <div class="point">
                                                                <div class="fengxian">
                                                                    <img src="./images/great-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">重大风险</p>
                                                                        <div id="zd">${allData[0].sublist[0].MAJOR_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                                <div class="fengxian">
                                                                    <img src="./images/larger-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">较大风险</p>
                                                                        <div id="jd">${allData[0].sublist[0].MORE_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                                <div class="fengxian">
                                                                    <img src="./images/common-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">一般风险</p>
                                                                        <div id="yb">${allData[0].sublist[0].NORMAL_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                                <div class="fengxian">
                                                                    <img src="./images/low-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">低风险</p>
                                                                        <div id="dd">${allData[0].sublist[0].LOW_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="content" id="area2">
                                                    <div class="item">
                                                    <p onclick="window.location.href='detail.html'">风险等级</p>
                                                    <div class="point">
                                                        <div class="fengxian">
                                                            <img src="./images/great-icon.png" alt="">
                                                            <div class="num">
                                                                <p>重大风险</p>
                                                                <div id="zd">${allData[0].sublist[1].MAJOR_RISK}个</div>
                                                            </div>
                                                        </div>
                                                        <div class="fengxian">
                                                            <img src="./images/larger-icon.png" alt="">
                                                            <div class="num">
                                                                <p>较大风险</p>
                                                                <div id="jd">${allData[0].sublist[1].MORE_RISK}个</div>
                                                            </div>
                                                        </div>
                                                        <div class="fengxian">
                                                            <img src="./images/common-icon.png" alt="">
                                                            <div class="num">
                                                                <p>一般风险</p>
                                                                <div id="yb">${allData[0].sublist[1].NORMAL_RISK}个</div>
                                                            </div>
                                                        </div>
                                                        <div class="fengxian">
                                                            <img src="./images/low-icon.png" alt="">
                                                            <div class="num">
                                                                <p>低风险</p>
                                                                <div id="dd">${allData[0].sublist[1].LOW_RISK}个</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                </div>
                                                <div class="content" id="area3">
                                                <div class="item">
                                                <p onclick="window.location.href='detail.html'">风险等级</p>
                                                <div class="point">
                                                    <div class="fengxian">
                                                        <img src="./images/great-icon.png" alt="">
                                                        <div class="num">
                                                            <p>重大风险</p>
                                                            <div id="zd">${allData[0].sublist[2].MAJOR_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/larger-icon.png" alt="">
                                                        <div class="num">
                                                            <p>较大风险</p>
                                                            <div id="jd">${allData[0].sublist[2].MORE_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/common-icon.png" alt="">
                                                        <div class="num">
                                                            <p>一般风险</p>
                                                            <div id="yb">${allData[0].sublist[2].NORMAL_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/low-icon.png" alt="">
                                                        <div class="num">
                                                            <p>低风险</p>
                                                            <div id="dd">${allData[0].sublist[2].LOW_RISK}个</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                                </div>
                                                <div class="content" id="area4">
                                                <div class="item">
                                                <p onclick="window.location.href='detail.html'">风险等级</p>
                                                <div class="point">
                                                    <div class="fengxian">
                                                        <img src="./images/great-icon.png" alt="">
                                                        <div class="num">
                                                            <p>重大风险</p>
                                                            <div id="zd">${allData[0].sublist[3].MAJOR_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/larger-icon.png" alt="">
                                                        <div class="num">
                                                            <p>较大风险</p>
                                                            <div id="jd">${allData[0].sublist[3].MORE_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/common-icon.png" alt="">
                                                        <div class="num">
                                                            <p>一般风险</p>
                                                            <div id="yb">${allData[0].sublist[3].NORMAL_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/low-icon.png" alt="">
                                                        <div class="num">
                                                            <p>低风险</p>
                                                            <div id="dd">${allData[0].sublist[3].LOW_RISK}个</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                `
                }
                else if(params.name== allData[i].NAME && params.name=='三亚市'){
                  return `
                                        <div class="tooltip-box">
                                            <div class="title">
                                                <div class="location">${allData[1].NAME}</div>
                                                <div class="total">风险统计<span>${allData[1].tongJi}</span>个</div>
                                            </div>
                                            <div class="detail">
                                                <div class="tab-container" id="typhoonContainer">
                                                    <div class="tab tabActive" data-tab="area1">
                                                        <span>海棠区</span>
                                                    </div>
                                                    <div class="tab" data-tab="area2">
                                                        <span>崖州区</span>
                                                    </div>
                                                    <div class="tab" data-tab="area3">
                                                        <span>吉阳区</span>
                                                    </div>
                                                    <div class="tab" data-tab="area4">
                                                        <span>天涯区</span>
                                                    </div>
                                                </div>
                                                <div class="tab-content" id="typhoonContent">
                                                    <div class="content active" id="area1">
                                                        <div class="list">
                                                        <div class="item">
                                                            <p>风险等级</p>
                                                            <div class="point">
                                                                <div class="fengxian">
                                                                    <img src="./images/great-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">重大风险</p>
                                                                        <div id="zd">${allData[1].sublist[0].MORE_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                                <div class="fengxian">
                                                                    <img src="./images/larger-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">较大风险</p>
                                                                        <div id="jd">${allData[1].sublist[0].MORE_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                                <div class="fengxian">
                                                                    <img src="./images/common-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">一般风险</p>
                                                                        <div id="yb">${allData[1].sublist[0].NORMAL_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                                <div class="fengxian">
                                                                    <img src="./images/low-icon.png" alt="">
                                                                    <div class="num">
                                                                        <p onclick="window.location.href='detail.html'">低风险</p>
                                                                        <div id="dd">${allData[1].sublist[0].LOW_RISK}个</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="content" id="area2">
                                                    <div class="item">
                                                    <p onclick="window.location.href='detail.html'">风险等级</p>
                                                    <div class="point">
                                                        <div class="fengxian">
                                                            <img src="./images/great-icon.png" alt="">
                                                            <div class="num">
                                                                <p>重大风险</p>
                                                                <div id="zd">${allData[1].sublist[1].MAJOR_RISK}个</div>
                                                            </div>
                                                        </div>
                                                        <div class="fengxian">
                                                            <img src="./images/larger-icon.png" alt="">
                                                            <div class="num">
                                                                <p>较大风险</p>
                                                                <div id="jd">${allData[1].sublist[1].MORE_RISK}个</div>
                                                            </div>
                                                        </div>
                                                        <div class="fengxian">
                                                            <img src="./images/common-icon.png" alt="">
                                                            <div class="num">
                                                                <p>一般风险</p>
                                                                <div id="yb">${allData[1].sublist[1].NORMAL_RISK}个</div>
                                                            </div>
                                                        </div>
                                                        <div class="fengxian">
                                                            <img src="./images/low-icon.png" alt="">
                                                            <div class="num">
                                                                <p>低风险</p>
                                                                <div id="dd">${allData[1].sublist[1].LOW_RISK}个</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                </div>
                                                <div class="content" id="area3">
                                                <div class="item">
                                                <p onclick="window.location.href='detail.html'">风险等级</p>
                                                <div class="point">
                                                    <div class="fengxian">
                                                        <img src="./images/great-icon.png" alt="">
                                                        <div class="num">
                                                            <p>重大风险</p>
                                                            <div id="zd">${allData[1].sublist[2].MAJOR_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/larger-icon.png" alt="">
                                                        <div class="num">
                                                            <p>较大风险</p>
                                                            <div id="jd">${allData[1].sublist[2].MORE_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/common-icon.png" alt="">
                                                        <div class="num">
                                                            <p>一般风险</p>
                                                            <div id="yb">${allData[1].sublist[2].NORMAL_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/low-icon.png" alt="">
                                                        <div class="num">
                                                            <p>低风险</p>
                                                            <div id="dd">${allData[1].sublist[2].LOW_RISK}个</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                                </div>
                                                <div class="content" id="area4">
                                                <div class="item">
                                                <p onclick="window.location.href='detail.html'">风险等级</p>
                                                <div class="point">
                                                    <div class="fengxian">
                                                        <img src="./images/great-icon.png" alt="">
                                                        <div class="num">
                                                            <p>重大风险</p>
                                                            <div id="zd">${allData[1].sublist[3].MAJOR_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/larger-icon.png" alt="">
                                                        <div class="num">
                                                            <p>较大风险</p>
                                                            <div id="jd">${allData[1].sublist[3].MORE_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/common-icon.png" alt="">
                                                        <div class="num">
                                                            <p>一般风险</p>
                                                            <div id="yb">${allData[1].sublist[3].NORMAL_RISK}个</div>
                                                        </div>
                                                    </div>
                                                    <div class="fengxian">
                                                        <img src="./images/low-icon.png" alt="">
                                                        <div class="num">
                                                            <p>低风险</p>
                                                            <div id="dd">${allData[1].sublist[3].LOW_RISK}个</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                `
                }
                else if(params.name== allData[i].NAME){
                  var otherHtml = '<div class="tooltip-box"><div class="title"> <div class="location">'+ allData[i].NAME +'</div>';
                  otherHtml += ' <div class="total">风险统计<span>' + allData[i].tongJi + '</span>个</div> </div> <div class="otherList"> <div class="item"> <p>风险等级</p > <div class="point"> <div class="fengxian"> <img src="./images/great-icon.png" alt=""></img> <div class="num"> <p>重大风险</p >';
                  otherHtml += '<div id="zd">'+ allData[i].MAJOR_RISK +'个</div>';
                  otherHtml += ' </div> </div> <div class="fengxian"> <img src="./images/larger-icon.png" alt=""></img> <div class="num"> <p>较大风险</p >';
                  otherHtml += '<div id="jd">'+allData[i].MORE_RISK+'个</div>';
                  otherHtml += '</div> </div> <div class="fengxian"> <img src="./images/common-icon.png" alt=""></img> <div class="num"> <p>一般风险</p >';
                  otherHtml += '<div id="yb">' + allData[i].NORMAL_RISK + '个</div>';
                  otherHtml += '</div> </div> <div class="fengxian"> <img src="./images/low-icon.png" alt=""></img> <div class="num"> <p>低风险</p >';
                  otherHtml += ' <div id="dd">'+ allData[i].LOW_RISK +'个</div>';
                  otherHtml += '</div> </div> </div> </div> </div> </div>';
                  return otherHtml;
                }
              }
            },
          },

          series: [
            {
              name: '海南省',
              type: 'map',
              map: 'hainan',
              zoom: 1,
              roam: false,
              label: {
                show: true,
                fontSize: 12,
                color: '#fff'
              },
              itemStyle: {
                normal: {//未对地图作任何操作时的样式，加载想默认展示的样式
                  borderWidth: 4, //边框大小
                  borderColor: "#000be2",
                  areaColor: "#000a91", //背景颜色
                  label: {
                    show: true,
                    color: '#fff',
                    position: "top",
                    rich: {
                      pic: {
                        padding: [2, 5],
                        width: "auto",
                        height: 15,
                        align: "center",
                        color: "#FFFFFF",
                        fontSize: "10px",
                        borderRadius: 5,
                      },
                      fline: {
                        color: "#FFFFFF",
                        align: "center",
                        fontSize: "12px",
                      },
                    },
                  },
                },
                // 高亮区域样式
                emphasis: {
                  // 高亮区域背景色
                  areaColor: "#01ADF2",
                },

                //     emphasis: {
                //         borderWidth:2,
                //         borderColor:'red',
                //         label: {
                //             show: true,
                //             textStyle: {
                //                 color: '#fff',
                //             }
                //         },
                //         areaColor: '#0726ff',
                //         itemStyle: {
                //             areaColor: '#0726ff',
                //         },

                // }

                emphasis: {// 这个是鼠标移上去就会选中的样式，鼠标mouseover
                  borderWidth:1,
                  borderColor:'#00c6fe',
                  areaColor:"#0726ff",
                },
                //重点下面，与点击事件不同，虽然也是点击选中，但是事件不一样
                selectedMode:"single", //选择模式，单选，只能选中一个地市
                select:{//这个就是鼠标点击后，地图想要展示的配置
                  disabled:true,//可以被选中
                  itemStyle:{//相关配置项很多，可以参考echarts官网

                  }
                },


              }
            },

          ],
        };

        // 使用刚指定的配置项和数据显示图表
        myChart.setOption(option);


        // 注册 tab 切换事件
        $(document).on('click', '.tab', function () {
          var tabId = $(this).data('tab');
          $(this).addClass('tabActive').siblings().removeClass('tabActive');
          $('#' + tabId).addClass('active').siblings().removeClass('active');
        });
        // 4.跟随屏幕自适应
        window.addEventListener("resize", function() {
          myChart.resize({animation: {duration:1000}});
        });
      });
    }

    // 柱状图
    function barCharts(){
      var allData;

      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getTongJiAccident",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          allData = res;
          console.log(allData)
        },
      })
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#barChart'))
      // 2. 指定配置项和数据

      let dataAxis = [];

      let data = [];

      for(var i = 0;i<allData.length;i++){
        debugger;
        dataAxis.push(allData[i].NAME);
        data.push(allData[i].total);
      }

      option = {
        xAxis: {
          data: dataAxis, // x轴数据
          axisLabel: {
            interval: 0, // x轴标签显示间隔
            inside: false, // x轴标签是否朝内显示
            color: '#fff', // x轴标签颜色
            rotate: 0 // x轴标签旋转角度
          },
          axisTick: {
            show: false // x轴刻度线是否显示
          },
          axisLine: {
            show: false // x轴轴线是否显示
          },
          splitLine: {
            show: false, // x轴分割线是否显示
            lineStyle: {
              color: "red" // x轴分割线颜色
            }
          },
          z: 10
        },
        yAxis: {
          axisLine: {
            show: false // y轴轴线是否显示
          },
          axisTick: {
            show: false // y轴刻度线是否显示
          },
          axisLabel: {
            color: '#fff' // y轴标签颜色
          },
          splitLine: {
            show: false, // y轴分割线是否显示
            lineStyle: {
              color: "red" // y轴分割线颜色
            }
          }
        },
        dataZoom: [{
          type: 'slider', // 设置滚动条类型为slider
          orient: 'horizontal', // 设置滚动条方向为横向
          start: 0, // 设置默认开始位置
          end: 30 // 设置默认结束位置
        }],
        series: [
          {
            type: 'bar',
            showBackground: true,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#83bff6' }, // 渐变色起始颜色
                { offset: 0.5, color: '#188df0' }, // 渐变色中间颜色
                { offset: 1, color: '#188df0' } // 渐变色结束颜色
              ])
            },
            emphasis: {
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#2378f7' }, // 高亮渐变色起始颜色
                  { offset: 0.7, color: '#2378f7' }, // 高亮渐变色中间颜色
                  { offset: 1, color: '#83bff6' } // 高亮渐变色结束颜色
                ])
              }
            },
            data: data // 数据
          }
        ]
      };



      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });
    }

    function lineChart(){
      var lineData;
      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getFengXianByDate",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          lineData = res;
        },
      })

      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#lineChart'))
      var dateList = [];
      var data = [];
      for (var i = lineData.length-1; i >= 0; i--) {
        var item = lineData[i].date;
        dateList.push(item);
        data.push(lineData[i].total);
      }
      // 2. 指定配置项和数据
      // const data = new Array(12).fill(null).map(item => {
      //   return {
      //     value: Math.ceil(Math.random() * 600) + 100
      //   }
      // })
      option = {
        backgroundColor:'#061d6e84',
        color: ['rgba(250, 109, 62, 1)'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: '12%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: dateList,
            boundaryGap: false,
            axisTick:{
              show:false // 不显示坐标轴刻度线
            },
            splitLine: {
              show: false,
            },
            axisLine:{
              show: false,
            },
            axisLabel: {
              color: "rgba(230, 247, 255, 0.50)",
              fontSize:14
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            //y右侧文字
            axisLabel: {
              color: 'rgba(230, 247, 255, 0.50)',
              fontSize:16
            },
            // y轴的分割线
            splitLine: {
              show: true,
              lineStyle: {
                type:'dashed',
                color:'rgba(230, 247, 255, 0.20)',

              }
            }
          }
        ],
        series: [
          {
            name: '风险总数',
            type: 'line',
            smooth: true,
            symbol: 'none', // 不显示连接点
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                // 坐标轴指示器，坐标轴触发有效
                type: 'line' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            lineStyle: {
              width: 3,
              shadowColor: "rgba(250, 109, 62, 1)",
              shadowBlur: 20
            },
            areaStyle: {
              opacity: 1,
              //右下左上
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(250, 109, 62, 1)'
                },
                {
                  offset: 0.3,
                  color: 'rgba(250, 109, 62, 0.5)'
                },
                {
                  offset: 1,
                  color: 'rgba(250, 109, 62, 0.35)'
                }
              ])
            },
            data: data
          }
        ]
      }
      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });
    }

    //二级公司风险点
    function secondUnit(){
      var secondUnitData;
      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getListFengXian",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          secondUnitData = res;
        },
      })

      for(var i = 0;i<secondUnitData.length;i++){
        var secondUnitHtml = '';
        secondUnitHtml += '<div class="company-item">';
        secondUnitHtml += '<img class="logo" src="<%=basePath%>/frontend/images/company-logo.png" alt=""></img>';
        secondUnitHtml += '<div class="detail">';
        secondUnitHtml += '<div class="name">'+ secondUnitData[i].org_name +'</div>';
        secondUnitHtml += '<div class="point-content">';
        secondUnitHtml += '<div id="great" class="point"><i></i>';
        secondUnitHtml += '<span class="num">'+ secondUnitData[i].zd +'</span></div>';
        secondUnitHtml += '<div id="larger" class="point"><i></i>';
        secondUnitHtml += '<span class="num">'+ secondUnitData[i].jd +'</span></div>';
        secondUnitHtml += '<div id="common" class="point"><i></i>';
        secondUnitHtml += '<span class="num">'+ secondUnitData[i].yb +'</span></div>';
        secondUnitHtml += '<div id="low" class="point"><i></i>';
        secondUnitHtml += '<span class="num">'+ secondUnitData[i].dfx +'</span></div></div></div></div>';

        $('.company-list').append(secondUnitHtml);
      }


    }

    // 风险人数
    var fengxianAll = 0 // 当前报道人数
    var zhongda = 0 //男生人数
    var jiaoda = 0 //女生人数

    // 更新风险数函数
    function updateCounter() {
      $.ajax({
        url: 'data.json',
        method: "GET",
        dataType: "json",
        success: function(data) {
          // 获取报道人数
          var all = data.fengxianAll;
          var zd = data.zhongdaAll;
          var jd = data.jiaodaAll;


          // 判断是否需要过渡动画
          if (all > fengxianAll || zd > zhongda || jd > jiaoda) {
            // 动画效果过渡到新的人数
            $({ Counter: fengxianAll }).animate(
                    { Counter: all },
                    {
                      duration: 2000, // 动画持续时间，单位为毫秒
                      easing: "swing", // 缓动函数，可以根据需要修改
                      step: function() {
                        $("#counter").text(Math.ceil(this.Counter));
                      }
                    }
            );
            // 动画效果过渡到新的人数
            $({ Zhongda: zhongda }).animate(
                    { Zhongda: zd },
                    {
                      duration: 2000, // 动画持续时间，单位为毫秒
                      easing: "swing", // 缓动函数，可以根据需要修改
                      step: function() {
                        $("#zhongda").text(Math.ceil(this.Zhongda));
                      }
                    }
            );
            // 动画效果过渡到新的人数
            $({ Jiaoda: jiaoda }).animate(
                    { Jiaoda: jd },
                    {
                      duration: 2000, // 动画持续时间，单位为毫秒
                      easing: "swing", // 缓动函数，可以根据需要修改
                      step: function() {
                        $("#jiaoda").text(Math.ceil(this.Jiaoda));
                      }
                    }
            );
          } else {
            // 直接更新人数显示
            $("#counter").text(all);
            $("#zhongda").text(zd);
            $("#jiaoda").text(jd);
          }

          // 更新当前报道人数
          fengxianAll = all;
          zhongda = zd;
          jiaoda = jd;
        },
        error: function(xhr, status, error) {
        }
      });
    }

    // 页面加载完成后立即更新风险数
    updateCounter();

    // 每隔5秒钟更新一次风险数
    setInterval(function() {
      updateCounter();
    }, 600000);
  })




</script>


</body>
</html>