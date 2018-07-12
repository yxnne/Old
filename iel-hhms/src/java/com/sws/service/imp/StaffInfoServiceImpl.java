package com.sws.service.imp;import java.util.ArrayList;import java.util.List;import org.hibernate.criterion.DetachedCriteria;import org.hibernate.criterion.MatchMode;import org.hibernate.criterion.Order;import org.hibernate.criterion.Restrictions;import org.springframework.beans.factory.annotation.Autowired;import com.gk.extend.hibernate.entity.DataStatusType;import com.gk.extend.hibernate.entity.QueryEntity;import com.gk.extend.hibernate.template.PageHibernateTemplate;import com.sws.dao.StaffInfoDao;import com.sws.model.StaffInfo;import com.sws.service.StaffInfoService;import com.sys.core.util.CollectionUtils;import com.sys.core.util.StringUtils;import com.sys.core.util.bean.Page;//@Service("staffInfoService")public class StaffInfoServiceImpl implements StaffInfoService {    	@Autowired    private StaffInfoDao staffInfoDao;	@Autowired	private PageHibernateTemplate pageHibernateTemplate;		@Override	public StaffInfo getById(Long id) {		return staffInfoDao.get(id);	}	@Override	public void save(StaffInfo info) {		staffInfoDao.save(info);	}	@Override	public void update(StaffInfo info) {		staffInfoDao.merge(info);	}	@Override	public void deleteAll(String ids) {		if (StringUtils.isNotBlank(ids)) {            List<Long> idList = CollectionUtils.toLongList(ids);            if (CollectionUtils.isNotEmpty(idList)) {                for (Long id : idList) {                	staffInfoDao.deleteById(id);                }            }        }	}	@Override	public Page page(String departIds,QueryEntity queryEntity, int start, int limit,String sortname,String sortorder) {		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffInfo.class);		if(StringUtils.isNotBlank(queryEntity.getStr1())){			detachedCriteria.add(Restrictions.like("no", queryEntity.getStr1(),MatchMode.ANYWHERE));		}		if(queryEntity.getStr2()!=null){			detachedCriteria.add(Restrictions.like("name", queryEntity.getStr2(),MatchMode.ANYWHERE));		}		if (sortorder.toLowerCase().equals("asc")) {			detachedCriteria.addOrder(Order.asc(sortname));		} else {			detachedCriteria.addOrder(Order.desc(sortname));		};		List<Long> groupIds = new ArrayList<Long>();		if(StringUtils.isNotBlank(queryEntity.getTreeId())&&!queryEntity.getTreeId().equals("1")){			detachedCriteria.add(Restrictions.eq("groupTree.id", Long.valueOf(queryEntity.getTreeId())));		}else{			String[] array = departIds.split("\\*");			if(!departIds.equals("1")){//非最高级别的管理员				for(String dId:array){					groupIds.add(Long.valueOf(dId));				}			}			if(groupIds!=null&&groupIds.size()>0){				detachedCriteria.add(Restrictions.in("groupTree.id", groupIds));			}		}		Page page = pageHibernateTemplate.pageByDetachedCriteria(detachedCriteria, start, limit);		return page;	}	@Override	public Page page(List<Long> staffIds, int start, int limit, String sortname, String sortorder) {		if(sortname.equals("departName")){			sortname="groupTree.id";		}else if(sortname.equals("docType")){			sortname="category";		} //排序名称转变		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffInfo.class);		detachedCriteria.add(Restrictions.eq("status", DataStatusType.NORMAL.getNumber()));		if (staffIds != null && staffIds.size() > 0) {			detachedCriteria.add(Restrictions.in("id", staffIds));		}		if (sortorder.toLowerCase().equals("asc")) {			detachedCriteria.addOrder(Order.asc(sortname));		} else {			detachedCriteria.addOrder(Order.desc(sortname));		};		Page page = pageHibernateTemplate.pageByDetachedCriteria(detachedCriteria, start, limit);		return page;	}	@Override	public Page pageByDLevel(List<String> manageDepart, String departIds,			QueryEntity queryEntity, int start, int limit, String sortname,			String sortorder) {		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffInfo.class);		if(StringUtils.isNotBlank(queryEntity.getStr1())){			detachedCriteria.add(Restrictions.like("no", queryEntity.getStr1(),MatchMode.ANYWHERE));		}		if(queryEntity.getStr2()!=null){			detachedCriteria.add(Restrictions.like("name", queryEntity.getStr2(),MatchMode.ANYWHERE));		}		if (sortorder.toLowerCase().equals("asc")) {			detachedCriteria.addOrder(Order.asc(sortname));		} else {			detachedCriteria.addOrder(Order.desc(sortname));		};		List<Long> groupIds = new ArrayList<Long>();		if(StringUtils.isNotBlank(queryEntity.getTreeId())&&!queryEntity.getTreeId().equals("1")){			detachedCriteria.add(Restrictions.eq("groupTree.id", Long.valueOf(queryEntity.getTreeId())));		}else{			String[] array = departIds.split("\\*");			if(!manageDepart.contains(departIds)){//非管理部门				for(String dId:array){					groupIds.add(Long.valueOf(dId));				}			}			if(groupIds!=null&&groupIds.size()>0){				detachedCriteria.add(Restrictions.in("groupTree.id", groupIds));			}		}		Page page = pageHibernateTemplate.pageByDetachedCriteria(detachedCriteria, start, limit);		return page;	}	@Override	public List<StaffInfo> findByStaffNo(String staffNo) {		List<StaffInfo> staffInfos = staffInfoDao.findByStaffNo(staffNo);		return staffInfos;	}		@Override	public String findNameByRfid(String rfid) {		String name = staffInfoDao.findNameByRfid(rfid);		return name;	}}