'use strict';
{

	//const btn = document.getElementById("ID_SCHEDULE_ID");

	const SearchBtn = document.getElementById("ID_SERCH");
	const reg = new RegExp(/^([0-9]{0,5})$/);


	SearchBtn.onclick = function(){

	  var elmScheduleId   = document.getElementById("ID_SCHEDULE_ID");
	  var canSubmit = true;

	  if(elmScheduleId.value == "" ){
	    alert("「入力がありません。５桁以内の数字を入力していください」");
	    canSubmit = false;
	  }

	  if(!reg.test(elmScheduleId.value)){
		    alert("「５桁以内の数字を入力していください」");
		    canSubmit = false;
	  }

	  return canSubmit;
	}
}