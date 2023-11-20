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
<head>
    <title>开放平台视频web插件Demo</title>
    <!--显示字符集设定：文本网页使用的字符集为utf-8-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!--清除缓存，再访问本网站需要重新下载-->
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
    <meta http-equiv="Expires" content="0" />
</head>
<style>
html, body {
    padding: 0;
    margin: 0;
}
.playWnd {
    margin: 30px 0 0 400px;
    width: 800px;
    height: 400px;
    border: 1px solid red;
}
.cbInfoDiv {
    float: left;
    width: 360px;
    margin-left: 16px;
    border:1px solid #7F9DB9;
}
.cbInfo {
    height: 200px;
    padding: 5px;
    border: 1px solid #7F9DB9;
    overflow: auto;
    word-break: break-all;
}
.operate {
    margin-top: 24px;
}
.operate::after {
    content: '';
    display: block;
    clear: both;
}
.operate .btns {
    height: 32px;
}
.module {
    float: left;
    width: 340px;
    min-height: 320px;
    margin-left: 16px;
    padding: 16px 8px;
    box-sizing: border-box;
    border: 1px solid #e5e5e5;
}
.module .item {
    margin-bottom: 4px;
}
.module .label {
    width: 150px;
    display: inline-block;
    vertical-align: middle;
    margin-right: 8px;
    text-align: right;
}
.module input[type="text"],
.module select {
    box-sizing: border-box;
    display: inline-block;
    vertical-align: middle;
    margin-left: 0;
    width: 150px;
    min-height: 20px;
}
.module .btn {
    min-width: 80px;
    min-height: 24px;
    margin-top: 16px;
    margin-left: 158px;
}
<!--弹框风格 
.white_content { 
 display: none; 
 position: absolute; 
 top: 25%; 
 left: 25%; 
 width: 30%; 
 height: 30%; 
 padding: 20px; 
 border: 3px solid orange; 
 background-color: white; 
 z-index:1002; 
 overflow: auto; 
} 
  -->
.white_content { 
 display: none; 
 position: absolute; 
 top: 80px; 
 left: 450px;
 width: 600px;
 height: 300px; 
 border: 3px solid orange; 
 background-color: white; 
 z-index:1002; 
}
</style>
<body>
    <div id="playWnd" class="playWnd" style="left: 109px; top: 133px;"></div>
    <div id="operate" class="operate">
        <!--初始化、反初始化、设置认证信息接口调用入口。
        1.插件所有接口都需要在调用初始化并返回成功后才能调用
        2.设置认证信息仅适用于多平台接入时的情况，具体参照开发指南
        3.反初始化后，插件资源销毁-->
        <div class="module" style="height:50;width:330px;padding:10;margin:10;">
            <div class="item">
                <label >初始化相关参数：</label>
                <textarea id="initParam" type="text" style="width:310px;height:350px;">
{
    "argument": {
        "appkey": "",
        "ip": "",
        "port": 443,
        "secret": "",
        "enableHTTPS": 1,
        "language": "zh_CN",
        "layout": "2x2",
        "playMode": 0,
        "reconnectDuration": 5,
        "reconnectTimes": 5,
        "showSmart": 0,
        "showToolbar": 1,
        "toolBarButtonIDs": "2048,2049,2050,2304,2306,2305,2307,2308,2309,4096,4608,4097,4099,4098,4609,4100",
        "snapDir": "D:/snap",
        "videoDir": "D:/video"
    },
    "funcName": "init"
}
                </textarea>
            </div>
            <div class="item">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <select id="initFuncType" onchange="UpdateInitParamValue()" value="0">
                    <option value="0" selected>初始化</option>
                    <option value="1">反初始化</option>
                    <option value="2">设置认证信息</option>
                </select>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:10px;padding:0;margin:0;" id="initBtn" class="btn" title="初始化、反初始化、设置认证信息功能入口">执行</button>
            </div>
        </div>
        
        <!--单个点位播放、批量点位播放、批量停止播放、全部停止播放接口调用入口。
        1.authUuid为多平台接入时必须的播放字段，单平台接入时，可不指定-->
        <div class="module" style="height:50;width:330px;padding:10;margin:10;">
            <div class="item">
                <label >播放相关参数：</label>
                <textarea id="playParam" type="text" style="width:310px;height:350px;">
{
    "argument": {
        "authUuid": "",
        "cameraIndexCode": "",
        "ezvizDirect": 0,
        "gpuMode": 0,
        "streamMode": 0,
        "transMode": 1,
        "wndId": -1,
        "cascade": 1
    },
    "funcName": "startPreview"
}
                </textarea>
            </div>
            <div class="item">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <select id="playFuncType" onchange="UpdatePlayParamValue()"  value="0">
                    <option value="0" selected>指定监控点编号播放</option>
                    <option value="1">指定监控点编号批量播放</option>
                    <option value="2">指定窗口Id集停止播放</option>
                    <option value="3">停止全部播放</option>
                </select>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:10px;padding:0;margin:0;" id="playBtn" class="btn" title="指定监控点编号播放 、指定监控点编号批量播放、指定窗口Id集停止播放、停止全部播放功能入口">执行</button>
            </div>
        </div>
        
        <!-- 轮巡测试模块，仅支持单平台对接时的测试效果，多个监控点编号以英文逗号分隔开-->
        <div class="module" id = "TourPreview" style="display: ;" style="height:50;width:330px;padding:10;margin:10;">
            <div class="item" >
                <label >10s轮巡预览(仅支持测试单平台轮巡)</label>
                 <div class="item">
                    <label >监控点编号集(多个监控点以','分隔)：</label>
                    <textarea id="TourPreviewCamIdx" type="text" style="width:310px;height:325px;"  placeholder="多个监控点以','分隔" ></textarea>
                </div>
                <!--<div class="item"><label >监控点编号集：</label>
                <input id="TourPreviewCamIdx" type="text" placeholder="多个监控点以','分隔" value="4c47d87440304304ad948883a76350a0,5540a1c645314e43b31a53bf2799d2a4,6bb1345ba3fe428f80631f8a38c3386d"></div>-->
                <div class="item">
                    <select id="tourPreviewType" style="width:130px;" value="0" >
                        <option value="0" selected>单路播放接口轮巡</option>
                        <option value="1">批量播放接口轮巡</option>
                    </select>
                    <button style="width:;padding:0;margin:0;" id="startTourPreviewBtn" class="btn" title="1.支持单路预览接口轮巡和批量播放接口轮巡，可通过下拉框配置； &#13;&#10;2.仅支持单平台对接预览时测试轮巡功能； &#13;&#10;3.轮巡时间默认10s，不可配置，但可修改html文件中的内容修改轮巡时间；">开始轮巡预览</button>
                    <button style="width:;padding:0;margin:0;" id="stopTourPreviewBtn" class="btn"  title="1.需要配合开始轮巡预览使用，每次开始后都需要停止">停止轮巡预览</button>
                </div>
			</div>
        </div>
        
        <!--设置布局、获取布局、画面字符叠加接口调用入口-->
        <div class="module" style="height:50;width:330px;padding:10;margin:10;">
            <!--设置布局、获取布局接口调用入口
            1.支持初始化时指定默认布局
            2.支持手动点击插件界面上的切换布局或调用切换布局接口后，抛出切换布局消息，信息包含切换后的布局类型、窗口个数-->
            <div class="item">
                <label >设置布局相关参数：</label>
                <textarea id="layoutParam" type="text" style="width:310px;height:75px;">
{
    "funcName": "getLayout"
}
                </textarea>
            </div>
            <div class="item">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <select id="layoutFuncType" onchange="UpdateLayoutParamValue()"  value="0">
                    <option value="0" selected>获取布局</option>
                    <option value="1">设置布局</option>
                </select>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:10px;padding:0;margin:0;" id="layoutBtn" class="btn" title="1.获取布局、设置布局功能入口&#13;&#10;2.双击窗口放大后，切换为1窗口视图，但还是原有布局类型，仅视图切换为1窗口">执行</button>
            </div>

            <!--画面字符叠加接口调用入口
            1.支持调整字体颜色、字体大小、是否加粗、x轴位置、y轴位置、字符所占区域的居中类型（各字段具体取值范围详见对应开发指南）
            2.alignType居中类型字段、仅支持对字符所占矩形区域的内容进行居中调整，而不是整个画面的矩形区域居中调整（位置信息主要根据x、y轴字段内容）
            3.字体大小设置后，插件放大或窗口放大，字体大小不会发生改变-->
            <div class="item">
                <label >画面字符叠加相关参数：</label>
                <textarea id="drawOSDParam" type="text" style="width:310px;height:175px;">
{
    "argument": {
        "alignType": 1,
        "bold": 0,
        "color": 255,
        "fontSize": 12,
        "text": "测试监控点",
        "wndId": 0,
        "x": 0,
        "y": 0
    },
    "funcName": "drawOSD"
}
                </textarea>
            </div>
            <div class="item">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:100px;padding:0;margin:0;" id="drawOSDBtn" class="btn" title="1.对齐方式仅支持控制字符所占矩形区域内的对齐方式 &#13;&#10;2.字体大小设置后，效果固定，无法随着画面变大变小一起变大变小 &#13;&#10;3.去除字符叠加内容只需要将叠加字符串字段传入空字符即可">画面字符叠加</button>
            </div>
            <div class="item">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:100px;padding:0;margin:0;" id="enterFullScreen" class="btn">进入全屏</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:100px;padding:0;margin:0;" id="exitFullScreen" class="btn">退出全屏</button>
            </div>
        </div>
        
        <!--抓图接口调用入口、接口调用返回信息显示、插件事件信息显示-->
        <div class="module" style="height:50;width:350px;padding:10;margin:10;">
            <!--抓图接口调用入口
            1.抓图仅支持jpg和bmp后缀的图片文件名，其他后缀则报错
            2.抓图后，插件会抛出抓图结果事件。若成功，则包含图片存储路径；当且仅当抓图后缀为jpg时，支持返回图片base64位编码字符串字段-->
            <div class="item">
                <label >抓图相关参数：</label>
                <textarea id="snapParam" type="text" style="width:310px;height:100px;">
{
    "argument": {
        "name": "D:/snap/测试抓图.jpg",
        "wndId": 1
    },
    "funcName": "snapShot"
}
                </textarea>
            </div>
            <div class="item">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button style="width:100px;padding:0;margin:0;" id="snapBtn" class="btn" title="1.仅支持jpg和bmp两种图片格式 &#13;&#10;2.bmp抓图后，由于图片过大，抓图事件中无图片base64位编码信息 &#13;&#10;3.支持设置路径+名称、仅设置路径，若路径错误，使用默认路径及图片命名规则">抓图</button>
            </div>
            
            <legend>返回值信息</legend>
            <div id="cbInfo" class="cbInfo"></div>
            &nbsp;&nbsp;
            <button id="clear">清空</button>
            &nbsp;&nbsp;&nbsp;
            <button id="getVerSion" title="插件V1.5.0以下版本不支持此功能（V1.5.0版本支持）">获取版本号</button>
            &nbsp;&nbsp;&nbsp;
            <button id="instructions" title="1.插件demo使用前先安装对应版本的插件到本地机器； &#13;&#10;2.所有操作都需要先初始化。若反初始化后，需要再次操作，请重新初始化； &#13;&#10;3.初始化指定ip、port、appkey、secret时，仅支持单平台接入，此时播放参数中authUuid字段非必填（参照开发安指南文档）； &#13;&#10;4.初始化不指定ip、port、appkey、secret时，支持多平台接入，需要通过设置认证信息传入各平台的合作方信息，此时播放参数中authUuid字段必填；&#13;&#10;5.demo中轮巡模块只有在单平台接入预览时，才显示；多平台接入或回放时，默认隐藏">使用说明(弹框示例)</button>
        </div>
    </div>
    <div id="light" class="white_content">
        <!--iframe标签，为解决ie10上OCX控件遮挡div问题。chrome或ie11使用websocket方式，无需使用iframe-->
        <iframe  src='about:blank' frameBorder='0' marginHeight='0' marginWidth='0' style='position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 0);'></iframe>
        这是一个弹窗示例<a href = "javascript:void(0)" onclick=CloseInstructionsDiv()>点这里关闭本窗口
        </a>
        <div>【使用说明】 </div>
        <div>1.插件demo使用前先安装对应版本的插件到本地机器； </div>
        <div>2.所有操作都需要先初始化。若反初始化后，需要再次操作，请重新初始化；</div>
        <div>3.初始化指定ip、port、appkey、secret时，仅支持单平台接入，此时播放参数中authUuid字段非必填（参照开发安指南文档）； </div>
        <div>4.初始化不指定ip、port、appkey、secret时，支持多平台接入，需要通过设置认证信息传入各平台的合作方信息，此时播放参数中authUuid字段必填；</div>
        <div>5.demo中轮巡模块只有在单平台接入预览时，才显示；多平台接入或回放时，默认隐藏</div>
    </div> 
</body>
<script src="jquery-1.12.4.min.js"></script>
<script src="jsencrypt.min.js"></script>
<script src="jsWebControl-1.0.0.min.js"></script>

<script type="text/javascript">
    // 插件对象实例，初始化为null，需要创建多个插件窗口时，需要定义多个插件对象实例变量，各个变量唯一标志对应的插件实例
    var oWebControl = null;
    var bIE = (!!window.ActiveXObject || 'ActiveXObject' in window);// 是否为IE浏览器
    var pubKey = '';                    // demo中未使用加密，可根据需求参照开发指南自行使用加密功能
	var initCount = 0;
    var playMode = 0;                   // 播放类型，0-预览，1-回放
	var showDivInstruction = false;     // 标志是否显示使用说明弹框
    
    var endTime = Math.floor(new Date(dateFormat(new Date(), "yyyy-MM-dd 23:59:59").replace('-', '/').replace('-', '/')).getTime() / 1000).toString();
    var startTime = Math.floor(new Date(dateFormat(new Date(), "yyyy-MM-dd 00:00:00").replace('-', '/').replace('-', '/')).getTime() / 1000).toString();
	var playTime = Math.floor(new Date(dateFormat(new Date(), "yyyy-MM-dd 00:00:00").replace('-', '/').replace('-', '/')).getTime() / 1000).toString();

    
    function UpdateInitParamValue(){
        var sel = document.getElementById("initFuncType");
        var selectedId = sel.selectedIndex;
        var v = sel.options[selectedId].value;
        var param;
        if (0 == v){                //更新为初始化请求的参数
            var result = JSON.stringify({
                "argument": {
                    "appkey": "",
                    "ip": "",
                    "port": 443,
                    "secret": "",
                    "enableHTTPS": 1,
                    "language": "zh_CN",
                    "layout": "2x2",
                    "playMode": 0,
                    "reconnectDuration": 5,
                    "reconnectTimes": 5,
                    "showSmart": 0,
                    "showToolbar": 1,
                    "toolBarButtonIDs": "2048,2049,2050,2304,2306,2305,2307,2308,2309,4096,4608,4097,4099,4098,4609,4100",
                    "snapDir": "D:/snap",
                    "videoDir": "D:/video"
                },
                "funcName": "init"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('initParam').innerText= result ;
            $("#initParam").val(result);
        }
        else if (1 == v)                //更新为反初始化请求的参数
        {
            var result = JSON.stringify({
                "funcName": "uninit"
            }, null, 2);//将json对象转换成字符串
            
            //document.getElementById('initParam').innerText= result ;
            $("#initParam").val(result);
        }
        else                            //更新为设置认证信息请求的参数
        {
            var result = JSON.stringify({
                "argument": {
                    "list": [
                        {
                            "appkey": "",
                            "authUuid": "111",
                            "ip": "",
                            "port": 443,
                            "secret": ""
                        },
                        {
                            "appkey": "",
                            "authUuid": "222",
                            "ip": "",
                            "port": 443,
                            "secret": ""
                        }
                    ]
                },
                "funcName": "setAuthInfo"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('initParam').innerText= result;
            $("#initParam").val(result);
        }
    }
    
    function UpdatePlayParamValue(){
        var sel = document.getElementById("playFuncType");
        var selectedId = sel.selectedIndex;
        var v = sel.options[selectedId].value;
        var param;
        if (0 == v){                    //更新为单个播放请求的参数，根据初始化时的playmode字段，设置为预览或回放
            var result = 0 == playMode ? JSON.stringify({
                "argument": {
                    "authUuid": "",
                    "cameraIndexCode": "",
                    "ezvizDirect": 0,
                    "gpuMode": 0,
                    "streamMode": 0,
                    "transMode": 1,
                    "wndId": -1,
		            "cascade": 1
                },
                "funcName": "startPreview"
            }, null, 2) : JSON.stringify({
                "argument": {
                    "authUuid": "",
                    "cameraIndexCode": "",
                    "endTimeStamp": endTime,
                    "ezvizDirect": 0,
                    "gpuMode": 0,
                    "playTimeStamp": startTime,
                    "recordLocation": 0,
                    "startTimeStamp": startTime,
                    "transMode": 1,
                    "wndId": 0,
		            "cascade": 1
                },
                "funcName": "startPlayback"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('playParam').innerText= result;
            $("#playParam").val(result);
        }
        else if (1 == v)                    //更新为批量播放请求的参数，根据初始化时的playmode字段，设置为预览或回放
        {
            var result =  0 == playMode ? JSON.stringify({
                "argument": {
                    "list": [
                        {
                            "authUuid": "",
                            "cameraIndexCode": "",
                            "ezvizDirect": 0,
                            "gpuMode": 0,
                            "streamMode": 0,
                            "transMode": 1,
                            "wndId": 1
                        },
                        {
                            "authUuid": "",
                            "cameraIndexCode": "",
                            "ezvizDirect": 0,
                            "gpuMode": 0,
                            "streamMode": 0,
                            "transMode": 1,
                            "wndId": 2
                        }
                    ]
                },
                "funcName": "startMultiPreviewByCameraIndexCode"
            }, null, 2) : JSON.stringify({
                "argument": {
                    "list": [
                        {
                            "authUuid": "",
                            "cameraIndexCode": "",
                            "endTimeStamp": endTime,
                            "ezvizDirect": 0,
                            "gpuMode": 0,
                            "playTimeStamp": startTime,
                            "recordLocation": 0,
                            "startTimeStamp": startTime,
                            "transMode": 1,
                            "wndId": 1
                        },
                        {
                            "authUuid": "",
                            "cameraIndexCode": "",
                            "endTimeStamp": endTime,
                            "ezvizDirect": 0,
                            "gpuMode": 0,
                            "playTimeStamp": startTime,
                            "recordLocation": 0,
                            "startTimeStamp": startTime,
                            "transMode": 1,
                            "wndId": 2
                        }
                    ]
                },
                "funcName": "startMultiPlaybackByCameraIndexCode"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('playParam').innerText= result;
            $("#playParam").val(result);
        }
        else if (2 == v)                    //更新为批量停止播放请求的参数
        {
            var result = JSON.stringify({
                "argument": {
                    "list": [
                        {
                            "wndId": 1
                        },
                        {
                            "wndId": 2
                        }
                    ]
                },
                "funcName": "stopMultiPlay"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('playParam').innerText= result;
            $("#playParam").val(result);
        }
        else                    //更新为停止所有播放请求的参数，根据初始化时的playmode字段，设置为预览或回放
        {
            var result =  0 == playMode ? JSON.stringify({
                "funcName": "stopAllPreview"
            }, null, 2) : JSON.stringify({
                "funcName": "stopAllPlayback"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('playParam').innerText= result;
            $("#playParam").val(result);
        }
    }
    
    function UpdateLayoutParamValue(){
        var sel = document.getElementById("layoutFuncType");
        var selectedId = sel.selectedIndex;
        var v = sel.options[selectedId].value;
        var param;
        if (0 == v){                    //更新为获取布局请求的参数
            var result = JSON.stringify({
                "funcName": "getLayout"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('layoutParam').innerText= result;
            $("#layoutParam").val(result);
        }
        else                    //更新为设置布局请求的参数
        {
            var result = JSON.stringify({
                "argument": {
                    "layout": "2x2"
                },
                "funcName": "setLayout"
            }, null, 2);//将json对象转换成字符串
            //document.getElementById('layoutParam').innerText= result;
            $("#layoutParam").val(result);
        }
    }
    
    // 标签关闭
    $(window).unload(function () {	
        if (oWebControl != null){
				oWebControl.JS_HideWnd();  // 先让窗口隐藏，规避可能的插件窗口滞后于浏览器消失问题
                oWebControl.JS_Disconnect().then(function(){}, function() {});
            }
    });

    // 窗口resize
    $(window).resize(function () {
        if (oWebControl != null) {
            oWebControl.JS_Resize(800, 400);
            setWndCover();
        }
    });

    // 滚动条scroll
    $(window).scroll(function () {
        if (oWebControl != null) {
            oWebControl.JS_Resize(800, 400);
            setWndCover();
        }
    });

    // 设置窗口裁剪，当因滚动条滚动导致窗口需要被遮住的情况下需要JS_CuttingPartWindow部分窗口
    function setWndCover() {
        //获取web页面的尺寸
        var iWidth = $(window).width();
        var iHeight = $(window).height();
        //获取播放窗口div元素的左边界、右边界距离web页面左边界的长度、上边界、下边界距离web页面上边界的长度
        var oDivRect = $("#playWnd").get(0).getBoundingClientRect();
        //Math.round 为四舍五入    Math.abs 为取绝对值
        var iCoverLeft = (oDivRect.left < 0) ? Math.abs(oDivRect.left): 0;
        var iCoverTop = (oDivRect.top < 0) ? Math.abs(oDivRect.top): 0;
        var iCoverRight = (oDivRect.right - iWidth > 0) ? Math.round(oDivRect.right - iWidth) : 0;
        var iCoverBottom = (oDivRect.bottom - iHeight > 0) ? Math.round(oDivRect.bottom - iHeight) : 0;

        iCoverLeft = (iCoverLeft > 800) ? 800 : iCoverLeft;
        iCoverTop = (iCoverTop > 400) ? 400 : iCoverTop;
        iCoverRight = (iCoverRight > 800) ? 800 : iCoverRight;
        iCoverBottom = (iCoverBottom > 400) ? 400 : iCoverBottom;

		oWebControl.JS_RepairPartWindow(0, 0, 801, 400);  // 多1个像素点防止还原后边界缺失一个像素条
        //抠除左边界
        if (iCoverLeft != 0) {
			oWebControl.JS_CuttingPartWindow(0, 0, iCoverLeft, 401);
        }
        //抠除上边界
        if (iCoverTop != 0) {
            oWebControl.JS_CuttingPartWindow(0, 0, 801, iCoverTop);  // 多剪掉一个像素条，防止出现剪掉一部分窗口后出现一个像素条
        }
        //抠除右边界
        if (iCoverRight != 0) {
            oWebControl.JS_CuttingPartWindow(801 - iCoverRight, 0, iCoverRight, 401);
        }
        //抠除下边界
        if (iCoverBottom != 0) {
            oWebControl.JS_CuttingPartWindow(0, 401 - iCoverBottom, 801, iCoverBottom);
        }
        //弹框示例位置扣除
        if (showDivInstruction)
        {
            // 获取弹框的位置、尺寸信息
            var oDivLightRect = document.getElementById('light').getBoundingClientRect();
            
            iCoverLeft = (oDivLightRect.left - oDivRect.left < 0) ? 0 : oDivLightRect.left - oDivRect.left;
            iCoverTop = (oDivLightRect.top - oDivRect.top < 0) ? 0 : oDivLightRect.top - oDivRect.top;
            iCoverRight = (oDivLightRect.right - oDivRect.left < 0) ? 0 : oDivLightRect.right - oDivRect.left;
            iCoverBottom = (oDivLightRect.bottom - oDivRect.top < 0) ? 0 : oDivLightRect.bottom - oDivRect.top;
            
            iCoverLeft = (iCoverLeft > 800) ? 800 : Math.round(iCoverLeft);
            iCoverTop = (iCoverTop > 400) ? 400 : Math.round(iCoverTop);
            iCoverRight = (iCoverRight > 800) ? 800 : Math.round(iCoverRight);
            iCoverBottom = (iCoverBottom > 400) ? 400 : Math.round(iCoverBottom);

            //JS_CuttingPartWindow接口参照开发指南，参数为：抠图左上角点在插件窗口上的left长度、top长度、所扣除矩形区域的宽度、所扣除矩形区域的长度
            oWebControl.JS_CuttingPartWindow(iCoverLeft - 1, iCoverTop - 1, iCoverRight - iCoverLeft + 2, iCoverBottom - iCoverTop + 2);
        }
    }
	
    // 创建插件实例，并启动本地服务建立websocket连接，创建插件窗口
	function initPlugin () {
		oWebControl = new WebControl({
			szPluginContainer: "playWnd",
			iServicePortStart: 15900,
			iServicePortEnd: 15909,
			szClassId:"23BF3B0A-2C56-4D97-9C03-0CB103AA8F11",   // 用于IE10使用ActiveX的clsid
			cbConnectSuccess: function () {
				initCount = 0;
				setCallbacks();			
				oWebControl.JS_StartService("window", {
					dllPath: "./VideoPluginConnect.dll"
				}).then(function () {
					oWebControl.JS_CreateWnd("playWnd", 800, 400).then(function () {
						console.log("JS_CreateWnd success");
                        oWebControl.JS_RequestInterface({
                        funcName: "getRSAPubKey",
                        argument: JSON.stringify({
                        keyLength: 1024
                        })
					    }).then(function (oData) {
						    console.log(oData)
						    if (oData.responseMsg.data) {
						    pubKey = oData.responseMsg.data
					       }
					    });						
					});
				}, function () {
				
				});
			},
			cbConnectError: function () {
				console.log("cbConnectError");
				oWebControl = null;
				$("#playWnd").html("插件未启动，正在尝试启动，请稍候...");
				WebControl.JS_WakeUp("VideoWebPlugin://");
				initCount ++;
				if (initCount < 3) {
					setTimeout(function () {
						initPlugin();
					}, 3000)
				} else {
					$("#playWnd").html("插件启动失败，请检查插件是否安装！");
				}
			},
			cbConnectClose: function (bNormalClose) {
				// 异常断开：bNormalClose = false
				// JS_Disconnect正常断开：bNormalClose = true
				console.log("cbConnectClose");
				oWebControl = null;
			}
		});
	}
	
	initPlugin(); 

    // 获取公钥
    function getPubKey (callback) {
        oWebControl.JS_RequestInterface({
            funcName: "getRSAPubKey",
            argument: JSON.stringify({
                keyLength: 1024
            })
        }).then(function (oData) {
            console.log(oData)
            if (oData.responseMsg.data) {
                pubKey = oData.responseMsg.data
                callback()
            }
        })
    }

    // 设置窗口控制回调
    function setCallbacks() {
        oWebControl.JS_SetWindowControlCallback({
            cbIntegrationCallBack: cbIntegrationCallBack
        });
    }

    // 推送消息
    function cbIntegrationCallBack(oData) {
        showCBInfo(JSON.stringify(oData.responseMsg));
    }

    // RSA加密
    function setEncrypt (value) {
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(pubKey);
        return encrypt.encrypt(value);
    }

    // value为字符串，JS_RequestInterface仅接收json格式的变量，且需要先解析出argument，并且将argument字段的内容转为字符串
    function requestInterface(value)
    {
        isJSON(value);
        var JsonParam = JSON.parse(value);
        var JsonArgument = JsonParam.argument;
        JsonParam.argument = JSON.stringify(JsonArgument);
        
        oWebControl.JS_RequestInterface(JsonParam).then(function (oData) {
            console.log(oData)
            showCBInfo(JSON.stringify(oData ? oData.responseMsg : ''));
        });
    }

    // 显示接口返回的消息及插件回调信息
    function showCBInfo(szInfo, type) {
        if (type === 'error') {
            szInfo = "<div style='color: red;'>" + dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss") + " " + szInfo + "</div>";
        } else {
            szInfo = "<div>" + dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss") + " " + szInfo + "</div>";
        }
        $("#cbInfo").html(szInfo + $("#cbInfo").html());
    }

    $("#initBtn").click(function() {
        var param = $("#initParam").val();
        //删除字符串中的回车换行和空格
		//param = param.replace(/(\s*)/g, "");
        //解析初始化时的playMode字段
        var initFuncType = $("#initFuncType").val();
        if (0 == initFuncType)
        {
            isJSON(param);
            var JsonParam = JSON.parse(param);
            if (!JsonParam.hasOwnProperty("argument"))
            {
                showCBInfo("init failed, param miss argument field");
            }
            else
            {
                if (JsonParam.argument.hasOwnProperty("playMode"))
                {
                    playMode = JsonParam.argument.playMode;
                }
                //隐藏/显示轮巡功能模块
                document.getElementById("TourPreview").style.display= (0 == playMode && JsonParam.argument.hasOwnProperty("appkey")) ? "" : "none";
				
				//如果包含加密处理，处理加密字段
				if(JsonParam.argument.hasOwnProperty("encryptedFields"))
				{
				    var enFields = JsonParam.argument.encryptedFields;
					var strArray = new Array();
					strArray = enFields.split(",");
					for(var i = 0, len = strArray.length; i< len; i++)
					{
					    if("appkey" == strArray[i])
						{
							if (JsonParam.argument.hasOwnProperty("appkey"))
							{
								var appkey = JsonParam.argument.appkey;
								appkey = setEncrypt(appkey);
								JsonParam.argument.appkey = appkey;
							}
						}
						
						if("secret" == strArray[i])
						{
							if (JsonParam.argument.hasOwnProperty("secret"))
							{
								var secret = JsonParam.argument.secret;
								secret = setEncrypt(secret);
								JsonParam.argument.secret = secret;
							}
						}
						
						if("ip" == strArray[i])
						{
						    if (JsonParam.argument.hasOwnProperty("ip"))
							{
								var ip = JsonParam.argument.ip;
								ip = setEncrypt(ip);
								JsonParam.argument.ip = ip;
							}
						}
						
						if("snapDir" == strArray[i])
						{
						    if (JsonParam.argument.hasOwnProperty("snapDir"))
							{
								var snapDir = JsonParam.argument.snapDir;
								snapDir = setEncrypt(snapDir);
								JsonParam.argument.snapDir = snapDir;
							}
						}
						
						if("layout" == strArray[i])
						{
						    if (JsonParam.argument.hasOwnProperty("layout"))
							{
								var layout = JsonParam.argument.layout;
								layout = setEncrypt(layout);
								JsonParam.argument.layout = layout;
							}
						}
						
						if("videoDir" == strArray[i])
						{
						    if (JsonParam.argument.hasOwnProperty("videoDir"))
							{
								var videoDir = JsonParam.argument.videoDir;
								videoDir = setEncrypt(videoDir);
								JsonParam.argument.videoDir = videoDir;
							}
						}
					}
				}

                //1.4.1及以上版本支持argument字段为json，以下版本argument必须为string
                var JsonArgument = JsonParam.argument;
                JsonParam.argument = JSON.stringify(JsonArgument);
            }
            
            //param = {
            //    "argument": '{"appkey": "21216099","ip": "172.7.13.242","port": 443,"secret": "dX5Gt0C0hmKbQ9ucHnWY","enableHTTPS": 1,"language": "zh_CN","layout": "2x2","playMode": 0,"reconnectDuration": 5,"reconnectTimes": 5,"showSmart": 0,"showToolbar": 1,"snapDir": "D:/snap","videoDir": "D:/video"}',
            //    "funcName": "init"
            //}
            
            oWebControl.JS_RequestInterface(JsonParam).then(function (oData) {
                console.log(oData)
                showCBInfo(JSON.stringify(oData ? oData.responseMsg : ''));
                UpdatePlayParamValue();
                oWebControl.JS_Resize(800, 400);  // 
            });
        }
        else{
            requestInterface(param);
        }
    })

    $("#playBtn").click(function() {
        var param = $("#playParam").val();
        //删除字符串中的回车换行
		//param = param.replace(/(\s*)/g, "");

        requestInterface(param);
    })
    
    // 设置布局/获取布局
    $("#layoutBtn").click(function() {
        var param = $("#layoutParam").val();
        //删除字符串中的回车换行
		//param = param.replace(/(\s*)/g, "");

        requestInterface(param);
    })
    
    //画面字符叠加
	$("#drawOSDBtn").click(function (){
        var param = $("#drawOSDParam").val();
        //删除字符串中的回车换行
		//param = param.replace(/(\s*)/g, "");

        requestInterface(param);
	})

    // 进入全屏
	$("#enterFullScreen").click(function (){
        oWebControl.JS_RequestInterface({
            funcName: "setFullScreen"
        }).then(function (oData) {
            console.log(oData)
            showCBInfo(JSON.stringify(oData ? oData.responseMsg : ''));
        });
	})
    
    // 退出全屏
	$("#exitFullScreen").click(function (){
        oWebControl.JS_RequestInterface({
            funcName: "exitFullScreen"
        }).then(function (oData) {
            console.log(oData)
            showCBInfo(JSON.stringify(oData ? oData.responseMsg : ''));
        });
	})
    
    //抓图
	$("#snapBtn").click(function (){
        var param = $("#snapParam").val();
        //删除字符串中的回车换行
		//param = param.replace(/(\s*)/g, "");

        requestInterface(param);
	})
    
    var timeStart = false;
    var timer;
    //轮巡，每隔10s执行一次
	$("#startTourPreviewBtn").click(function (){
        
        if (!timeStart)
        {
            timeStart = true;
            var sel = document.getElementById("tourPreviewType");
            var selectedId = sel.selectedIndex;
            var v = sel.options[selectedId].value;
            if (0 == v)     // 单路播放轮巡
            {
                SingleTourPreview();
                timer = window.setInterval(SingleTourPreview, 1000*10);
            }
            else{           // 批量播放轮巡
                MultiTourPreview();
                timer = window.setInterval(MultiTourPreview, 1000*10);
            }
        }
        else{
            showCBInfo('start tour preview failed, please stop tour preview first');
        }
	})
    
    $("#stopTourPreviewBtn").click(function(){
        window.clearInterval(timer);
        timeStart = false;
    })
    
    // 单路播放轮巡函数
    function SingleTourPreview() {
        // 解析轮巡的监控点编号
        var param = $("#TourPreviewCamIdx").val();
        //param = param.replace(/(\s*)/g, "");
        var cameraIndexArray = new Array();
        cameraIndexArray = param.split(",");
        var arraySize = cameraIndexArray.length;
        
        // 获取当前窗口个数
        var WndNum = 1;
        oWebControl.JS_RequestInterface({
            funcName: "getLayout"
        }).then(function (oData) {
            //分析窗口数
            var Data = JSON.stringify(oData.responseMsg.data);
            Data = Data.replace(/\\n/g, "");
            Data = Data.replace(/\\/g, "");
            Data = Data.replace(/\"{/g, "{");
            Data = Data.replace(/}\"/g, "}");
            WndNum = JSON.parse(Data).wndNum;
            
            for (i = 0; i < WndNum; i++)
            {
                var cameraIdx = cameraIndexArray[i % arraySize];
                var previewParam = JSON.stringify({
                    "argument": {
                        "authUuid": "",
                        "cameraIndexCode": cameraIdx,
                        "ezvizDirect": 0,
                        "gpuMode": 0,
                        "streamMode": 0,
                        "transMode": 1,
                        "wndId": i+1,
		                "cascade": 1
                    },
                    "funcName": "startPreview"
                });
                requestInterface(previewParam);
            }
        });
    }
    
    // 批量播放轮巡函数
    function MultiTourPreview() {
        // 解析轮巡的监控点编号
        var param = $("#TourPreviewCamIdx").val();
        //param = param.replace(/(\s*)/g, "");
        var cameraIndexArray = new Array();
        cameraIndexArray = param.split(",");
        var arraySize = cameraIndexArray.length;
        
        // 获取当前窗口个数
        var WndNum = 1;
        oWebControl.JS_RequestInterface({
            funcName: "getLayout"
        }).then(function (oData) {
            //分析窗口数
            var Data = JSON.stringify(oData.responseMsg.data);
            Data = Data.replace(/\\n/g, "");
            Data = Data.replace(/\\/g, "");
            Data = Data.replace(/\"{/g, "{");
            Data = Data.replace(/}\"/g, "}");
            WndNum = JSON.parse(Data).wndNum;
            
            var multiPlayParam = {
                "argument": {
                    "list": []
                },
                "funcName": "startMultiPreviewByCameraIndexCode"
            }
            
            for (i = 0; i < WndNum; i++)
            {
                var PlayParam = {
                    "authUuid": "",
                    "cameraIndexCode": cameraIndexArray[i % arraySize],
                    "ezvizDirect": 0,
                    "gpuMode": 0,
                    "streamMode": 0,
                    "transMode": 1,
                    "wndId": i+1
                }

                multiPlayParam.argument.list.push(PlayParam);
            }
            var previewParam = JSON.stringify(multiPlayParam);
            requestInterface(previewParam);
        });
    }
    
    // 清空
    $("#clear").click(function() {
        $("#cbInfo").html('');
    })
    
    // 获取版本
    $("#getVerSion").click(function() {
        oWebControl.JS_RequestInterface({
            funcName: "getVersion"
        }).then(function (oData) {
            console.log(oData)
            showCBInfo(JSON.stringify(oData ? oData.responseMsg : ''));
        });
    })
    
    // 使用说明
    $("#instructions").click(function() {
        //var instructionInfo = "1.插件demo使用前先安装对应版本的插件到本地机器； \n2.所有操作都需要先初始化，反初始化后，再次操作需要重新初始化； \n3.初始化指定ip、port、appkey、secret时，仅支持单平台接入，此时播放参数中authUuid字段非必填； \n4.初始化不指定ip、port、appkey、secret时，支持多平台接入，需要通过设置认证信息传入各平台的合作方信息，此时播放参数中authUuid字段必填；\n5.demo中轮巡模块只有在单平台接入预览时，才显示；多平台接入或回放时，默认隐藏";
        //$("#cbInfo").html(instructionInfo + $("#cbInfo").html());
        document.getElementById('light').style.display='block';
        showDivInstruction = true;
        setWndCover();
    })
    
    // 关闭隐藏弹框
    function CloseInstructionsDiv()
    {
        document.getElementById('light').style.display='none';
        showDivInstruction = false;
        setWndCover();
    }
    
    // 判断字符串是否为json
    function isJSON(str) {
        if (typeof str == 'string') {
            try {
                var obj=JSON.parse(str);
                if(typeof obj == 'object' && obj ){
                    return true;
                }else{
                    showCBInfo("param is not the correct JSON message");
                    return false;
                }
            } catch(e) {
                showCBInfo("param is not the correct JSON message");
                return false;
            }
        }
        console.log('It is not a string!')
    }

    // 格式化时间
    function dateFormat(oDate, fmt) {
        var o = {
            "M+": oDate.getMonth() + 1, //月份
            "d+": oDate.getDate(), //日
            "h+": oDate.getHours(), //小时
            "m+": oDate.getMinutes(), //分
            "s+": oDate.getSeconds(), //秒
            "q+": Math.floor((oDate.getMonth() + 3) / 3), //季度
            "S": oDate.getMilliseconds()//毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (oDate.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

	function textbox(obj, min, max){
		obj.value=obj.value.replace(/[^\d]/g,'');
		if(parseInt(obj.value)==obj.value && parseInt(obj.value)>=min && parseInt(obj.value)<=max)
		{}
		else
		{
			if(parseInt(obj.value) < min)
			{
				obj.value = min;
			}
			if(parseInt(obj.value) > max)
			{
				obj.value = max;
			}
		}	
	}
</script>
</html>