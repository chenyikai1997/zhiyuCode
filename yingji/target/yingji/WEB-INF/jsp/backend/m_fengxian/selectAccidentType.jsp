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

            <form action="tendering_agent/list.do" method="post" name="Form" id="Form">
              <input name="ACCIDENT_TYPE_ID" id="ACCIDENT_TYPE_ID" type="hidden">
              <input name="ACCIDENT_TYPE_NAME" id="ACCIDENT_TYPE_NAME" type="hidden">
              <table style="margin-top:5px;">
                <%--<tr>
                  <td>
                    <div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
                    </div>
                  </td>
                    <td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                    </tr>--%>
              </table>
              <!-- 检索  -->

              <div id="zhongxin3" style="padding-top: 13px;">
                <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                  <thead>
                  <tr>
                    <th class="center" style="width:35px;">
                      <label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
                    </th>
                    <th class="center" style="width:50px;">序号</th>
                    <th class="center">事故类型名称</th>
                    <th class="center">英文名称</th>
                  </tr>
                  </thead>

                  <tbody>
                  <!-- 开始循环 -->
                  <c:choose>
                    <c:when test="${not empty varList}">
                      <c:forEach items="${varList}" var="var" varStatus="vs">
                        <tr>
                          <td class='center'>
                            <label class="pos-rel">
                              <input type='checkbox' name='ids' value="${var.BIANMA}" class="ace"
                                      <c:forEach items="${accidentTypeString}" var="map" varStatus="vs1">
                                        <c:if test="${map == var.BIANMA}">
                                          checked
                                        </c:if>
                                      </c:forEach>
                              />
                              <span class="lbl"></span></label>
                          </td>
                          <td class='center' style="width: 30px;">${vs.index+1}</td>
                          <td class='center'>${var.NAME}</td>
                          <td class='center'>${var.NAME_EN}</td>
                          <td class="center">
                            <div class="hidden-sm hidden-xs btn-group">
                            </div>
                            <div class="hidden-md hidden-lg">
                              <div class="inline pos-rel">
                                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                  <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                  <c:if test="${QX.edit == 1 }">
                                    <li>
                                      <a style="cursor:pointer;" onclick="edit('${var.BIANMA}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
                                      </a>
                                    </li>
                                  </c:if>
                                  <c:if test="${QX.del == 1 }">
                                    <li>
                                      <a style="cursor:pointer;" onclick="del('${var.BIANMA}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
                                      </a>
                                    </li>
                                  </c:if>
                                </ul>
                              </div>
                            </div>
                          </td>
                        </tr>
                      </c:forEach>
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
                        <a class="btn btn-mini btn-success" onclick="makeAll('确定要保存选中的事故类型吗?');" title="保存" >保存</a>
                      </td>
                      <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                    </tr>
                  </table>
                </div>
              </div>

              <div id="zhongxin4" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">123...</h4></div>

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
<script src="static/ace/js/bootbox.js"></script>
<script src="static/ace/js/ace/ace.js"></script>
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
  $(function() {
    //复选框全选控制
    var active_class = 'active';
    $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
      var th_checked = this.checked;//checkbox inside "TH" table header
      $(this).closest('table').find('tbody > tr').each(function(){
        var row = this;
        if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
        else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
      });
    });
  });

  //批量操作
  function makeAll(msg){
    //用来保存供应商id和名字
    var varList = JSON.parse('${varList}');
    bootbox.confirm(msg, function(result) {
      if(result) {
        var str = '';
        var strName = '';
        for(var i=0;i < document.getElementsByName('ids').length;i++){
          if(document.getElementsByName('ids')[i].checked){
            //第一个id
            if(str==''){
              str += document.getElementsByName('ids')[i].value;
              $.each(varList, function(index, value) {
                if(value.BIANMA == document.getElementsByName('ids')[i].value){
                  strName += value.NAME;
                }
              });

            }
            //后面的id
            else{
              str += ',' + document.getElementsByName('ids')[i].value;
              $.each(varList, function(index, value) {
                if(value.BIANMA == document.getElementsByName('ids')[i].value){
                  strName += ',' + value.NAME;
                }
              });
            }
          }
        }

        if(str==''){
          bootbox.dialog({
            message: "<span class='bigger-110'>您没有选择任何内容!</span>",
            buttons:
                    { "button":{ "label":"确定", "className":"btn-sm btn-success"}}
          });
          $("#zcheckbox").tips({
            side:1,
            msg:'点这里全选',
            bg:'#AE81FF',
            time:8
          });
          return;
        }else{
          if(msg == '确定要保存选中的事故类型吗?'){
            $("#ACCIDENT_TYPE_ID").val(str);
            $("#ACCIDENT_TYPE_NAME").val(strName);
            console.log(strName)
            document.getElementById('zhongxin3').style.display = 'none';
            top.Dialog.close();
            <%--window.location.href='<%=basePath%>m_inquiry/saveTendering?DATA_IDS='+str;--%>

          }
        }
      }
    });
  };
</script>