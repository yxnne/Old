package com.sws.service.imp;import java.util.ArrayList;import java.util.List;import org.apache.commons.lang3.StringUtils;import org.hibernate.criterion.DetachedCriteria;import org.hibernate.criterion.Order;import org.hibernate.criterion.Restrictions;import org.springframework.beans.factory.annotation.Autowired;import com.gk.extend.hibernate.entity.DataStatusType;import com.gk.extend.hibernate.entity.QueryEntity;import com.gk.extend.hibernate.template.PageHibernateTemplate;import com.sws.dao.SignalLogDao;import com.sws.model.SignalLog;import com.sws.service.SignalLogService;import com.sys.core.util.bean.Page;//@Service("staffInfoService")public class SignalLogServiceImpl implements SignalLogService {    	@Autowired    private SignalLogDao signalLogDao;	@Autowired	private PageHibernateTemplate pageHibernateTemplate;		@Override	public Page pageByEvent(QueryEntity queryEntity, int start, int limit,String sortname,String sortorder) {		//queryEntity.		// TODO Auto-generated method stub		if(sortname.equals("eventTypeName")){			sortname="eventType";		}else if(sortname.equals("deviceTypeName")){			sortname="deviceType";		} //排序名称转变		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SignalLog.class);		detachedCriteria.add(Restrictions.eq("status", DataStatusType.NORMAL.getNumber()));		List<String> event = new ArrayList<String>();		if (queryEntity.getStr1() != null) {            String[] eventArray = queryEntity.getStr1().split(",");// 事件类型            for (String info : eventArray) {                event.add(info);            }            detachedCriteria.add(Restrictions.in("eventType", event));        }		List<String> deviceType = new ArrayList<String>();		if (queryEntity.getStr2() != null) {            String[] deviceTypeArray = queryEntity.getStr2().split(",");// 设备类型            for (String info : deviceTypeArray) {            	deviceType.add(info);            }            detachedCriteria.add(Restrictions.in("deviceType", deviceType));        }		if(StringUtils.isNotBlank(queryEntity.getStr3())){			//detachedCriteria.add(Restrictions.like("deviceNo", "%"+queryEntity.getStr3()+"%"));			detachedCriteria.add(Restrictions.or(Restrictions.like("deviceNo", "%"+queryEntity.getStr3()+"%"), Restrictions.like("rfid", "%"+queryEntity.getStr3()+"%")));		}		if (queryEntity.getStartTime() != null				&& queryEntity.getEndTime() != null) {			detachedCriteria.add(Restrictions.between("createTime",					queryEntity.getStartTime(), queryEntity.getEndTime()));		}		if (sortorder.toLowerCase().equals("asc")) {			detachedCriteria.addOrder(Order.asc(sortname));		} else {			detachedCriteria.addOrder(Order.desc(sortname));		};		Page page = pageHibernateTemplate.pageByDetachedCriteria(detachedCriteria, start, limit);		return page;	}}