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

    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
    <script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
    <style type="text/css">
        footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
    </style>
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

                        <form action="morg/${msg}.do" name="Form" id="Form" method="post">
                            <input type="hidden" name="ORG_ID" id="ORG_ID" value="${pd.ORG_ID}"/>
                            <input type="hidden" name="ISORG" id="ISORG" value="${pd.ISORG}"/>
                            <input type="hidden" name="ORG_LEVEL" id="ORG_LEVEL" value="${pd.ORG_LEVEL}"/>
                            <input type="hidden" name="CREATER" id="CREATER" value="${pd.CREATER}"/>
                            <input type="hidden" name="CREATE_DATE" id="CREATE_DATE" value="${pd.CREATE_DATE}"/>
                            <input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${null == pds.ORG_ID ? 0:pds.ORG_ID}"/>
                            <input type="hidden" name="RESERVOIR_ID" id="RESERVOIR_ID" value="${pd.RESERVOIR_ID}"/>
                            <input type="hidden" name="MONITOR_ID" id="MONITOR_ID" value="${pd.MONITOR_ID}"/>
                            <input type="hidden" name="PROJECT_UNIT" id="PROJECT_UNIT" value="${pd.PROJECT_UNIT}"/>
                            <input type="hidden" name="PROJECT_AREA" id="PROJECT_AREA" value="${pd.PROJECT_AREA}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">上级:</td>
                                        <td>
                                            <div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
                                                <b>${null == pds.ORG_NAME ?'(无) 此为顶级':pds.ORG_NAME}</b>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">项目名称:</td>
                                        <td><input type="text" name="ORG_NAME" id="ORG_NAME" value="${pd.ORG_NAME}" maxlength="100" placeholder="这里输入项目名称" title="项目名称" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">项目简称:</td>
                                        <td><input type="text" name="ORG_NAME_SHORT" id="ORG_NAME_SHORT" value="${pd.ORG_NAME_SHORT}" maxlength="100" placeholder="这里输入项目简称" title="项目简称" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">项目编码:</td>
                                        <td><input type="text" name="ORG_CODE" id="ORG_CODE" maxlength="100" value="${pd.ORG_CODE}"  placeholder="这里输入项目编码" title="项目编码" style="width:98%;"></input></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">项目简介:</td>
                                        <td><textarea type="text" name="REMARK" id="REMARK" maxlength="65535" cols="30" rows="8" placeholder="这里输入项目简介" title="项目简介" style="width:98%;">${pd.REMARK}</textarea></td>
                                    </tr>
<%--                                    <tr>--%>
<%--                                        <td style="width:75px;text-align: right;padding-top: 13px;">排序:</td>--%>
<%--                                        <td><input type="number" name="SORT" id="SORT" value="${pd.SORT}" maxlength="32" placeholder="这里输入排序" title="排序" style="width:98%;"/></td>--%>
<%--                                    </tr>--%>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">所属单位:</td>
                                        <td>
                                            <div style="overflow: scroll; scrolling: yes;height:120px;width: 100%;">
                                                <ul id="tree" class="tree" style="overflow:auto;"></ul>
                                            </div>

<%--                                            <div class="selectTree" id="selectTree"></div>--%>
<%--                                            <select class="chosen-select form-control" name="PROJECT_UNIT" id="PROJECT_UNIT" data-placeholder="这里选择所属单位" style="vertical-align:top;width: 98%;">--%>
<%--                                                <option value=""  > </option>--%>
<%--                                                <c:forEach items="${unitList}" var="map" varStatus="vs">--%>
<%--                                                    <option value="${map.ORG_ID}" <c:if test="${map.ORG_ID == pd.PROJECT_UNIT}">selected</c:if> >${map.ORG_NAME}</option>--%>
<%--                                                </c:forEach>--%>
<%--                                            </select>--%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">项目所属区域:</td>
                                        <td>
                                            <div style="overflow: scroll; scrolling: yes;height:120px;width: 100%;">
                                                <ul id="tree2" class="tree" style="overflow:auto;"></ul>
                                            </div>
<%--                                            <select class="chosen-select form-control" name="PROJECT_AREA" id="PROJECT_AREA" data-placeholder="这里选择公司所属区域" style="vertical-align:top;width: 98%;">--%>
<%--                                                <c:forEach items="${areaList}" var="map" varStatus="vs">--%>
<%--                                                    <option value="${map.DICTIONARIES_ID}" <c:if test="${map.DICTIONARIES_ID == pd.PROJECT_AREA}">selected</c:if> >${map.NAME}</option>--%>
<%--                                                    <c:forEach items="${map.subDict}" var="submap" varStatus="vs1">--%>
<%--                                                        <option value="${submap.DICTIONARIES_ID}" <c:if test="${submap.DICTIONARIES_ID == pd.PROJECT_AREA}">selected</c:if> >${map.NAME} —${submap.NAME}</option>--%>
<%--                                                    </c:forEach>--%>
<%--                                                    &lt;%&ndash;													<option value="${map.DICTIONARIES_ID}" <c:if test="${map.DICTIONARIES_ID == pd.FENGXIAN_AREA}">selected</c:if> >${map.NAME}</option>&ndash;%&gt;--%>
<%--                                                </c:forEach>--%>
<%--                                            </select>--%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: right;padding-top: 13px;">选择水库:</td>
                                        <td>
                                            <a class="btn btn-mini btn-success" onclick="selectWater('${pd.ORG_ID}');">选择</a>
                                            <div id="RESERVOIR_NAME">${waterName}</div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: right;padding-top: 13px;">选择监控:</td>
                                        <td>
                                            <a class="btn btn-mini btn-success" onclick="selectMonitor('${pd.ORG_ID}');">选择</a>
                                            <div id="MONITOR_NAME">${monitorName}</div>
                                        </td>
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

    var zTree;
    var zTree2;
    $(document).ready(function(){

        var setting = {
            showLine: true,
            checkable: true,
            checkType : { "Y": "s", "N": "s" }
        };
        var zn = '${zTreeNodes}';
        var zTreeNodes = eval(zn);
        zTree = $("#tree").zTree(setting, zTreeNodes);


        var setting2 = {
            checkable: true,
            checkStyle: "radio",
            checkType : { "Y" : "", "N" : "" }
            // checkType : { "Y": "", "N": "" },
            // data: {
            //     simpleData: {
            //         enable: true
            //     }
            // }
        };
        var zn2 = '${zTreeNodes2}';
        var zTreeNodes2 = eval(zn2);
        zTree2 = $("#tree2").zTree(setting2, zTreeNodes2);
    });

    //保存
    function save(){
        if($("#ORG_NAME").val()==""){
            $("#ORG_NAME").tips({
                side:3,
                msg:'请输入项目名称',
                bg:'#AE81FF',
                time:2
            });
            $("#ORG_NAME").focus();
            return false;
        }

        // if($("#SORT").val()==""){
        //     $("#SORT").tips({
        //         side:3,
        //         msg:'请输入排序',
        //         bg:'#AE81FF',
        //         time:2
        //     });
        //     $("#SORT").focus();
        //     return false;
        // }
        // if($("#ORG_NAME_SHORT").val()==""){
        //     $("#ORG_NAME_SHORT").tips({
        //         side:3,
        //         msg:'请输入项目简称',
        //         bg:'#AE81FF',
        //         time:2
        //     });
        //     $("#ORG_NAME_SHORT").focus();
        //     return false;
        // }

        var nodes = zTree.getCheckedNodes();
        var tmpNode;
        var ids = "";
        for(var i=0; i<nodes.length; i++){
            tmpNode = nodes[i];
            if(i!=nodes.length-1){
                ids += tmpNode.id+",";
            }else{
                ids += tmpNode.id;
            }
        }
        $("#PROJECT_UNIT").val(ids)

        var nodes2 = zTree2.getCheckedNodes();
        var tmpNode2;
        var ids2 = "";
        for(var i=0; i<nodes2.length; i++){
            tmpNode2 = nodes2[i];
            if(i!=nodes2.length-1){
                ids2 += tmpNode2.id+",";
            }else{
                ids2 += tmpNode2.id;
            }
        }
        $("#PROJECT_AREA").val(ids2)
        console.log("PROJECT_AREA",$("#PROJECT_AREA").val())


        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();

        <%--//判断是否重复--%>
        <%--var localSign;--%>
        <%--$.ajax({--%>
        <%--    type: "POST",--%>
        <%--    url: "<%=basePath%>morg/duplicate.go",--%>
        <%--    data: { "ORG_CODE": $("#ORG_CODE").val(),--%>
        <%--        "ORG_ID": $("#ORG_ID").val()--%>
        <%--    },--%>
        <%--    dataType:'json',--%>
        <%--    async : false,--%>
        <%--    success: function(data){--%>
        <%--        localSign = data.sign;--%>
        <%--    },--%>
        <%--    error : function(){--%>
        <%--        return false;--%>
        <%--    }--%>
        <%--});--%>

        <%--console.log("localSign",localSign)--%>
        <%--//如果没有相同ORG_ID，就允许上传--%>
        <%--if(localSign == "true"){--%>
        <%--    $("#Form").submit();--%>
        <%--    $("#zhongxin").hide();--%>
        <%--    $("#zhongxin2").show();--%>
        <%--}--%>
        <%--else if(localSign == "Same" && '${msg}' == "edit"){--%>
        <%--    $("#Form").submit();--%>
        <%--    $("#zhongxin").hide();--%>
        <%--    $("#zhongxin2").show();--%>
        <%--}--%>
    }

    function selectWater(ORG_ID) {
        /*$("#zhongxin").hide();*/

        var diag1 = new top.Dialog();
        diag1.Drag=true;
        diag1.Title ="选择水库";
        diag1.URL = '<%=basePath%>m_water_project/goSelect?ORG_ID='+ORG_ID;
        diag1.Width = 1200;
        diag1.Height = 1000;
        diag1.Modal = true;				//有无遮罩窗口
        diag1. ShowMaxButton = true;	//最大化按钮
        diag1.ShowMinButton = true;		//最小化按钮
        diag1.CancelEvent = function(){ //关闭事件
            if(diag1.innerFrame.contentWindow.document.getElementById('zhongxin3').style.display == 'none'){
                //获取选中的供应商
                $("#RESERVOIR_ID").val(diag1.innerFrame.contentWindow.document.getElementById('RESERVOIR_ID').value)	//从选择供应商页面获取到的供应商ID
                $("#RESERVOIR_NAME").text(diag1.innerFrame.contentWindow.document.getElementById('RESERVOIR_NAME').value)
                diag1.close();
            }
            diag1.close();
        };
        diag1.show();
    }

    function selectMonitor(ORG_ID) {
        /*$("#zhongxin").hide();*/

        var diag1 = new top.Dialog();
        diag1.Drag=true;
        diag1.Title ="选择监控";
        diag1.URL = '<%=basePath%>m_monitor_project/goSelect?ORG_ID='+ORG_ID;
        diag1.Width = 1200;
        diag1.Height = 1000;
        diag1.Modal = true;				//有无遮罩窗口
        diag1. ShowMaxButton = true;	//最大化按钮
        diag1.ShowMinButton = true;		//最小化按钮
        diag1.CancelEvent = function(){ //关闭事件
            if(diag1.innerFrame.contentWindow.document.getElementById('zhongxin3').style.display == 'none'){
                //获取选中的供应商
                $("#MONITOR_ID").val(diag1.innerFrame.contentWindow.document.getElementById('MONITOR_ID').value)	//从选择供应商页面获取到的供应商ID
                $("#MONITOR_NAME").text(diag1.innerFrame.contentWindow.document.getElementById('MONITOR_NAME').value)
                diag1.close();
            }
            diag1.close();
        };
        diag1.show();
    }

    $(function() {
        //日期框
        $('.date-picker').datepicker({autoclose: true,todayHighlight: true});
    });



</script>
</body>
</html>