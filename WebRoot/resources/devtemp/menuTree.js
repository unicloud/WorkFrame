<!--目前先硬编码菜单，待登录做好，从数据库中读取菜单 -->
var treedata =  
    [
        {
            id : "01",
            text : "客户评分系统",
            children : [
                {
                    id : "1001",
                    text  : "客户汇总信息",
                    leaf : true,
                    url : "customerScore"
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
                },
                {
                    id : "2003",
                    text  : "用户权限",
                    leaf : true,
                    url : "userPrivilege"
                }
            ]
        },
    ];