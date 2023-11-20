
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>海南控股应急管理信息平台</title>
  <link rel="stylesheet" href="<%=basePath%>frontend/css/reset.css">
  <link rel="stylesheet" href="<%=basePath%>frontend/css/public.css">
  <link rel="stylesheet" href="<%=basePath%>frontend/css/enterIndex.css">
</head>
<body>
<!-- 1.顶部标题 -->
<header>
  <div class="container">
    <div class="title">海南控股应急管理信息平台</div>
  </div>
</header>
<!-- 2. 内容区域 -->
<div class="main-container">
  <div class="left-container">
    <div class="top-box">
      <div class="hkLogo">
        <img src="<%=basePath%>frontend/images/hk-logo.png" alt="">
      </div>

      <div class="systemSelect">
        <a target="_blank" href="<%=basePath%>yjfhmFrontend/index">应急响应管理模块</a>
        <a target="_blank" href="<%=basePath%>hkSafeFrontend/subviewBackend?ORG_iD=${pd.ORG_iD}">安全隐患管理模块</a>
        <a target="_blank" href="<%=basePath%>fengxian/fengxianSecond?ORG_iD=${pd.ORG_iD}">安全风险管理模块</a>
      </div>
      <div class="panel-footer"></div>
    </div>
    <div class="center-box">
      <div class="title">各隐患因素统计</div>
      <div id="yinhuanType"></div>
      <div class="title">二级公司隐患总数</div>
      <div id="yinhuanTotal"></div>
      <div class="panel-footer"></div>
    </div>
    <div class="bottom-box">
      <div class="title">隐患数量趋势</div>
      <div id="yinhuanTrend"></div>
      <div class="panel-footer"></div>
    </div>
  </div>
  <div class="map-container">
    <div class="reset">隐藏</div>
    <div class="top-box">
      <div class="title">
        <img src="<%=basePath%>frontend/images/tongji-icon.png" alt="">
      </div>
      <div class="sum-list">
        <div class="total" id="projectNum">
          <p>项目总数</p>
          <div class="num"><span>0</span>个</div>
          <div class="compare">
            <span class="text">同比</span>
            <div>
              <span class="comparison-text"></span>
              <span class="percentage"></span>
            </div>
          </div>
        </div>
        <div class="total" id="yinhuanNum">
          <p>安全隐患</p>
          <div class="num"><span>0</span>个</div>
          <div class="compare">
            <span class="text">同比</span>
            <div>
              <span class="comparison-text"></span>
              <span class="percentage"></span>
            </div>
          </div>
        </div>
        <div class="total" id="fengxianNum">
          <p>安全风险</p>
          <div class="num"><span>0</span>个</div>
          <div class="compare">
            <span class="text">同比</span>
            <div>
              <span class="comparison-text"></span>
              <span class="percentage"></span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="tab-content" id="mapContent">
      <div class="content img-text active" id="map1">
        <div class="total">
          <div class="text">项目<br>总数</div>
          <div class="num" id="map1total">0</div>
          个
        </div>
        <div id="mapChart1"></div>
      </div>
      <div class="content img-text" id="map2">
        <div class="total">
          <div class="text">安全<br>隐患</div>
          <div class="num" id="map2total">0</div>
          个
        </div>
        <div id="mapChart2"></div>
      </div>
      <div class="content img-text" id="map3">
        <div class="total">
          <div class="text">安全<br>风险</div>
          <div class="num" id="map3total">0</div>
          个
        </div>
        <div id="mapChart3"></div>
      </div>
    </div>
  </div>
  <div class="right-container">
    <div class="top-box">
      <!-- 隐患类型分析pie -->
      <div class="title">风险等级</div>
      <div id="fengxianLevel"></div>
      <div class="panel-footer"></div>
      <!-- 隐患总数排名line -->
      <div class="title">可能导致的事故类型</div>
      <div id="fengxianType"></div>
    </div>
    <div class="bottom-box">
      <div class="title">风险总数趋势</div>
      <div id="fengxianTrend"></div>
      <div class="panel-footer"></div>
    </div>
  </div>
</div>
<!-- 3. 底部切换地图栏 -->
<div class="map-select">
  <div class="tab-container" id="mapContainer">
    <div class="tab map1 tabActive" data-tab="map1">
      <span>项目概况</span>
    </div>
    <div class="tab map2" data-tab="map2">
      <span>安全隐患</span>
    </div>
    <div class="tab map3" data-tab="map3">
      <span>安全风险</span>
    </div>
  </div>
</div>

<script src="<%=basePath%>frontend/js/jquery.js"></script>
<script src="<%=basePath%>static/jquery/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>frontend/js/flexible.js"></script>
<script src="<%=basePath%>frontend/js/echarts.min.js"></script>
<script>
  $(document).ready(function () {
    //通过宽度自适应
    function fontSize(res){
      let docEl = document.documentElement,
              clientWidth = window.innerWidth||document.documentElement.clientWidth||document.body.clientWidth;
      if (!clientWidth) return;
      let fontSize = 100 * (clientWidth / 1920);
      return res*fontSize;
    }

    //地图左上方大字的总数
    mapTotal()
    function mapTotal(){
      $.ajax({
        url: "<%=basePath%>frontend/mapTotal",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          $("#map1total").text(res.projectTotal)
          $("#map2total").text(res.rectifyTotal)
          $("#map3total").text(res.fengXianTotal)
        },
      })
    }

    //获取首页驾驶舱顶部数据
    var allData;
    $.ajax({
      url: "<%=basePath%>frontend/indexTopData",
      data: {},
      type: "GET",
      text: "json",
      async: false,
      success: function(res){
        allData = res;
      },
    })

    var projectList = allData.projectList.zs;
    var rectifyInfoList = allData.rectifyInfoList.zs;
    var fengXianList = allData.fengXianList.zs;

    var prevProjectList = allData.prevProjectList.zs;
    var prevRectifyInfoList = allData.prevRectifyInfoList.zs;
    var prevFengXianList = allData.prevFengXianList.zs;

    // 项目总数数据
    var projectPre = prevProjectList.length - projectList.length;
    var projectCur = projectList.length;
    var projectBox = $('#projectNum');
    // 安全隐患数据
    var yinhuanPre = prevRectifyInfoList.length - rectifyInfoList.length;
    var yinhuanCur = rectifyInfoList.length;
    var yinhuanBox = $('#yinhuanNum');
    // 风险总数数据
    var fengxianPre = prevFengXianList.length - fengXianList.length;
    var fengxianCur = fengXianList.length;
    var fengxianBox = $('#fengxianNum');

    updateComparisonData(projectPre, projectCur, projectBox );
    updateComparisonData(yinhuanPre, yinhuanCur, yinhuanBox );
    updateComparisonData(fengxianPre, fengxianCur, fengxianBox );
    //统计数量
    function updateComparisonData(previousData, currentData, comparisonBox) {
      var numElement = comparisonBox.find('.num span');
      var compare = comparisonBox.find('.compare')
      if(previousData === undefined || previousData === 0){
        compare.text('无变动')
      }
      numElement.prop('counter', 0).animate({
        counter: currentData
      }, {
        duration: 1000, // 动画持续时间（以毫秒为单位）
        easing: 'swing', // 动画缓动函数
        step: function(now) {
          numElement.text(Math.ceil(now)); // 使用向上取整确保数字递增
        },
        complete: function() {
          var comparisonText = currentData > previousData ? '增长' : '下降';
          var percentage = Math.abs(((currentData - previousData) / previousData * 100).toFixed(2));
          var comparisonTextElement = comparisonBox.find('.comparison-text');
          comparisonTextElement.text(comparisonText);
          comparisonBox.find('.percentage').text(percentage + '%');
          if (comparisonText === '增长') {
            comparisonTextElement.addClass('increase');
          } else {
            comparisonTextElement.removeClass('increase');
          }
        }
      });
    }

    yinhuanType();
    // 隐患因素饼图
    function yinhuanType(){
      // 模拟数据
      let data = '';
      $.ajax({
        url: "<%=basePath%>hkSafeFrontend/factorComplete.do",
        data: {"ORG_CODE": ""},
        type: "POST",
        dataType: "json",
        async: false,
        success: function(res){
          data = res.array;
        },
      })

      var DATA = data;
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#yinhuanType'))
      // 2. 指定配置项和数据
      option = {
        // tooltip: {
        //     trigger: 'item',
        //     formatter(params){
        //         if(params.data.name === ''){
        //             return '无'
        //         }else{
        //             return params.name + ':' + params.data.value + '个'
        //         }
        //     }
        // },
        legend: {
          left: 'left',
          top: 'center',
          width: '5%',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          formatter(name){
            var total = 0 ;
            var samValue;
            $.each(DATA, function (index, value) {
              total += DATA[index].value

              if (DATA[index].name == name) {
                samValue = DATA[index].value
              }
            });
            //计算出百分比
            let percent = Math.round((samValue / total) * 100) + '%'
            return name + " " + percent
          }

        },
        series: [
          {
            name: '各隐患隐患因素统计',
            type: 'pie',
            center: ['70%', '50%'],
            radius: ['40%', '80%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 4,
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold',
                color: '#fff',
              }
            },
            labelLine: {
              show: false
            },
            data: [],
            color: [
              new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: 'rgba(50, 84, 221, 1)'},
                {offset: 1, color: 'rgba(50, 84, 221, .5)'}
              ]),
              new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: 'rgba(43, 142, 243, 1)'},
                {offset: 1, color: 'rgba(43, 142, 243, .5)'}
              ]),
              new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: 'rgba(190, 229, 251, 1)'},
                {offset: 1, color: 'rgba(190, 229, 251, .5)'}
              ]),
              new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: 'rgba(61, 188, 190, 1)'},
                {offset: 1, color: 'rgba(61, 188, 190, .5)'}
              ]),
            ],
          }
        ]
      };
      $.each(DATA, function (index, value) {
        option.series[0].data.push({
          name: DATA[index].name,
          value: DATA[index].value
        })
      });
      // 3. 把配置项给实例对象
      myChart.setOption(option)
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      })
    }

    yinhuanTotal()
    // 二级隐患总数柱状图
    function yinhuanTotal(){

      let data = '';
      $.ajax({
        url: "<%=basePath%>hkSafeFrontend/erjiComplete.do",
        // data: {"ORG_CODE": ORG_CODE},
        type: "GET",
        dataType: "json",
        async: false,
        success: function(res){
          data = res.totalArr;
        },
      })
      // 模拟数据
      var DATA = data;
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#yinhuanTotal'))

      // 2. 指定配置项和数据
      option = {
        color: ["#cf7f2ffc"],
        // title: {
        //   text: 'World Population'
        // },
        // tooltip: {
        //     trigger: 'axis',
        //     axisPointer: {
        //         type: 'shadow'
        //     }
        // },
        legend: {},
        grid: {
          top: '10%',
          left: '1%',
          right: '10%',
          bottom: '0',
          containLabel: true
        },
        xAxis: {
          show: false,
          type: 'value',
          boundaryGap: [0, 0.01],
          axisTick: {
            alignWithLabel: true
          },
          axisLabel: {
            textStyle: {
              color: "rgba(255,255,255,.6)",
              fontSize: "12"
            }
          },
          axisLine: {
            show: false
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: "red"
            }
          }
        },
        yAxis: {
          type: 'category',
          inverse: true,
          data: [],
          axisLabel: {
            interval: 0,
            textStyle: {
              color: "rgba(255,255,255,1)",
              fontSize: "12"
            }
          },
          axisLine: {
            lineStyle: {
              color: "rgba(255,255,255,.1)"
              // width: 1,
              // type: "solid"
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: "red"
            }
          }
        },
        dataZoom: [
          {
            // 设置滚动条的隐藏或显示
            show: true,
            // 设置类型
            type: "slider",
            // 是否显示detail，即拖拽时候显示详细数值信息
            showDetail: true,
            // 数据窗口范围的起始数值
            startValue: 0,
            // 数据窗口范围的结束数值（一页显示多少条数据）
            endValue: 5,
            // 控制哪个轴，如果是number表示控制一个轴，
            // 如果是Array表示控制多个轴。此处控制第二根轴
            yAxisIndex: [0, 1],
            // empty：当前数据窗口外的数据，被设置为空。
            // 即不会影响其他轴的数据范围
            filterMode: "none",
            // 是否锁定选择区域（或叫做数据窗口）的大小
            zoomLoxk: false,
            // 组件离容器上侧的距离
            // 如果top的值为'top', 'middle', 'bottom'，组件会根据相应的位置自动对齐
            top: "middle",
          },
          {
            // 没有下面这块的话，只能拖动滚动条，
            // 鼠标滚轮在区域内不能控制外部滚动条
            type: "inside",
            // 控制哪个轴，如果是number表示控制一个轴，
            // 如果是Array表示控制多个轴。此处控制第二根轴
            yAxisIndex: [0, 1],
            // 滚轮是否触发缩放
            zoomOnMouseWheel: false,
            // 鼠标移动能否触发平移
            moveOnMouseMove: true,
            // 鼠标滚轮能否触发平移
            moveOnMouseWheel: true,
          },
        ],
        series: [
          {
            type: 'bar',
            data: [],
            realtimeSort: true,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                  // 四个数字分别对应 数组中颜色的开始位置，分别为 右，下，左，上。例如（1,0,0,0 ）代表从右边开始渐
                  // 变。offset取值为0~1，0代表开始时的颜色，1代表结束时的颜色，柱子表现为这两种颜色的渐变。
                  offset: 1,
                  color: '#188df0'
                },
                  {
                    offset: 0,
                    color: '#83bff6'
                  }
                ]),
                barBorderRadius: [10, 10, 10, 10]
              }
            },
            label: {
              normal: {
                show: true,
                position: "outside",
                textStyle: {
                  color: '#fff',
                }
              }
            },

          },
        ]
      };
      // for(var i = 0; i < data.length; i++){
      // option.yAxis.data.push(data[i].name)
      // option.series[0].data.push(data[i].value)
      // }
      $.each(DATA, function (index, value) {
        option.yAxis.data.push(DATA[index].name)
        option.series[0].data.push(DATA[index].value)
      });
      // 3. 把配置项给实例对象
      myChart.setOption(option)
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      })
    }

    yinhuanTrend()
    // 隐患趋势折线图
    function yinhuanTrend(){
      let data = '';
      $.ajax({
        url: "<%=basePath%>hkSafeFrontend/dangerComplete.do",
        data: {"ORG_CODE": "","dayNum": "7"},
        type: "POST",
        text: "json",
        async: false,
        success: function(res){
          data = res;
        },
      })

      // 模拟数据
      // var DATA = {
      //     processed:[30, 40, 20, 50, 60, 10, 90],
      //     untreated:["50", "30", "50", "60", "10", "50", "30"]
      // }
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#yinhuanTrend'))
      // var dataDate = ['2022/12/13', '2023/1/13', '2023/2/13', '2023/3/13', '2023/4/13', '2023/5/13', '2023/6/13']
      // 2. 指定配置项和数据
      option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            lineStyle: {
              color: "#dddc6b"
            }
          }
        },
        legend: {
          top: "top",
          textStyle: {
            color: "rgba(255,255,255,.5)",
            fontSize: "12"
          }
        },
        grid: {
          left: "10",
          top: "30",
          right: "40",
          bottom: "0",
          containLabel: true
        },

        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.6)",
                fontSize: 12
              }
            },
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.2)"
              }
            },

            data: data.dayArray
          },
          {
            axisPointer: { show: false },
            axisLine: { show: false },
            position: "bottom",
            offset: 20
          }
        ],

        yAxis: [
          {
            type: "value",
            axisTick: { show: false },
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)"
              }
            },
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.6)",
                fontSize: 12
              }
            },

            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)"
              }
            }
          }
        ],
        series: [
          {
            name: "整改完成",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#0184d5",
                width: 2
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                        0,
                        0,
                        0,
                        1,
                        [
                          {
                            offset: 0,
                            color: "rgba(1, 132, 213, 0.4)"
                          },
                          {
                            offset: 0.8,
                            color: "rgba(1, 132, 213, 0.1)"
                          }
                        ],
                        false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)"
              }
            },
            itemStyle: {
              normal: {
                color: "#0184d5",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12
              }
            },
            data: data.finishArray
          },
          {
            name: "正在整改",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#00d887",
                width: 2
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                        0,
                        0,
                        0,
                        1,
                        [
                          {
                            offset: 0,
                            color: "rgba(0, 216, 135, 0.4)"
                          },
                          {
                            offset: 0.8,
                            color: "rgba(0, 216, 135, 0.1)"
                          }
                        ],
                        false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)"
              }
            },
            itemStyle: {
              normal: {
                color: "#00d887",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12
              }
            },
            data: data.unfinishArray
          },
          {
            name: "整改逾期",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#00d887",
                width: 2
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                        0,
                        0,
                        0,
                        1,
                        [
                          {
                            offset: 0,
                            color: "rgba(0, 216, 135, 0.4)"
                          },
                          {
                            offset: 0.8,
                            color: "rgba(0, 216, 135, 0.1)"
                          }
                        ],
                        false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)"
              }
            },
            itemStyle: {
              normal: {
                color: "#001dd8",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12
              }
            },
            data: data.overdueArray
          },
          {
            name: "逾期完成",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                color: "#00d887",
                width: 2
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                        0,
                        0,
                        0,
                        1,
                        [
                          {
                            offset: 0,
                            color: "rgba(0, 216, 135, 0.4)"
                          },
                          {
                            offset: 0.8,
                            color: "rgba(0, 216, 135, 0.1)"
                          }
                        ],
                        false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)"
              }
            },
            itemStyle: {
              normal: {
                color: "#9bd800",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12
              }
            },
            data: data.overFinishArray
          }
        ]
      };
      // for(i = 0; i < data.finishArray.length; i++){
      //     option.series[0].data.push(data.finishArray[i])
      //     option.series[1].data.push(data.unfinishArray[i])
      // }
      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });
    }

    fengxianLevelChart()
    // 风险等级饼图
    function fengxianLevelChart(){
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

      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#fengxianLevel'))
      // 2. 指定配置项和数据
      const colorList1 = [ 'rgb(255,0,0,1.000)','', 'rgb(255,192,0,1.000)','', 'rgb(255,255,0,1.000)','', 'rgb(0,176,240,1.000)',]
      const colorList2 =  [ 'rgb(255,0,0, .5)','', 'rgb(255,192,0, .5)','', 'rgb(255,255,0, .5)', '','rgb(0,176,240,.5)',]
      let total = 0
      let dataList = []
      var zd = {'data': 22}
      const moduleContent = { '重大风险': peiData.zd, '较大风险': peiData.jd, '一般风险': peiData.yb ,'低风险': peiData.dfx}
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
        // legend: {
        //     left: 'left',
        //     top: 'center',
        //     width: '20%',
        //     textStyle:{
        //         color: '#fff'
        //     }
        // },
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
        // tooltip: {
        //     trigger: 'item',
        //     axisPointer: {
        //         // 坐标轴指示器，坐标轴触发有效
        //         type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
        //     },
        //     formatter(params) {
        //         if(params.data.name === ''){
        //             return '无'
        //         }else{
        //             return params.name + ':' + params.data.value;
        //         }
        //     },
        //     textStyle: {
        //         fontSize: 18,
        //         color: 'rgba(255,255,255,0.8)'
        //     },
        //     borderColor: 'rgba(255,255,255,0.9)',
        //     backgroundColor: 'rgba(255,255,255,0.5)',
        //     extraCssText: 'box-shadow: 2px 2px 4px 0px rgba(255,255,255,0.5);'
        // },
        series: [
          {
            type: 'pie',
            radius: ['50%', '72%'],
            center: ['50%', '50%'],
            minAngle: 1,
            labelLine: {
              show: true,
              normal: {
                show: true,
                length: 15,
                length2: 30,
              },
            },
            label: {
              show: true,
              normal: {
                formatter: "{b|{b}}\n{d|{c}}",
                rich: {
                  b: {
                    fontSize: 14,
                    color: "#fff",
                    align: "center",
                    padding: [0, 0, 0, 0],
                  },
                  d: {
                    fontSize: 16,
                    color: "#68BBC4",
                    align: "center",
                    padding: [4, 0, 0, 0],
                  },
                },
              },
              textStyle: {
                color: '#fff',
              }
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

    // 风险趋势折线图
    fengxianTrendChart()
    function fengxianTrendChart(){
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
      var myChart = echarts.init(document.querySelector('#fengxianTrend'))
      // 2. 指定配置项和数据
      var dateList = [];
      var data = [];
      for (var i = lineData.length-1; i >= 0; i--) {
        var item = lineData[i].date;
        dateList.push(item);
        data.push(lineData[i].total);
      }
      option = {
        backgroundColor:'#061d6e84',
        color: ['#0184d5'],
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
              shadowColor: "rgba(1, 132, 213, 1)",
              shadowBlur: 20
            },
            areaStyle: {
              opacity: 1,
              //右下左上
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(1, 132, 213, 0.7)'
                },
                {
                  offset: 0.3,
                  color: 'rgba(1, 132, 213, 0.4)'
                },
                {
                  offset: 1,
                  color: 'rgba(1, 132, 213, 0.1)'
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

    // 柱状图
    fengxianTypeChart()
    function fengxianTypeChart(){
      var allData;

      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getTongJiAccident",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          allData = res;
        },
      })
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#fengxianType'))
      // 2. 指定配置项和数据
      let dataAxis = [];
      let data = [];

      $.each(allData, function (index, item) {
        console.log(index,item)
        dataAxis.push(index);
        data.push(item);
      });

      // for(var i = 0;i<allData.length;i++){
      //     dataAxis.push(allData.key);
      //     data.push(allData.value);
      // }

      option = {
        grid: {
          left: '1%',
          right: '1',
          top: '5%',
          bottom: '25%',
          containLabel:true
        },
        xAxis: {
          data: dataAxis, // x轴数据
          axisLabel: {
            interval: 0, // x轴标签显示间隔
            inside: true, // x轴标签是否朝内显示
            color: '#fff', // x轴标签颜色
            // rotate: 20 // x轴标签旋转角度
            formatter: function (value){
              return value.split('').join('\n')
            }
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
            data: data.sort(function (a,b){
              return b - a
            })  // 数据
          }
        ]
      };


      // option = {
      //     grid: {
      //         left: '1%',
      //         right: '1%',
      //         top: '5%',
      //         containLabel: true
      //     },
      //     xAxis: {
      //         data: dataAxis, // x轴数据
      //         axisLabel: {
      //             interval: 0, // x轴标签显示间隔
      //             inside: false, // x轴标签是否朝内显示
      //             color: '#fff', // x轴标签颜色
      //             rotate: 0 // x轴标签旋转角度
      //         },
      //         axisTick: {
      //             show: false // x轴刻度线是否显示
      //         },
      //         axisLine: {
      //             show: false // x轴轴线是否显示
      //         },
      //         splitLine: {
      //             show: false, // x轴分割线是否显示
      //             lineStyle: {
      //                 color: "red" // x轴分割线颜色
      //             }
      //         },
      //         z: 10
      //     },
      //     yAxis: {
      //         axisLine: {
      //             show: false // y轴轴线是否显示
      //         },
      //         axisTick: {
      //             show: false // y轴刻度线是否显示
      //         },
      //         axisLabel: {
      //             color: '#fff' // y轴标签颜色
      //         },
      //         splitLine: {
      //             show: false, // y轴分割线是否显示
      //             lineStyle: {
      //                 color: "red" // y轴分割线颜色
      //             }
      //         }
      //     },
      //     dataZoom: [{
      //         type: 'slider', // 设置滚动条类型为slider
      //         orient: 'horizontal', // 设置滚动条方向为横向
      //         start: 0, // 设置默认开始位置
      //         end: 30 // 设置默认结束位置
      //     }],
      //     series: [
      //         {
      //             type: 'bar',
      //             showBackground: true,
      //             itemStyle: {
      //                 color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      //                     { offset: 0, color: '#83bff6' }, // 渐变色起始颜色
      //                     { offset: 0.5, color: '#188df0' }, // 渐变色中间颜色
      //                     { offset: 1, color: '#188df0' } // 渐变色结束颜色
      //                 ])
      //             },
      //             emphasis: {
      //                 itemStyle: {
      //                     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      //                         { offset: 0, color: '#2378f7' }, // 高亮渐变色起始颜色
      //                         { offset: 0.7, color: '#2378f7' }, // 高亮渐变色中间颜色
      //                         { offset: 1, color: '#83bff6' } // 高亮渐变色结束颜色
      //                     ])
      //                 }
      //             },
      //             data: data.sort(function(a,b){return b-a}) // 数据
      //         }
      //     ]
      // };



      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });
    }

    // 地图1
    mapChart()
    function mapChart(){
      var myChart1 = echarts.init(document.getElementById('mapChart1'));

      var allData;

      $.ajax({
        url: "<%=basePath%>/frontend/listProjectByArea",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          debugger;
          allData = res;
        },
      })


      var dataList = [
        { name: '海口市', value: 218 },
        { name: '文昌市', value: 78 },
        { name: '琼海市', value: 18 },
        { name: '临高市', value: 8 },
      ]

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
          visualMap: {
            min: 0,
            max: 100000,
            orient: 'horizontal',
            left: 'center',
            bottom: 5,
            showLabel: true,
            textStyle: {
              color: '#fff'
            },
            pieces: [
              //     {
              //     gt: 1000,
              //     label: "> 1000个",
              //     color: "#e64b45"
              // },
              {
                gt:500,
                // lte: 1000,
                label: ">500个",
                color: "#e64b45"
              }, {
                gte: 100,
                lt: 500,
                label: "100 - 500个",
                color: "rgb(246,178,37)"
              }, {
                gte: 10,
                lt: 100,
                label: "10 - 100个",
                color: "rgb(14,64,183)"
              }, {
                gte: 0,
                lt: 10,
                label: "0 - 10个",
                color: "#005be3"
              }],
            show: true
          },
          // 悬浮框
          tooltip: {
            padding: 0,
            position: ['150%', '20%'],
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
                if(params.name==allData[i].name){
                  var listHtml = allData[i].list.map(function (item) {
                    return '<a href="<%=basePath%>frontend/enterProject?ORG_ID='+ item.id +'" target="_blank" class="item">' + item.name + '</a>';
                  }).join('');
                  var totalHtml = ' <div class="tooltip"> <div class="left">';
                  totalHtml += '<div class="area">' + allData[i].name + '</div>';
                  totalHtml += '<div class="num"><span>' + allData[i].list.length + '</span>个</div> <div class="text">项目总数</div> </div> <div class="right"> <input type="text" id="searchBox" class="search-input" placeholder="请输入搜索关键字">';
                  totalHtml += '<div class="list">' + listHtml + ' </div>';
                  totalHtml += '</div></div>';
                  return totalHtml;
                }
              }

            },

          },

          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: "none"
              },
              dataView: {
                readOnly: false
              },
              magicType: {
                type: ["line", "bar"]
              },
              restore: {},
              saveAsImage: {
                type: "png"
              }
            }
          },

          geo: {
            map: "hainan",
            // 底层添加边框
            itemStyle: {
              borderColor: "#4394f0",
              borderWidth: 5,
              shadowBlur: 20, // 阴影模糊大小
              shadowColor: "rgba(67,148,240, 0.8)" // 阴影颜色
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
                color: '#fff',
                emphasis: {// 这个是鼠标移上去就会选中的样式，鼠标mouseover
                  color: '#fff'
                },
              },
              // itemStyle: {
              //     areaColor: {
              //         type: 'linear',
              //         x: 0,
              //         y: 0,
              //         x2: 0,
              //         y2: 1,
              //         colorStops: [
              //             {
              //             offset: 0,
              //             color: '#000a89' // 渐变起始颜色
              //             },
              //             {
              //             offset: 1,
              //             color: '#000a90' // 渐变结束颜色
              //             }
              //         ]
              //     },
              //     borderColor: '#0011f2'
              // },
              itemStyle: {
                normal: {//未对地图作任何操作时的样式，加载想默认展示的样式
                  borderWidth: 1, //边框大小
                  borderColor: "#4394f0",
                  areaColor: "#005be3", //背景颜色
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
                  borderColor:'#0060ff',
                  areaColor:"#0019d7",
                  textStyle: {
                    color: '#fff',
                  }
                },
                //重点下面，与点击事件不同，虽然也是点击选中，但是事件不一样
                selectedMode:"single", //选择模式，单选，只能选中一个地市
                select:{//这个就是鼠标点击后，地图想要展示的配置
                  disabled:true,//可以被选中
                  itemStyle:{//相关配置项很多，可以参考echarts官网

                  }
                },


              },
              data: []
            },

          ],
        };
        $.each(allData, function (index, value) {
          option.series[0].data.push({
            name: allData[index].name,
            value: allData[index].list.length
          })
        })

        // // 监听click事件
        // myChart1.on('showTip', function (params) {
        //     // 点击地图区域时缩小地图
        //     myChart1.setOption({
        //         series: [
        //             {
        //                 zoom: 0.8, // 缩小的缩放比例
        //                 left: 0
        //             }
        //         ],
        //         geo:{
        //             zoom: 0.8,
        //             left: 0
        //         }
        //     });
        //     // 显示.reset按钮
        //     $('.reset').show();
        // });
        //
        // // 监听tooltip隐藏事件
        // myChart1.on('hideTip', function () {
        //     // tooltip隐藏时复原地图
        //     myChart1.setOption({
        //         series: [
        //             {
        //                 zoom: 1, // 原始缩放比例
        //                 left: 'center'
        //             }
        //         ],
        //         geo:{
        //             zoom: 1,
        //             left: 'center'
        //         }
        //     });
        // });
        //
        // // 点击.reset按钮隐藏
        // $('.reset').mouseenter(function () {
        //     // 隐藏.reset按钮
        //     $('.reset').hide();
        // });

        // 使用刚指定的配置项和数据显示图表
        myChart1.setOption(option);

        // 将搜索框事件注册到window对象
        window.addEventListener('input', function(event) {
          if (event.target.id === 'searchBox') {
            var keyword = event.target.value;
            // 在这里进行搜索逻辑的处理
            $('.search-input').on('input', function() {
              var keyword = $(this).val().trim();
              search(keyword);
            });

            function search(keyword) {
              var items = $('.tooltip .list .item');
              items.removeClass('hidden');

              if (keyword !== '') {
                items.each(function() {
                  var itemText = $(this).text();
                  if (!itemText.includes(keyword)) {
                    $(this).addClass('hidden');
                  }
                });
              }
            }
          }
        });

        // 4.跟随屏幕自适应
        window.addEventListener("resize", function() {
          myChart1.resize({animation: {duration:1000}});
        });
      });
    }

    mapChart2()
    function mapChart2(){

      var dataList;

      $.ajax({
        url: "<%=basePath%>/frontend/getRectifyByArea",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          debugger;
          dataList = res;
        },
      })

      var myChart1 = echarts.init(document.getElementById('mapChart2'));

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
          visualMap: {
            min: 0,
            max: 100000,
            orient: 'horizontal',
            left: 'center',
            bottom: 5,
            showLabel: true,
            textStyle: {
              color: '#fff'
            },
            pieces: [{
              gt: 1000,
              label: "> 1000个",
              color: "#e64b45"
            }, {
              gte: 500,
              lte: 1000,
              label: "500 - 1000个",
              color: "#0a64b9"
            }, {
              gte: 100,
              lt: 500,
              label: "100 - 500个",
              color: "#0a64a5"
            }, {
              gte: 10,
              lt: 100,
              label: "10 - 100个",
              color: "#1979c0"
            }, {
              gte: 1,
              lt: 10,
              label: "1 - 10个",
              color: "#2e8ece"
            }],
            show: true
          },
          // 悬浮框
          tooltip: {
            padding: 0,
            position: ['150%', '20%'],
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

              for(var i = 0;i<dataList.length;i++){
                if(params.name==dataList[i].NAME){
                  var listHtml = '';

                  listHtml += '<div class="tooltip"> <div class="left">';
                  listHtml += '<div class="area">'+dataList[i].NAME+'</div>';
                  listHtml += '<div class="num"><span> ' + dataList[i].value + '</span>个</div>';
                  listHtml += '<div class="text">安全隐患</div></div>';
                  listHtml += '<div class="right">';
                  listHtml += '<div class="great"><img src="<%=basePath%>/frontend/images/great-icon.png" alt=""><p>重大隐患<span>' + dataList[i].MORE + '</span></p ></div>';
                  listHtml += '<div class="larger"><img src="<%=basePath%>/frontend/images/larger-icon.png" alt=""><p>一般隐患<span>' + dataList[i].NORMAL + '</span></p ></div>';
                  listHtml += '</div></div>';
                  return listHtml;
                }
              }
            },
          },

          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: "none"
              },
              dataView: {
                readOnly: false
              },
              magicType: {
                type: ["line", "bar"]
              },
              restore: {},
              saveAsImage: {
                type: "png"
              }
            }
          },

          geo: {
            map: "hainan",
            // 底层添加边框
            itemStyle: {
              borderColor: "#ff5800",
              borderWidth: 5,
              shadowBlur: 20, // 阴影模糊大小
              shadowColor: "rgba(255,162,0,.8)" // 阴影颜色
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
                color: '#fff',
                emphasis: {// 这个是鼠标移上去就会选中的样式，鼠标mouseover
                  color: '#fff'
                },
              },
              // itemStyle: {
              //     areaColor: {
              //         type: 'linear',
              //         x: 0,
              //         y: 0,
              //         x2: 0,
              //         y2: 1,
              //         colorStops: [
              //             {
              //             offset: 0,
              //             color: '#000a89' // 渐变起始颜色
              //             },
              //             {
              //             offset: 1,
              //             color: '#000a90' // 渐变结束颜色
              //             }
              //         ]
              //     },
              //     borderColor: '#0011f2'
              // },
              itemStyle: {
                normal: {//未对地图作任何操作时的样式，加载想默认展示的样式
                  borderWidth: 1, //边框大小
                  borderColor: "#4394f0",
                  areaColor: "#005be3", //背景颜色
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
                  borderColor:'#ff9000',
                  areaColor:"#ff4800",
                },
                //重点下面，与点击事件不同，虽然也是点击选中，但是事件不一样
                selectedMode:"single", //选择模式，单选，只能选中一个地市
                select:{//这个就是鼠标点击后，地图想要展示的配置
                  disabled:true,//可以被选中
                  itemStyle:{//相关配置项很多，可以参考echarts官网

                  }
                },


              },
              data: dataList
            },

          ],
        };

        // 使用刚指定的配置项和数据显示图表
        myChart1.setOption(option);

        // // 监听click事件
        // myChart1.on('showTip', function (params) {
        //     // 点击地图区域时缩小地图
        //     myChart1.setOption({
        //         series: [
        //             {
        //                 zoom: 0.8, // 缩小的缩放比例
        //                 left: 0
        //             }
        //         ],
        //         geo:{
        //             zoom: 0.8,
        //             left: 0
        //         }
        //     });
        //     // 显示.reset按钮
        //     $('.reset').show();
        // });
        //
        // // 监听tooltip隐藏事件
        // myChart1.on('hideTip', function () {
        //     // tooltip隐藏时复原地图
        //     myChart1.setOption({
        //         series: [
        //             {
        //                 zoom: 1, // 原始缩放比例
        //                 left: 'center'
        //             }
        //         ],
        //         geo:{
        //             zoom: 1,
        //             left: 'center'
        //         }
        //     });
        // });
        //
        // // 点击.reset按钮隐藏
        // $('.reset').mouseenter(function () {
        //     // 隐藏.reset按钮
        //     $('.reset').hide();
        // });

        // 将搜索框事件注册到window对象
        window.addEventListener('input', function(event) {
          if (event.target.id === 'searchBox') {
            var keyword = event.target.value;
            // 在这里进行搜索逻辑的处理
            $('.search-input').on('input', function() {
              var keyword = $(this).val().trim();
              search(keyword);
            });

            function search(keyword) {
              var items = $('.tooltip .list .item');
              items.removeClass('hidden');

              if (keyword !== '') {
                items.each(function() {
                  var itemText = $(this).text();
                  if (!itemText.includes(keyword)) {
                    $(this).addClass('hidden');
                  }
                });
              }
            }
          }
        });


        // 4.跟随屏幕自适应
        window.addEventListener("resize", function() {
          myChart1.resize({animation: {duration:1000}});
        });
      });
    }

    mapChart3()
    function mapChart3(){

      var myChart1 = echarts.init(document.getElementById('mapChart3'));
      var dataList;

      $.ajax({
        url: "<%=basePath%>/frontend/getFengXianByArea",
        data: {},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          dataList = res;
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
          visualMap: {
            min: 0,
            max: 100000,
            orient: 'horizontal',
            left: 'center',
            bottom: 5,
            showLabel: true,
            textStyle: {
              color: '#fff'
            },
            pieces: [{
              gt: 1000,
              label: "> 1000个",
              color: "#e64b45"
            }, {
              gte: 500,
              lte: 1000,
              label: "500 - 1000个",
              color: "#0a64b9"
            }, {
              gte: 100,
              lt: 500,
              label: "100 - 500个",
              color: "#0a64a5"
            }, {
              gte: 10,
              lt: 100,
              label: "10 - 100个",
              color: "#1979c0"
            }, {
              gte: 1,
              lt: 10,
              label: "1 - 10个",
              color: "#2e8ece"
            }],
            show: true
          },
          // 悬浮框
          tooltip: {
            padding: 0,
            position: ['150%', '20%'],
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
              for(var i = 0;i<dataList.length;i++){
                if(params.name==dataList[i].NAME){
                  var listHtml = '';

                  listHtml += '<div class="tooltip"> <div class="left">';
                  listHtml += '<div class="area">'+dataList[i].NAME+'</div>';
                  listHtml += '<div class="num"><span> ' + dataList[i].ALL_RISK + '</span>个</div>';
                  listHtml += '<div class="text">风险点</div></div>';
                  listHtml += '<div class="right">';
                  listHtml += '<div class="great"><img src="<%=basePath%>/frontend/images/great-icon.png" alt=""><p>重大风险<span>' + dataList[i].MAJOR_RISK + '</span></p ></div>';
                  listHtml += '<div class="larger"><img src="<%=basePath%>/frontend/images/larger-icon.png" alt=""><p>较大风险<span>' + dataList[i].MORE_RISK + '</span></p ></div>';
                  listHtml += '<div class="common"><img src="<%=basePath%>/frontend/images/common-icon.png" alt=""><p>一般风险<span>' + dataList[i].NORMAL_RISK + '</span></p ></div>';
                  listHtml += '<div class="low"><img src="<%=basePath%>/frontend/images/low-icon.png" alt=""><p>低风险<span>' + dataList[i].LOW_RISK + '</span></p ></div>';
                  listHtml += '</div></div>';
                  return listHtml;
                }
              }

            },
          },

          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: "none"
              },
              dataView: {
                readOnly: false
              },
              magicType: {
                type: ["line", "bar"]
              },
              restore: {},
              saveAsImage: {
                type: "png"
              }
            }
          },

          geo: {
            map: "hainan",
            // 底层添加边框
            itemStyle: {
              borderColor: "#dd0000",
              borderWidth: 5,
              shadowBlur: 20, // 阴影模糊大小
              shadowColor: "rgba(255,72,0,.8)" // 阴影颜色
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
                color: '#fff',
                emphasis: {// 这个是鼠标移上去就会选中的样式，鼠标mouseover
                  color: '#fff'
                },
              },
              // itemStyle: {
              //     areaColor: {
              //         type: 'linear',
              //         x: 0,
              //         y: 0,
              //         x2: 0,
              //         y2: 1,
              //         colorStops: [
              //             {
              //             offset: 0,
              //             color: '#000a89' // 渐变起始颜色
              //             },
              //             {
              //             offset: 1,
              //             color: '#000a90' // 渐变结束颜色
              //             }
              //         ]
              //     },
              //     borderColor: '#0011f2'
              // },
              itemStyle: {
                normal: {//未对地图作任何操作时的样式，加载想默认展示的样式
                  borderWidth: 1, //边框大小
                  borderColor: "#4394f0",
                  areaColor: "#005be3", //背景颜色
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
                  borderColor:'#ff4800',
                  areaColor:"#dd0000",
                },
                //重点下面，与点击事件不同，虽然也是点击选中，但是事件不一样
                selectedMode:"single", //选择模式，单选，只能选中一个地市
                select:{//这个就是鼠标点击后，地图想要展示的配置
                  disabled:true,//可以被选中
                  itemStyle:{//相关配置项很多，可以参考echarts官网

                  }
                },


              },
              data: dataList
            },

          ],
        };

        // 使用刚指定的配置项和数据显示图表
        myChart1.setOption(option);

        // // 监听click事件
        // myChart1.on('showTip', function (params) {
        //     // 点击地图区域时缩小地图
        //     myChart1.setOption({
        //         series: [
        //             {
        //                 zoom: 0.8, // 缩小的缩放比例
        //                 left: 0
        //             }
        //         ],
        //         geo:{
        //             zoom: 0.8,
        //             left: 0
        //         }
        //     });
        //     // 显示.reset按钮
        //     $('.reset').show();
        // });
        //
        // // 监听tooltip隐藏事件
        // myChart1.on('hideTip', function () {
        //     // tooltip隐藏时复原地图
        //     myChart1.setOption({
        //         series: [
        //             {
        //                 zoom: 1, // 原始缩放比例
        //                 left: 'center'
        //             }
        //         ],
        //         geo:{
        //             zoom: 1,
        //             left: 'center'
        //         }
        //     });
        // });
        //
        // // 点击.reset按钮隐藏
        // $('.reset').mouseenter(function () {
        //     // 隐藏.reset按钮
        //     $('.reset').hide();
        // });

        // 将搜索框事件注册到window对象
        window.addEventListener('input', function(event) {
          if (event.target.id === 'searchBox') {
            var keyword = event.target.value;
            // 在这里进行搜索逻辑的处理
            $('.search-input').on('input', function() {
              var keyword = $(this).val().trim();
              search(keyword);
            });

            function search(keyword) {
              var items = $('.tooltip .list .item');
              items.removeClass('hidden');

              if (keyword !== '') {
                items.each(function() {
                  var itemText = $(this).text();
                  if (!itemText.includes(keyword)) {
                    $(this).addClass('hidden');
                  }
                });
              }
            }
          }
        });


        // 4.跟随屏幕自适应
        window.addEventListener("resize", function() {
          myChart1.resize({animation: {duration:1000}});
        });
      });
    }


  })

  tabClick("#mapContainer", "#mapContent")
  function tabClick(tabContainer, tabContent) {
    // 鼠标点击切换选项卡
    $(tabContainer + ' .tab').click(function() {
      // 定义一个对象来存储已获取过的数据
      var dataCache = {};
      var tabId = $(this).data('tab');
      // 移除所有选项卡的active类
      $(tabContainer + ' .tab').removeClass('tabActive');

      // 给当前选项卡添加active类
      $(this).addClass('tabActive');

      // 隐藏所有选项卡内容
      $(tabContent + ' .content').removeClass('active').hide();

      // 显示对应选项卡的内容
      $(tabContent + ' #' + tabId).addClass('active').show();
    });
  }

  $('.search-input').on('input', function() {
    var keyword = $(this).val().trim();
    search(keyword);
  });

  function search(keyword) {
    var items = $('.tooltip .list .item');
    items.removeClass('hidden');

    if (keyword !== '') {
      items.each(function() {
        var itemText = $(this).text();
        if (!itemText.includes(keyword)) {
          $(this).addClass('hidden');
        }
      });
    }
  }
</script>
</body>
</html>