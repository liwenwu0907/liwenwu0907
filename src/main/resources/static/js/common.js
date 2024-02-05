//页面通用变量
var $,
    jQuery,
    layer;


//页面初始化后执行
var isDisplayRightCommonWidget = true;
layui.use(['jquery', 'layer'], function () {
    layer = layui.layer;
    $ = jQuery = layui.jquery;
    $(function () {
        if (!(self.frameElement && self.frameElement.tagName == "IFRAME")) {
            if (isDisplayRightCommonWidget) {
                //加载页面右侧控件
                $("body").append('<div class="con-right-operate-info"><div class="bdc-right-other"><a class="SidebarAppCodeIcon" href="javascript:;"><img class="bdc-default-img" src="' + getStaticResourceIp() + '/images/appCode.png" alt=""><span>扫码下载App</span><img class="bdc-hover-img bdc-hover-appCode bdc-hide" src="' + getStaticResourceIp() + '/images/dynamic/appCode.jpg" alt=""></a><a class="SidebarWechatIcon" href="javascript:;"><img class="bdc-default-img" src="' + getStaticResourceIp() + '/images/gzh.png" alt=""><span>微信公众号</span><img class="bdc-hover-img bdc-hover-gzh bdc-hide" src="' + getStaticResourceIp() + '/images/dynamic/public-number-qrcode.jpg" alt=""></a><a class="SidebarQuestionIcon" href="javascript:;" onclick="pageToUrl(' + "'intelligent_dialogue','_blank'" + ')"><img class="bdc-default-img" src="' + getStaticResourceIp() + '/images/kf.png" alt=""><span>智能客服</span></a><a class="SidebarHotlineIcon" href="javascript:;"><img class="bdc-default-img" src="' + getStaticResourceIp() + '/images/rx.png" alt=""><span>市民热线</span><img class="bdc-hover-img bdc-hover-rx bdc-hide" src="' + getStaticResourceIp() + '/images/dynamic/server-number.png" alt=""></a><a href="javascript:;" class="bdc-pack-up"><i class="layui-icon layui-icon-up"></i></a></div><div class="bdc-right-other-open bdc-hide"><img src="' + getStaticResourceIp() + '/images/zk.png" alt=""></div></div>');
                //根据functionConfig.xml配置隐藏与显示
                if (conPageConfig.estateplat_register.SidebarAppCodeIcon && conPageConfig.estateplat_register.SidebarAppCodeIcon != 'true') {
                    $(".SidebarAppCodeIcon").css("display", "none");
                }
                if (conPageConfig.estateplat_register.SidebarWechatIcon && conPageConfig.estateplat_register.SidebarWechatIcon != 'true') {
                    $(".SidebarWechatIcon").css("display", "none");
                }
                if (conPageConfig.estateplat_register.SidebarQuestionIcon && conPageConfig.estateplat_register.SidebarQuestionIcon != 'true') {
                    $(".SidebarQuestionIcon").css("display", "none");
                }
                if (conPageConfig.estateplat_register.SidebarHotlineIcon && conPageConfig.estateplat_register.SidebarHotlineIcon != 'true') {
                    $(".SidebarHotlineIcon").css("display", "none");
                }
                if (conPageConfig.estateplat_register.SidebarWechatIcon != 'true' && conPageConfig.estateplat_register.SidebarQuestionIcon != 'true' && conPageConfig.estateplat_register.SidebarHotlineIcon != 'true') {
                    $(".bdc-right-other").css("display", "none");
                }
                //点击收起
                $('body').on('click', '.bdc-pack-up', function () {
                    $('.bdc-right-other').addClass('bdc-hide');
                    $('.bdc-right-other-open').removeClass('bdc-hide');
                });
                //点击展开
                $('body').on('click', '.bdc-right-other-open', function () {
                    $('.bdc-right-other').removeClass('bdc-hide');
                    $('.bdc-right-other-open').addClass('bdc-hide');
                });

                //鼠标进入右侧固定列
                $('.bdc-right-other>a').on('mouseenter', function () {
                    $(this).find('.bdc-hover-img').removeClass('bdc-hide');
                }).on('mouseleave', function () {
                    $(this).find('.bdc-hover-img').addClass('bdc-hide');
                });
            }
        }
        //流程回退控制
        (function () {
            if (window.history.pushState) {
                window.history.pushState(null, {});
                window.addEventListener("popstate", function (e) {
                    if (sessionStorage.getItem("applyProcessObj")) {
                        if (confirm("您确定要终止流程，返回个人中心吗?")) {
                            sessionStorage.removeItem("applyProcessObj")
                            toUserIndex();
                        } else {
                            window.history.pushState(null, {});
                        }
                    } else {
                        history.go(-1);
                    }
                }, false);
            }
        })();

        //省市县页面基础信息赋值
        $(".province-name").text(conPageConfig.area_base_info.province)
        $(".city-name").text(conPageConfig.area_base_info.city)
        $(".county-name").text(conPageConfig.area_base_info.county)
        $(".address-name").text(conPageConfig.area_base_info.address)
    });
});


//注册事件-手机和座机验证
layui.use(['form'], function () {
    var form = layui.form;
    form.verify(
        {
            phone: function (phone) {
                if (phone && !(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(phone)) && !(/^1\d{10}$/.test(phone))) {
                    return "请输入正确的联系电话，例如：座机：XXX-XXXXXXX或手机号(11位数字)";
                }
            },
            isPhone: function (phone) {
                if (phone && !(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(phone)) && !(/^1\d{10}$/.test(phone))) {
                    return "请输入正确的联系电话，例如：座机：XXX-XXXXXXX或手机号(11位数字)";
                }
            }
        }
    );
});

//序列化页面大对象
var conPageConfig = sessionStorage.getItem("cur_pageElement");
if (conPageConfig == '' || conPageConfig == 'null' || conPageConfig == 'undefined' || !conPageConfig) {
    postDataToServer("/configModel/getFunctional", {pathname: ""}, function (PageElement_data) {
        conPageConfig = PageElement_data;
        sessionStorage.setItem("cur_pageElement", JSON.stringify(PageElement_data));
    }, true);
} else {
    conPageConfig = JSON.parse(sessionStorage.getItem("cur_pageElement"));
}

//初始化系统状态对象集合
function systemStateArr() {
    this.stateArr = [];
    this.getItemByDm = function (dm) {
        return this.stateArr.find(function (item) {
            return item.dm == String(dm)
        });
    };
    this.getItemByMc = function (mc) {
        return this.stateArr.find(function (item) {
            return item.mc == String(mc)
        });
    };
    var getColorByDm = function (dm) {
        if (["0"].indexOf(dm) > -1) {
            return "#666666"
        }
        if (["1", "4"].indexOf(dm) > -1) {
            return "#1d87d1"
        }
        if (["2", "7", "8", "9"].indexOf(dm) > -1) {
            return "#32b032"
        }
        if (["3", "5", "6", "10", "11"].indexOf(dm) > -1) {
            return "#f24b43"
        }
        if (["12"].indexOf(dm) > -1) {
            return "#FFC125"
        }
    };
    //补充受理状态颜色
    if (conPageConfig.dict) {
        this.stateArr = conPageConfig.dict.slzt.map(function (item) {
            item.color = getColorByDm(item.dm);
            return item;
        });
        this.stateArr.unshift({mc: "", dm: "", color: ""});
    }
}
var conPageApplicationState = new systemStateArr();

//配置服务url
function getIP() {
    //return "http://lkwx.gtis.com.cn/estateplat-olcommon-kf/api/v2";
    //return "http://www.hfzrzy.com/estateplat-olcommon/api/v2";
    return "http://192.168.8.208/estateplat-olcommon/api/v2";
    //return "http://127.0.0.1/estateplat-olcommon/api/v2";

}

function getRegisterApiIP() {
    //return "http://lkwx.gtis.com.cn/estateplat-olcommon-kf/api/v2";
    //return "http://www.hfzrzy.com/estateplat-olcommon/api/v2";
    return "http://192.168.8.208:8080/estateplat-register/v2";
    //return "http://127.0.0.1:8080/estateplat-register/v2";
}

// 配置页面url
function getRegisterIp() {
    //return "http://lkwx.gtis.com.cn/estateplat-register-kf/page/v2.1";
    return "http://192.168.8.208:8080/estateplat-register/page/v2.1";
    //return "http://127.0.0.1:8080/estateplat-register/page/v2.1";

}

//配置静态资源IP
function getStaticResourceIp() {
    //return "http://lkwx.gtis.com.cn/estateplat-register-kf/static/project2.1";
    return "http://192.168.8.208:8080/estateplat-register/static/project2.1";
    //return "http://127.0.0.1:8080/estateplat-register/static/project2.1";

}

//获取支付宝id，state
function con_getAliPayStateId() {
    return {
        app_id: "2019050564328963",
        state: new Date().getTime() + "" + parseInt(Math.random() * 99999)
    }
}

//微信扫码登录配置
function con_getWechatConfig() {
    return {
        app_id: "wxf352b9c25ffd4f82",
        redirct_url: "http://lkwx.gtis.com.cn/estateplat-wechat-page-sh/html/begin.html?type=web"
    }
}

//是否开启人脸服务(true为开启)
function wetherOpenFace() {
    return true;
}

//域名地址
function realmName() {
    return "http://192.169.50.45:8060/estateplat-register";
    //return "http://lkwx.gtis.com.cn/estateplat-register";
    // return "http://127.0.0.1:8080/estateplat-register"
}

//判断属于什么浏览器
function judgeBrowser() {
    if (/MicroMessenger/.test(window.navigator.userAgent)) {
        return "wechat";
    } else if (/AlipayClient/.test(window.navigator.userAgent)) {
        return "alipay";
    } else {
        return "other";
    }
}

//阿里芝麻信用人脸识别通用方法(whetherSkip:true为跳转页面，false不跳,_fn回调函数)
function alipay_user_certify(_fn) {
    var access_token = sessionStorage.access_token;
    if (access_token && access_token != undefined) {
        if (judgeBrowser() == "alipay") {
            var matchResult = window.navigator.userAgent.match(/AliApp\(AP\/([\d\.]+)\)/i);
            var apVersion = (matchResult && matchResult[1]) || ''; // 如: 10.1.58.00000170
            console.log("支付宝版本号：" + apVersion);
            if (parseInt(apVersion.split('.')[0]) >= 10) {
                postDataToServer("/faceIdcardLivedetectModel/alipayUserCertify", {}, function (data) {
                    //业务id
                    var certifyId = data.certifyId;
                    //支付宝内的身份认证地址
                    var url = data.url;
                    console.log(url);
                    if (certifyId && url) {
                        //这是H5的调用方式
                        readyH5(function () {
                            // 需要确保在 AlipayJSBridge ready 之后才调用
                            startAPVerifyH5({
                                certifyId: certifyId,
                                url: url
                            }, function (verifyResult) {
                                // 认证结果回调触发, 以下处理逻辑为示例代码，开发者可根据自身业务特性来自行处理
                                if (verifyResult.resultStatus === '9000') {
                                    // 验证成功，接入方在此处处理后续的业务逻辑.resultStatus=9000 时，业务方需要去支付宝网关接口查询最终状态
                                    postDataToServer("/faceIdcardLivedetectModel/alipayUserCertifyResult", {
                                        certifyId: certifyId,
                                        bh: sessionStorage.getItem("bh")
                                    }, function (data) {
                                        if (data.code == "0000") {
                                            _fn.call(this, "true", "true");
                                        }
                                    });
                                } else if (verifyResult.resultStatus === '6001') {
                                    // 用户主动取消认证
                                    // 可做下 toast 弱提示
                                    $.toast("您取消了认证", "text");
                                    _fn.call(this, "false", "false");
                                } else {
                                    // 其他结果状态码判断和处理 ...
                                    $.toast("业务或网络异常");
                                    _fn.call(this, "false", "false");
                                }
                            });
                        });
                    }

                })
            } else {
                alert("您的支付宝版本过低，请升级支付宝");
            }
        } else {
            _fn.call(this, "false", "false");
        }
    }
}

function readyH5(callback) {
// 如果jsbridge已经注入则直接调用
    if (window.AlipayJSBridge) {
        callback && callback();
    } else {
// 如果没有注入则监听注入的事件
        document.addEventListener('AlipayJSBridgeReady', callback, false);
    }
}
// startBizService 接口仅在支付宝 10.0.15 及以上支持
// 需要接入者自行做下版本兼容处理 ！！
function startAPVerifyH5(options, callback) {
    AlipayJSBridge.call('startBizService', {
        name: 'open-certify',
        param: JSON.stringify(options)
    }, callback);
}

//数据交互
function postDataToServer(_path, _param, _fn, _async, _errFn) {
    var access_token = sessionStorage.getItem("access_token") || "";
    if (!_path) {
        return;
    }
    var _url = getIP() + _path;
    beginAction();
    jQuery.support.cors = true;
    var isAsync = String(_async) == "true" ? false : true;
    $.ajax({
        url: _url,
        type: 'post',
        async: isAsync,
        timeout: 300000,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                access_token: access_token
            },
            "data": _param
        }),
        success: function (res) {
            endAction();
            if (res.head.code == "0000") {
                var accessToken = res.head.access_token;
                if (accessToken != null && accessToken != '') {
                    sessionStorage.setItem("access_token", res.head.access_token);
                }
                _fn.call(this, res.data, res)
            } else if (res.head.code == "6666") {
                var cur_pageElement = sessionStorage.getItem("cur_pageElement");
                sessionStorage.clear();
                sessionStorage.setItem("cur_pageElement",cur_pageElement);
            } else if (res.head.code == "20055") {
                layer.msg(res.head.msg + "：" + res.data.MESSG, {time: 3000});
            } else if (typeof _async == "function" || typeof _errFn == "function") {
                if (typeof (_errFn) == "function") {
                    _errFn.call(this, res);
                } else {
                    _async.call(this, res);
                }
            } else {
                console.log(typeof _async);
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            endAction();
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        }
    });
}

//为后端重定向写的方法
function postDataToServerComplete(_path, _param, _fn, _async, _errFn) {
    var access_token = sessionStorage.getItem("access_token") || "";
    if (!_path) {
        return;
    }
    var _url = getIP() + _path;
    beginAction();
    jQuery.support.cors = true;
    var isAsync = String(_async) == "true" ? false : true;
    $.ajax({
        url: _url,
        type: 'post',
        async: isAsync,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                access_token: access_token
            },
            "data": _param
        }),
        success: function (res) {
            endAction();
            if (res.head.code == "0000") {
                var accessToken = res.head.access_token;
                if (accessToken != null && accessToken != '') {
                    sessionStorage.setItem("access_token", res.head.access_token);
                }
                _fn.call(this, res.data, res)
            } else if (typeof _async == "function" || typeof _errFn == "function") {
                if (typeof (_errFn) == "function") {
                    _errFn.call(this, res);
                } else {
                    _async.call(this, res);
                }
            } else if (res.head.code == "6666") {
                sessionStorage.clear();
            } else if (res.head.code == "20055") {
                layer.msg(res.head.msg + "：" + res.data.MESSG, {time: 3000});
            } else {
                console.log(typeof _async);
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            endAction();
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        },
        //设置ajax请求结束后的执行动作
        complete: function (XMLHttpRequest, textStatus) {
            // 通过XMLHttpRequest取得响应头，REDIRECT
            var redirect = XMLHttpRequest.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向
            if (redirect == "REDIRECT") {
                var win = window;
                while (win != win.top) {
                    win = win.top;
                }
                //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求
                win.location.href = XMLHttpRequest.getResponseHeader("CONTEXTPATH");
            }
        }
    });
}

//数据交互
function postDataToRegisterServer(_path, _param, _fn, _async, _errFn) {
    var access_token = sessionStorage.getItem("access_token") || "";
    if (!_path) {
        return;
    }
    var _url = getRegisterApiIP() + _path;
    beginAction();
    jQuery.support.cors = true;
    var isAsync = String(_async) == "true" ? false : true;
    $.ajax({
        url: _url,
        type: 'post',
        async: isAsync,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                access_token: access_token
            },
            "data": _param
        }),
        success: function (res) {
            endAction();
            if (res.head.code == "0000") {
                var accessToken = res.head.access_token;
                if (accessToken != null && accessToken != '') {
                    sessionStorage.setItem("access_token", res.head.access_token);
                }
                _fn.call(this, res.data, res)
            } else if (res.head.code == "6666") {
                sessionStorage.clear();
            } else if (res.head.code == "20055") {
                layer.msg(res.head.msg + "：" + res.data.MESSG, {time: 3000});
            } else if (typeof _async == "function" || typeof _errFn == "function") {
                if (typeof (_errFn) == "function") {
                    _errFn.call(this, res);
                } else {
                    _async.call(this, res);
                }
            } else {
                console.log(typeof _async);
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            endAction();
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        }
    });
}

//数据交互-无弹层
function postDataToServerWithoutMoadal(_path, _param, _fn, _async, _errFn) {
    var access_token = sessionStorage.getItem("access_token") || "";
    if (!_path) {
        return;
    }
    var _url = getIP() + _path;
    jQuery.support.cors = true;
    var isAsync = String(_async) == "true" ? false : true;
    $.ajax({
        url: _url,
        type: 'post',
        async: isAsync,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                access_token: sessionStorage.getItem("access_token")
            },
            "data": _param
        }),
        success: function (res) {
            if (res.head.code == "0000") {
                var accessToken = res.head.access_token;
                if (accessToken != null && accessToken != '') {
                    sessionStorage.setItem("access_token", res.head.access_token);
                }
                _fn.call(this, res.data, res)
            } else if (res.head.code == "6666") {
                sessionStorage.clear();
            } else if (res.head.code == "20055") {
                layer.msg(res.head.msg + "：" + res.data.MESSG, {time: 3000});
            } else if (typeof _async == "function" || typeof _errFn == "function") {
                if (typeof (_errFn) == "function") {
                    _errFn.call(this, res);
                } else {
                    _async.call(this, res);
                }
            } else {
                console.log(typeof _async);
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        }
    });
}

//固定区域内加载，要求其节点存在定位类型及固定宽高
function postDataToServerInLocalArea(_path, _param, paramObj) {
    var access_token = sessionStorage.getItem("access_token") || "";
    if (!_path) {
        return;
    }
    var _url = getIP() + _path;
    paramObj.domeSelector && beginAreaAction(paramObj.domeSelector);
    jQuery.support.cors = true;
    var isAsync = String(paramObj.type) == "true" ? false : true;
    $.ajax({
        url: _url,
        type: 'post',
        async: isAsync,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                token: access_token,
                access_token: sessionStorage.getItem("access_token")
            },
            "data": _param
        }),
        success: function (res) {
            return;
            paramObj.domeSelector && endAreaAction(paramObj.domeSelector);
            if (res.head.code == "0000") {
                var accessToken = res.head.access_token;
                if (accessToken != null && accessToken != '') {
                    sessionStorage.setItem("access_token", res.head.access_token);
                }
                paramObj.success.call(this, res.data, res)
            } else if (res.head.code == "6666") {
                sessionStorage.clear();
            } else if (res.head.code == "20055") {
                layer.msg(res.head.msg + "：" + res.data.MESSG, {time: 3000});
            } else if (typeof paramObj.wrong == "function") {
                paramObj.wrong.call(this, res);
            } else {
                console.log(typeof _async);
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            endAction();
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        }
    });
}

//等待背景层
function beginAction() {
    var modalHtml = '<div id="waitModalLayer" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 9999999999;background-color: #000;opacity: 0.3;filter:Alpha(opacity=30);"><span style="position: absolute;color: #fff;font-size: 61px;top: 0;left: 0;right: 0;bottom: 0;margin: auto;width: 100px;height: 100px;"><i style="font-size:100px;" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span></div>';
    $('body').append(modalHtml);
}

//移除等待背景层
function endAction() {
    $("#waitModalLayer").remove();
}

//局部地区加载项
function beginAreaAction(domeSelector) {
    if ($(domeSelector).children(".areaAction").length == 0) {
        var modalHtml = '<div class="areaAction" style="position: absolute;top: 0;bottom: 0;left: 0;right: 0;z-index: 9999999999;background-color: #000;opacity: 0.3;filter:Alpha(opacity=30);"><span style="position: absolute;color: #fff;font-size: 61px;top: 0;left: 0;right: 0;bottom: 0;margin: auto;width: 100px;height: 100px;"><i style="font-size:100px;" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span></div>';
        $(domeSelector).append(modalHtml);
    }
}

//移除局部地区加载项
function endAreaAction(domeSelector) {
    $(domeSelector).children(".areaAction").remove();
}

//获取受理编号
function getSlbh() {
    var slbh;
    if (sessionStorage.getItem("slbh")) {
        slbh = sessionStorage.getItem("slbh");
    } else {
        //获取受理编号
        postDataToServer('/applyModel/getApplySlbh', {}, function (obj) {
            slbh = obj.slbh;
            sessionStorage.setItem("slbh", obj.slbh);
        }, true);
    }
    return slbh;
}

//转换-将图片文件转为base64传回。
function con_conertImageToBase64(files, _fn) {
    if (window.FileReader) {
        var _fileCount = files.length;
        var _curReadIndex = 0;
        var hasIllegalFile = [];
        var filesBaseStr = [];
        var reader = new FileReader();
        var lastFileCheck = function () {
            _curReadIndex++;
            if (_curReadIndex === _fileCount) {
                endAction();
                _fn.call(this, filesBaseStr, hasIllegalFile);
            } else {
                readIt(files[_curReadIndex]);
            }
        };
        var readIt = function (_file) {
            if (_file.type == "") {
                _file.type = _file.name.substring(_file.name.lastIndexOf(".") + 1, _file.name.length);
            }
            if (!((_file.type.split("/")[0] == "image" || _file.type.split("/")[1] == "pdf") && (_file.type != "image/gif"))) {
                hasIllegalFile.push({name: _file.name, desc: "非图片类型文件"});
                lastFileCheck()
            } else if (_file.size > 1024 * 1024 * 10) {
                hasIllegalFile.push({name: _file.name, desc: "文件大小超过10M"});
                lastFileCheck()
            } else {
                reader.readAsDataURL(_file);
            }
        };
        reader.onload = function (e) {
            var loginMsg = JSON.parse(sessionStorage.getItem("loginMsg")) || {};
            var base64Str = e.target.result.replace("data:image/png;base64,", "").replace("data:image/gif;base64,", "").replace("data:image/jpeg;base64,", "").replace("data:application/pdf;base64,", "");
            var attach = {
                "createUser": loginMsg.userName || {},
                "fileName": files[_curReadIndex].name,
                "base64Str": base64Str,
                "imgUrl": e.target.result,
                "fileType": files[_curReadIndex].type.split("/")[1]
            };
            filesBaseStr.push(attach);
            lastFileCheck();
        };
        beginAction();
        readIt(files[_curReadIndex]);
    } else {
        layer.msg("本浏览器不支持该功能，请升级或使用其他浏览器");
    }
}

//转换-将图片文件转错误提示
function con_conertImageToBase64WrongMessage(wrongMessageArr) {
    layui.use(["layer"], function () {
        var layer = layui.layer;
        layer.open({
            title: "错误附件,已被删除！",
            area: ["600px"],
            content: wrongMessageArr.map(function (item) {
                return '<div class="no-wrap pointer" title="' + item.name + '">' + item.desc + "：" + item.name + '</div>'
            }).join(""),
            btn: ["确认"],
            btnAlign: "c",
            yes: function (index) {
                layer.close(index);
            }
        })
    })
}

//附件查看--单个
function con_showImageExample(_imageUrl, _imageName, _imageType) {
    if (_imageType == "pdf") {
        window.top.pageToUrl("pdfViewer?file=" + encodeURIComponent(_imageUrl), true);
    } else {
        window.top.con_showPictureSingleWidget(_imageUrl, _imageName)
    }
}

//通用跳页
function pageToUrl(url, way) {
    if (way) {
        window.open(getRegisterIp() + "/" + url, way);
    } else {
        location.href = getRegisterIp() + "/" + url;
    }
}

//去个人中心
function toUserIndex() {
    pageToUrl('new_user_index');
}

function pageToUrlNew(url, way) {
    if (way) {
        window.open('./' + url, way);
    } else {
        location.href = './' + url;
    }
}

//展示图片组--图片控件 ---- 新
function con_showPictureWidget(imagesGroup, _imageType, _imageIndex) {
    if (Array.isArray(imagesGroup) && _imageType && _imageIndex > -1) {
        //加载layui控件
        layui.use(['laytpl'], function () {
            var laytpl = layui.laytpl;
            var imageData = {
                imagesGroup: imagesGroup,
                imageType: "",
                curImages: {
                    images: [],
                    index: _imageIndex ? _imageIndex : 0
                }
            };

            //固有元素注册
            var $pictureWidget, $curDisplayImages, $curDisplayImageParent, $curDisplayImage;

            //初始化宽度
            var setInitImageWidth = function () {
                if ($curDisplayImage.el.width()) {
                    if ($curDisplayImage.el.width() / $curDisplayImage.el.height() > $curDisplayImageParent.width() / $curDisplayImageParent.height()) {
                        $curDisplayImage.width = $curDisplayImage.el.width();
                        if ($curDisplayImageParent.width() * 0.8 < $curDisplayImage.el.width()) {
                            $curDisplayImage.el.css({"width": $curDisplayImageParent.width() * 0.8});
                            $curDisplayImage.width = $curDisplayImageParent.width() * 0.8;
                            $curDisplayImage.scaleRate = 1;
                        }
                    } else {
                        $curDisplayImage.width = $curDisplayImage.el.width();
                        if ($curDisplayImageParent.height() * 0.8 < $curDisplayImage.el.height()) {
                            var _width = $curDisplayImage.el.width() * $curDisplayImageParent.height() * 0.8 / $curDisplayImage.el.height();
                            $curDisplayImage.el.css({"width": _width});
                            $curDisplayImage.width = _width;
                            $curDisplayImage.scaleRate = 1;
                        }
                    }
                } else {
                    setTimeout(setInitImageWidth, 100);
                }
            };

            //展示选中的图片
            var showThisPicture = function (_rotate) {
                //图片名称
                var imgTitle = $(".img-title");
                imgTitle.text(imageData.curImages[imageData.curImages.index].fileName);
                imgTitle.attr("title", imageData.curImages[imageData.curImages.index].fileName);
                var _url = imageData.curImages[imageData.curImages.index].imgurl;
                if (imageData.curImages[imageData.curImages.index].fileName.split(".")[1] == "pdf") {
                    $curDisplayImageParent.html('<p id="pictureFullBrowserCurr" class="picture-full-browser-curr">该pdf文件在此处无法正常显示，<span class="widgget-show-pdf" data-src="' + _url + '">点击查看</span></p>');
                } else {
                    $curDisplayImageParent.html('<img id="pictureFullBrowserCurr" class="picture-full-browser-curr" src="' + _url + '" alt=""/>');
                }
                //图片列表之选中框
                $curDisplayImages.children().removeClass("small-view-selected");
                $($curDisplayImages.children()[imageData.curImages.index]).addClass("small-view-selected");
                //滚动条变动
                if (($curDisplayImages.width()) < ($curDisplayImages.children().eq(imageData.curImages.index).offset().left + 200)) {
                    $curDisplayImages.scrollLeft($curDisplayImages.scrollLeft() + 120);
                } else if ($curDisplayImages.children().eq(imageData.curImages.index).offset().left < 120) {
                    $curDisplayImages.scrollLeft($curDisplayImages.scrollLeft() - 120);
                }
                //初始化图片
                $curDisplayImage = {
                    el: $("#pictureFullBrowserCurr"),
                    rotate: _rotate || 0,
                    width: _rotate || 0,
                    scaleRate: 1
                };
                //初始以maxlength 80%显示
                setInitImageWidth();
            };

            //获取需要展示的图片组
            var showThisImageArr = function (_type, _index) {
                //加载图片组
                for (var i = 0, j = imageData.imagesGroup.length; i < j; i++) {
                    var item = imageData.imagesGroup[i];
                    if (item.fjxm.fjlx == _type) {
                        imageData.curImages = item.fjList;
                        $("#imgCatalog").text(item.fjxm.fjlx);
                        $(".catalog-items li").removeClass("item-active");
                        $(".catalog-items li").eq(i).addClass("item-active");
                        if (i > 0) {
                            $("#prevImageType").text(imageData.imagesGroup[i - 1].fjxm.fjlx);
                        } else {
                            $("#prevImageType").text("无");
                        }
                        if (i < (j - 1)) {
                            $("#nextImageType").text(imageData.imagesGroup[i + 1].fjxm.fjlx);
                        } else {
                            $("#nextImageType").text("无");
                        }
                    }
                }
                laytpl('{{# layui.each(d,function(index,item){ }}{{# if(item.fileName.split(".")[1]=="pdf"){ }}<div class="small-view" data-index="{{index}}">pdf文件</div>{{# }else{ }}<div class="small-view" data-index="{{index}}"><img src="{{item.imgurl}}" alt=""/></div>{{# } }}{{# }) }}').render(imageData.curImages, function (_html) {
                    $curDisplayImages.html(_html);
                });
                imageData.imageType = _type;
                //加载大图
                if (_index > -1) {
                    imageData.curImages.index = _index;
                } else {
                    imageData.curImages.index = 0;
                }
                showThisPicture();
            };

            //图片缩放
            var scaleImage = function (_scaleDirection) {
                var _width;
                if (_scaleDirection) {
                    if ($curDisplayImage.scaleRate > 10) {
                        return;
                    } else {
                        _width = $curDisplayImage.width * 1.25;
                        $curDisplayImage.width = _width;
                        $curDisplayImage.scaleRate += 1;
                        $curDisplayImage.el.width(_width);
                        $curDisplayImage.el.css({"left": "0px", "top": "0px"});
                    }
                } else {
                    if ($curDisplayImage.scaleRate < -10) {
                        return;
                    } else {
                        _width = $curDisplayImage.width * 0.8;
                        $curDisplayImage.width = _width;
                        $curDisplayImage.scaleRate -= 1;
                        $curDisplayImage.el.width(_width);
                        $curDisplayImage.el.css({"left": "0px", "top": "0px"});
                    }
                }
            };

            //页面初始化
            var initPageElement = function () {
                //加载控件背景
                $("body").append('<div class="con-picture-full-browser" id="conPictureFullBrowser"><div class="img-catalog">                附件目录：<span id="imgCatalog"></span><ul class="catalog-items"><li>未获取到目录</li></ul></div><span class="img-title"></span>	<div class="img-view" id="conImgViewContainer"></div>	<div class="close-part" title="关闭"><i class="fa fa-times"></i></div>	<div class="left-part" title="上一张"><i class="fa fa-chevron-circle-left"></i></div>	<div class="right-part" title="下一张"><i class="fa fa-chevron-circle-right"></i></div>	<div class="operate-part">	<span class="prev-image-type">	<i class="fa fa-angle-double-left"></i>上一图集：	<span id="prevImageType">无</span>	</span>	<i class="fa fa-search-plus" title="放大"></i>                	<i class="fa fa-search-minus" title="缩小"></i>	<i class="fa fa-rotate-left" title="向左旋转90°"></i>	<i class="fa fa-rotate-right" title="向右旋转90°"></i><span class="next-image-type">下一图集：	<span id="nextImageType">无</span>	<i class="fa fa-angle-double-right"></i>	</span>                	</div>	<div class="preview-part" id="conPreviewPart"></div>	</div>');
                //附件目录
                var catalogArr = [];
                for (var i = 0, j = imageData.imagesGroup.length; i < j; i++) {
                    catalogArr.push(imageData.imagesGroup[i].fjxm.fjlx);
                }
                $(".catalog-items").html('<li>' + catalogArr.join('</li><li>') + '</li>');

                //jquery页面对象
                //控件
                $pictureWidget = $("#conPictureFullBrowser");
                //事件注册
                $pictureWidget.on("click", ".close-part", function () {
                    $("#conPictureFullBrowser").remove();
                }).on("click", ".left-part", function () {
                    //上一页
                    if (imageData.curImages.index > 0) {
                        imageData.curImages.index--;
                        showThisPicture();
                    } else {
                        layer.msg("已经是第一张了");
                    }
                }).on("click", ".right-part", function () {
                    //下一页
                    if (imageData.curImages.index < (imageData.curImages.length - 1)) {
                        imageData.curImages.index++;
                        showThisPicture();
                    } else {
                        layer.msg("已经是最后一张了");
                    }
                }).on("click", ".prev-image-type", function () {
                    //上一图集
                    var _txt = $(this).children("span").text();
                    if (!_txt || (_txt == "无")) {
                        return;
                    }
                    showThisImageArr(_txt);
                }).on("click", ".next-image-type", function () {
                    //下一图集
                    var _txt = $(this).children("span").text();
                    if (!_txt || (_txt == "无")) {
                        return;
                    }
                    showThisImageArr(_txt);
                }).on("click", ".operate-part .fa-search-plus", function () {
                    //放大
                    scaleImage(true);
                }).on("click", ".operate-part .fa-search-minus", function () {
                    //缩小
                    scaleImage(false);
                }).on("click", ".operate-part .fa-rotate-left", function () {
                    //左旋转
                    showThisPicture($curDisplayImage.rotate);
                    $curDisplayImage.rotate -= 90;
                    $curDisplayImage.el.css({"transform": "rotate(" + $curDisplayImage.rotate + "deg)"});
                }).on("click", ".operate-part .fa-rotate-right", function () {
                    //右旋转
                    showThisPicture($curDisplayImage.rotate);
                    $curDisplayImage.rotate += 90;
                    $curDisplayImage.el.css({"transform": "rotate(" + $curDisplayImage.rotate + "deg)"});
                });
                //当前图集
                $curDisplayImages = $("#conPreviewPart");
                //注册图片集点击事件
                $curDisplayImages.on("click", ".small-view", function () {
                    var self = $(this);
                    if (self.index() != imageData.curImages.index) {
                        imageData.curImages.index = self.index();
                        showThisPicture();
                    }
                });
                //展示图片父元素
                $curDisplayImageParent = $("#conImgViewContainer");

                //正在展示的图片 事件注册
                var mouseIsPressed = false, mousePageX, mousePageY;
                $curDisplayImageParent.on("mousewheel", "img", function (event) {
                    //滚轮缩放
                    if (event.originalEvent.wheelDelta > 0) {
                        scaleImage(true);
                    } else {
                        scaleImage(false);
                    }
                    event.stopPropagation();
                    event.preventDefault();
                }).on("mousedown", "img", function (jqData) {
                    mouseIsPressed = true;
                    mousePageX = jqData.pageX;
                    mousePageY = jqData.pageY;
                }).on("dragstart", function (jqData) {
                    return false;
                }).on("mouseup", function () {
                    mouseIsPressed = false;
                }).on("mouseleave", "img", function () {
                    mouseIsPressed = false;
                }).on("mousemove", "img", function (jqdata) {
                    if (mouseIsPressed) {
                        var mouseX = jqdata.pageX - mousePageX;
                        var mouseY = jqdata.pageY - mousePageY;
                        $curDisplayImage.el.css({"left": parseFloat($curDisplayImage.el.css("left")) + mouseX + "px"});
                        $curDisplayImage.el.css({"top": parseFloat($curDisplayImage.el.css("top")) + mouseY + "px"});
                        mousePageX = jqdata.pageX;
                        mousePageY = jqdata.pageY;
                    }
                }).on("click", ".widgget-show-pdf", function () {
                    //pdf文件查看
                    var _src = $(this).attr("data-src");
                    if (_src.indexOf("http") > -1) {
                        pageToUrl("pdfViewer?file=" + encodeURIComponent(_src), true);
                    } else {
                        var PDFData = _src.replace("data:application/pdf;base64,", "");
                        sessionStorage.removeItem("_imgUrl");
                        sessionStorage.setItem("_imgUrl", PDFData);
                        pageToUrl("pdfViewer", true);
                    }
                });
                //目录点击事件注册
                $pictureWidget.on("click", ".catalog-items li", function () {
                    showThisImageArr($(this).text(), 0);
                });
                showThisImageArr(_imageType, _imageIndex);
            };
            initPageElement();
        });
    } else {
        layer.msg('参数错误，附件查看失败');
    }
}

//展示图片组--图片控件 ---- 新-----无分组
function con_showPictureWidgetNoGroup(imagesGroup, imageIndex) {
    if (Array.isArray(imagesGroup) && imageIndex > -1) {
        //兼容IE，图片地址编码
        imagesGroup.forEach(function(item){
            item.imgurl = encodeURI(item.imgurl);
        });
        //加载layui控件
        layui.use(['laytpl'], function () {
            var laytpl = layui.laytpl;
            imageIndex = imageIndex ? imageIndex : 0

            //固有元素注册
            var $pictureWidget, $curDisplayImages, $curDisplayImageParent, $curDisplayImage;

            //初始化宽度
            var setInitImageWidth = function () {
                if ($curDisplayImage.el.width()) {
                    if ($curDisplayImage.el.width() / $curDisplayImage.el.height() > $curDisplayImageParent.width() / $curDisplayImageParent.height()) {
                        $curDisplayImage.width = $curDisplayImage.el.width();
                        if ($curDisplayImageParent.width() * 0.8 < $curDisplayImage.el.width()) {
                            $curDisplayImage.el.css({"width": $curDisplayImageParent.width() * 0.8});
                            $curDisplayImage.width = $curDisplayImageParent.width() * 0.8;
                            $curDisplayImage.scaleRate = 1;
                        }
                    } else {
                        $curDisplayImage.width = $curDisplayImage.el.width();
                        if ($curDisplayImageParent.height() * 0.8 < $curDisplayImage.el.height()) {
                            var _width = $curDisplayImage.el.width() * $curDisplayImageParent.height() * 0.8 / $curDisplayImage.el.height();
                            $curDisplayImage.el.css({"width": _width});
                            $curDisplayImage.width = _width;
                            $curDisplayImage.scaleRate = 1;
                        }
                    }
                } else {
                    setTimeout(setInitImageWidth, 100);
                }
            };

            //展示选中的图片
            var showThisPicture = function (_rotate) {
                //图片名称
                var imgTitle = $(".img-title");
                imgTitle.text(imagesGroup[imageIndex].fileName);
                imgTitle.attr("title", imagesGroup[imageIndex].fileName);
                var _url = imagesGroup[imageIndex].imgurl;
                if (imagesGroup[imageIndex].fileName.substring(imagesGroup[imageIndex].fileName.lastIndexOf(".") + 1, imagesGroup[imageIndex].fileName.length) == "pdf") {
                    $curDisplayImageParent.html('<p id="pictureFullBrowserCurr" class="picture-full-browser-curr">该pdf文件在此处无法正常显示，<span class="widgget-show-pdf" data-src="' + _url + '">点击查看</span></p>');
                } else {
                    $curDisplayImageParent.html('<img id="pictureFullBrowserCurr" class="picture-full-browser-curr" src="' + _url + '" alt=""/>');
                }
                //图片列表之选中框
                $curDisplayImages.children().removeClass("small-view-selected");
                $($curDisplayImages.children()[imageIndex]).addClass("small-view-selected");
                //滚动条变动
                if (($curDisplayImages.width()) < ($curDisplayImages.children().eq(imageIndex).offset().left + 200)) {
                    $curDisplayImages.scrollLeft($curDisplayImages.scrollLeft() + 120);
                } else if ($curDisplayImages.children().eq(imageIndex).offset().left < 120) {
                    $curDisplayImages.scrollLeft($curDisplayImages.scrollLeft() - 120);
                }
                //初始化图片
                $curDisplayImage = {
                    el: $("#pictureFullBrowserCurr"),
                    rotate: _rotate || 0,
                    width: _rotate || 0,
                    scaleRate: 1
                };
                //初始以maxlength 80%显示
                setInitImageWidth();
            };

            //图片缩放
            var scaleImage = function (_scaleDirection) {
                var _width;
                if (_scaleDirection) {
                    if ($curDisplayImage.scaleRate > 10) {
                        return;
                    } else {
                        _width = $curDisplayImage.width * 1.25;
                        $curDisplayImage.width = _width;
                        $curDisplayImage.scaleRate += 1;
                        $curDisplayImage.el.width(_width);
                        $curDisplayImage.el.css({"left": "0px", "top": "0px"});
                    }
                } else {
                    if ($curDisplayImage.scaleRate < -10) {
                        return;
                    } else {
                        _width = $curDisplayImage.width * 0.8;
                        $curDisplayImage.width = _width;
                        $curDisplayImage.scaleRate -= 1;
                        $curDisplayImage.el.width(_width);
                        $curDisplayImage.el.css({"left": "0px", "top": "0px"});
                    }
                }
            };

            //页面初始化
            var initPageElement = function () {
                //加载控件背景
                $("body").append('<div class="con-picture-full-browser" id="conPictureFullBrowser"><span class="img-title"></span>	<div class="img-view" id="conImgViewContainer"></div>	<div class="close-part" title="关闭"><i class="fa fa-times"></i></div>	<div class="left-part" title="上一张"><i class="fa fa-chevron-circle-left"></i></div>	<div class="right-part" title="下一张"><i class="fa fa-chevron-circle-right"></i></div>	<div class="operate-part">	<i class="fa fa-search-plus" title="放大"></i>                	<i class="fa fa-search-minus" title="缩小"></i>	<i class="fa fa-rotate-left" title="向左旋转90°"></i>	<i class="fa fa-rotate-right" title="向右旋转90°"></i>                	</div>	<div class="preview-part" id="conPreviewPart"></div>	</div>');

                //jquery页面对象
                //控件
                $pictureWidget = $("#conPictureFullBrowser");
                //事件注册
                $pictureWidget.on("click", ".close-part", function () {
                    $("#conPictureFullBrowser").remove();
                }).on("click", ".left-part", function () {
                    //上一页
                    if (imageIndex > 0) {
                        imageIndex--;
                        showThisPicture();
                    }
                }).on("click", ".right-part", function () {
                    //下一页
                    if (imageIndex < (imagesGroup.length - 1)) {
                        imageIndex++;
                        showThisPicture();
                    }
                }).on("click", ".operate-part .fa-search-plus", function () {
                    //放大
                    scaleImage(true);
                }).on("click", ".operate-part .fa-search-minus", function () {
                    //缩小
                    scaleImage(false);
                }).on("click", ".operate-part .fa-rotate-left", function () {
                    //左旋转
                    showThisPicture($curDisplayImage.rotate);
                    $curDisplayImage.rotate -= 90;
                    $curDisplayImage.el.css({"transform": "rotate(" + $curDisplayImage.rotate + "deg)"});
                }).on("click", ".operate-part .fa-rotate-right", function () {
                    //右旋转
                    showThisPicture($curDisplayImage.rotate);
                    $curDisplayImage.rotate += 90;
                    $curDisplayImage.el.css({"transform": "rotate(" + $curDisplayImage.rotate + "deg)"});
                });
                //当前图集
                $curDisplayImages = $("#conPreviewPart");
                //注册图片集点击事件
                $curDisplayImages.on("click", ".small-view", function () {
                    var self = $(this);
                    if (self.index() != imagesGroup.length) {
                        imageIndex = self.index();
                        showThisPicture();
                    }
                });
                //展示图片父元素
                $curDisplayImageParent = $("#conImgViewContainer");

                //正在展示的图片 事件注册
                var mouseIsPressed = false, mousePageX, mousePageY;
                $curDisplayImageParent.on("mousewheel", "img", function (event) {
                    //滚轮缩放
                    if (event.originalEvent.wheelDelta > 0) {
                        scaleImage(true);
                    } else {
                        scaleImage(false);
                    }
                    event.stopPropagation();
                    event.preventDefault();
                }).on("mousedown", "img", function (jqData) {
                    mouseIsPressed = true;
                    mousePageX = jqData.pageX;
                    mousePageY = jqData.pageY;
                }).on("dragstart", function (jqData) {
                    return false;
                }).on("mouseup", function () {
                    mouseIsPressed = false;
                }).on("mouseleave", "img", function () {
                    mouseIsPressed = false;
                }).on("mousemove", "img", function (jqdata) {
                    if (mouseIsPressed) {
                        var mouseX = jqdata.pageX - mousePageX;
                        var mouseY = jqdata.pageY - mousePageY;
                        $curDisplayImage.el.css({"left": parseFloat($curDisplayImage.el.css("left")) + mouseX + "px"});
                        $curDisplayImage.el.css({"top": parseFloat($curDisplayImage.el.css("top")) + mouseY + "px"});
                        mousePageX = jqdata.pageX;
                        mousePageY = jqdata.pageY;
                    }
                }).on("click", ".widgget-show-pdf", function () {
                    //pdf文件查看
                    var _src = $(this).attr("data-src");
                    if (_src.indexOf("http") > -1) {
                        pageToUrl("pdfViewer?file=" + encodeURIComponent(_src), true);
                    } else {
                        var PDFData = _src.replace("data:application/pdf;base64,", "");
                        sessionStorage.removeItem("_imgUrl");
                        sessionStorage.setItem("_imgUrl", PDFData);
                        pageToUrl("pdfViewer", true);
                    }
                });
                //加载缩略列表
                laytpl('{{# layui.each(d,function(index,item){ }}{{# if(item.fileName.split(".")[1]=="pdf"){ }}<div class="small-view" data-index="{{index}}">pdf文件</div>{{# }else{ }}<div class="small-view" data-index="{{index}}"><img src="{{item.imgurl}}" alt=""/></div>{{# } }}{{# }) }}').render(imagesGroup, function (_html) {
                    $curDisplayImages.html(_html);
                });
                //加载大图
                showThisPicture();
            };
            initPageElement();
        });
    } else {
        layer.msg('参数错误，附件查看失败');
    }
}

//展示单个图片--图片控件
function con_showPictureSingleWidget(_imgUrl, _imgName, _imgType) {
    if (_imgUrl) {
        _imgUrl = encodeURI(_imgUrl);
        if (_imgType == "pdf") {
            window.top.pageToUrl("pdfViewer?file=" + encodeURIComponent(_imageUrl), true);
        } else {
            //固有元素注册
            var $pictureWidget, $curDisplayImages, $curDisplayImageParent, $curDisplayImage;
            //展示选中的图片
            var showThisPicture = function (_rotate) {
                //图片名称
                var imgTitle = $(".img-title");
                imgTitle.text(_imgName);
                imgTitle.attr("title", _imgName);
                if (_imgName && _imgName.split(".")[1] == "pdf") {
                    if (_imgUrl.indexOf("http") > -1) {
                        pageToUrl("pdfViewer?file=" + encodeURIComponent(_imgUrl), true);
                    } else {
                        var PDFData = _imgUrl.replace("data:application/pdf;base64,", "");
                        sessionStorage.removeItem("_imgUrl");
                        sessionStorage.setItem("_imgUrl", PDFData);
                        pageToUrl("pdfViewer", true);
                    }
                } else {
                    $curDisplayImageParent.html('<img id="pictureFullBrowserCurr" class="picture-full-browser-curr" src="' + _imgUrl + '" alt=""/>');
                }
                //初始化图片
                $curDisplayImage = {
                    el: $("#pictureFullBrowserCurr"),
                    rotate: _rotate || 0,
                    width: 0,
                    scaleRate: 1
                };
                //初始以maxlength 80%显示
                var setInitImageWidth = function () {
                    if ($curDisplayImage.el.width()) {
                        if ($curDisplayImage.el.width() / $curDisplayImage.el.height() > $curDisplayImageParent.width() / $curDisplayImageParent.height()) {
                            $curDisplayImage.width = $curDisplayImage.el.width();
                            if ($curDisplayImageParent.width() * 0.8 < $curDisplayImage.el.width()) {
                                $curDisplayImage.el.css({"width": $curDisplayImageParent.width() * 0.8});
                                $curDisplayImage.width = $curDisplayImageParent.width() * 0.8;
                            }
                        } else {
                            $curDisplayImage.width = $curDisplayImage.el.width();
                            if ($curDisplayImageParent.height() * 0.8 < $curDisplayImage.el.height()) {
                                var _width = $curDisplayImage.el.width() * $curDisplayImageParent.height() * 0.8 / $curDisplayImage.el.height();
                                $curDisplayImage.el.css({"width": _width});
                                $curDisplayImage.width = _width;
                            }
                        }
                    } else {
                        setTimeout(setInitImageWidth, 100);
                    }
                };
                setInitImageWidth();
            };

            //图片缩放
            var scaleImage = function (_scaleDirection) {
                var _width;
                if (_scaleDirection) {
                    if ($curDisplayImage.scaleRate > 10) {
                        return;
                    } else {
                        _width = $curDisplayImage.width * 1.25;
                        $curDisplayImage.width = _width;
                        $curDisplayImage.scaleRate += 1;
                        $curDisplayImage.el.width(_width);
                        $curDisplayImage.el.css({"left": "0px", "top": "0px"});
                    }
                } else {
                    if ($curDisplayImage.scaleRate < -10) {
                        return;
                    } else {
                        _width = $curDisplayImage.width * 0.8;
                        $curDisplayImage.width = _width;
                        $curDisplayImage.scaleRate -= 1;
                        $curDisplayImage.el.width(_width);
                        $curDisplayImage.el.css({"left": "0px", "top": "0px"});
                    }
                }
            };

            //页面初始化
            var initPageElement = function () {
                //加载控件背景
                $("body").append('<div class="con-picture-full-browser" id="conPictureFullBrowser"><span class="img-title"></span>	<div class="img-view img-view-single" id="conImgViewContainer"></div>	<div class="close-part" title="关闭"><i class="fa fa-times"></i></div><div class="operate-part operate-part-single"><i class="fa fa-search-plus" title="放大"></i><i class="fa fa-search-minus" title="缩小"></i><i class="fa fa-rotate-left" title="向左旋转90°"></i>	<i class="fa fa-rotate-right" title="向右旋转90°"></i></div></div>');
                //jquery页面对象
                //控件
                $pictureWidget = $("#conPictureFullBrowser");
                //事件注册
                $pictureWidget.on("click", ".close-part", function () {
                    $("#conPictureFullBrowser").remove();
                }).on("click", ".operate-part .fa-search-plus", function () {
                    //放大
                    scaleImage(true);
                }).on("click", ".operate-part .fa-search-minus", function () {
                    //缩小
                    scaleImage(false);
                }).on("click", ".operate-part .fa-rotate-left", function () {
                    //左旋转
                    showThisPicture($curDisplayImage.rotate);
                    $curDisplayImage.rotate -= 90;
                    $curDisplayImage.el.css({"transform": "rotate(" + $curDisplayImage.rotate + "deg)"});
                }).on("click", ".operate-part .fa-rotate-right", function () {
                    //右旋转
                    showThisPicture($curDisplayImage.rotate);
                    $curDisplayImage.rotate += 90;
                    $curDisplayImage.el.css({"transform": "rotate(" + $curDisplayImage.rotate + "deg)"});
                });
                //展示图片父元素
                $curDisplayImageParent = $("#conImgViewContainer");
                //正在展示的图片 事件注册
                var mouseIsPressed = false, mousePageX, mousePageY;
                $curDisplayImageParent.on("mousewheel", "img", function (event) {
                    //滚轮缩放
                    if (event.originalEvent.wheelDelta > 0) {
                        scaleImage(true);
                    } else {
                        scaleImage(false);
                    }
                    event.stopPropagation();
                    event.preventDefault();
                }).on("mousedown", "img", function (jqData) {
                    mouseIsPressed = true;
                    mousePageX = jqData.pageX;
                    mousePageY = jqData.pageY;
                }).on("dragstart", function (jqData) {
                    return false;
                }).on("mouseup", function () {
                    mouseIsPressed = false;
                }).on("mouseleave", "img", function () {
                    mouseIsPressed = false;
                }).on("mousemove", "img", function (jqdata) {
                    if (mouseIsPressed) {
                        var mouseX = jqdata.pageX - mousePageX;
                        var mouseY = jqdata.pageY - mousePageY;
                        $curDisplayImage.el.css({"left": parseFloat($curDisplayImage.el.css("left")) + mouseX + "px"});
                        $curDisplayImage.el.css({"top": parseFloat($curDisplayImage.el.css("top")) + mouseY + "px"});
                        mousePageX = jqdata.pageX;
                        mousePageY = jqdata.pageY;
                    }
                });
                showThisPicture();
            };
            initPageElement();
        }
    } else {
        layer.msg('参数错误，附件查看失败');
    }
}

//判断文件类型是否为图片--图片控件
function con_isImage(fileType) {
    var imgTypes = ['png', 'jpg', 'jpeg', 'bmp', 'gif', 'webp', 'psd', 'svg', 'tiff', 'jfif', 'pdf'];
    var pointImgTypes = ['.png', '.jpg', '.jpeg', '.bmp', '.gif', '.webp', '.psd', '.svg', '.tiff', '.jfif', '.pdf'];
    return fileType ? (imgTypes.concat(pointImgTypes)).indexOf(fileType.toLowerCase()) != -1 : false;
}

//展示单个图片--图片控件
function con_showPDFSingleWidget(_imgUrl) {
    pageToUrl("pdfViewer?file=" + encodeURIComponent(_imgUrl), true);
}

//获取登记原因及配置数据
function con_getDjyyDictionaryAndConfig(_sqlx, _fn) {
    postDataToServer("/zdModel/getDjyy", {sqlx: _sqlx}, function (res) {
        var djyyConfig = res.map(function (item) {
            return {dm: item.djyydm, mc: item.mc}
        });
        djyyConfig.unshift({dm: "", mc: ""});
        _fn(djyyConfig, conPageConfig.estateplat_register.IsDjyyNecessary == "true");
    });
}

//获取URL参数
function con_getPageUrlParam() {
    var _search = location.search;
    var paramObj = {};
    if (typeof _search == "string") {
        var param = _search.split("?")[1];
        param && param.split("&").forEach(function (item) {
            if (item) {
                var arr = item.split("=");
                paramObj[arr[0]] = arr[1];
            }
        });
        return paramObj;
    } else {
        return paramObj;
    }
}

//评价
function con_evaluateWidget(fn, obj) {
    //初始值obj:{title,stars,content}
    if (fn) {
        layui.use(["rate", 'layer'], function () {
            var rate = layui.rate;
            var layer = layui.layer;
            var stars = (obj && obj.stars) || 5;
            var content = (obj && obj.content) || "";
            var display = (obj && obj.display) || "";
            layer.open({
                type: 1,
                title: obj.title || "评价",
                area: ["650px", "340px"],
                btn: display ? ["确定"] : ["确认评价", "取消"],
                btnAlign: "c",
                content: '<div class="comment-panel"><form class="layui-form" action=""><div class="layui-form-item"><label class="layui-form-label width-100">请评分</label><div class="layui-input-block"><div id="commentStars"></div><div class="stars-introduce">说明：五星为非常满意，一星为非常不满意。</div></div></div><div class="layui-form-item layui-form-text"><label class="layui-form-label width-100">请留言</label><div class="layui-input-block width-450"><textarea placeholder="请留下您的宝贵建议" class="layui-textarea" id="commentContent"></textarea></div></div></form></div>',
                yes: function (index) {
                    var _callObj = {
                        stars: stars,
                        content: $("#commentContent").val()
                    }
                    layer.close(index);
                    fn.call(this, _callObj);
                }, success: function () {
                    $("#commentContent").val(content);
                    if (display) {
                        $("#commentContent").attr("readonly", "readonly");
                        if (!content) {
                            $("#commentContent").attr("placeholder", "");
                        }
                    }
                    rate.render({
                        elem: "#commentStars",
                        value: stars,
                        choose: function (value) {
                            stars = value;
                        },
                        readonly: display ? true : false
                    });
                }
            });
        });
    }
}

//通用数据表格绑定--后台数据分页
function con_dataTableRender(_domId, _colsPaeam, _data, _pageId, _pageCount, _curPage, _curPageLimit, _fn) {
    var self = this;
    layui.use(['table', 'laypage', 'jquery'], function () {
        var table = layui.table;
        var laypage = layui.laypage;
        var $ = layui.$;
        table.render({
            elem: _domId,
            limit: _curPageLimit,
            cols: [_colsPaeam],
            data: _data,
            done: function () {
                var pageDomeId = _pageId ? _pageId.split("#")[1] : "";
                if (_pageCount > 0 && pageDomeId) {
                    $(_pageId).show();
                    laypage.render({
                        elem: pageDomeId,
                        count: _pageCount,
                        curr: _curPage,
                        limit: _curPageLimit,
                        layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
                        jump: function (obj, first) {
                            if (!first) {
                                _fn.call(self, obj);
                            }
                        }
                    })
                } else {
                    $(_pageId).hide();
                }
            }
        });
    });
}

//通用数据表格绑定--后台数据分页
function con_dataTableRenderWithToolbar(_domId, _colsPaeam, _data, _toolbarId, _pageId, _pageCount, _curPage, _curPageLimit, _fn) {
    var self = this;
    layui.use(['table', 'laypage', 'jquery'], function () {
        var table = layui.table;
        var laypage = layui.laypage;
        var $ = layui.$;
        table.render({
            elem: _domId,
            limit: _curPageLimit,
            cols: [_colsPaeam],
            toolbar: _toolbarId,
            data: _data,
            done: function () {
                var pageDomeId = _pageId ? _pageId.split("#")[1] : "";
                if (_pageCount > 0 && pageDomeId) {
                    $(_pageId).show();
                    laypage.render({
                        elem: pageDomeId,
                        count: _pageCount,
                        curr: _curPage,
                        limit: _curPageLimit,
                        layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'],
                        jump: function (obj, first) {
                            if (!first) {
                                _fn.call(self, obj);
                            }
                        }
                    })
                } else {
                    $(_pageId).hide();
                }
            }
        });
    });
}

//通用数据表格绑定--自动分页
//由于layui无法正常获取自动分页的当前分页limit，通过Dome元素去获取。
function con_dataTableRenderAutoPage(_domId, _colsPaeam, _data, _currPage, _curPageLimit, _fn, isRowSpan) {
    var _curr = _currPage || 1;
    var _limit = _curPageLimit || 10;
    layui.use(['table', 'laypage', 'jquery'], function () {
        var table = layui.table;
        var tableRenderConfig = {
            elem: _domId,
            limit: _limit,
            cols: [_colsPaeam],
            page: {curr: _curr},
            data: _data,
            done: function (renderData, _page) {
                if (_fn) {
                    var _limit = $(_domId).next().find("span :selected").val();
                    _fn.call(this, _page, Number(_limit));
                }
                //设置合并单元格 阻止用户 批量上传
                if (isRowSpan) {
                    $(_domId).next().find("tbody").children().each(function (index, el) {
                        if (index % 2 == 0) {
                            $(el).children().first().attr("rowspan", "2");
                        } else {
                            $(el).children().first().hide();
                        }
                    });
                }
            }
        }
        table.render(tableRenderConfig);
    });
}

function con_dataTableRenderNoPage(_domId, _colsPaeam, _data) {
    layui.use(['table', 'laypage', 'jquery'], function () {
        var table = layui.table;
        var tableRenderConfig = {
            elem: _domId,
            cols: [_colsPaeam],
            data: _data
        }
        table.render(tableRenderConfig);
    });
}

//获取流程信息模板
function con_processGetTemplate(_process, fn) {
    layui.use(["layer", "laytpl"], function () {
        var layer = layui.layer,
            laytpl = layui.laytpl;
        var htmlTemplate = '<ul class="process-nav">         {{# layui.each(d,function(index,item){ }} {{# if(item.state == "2"){ }}<li class="process-nav-done" style="width: {{100/d.length}}%;z-index: {{d.length-index}}"><span class="process-name" title="{{item.stepName}}">{{item.orderNumber}}、{{item.stepName}}</span>                {{# if(index<d.length-1){ }}<div class="process-img"><div class="process-img-bg process-img-done-bg"></div><img src="' + getStaticResourceIp() + '/images/jiantou.png"></div>                 {{# } }}</li>             {{# }else if(item.state == "1"){ }}<li class="process-nav-doing" style="width: {{100/d.length}}%;z-index: {{d.length-index}}"><span class="process-name" title="{{item.stepName}}">{{item.orderNumber}}、{{item.stepName}}</span>                 {{# if(index<d.length-1){ }}<div class="process-img"><div class="process-img-bg process-img-doing-bg"></div><img src="' + getStaticResourceIp() + '/images/jiantou.png"></div>                 {{# } }}</li>             {{# }else{ }}<li style="width: {{100/d.length}}%;z-index: {{d.length-index}}"><span class="process-name" title="{{item.name}}">{{item.orderNumber}}、{{item.stepName}}</span>                {{# if(index<d.length-1){ }}<div class="process-img"><div class="process-img-bg"></div><img src="' + getStaticResourceIp() + '/images/jiantou.png"></div>                 {{# } }}</li>             {{# } }}         {{# }) }}<i style="clear: both;display: block;"></i></ul>';
        laytpl(htmlTemplate).render(_process.processSteps, function (_html) {
            fn(_html);
        });
    });
}

//流程控件
function con_startApplyProcess(_sqlx, fn) {
    layui.use(["layer", "laytpl"], function () {
        var layer = layui.layer,
            laytpl = layui.laytpl;
        if (!_sqlx) {
            layer.msg("未获取到申请类型！");
            return;
        }
        postDataToServer("/userAccessModel/getSqlxProcessHead", {sqlx: _sqlx, clientType: "2"}, function (data) {
            if (data) {
                var applyProcessObj = {
                    process: data
                };
                //流程数据初始化
                if (data.processSteps[0].href) {
                    data.processSteps[0].state = "1";
                } else {
                    data.processSteps[0].processSteps[0].state = "1";
                }
                con_processGetTemplate(applyProcessObj.process, function (_html) {
                    fn(_html, applyProcessObj);
                });
            } else {
                layer.msg("获取流程信息失败")
            }
        });
    });
}

//流程控件--新
function con_startApplyProcessNew(reqParam, fn) {
    layui.use(["layer", "laytpl"], function () {
        var layer = layui.layer,
            laytpl = layui.laytpl;
        postDataToServer("/userAccessModel/getSqlxProcessHead", reqParam, function (data) {
            if (data) {
                var applyProcessObj = {
                    process: data
                };
                fn(applyProcessObj);
            } else {
                layer.msg("获取流程信息失败")
            }
        });
    });
}

//流程控件--下一步
function con_startApplyProcessNextStep(applyProcessObj) {
    var nextUrl = (function (_process) {
        for (var i = 0, j = _process.processSteps.length; i < j; i++) {
            var item = _process.processSteps[i];
            if (item.state == "1" && item.processSteps.length == "0") {
                item.state = "2";
                _process.processSteps[i + 1].state = "1";
                applyProcessObj.process.stepName = _process.processSteps[i + 1].stepName;
                return _process.processSteps[i + 1].href;
            } else if (item.state == "1") {
                var _href = "";
                for (var p = 0, q = item.processSteps.length; p < q; p++) {
                    if (item.processSteps[p].state == "0") {
                        (p > 0) && (item.processSteps[p - 1].state = "2");
                        item.processSteps[p].state = "1";
                        return item.processSteps[p].href;
                    }
                    //item.processSteps[item.processSteps.length-1].state = "2";
                }
                item.state = "2";
                _process.processSteps[i + 1].state = "1";
                applyProcessObj.process.stepName = _process.processSteps[i + 1].stepName;
                return _process.processSteps[i + 1].href;
            }
        }
    })(applyProcessObj.process);

    sessionStorage.setItem("applyProcessObj", JSON.stringify(applyProcessObj));
    nextUrl && pageToUrl(nextUrl);
}

//流程控件--流程申请查看
function con_toApplyDetailHref(sqlx, slbh, type, approveType) {
    if (!sqlx) {
        layer.msg("未获取到申请类型！");
        return;
    }
    //获取申请类型
    con_getAllUserSafeInfoNow(function (userInfo) {
        postDataToServer("/userAccessModel/getSqlxProcessHead", {
            sqlx: sqlx,
            role: userInfo.role,
            type: "2",
            clientType: "2"
        }, function (data) {
            if (data && data.processSteps[0] && data.processSteps[0].href) {
                if (type == "_blank") {
                    pageToUrl(data.processSteps[0].href + "?slbh=" + slbh + (approveType ? ("&type=" + approveType) : ""), "_blank");
                } else {
                    pageToUrl(data.processSteps[0].href + "?slbh=" + slbh);
                }
            } else {
                layer.msg("未获取到地址。")
            }
        });
    });
}

//流程控件--流程申请查看
function con_getApplyDetailHref(sqlx, fn) {
    if (!sqlx) {
        layer.msg("未获取到申请类型！");
        return;
    }
    //获取申请类型
    con_getAllUserSafeInfoNow(function (userInfo) {
        postDataToServer("/userAccessModel/getSqlxProcessHead", {
            sqlx: sqlx,
            role: userInfo.role,
            type: "2",
            clientType: "2"
        }, function (data) {
            if (data && data.processSteps[0] && data.processSteps[0].href) {
                fn(data.processSteps[0].href);
            } else {
                layer.msg("未获取到地址。")
            }
        });
    });

}

//启动上传附件面板
function con_startUploadFilePanel(fileData, fn) {
    layui.use(["layer", "element"], function () {
        var layer = layui.layer;
        var element = layui.element;
        var singleFileArr = [];
        fileData[0].fjXmModuleList.forEach(function (p) {
            p.fjList.forEach(function (q) {
                singleFileArr.push({
                        name: q.fileName,
                        value: [{
                            slbh: fileData[0].slbh,
                            fjXmModuleList: {
                                fjxm: p.fjxm,
                                fjList: [q]
                            }
                        }]
                    }
                );
            });
        });
        if (singleFileArr.length < 1) {
            fn();
            return;
        }
        var submitResults = [];
        layer.open({
            area: ["800px", "400px"],
            content: '<div class="upload-process-panel"><span class="process-title">附件上传进度：</span><div class="process-content"><div class="layui-progress layui-progress-big" lay-filter="uploadingProcess" lay-showPercent="true"><div class="layui-progress-bar" lay-percent="0%"></div></div></div><div class="process-animate"><div class="uploading-img"></div><span class="uploading-name">正在上传：<span></span></span></div></div>',
            closeBtn: 0,
            success: function () {
                $(".layui-layer-btn0").css({
                    "display": "none"
                });
                element.init();
                var startIndex = 0, endIndex = singleFileArr.length - 1;
                ;
                var recursionPost = function () {
                    //正在上传...
                    $(".uploading-name span").text(singleFileArr[startIndex].name);
                    element.progress('uploadingProcess', (parseInt(startIndex / endIndex * 100)) + "%");
                    postDataToServerWithoutMoadal("/applyModel/saveApplyFjData", singleFileArr[startIndex].value, function () {
                        submitResults.push({name: singleFileArr[startIndex].name, result: true});
                        startIndex++;
                        if (startIndex > endIndex) {
                            layer.closeAll();
                            fn(submitResults);
                        } else {
                            recursionPost();
                        }
                    }, function (res) {
                        submitResults.push({name: singleFileArr[startIndex].name, result: false, msg: res.head.msg});
                        startIndex++;
                        if (startIndex > endIndex) {
                            layer.closeAll();
                            fn(submitResults);
                        } else {
                            recursionPost();
                        }
                    });
                };
                recursionPost();
            }
        });
    });
}

//判断不动产单元号是否必填
function con_isBdcdyhNecessary() {
    var isNecessary = conPageConfig.estateplat_register.IsBdcdyhNecessary == "true";
    if (isNecessary) {
        layui.use(["layer"], function () {
            var layer = layui.layer;
            layer.msg("该产权证没有不动产单元号，不可创建流程！");
        });
    }
    return isNecessary;
}

//退出登陆
function logout() {
    sessionStorage.clear();
    pageToUrl('logout');
}

String.prototype.formatDateString = function () {
    var _str = this.split("-");
    (Number(_str[1]) < 10) && (_str[1] = "0" + _str[1]);
    (Number(_str[2]) < 10) && (_str[2] = "0" + _str[2]);
    return _str.join("-");
}

Date.prototype.format = function (format) {
    if(!format){
        return "";
    }
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

//数组去重
Array.prototype.duplicateRemoval = function () {
    var self = this;
    var arr = [];
    self.forEach(function (item) {
        if (arr.indexOf(item) < 0) {
            arr.push(item);
        }
    });
    return arr;
}

//IE12一下中find方法失效
if (!Array.prototype.find) {
    Array.prototype.find = function (callback) {
        return callback && (this.filter(callback) || [])[0];
    }
}

//判断IE版泵
function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if (isIE) {
        // var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        // reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if (fIEVersion == 7) {
            return 7;
        } else if (fIEVersion == 8) {
            return 8;
        } else if (fIEVersion == 9) {
            return 9;
        } else if (fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }
    } else if (isEdge) {
        return 'edge';//edge
    } else if (isIE11) {
        return 11; //IE11
    } else {
        return -1;//不是ie浏览器
    }
}

function con_getAllUserInfoNow(_fn) {
    postDataToServer("/loginModel/getUserInfo", {}, function (data) {
        _fn.call(this, data);
    }, true);
}

///获取用户信息
function con_getAllUserSafeInfoNow(_fn) {
    postDataToServer("/loginModel/getUserInfoFromCookie", {}, function (data) {
        _fn.call(this, data);
    }, true, function (res) {
        pageToUrl("login");
    });
}

function con_getSessionUserInfo() {
    return JSON.parse(sessionStorage.getItem("loginMsg"));
}


//js 加法计算
function con_accAdd(arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2))
    return ((arg1 * m + arg2 * m) / m).toFixed(2);
}

//js 减法计算
function con_subtr(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2));
    //last modify by deeka
    //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(2);
}

//js 乘法函数
function con_accMul(arg1, arg2) {
    // 兼容权利比例为50%
    if (arg1.indexOf("%") > 0) {
        arg1 = arg1.replace("%", "") / 100;
    }
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

//js 除法函数
function con_accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length
    } catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""))
        r2 = Number(arg2.toString().replace(".", ""))
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

//生成UUID
function createUUID() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    var uuid = s.join("");
    return uuid;
}

/**
 * AES加密
 * @param word
 * @returns {*}
 */
function encrypt(word) {
    var key = CryptoJS.enc.Utf8.parse("9LQwYy7tsonieMqx");
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

/**
 * AES解密
 * @param word
 * @returns {*}
 */
function decrypt(word) {
    var key = CryptoJS.enc.Utf8.parse("9LQwYy7tsonieMqx");
    var decrypt = CryptoJS.AES.decrypt(word, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}

// 父页面layer // 仅用于用户管理、部门管理和角色管理
function con_parentLayerUser(fn, obj) {
    //初始值obj:{title,_data, content} _data: {organizeList, roleList, orgId, role} content: html code
    if (fn) {
        layui.use(['form', 'tree', 'layer', 'formSelects', 'laytpl'], function () {
            var form = layui.form,
                tree = layui.tree,
                formSelects = layui.formSelects,
                laytpl = layui.laytpl,
                layer = layui.layer;

            var _data = obj._data;
            var content = obj.content;
            laytpl(content).render(_data, function (_html) {
                layer.open({
                    type: 1,
                    title: obj.title,
                    area: obj.area ? obj.area : ["550px", "auto"],
                    btn: ['保存', '取消'],
                    btnAlign: "c",
                    move: false,
                    content: _html,
                    yes: function (index) {
                        $("button.main-btn-a.hide").click();
                    },
                    success: function (layero, index) {
                        _data.roleId = _data.roleId || "";
                        if (obj.title == '编辑用户' || obj.title == '新增用户') {
                            // 角色
                            //local模式
                            formSelects.data('role', 'local', {
                                arr: _data.roleList
                            });

                            // 加载部门树
                            formSelects.data('org', 'local', {
                                arr: _data.organizeList
                            });

                            if (obj.title === '编辑用户') {
                                $("select[name='zjType']").val(_data.zjType);
                                formSelects.value('role', _data.roleId.split(','));
                                formSelects.value('org', _data.orgId.split(','));
                            }
                        } else if (obj.title == '编辑部门' || obj.title == '新增部门') {
                            // 加载角色树
                            formSelects.data('role', 'local', {
                                arr: _data.roleList
                            });
                            if (obj.title == '编辑部门') {
                                formSelects.value('role', _data.roleId.split(','));
                                // 加载部门树
                                formSelects.data('parentOrgId', 'local', {
                                    arr: _data.orgList
                                });
                                formSelects.value('parentOrgId', _data.parentOrgId.split(','));
                            } else {
                                // 加载部门树
                                formSelects.data('parentOrgId', 'local', {
                                    arr: _data.organizeList
                                });
                            }
                        } else if (obj.title == '授权' || obj.title == '功能菜单配置') {
                            // 添加鼠标覆盖显示title
                            $(".checkbox-container").on('mouseenter', '.layui-form-checkbox[lay-skin=primary] span', function () {
                                var $this = $(this);
                                $this.attr('title', $this.text());
                            });

                            form.on('checkbox', function (obj) {
                                var $this = $(obj.elem);
                                if (obj.othis[0].innerText == '全部') {
                                    if (obj.elem.checked) {
                                        $this.parents(".checkbox-czqx").find("input").prop("checked", true);
                                    } else {
                                        $this.parents(".checkbox-czqx").find("input").prop("checked", false);
                                    }
                                } else {
                                    if (obj.elem.checked) {
                                        var $thisSiblings = $this.parent().parent().find("input");
                                        var isAll = true;  //是否选中全部
                                        $thisSiblings.each(function () {
                                            if (!$(this).context.checked) {
                                                isAll = false;
                                                return false;
                                            }
                                        });

                                        if (isAll) {
                                            $this.parents(".checkbox-czqx").find("input").eq(0).prop("checked", true);
                                        } else {
                                            $this.parents(".checkbox-czqx").find("input").eq(0).prop("checked", false);
                                        }
                                    } else {
                                        $this.parents(".checkbox-czqx").find("input").eq(0).prop("checked", false);
                                    }
                                }

                                form.render();
                            })
                        } else if (obj.title == '新增角色') {
                            // 加载部门树
                            formSelects.data('orgList', 'local', {
                                arr: _data.organizeList
                            });
                        } else if (obj.title == '编辑角色') {
                            // 加载部门树
                            formSelects.data('orgList', 'local', {
                                arr: _data.relativeDept
                            });
                            formSelects.value('orgList', _data.orgId.split(','));
                            //加载已经绑定的所属角色
                            console.log(_data);
                            $("select[name='belongRole']").val(_data.belongRole);

                        }

                        form.render();

                        // 垂直居中修正位置
                        layero.css({
                            "top": "50%",
                            "margin-top": -layero.height() / 2
                        });

                        // 表单验证 自定义
                        form.verify({
                            // 自定义角色编号认证
                            roleNum: function (value) {
                                if (!(/(^[0-9]\d*$)/.test(value))) {
                                    return '请输入正整数'
                                }
                            }
                        });

                        // 下一步表单提交
                        form.on('submit(saveUserInfo)', function (obj) {
                            obj = obj.field;
                            fn.call(this, obj);
                            return false;
                        });
                    }
                });
            })
        });
    }
}

//关闭当前窗口
function con_windowCloseSelf() {
    var browserName = navigator.appName;
    if (browserName == "Microsoft Internet Explorer") {
        this.focus();
        self.opener = this;
        self.close();
    } else {
        try {
            this.focus();
            self.opener = this;
            self.close();
        } catch (e) {
            console.log(e)
        }
        try {
            window.open('', '_self', '');
            window.close();
        } catch (e) {
            console.log(e)
        }
    }
}

//公共审核功能
function con_isPrevApprove(_type, _slzt, _slbh) {
    if (_type == "check" && (_slzt == "0" || _slzt == "8")) {
        $(".btn-center").html('<button class="layui-btn approve-pass">审核通过</button><button class="layui-btn layui-btn-danger approve-reject">审核驳回</button>' + $(".btn-center").html());
        $("body").on("click", '.approve-pass', function () {
            //layer.confirm("确认通过？",{btnAlign:"c"},function(){
            layer.prompt({
                formType: 2,
                title: "请输入审核通过原因",
                area: ['800px', '350px'],
                btnAlign: "c",
                value: "",
                yes: function (index, layero) {
                    layer.close(index);
                    var value = layero.find(".layui-layer-input").val();
                    postDataToServer("/manageSqxxModel/examForPass", {
                        slbh: _slbh,
                        slzt: 4,
                        slxx: value.trim()
                    }, function (data) {
                        layer.msg("审核完成！", {
                            end: function () {
                                window.opener.frames[0].renderApplyTablefnc();
                                location.reload();
                            }, time: 2000
                        });
                    });
                }
            });
        });
        $("body").on("click", '.approve-reject', function () {
            layer.prompt({
                formType: 2,
                title: "请输入审核不通过原因（必填）",
                area: ['800px', '350px'],
                btnAlign: "c",
                yes: function (index, layero) {
                    var value = layero.find(".layui-layer-input").val();
                    if (value == "") {
                        layer.msg("请输入审核不通过原因");
                    } else {
                        layer.close(index);
                        postDataToServer("/manageSqxxModel/examForPass", {
                            slbh: _slbh,
                            slzt: 5,
                            slxx: value.trim()
                        }, function (data) {
                            layer.msg("审核完成！", {
                                end: function () {
                                    window.opener.frames[0].renderApplyTablefnc();
                                    location.reload();
                                }, time: 2000
                            });
                        });
                    }
                }
            });
        });
    }
}

//读取身份证
var CVR_IDCard = {};
function con_startReadIdCard(_fn) {
    layui.use(["layer"], function () {
        var layer = layui.layer;
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            var userInfo = {realName: "", identityNo: ""};
            CVR_IDCard = document.getElementById("CVR_IDCard");
            //若无CVR_IDCard，创建页面OBject对象
            if (CVR_IDCard == undefined || CVR_IDCard == null) {
                try {
                    $("body").append('<OBJECT classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" codebase="CVR100.cab#version=3,0,3,3" id="CVR_IDCard" name="CVR_IDCard" width=0 height=0 align=center hspace=0	vspace=0> </OBJECT>');
                } catch (e) {
                    console.log(e);
                    fn({isWrong: true, msg: "创建dome错误，请联系管理员"});
                }
                CVR_IDCard = document.getElementById("CVR_IDCard");
            }
            CVR_IDCard.PhotoPath = "C:/IDCardPhoto";
            CVR_IDCard.TimeOut = 5;
            layer.alert("请刷身份证...", {closeBtn: 0, btn: []}, function (index) {
                // layer.close(index);
                // _fn({isWrong:true,msg:"您取消读取身份证"});
                // CVR_IDCard.DoStopRead;
            });
            var strReadResult = CVR_IDCard.ReadCard;
            if (typeof(strReadResult) == "undefined") {
                _fn({isWrong: true, msg: "请检查身份证读卡器是否安装成功！"});
                return false;
            }
            if (strReadResult == 0) {
                userInfo.realName = CVR_IDCard.Name;
                userInfo.identityNo = CVR_IDCard.CardNo;
                if (userInfo.realName == null || userInfo.identityNo == "") {
                    layer.alert("读取身份证失败，请拿起身份证重新放置！", {btn: ["继续放置"]}, function (index) {
                        layer.close(index);
                        con_startReadIdCard(_fn);
                    });
                } else {
                    _fn(userInfo);
                }
            } else {
                layer.alert("读取身份证失败，请拿起身份证重新放置！", {btn: ["继续放置"]}, function (index) {
                    layer.close(index);
                    con_startReadIdCard(_fn);
                });
            }
        } else {
            _fn({isWrong: true, msg: "请在IE浏览器中使用该功能"});
        }
    });
}

//启用图形验证
function con_startImageValidate(_successFn, _title) {
    if (conPageConfig.estateplat_register.UseImageValidate == "true") {
        layui.use("layer", function (layer) {
            layer.open({
                type: 1,
                title: _title ? _title : "请完成图形验证",
                content: '<div class="picture-validate" style="padding: 20px;"><div id="pictureSliderValidate"></div></div>',
                area: ["375px", "375px"],
                success: function (layero, index) {
                    pictureSliderValidate({
                        el: '$("#pictureSliderValidate")',
                        width: '300',
                        height: '185',
                        img: [
                            '../../static/project/images/estateRegisterImg1.jpg',
                            '../../static/project/images/estateRegisterImg2.jpg',
                            '../../static/project/images/estateRegisterImg3.jpg'
                        ],
                        success: function () {
                            layer.close(index);
                            _successFn();
                        },
                        error: function () {
                        }
                    });
                }
            });
        });
    } else {
        _successFn();
        return false;
    }
}

// 查询所选不动产是否正处于办理状态
function con_checkBdcdyh(sqlx, bdcdyhList, _successFn) {
    postDataToServer("/BdcXmCheck/checkBdcdyh", {
        sqlx: sqlx,
        bdcdyhList: bdcdyhList
    }, function (res) {
        if (res && res.data && res.data.bdcdyhList && res.data.bdcdyhList.length) {
            var msgLists = res.data.bdcdyhList.map(function (item) {
                return item.msg
            });
            layer.open({
                title: "信息",
                content: msgLists.join('</br>'),
                area: ['auto', 'auto'],
                btnAlign: 'c'
            });
        } else {
            _successFn();
        }
    });
}

//转换元为万元
function con_convertYuanToWan(_number) {
    if (!_number || isNaN(_number)) {
        return "";
    }
    return con_accDiv(_number, 10000);
}

//转换日期格式：补0
function con_formatDate(date) {
    if (date != "") {
        var arr1 = date.split("-");
        var arr2 = arr1.map(function (item) {
            if (item.length < 2) {
                item = "0" + item.toString();
            }
            return item;
        });
    }
    return date != "" ? arr2.join("-") : "";
}