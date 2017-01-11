<!--目前先硬编码菜单，待登录做好，从数据库中读取菜单 -->
var treedata =  
    [
        {
            id : "01",
            text : "客户评分系统",
            children : [
                {
                    id : "1001",
                    text  : "客户基本信息",
                    leaf : true,
                    url : "customerInfo"
                },
                {
                    id : "1002",
                    text  : "评分要素维护",
                    leaf : true,
                    url : "scoreFactor"
                },
                {
                    id : "1003",
                    text  : "客户汇总信息",
                    leaf : true,
                    url : "customerScore"
                },
                {
                    id : "1004",
                    text  : "汇总信息查询",
                    leaf : true,
                    url : "computeScore"
                }
            ]
        },{
            id : "02",
            text : "系统后台维护",
            children : [
                {
                    id : "2001",
                    text  : "数据窗维护",
                    leaf : true,
                    url : "dwMaintain"
                },
                {
                    id : "2002",
                    text  : "基础列表维护",
                    leaf : true,
                    url : "mdCodeList"
                }
            ]
        },
    ];