<!--目前先硬编码菜单，待登录做好，从数据库中读取菜单 -->
var treedata =  
    [
        {
            id : "01",
            text : "公共管理",
            children : [
                {
                    id : "1001",
                    text  : "系统管理",
                    leaf : false,
                    children : [
                        {
                            id : "1001001",
                            text : "用户管理",
                            leaf : true,
                            url : "systemUser"
                        },
                        {
                            id : "1001002",
                            text : "作业管理",
                            leaf : true,
                            url : "myJsp"
                        },
                        {
                            id : "1001003",
                            text : "关账管理",
                            leaf : true,
                            url : "closeBill"
                        },
                        {
                            id : "1001004",
                            text : "系统日志",
                            leaf : true,
                            url : "sysLogManage"
                        }
                        ,
                        {
                            id : "1001005",
                            text : "数据更新日志",
                            leaf : true,
                            url : "StTableAlertLog"
                        },
                        {
                            id : "1001006",
                            text : "个人管理",
                            leaf : true,
                            url : "personalManage"
                        },
                        {
                            id : "1001007",
                            text : "系统代码维护",
                            leaf : true,
                            url : "sysCodeList"
                        },
                        {
                            id : "1001008",
                            text : "数据窗配置",
                            leaf : true,
                            url : "dwMaintain"
                        },
                        {
                            id : "1001009",
                            text : "灵活报表配置",
                            leaf : true,
                            url : "flexibleMaintain"
                        },
                        {
                            id : "1001010",
                            text : "灵活报表查询",
                            leaf : true,
                            url : "flexibleQuery"
                        },
                        {
                            id : "1001011",
                            text : "分组报表配置",
                            leaf : true,
                            url : "rptGroupMaintain"
                        }
                    ]
                },
                {
                    id : "1002",
                    text : "公共主数据",
                    leaf : false,
                    children : [
                        {
                            id : "1002001",
                            text : "航空基础信息",
                            leaf : true,
                            url : "airlinesInfo"
                        },
                        {
                            id : "1002002",
                            text : "地理信息",
                            leaf : true,
                            url : "geographyInfo"
                        },
                        {
                            id : "1002003",
                            text : "组织架构",
                            leaf : true,
                            url : "orgStruct"
                        },
                        {
                            id : "1002004",
                            text : "汇率信息",
                            leaf : true,
                            url : "exchangeRateInfo"
                        },
                        {
                            id : "1002005",
                            text : "渠道信息",
                            leaf : true,
                            url : "channelInfo"
                        },
                        {
                            id : "1002006",
                            text : "订座OFFICE号",
                            leaf : true,
                            url : "officeInfo"
                        },
                        {
                            id : "1002007",
                            text : "代理人信息",
                            leaf : true,
                            url : "agentInfo"
                        },
                        {
                            id : "1002008",
                            text : "基础代码维护",
                            leaf : true,
                            url : "baseCodeList"
                        }
                        ,
                        {
                            id : "1002009",
                            text : "网电维护",
                            leaf : true,
                            url : "baseNetwork"
                        }
                    ]
                },
                {
                    id : "1003",
                    text : "结算主数据",
                    leaf : false,
                    children : [
                        {
                            id : "1003001",
                            text : "公司信息",
                            leaf : true,
                            url : "companyInfo"
                        },
                        {
                            id : "10030010",
                            text : "国家信息",
                            leaf : true,
                            url : "countryInfo"
                        },
                        {
                            id : "1003011",
                            text : "航空公司",
                            leaf : true,
                            url : "airlinesData"
                        },
                        {
                            id : "1003002",
                            text : "IATA区域",
                            leaf : true,
                            url : "iataZone"
                        },
                        {
                            id : "1003003",
                            text : "结算日历",
                            leaf : true,
                            url : "settCalendar"
                        },
                        {
                            id : "1003004",
                            text : "公司代码维护",
                            leaf : true,
                            url : "companyCodeList"
                        },
                        {
                            id : "1003005",
                            text : "账务科目对照",
                            leaf : true,
                            url : "accSubjectConvert"
                        },
                        {
                            id : "1003006",
                            text : "账务项目对照表",
                            leaf : true,
                            url : "accItemContrast"
                        },
                        {
                            id : "1003007",
                            text : "运输项目对照表",
                            leaf : true,
                            url : "tranItemContrast"
                        },
                        {
                            id : "1003008",
                            text : "城市群",
                            leaf : true,
                            url : "cityGroup"
                        },
                        {
                            id : "1003009",
                            text : "外部账号管理",
                            leaf : true,
                            url : "outAccountManage"
                        },
                        {
                            id : "1003010",
                            text : "责任会计",
                            leaf : true,
                            url : "dutyAccountant"
                        }
                    ]
                },
                {
                    id : "1004",
                    text : "客户管理",
                    leaf : false,
                    children : [
                        {
                            id : "1004001",
                            text : "代理人管理",
                            leaf : true,
                            url : "agentManage"
                        }
                    ]
                },
                {
                    id : "1005",
                    text : "空白票证",
                    leaf : false,
                    children : [
                                {
                                    id : "1005001",
                                    text : "票证入库",
                                    leaf : true,
                                    url : "companyStock"
                                },
                                {
                                    id : "1005002",
                                    text : "票证领用",
                                    leaf : true,
                                    url : "agentStock"
                                },
                                {
                                    id : "1005003",
                                    text : "其他流通",
                                    leaf : true,
                                    url : "fluxStockRecord"
                                },
                                {
                                    id : "1005004",
                                    text : "票证销号",
                                    leaf : true,
                                    url : "stockClear"
                                },
                                {
                                    id : "1005005",
                                    text : "库存备份",
                                    leaf : true,
                                    url : "inventoryBackUp"
                                },
                                {
                                    id : "1005006",
                                    text : "票证查询(空白票证)",
                                    leaf : true,
                                    url : "stockQuery"
                                },
                                {
                                    id : "1005007",
                                    text : "盘存报表",
                                    leaf : true,
                                    url : "inventoryReport"
                                }
                            ]
                }               
            ]
        },
        {
            id : "02",
            text : "后台功能",
            children : [
                {
                    id : "2001",
                    text : "权限管理",
                    leaf : true,
                    url : ""
                },
                {
                    id : "2002",
                    text : "功能集成",
                    leaf : true,
                    url : ""
                },
                {
                    id : "2003",
                    text : "CGD测试界面",
                    leaf : true,
                    url : "test/sysCodeList.jsp"
                },
                {
                    id : "2004",
                    text : "后台批处理",
                    leaf : true,
                    url : "backgroundBatch"
                }
            ]
        },
        {
            id : "03",
            text : "客运结算",
            children : [
                {
                    id : "3001",
                    text : "销售结算",
                    leaf : false,
                    children : [
                        {
                            id : "3001001",
                            text : "销售数据",
                            leaf : true,
                            url : "saleSettlement"
                        },
                        {
                            id : "3001002",
                            text : "终端电票",
                            leaf : true,
                            url : "terminalTickets"
                        },
                        {
                            id : "3001003",
                            text : "纸质票证",
                            leaf : true,
                            url : "paperTickets"
                        },
                        {
                            id : "3001004",
                            text : "网上电票",
                            leaf : true,
                            url : "websiteTickets"
                        },
                        {
                            id : "3001005",
                            text : "BSP电票",
                            leaf : true,
                            url : "bspTickets"
                        },
                        {
                            id : "3001006",
                            text : "ARC电票",
                            leaf : true,
                            url : "arcTickets"
                        },
                        {
                            id : "3001007",
                            text : "账务凭证",
                            leaf : true,
                            url : "accountingVouchers"
                        },
                        {
                            id : "3001008",
                            text : "二次结转",
                            leaf : true,
                            url : "twiceCarryDown"
                        },
                        {
                            id : "3001009",
                            text : "销售预估",
                            leaf : true,
                            url : "salesForecast"
                        },
                        {
                            id : "3001010",
                            text : "票证查询",
                            leaf : false,
                            children : [
                                        {
                                            id : "300101001",
                                            text : "票证查询(票号)",
                                            leaf : true,
                                            url : "psTicketNoQuery"
                                        },
                                        {
                                            id : "300101002",
                                            text : "票证查询(票联)",
                                            leaf : true,
                                            url : "psTicketDetailQuery"
                                        },
                                        {
                                            id : "300101003",
                                            text : "票证查询(杂费)",
                                            leaf : true,
                                            url : "psIncidentalsQuery"
                                        },
                                        {
                                            id : "300101004",
                                            text : "税费明细",
                                            leaf : true,
                                            url : "taxFeeDetail"
                                        },
                                        ]
                        }
                    ]
                },
                {
                    id : "3002",
                    text : "运输收款",
                    leaf : false,
                    children : [
                        {
                            id : "3002001",
                            text : "航班信息",
                            leaf : true,
                            url : "flightInfo"
                        },
                        {
                            id : "30020011",
                            text : "航班处理",
                            leaf : false,   
                            children:[
                                        {
                                            id : "3002001101",
                                            text : "FOC与CKI差异",
                                            leaf : true,
                                            url : "ptFocCkIDiff"
                                        },
                                        {
                                            id : "3002001102",
                                            text : "航班迁移",
                                            leaf : true,
                                            url : "ptFlightMove"
                                        },
                                        {
                                            id : "3002001103",
                                            text : "航班删除",
                                            leaf : true,
                                            url : "ptFlightDel"
                                        },
                                        ]
                        },
                        {
                            id : "3002002",
                            text : "离港数据",
                            leaf : false,
                            children:[
                                        {
                                            id : "3002002001",
                                            text : "离港数据",
                                            leaf : true,
                                            url : "lkCkiData"
                                        },
                                        {
                                            id : "3002002002",
                                            text : "离港数据比较",
                                            leaf : true,
                                            url : "lkCkiDataCompare"
                                        },
                                      ]
                        },
                        {
                            id : "3002003",
                            text : "票证处理",
                            leaf : true,
                            url : "ptTicketDeal"
                        },
                        {
                            id : "3002004",
                            text : "批号接收",
                            leaf : true,
                            url : "batchNoReception"
                        },
                        {
                            id : "3002005",
                            text : "票证审核",
                            leaf : true,
                            url : "ticketAudit"
                        },
                        {
                            id : "3002006",
                            text : "账单处理",
                            leaf : true,
                            url : "ptAccountDeal"
                        },
                        {
                            id : "3002007",
                            text : "记账凭证",
                            leaf : true,
                            url : "ptAccountingVouchers"
                        },
                        {
                            id : "3002008",
                            text : "收入预估",
                            leaf : true,
                            url : "ptRevenueForecast"
                        },
                        {
                            id : "3002009",
                            text : "清算导入",
                            leaf : true,
                            url : "ptAccountImport"
                        },
                        {
                            id : "3002010",
                            text : "票证查询",
                            leaf : false,
                            children:[
                                        {
                                            id : "3002010001",
                                            text : "票证查询(航班)",
                                            leaf : true,
                                            url : "ptTicketQuery"
                                        },
                                        {
                                            id : "3002010002",
                                            text : "税费明细(运输收款)",
                                            leaf : true,
                                            url : "ptTaxFeeDetail"
                                        },
                                        {
                                            id : "3002010003",
                                            text : "票证查询(账单)",
                                            leaf : true,
                                            url : "ptAccountTicketQuery"
                                        },
                                        {
                                            id : "3002010004",
                                            text : "杂费查询(航班)",
                                            leaf : true,
                                            url : "ptIncidentalsQuery"
                                        }
                                      ]
                        },
                        
                    ]
                },
                {
                    id : "3003",
                    text : "运输付款",
                    leaf : false,
                    children : [
                        {
                            id : "3003001",
                            text : "账单导入",
                            leaf : true,
                            url : "paymentImport"
                        },
                        {
                            id : "3003002",
                            text : "数据处理",
                            leaf : true,
                            url : "paymentDeal"
                        },
                        {
                            id : "3003003",
                            text : "账单审核",
                            leaf : true,
                            url : "paymentAudit"
                        },
                        {
                            id : "3003004",
                            text : "账单记账",
                            leaf : true,
                            url : "paymentAccount"
                        },
                        {
                            id : "3003005",
                            text : "任务单分配",
                            leaf : true,
                            url : "poJobOrderInput"
                        },
                        {
                            id : "3003006",
                            text : "任务单接收",
                            leaf : true,
                            url : "poJobOrderReceive"
                        },
                        {
                            id : "3003007",
                            text : "任务单审核",
                            leaf : true,
                            url : "poJobOrderAudit"
                        },
                        {
                            id : "3003008",
                            text : "任务单调整",
                            leaf : true,
                            url : "poJobOrderAdjust"
                        },
                        {
                            id : "3003009",
                            text : "票证查询",
                            leaf : false,
                            children:[
                                        {
                                            id : "300300901",
                                            text : "票证查询(运输付款)",
                                            leaf : true,
                                            url : "paymentTicketQuery"
                                        },
                                        {
                                            id : "300300902",
                                            text : "税费明细(运输付款)",
                                            leaf : true,
                                            url : "paymentTaxFeeDetail"
                                        },
                                        {
                                            id : "300300903",
                                            text : "任务单差异查询",
                                            leaf : true,
                                            url : "poJobOrderDiff"
                                        }
                                      ]
                        },
                    ]
                },
                {
                    id : "3004",
                    text : "批量处理",
                    leaf : false,
                    children : [
                        {
                            id : "3004001",
                            text : "销售配比",
                            leaf : true,
                            url : "saleMatch"
                        },
                        {
                            id : "3004002",
                            text : "运输配比",
                            leaf : true,
                            url : "transMatch"
                        }
                    ]
                }
            ]
        },
        {
            id : "04",
            text : "收入分析",
            children : [
                {
                    id : "4001",
                    text : "销售分析",
                    leaf : false,
                    children : [
                        {
                            id : "4001001",
                            text : "第三方支付收款分析",
                            leaf : true,
                            url : "psBankAnlysis"
                        }
                    ]
                },
                {
                    id : "5001",
                    text : "数据分析小组",
                    leaf : false,
                    children : [
                        {
                            id : "5001001",
                            text : "分公司收入统计",
                            leaf : true,
                            url : "companyIncome"
                        },
                        {
                            id : "5001002",
                            text : "航班收入统计",
                            leaf : true,
                            url : "flightRevIncome"
                        },
                        {
                            id : "5001003",
                            text : "销售渠道报表",
                            leaf : true,
                            url : "ptSaleChannel"
                        },
                        {
                            id : "5001004",
                            text : "头等舱明细",
                            leaf : true,
                            url : "ptFirstGradeDetail"
                        }
                    ]
                }
            ]
        },
        {
            id : "05",
            text : "数据处理中心",
            children : [
                {
                    id : "5001",
                    text : "外部数据总线",
                    leaf : false,
                    children : [
                        {
                            id : "5001001",
                            text : "销售任务单",
                            leaf : false,
                            children : [
                                        {
                                            id : "5001001001",
                                            text : "销售核收",
                                            leaf : true,
                                            url : "psTaskInput"
                                        },
                                        {
                                            id : "5001001002",
                                            text : "销售接收",
                                            leaf : true,
                                            url : "psTaskReceive"
                                        },
                                        {
                                            id : "5001001003",
                                            text : "销售票证录入",
                                            leaf : true,
                                            url : "psTicketInput"
                                        }
                                    ]
                        },
                        {
                            id : "5001002",
                            text : "运输任务单",
                            leaf : false,
                            children : [
                                        {
                                            id : "5001002001",
                                            text : "运输核收",
                                            leaf : true,
                                            url : "ptTaskInput"
                                        },
                                        {
                                            id : "5001002002",
                                            text : "运输接收",
                                            leaf : true,
                                            url : "ptTaskReceive"
                                        },
                                        {
                                            id : "5001002003",
                                            text : "运输票证录入",
                                            leaf : true,
                                            url : "ptTicketInput"
                                        }
                                    ]
                        },
                        {
                            id : "5001003",
                            text : "离线数据管理",
                            leaf : true,
                            url : "downfileconfig"
                        },
                        {
                            id : "5001004",
                            text : "文件解析情况",
                            leaf : true,
                            url : "fileParseStatus"
                        }
                    ]
                }
            ]
        },
        {
            id : "06",
            text : "运价管理",
            children : [
                {
                    id : "6001",
                    text : "运价基础管理",
                    leaf : false,
                    children : [
                        {
                            id : "6001001",
                            text : "TTBS导入",
                            leaf : true,
                            url : "ttbsImport"
                        },
                        {
                            id : "6001002",
                            text : "RATD导入",
                            leaf : true,
                            url : "ratdImport"
                        }
                    ]
                },
                {
                    id : "6002",
                    text : "运价计算引擎",
                    leaf : true,
                },
                {
                    id : "6003",
                    text : "运价分摊引擎",
                    leaf : false,
                    children : [
                        {
                            id : "6003001",
                            text : "分摊系数(PMP)",
                            leaf : true,
                            url : "sharingCoefficient"
                        },
                        {
                            id : "6003002",
                            text : "基础金额",
                            leaf : true,
                            url : "baseAmount"
                        },
                        {
                            id : "6003003",
                            text : "约束条件",
                            leaf : true,
                            url : "constraintCondition"
                        },
                        {
                            id : "6003004",
                            text : "强制折扣",
                            leaf : true,
                            url : "forceDiscount"
                        },
                        {
                            id : "6003005",
                            text : "最低分摊比例",
                            leaf : true,
                            url : "miniShareRatio"
                        },
                        {
                            id : "6003006",
                            text : "代码共享",
                            leaf : true,
                            url : "codeShare"
                        },
                        {
                            id : "6003007",
                            text : "航段距离(TPM)",
                            leaf : true,
                            url : "legDistance"
                        },
                        {
                            id : "6003008",
                            text : "SPA运价政策",
                            leaf : true,
                            url : "spaFareMode"
                        },
                        {
                            id : "60030009",
                            text : "预期价格维护",
                            leaf : true,
                            url : "ptExpectRev"
                        },
                        {
                            id : "60030010",
                            text : "比例分摊表",
                            leaf : true,
                            url : "ratioShare"
                        }
                    ]
                },
                {
                    id : "6004",
                    text : "税费引擎",
                    leaf : false,
                    children : [
                        {
                            id : "6004001",
                            text : "税费协议",
                            leaf : true,
                            url : "taxFeePolicy"
                        }
                    ]
                },
                {
                    id : "6005",
                    text : "退改签引擎",
                    leaf : true,
                }
            ]
        },      
        {
            id : "07",
            text : "账款管理",
            children : [

            ]
        },
        {
            id : "08",
            text : "账务管理",
            children : [
                {
                    id : "8001001",
                    text : "结转科目监控",
                    leaf : true,
                    url : "carryoverMonitor"
                },
                {
                    id : "8001002",
                    text : "结转科目调整",
                    leaf : true,
                    url : "carryoverAdjust"
                },
                {
                    id : "8001003",
                    text : "账务凭证",
                    leaf : true,
                    url : "payAccountingVouchers"
                }
            ]
        },
        {
            id : "09",
            text : "质量平台",
            children : [
                {
                    id : "9001",
                    text : "工作台",
                    leaf : false,
                    children : [
                        {
                            id : "9001001",
                            text : "工作量统计",
                            leaf : true,
                            url : "workloadStatistic"
                        }
                    ]
                },
            ]
        },
    ];