<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>安全隐患管理模块</title>
	<link rel="stylesheet" href="frontend/HK-safe/css/index.css?v=1.0.1">
</head>
<body>
	
	<!-- 1. 头部 -->
	<header>
		<h1>安全隐患管理模块</h1>
		<div class="showTime"></div>
	</header>

	<!-- 2. 主体 -->
	<div class="main-box">
		<div class="column-l">
			<!-- 隐患总数 -->
			<div class="panel-t">
				<div class="danger-all">
					<img src="frontend/HK-safe/images/danger1.png" alt="">
					<p id="dangerNum">0</p>
					<div class="title">隐患总数</div>
				</div>
				<div class="detail">
					<div class="finish">
						<img src="frontend/HK-safe/images/danger2.png" alt="">
						<p id="finishNum">0</p>
						<div class="text">
							<span id="green"></span>
							<div class="title">已完成总数</div>
						</div>
					</div>
					<div class="untreated">
						<img src="frontend/HK-safe/images/danger3.png" alt="">
						<p id="untreatedNum">0</p>
						<div class="text">
							<span id="yellow"></span>
							<div class="title">整改中总数</div>
						</div>
					</div>
					<div class="overdue">
						<img src="frontend/HK-safe/images/danger4.png" alt="">
						<p id="overdueNum">0</p>
						<div class="text">
							<span id="red"></span>
							<div class="title">逾期总数</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 隐患整改 -->
			<div class="panel-c rectify">
				<h2>隐患整改统计</h2>
				<div class="rectify-chart"></div>
				<div class="panel-footer"></div>
			</div>
			<!-- 隐患类别排名 -->
			<div class="panel-b category">
				<h2>各隐患类别排名</h2>
				<div class="category-chart"></div>
				<div class="panel-footer"></div>
			</div>
		</div>
		<div class="column-c">
			<div class="panel-t">
				<div class="year">
					<p id="danger-Y">0</p>
					<h3>年度隐患</h3>
				</div>
				<div class="quarter">
					<p id="danger-Q">0</p>
					<h3>季度隐患</h3>
				</div>
				<div class="month">
					<p id="danger-M">0</p>
					<h3>月度隐患</h3>
				</div>
			</div>
			<div class="panel-c">
				<div class="danger">
					<div class="great">
						<h2>重大隐患</h2>
						<p id="danger-great">0</p>
					</div>
					<img src="frontend/HK-safe/images/danger-ball.jpg" alt="">
					<div class="common">
						<h2>一般隐患</h2>
						<p id="danger-common">0</p>
					</div>
				</div>
				<div class="box" id="liqiud-content">
					<%-- <div class="liqiud-box">
						<div class="liqiud" id="liqiud1"></div>
						<div class="text">海南机场</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud2"></div>
						<div class="text"><a href="<%=basePath%>frontend/pc/subview?ORG_CODE=0100">海控能建</a></div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud3"></div>
						<div class="text">海控能源</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud4"></div>
						<div class="text">海南发展</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud5"></div>
						<div class="text">海南建设</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud6"></div>
						<div class="text">水电集团</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud7"></div>
						<div class="text">海控贸易</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud8"></div>
						<div class="text">莺歌海盐场</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud9"></div>
						<div class="text">美丽乡村</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud10"></div>
						<div class="text">全球精品</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud11"></div>
						<div class="text">海口空港</div>
					</div>
					<div class="liqiud-box">
						<div class="liqiud" id="liqiud12"></div>
						<div class="text">地产集团</div>
					</div> --%>
				</div>
				
			</div>
			<div class="panel-b trend">
				<h2>隐患数量趋势</h2>
				<div class="trend-chart"></div>
				<div class="panel-footer"></div>
			</div>
		</div>
		<div class="column-r">
			<div class="panel-t rectifyAll">
				<h2>二级公司隐患总数</h2>
				<div class="rectifyAll-chart"></div>
				<div class="panel-footer"></div>
			</div>
			<div class="panel-c rectification">
				<h2>二级公司整改率</h2>
				<div class="rectification-chart"></div>
				<div class="panel-footer"></div>
			</div>
			<div class="panel-b factor">
				<h2>各隐患因素统计</h2>
				<div class="factor-chart"></div>
				<div class="panel-footer"></div>
			</div>
		</div>
	</div>

	<script src="frontend/HK-safe/js/jquery-3.1.1.min.js"></script>
	<script src="frontend/HK-safe/js/flexible.js"></script>
	<script src="frontend/HK-safe/js/echarts.min.js"></script>
	<script src="frontend/HK-safe/js/echarts-liquidfill.js"></script>
	<!-- <script src="frontend/HK-safe/js/chart.js"></script> -->
	<script src="frontend/HK-safe/js/getTime.js"></script>
	<script src="frontend/HK-safe/js/liquid.js"></script>
	
	<!--头部开始-->
		<%@ include file="getDate.jsp"%>
	<!--头部结束-->
	
	<script>
		$(function(){
			// 获取统计总数的数据
			var dangerAllData = dangerAll("");
			$("#dangerNum").html(dangerAllData.COUNT_ALL);
			$("#finishNum").html(dangerAllData.IS_COMPLETE);
			$("#untreatedNum").html(dangerAllData.NOT_COMPLETE);
			$("#overdueNum").html(dangerAllData.OVER_TIME);
			$("#danger-Y").html(dangerAllData.YEAR);
			$("#danger-Q").html(dangerAllData.QUARTER);
			$("#danger-M").html(dangerAllData.MONTH);
			$("#danger-great").html(dangerAllData.ZHONG_DA);
			$("#danger-common").html(dangerAllData.YI_BAN);
			
			// 获取隐患整改统计
			var dangerCompleteData = tongjiByStage("","");
			// for (var i = 0; i < dangerCompleteData.dayArray.length; i++) {
			// 	dangerCompleteData.dayArray[i] = dangerCompleteData.dayArray[i].substr(5);
			// }
			rectify(dangerCompleteData);
			
			// 根据隐患类别统计
			var classifyCompleteData = classifyComplete("");
			category(classifyCompleteData.array);
			
			// 按隐患因素统计，mark123
			var factorCompleteData = factorComplete("");
			factor(factorCompleteData.array);
			
			// 隐患数量折线图，mark123
			var dangerTrendData = dangerComplete("","7");
			trend(dangerTrendData);
			
			// 获取二级单位隐患情况
			var erjiCompleteData = erjiComplete();
			rectifyAll(erjiCompleteData.totalArr);// 渲染二级公司隐患总数
			rectification(erjiCompleteData.rateArr);// 渲染二级整改率柱状图
			// 渲染二级公司水球
			var orgList = erjiCompleteData.orgList;
			var orgHtml = '';
			$.each(orgList, function(index, item){
				orgHtml += '<div class="liqiud-box" onclick="goUrl(\'<%=basePath%>hkSafeFrontend/subview?ORG_CODE=' + item.ORG_CODE + '\')">';
				orgHtml += '<div class="liqiud" id="liqiud' + (index+1) + '" rate="' + item.rate + '"></div>';
				orgHtml += '<div class="text">' + item.ORG_NAME_SHORT + '</div>';
				orgHtml += '</div>';
				if (index >= 11) {
					return false;
				}
			});
			$("#liqiud-content").html(orgHtml);
			makeLiquid();
			
			$('.column-l p, .column-c p').each(function () {
					$(this).prop('Counter',0).animate({
					Counter: $(this).text()
				}, {
					duration: 1000,
					easing: 'swing',
					step: function (now) {
						$(this).text(Math.ceil(now));
					}
				});
			});
		});
		
		function goUrl(url) {
			window.location.href = url;
		}
	</script>
	
	<script type="text/javascript">
		// 隐患整改柱状图
		function rectify(data){
			console.log("rectify",data);
		  // 1. 实例化对象
		  var myChart = echarts.init(document.querySelector('.rectify-chart'))
		  var app = {}
		      const posList = [
		      'left',
		      'right',
		      'top',
		      'bottom',
		      'inside',
		      'insideTop',
		      'insideLeft',
		      'insideRight',
		      'insideBottom',
		      'insideTopLeft',
		      'insideTopRight',
		      'insideBottomLeft',
		      'insideBottomRight'
		  ];
		  app.configParameters = {
		      rotate: {
		          min: -90,
		          max: 90
		      },
		      align: {
		          options: {
		              left: 'left',
		              center: 'center',
		              right: 'right'
		          }
		      },
		      verticalAlign: {
		          options: {
		              top: 'top',
		              middle: 'middle',
		              bottom: 'bottom'
		          }
		      },
		      position: {
		          options: posList.reduce(function (map, pos) {
		              map[pos] = pos;
		              return map;
		          }, {})
		      },
		      distance: {
		          min: 0,
		          max: 100
		      }
		  };
		      app.config = {
		          rotate: 0,
		          align: 'cennter',
		          verticalAlign: 'top',
		          position: 'top',
		          distance: 20,
		      onhange: function () {
		          const labelOption = {
		              rotate: app.config.rotate,
		              align: app.config.align,
		              verticalAlign: app.config.verticalAlign,
		              position: app.config.position,
		              distance: app.config.distance
		        };
		        myChart.setOption({
		          series: [
		            {
		              label: labelOption
		            },
		            {
		              label: labelOption
		            },
		            {
		              label: labelOption
		            },
		            {
		              label: labelOption
		            }
		          ]
		        });
		      }
		    };
		    const labelOption = {
		      show: false,
		      position: app.config.position,
		      distance: app.config.distance,
		      align: app.config.align,
		      verticalAlign: app.config.verticalAlign,
		      rotate: app.config.rotate,
		      
		      fontSize: 12,
		      color: '#fff',
		      rich: {
		        name: {}
		      }
		    };
	
		  // 2. 指定配置项和数据
		  // var option =  {
		  //           color: ["#a0eb7d", "#eb7d7d", "#ebca7d"],
		  //           tooltip: {
		  //             trigger: 'axis',
		  //             axisPointer: {
		  //               type: 'shadow'
		  //             }
		  //           },
		  //           // 修改图标大小
		  //           grid: {
		  //               left: '0',
		  //               top: '15%',
		  //               right: '10%',
		  //               bottom: '0',
		  //               containLabel: true
		  //           },
		  //           legend: {
		  //             barBorderRadius: [10, 10, 0, 0],
		  //             data: [
		  //               {
		  //                   name: '已处理',
		  //                   textStyle:{
		  //                       color: '#fff'
		  //                   }
		  //               },
		  //               {
		  //                   name: '未处理',
		  //                   textStyle:{
		  //                       color: '#fff'
		  //                   }
		  //               },
		  //               {
		  //                   name: '逾期',
		  //                   textStyle:{
		  //                       color: '#fff'
		  //                   }
		  //               },
		  //           ]
		  //           },
		  //           toolbox: {
		  //             show: true,
		  //             orient: 'vertical',
		  //             left: 'right',
		  //             top: 'center',
		  //             feature: {
		  //               mark: { show: true },
		  //               dataView: { show: true, readOnly: false },
		  //               magicType: { show: true, type: ['line', 'bar', 'stack'] },
		  //               restore: { show: true },
		  //               saveAsImage: { show: true }
		  //             }
		  //           },
		  //           xAxis: [
		  //             {
		  //               type: 'category',
		  //               axisTick: { show: false },
		  //               data: data.dayArray,
		  //               axisLine:{
		  //                   lineStyle:{
		  //                       color: '#fff'
		  //                   }
		  //               },
		  //             },
		  //           ],
		  //           yAxis: [
		  //             {
		  //               type: 'value',
		  //               axisLine:{
		  //                   lineStyle:{
		  //                       color: '#fff'
		  //                   }
		  //               },
		  //               // 修改y轴分割线的颜色
		  //             splitLine: {
		  //               lineStyle: {
		  //                 color: "#012f4a"
		  //                   }
		  //               }
		  //             }
		  //           ],
		  //           series: [
		  //             {
		  //               name: '已处理',
		  //               type: 'bar',
		  //               label: labelOption,
		  //               emphasis: {
		  //                 focus: 'series'
		  //               },
		  //               data: data.finishArray,
		  //               itemStyle: {
		  //                 barBorderRadius: [5, 5, 0, 0]
		  //               }
		  //             },
		  //             {
		  //               name: '未处理',
		  //               type: 'bar',
		  //               label: labelOption,
		  //               emphasis: {
		  //                 focus: 'series'
		  //               },
		  //               data: data.unfinishArray,
		  //               itemStyle: {
		  //                 barBorderRadius: [5, 5, 0, 0]
		  //               }
		  //             },
		  //             {
		  //               name: '逾期',
		  //               type: 'bar',
		  //               label: labelOption,
		  //               emphasis: {
		  //                 focus: 'series'
		  //               },
		  //               data: data.overdueArray,
		  //               itemStyle: {
		  //                 barBorderRadius: [5, 5, 0, 0]
		  //               }
		  //             }
		  //           ]
		  //         };
		  var option = {
				title: {
					left: 'center'
				},
				tooltip: {
					trigger: 'item'
				},
				legend: {
					show: false,
					orient: 'horizontal',
					top: '0',
					textStyle: {
						color: '#fff'
					}
				},
				series: [
					{
						name: '',
						type: 'pie',
						// color: ['red', 'blue', 'yellow', 'pink'],
						radius: '60%',
						center: ['50%', '50%'],
						label: {
							normal : {
								formatter: '{b}: ({d}%)',
								textStyle:{
									fontWeight : 'normal',
									fontSize : 10,
									color: '#fff',
								}
							}
						},
						data: [
							{ name:'整改中', value: 1 },
							{ name:'整改完成', value: 1 },
							{ name:'整改逾期', value: 1 },
							{ name:'逾期完成', value: 1 }
						],
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};
		           for(var i = data.length-1; i >= 0; i--){
					   option.series[0].data[0].value = data[0].zhenggaizhong;
					   option.series[0].data[1].value = data[0].zhenggaiwancheng;
					   option.series[0].data[2].value = data[0].zhenggaiyuqi;
					   option.series[0].data[3].value = data[0].yuqiwancheng;

					   // if(data[i].name = 'zhenggaizhong'){
						//    option.series[0].data[0].value = data[0].zhenggaizhong;
					   // }
					   // else if(data[i].name = 'zhenggaiwancheng'){
						//    option.series[0].data[0].value = data[0].zhenggaiwancheng;
					   // }
					   // else if(data[i].name = 'zhenggaiyuqi'){
						//    option.series[0].data[0].value = data[0].zhenggaiyuqi;
					   // }
					   // else if(data[i].name = 'yuqiwancheng'){
						//    option.series[0].data[0].value = data[0].yuqiwancheng;
					   // }
		          };
					/*
				   for(var i = 0; i < data.unfinish.length; i++){
					 option.series[1].data.push(data.unfinish[i].value)
				   };
				   for(var i = 0; i < data.overdue.length; i++){
					 option.series[2].data.push(data.overdue[i].value)
				   }; */
		  // 3. 把配置项给实例对象
		  myChart.setOption(option)
		  // 4.跟随屏幕自适应
		  window.addEventListener("resize", function() {
		      myChart.resize({animation: {duration:1000}});
		  })
		}
		
		// 隐患排名饼图
		function category(data){
		  // 1. 实例化对象
		  var myChart = echarts.init(document.querySelector('.category .category-chart'))

		  // 2. 指定配置项和数据
		  option = {
		    title: {
		      left: 'center'
		    },
		    tooltip: {
		      trigger: 'item'
		    },
		    legend: {
		      show: false,
		      orient: 'horizontal',
		      top: '0',
		      textStyle: {
		        color: '#fff'
		      }
		    },
		    series: [
		      {
		        name: '各隐患类别排名',
		        type: 'pie',
		        radius: '60%',
		        center: ['50%', '50%'],
		        label: {
		          normal : {
		            formatter: '{b}: ({d}%)',
		            textStyle:{
		              fontWeight : 'normal',
		              fontSize : 10,
		              color: '#fff',
		            }
		            }
		        },
		        data: data,
		        emphasis: {
		          itemStyle: {
		            shadowBlur: 10,
		            shadowOffsetX: 0,
		            shadowColor: 'rgba(0, 0, 0, 0.5)'
		          }
		        }
		      }
		    ]
		  };
		  // 3. 把配置项给实例对象
		  myChart.setOption(option)
		  // 4.跟随屏幕自适应
		  window.addEventListener("resize", function() {
		      myChart.resize({animation: {duration:1000}});
		  })
		}
		
		// 隐患因素饼图
		function factor(data){
		  // 1. 实例化对象
		  var myChart = echarts.init(document.querySelector('.factor .factor-chart'))
		  // 2. 指定配置项和数据
		  option = {
		  tooltip: {
		    trigger: 'item',
		  },
		  // legend: {
		  //   top: '0%',
		  //   left: 'center',
		  //   textStyle: {
		  //     color: '#fff',
		  //   }
		  // },
		  series: [
		    {
		      name: '各隐患隐患因素统计',
		      type: 'pie',
		      center: ['55%', '60%'],
		      radius: ['30%', '65%'],
		      avoidLabelOverlap: false,
		      // itemStyle: {
		      //   borderRadius: 10,
		      // },
		      label: {
		        show: false,
		        position: 'center'
		      },
				label: {
					show: true,
					formatter: '{b}: {c}',
					textStyle: {
						fontSize: 10,
						color: '#fff'
					}
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
		        show: true
		      },
		      data: data
		    }
		  ]
		};
		  // 3. 把配置项给实例对象
		  myChart.setOption(option)
		  // 4.跟随屏幕自适应
		  window.addEventListener("resize", function() {
		      myChart.resize({animation: {duration:1000}});
		  })
		}
		
		// 隐患趋势折线图
		function trend(data){
		  // 1. 实例化对象
		  var myChart = echarts.init(document.querySelector('.trend .trend-chart'))
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
		  // 3. 把配置项给实例对象
		  myChart.setOption(option);
		  // 4.跟随屏幕自适应
		  window.addEventListener("resize", function() {
		      myChart.resize({animation: {duration:1000}});
		  });
		}
		
		// 二级隐患总数柱状图
		function rectifyAll(data){
		  // 1. 实例化对象
		  var myChart = echarts.init(document.querySelector('.rectifyAll .rectifyAll-chart'))

		  
		  // 2. 指定配置项和数据
		  option = {
		    color: ["#cf7f2ffc"],
		    // title: {
		    //   text: 'World Population'
		    // },
		    // tooltip: {
		    //   trigger: 'axis',
		    //   axisPointer: {
		    //     type: 'shadow'
		    //   }
		    // },
		    legend: {},
		    grid: {
		      top: '1%',
		      left: '1%',
		      right: '10%',
		      bottom: '0',
		      containLabel: true
		    },
		    xAxis: {
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
		    dataZoom: [//滑动条
                {
                    yAxisIndex: 0,//这里是从X轴的0刻度开始
                    show: false,//是否显示滑动条，不影响使用
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    startValue: 0, // 从头开始。
                    endValue: 9  // 一次性展示6个。
                }
            ],
		    yAxis: {
		      type: 'category',
		      data: [],
		      inverse: "true",
		      axisLabel: {
		        textStyle: {
		          color: "rgba(255,255,255,.6)",
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
		              color: '#ff884d'
		            }, 
		            {
		              offset: 0,
		              color: '#ffc080'
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
		        }
		      },
		    ]
		  };
		  for(var i = 0; i < data.length; i++){
		    option.yAxis.data.push(data[i].name)
		    option.series[0].data.push(data[i].value)
		  }
		  // 3. 把配置项给实例对象
		  myChart.setOption(option)
		  // 4.跟随屏幕自适应
		  window.addEventListener("resize", function() {
		      myChart.resize({animation: {duration:1000}});
		  })
		}
		
		// 二级整改率柱状图
		function rectification(data){
		  // 1. 实例化对象
		  var myChart = echarts.init(document.querySelector('.rectification .rectification-chart'))
		  // 2. 指定配置项和数据
		  option = {
		    color: ["#cf7f2ffc"],
		    // title: {
		    //   text: 'World Population'
		    // },
		    // tooltip: {
		    //   trigger: 'axis',
		    //   axisPointer: {
		    //     type: 'shadow'
		    //   }
		    // },
		    legend: {},
		    grid: {
		      top: '1%',
		      left: '1%',
		      right: '10%',
		      bottom: '0',
		      containLabel: true
		    },
		    xAxis: {
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
		    dataZoom: [//滑动条
                {
                    yAxisIndex: 0,//这里是从X轴的0刻度开始
                    show: false,//是否显示滑动条，不影响使用
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    startValue: 0, // 从头开始。
                    endValue: 9  // 一次性展示6个。
                }
            ],
		    yAxis: {
		      type: 'category',
		      data: [],
		      inverse: "true",
		      axisLabel: {
		        textStyle: {
		          color: "rgba(255,255,255,.6)",
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
		              color: '#4d76ff'
		            }, 
		            {
		              offset: 0,
		              color: '#80aaff'
		            }
		          ]),
		          barBorderRadius: [10, 10, 10, 10]
		          }
		        },
		        label: {
		          normal: {
		            show: true,
		            position: "outside",
		            formatter: "{c}%",
		            textStyle: {
		              color: '#fff',
		            }
		          }
		        }
		      },
		    ]
		  };
		  for(var i = 0; i < data.length; i++){
		    option.yAxis.data.push(data[i].name)
		    option.series[0].data.push(data[i].value)
		  }
		  // 3. 把配置项给实例对象
		  myChart.setOption(option)
		  // 4.跟随屏幕自适应
		  window.addEventListener("resize", function() {
		      myChart.resize({animation: {duration:1000}});
		  })
		}
	</script>
</body>
</html>