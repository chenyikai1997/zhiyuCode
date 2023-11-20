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
  <link rel="stylesheet" href="<%=basePath%>frontend/css/index.css">
</head>
<body>
<!-- 1. 顶部 -->
<header>
  <div class="header-title">安全风险管理模块</div>
</header>

<div class="table-title">
  <span>风险点管理统计表</span>
</div>
<div class="select-container">
  <!-- 风险下拉框 -->
  <div class="select-box">
    <div class="title">按风险等级筛选：</div>
    <select id="fengxianSelect">
      <option value="all">全部</option>
      <option value="重大风险">重大风险</option>
      <option value="较大风险">较大风险</option>
      <option value="一般风险">一般风险</option>
      <option value="低风险">低风险</option>
    </select>
  </div>
  <!-- 区域下拉框 -->
  <div class="select-box">
    <div class="title">按区域筛选：</div>
    <select id="areaSelect">
      <option value="all">全部</option>
      <option value="海口市">海口市</option>
      <option value="龙华区">龙华区</option>
      <option value="美兰区">美兰区</option>
      <option value="秀英区">秀英区</option>
      <option value="琼山区">琼山区</option>
      <option value="天涯区">天涯区</option>
      <option value="吉阳区">吉阳区</option>
      <option value="海棠区">海棠区</option>
      <option value="天涯区">天涯区</option>
      <option value="文昌市">文昌市</option>
      <option value="三亚市">三亚市</option>
      <option value="临高县">临高县</option>
      <option value="澄迈县">澄迈县</option>
      <option value="定安县">定安县</option>
      <option value="琼海市">琼海市</option>
      <option value="儋州市">儋州市</option>
      <option value="屯昌市">屯昌市</option>
      <option value="昌江县">昌江县</option>
      <option value="白沙县">白沙县</option>
      <option value="琼中县">琼中县</option>
      <option value="万宁市">万宁市</option>
      <option value="东方市">东方市</option>
      <option value="五指山市">五指山市</option>
      <option value="保亭县">保亭县</option>
      <option value="陵水县">陵水县</option>
      <option value="东方市">东方市</option>
      <option value="乐东县">乐东县</option>
      <option value="文昌市">文昌市</option>
    </select>
  </div>
  <!-- 二级单位下拉框 -->
<%--  <div class="select-box">--%>
<%--    <div class="title">按二级单位筛选：</div>--%>
<%--    <select id="secondUnitSelect">--%>
<%--      <option value="all">全部</option>--%>
<%--    </select>--%>
<%--  </div>--%>
</div>
<div class="dataTable">

  <table id="dataTable">
    <thead>
    <tr>
      <th>二级单位</th>
      <th>三级单位(项目)</th>
      <th>地址</th>
      <th>区域</th>
      <th>危险源</th>
      <th>可能导致的事故类型</th>
      <th>风险等级</th>
      <th>控制措施</th>
      <th>应急措施</th>
      <th>危险源持续时间</th>
      <th>管理层级</th>
      <th>责任单位</th>
      <th>责任人</th>
      <th>责任人联系方式</th>
      <th>识别时间</th>
      <th>起止时间</th>
    </tr>
    </thead>
    <tbody id="dataList">
    </tbody>
  </table>
</div>

<script src="<%=basePath%>frontend/js/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>frontend/js/flexible.js"></script>
<script>
  $(document).ready(function() {
    //ajax请求数据
    debugger;
    var list = [];
    $.ajax({
      url: "<%=basePath%>fengXianFrontend/listFengXian",
      data: {ORG_ID:'${pd.ORG_ID}'},
      type: "POST",
      text: "json",
      async: false,
      success: function(res){
        debugger;
        list = res;
      },
    })

    //二级单位下拉框赋值
    var secondList = ${secondList};
    secondList.forEach(function (val,index){
      var secondUnitHtml = '<option value="'+val.nAME+'">' + val.nAME + '</option>';
      $("#secondUnitSelect").append(secondUnitHtml)
    })

    //列表拼接
      var dataList = '';

      dataList += '<c:forEach items="${varList}" var="var" varStatus="vs"><tr>';

      dataList += '<td secondunit=" <c:forEach items="${secondUnit}" var="map" varStatus="vs"><c:if test="${map.ORG_ID == var.SECOND_UNIT}">${map.ORG_NAME}</c:if></c:forEach>">';
      dataList +='<c:forEach items="${secondUnit}" var="map" varStatus="vs">';
      dataList +='<c:if test="${map.ORG_ID == var.SECOND_UNIT}">${map.ORG_NAME}</c:if>';
      dataList +='</c:forEach></td>';

      dataList += '<td><c:forEach items="${thirdUnit}" var="map" varStatus="vs"><c:if test="${map.ORG_ID == var.THIRD_UNIT}">${map.ORG_NAME}</c:if></c:forEach></td>';

      dataList += `<td> ${var.FENGXIAN_ADDRESS}</td>`;

      dataList += '<td area="';
      dataList += '<c:forEach items="${areaList}" var="map" varStatus="vs">';
      dataList += '<c:if test="${map.DICTIONARIES_ID == var.FENGXIAN_AREA}">${map.NAME}</c:if>';
      dataList += '<c:forEach items="${map.subDict}" var="submap" varStatus="vs1">';
      dataList += '<c:if test="${submap.DICTIONARIES_ID == var.FENGXIAN_AREA}">${submap.NAME}</c:if>';
      dataList += '</c:forEach></c:forEach>">';

      dataList += '<c:forEach items="${areaList}" var="map" varStatus="vs">';
      dataList += '<c:if test="${map.DICTIONARIES_ID == var.FENGXIAN_AREA}">${map.NAME}</c:if>';

      dataList += '<c:forEach items="${map.subDict}" var="submap" varStatus="vs1">';
      dataList += '<c:if test="${submap.DICTIONARIES_ID == var.FENGXIAN_AREA}">${submap.NAME}</c:if>';

      dataList += '<c:forEach items="${submap.subDict}" var="subsubmap" varStatus="vs2">';
      dataList += '<c:if test="${subsubmap.DICTIONARIES_ID == var.FENGXIAN_AREA}">${subsubmap.NAME}</c:if>';
      dataList += '</c:forEach></c:forEach></c:forEach>';
      dataList += '</td>';

      dataList += `<td>${var.FENGXIAN_HAZARD}</td>`;
      dataList += '<td>';

    dataList += '<c:forEach items="${var.FENGXIAN_ACCIDENT_TYPE}" var="fat" varStatus="vs2">';
    dataList += '<c:forEach items="${accidentTypeMap}" var="map" varStatus="vs">';
    dataList += '<c:if test="${map.bIANMA == fat && vs2.index == 0}">${map.nAME}</c:if>';
    dataList += '<c:if test="${map.bIANMA == fat && vs2.index != 0}">,${map.nAME}</c:if>';
    dataList += '</c:forEach>';
    dataList += '</c:forEach>';
    dataList += '</td>';

      dataList += '<td title="'
      dataList += '<c:forEach items="${riskLevelMap}" var="map" varStatus="vs">';
      dataList += '<c:if test="${map.bIANMA == var.FENGXIAN_LEVEL}">${map.nAME}</c:if></c:forEach>">';
      dataList += '<c:forEach items="${riskLevelMap}" var="map" varStatus="vs">';
      dataList += '<c:if test="${map.bIANMA == var.FENGXIAN_LEVEL}">${map.nAME}</c:if>';
      dataList += '</c:forEach>';
      dataList += '</td>';

      dataList += `<td title="${var.CONTROL_MEASURE}">${var.CONTROL_MEASURE}</td>`;
      dataList += `<td title="${var.CONTROL_MEASURE}">${var.EMERGENCY_MEASURE}</td>`;
      dataList += '<td>${var.HAZARD_DURATION}</td>';
      dataList += '<td>${var.MANAGEMENT_LEVEL}</td>';
      dataList += '<td><c:forEach items="${responsibilityList}" var="map" varStatus="vs"><c:if test="${map.ORG_ID == var.RESPONSIBILITY_UNIT}">${map.ORG_NAME}</c:if></c:forEach></td>';
      dataList += '<td>${var.RESPONSIBILITY_PEOPLE}</td>';
    dataList += '<td>${var.RESPONSIBILITY_PHONE}</td><td>${var.RECOGNITION_TIME}</td><td><c:choose><c:when test="${var.START_TIME == '长期' && var.END_TIME == '长期'}">${var.START_TIME}</c:when><c:when test="${var.START_TIME == '' && var.END_TIME == ''}"></c:when><c:otherwise>${var.START_TIME}至${var.END_TIME}</c:otherwise></c:choose></td></tr></c:forEach>';

      $("#dataList").append(dataList);


    // 监听下拉选择框的change事件
    $('#fengxianSelect').change(function() {
      var selectedValue = $(this).val();
      if (selectedValue === 'all') {
        $('#dataTable tbody tr').show();
      } else {
        $('#dataTable tbody tr').hide();
        $('#dataTable tbody tr td[title="' + selectedValue + '"]').parent('tr').show();
      }
    });

    // 监听下拉选择框的change事件
    $('#areaSelect').change(function() {
      var selectedValue = $(this).val();
      if (selectedValue === 'all') {
        $('#dataTable tbody tr').show();
      } else {
        $('#dataTable tbody tr').hide();
        $('#dataTable tbody tr td[area="' + selectedValue + '"]').parent('tr').show();
      }
    });

    // 监听下拉选择框的change事件
    $('#secondUnitSelect').change(function() {
      var selectedValue = $(this).val();
      if (selectedValue === 'all') {
        $('#dataTable tbody tr').show();
      } else {
        $('#dataTable tbody tr').hide();
        $('#dataTable tbody tr td[secondunit="' + selectedValue + '"]').parent('tr').show();
      }
    });
  });
</script>
</body>
</html>