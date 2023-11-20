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
    <link rel="stylesheet" href="<%=basePath%>/frontend/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>/frontend/css/index.css">
</head>
<body>
<!-- 1. 顶部 -->
<header>
    <div class="header-title">海南控股重点监督风险管控系统</div>
</header>

<div class="table-title">
    <span>风险点管理统计表</span>
</div>

<div class="table-container">
    <div class="select-container">
        <!-- 风险下拉框 -->
        <div class="select-box">
            <div class="title">按风险等级筛选：</div>
            <select id="fengxianSelect">
                <option value="option1">选项1</option>
                <option value="option2">选项2</option>
                <option value="option3">选项3</option>
            </select>
        </div>
        <!-- 区域下拉框 -->
        <div class="select-box">
            <div class="title">按区域筛选：</div>
            <select id="areaSelect">
                <option value="option1">选项1</option>
                <option value="option2">选项2</option>
                <option value="option3">选项3</option>
            </select>
        </div>
    </div>
    <table>
        <thead>
        <tr ver>
            <th>序号</th>
            <th>二级单位</th>
            <th>三级公司</th>
            <th>地址</th>
            <th>区域</th>
            <th>危险源</th>
            <th>肯能导致的事故类型</th>
            <th>风险等级</th>
            <th>控制措施</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td title="1asasasasas">数据 1asasasasas</td>
            <td>数据 2</td>
            <td>数据 3</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
        </tr>
        <tr>
            <td title="1asasasasas">数据 1asasasasas</td>
            <td>数据 2</td>
            <td>数据 3</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
            <td>数据 4</td>
        </tr>
        </tbody>
    </table>
</div>

<script src="<%=basePath%>/frontend/js/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>/frontend/js/flexible.js"></script>
<script>
    $(document).ready(function() {
        // 监听下拉选择框的change事件
        $('#fengxianSelect').change(function() {
            // 获取选中的值
            var selectedValue = $(this).val();
            console.log(selectedValue);
            // 执行相应的操作
            // ...
        });

        // 监听下拉选择框的change事件
        $('#areaSelect').change(function() {
            // 获取选中的值
            var selectedValue = $(this).val();
            console.log(selectedValue);
            // 执行相应的操作
            // ...
        });
    });
</script>
</body>
</html>