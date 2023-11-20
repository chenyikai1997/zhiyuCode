﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
							
						<!-- 检索  -->
						<form action="morg/list.do" method="post" name="Form" id="Form">
							<input type="hidden" name="ISORG" id="ISORG" value="${pd.ISORG}"/>

						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input  onkeydown="if(event.keyCode==13){tosearch()}" type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pds.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<%--<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>--%>
								<%--<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>--%>
								<%--<c:if test="${QX.cha == 1 }">--%>
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								<%--</c:if>--%>
								<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td>
								<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="ace-icon fa fa-cloud-upload bigger-110 nav-search-icon blue"></i></a></td>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">机构名称</th>
									<th class="center">机构简称</th>
									<th class="center">机构编码</th>
									<th class="center">机构层级</th>
									<th class="center" style="width:50px;">排序</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<%--<c:if test="${QX.cha == 1 }">--%>
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'><a href="javascript:goSondict('${var.ORG_ID}','${pd.ISORG}')"><i class="ace-icon fa fa-share bigger-100"></i>&nbsp;${var.ORG_NAME}</a></td>
											<td class='center'>${var.ORG_NAME_SHORT}</td>
											<td class='center'>${var.ORG_CODE}</td>
											<td class='center'>${var.ORG_LEVEL}</td>
											<td class='center'>${var.SORT}</td>
											<td class="center">
<%--												<c:if test="${QX.edit != 1 && QX.del != 1 }">--%>
<%--												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>--%>
<%--												</c:if>--%>
												<div class="hidden-sm hidden-xs btn-group">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.ORG_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													<a class="btn btn-xs btn-danger" onclick="del('${var.ORG_ID}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.ORG_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>

															<li>

																<a style="cursor:pointer;" onclick="del('${var.ORG_ID}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									
									</c:forEach>
									<%--</c:if>--%>
<%--									<c:if test="${QX.cha == 0 }">--%>
<%--										<tr>--%>
<%--											<td colspan="100" class="center">您无权查看</td>--%>
<%--										</tr>--%>
<%--									</c:if>--%>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">

									<a class="btn btn-mini btn-success" onclick="add('${pd.ORG_ID}','${pd.ISORG}');">新增</a>
									<c:if test="${null != pd.ORG_ID && pd.ORG_ID != ''}">
									<a class="btn btn-mini btn-success" onclick="goSondict('${pd.PARENT_ID}','${pd.ISORG}');">返回</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		
		//去此ID下子级列表
		function goSondict(ORG_ID,ISORG){
			console.log("list返回上级机构")
			top.jzts();
			window.location.href="<%=basePath%>morg/list?ORG_ID="+ORG_ID+"&ISORG="+ISORG;
		};
		
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
		});
		
		//新增
		function add(ORG_ID,ISORG){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>morg/goAdd.do?ORG_ID='+ORG_ID+"&ISORG="+ISORG;
			 diag.Width = 800;
			 diag.Height = 400;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 parent.location.href="<%=basePath%>morg/listTree.do?ORG_ID=${pd.ORG_ID}&ISORG=${pd.ISORG}&dnowPage=${page.currentPage}";
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(ORG_ID){
			/*console.log(ORG_ID)*/
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>morg/delete.do?ORG_ID="+ORG_ID+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if("success" == data.result){
							parent.location.href="<%=basePath%>morg/listTree.do?ORG_ID=${ORG_ID}&ISORG=${pd.ISORG}&dnowPage=${page.currentPage}";
						}else if("false" == data.result){
							top.hangge();
							bootbox.dialog({
								message: "<span class='bigger-110'>删除失败！请先删除子级.</span>",
								buttons: 			
								{
									"button" :
									{
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
						}
					});
				}
			});
		}
		
		
		//修改
		function edit(ORG_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>morg/goEdit.do?ORG_ID='+ORG_ID;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 parent.location.href="<%=basePath%>morg/listTree.do?ORG_ID=${ORG_ID}&ISORG=${pd.ISORG}&dnowPage=${page.currentPage}";
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>morg/excel.do';
		}


		//打开上传excel页面
		function fromExcel(){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="EXCEL 导入到数据库";
			diag.URL = '<%=basePath%>morg/goUploadExcel.do';
			diag.Width = 300;
			diag.Height = 150;
			diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					if('${page.currentPage}' == '0'){
						top.jzts();
						setTimeout("self.location.reload()",100);
					}else{
						nextPage(${page.currentPage});
					}
				}
				diag.close();
			};
			diag.show();
		}
	</script>


</body>
</html>