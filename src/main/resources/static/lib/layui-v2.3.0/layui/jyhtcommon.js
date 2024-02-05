/**
 * Created by yy on 2018/9/14.
 */
//合同导入
var contractFileParam = null;
function con_ContractFileIn(_fn) {
    var self = this;
    self._content = '<form class="layui-form"><div class="layui-form-item" style="margin: 20px 20px 0 20px"><div class="layui-input-inline"><input type="text" class="layui-input" placeholder="合同号" id="contractNo"/></div><span class="layui-btn layui-btn-normal" onclick="contractFileParam.searchContract()">搜索<i class="fa fa-search"></i></span></div></form><div style="padding: 20px;"><table id="contractFileTable" lay-filter="contractFileTable"></table></div>';
    self.tableData = [];
    self.renderDataTable = function (_data) {
        layui.use(['table'], function () {
            var table = layui.table;
            var tableHeade = [
                {
                    field: "ywh",
                    title: "业务号",
                    align: "center"
                }, {
                    field: "hth",
                    title: "合同号",
                    align: "center"
                }, {
                    field: "zl",
                    title: "坐落",
                    align: "center"
                }, {
                    field: "qlrmc",
                    title: "权利人名称",
                    align: "center"
                }, {
                    field: "qlrzjzl",
                    title: "权利人证件种类",
                    align: "center"
                }, {
                    field: "qlrzjh",
                    title: "权利人证件号",
                    align: "center"
                }, {
                    field: "",
                    title: "导入",
                    align: "center",
                    toolbar:'<div class="layui-btn-container" id="sdaadsads"><button class="layui-btn layui-btn-sm" onclick="contractFileParam.fileIn('+"'{{d.ywh}}'"+')">导入</button></div>'
                }
            ];
            table.render({
                elem: "#contractFileTable",
                cols: [tableHeade],
                page: true,
                data: _data,
            });
        })
    }
    self.fileIn = function(_ywh){
        self.tableData.forEach(function(item){
            if(item.ywh == _ywh){
                _fn.call(this,item);
                layui.use(["layer"],function(){
                    var layer = layui.layer;
                    layer.closeAll();
                });
            }
        })
    }
    self.searchContract = function () {
        var contractNo = $("#contractNo").val();
        layui.use(['layer'], function () {
            var layer = layui.layer;
            if (!contractNo) {
                layer.msg("请输入合同号");
            }
            postDataToServer("/applyModel/getJyht",{hth: contractNo,page:10,size:0},function(data){
                if (data && data.code == "0000") {
                    console.log(JSON.stringify(data));
                    self.tableData = [];
                    data.data.htxx.forEach(function(item){
                        var tmplData = {
                            htxx:item,
                            hth:item.hth,
                            ywh:item.ywh,
                            zl:item.zl,
                            qlrmc:item.qlrxx.map(function(q){
                                return q.QLRMC;
                            }).join("、"),
                            qlrzjzl:item.qlrxx.map(function(q){
                                return q.zjlxmc;
                            }).join("、"),
                            qlrzjh:item.qlrxx.map(function(q){
                                return q.ZJH;
                            }).join("、"),
                        }
                        self.tableData.push(tmplData)
                    })
                    self.renderDataTable(self.tableData);
                    console.log(self.tableData);
                } else {
                    layer.msg(data.msg);
                }
            })
        })

    }
    self.initData = function () {
        layui.use(["layer", "form"], function () {
            var layer = layui.layer;
            var form = layui.form;
            layer.open({
                type: "1",
                title: "合同导入",
                area: ["1150px", "550px"],
                content: self._content
            })
            self.renderDataTable(self.tableData);
        })
    }
    self.initData();
}

//合同导入--页面赋值
function importContract (data) {
    //再次导入时，删除上次的数据
    $(".person1").each(function(){
        if(undefined != $(this).attr("id")){
            $(this).remove();
        }
    });
    //如果是多个联系人
    //$("input[name=gyfs][type=radio][value=1]").attr("checked",true);//value值为2的被选中
    //gyblControl();
    //赋值坐落
    $("#zl").val(data.zl);
    //合同号
    $("#mmhth").val(data.hth)
    //权利人信息--数组
    var qlrxxList = data.htxx.qlrxx;

    if (qlrxxList.length > 0){
        var qlrlen = 1;//权利人数量
        var ywrlen = 1;//义务人数量
        qlrxxList.forEach(function (item) {
            //item.qlrlb == 1 权利人
            if (item.QLRLB == 1 || item.QLRLB == "1"){
                if (1 == qlrlen){
                    forEachInsert(item,1);
                }else{
                    //添加权利人
                    addShareQlr(item,qlrlen);
                }
                qlrlen ++;
            }
            //item.qlrlb == 2 义务人
            if ((item.QLRLB == 2 || item.QLRLB == "2") && $("#obligeers").length > 0) {
                if (1 == ywrlen){
                    forEachInsert(item,2);
                }else{
                    //添加义务人 -- 待开发
                    //addShareQlr(item,ywrlen);
                }
                ywrlen ++;
            }

        })

        if (qlrlen > 2) {
            //选中共有方式
            choiceShareType(1);
        }

    }


}

//循环插入数据
function forEachInsert(item,type){
    if (1 == type){
        //权利人类型
        //$("#obligeers").find("input[name=qlrlx][type=hidden]").val(item.QLRLB);
        //权利人名称qlrmc
        $("#obligeers").find("input[name='qlr1-qlrmc'][type=text]").val(item.QLRMC);
        //证件种类qlrzjzl
        $("#obligeers").find("select[name='qlr1-qlrsfzjzl']").find("option[value = '"+item.ZJZL+"']").attr("selected","selected");
        //权利人证件号qlrzjh
        $("#obligeers").find("input[name='qlr1-qlrzjh'][type=text]").val(item.ZJH);
        //权利人邮编qlryb
        $("#obligeers").find("input[name='qlr1-qlryb'][type=text]").val(item.YB);
        //权利人地址DZ qlrtxdz
        $("#obligeers").find("input[name='qlr1-qlrtxdz'][type=text]").val(item.DZ);
        //权利人电话fddbrhfzrdh
        $("#obligeers").find("input[name='qlr1-qlrlxdh'][type=text]").val(item.DH);
    }
    if(2 == type){
        //权利人名称qlrmc
        $("#obligors").find("input[name='ywr1-qlrmc'][type=text]").val(item.QLRMC);
        //证件种类qlrzjzl
        $("#obligors").find("select[name='ywr1-qlrsfzjzl']").find("option[value = '"+item.ZJZL+"']").attr("selected","selected");
        //权利人证件号qlrzjh
        $("#obligors").find("input[name='ywr1-qlrzjh'][type=text]").val(item.ZJH);
        //权利人邮编qlryb
        $("#obligors").find("input[name='ywr1-qlryb'][type=text]").val(item.YB);
        //权利人地址DZ qlrtxdz
        $("#obligors").find("input[name='ywr1-qlrtxdz'][type=text]").val(item.DZ);
        //权利人电话fddbrhfzrdh
        $("#obligors").find("input[name='ywr1-qlrlxdh'][type=text]").val(item.DH);
    }

}

//选中共有方式
function choiceShareType(valu) {
    //共有方式
    $("input[name=gyfs][type=radio][value=1"+"]").attr("checked",true);
    //分别持证
    //$("input[name=sqfbcz][type=radio][value=0"+"]").attr("checked",true);
    //展示共有方式样式
    $("input[name='sqfbcz']").removeAttr('disabled');
    $(".img").show();
    var $qlbl = $(".person1").find("input[name='qlbl']");
    $.each($qlbl, function () {
        $(this).val("");
        $(this).attr('disabled', 'disabled');
    });
}
//获取权利人
function getUserObjMany(userType,item){
    if(!userType){
        layer.msg("新增人员时需指定起类型");
    }
    return {
        qlrid: "",
        sqid: "",
        qlrmc: item.QLRMC,
        qlrsfzjzl: "",
        qlrzjh: item.ZJH,
        qlrtxdz: item.DZ,
        qlbl: "",
        qlrlx: userType,
        qlrlxdh: "",
        qlryb: "",
        dlrdh: "",
        dlrmc: "",
        fddbrhfzr:"" ,
        fddbrhfzrdh: item.DH,
        dljgmc: ""
    }
}

//添加共有权利人
function addShareQlr(item, len) {

    if(pageParam.curCommonWay =="0"){
        layer.msg("当前共有方式为:单独所有,权利人数量限1位.");
        return;
    }
    var obj = getUserObjMany("1",item);
    pageParam.obligeerCount++;
    obj.obligeerCount = pageParam.obligeerCount;
    obj.id= "qlr"+pageParam.obligeerCount;
    obj.obligertitle = "权利人姓名(名称)";
    layui.use(["laytpl","form"],function(){
        var laytpl = layui.laytpl;
        var form = layui.form;
        laytpl($("#obligeerTmpl").html()).render(obj,function(_html){
            $("#obligeers").append(_html);
            //加载权利人证件类型
            pageParam.applyUserIdentityType.value="身份证";
            pageParam.applyUserIdentityType.name = obj.id+"-qlrsfzjzl";
            laytpl($("#selectTmpl").html()).render(pageParam.applyUserIdentityType,function(_html){
                $("#"+obj.id+"-qlrsfzjzl").html(_html);
                pageParam.applyUserIdentityType.value = "";
                applicationObj.qlrList.push(obj);
                if(pageParam.curCommonWay =="2"){
                    $(".qlr-gybl").attr("disabled",false);
                    $(".qlr-gybl").removeClass("input-disabled");
                }
                form.render()
            });
        })
    });
}