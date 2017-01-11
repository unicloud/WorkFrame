<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/content/commonJsp/base.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售配比</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="销售配比">

<script type="text/javascript">
    /**   定义公用变量   */
    // 存储查询的JSON对象.
    var whereJson = "";

    // 定义一个变量,标识当前界面点到了哪个tab上
    var currentTabPrefix = "tab1";

</script>
<style> 
    .itemDiv {
        float:left;
        width:100%;
        height:15%;
    }
    #item4 {
        height: 25%
    }
    .rams_label{
      float:left;
      height:28px;
      font-size:15px;
      text-align:center;
      margin-top:4px;
    }
    .inputLabel{
      float:left;
      margin:4px 10px;
    }
</style>
</head>

<body>
    <div id="tabsMain">
        <div class="itemDiv" id="item1">
            <div class="feedExpander" style="overflow: hidden;">
                <div class="splitter-panel">银行数据配比</div>
                <div>
                    <div>
                        <div class="inputLabel">
                            <label class="rams_label">开始日期:</label>
                            <div id="startDate1" class="jqxDateTimeInput" style="float:left;"></div>
                        </div>
                        <div class="inputLabel">
                            <label  class="rams_label">结束日期:</label>
                            <div id="endDate1" class="jqxDateTimeInput" style="float:left;"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">出票公司:</label>
                            <div id="issCo1"></div>
                        </div>
                    </div>
                    <div id="tab1Toolbar1"></div>
                </div>
            </div>
        </div>
        <div class="itemDiv" id="item2">
            <div class="feedExpander" style="overflow: hidden;">
                <div class="splitter-panel">代理人配比</div>
                <div>
                    <div>
                        <div class="inputLabel">
                            <label class="rams_label">开始日期:</label>
                            <div id="startDate2" class="jqxDateTimeInput"></div>
                        </div>

                        <div class="inputLabel">
                            <label class="rams_label">结束日期:</label>
                            <div id="endDate2" class="jqxDateTimeInput"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">出票公司:</label>
                            <div id="issCo2" ></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">重新配比:</label>
                            <div style="float:left; margin-top:5px"> 
                                <input type="checkbox" id="checkbox1" name="checkbox1"/>
                            </div>
                        </div>
                    </div>
                    <div id="tab1Toolbar2" style="float:left;"></div>
                </div>
            </div>
        </div>
        <div class="itemDiv" id="item3">
            <div class="feedExpander" style="overflow: hidden;">
                <div class="splitter-panel">网站配比</div>
                <div>
                    <div>
                        <div class="inputLabel">
                            <label class="rams_label">开始日期:</label>
                            <div id="startDate3" class="jqxDateTimeInput"></div>
                        </div>

                        <div class="inputLabel">
                            <label class="rams_label">结束日期:</label>
                            <div id="endDate3" class="jqxDateTimeInput"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">出票公司:</label>
                            <div id="issCo3" ></div>
                        </div>
                    </div>
                    <div id="tab1Toolbar3" style="float:left;"></div>
                </div>
            </div>
        </div>
        <div class="itemDiv" id="item4">
            <div class="feedExpander" style="overflow: hidden;">
                <div class="splitter-panel">运价计算</div>
                <div>
                    <div>
                        <div class="inputLabel">
                            <label class="rams_label">票库:</label>
                            <div id="ticType4"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">开始日期:</label>
                            <div id="startDate4" class="jqxDateTimeInput"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">结束日期:</label>
                            <div id="endDate4" class="jqxDateTimeInput" ></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">出票公司:</label>
                            <div id="issCo4" ></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">出票方式:</label>
                            <div id="issFlag" ></div>
                        </div><br><br><br>
                        <div class="inputLabel">
                            <label class="rams_label">计算方式:</label>
                            <div id="computeFlag"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">计算类型:</label>
                            <div id="computeStr"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">代理人代码:</label>
                            <input type="text" id= "agentCode" class="keyun_Input" />
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">营业部:</label>
                            <input type="text" id= "saleDepartment" class="keyun_Input"/>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">航线:</label>
                            <input type="text" id= "airlines" class="keyun_Input"/>
                        </div>
                    </div>
                    <div id="tab1Toolbar4" style="float:left;"></div>
                </div>
            </div>
        </div>
        <div class="itemDiv" id="item5">
            <div class="feedExpander" style="overflow: hidden;">
                <div class="splitter-panel">退换开明细更新</div>
                <div>
                    <div>
                        <div class="inputLabel">
                            <label class="rams_label">开始日期:</label>
                            <div id="startDate5" class="jqxDateTimeInput"></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">结束日期:</label>
                            <div id="endDate5" class="jqxDateTimeInput" ></div>
                        </div>
                        <div class="inputLabel">
                            <label class="rams_label">出票公司:</label>
                            <div id="issCo5" ></div>
                        </div>
                    </div>
                    <div id="tab1Toolbar5" ></div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        /** 界面布局 */
        $(document).ready(function(e) {
        
            $("#tabsMain").jqxPanel({theme : sysTheme,width : "100%",height : "100%"});

            $(".keyun_Input").jqxInput({width:100, height:25, theme : sysTheme});
            
            $(".feedExpander").jqxExpander({theme : sysTheme,toggleMode: 'none', 
                showArrow: false, 
                width: "100%", height: "100%",
            });
            
            $('.jqxDateTimeInput').jqxDateTimeInput({ theme : sysTheme, formatString: "yyyy-MM-dd", width: "100px", height: "25px", culture: "zh-CN" });
            //获取当前日期前七天
            var now = new Date();
            var date = new Date(now.getTime() - 7 * 24 * 3600 * 1000);
            var yyyy = date.getFullYear();
            var MM = date.getMonth() + 1;
            var dd = date.getDate();
            var myDateStr=yyyy+"-"+MM+"-"+dd;
            $("#startDate1").jqxDateTimeInput("val", myDateStr);  //开始日期           
            $("#startDate2").jqxDateTimeInput("val", myDateStr);  //开始日期
            $("#startDate3").jqxDateTimeInput("val", myDateStr);  //开始日期
            $("#startDate4").jqxDateTimeInput("val", myDateStr);  //开始日期
            $("#startDate5").jqxDateTimeInput("val", myDateStr);  //开始日期
            /** 获取公司列表 */
            var reCo3cArry = sessionUserInfo.reCo3c.split(",");
            var issCoList = [];
            $.each(reCo3cArry, function() {
              issCoList.push({"TEXT": this,"VALUE":this});
            });
            $("#issCo1").jqxDropDownList({
                  theme : sysTheme,selectedIndex: 0, source: issCoList, displayMember: "TEXT", 
                 valueMember: "VALUE", width: "80px", height: "25px",autoDropDownHeight: true
            });
            $("#issCo2").jqxDropDownList({
                  theme : sysTheme,selectedIndex: 0, source: issCoList, displayMember: "TEXT", 
                 valueMember: "VALUE", width: "80px", height: "25px",autoDropDownHeight: true
            });
            $("#issCo3").jqxDropDownList({
                  theme : sysTheme,selectedIndex: 0, source: issCoList, displayMember: "TEXT", 
                 valueMember: "VALUE", width: "80px", height: "25px",autoDropDownHeight: true
            });
            $("#issCo4").jqxDropDownList({
                  theme : sysTheme,selectedIndex: 0, source: issCoList, displayMember: "TEXT", 
                 valueMember: "VALUE", width: "80px", height: "25px",autoDropDownHeight: true
            });
            $("#issCo5").jqxDropDownList({
                  theme : sysTheme,selectedIndex: 0, source: issCoList, displayMember: "TEXT", 
                 valueMember: "VALUE", width: "80px", height: "25px",autoDropDownHeight: true
            });

            /** 获取票库 */
            var ticTypeSource=[{"display" : "客票", "value" : "PAG"},{"display" : "杂费", "value" : "EMD"}];
            $("#ticType4").jqxDropDownList({ theme : sysTheme, source: ticTypeSource, displayMember: "display", valueMember: "value", selectedIndex: 0,width: "80px",height: "25px",autoDropDownHeight: true}); 
            
            /** 获取计算方式列表 */
            var computeFlagSource=[{"display" : "计算未找到政策的数据", "value" : "F"},{"display" : "重新计算", "value" : "T"},{"display" : "只计算未计算过的数据", "value" : "N"}];
            $("#computeFlag").jqxDropDownList({ theme : sysTheme, source: computeFlagSource, displayMember: "display", valueMember: "value", selectedIndex: 0,width: "140px",height: "25px",autoDropDownHeight: true}); 
           
           
            /** 获取计算类型列表 */
            var computeStrList = [{"TEXT" : "票款计算", "VALUE" : "FARE"},{"TEXT" : "税费", "VALUE" : "TAX"},{"TEXT" : "票款分摊", "VALUE" : "APP"}];
            var computeStrSource =
            {
                datatype: "json",
                datafields: [
                    { name: 'TEXT'},
                    { name: 'VALUE'}
                ],
                localdata : computeStrList
            };
            var computeStrDataAdapter = new $.jqx.dataAdapter(computeStrSource);
            $("#computeStr").jqxDropDownList({ 
                  theme : sysTheme,checkboxes: true, source: computeStrDataAdapter, displayMember: "TEXT", 
                 valueMember: "VALUE", width: "100px", height: "25px",autoDropDownHeight: true
            });
            $("#computeStr").jqxDropDownList('checkIndex', 0);

        });  
    </script>
</body>
</html>