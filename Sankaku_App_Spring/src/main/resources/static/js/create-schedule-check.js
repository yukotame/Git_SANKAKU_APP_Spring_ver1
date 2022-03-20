'use strict';
{


	var elmScheduleDate1 = document.getElementById("ID_SCHEDULE_DATE1");

	var elmADD = document.getElementById("ID_ADD");
	var elmScheduleName = document.getElementById("ID_SCHEDULE_NAME");
	var elmScheduleDate = document.getElementsByClassName('CLASS_SCHEDULE_DATE');
	var date_alert=true;
	//alert("チェック");
	//console.log("コンソールテスト");
	elmADD.onclick = function(){

	  var canSubmit = true;
	  var len = elmScheduleDate.length;
	 console.log(len);
	  if(elmScheduleName.value == "" ){
		    alert("スケジュール名を入力してください。");
		    canSubmit = false;
	  }

	  //日付が１カ所入力されていればOK。入力がなければアラート出力
	  for (let i = 0; i < len; i++){
		  if(elmScheduleDate[i].value != "" ){
			    date_alert= false;
		  }
	  }

	  if(date_alert){
		  alert("日付を入力してください。");
		    canSubmit = false;
	  }


	  return canSubmit;
	}

}
