<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="projectType/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PROJECTTYPE_ID" id="PROJECTTYPE_ID" value="${pd.PROJECTTYPE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目分类名称:</td>
								<td><input type="text" name="PROJECTTYPE_NAME" id="PROJECTTYPE_NAME" value="${pd.PROJECTTYPE_NAME}" maxlength="255" placeholder="这里输入项目名称（分类）" title="项目名称（分类）" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目数量:</td>
								<td><input type="number" name="PROJECTTYPE_NUM" id="PROJECTTYPE_NUM" value="${pd.PROJECTTYPE_NUM}" maxlength="32" placeholder="这里输入项目数量" title="项目数量" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">颜色值:</td>
								<td>
								<select class="chosen-select form-control" name="PROJECTTYPE_COLOR" id="PROJECTTYPE_COLOR" data-placeholder="颜色值" style="vertical-align:top;width: 98%;">
										<option value=""></option>
										<c:forEach items="${colorMap}" var="data" varStatus="vs">
											<option value="${data.key }" <c:if test="${data.key == pd.PROJECTTYPE_COLOR }">selected</c:if> >${data.value}</option>
										</c:forEach>
								  	</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">排序:</td>
								<td><input type="number" name="SORT" id="SORT" value="${pd.SORT}" maxlength="255" placeholder="这里输入排序" title="排序" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			
			if($("#PROJECTTYPE_NAME").val()==""){
				$("#PROJECTTYPE_NAME").tips({
					side:3,
		            msg:'请输入项目名称（分类）',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROJECTTYPE_NAME").focus();
			return false;
			}
			if($("#PROJECTTYPE_NUM").val()==""){
				$("#PROJECTTYPE_NUM").tips({
					side:3,
		            msg:'请输入项目数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROJECTTYPE_NUM").focus();
			return false;
			}
			if($("#PROJECTTYPE_COLOR").val()==""){
				$("#PROJECTTYPE_COLOR").tips({
					side:3,
		            msg:'请输入颜色值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROJECTTYPE_COLOR").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>