//处理头部信息
$(function(){
    //消息控件
    // if(loginMsg.userGuid){
    //     var $dome = $(".user-message-alarm")[0];
    //     if($dome){
    //         $dome = $($dome);
    //         var userMessageData = [
    //             {
    //                 content:"存在需要您核验的申请，编号为：123456789，请在我的申请中进行查看",
    //                 time:"2019-05-29 16:00:02",
    //                 //以消息类型字典表，控制跳转方位。
    //                 type:1
    //             },{
    //                 content:"您的预约申请已通过，编号为：123456789，请在我的预约中进行查看",
    //                 time:"2019-05-29 16:00:02",
    //                 type:2
    //             }
    //         ];
    //         var messateHtml = "";
    //         laytpl('<div class="user-message-alarm-zone"><h3 class="message-title">消息通知</h3><ul class="message-item">{{# layui.each(d,function(index,item){ }}<li><p class="item-content">{{item.content}}</p><p class="item-time">{{item.time}}</p></li>{{# })}}</ul></div>').render(userMessageData,function(_html){
    //             messateHtml = _html;
    //         });
    //         $dome.parent().append(messateHtml);
    //         $dome.parent().on("mouseenter",function(){
    //             $dome.parent().append(messateHtml);
    //         }).on("mouseout",function(){
    //             $(".user-message-alarm-zone").remove();
    //         });
    //     }
    // }

    //加载头部
    var headerHtml = '<div class="header-inner"><div class="header-logo"><img src="'+getStaticResourceIp()+'/images/'+conPageConfig.area_base_info.otherTitle+'" alt=""></div><div class="header-title" onclick="'+(conPageConfig.area_base_info.homePage?("window.open(\'"+conPageConfig.area_base_info.homePage+"\',\'_self\')"):"pageToUrl(\'login\')")+'"><img src="'+getStaticResourceIp()+'/images/back-to-index.png"> 返回网站首页</div><ul class="layui-nav" lay-filter=""><li class="layui-nav-item"><a href="javascript:void(0)" onclick="toUserIndex()">用户中心</a><ul class="header-operate-zone"><li class="reset-pwd">修改密码</li></ul></li><li class="layui-nav-item"><a href="javascript:void(0)" onclick="logout()">退出登录</a></li></ul></div>';
    $(".header").html(headerHtml);
    if(conPageConfig.estateplat_register.UpdatePwd && con_getSessionUserInfo() && conPageConfig.estateplat_register.UpdatePwd.indexOf(con_getSessionUserInfo().role)>-1){
        function showNavItem(){
            if(isOnPar || isOnSel){
                $(".header-inner .layui-nav-item .header-operate-zone").fadeIn(100);
            }else{
                $(".header-inner .layui-nav-item .header-operate-zone").fadeOut(100);
            }
        }
        var isOnPar = false;
        var isOnSel = false;
        $(".header-inner .layui-nav-item").mouseenter(function() {
            isOnPar = true;
            showNavItem()
        }).mouseleave(function(){
            isOnPar = false;
            setTimeout(function(){showNavItem()},100);
        });
        $(".header-inner .layui-nav-item .header-operate-zone").mouseenter(function() {
            isOnSel = true;
            showNavItem()
        }).mouseleave(function(){
            isOnSel = false;
            showNavItem();
        });

        $(".header-operate-zone").mouseover(function(e){
            $(this).show();
        });
        layui.use(["layer",'form'],function(){
            //修改密码事件修改
            var layer = layui.layer;
            var form  = layui.form;
            $(".reset-pwd").on("click",function(){
                layer.open({
                    type:1,
                    title:"用户密码修改",
                    area:["400px","260px"],
                    content:'<div class="user-pwd-reset-zone"><form class="layui-form"><div class="layui-form-item"><label class="layui-form-label">旧密码：</label><div class="layui-input-inline"><input class="layui-input" type="password" name="oldPwd" lay-verify="required"/></div></div><div class="layui-form-item"><label class="layui-form-label">新密码：</label><div class="layui-input-inline"><input class="layui-input" type="password" name="newPwd" lay-verify="required|pwdForm"/></div></div><div class="layui-form-item"><label class="layui-form-label">确认密码：</label><div class="layui-input-inline"><input class="layui-input" type="password" name="confirmNewPwd" lay-verify="required"/></div></div><div class="layui-form-item btn-container"><span class="layui-btn layui-btn-sm bdc-major-btn" lay-submit lay-filter="submitPwd">确认修改</span></div></form></div>',
                    success:function(){
                        form.verify({
                            pwdForm:function(data){
                                var reg = new RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,20}$/);
                                if (!reg.test(data)) {
                                    return "密码8-20个字符，至少包含大写字母，小写字母和数字！";
                                }
                            }
                        });
                        form.on("submit(submitPwd)",function(data){
                            if(data.field.newPwd != data.field.confirmNewPwd){
                                layer.msg("两次密码输入不一致");
                                return false;
                            }else if(data.field.oldPwd == data.field.newPwd){
                                layer.msg("原密码和新密码不能相同");
                                return false;
                            }else{
                                postDataToServer("/loginModel/changePwd",{userPwd:encrypt(data.field.oldPwd),newUserPwd:encrypt(data.field.newPwd)},function () {
                                    layer.msg("密码修改成功",function () {
                                        layer.closeAll();
                                    })
                                })
                            }
                        });
                    }
                });
            });
        });
    }else{
        $(".reset-pwd").remove()
    }

    //加载底部
    var footerHtml = '<div class="footer-content">'+conPageConfig.area_base_info.address+'</div>';
    var footerHref = '';
    if(conPageConfig.footer_href){
        var _arr = [];
        for(var attr in conPageConfig.footer_href){
            _arr.push('<span class="data-href pointer footer-href-item" onclick="window.open('+"'"+conPageConfig.footer_href[attr]+"'"+')">'+attr+'</span>')
        }
        footerHref += ('<div class="footer-href">'+ _arr.join("|")+'</div>');
    }
    footerHtml+= footerHref;
    $(".footer").html(footerHtml);
});