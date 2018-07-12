package com.sws.common.baseAction;import java.text.NumberFormat;import java.util.Date;import java.util.List;import java.util.Locale;import java.util.Map;import java.util.TreeMap;import org.hibernate.criterion.Order;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import com.alibaba.fastjson.JSON;import com.alibaba.fastjson.JSONObject;import com.gk.essh.exception.ProgramException;import com.gk.essh.util.ContentType;import com.gk.essh.util.ServletUtil;import com.gk.essh.util.errorcode.ErrorCode;import com.gk.essh.util.errorcode.ErrorCodeUtil;import com.gk.essh.www.session.GkSession;import com.gk.extend.hibernate.entity.QueryEntity;import com.gk.sws.cache.core.common.CacheConstants.DATA_TYPE;import com.gk.sws.cache.manager.DataLoadException;import com.gk.sws.cache.manager.DataManager;import com.opensymphony.xwork2.ActionContext;import com.opensymphony.xwork2.util.LocalizedTextUtil;import com.sws.common.MobileSessionProcessor;import com.sws.common.statics.SysStatics;import com.sws.common.until.CommUtil;import com.sws.common.until.StringUtil;import com.sws.dao.DeviceInfoDao;import com.sws.dao.ParameterInfoDao;import com.sws.dao.StaffInfoDao;import com.sws.model.DeviceInfo;import com.sws.model.GroupTree;import com.sws.model.ParameterInfo;import com.sws.model.StaffInfo;import com.sws.model.UserInfo;import com.sws.service.GroupTreeService;import com.sws.service.UserInfoService;import com.sys.core.util.DateFormatUtils;import com.sys.core.util.JsonUtils;import com.sys.core.util.StringUtils;/** * ACTION鐨勫熀绫伙紝鏈変竴浜涙柟渚夸娇鐢ㄧ殑鏂规硶銆� *  * @author truk 2015-9-21 涓婂崍10:23:25 * @version V1.0 * @param */public class BaseAction<T> extends com.gk.essh.www.action.BaseAction<T> {	private static final long serialVersionUID = 1536071082975285559L;	private Logger log = LoggerFactory.getLogger(BaseAction.class);	@Autowired	protected ParameterInfoDao parameterInfoDao;	@Autowired	protected StaffInfoDao staffInfoDao;	@Autowired	protected DeviceInfoDao deviceInfoDao;	@Autowired	protected UserInfoService userInfoService;	@Autowired	protected GroupTreeService groupTreeService;	protected String random;	// 接收框架中的传递参数	protected String _time;	protected String time;	protected int _page = 1;	// 分页参数，当前页码	protected int rowNum = 10;	// 分页参数，每页大小	protected String sortname;	protected String sortorder;	protected String _ajax_submit;	protected String isDialog;	protected String sessionID;	protected DataManager dataManager;	protected Boolean isTreeOpen;	protected Integer topGroupId;	protected GroupTree groupTree;	protected GroupTree parentGroup;	protected String ids;          	protected String groupTreeId;  	protected QueryEntity queryEntity = new QueryEntity();	//ajax请求对应参数	protected String treeId;	protected String startTime;	protected String endTime;	protected String staffType;	protected String depart;	protected String staffId;	protected MobileSessionProcessor mobileSessionProcessor;		/**	 * 查询所有的人员类别	 * @return	 */	protected Map<String, String> getJobTypeMap(){		Map<String, String> jobTypeMap = new TreeMap<String, String>();		List<ParameterInfo> list =  parameterInfoDao.findBy("type", 1);		for(ParameterInfo info:list){			if(info.getKey()!=null&&info.getValue()!=null){				jobTypeMap.put(info.getKey(), info.getValue());			}		}		return jobTypeMap;	}		// 根据人员Id得到rfid（胸牌）	protected String getRfidByStaffId(Long staffId) {		String rfid = "";		DeviceInfo deviceInfo = deviceInfoDao.findUniqueBy("staffId", staffId);		if (deviceInfo != null && deviceInfo.getNo() != null) {			rfid = deviceInfo.getNo();		}		return rfid;	}		/**	 * rfid得到对应科室	 * 	 * @param rfid	 * @return	 */	protected String getDepartNameByRfid(String rfid) {		String departName = "该胸牌未关联员工";		DeviceInfo device = deviceInfoDao.findUniqueBy("no", rfid);		if (device != null) {			if (device.getStaffId() != null) {				StaffInfo staffInfo = staffInfoDao.findUniqueBy("id",device.getStaffId());				if (staffInfo != null) {					groupTree = groupTreeService.getById(staffInfo.getGroupTree().getId());					if (groupTree.getName() != null) {						departName = groupTree.getName();					}				}			}		}		return departName;	}		protected String getGroupTreeNameByID(Long id){		GroupTree groupTree=groupTreeService.getById(id);		if(groupTree!=null){			return groupTree.getName();		}		return "";	}		/**	 * rfid得到员工	 * 	 */	protected StaffInfo getStaff(String rfid) {		//String docName = "该胸牌未关联员工";		DeviceInfo device = deviceInfoDao.findUniqueBy("no", rfid);		if (device != null) {			if (device.getStaffId() != null) {				StaffInfo staffInfo = staffInfoDao.findUniqueBy("id",device.getStaffId());				return staffInfo;			}		}		return null;	}		/**	 * rfid得到对应医生名称	 * 	 * @param rfid	 * @return	 */	protected String getDocByRfid(String rfid) {		String docName = "该胸牌未关联员工";		DeviceInfo device = deviceInfoDao.findUniqueBy("no", rfid);		if (device != null) {			if (device.getStaffId() != null) {				StaffInfo staffInfo = staffInfoDao.findUniqueBy("id",device.getStaffId());				if (staffInfo != null) {					docName = staffInfo.getName();				}			}		}		return docName;	}		/**	 * rfid得到对应员工的类型名	 * @param rfid	 * @return	 */	protected String getStaffTypeName(String rfid) {		String staffTypeName = "该胸牌未关联员工";		DeviceInfo device = deviceInfoDao.findUniqueBy("no", rfid);		if (device != null) {			if (device.getStaffId() != null) {				StaffInfo staffInfo = staffInfoDao.findUniqueBy("id",device.getStaffId());				if (staffInfo != null) {					staffTypeName = SysStatics.jobTypeMap.get(staffInfo.getCategory());				}			}		}		return staffTypeName;	}		/**	 * rfid得到对应员工的类型	 * @param rfid	 * @return	 */	protected String getStaffType(String rfid) {		String staffType = "";		DeviceInfo device = deviceInfoDao.findUniqueBy("no", rfid);		if (device != null) {			if (device.getStaffId() != null) {				StaffInfo staffInfo = staffInfoDao.findUniqueBy("id",device.getStaffId());				if (staffInfo != null) {					staffType = staffInfo.getCategory();				}			}		}		return staffType;	}		/**	 * add by yxnne	 * deviceNo得到对应设备名称	 * @param deviceNo	 * @return deviceName	 */	protected String getNameByDeviceNo(String deviceNo,String type) {				DeviceInfo device = deviceInfoDao.findByNoType(deviceNo, type);		if(device!=null&&device.getName()!=null){			return device.getName();		}		return "";			}		protected DeviceInfo getDeviceInfoByNoType(String deviceNo,String type){		DeviceInfo device = deviceInfoDao.findByNoType(deviceNo, type);		return device;			}		// 计算百分比	protected String getPercent(Integer tem1, Integer tem2) {		String result = "0.0";		if (!tem2.equals(0)) {			NumberFormat numberFormat = NumberFormat.getInstance();			numberFormat.setMaximumFractionDigits(1);			result = numberFormat.format((float) tem1 / (float) tem2 * 100);		}		return result;	}		/**	 * 取得用户信息	 * @return	 */	protected UserInfo getUser() {		// 先看下内存中有没有	/*	UserInfo user = getFromMemoryStore(SysStatics.USER_INFO, UserInfo.class);		if (CommUtil.isNullOrEmpty(user)) {			// 没有的话去数据库中取			user = userInfoService.getById(Long.valueOf(getCommSession().getUserId()));			// 放入内存中			setToMemoryStore(SysStatics.USER_INFO, user);		}*/		UserInfo user = userInfoService.getById(Long.valueOf(getCommSession().getUserId()));		return user;	}		protected GkSession getCommSession() {		if (null != getSession()) {			return getSession();		}		return null;	}	/**	 * 把对象放入MemoryStore中	 * @param key	 * @param obj	 */	protected void setToMemoryStore(String key, Object obj) {		try {			dataManager.set(getCommSession().getSessionId() + ";" + key, JsonUtils.object2Json(obj), DATA_TYPE.OTHER);		}		catch (DataLoadException e) {			e.printStackTrace();		}	}		/**	 * 从MemoryStore中取对象	 * @param key	 * @param obj	 */	protected <J> J getFromMemoryStore(String key, Class<J> clazz) {		try {			String str = dataManager.get(getCommSession().getSessionId() + ";" + key);			if (!CommUtil.isNullOrEmpty(str)) {				return JSON.parseObject(str, clazz);			}		}		catch (DataLoadException e) {			e.printStackTrace();		}		return null;	}		/**	 * 得到排序，默认根据createTime倒序	 * @param sortname	 * @param sortorder	 * @return	 */	protected Order getOrder(String sortname, String sortorder) {		if (StringUtil.isNullOrEmpty(sortname) || StringUtil.isNullOrEmpty(sortorder)) {			return getOrder();			//sortname、sortorder均不为空时；		}else if (SysStatics.SORT_TYPE_ASC.equalsIgnoreCase(sortorder)) {			return Order.asc(sortname);		}else {			return Order.desc(sortname);		}	}	/**	 * 得到默认排序，根据createTime倒序	 * @param sortname	 * @param sortorder	 * @return	 */	protected Order getOrder() {		return Order.asc("createTime");	}	/**	 * 向页面写数据	 * 只返回操作结果和信息	 */	protected void writeResponse() {		JSONObject result = new JSONObject();		result.put("success", success);		result.put("message", msg);		writeResponse(result.toString());	}	/**	 * 向页面写数据	 * 返回对象(或集合)、操作结果和信息	 */	protected void writeResponse(Object obj) {		JSONObject jo = new JSONObject();		jo.put("success", success);		jo.put("message", msg);		if (!CommUtil.isNullOrEmpty(obj)) {			jo.put("data", obj);		}		writeResponse(jo.toString());	}	protected void writeResponse(String str) {		try {			ServletUtil.writeResponse(getResponse(), str, ContentType.HTML);		}		catch (Exception e) {			e.printStackTrace();			log.error("write response error", e);		}	}	/**	 * 校验前台传入的参数	 * @param values	 * @return	 */	protected boolean checkObjParams(Object... objs) {		for (Object obj : objs) {			if (CommUtil.isNullOrEmpty(obj)) {				success = Boolean.FALSE;				msg = "参数错误";				return false;			}		}		return true;	}	protected String getErrorInfo(ProgramException ex) {		String errorInfo = ex.getMessage();		if (!StringUtils.isEmpty(errorInfo)) {			return errorInfo;		}		else {			Locale locale = ActionContext.getContext().getLocale();			String logInfo = LocalizedTextUtil.findDefaultText("errorcode." + ex.getCode(), locale);			if(logInfo == null){				ErrorCode ec = ErrorCodeUtil.getGlobalErrorCode(ex.getClass().getSimpleName());				logInfo = ec.getName();				if(logInfo == null){					logInfo = "";				}				return logInfo;			}else if (ex.getArgs() == null || ex.getArgs().length <= 0) {				return logInfo;			} else {				int index = 0;				for (Object o : ex.getArgs()) {					logInfo = logInfo.replace("{" + index++ + "}", String.valueOf(o));				}			}			return String.format(logInfo, ex.getArgs());		}	}		public String formateDateYMD(Date date) {		return DateFormatUtils.format(date,				DateFormatUtils.DATE_FORMAT_MIN);	}	@SuppressWarnings("unused")	private GkSession getMobileSession() {		if (null == getSession()) {			return mobileGetSession();		}		else {			return getSession();		}	}		/**	 * 得到管理部门/科室	 */	protected List<String> getManageDepart() {		return groupTreeService.getManageDeparts();	}		/**	 * 移动端取得Session	 */	protected GkSession mobileGetSession() {		return mobileSessionProcessor.getSession(sessionID);	}	/**	 * 移动端创建Session	 */	protected void mobileSetSession(GkSession session, int expire) {		mobileSessionProcessor.setSession(session, expire);	}	/**	 * 移动端移除Session	 */	protected void mobileRemoveSession() {		mobileSessionProcessor.removeSession(sessionID);	}		public String getIsDialog() {		return isDialog;	}	public void setIsDialog(String isDialog) {		this.isDialog = isDialog;	}	public String getRandom() {		return random;	}	public void setRandom(String random) {		this.random = random;	}	public String get_time() {		return _time;	}	public void set_time(String _time) {		this._time = _time;	}	public int get_page() {		return _page;	}	public void set_page(int _page) {		this._page = _page;	}	public int getRowNum() {		return rowNum;	}	public void setRowNum(int rowNum) {		this.rowNum = rowNum;	}	public String getSortname() {		return sortname;	}	public void setSortname(String sortname) {		this.sortname = sortname;	}	public String getSortorder() {		return sortorder;	}	public void setSortorder(String sortorder) {		this.sortorder = sortorder;	}	public String get_ajax_submit() {		return _ajax_submit;	}	public void set_ajax_submit(String _ajax_submit) {		this._ajax_submit = _ajax_submit;	}	public void setDataManager(DataManager dataManager) {		this.dataManager = dataManager;	}	public String getSessionID() {		return sessionID;	}	public void setSessionID(String sessionID) {		this.sessionID = sessionID;	}	public Boolean getIsTreeOpen() {		return isTreeOpen;	}	public void setIsTreeOpen(Boolean isTreeOpen) {		this.isTreeOpen = isTreeOpen;	}	public Integer getTopGroupId() {		return topGroupId;	}	public void setTopGroupId(Integer topGroupId) {		this.topGroupId = topGroupId;	}	public GroupTree getGroupTree() {		return groupTree;	}	public void setGroupTree(GroupTree groupTree) {		this.groupTree = groupTree;	}	public GroupTree getParentGroup() {		return parentGroup;	}	public void setParentGroup(GroupTree parentGroup) {		this.parentGroup = parentGroup;	}	public String getTime() {		return time;	}	public void setTime(String time) {		this.time = time;	}	public String getIds() {		return ids;	}	public void setIds(String ids) {		this.ids = ids;	}	public String getGroupTreeId() {		return groupTreeId;	}	public void setGroupTreeId(String groupTreeId) {		this.groupTreeId = groupTreeId;	}	public QueryEntity getQueryEntity() {		return queryEntity;	}	public void setQueryEntity(QueryEntity queryEntity) {		this.queryEntity = queryEntity;	}	public String getTreeId() {		return treeId;	}	public void setTreeId(String treeId) {		this.treeId = treeId;	}	public String getStartTime() {		return startTime;	}	public void setStartTime(String startTime) {		this.startTime = startTime;	}	public String getEndTime() {		return endTime;	}	public void setEndTime(String endTime) {		this.endTime = endTime;	}	public String getStaffType() {		return staffType;	}	public void setStaffType(String staffType) {		this.staffType = staffType;	}	public String getDepart() {		return depart;	}	public void setDepart(String depart) {		this.depart = depart;	}	public String getStaffId() {		return staffId;	}	public void setStaffId(String staffId) {		this.staffId = staffId;	}	}