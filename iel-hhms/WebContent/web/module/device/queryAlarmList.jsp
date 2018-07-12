<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/commons.jsp"%>
<style type="text/css">
</style>
</head>
<body>
     <!-- 菜单栏  -->
	<div class="breadLine displayNone">
		<ul class="breadcrumbs auto-navigation" menu-code="${menuCode}"></ul>
	</div>
	<!-- 主页面 -->
	<div class="wrapper grid-s6m0">
		<div class="col-main">
			<div class="main-wrap"
				style="overflow-y: inherit;">
				
				<!-- 查询条件  -->
				<div class="fluid filterForm">
					<form id="filterForm" action="" method="post"
						class="form-horizontal" history="true">
						<input type="hidden" id="treeId" name="queryEntity.treeId" value="1"/>
						<ul class="formRow" style="padding: 0px">
							<li class="control-group auto-by-filter"><label
								class="control-label">设备编号：</label>
								<div class="controls">
									<input type="text" name="queryEntity.str1">
								</div></li>
							<li class="control-group auto-by-filter" style="display:none"><label
								class="control-label">名称：</label>
								<div class="controls">
									<input type="text" name="queryEntity.str2">
								</div></li>
						   <li class="control-group auto-by-filter"><label
								class="control-label">设备类型：</label>
								<div class="controls">
								  <select id="type" name="queryEntity.str3">
							        <option value="-1">---全部---</option>
							        <c:forEach items="${deviceTypeMap}" var="tList">
                                      <option value="${tList.key}">${tList.value}</option>
                                    </c:forEach>	
							      </select>
								</div></li> 
						</ul>
						<div class=btn-set>
							<a class="buttonM bBlue" type="button" id="data_filtering">查询</a>
						</div>
					</form>
				</div>
                <!-- 数据列表  -->
                 <div class="datatable">
			         <table id="table"> </table>
			         <div id="dataTablePage"></div>
		          </div>
			</div>
		</div>
		<!-- 左侧树 -->
		<div class="col-sub" >
			<div class="treeview">
				<form id="treeForm" action="">
					<input type="hidden" id="topGroupId" name="topGroupId"
						value="${topGroupId}">
					<!-- 存放groupId -->
				</form>
				<ul id="zTreea" class="ztree" style="margin: 0px 10px"></ul>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/queryAlarmList.js"></script>
</body>
</html>