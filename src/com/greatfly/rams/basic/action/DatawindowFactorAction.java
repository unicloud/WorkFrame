package com.greatfly.rams.basic.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.greatfly.common.action.BaseAction;
import com.greatfly.common.annotation.Description;
import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.StringUtil;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;
import com.greatfly.rams.basic.ucc.DatawindowFactorService;
import com.greatfly.rams.basic.vo.DatawindowFactorVo;

/**
 * 数据窗配置action
 * @author huangqb
 * 2017-01-12 15:47:18
 */
@Scope("prototype")
@Controller
@Description("数据窗配置Action")
public class DatawindowFactorAction extends BaseAction {
	private static Logger log = Logger.getLogger(DatawindowFactorAction.class);
	@Resource
	private DatawindowFactorService datawindowFactorService;
	//分页数据集
	private PageSupport<DatawindowFactorVo> ps;
	//查询条件
	private DatawindowFactorVo datawindowFactorVo;
	//编辑对象
	private MdDatawindowFactor mdDatawindowFactor;
	
	@Description("查询数据窗信息")
	public void queryDwInfo() {
		datawindowFactorVo = getRequestVo(DatawindowFactorVo.class);
		Long pkid = Long.valueOf(request.getParameter("pkid"));
		MdDatawindowFactor aa=datawindowFactorService.get(pkid);
		String jsonString = CommonMsgOutput.getResponseJson(true, 0, aa, "OK", "QUERY");
		output(jsonString,log);
	}

	/**
	 * 查询界面初始化
	 * @return 查询界面
	 */
	public String init() {
		datawindowFactorVo = new DatawindowFactorVo();
		//默认查询条件
		
		return list();
	}
	
	/**
	 * 查询
	 * @return 查询界面
	 */
	public String list() {
		ps = initPs();
		try {
			datawindowFactorService.list(ps, datawindowFactorVo);
		} catch (Exception e) {
			log.error("查询异常", e);
			request.setAttribute("msg", "查询异常：" + e.getMessage());
		}
		
		return "list";
	}
	
	/**
	 * 新增/编辑
	 * @return 编辑页面
	 */
	public String edit() {
		String idStr = request.getParameter("id");
		try {
			if (StringUtil.isNotBlank(idStr)) { //编辑
				Long idLng = Long.valueOf(idStr);
				mdDatawindowFactor = datawindowFactorService.get(idLng);
				
			} else { //新增
				mdDatawindowFactor = datawindowFactorService.getDefaultModel();				
			}
		} catch (Exception e) {
			log.error("新增/编辑异常", e);
			request.setAttribute("msg", "新增/编辑异常：" + e.getMessage());
		}
		
		return "edit";
	}
	
	/**
	 * 保存
	 * @return 编辑页面
	 */
	public String save() {
		try {
			datawindowFactorService.save(mdDatawindowFactor);
			request.setAttribute("msg", "保存成功");
		} catch (Exception e) {
			log.error("保存异常", e);
			request.setAttribute("msg", "保存异常：" + e.getMessage());
		}
		
		return "edit";
	}
	
	/**
	 * 删除
	 * @return 查询界面
	 */
	public String delete() {
		String ids = request.getParameter("ids");
		try {
			datawindowFactorService.delete(ids);
			request.setAttribute("msg", "删除成功!");
			
		} catch (Exception e) {
			log.error("删除异常", e);
			request.setAttribute("msg", "删除异常：" + e.getMessage());
		}
		
		return list();
	}
	
	// getter && setter
	
	public PageSupport<DatawindowFactorVo> getPs() {
		return ps;
	}

	public void setPs(PageSupport<DatawindowFactorVo> ps) {
		this.ps = ps;
	}
	
	public DatawindowFactorVo getDatawindowFactorVo() {
		return datawindowFactorVo;
	}

	public void setDatawindowFactorVo(DatawindowFactorVo datawindowFactorVo) {
		this.datawindowFactorVo = datawindowFactorVo;
	}
	
	public MdDatawindowFactor getMdDatawindowFactor() {
		return mdDatawindowFactor;
	}

	public void setMdDatawindowFactor(MdDatawindowFactor mdDatawindowFactor) {
		this.mdDatawindowFactor = mdDatawindowFactor;
	}
	
}