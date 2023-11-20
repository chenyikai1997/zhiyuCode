<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            <form action="rectifyinfo/listSecondUnit.do" method="post" name="Form" id="Form">
              <input type="hidden" name="IS_COMPLETE" id="IS_COMPLETE" value="${pd.IS_COMPLETE}"/>
              <input type="hidden" name="OVER_TIME" id="OVER_TIME" value="${pd.OVER_TIME}"/>
              <input type="hidden" name="IS_SUBMITE" id="IS_SUBMITE" value="${pd.IS_SUBMITE}"/>
              <input type="hidden" name="msg" id="msg" value="${pd.msg}"/>
              <table style="margin-top:5px;">
                <tr>
                  <td>
                    <div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
                    </div>
                  </td>
                  <td style="padding-left:2px;">
                    <div class="input-daterange input-group">
                      <input type="text" class="input-sm form-control" id="start" value="${pd.start}" name="start" placeholder="发现时间" type="text" readonly="readonly"/>
                      <span class="input-group-addon">
											<i class="fa fa-exchange"></i>
										</span>
                      <input type="text" class="input-sm form-control" id="end" value="${pd.end}" name="end" placeholder="发现时间" type="text" readonly="readonly"/>
                    </div>
                  </td>
                  <td style="vertical-align:top;padding-left:2px;">
                    <select class="chosen-select form-control" name="ORG_ID" id="ORG_ID" data-placeholder="组织机构" style="vertical-align:top;width: 200px;">
                      <option value=""></option>
                      <option value="">全部</option>
                      <c:forEach items="${orgList}" var="map" varStatus="vs">
                        <option value="${map.ORG_ID}" <c:if test="${map.ORG_ID == pd.ORG_ID}">selected</c:if> >${map.ORG_NAME}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <td style="vertical-align:top;padding-left:2px;">
                    <select class="chosen-select form-control" name="HIDDEN_DANGER_CLASSIFY" id="HIDDEN_DANGER_CLASSIFY" data-placeholder="隐患类别" style="vertical-align:top;width: 200px;">
                      <option value=""></option>
                      <option value="">全部</option>
                      <c:forEach items="${classifyMap}" var="map" varStatus="vs">
                        <option value="${map.key}" <c:if test="${map.key == pd.HIDDEN_DANGER_CLASSIFY}">selected</c:if> >${map.value}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <td style="vertical-align:top;padding-left:2px;">
                    <select class="chosen-select form-control" name="HIDDEN_DANGER_LEVEL" id="HIDDEN_DANGER_LEVEL" data-placeholder="隐患级别" style="vertical-align:top;width: 120px;">
                      <option value=""></option>
                      <option value="">全部</option>
                      <c:forEach items="${levelMap}" var="map" varStatus="vs">
                        <option value="${map.key}" <c:if test="${map.key == pd.HIDDEN_DANGER_LEVEL}">selected</c:if> >${map.value}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <td style="vertical-align:top;padding-left:2px;">
                    <select class="chosen-select form-control" name="HIDDEN_DANGER_FACTOR" id="HIDDEN_DANGER_FACTOR" data-placeholder="隐患因素" style="vertical-align:top;width: 120px;">
                      <option value=""></option>
                      <option value="">全部</option>
                      <c:forEach items="${factorMap}" var="map" varStatus="vs">
                        <option value="${map.key}" <c:if test="${map.key == pd.HIDDEN_DANGER_FACTOR}">selected</c:if> >${map.value}</option>
                      </c:forEach>
                    </select>
                  </td>
<%--                  <td style="vertical-align:top;padding-left:2px;">--%>
<%--                    <select class="chosen-select form-control" name="RECTIFY_STAGE" id="RECTIFY_STAGE" data-placeholder="隐患阶段" style="vertical-align:top;width: 120px;">--%>
<%--                      <option value="">全部</option>--%>
<%--                      <option value="1">未提交</option>--%>
<%--                      <option value="2">未整改</option>--%>
<%--                      <option value="3">未完成</option>--%>
<%--                      <option value="4">已完成</option>--%>
<%--                    </select>--%>
<%--                  </td>--%>
                  <c:if test="${QX.cha == 1 }">
                    <td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                  </c:if>
                  <c:if test="${QX.cha == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
                  <%-- <c:if test="${QX.add == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="ace-icon fa fa-cloud-upload bigger-110 nav-search-icon blue"></i></a></td></c:if> --%>
                </tr>
              </table>
<%--              <table style="position: absolute;top: 5px;right: 5px;">--%>
<%--                <tr>--%>
<%--                  <td style="vertical-align:top;">--%>
<%--                    &lt;%&ndash; <c:if test="${QX.add == 1 and pd.noAddBtn != 1}">--%>
<%--                        <a class="btn btn-mini btn-success" onclick="add();">新增</a>--%>
<%--                    </c:if> &ndash;%&gt;--%>
<%--                    <c:if test="${QX.del == 1}">--%>
<%--                      <a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >批量删除</a>--%>
<%--                    </c:if>--%>
<%--                  </td>--%>
<%--                </tr>--%>
<%--              </table>--%>
              <tr>
                <td style="vertical-align:top;">
                  <%-- <c:if test="${QX.add == 1 and pd.noAddBtn != 1}">
                      <a class="btn btn-mini btn-success" onclick="add();">新增</a>
                  </c:if> --%>
                  <c:if test="${QX.del == 1}">
                    <a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >批量删除</a>
                  </c:if>
                </td>
              </tr>
              <!-- 检索  -->

              <div style="width: 100%;overflow-x: scroll;">
                <div style="width: 2000px;">
                  <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                    <thead>
                    <tr>
                      <th class="center" style="width:35px;">
                        <label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
                      </th>
                      <th class="center" style="width:50px;">序号</th>
                      <th class="center" style="width: 310px;">操作</th>
                      <th class="center" style="width:300px;">安全隐患编码</th>
                      <th class="center" style="width:150px;">组织机构</th>
                      <th class="center" style="width:150px;">项目名称</th>
                      <th class="center" style="width:200px;">文件编号</th>
                      <th class="center" style="width:200px;">隐患情况</th>
                      <th class="center" style="width:200px;">隐患类别</th>
                      <th class="center" style="width:200px;">隐患级别</th>
                      <th class="center" style="width:200px;">隐患因素</th>
                      <th class="center" style="width:200px;">整改期限</th>
                      <th class="center" style="width:350px;">第一填报单位</th>
                      <th class="center" style="width:200px;">第一填报人</th>
                      <th class="center" style="width:300px;">整改措施</th>
                      <th class="center" style="width:350px;">责任单位</th>
                      <th class="center" style="width:200px;">责任人</th>
                      <th class="center" style="width:200px;">完成时间</th>
                      <th class="center" style="width:200px;">整改投入（元）</th>
                      <th class="center" style="width:350px;">填报单位</th>
                      <th class="center" style="width:200px;">填报人</th>
                      <th class="center" style="width:150px;">备注</th>
                      <th class="center" style="width:200px;">发现时间</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                      <c:when test="${not empty varList}">
                        <c:if test="${QX.cha == 1 }">
                          <c:forEach items="${varList}" var="var" varStatus="vs">
                            <tr>
                              <td class='center'>
                                <label class="pos-rel"><input type='checkbox' name='ids' value="${var.RECTIFY_ID}" class="ace" /><span class="lbl"></span></label>
                              </td>
                              <td class='center' style="width: 30px;">${vs.index+1}</td>
                              <td class="center">
                                <div class="hidden-sm hidden-xs btn-group">
                                  <c:if test="${QX.edit == 1 }">
                                    <a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.RECTIFY_ID}');">
                                      <i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
                                    </a>
                                  </c:if>
                                  <c:if test="${var.RECTIFY_STAGE == 1}">
                                    <a class="btn btn-xs btn-success" onclick="submitInfo('${var.RECTIFY_ID}');">
                                      提交
                                    </a>
                                  </c:if>
                                  <c:if test="${var.IS_SUBMITE == 1 and var.IS_COMPLETE != 1}">
                                    <a class="btn btn-xs btn-success" onclick="complete('${var.RECTIFY_ID}');">
                                      完成
                                    </a>
                                  </c:if>
                                  <c:if test="${QX.del == 1}">
                                    <a class="btn btn-xs btn-danger" onclick="del('${var.RECTIFY_ID}');">
                                      <i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
                                    </a>
                                  </c:if>
                                </div>
                                <div class="hidden-md hidden-lg">
                                  <div class="inline pos-rel">
                                    <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                      <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                      <c:if test="${QX.edit == 1 }">
                                        <li>
                                          <a style="cursor:pointer;" onclick="edit('${var.RECTIFY_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                                          </a>
                                        </li>
                                      </c:if>
                                      <c:if test="${QX.del == 1}">
                                        <li>
                                          <a style="cursor:pointer;" onclick="del('${var.RECTIFY_ID}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
                              <td class='center'>${var.RECTIFYINFO_NUMBER}</td>
                              <td class='center'>${var.ORG_NAME}</td>
                              <td class='center'>
                                <c:forEach items="${projectList}" var="map" varStatus="vs">
                                  <c:if test="${map.ORG_ID == var.PROJECT_NAME}">${map.ORG_NAME}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>${var.FILE_CODE}</td>
                              <td class='center'>
                                <c:choose>
                                  <c:when test="${fn:length(var.HIDDEN_DANGER_INFO) > 30}">
                                    <c:out value="${fn:substring(var.HIDDEN_DANGER_INFO, 0, 30)}......" />
                                  </c:when>
                                  <c:otherwise>
                                    <c:out value="${var.HIDDEN_DANGER_INFO}" />
                                  </c:otherwise>
                                </c:choose>
                              </td>
                              <td class='center'>
                                <c:forEach items="${classifyMap}" var="map" varStatus="vs">
                                  <c:if test="${map.key == var.HIDDEN_DANGER_CLASSIFY}">${map.value}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>
                                <c:forEach items="${levelMap}" var="map" varStatus="vs">
                                  <c:if test="${map.key == var.HIDDEN_DANGER_LEVEL}">${map.value}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>
                                <c:forEach items="${factorMap}" var="map" varStatus="vs">
                                  <c:if test="${map.key == var.HIDDEN_DANGER_FACTOR}">${map.value}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>${var.PLAN_COMPLETE_TIME}</td>
                              <td class='center'>
                                <c:forEach items="${orgList}" var="map" varStatus="vs">
                                  <c:if test="${map.ORG_ID == var.REPORTING_UNIT_FIRST}">${map.ORG_NAME}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>${var.REPORTING_PERSON_FIRST}</td>
                              <td class='center'>
                                <c:choose>
                                  <c:when test="${fn:length(var.RECTIFY_MEASURES) > 30}">
                                    <c:out value="${fn:substring(var.RECTIFY_MEASURES, 0, 30)}......" />
                                  </c:when>
                                  <c:otherwise>
                                    <c:out value="${var.RECTIFY_MEASURES}" />
                                  </c:otherwise>
                                </c:choose>
                              </td>
                              <td class='center'>
                                <c:forEach items="${orgList}" var="map" varStatus="vs">
                                  <c:if test="${map.ORG_ID == var.RESPONSIBLE_UNIT}">${map.ORG_NAME}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>${var.PERSON_RESPONSIBLE}</td>
                              <td class='center'>${var.COMPLETE_TIME}</td>
                              <td class='center'>${var.RECTIFY_INVESTMENT}</td>
                              <td class='center'>
                                <c:forEach items="${orgList}" var="map" varStatus="vs">
                                  <c:if test="${map.ORG_ID == var.REPORTING_UNIT}">${map.ORG_NAME}</c:if>
                                </c:forEach>
                              </td>
                              <td class='center'>${var.REPORTING_PERSON}</td>
                              <td class='center'>${var.REMARK}</td>
                              <td class='center'>${var.COMMIT_TIME}</td>
                            </tr>

                          </c:forEach>
                        </c:if>
                        <c:if test="${QX.cha == 0 }">
                          <tr>
                            <td colspan="100" class="center">您无权查看</td>
                          </tr>
                        </c:if>
                      </c:when>
                      <c:otherwise>
                        <tr class="main_info">
                          <td colspan="100" class="center" >没有相关数据</td>
                        </tr>
                      </c:otherwise>
                    </c:choose>
                    </tbody>
                  </table>
                </div>
              </div>

              <div class="page-header position-relative">
                <table style="width:100%;">
                  <tr>
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
  $(function() {

    //日期框
    /* $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true,
        clearBtn: true
    }); */

    $('.input-daterange').datepicker({
      autoclose:true,
      todayHighlight: true,
      format: 'yyyy-mm-dd',
      clearBtn: true
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

  //新增
  function add(){
    top.jzts();
    var diag = new top.Dialog();
    diag.Drag=true;
    diag.Title ="新增";
    diag.URL = '<%=basePath%>rectifyinfo/goAdd.do';
    diag.Width = 900;
    diag.Height = 800;
    diag.Modal = true;				//有无遮罩窗口
    diag. ShowMaxButton = true;	//最大化按钮
    diag.ShowMinButton = true;		//最小化按钮
    diag.CancelEvent = function(){ //关闭事件
      if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
        if('${page.currentPage}' == '0'){
          tosearch();
        }else{
          nextPage(${page.currentPage});
        }
      }
      diag.close();
    };
    diag.show();
  }

  //删除
  function del(Id){
    bootbox.confirm("确定要删除吗?", function(result) {
      if(result) {
        top.jzts();
        var url = "<%=basePath%>rectifyinfo/delete.do?RECTIFY_ID="+Id+"&tm="+new Date().getTime();
        $.get(url,function(data){
          nextPage(${page.currentPage});
        });
      }
    });
  }

  // 提交
  function submitInfo(Id) {
    bootbox.confirm("确定要提交吗?", function(result) {
      if(result) {
        top.jzts();
        var url = "<%=basePath%>rectifyinfo/submit.do?RECTIFY_ID="+Id+"&tm="+new Date().getTime();
        $.get(url,function(data){
          nextPage(${page.currentPage});
        });
      }
    });
  }

  // 完成
  function complete(Id) {
    top.jzts();
    var diag = new top.Dialog();
    diag.Drag=true;
    diag.Title ="完成";
    diag.URL = '<%=basePath%>rectifyinfo/goComplete.do?RECTIFY_ID='+Id;
    diag.Width = 900;
    diag.Height = 800;
    diag.Modal = true;				//有无遮罩窗口
    diag. ShowMaxButton = true;	//最大化按钮
    diag.ShowMinButton = true;		//最小化按钮
    diag.CancelEvent = function(){ //关闭事件
      if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
        nextPage(${page.currentPage});
      }
      diag.close();
    };
    diag.show();
  }

  //修改
  function edit(Id){
    top.jzts();
    var diag = new top.Dialog();
    diag.Drag=true;
    diag.Title ="编辑";
    diag.URL = '<%=basePath%>rectifyinfo/goEditSecondUnit.do?RECTIFY_ID='+Id;
    diag.Width = 900;
    diag.Height = 800;
    diag.Modal = true;				//有无遮罩窗口
    diag. ShowMaxButton = true;	//最大化按钮
    diag.ShowMinButton = true;		//最小化按钮
    diag.CancelEvent = function(){ //关闭事件
      if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
        nextPage(${page.currentPage});
      }
      diag.close();
    };
    diag.show();
  }

  //批量操作
  function makeAll(msg){
    bootbox.confirm(msg, function(result) {
      if(result) {
        var str = '';
        for(var i=0;i < document.getElementsByName('ids').length;i++){
          if(document.getElementsByName('ids')[i].checked){
            if(str=='') str += document.getElementsByName('ids')[i].value;
            else str += ',' + document.getElementsByName('ids')[i].value;
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
          if(msg == '确定要删除选中的数据吗?'){
            top.jzts();
            $.ajax({
              type: "POST",
              url: '<%=basePath%>rectifyinfo/deleteAll.do?tm='+new Date().getTime(),
              data: {DATA_IDS:str},
              dataType:'json',
              //beforeSend: validateData,
              cache: false,
              success: function(data){
                $.each(data.list, function(i, list){
                  nextPage(${page.currentPage});
                });
              }
            });
          }
        }
      }
    });
  };

  //导出excel
  function toExcel(){
    var str = '';
    for(var i=0;i < document.getElementsByName('ids').length;i++){
      if(document.getElementsByName('ids')[i].checked){
        if(str=='') str += document.getElementsByName('ids')[i].value;
        else str += ',' + document.getElementsByName('ids')[i].value;
      }
    }
    var param = {
      "start": $("#start").val(),
      "end": $("#end").val(),
      "keywords": $("#keywords").val(),
      "HIDDEN_DANGER_CLASSIFY": $("#HIDDEN_DANGER_CLASSIFY").val(),
      "HIDDEN_DANGER_LEVEL": $("#HIDDEN_DANGER_LEVEL").val(),
      "HIDDEN_DANGER_FACTOR": $("#HIDDEN_DANGER_FACTOR").val(),
      "IS_COMPLETE": $("#IS_COMPLETE").val(),
      "ORG_ID": $("#ORG_ID").val(),
      "tag":"all",
      "selectId":str
    }
    window.location.href='<%=basePath%>rectifyinfo/excelSC.do?' + $.param(param);
  }

  //打开上传excel页面
  function fromExcel(){
    top.jzts();
    var diag = new top.Dialog();
    diag.Drag=true;
    diag.Title ="EXCEL 导入到数据库";
    diag.URL = '<%=basePath%>rectifyinfo/goUploadExcel.do';
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