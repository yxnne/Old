// DOM加载完后执行$(function() {	});  //查询ap编号$("#data_get_no").click(function(){	var ip = $("#ip").val();	$.ajax({		url : 'apNoGet.action',		data : {			ip : ip     	},     	beforeSend : function(){			loader.show('正在处理，请稍后...');		},		success : function(data, callback){			if(data.code==0){				$("#noD").attr("value",data.apNo);				$("#no").attr("value",data.apNo);				jAlert("执行成功!", '提示', 'attention');			}else{				jAlert("执行失败!", '错误', 'error');			}		},		dataType : "json",		complete : function() {			loader.hide();		}	});	});//查询ap编码$("#data_get_code").click(function(){	var ip = $("#ip").val();	$.ajax({		url : 'apCodeGet.action',		data : {			ip : ip     	},     	beforeSend : function(){			loader.show('正在处理，请稍后...');		},		success : function(data, callback){			if(data.code==0){				$("#postCodeD").attr("value",data.postCode);				$("#postCode").attr("value",data.postCode);				$("#hospitalNoD").attr("value",data.hospitalNo);				$("#hospitalNo").attr("value",data.hospitalNo);				$("#departNoD").attr("value",data.departNo);				$("#departNo").attr("value",data.departNo);				jAlert("执行成功!", '提示', 'attention');			}else{				jAlert("执行失败!", '错误', 'error');			}		},		dataType : "json",		complete : function() {			loader.hide();		}	});});//设置ap编号$("#data_set_no").click(function(){	var ip = $("#ip").val();	var apNo = $("#no").val();	$.ajax({		url : 'apNoSet.action',		data : {			ip : ip,			apNo : apNo     	},     	beforeSend : function(){			loader.show('正在处理，请稍后...');		},		success : function(data, callback){			if(data.code==0){				$("#noD").attr("value",data.apNo);				$("#no").attr("value",data.apNo);				jAlert("执行成功!", '提示', 'attention');			}else{				jAlert("执行失败!", '错误', 'error');			}		},		dataType : "json",		complete : function() {			loader.hide();		}	});	});//设置ap编码$("#data_set_code").click(function(){	var ip = $("#ip").val();	var postCode = $("#postCode").val();	var hospitalNo = $("#hospitalNo").val();	var departNo = $("#departNo").val();	$.ajax({		url : 'apCodeSet.action',		data : {			ip : ip,			postCode : postCode,			hospitalNo : hospitalNo,			departNo : departNo     	},     	beforeSend : function(){			loader.show('正在处理，请稍后...');		},		success : function(data, callback){			$("#postCodeD").attr("value",data.postCode);			$("#postCode").attr("value",data.postCode);			$("#hospitalNoD").attr("value",data.hospitalNo);			$("#hospitalNo").attr("value",data.hospitalNo);			$("#departNoD").attr("value",data.departNo);			$("#departNo").attr("value",data.departNo);			jAlert("执行成功!", '提示', 'attention');		},		dataType : "json",		complete : function() {			loader.hide();		}	});	});