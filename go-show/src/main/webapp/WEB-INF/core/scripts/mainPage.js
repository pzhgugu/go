/**
 * @require common:scripts/global.js
 * @require core:styles/mainPage.css
 */

 function opentab(id,name,uri)
  {
  	var ids = window.parent.frameTabbar.getAllTabs();
			for (var q=0; q<ids.length; q++) {
			    if(ids[q]==id){
			    	window.parent.frameTabbar.tabs(ids[q]).setActive();
			    	return;
			    }
			}
  	window.parent.frameTabbar.addTab(id, name, null, null, true);
	window.parent.frameTabbar.tabs(id).attachURL(uri);
  	
  }
  
  function logout()
  {
  	window.parent.location.href = GLOBAL.S.URL+"/j_spring_security_logout";
  }
  
  function get_time()
  {
    var date=new Date();
    var year="",month="",day="",week="",hour="",minute="",second="";
    year=date.getFullYear();
    month=add_zero(date.getMonth()+1);
    day=add_zero(date.getDate());
    week=date.getDay();
    switch (date.getDay()) {
    case 0:val="星期天";break
    case 1:val="星期一";break
    case 2:val="星期二";break
    case 3:val="星期三";break
    case 4:val="星期四";break
    case 5:val="星期五";break
    case 6:val="星期六";break
      }
    hour=add_zero(date.getHours());
    minute=add_zero(date.getMinutes());
    second=add_zero(date.getSeconds());
    document.getElementById("timetable").innerHTML=" "+hour+":"+minute+":"+second;
  }

  function add_zero(temp)
  {
    if(temp<10) return "0"+temp;
    else return temp;
  }
