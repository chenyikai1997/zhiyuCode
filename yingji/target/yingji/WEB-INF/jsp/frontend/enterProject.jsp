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
  <title>应急信息</title>
  <link rel="stylesheet" href="<%=basePath%>frontend/css/reset.css">
  <link rel="stylesheet" href="<%=basePath%>frontend/css/public.css">
  <link rel="stylesheet" href="<%=basePath%>frontend/css/project.css">
<%--  <link rel="stylesheet" href="<%=basePath%>frontend/css/jquery.dataTables.css">--%>
  <title>Document</title>
  <style>
    /* body{
        width: 100%;
        height: 47.25rem;
        background-image: url(./images/project-bg.jpg);
        background-size: 100% 100%;
        background-repeat: no-repeat;
    } */
  </style>
</head>
<body style="overflow: auto !important; height: auto !important;">
<!-- 1.顶部标题 -->
<header>
  <div class="container">
    <div class="title">海南控股应急管理信息平台</div>
  </div>
</header>
<!-- 2. 项目信息 -->
<div class="project-container">
  <div class="project-name">${pd.ORG_NAME}</div>
  <div class="project-intro">${pd.REMARK}
  </div>
  <div class="monitor-list">
  </div>
  <div class="shuiwei-chart">
    <div class="title">监控点位</div>
    <div id="shuiwei-chart"></div>
  </div>
  <div class="yinhuan-charts">
    <div class="title">隐患台账</div>
    <div class="yinhuan-chart">
      <div id="yinhuan-pie"></div>
      <div id="zhenggai-percent"></div>
    </div>
    <div class="yinhuan-form">
      <table id="yinhuanDataTable">
        <thead>
        <tr>
          <th>组织机构</th>
          <th>隐患情况</th>
          <th>隐患类别</th>
          <th>隐患级别</th>
          <th>隐患因素</th>
          <th>整改期限</th>
          <th>填报单位</th>
          <th>填报人</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
<%--      <div id="#pagination1"></div>--%>
    </div>
  </div>
  <div class="fengxian-charts">
    <div class="title">安全风险</div>
    <div id="fengxian-pie"></div>
    <div class="fengxian-form">
      <table id="fengxianDataTable">
        <thead>
        <tr>
          <th>二级单位</th>
          <th>风险区域</th>
          <th>风险地址</th>
          <th>危险源</th>
          <th>事故类型</th>
          <th>风险等级</th>
          <th>危险源持续时间</th>
          <th>填报单位</th>

        </tr>
        </thead>
        <tbody>

        </tbody>
      </table>
<%--      <div id="#pagination2"></div>--%>
    </div>
  </div>
</div>
<script src="<%=basePath%>frontend/js/jquery.js"></script>
<script src="<%=basePath%>static/jquery/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>frontend/js/flexible.js"></script>
<script src="<%=basePath%>frontend/js/echarts.min.js"></script>
<%--<script src="<%=basePath%>frontend/js/jquery.dataTables.js"></script>--%>
<script>
  $(document).ready(function () {
    shuiweiChart()
    // 项目水位折线图
    function shuiweiChart(){
      var lineData;
      $.ajax({
        url: "<%=basePath%>/frontend/waterProject",
        data: {PROJECT_ID:'${pd.ORG_ID}'},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          lineData = res;
          if(lineData == '' || lineData == undefined){
            $(".shuiwei-chart").css("display","none");
          }
        },
      })

      // 模拟数据
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#shuiwei-chart'))
      // var dataDate = ['2022/12/13 06:00:00', '2022/12/13 16:00:00', '2023/2/13', '2023/3/13 06:00:00', '2023/4/13 06:00:00', '2023/5/13', '2023/6/13'].map(function(date){return date.replace(' ', '\n')})
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

            data:[]
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
            name: "当前水位",
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
            data: []
          },
        ]
      };
      if(lineData.length == undefined){
        for(i = 0; i < lineData[1].length; i++){
          option.xAxis[0].data.push(lineData[1][i].WATER_DATETIME)
        }
      }
      if(lineData.length == undefined){
        for(i = 0; i < lineData[1].length; i++){
          option.series[0].data.push(lineData[1][i].WATER_DATA)
        }
      }

      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });
    }

    yinhuanPie()
    // 隐患台账饼图
    function yinhuanPie(){
      var peiData;
      $.ajax({
        url: "<%=basePath%>/hkSafeFrontend/tongjiByProject",
        data: {PROJECT_NAME:'${pd.ORG_ID}'},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          peiData = res;
        },
      })

      let data = peiData;
      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#yinhuan-pie'))
      // 2. 指定配置项和数据
      option = {

        // legend: {
        //   right: 'center',
        //   top: "5%",
        //   textStyle: {
        //     color: '#fff',
        //     fontSize: 20,
        //   }
        // },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '65%'],
            center: ['50%', '50%'],
            data: data,
            labelLine: {
              length: '2%',
              length2: 60,
            },
            label: {
              textStyle: {
                color: '#fff',
                fontSize: 20,
              },
              // alignTo: 'labelLine',    //对其方式
              position: 'outside',
              formatter: (fp) => {
                return fp.data.name + '  ' + fp.data.value + '条' + '  ' + fp.percent + '%'

              },
              distanceToLabelLine: -40,
              rich: {
                num: {
                  fontSize: 18,
                },
                zb: {
                  fontSize: 20,
                }
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            z: 2,
          },
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: data,
            itemStyle: {
              opacity: 0,
            },
            label: {
              show: false,
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
                opacity: 1
              }
            },
            z: 3
          }
        ]
      };
      // for(i = 0; i < DATA.length; i++){
      //     option.series[0].data.push({
      //         name: DATA[i].NAME,
      //         value: DATA[i].VALUE
      //     })
      //     console.log(DATA[i]);
      // }
      // $.each(DATA, function (index, value) {
      //     option.series[0].data.push({
      //         name: DATA[index].NAME,
      //         value: DATA[index].VALUE
      //     })
      // });
      // 3. 把配置项给实例对象
      myChart.setOption(option)
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      })
    }

    zhenggaiPercent()
    // 隐患整改率仪表盘
    function zhenggaiPercent(){
      var peiData;
      $.ajax({
        url: "<%=basePath%>/hkSafeFrontend/tongjiByProjectOnPrecent",
        data: {PROJECT_NAME:'${pd.ORG_ID}'},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          peiData = res;
        },
      })
      var data = (peiData.more/peiData.total)*100;

      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#zhenggai-percent'))
      // 2. 指定配置项和数据
      option = {
        title: [{
          text: '隐患整改率',
          left: 'center',
          bottom: '20%',
          textStyle: {
            color: '#3488db',
            fontSize:22,
            fontFamily: 'cuhei',
            fontWeight: 600,

          }
        }],
        series: [
          {
            type: 'gauge',
            axisLine: {
              lineStyle: {
                width: 15,
                color: [
                  [
                    0.2, '#f9387f'
                  ],
                  [
                    0.8, '#3488db'
                  ],
                  [
                    1, '#42e4fb'
                  ]
                ],
              }
            },
            radius: '80%',
            markPoint: {
              // 仪表盘指针圆
              animation: false,
              silent: true,
              data: [{
                x: '50%',
                y: '50%',
                symbol: 'circle',
                symbolSize: 40,
                itemStyle: {
                  color: 'auto',
                },
              }, {
                x: '50%',
                y: '50%',
                symbol: 'circle',
                symbolSize: 20,
                itemStyle: {
                  color: '#fff'
                },
              }],
            },
            pointer: {
              // 仪表盘指针
              //icon:'none',
              width: 10,
              length: "60%",
              itemStyle: {
                color: 'auto'
              },
            },
            axisTick: {
              distance: 0,
              length: 5,
              lineStyle: {
                color: 'auto',
                width: 2
              }
            },
            splitLine: {
              distance: 0,
              length: 15,
              lineStyle: {
                color: 'auto',
                width: 4
              }
            },
            axisLabel: {
              color: '#3488db',
              distance: 30,
              fontSize: 20
            },
            detail: {
              //valueAnimation: true,
              formatter: '{value} %',
              color: 'white'
            },
            data: [{
              value: data.toFixed(2)
            }]
          },
        ]
      };
      // for(i = 0; i < DATA.length; i++){
      //     option.series[0].data.push({
      //         name: DATA[i].NAME,
      //         value: DATA[i].VALUE
      //     })
      //     console.log(DATA[i]);
      // }
      // $.each(DATA, function (index, value) {
      //     option.series[0].data.push({
      //         name: DATA[index].NAME,
      //         value: DATA[index].VALUE
      //     })
      // });
      // 3. 把配置项给实例对象
      myChart.setOption(option)
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      })
    }

    fengxianPie()
    // 风险等级饼图
    function fengxianPie(){
      var peiData;
      $.ajax({
        url: "<%=basePath%>/fengXianFrontend/getTongJiFengXian",
        data: {ORG_ID:'${pd.ORG_ID}'},
        type: "GET",
        text: "json",
        async: false,
        success: function(res){
          peiData = res;
        },
      })
      console.log("peiData",peiData)

      var zdData;
      var jdData;
      var ybData;
      var dfxData;
      if(peiData != null){
        zdData = peiData.zd;
        jdData = peiData.jd;
        ybData = peiData.yb;
        dfxData = peiData.dfx;
      }

      // 1. 实例化对象
      var myChart = echarts.init(document.querySelector('#fengxian-pie'))
      // 2. 指定配置项和数据
      const colorList1 = [ 'rgb(255,0,0,1.000)','', 'rgb(255,192,0,1.000)','', 'rgb(255,255,0,1.000)','', 'rgb(0,176,240,1.000)',]
      const colorList2 =  [ 'rgb(255,0,0, .5)','', 'rgb(255,192,0, .5)','', 'rgb(255,255,0, .5)', '','rgb(0,176,240,.5)',]
      let total = 0
      let dataList = []
      const moduleContent = { '重大风险': zdData, '较大风险': jdData, '一般风险': ybData ,'低风险': dfxData}
      let sum = 0
      const chartdata = []
      for (const i in moduleContent) {
        chartdata.push({
          name: i,
          value: moduleContent[i] || 1
        })
        console.log("sum=",moduleContent[i])
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
          right: '10%',
          top: 'center',
          width: '5%',
          textStyle:{
            color: '#fff',
            fontSize: 20
          }
        },
        title: {
          text: total,
          left: 'center',
          top: 'center',
          itemGap: 10,
          textStyle: {
            color: '#fff',
            fontSize: 30,
            fontWeight: 500
          },
          subtext: '共计',
          subtextStyle: {
            color: 'rgba(255,255,255,0.5)',
            fontSize: 20,
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
            if(params.data.name === ''){
              return '无'
            }else{
              return params.data.name + ':' + params.data.value
            }
          },
          // formatter: "{b}: {c} "+" | "+"{d}%",
          textStyle: {
            fontSize: 24,
            color: '#222'
          },
          borderColor: 'rgba(255,255,255,0.9)',
          backgroundColor: 'rgba(255,255,255,.8)',
          extraCssText: 'box-shadow: 2px 2px 4px 0px rgba(255,255,255,0.5);'
        },
        series: [
          {
            type: 'pie',
            radius: ['40%', '70%'],
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
                    fontSize: 18,
                    color: "#fff",
                    align: "center",
                    padding: [0, 0, 0, 0],
                  },
                  d: {
                    fontSize: 20,
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
          },
          // {
          // type: 'pie',
          // radius: ['70%', '80%'],
          // center: ['60%', '50%'],
          // hoverAnimation: false,
          // minAngle: 1,
          // emphasis: { scale: false },
          // label: {
          //     show: false
          // },
          // itemStyle: {
          //     normal: {
          //     color: function (params) {
          //         return colorList2[params.dataIndex]
          //     }
          //     }
          // },
          // data: data1,
          // z: 1
          // },
        ]
      }

      // 3. 把配置项给实例对象
      myChart.setOption(option);
      // 4.跟随屏幕自适应
      window.addEventListener("resize", function() {
        myChart.resize({animation: {duration:1000}});
      });

    }


    rectifyList()
    function rectifyList(){
      <%--var data;--%>
      <%--$.ajax({--%>
      <%--  url: "<%=basePath%>/frontend/listForProject",--%>
      <%--  data: {PROJECT_NAME:'${pd.ORG_ID}'},--%>
      <%--  type: "GET",--%>
      <%--  text: "json",--%>
      <%--  async: false,--%>
      <%--  success: function(res){--%>
      <%--    data = res;--%>
      <%--  },--%>
      <%--})--%>

      // let data = [
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，消防安全规章制度不全，未按照本公司消防安全管理制度要求制定消防安全教育与培训制度、可燃及易燃易爆危险品管理制度、用火、用电、用气管理制度、消防安全检查制度等制度。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，9#楼楼宇内作业场所未设置明显的疏散指示标识。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，电动车车棚巡检记录未及时更新。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，施工现场的消火栓泵未采用专用消防配电线路。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，对施工人员消防安全教育培训和消防安全技术交底无记录",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，临时消防给水系统的消火栓泵、室内消防竖管等，未设有醒目标识。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目楼层内消防水带缺失。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，食堂外易燃垃圾较多需及时清理。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，临时消防设施未制定维护保养计划和维护保养记录。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，应急演练报告无演练人员签到表。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，9#楼第8层楼梯处的室内消火栓接口点， 未按规范要求设置每个点不少于2 套的消防水枪、 水带及软管。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，动火证中动火区域未划分，动火作业制度缺失。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，项目西侧临时消防车道堆放大量模板等材料遮挡占用。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，本年度应急演练计划无第四季度演练计划。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   },
      //   {
      //     "整改期限": "2023-06-11",
      //     "隐患情况": "椰东江岸项目，安全员配备不足。",
      //     "组织机构": "海南海控中能建工程有限公司",
      //     "填报人": "史贻晶",
      //     "填报单位": "46f3c5622c5243e89cd58f0fc19741de"
      //   }
      // ]
      <%--var rectifyInfoList =   ${rectifyInfoList};--%>
      // $.each(rectifyInfoList,  function (index, item) {
      //   // console.log(index);
      //   console.log(item);
      // });
      // console.log(data);
      // $('#fengxianDataTable').DataTable({
      //
      //
      //   data: data,
      //   language: {
      //     "sProcessing": "处理中...",
      //     "sLengthMenu": "显示 _MENU_ 项结果",
      //     "sZeroRecords": "没有匹配结果",
      //     "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
      //     "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
      //     "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
      //     "sInfoPostFix": "",
      //     "sSearch": "搜索:",
      //     "sUrl": "",
      //     "sEmptyTable": "表中数据为空",
      //     "sLoadingRecords": "载入中...",
      //     "sInfoThousands": ",",
      //     "oPaginate": {
      //       "sFirst": "首页",
      //       "sPrevious": "上页",
      //       "sNext": "下页",
      //       "sLast": "末页"
      //     },
      //     "oAria": {
      //       "sSortAscending": ": 以升序排列此列",
      //       "sSortDescending": ": 以降序排列此列"
      //     }
      //   },
      //   // bFilter: true,//过滤搜索
      //   columns : [
      //       { data: '组织机构' },
      //       { data: '整改期限',
      //
      //       },
      //       { data: '隐患情况',
      //         render: function (data, type, row) {
      //           //数据字符超过10截取
      //           if (data.length > 10) {
      //             return "<span title='" + data + "' style='text-decoration: none;'>" + data.trim().substr(0, 10) + "..." + "</span>";
      //           } else {
      //             return data;
      //           }
      //         }
      //         },
      //       { data: '填报人' },
      //       { data: '填报单位' }
      //     // { data: '隐患类别' },
      //     // { data: '隐患级别' },
      //     // { data: '隐患因素' },
      //   //
      //   //
      //   //
      //   ]
      // })



      var listHtml = '';

      listHtml += '<c:forEach items="${rectifyInfoList}" var="var" varStatus="vs">';
      listHtml += '<tr>';
      listHtml += '<td><c:forEach items="${orgList}" var="map" varStatus="vs"><c:if test="${var.ORG_ID == map.ORG_ID}">${map.ORG_NAME}</c:if></c:forEach></td>';
      listHtml += '<td>${var.FILE_CODE}</td>';
      listHtml += '<td><c:forEach items="${classifyMap}" var="map" varStatus="vs"><c:if test="${var.HIDDEN_DANGER_CLASSIFY == map.key}">${map.value}</c:if></c:forEach></td>';
      listHtml += '<td><c:forEach items="${levelMap}" var="map" varStatus="vs"><c:if test="${var.HIDDEN_DANGER_LEVEL == map.key}">${map.value}</c:if></c:forEach></td>';
      listHtml += '<td><c:forEach items="${factorMap}" var="map" varStatus="vs"><c:if test="${var.HIDDEN_DANGER_FACTOR == map.key}">${map.value}</c:if></c:forEach></td>';
      listHtml += '<td>${var.PLAN_COMPLETE_TIME}</td>';
      listHtml += '<td><c:forEach items="${orgList}" var="map" varStatus="vs"><c:if test="${var.REPORTING_UNIT_FIRST == map.ORG_ID}">${map.ORG_NAME}</c:if></c:forEach></td>';
      listHtml += '<td>${var.REPORTING_PERSON_FIRST}</td>';
      listHtml += '</tr></c:forEach>';

      $("#yinhuanDataTable tbody").append(listHtml)
    }

    monitorList()
    function  monitorList(){

      var listHtml = '';

      listHtml += '<c:forEach items="${monitorList}" var="var" varStatus="vs">';
      listHtml += '<li>';
      listHtml += '<iframe frameborder="no" scrolling="no" border="0" width="390px" id="iframe1" height="280px" src="<%=basePath%>yjfhmFrontend/play.html?MONITOR_ID=${var.MONITOR_ID}"></iframe>';
      // listHtml += '<input type="radio" name="movie" id="movie1" value="iframe1">';
      listHtml += '</li></c:forEach>';

      $(".monitor-list").append(listHtml);
    }

    fengXianList()
    function fengXianList(){
      var listHtml = '';

      listHtml += '<c:forEach items="${fengXianList}" var="var" varStatus="vs">';
      listHtml += '<tr>';
      listHtml += '<td><c:forEach items="${orgList}" var="map" varStatus="vs"><c:if test="${var.SECOND_UNIT == map.ORG_ID}">${map.ORG_NAME}</c:if></c:forEach></td>';

      listHtml += '<td><c:forEach items="${areaList}" var="map" varStatus="vs">';
      listHtml += '<c:if test="${map.DICTIONARIES_ID == var.FENGXIAN_AREA}">${map.NAME}</c:if>';
      listHtml += '<c:forEach items="${map.subDict}" var="submap" varStatus="vs1">';
      listHtml += '<c:if test="${submap.DICTIONARIES_ID == var.FENGXIAN_AREA}">${submap.NAME}</c:if></c:forEach>';
      listHtml += '<c:forEach items="${submap.subDict}" var="subsubmap" varStatus="vs2">';
      listHtml += '<c:if test="${subsubmap.DICTIONARIES_ID == var.FENGXIAN_AREA}">${subsubmap.NAME}</c:if></c:forEach>';
      listHtml += '</c:forEach></td>';

      listHtml += '<td>${var.FENGXIAN_ADDRESS}</td>';
      listHtml += '<td>${var.FENGXIAN_HAZARD}</td>';
      listHtml += '<td><c:forEach items="${accidentTypeMap}" var="map" varStatus="vs"><c:if test="${var.FENGXIAN_ACCIDENT_TYPE == map.bIANMA}">${map.nAME}</c:if></c:forEach></td>';
      listHtml += '<td><c:forEach items="${riskLevelMap}" var="map" varStatus="vs"><c:if test="${var.FENGXIAN_LEVEL == map.bIANMA}">${map.nAME}</c:if></c:forEach></td>';
      listHtml += '<td>${var.HAZARD_DURATION}</td>';
      listHtml += '<td><c:forEach items="${orgList}" var="map" varStatus="vs"><c:if test="${var.RESPONSIBILITY_UNIT == map.ORG_ID}">${map.ORG_NAME}</c:if></c:forEach></td>';

      listHtml += '</tr></c:forEach>';

      $("#fengxianDataTable tbody").append(listHtml)
    }

  });
</script>
</body>
</html>