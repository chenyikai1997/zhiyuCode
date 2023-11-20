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
  <base href="<%=basePath%>">

  <!-- jsp文件头和头部 -->
  <%@ include file="../system/index/top.jsp"%>
  <!-- 百度echarts -->
  <script src="plugins/echarts/echarts.min.js"></script>
  <script type="text/javascript">
    setTimeout("top.hangge()",500);
  </script>
</head>
<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
  <!-- /section:basics/sidebar -->
  <div class="main-content">
    <div class="main-content-inner">
      <div class="page-content">
        <div class="hr hr-18 dotted hr-double"></div>
        <div class="row">
          <div class="col-xs-12">

            <div class="alert alert-block alert-success">
              <button type="button" class="close" data-dismiss="alert">
                <i class="ace-icon fa fa-times"></i>
              </button>
<%--              <i class="ace-icon fa fa-check green"></i>--%>
<%--              欢迎使用海南控股应急管理平台&nbsp;&nbsp;--%>
<%--              <strong class="green">--%>
<%--                <a href="<%=basePath%>fengxianSecond.jsp" target="_blank"><small>(&nbsp;全屏打开驾驶舱&nbsp;)</small></a>--%>
<%--              </strong>--%>
            </div>

            <td id="main" style="width:100%;" valign="top" >
              <iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>fengxian/fengxianSecond?ORG_NAME=${pd.ORG_NAME}" style="margin:0 auto;width:100%;height:100%;"></iframe>
            </td>


            <%--							<div id="main" style="width: 600px;height:300px;"></div>--%>


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
<%@ include file="../system/index/foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
  $(top.hangge());

  function treeFrameT(){
    var hmainT = document.getElementById("treeFrame");
    var bheightT = document.documentElement.clientHeight;
    hmainT .style.width = '100%';
    // hmainT .style.height = (bheightT-26) + 'px';
    hmainT .style.height = (bheightT-800) + 'px';
  }
  treeFrameT();
  window.onresize=function(){
    treeFrameT();
  };
</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>