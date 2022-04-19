$( "#searchByGrade" ).click(function() {
//Grade
  var classGradeText = $('#schoolClass').val();
  if(classGradeText==""){alert("To Search By Grade, Please Enter the Grade First");
    }else{
  console.log( "searchByGrade Handler for .click() called." +classGradeText);
  window.location.href = "../../class/by-class-grade/"+classGradeText;
  }
});


$( "#searchByClassName" ).click(function() {
//ClassName
  var cName = $('#schoolClass').val();
  if(cName==""){alert("To Search By Class Name, Please Enter the Class Name First");
  }else{
  console.log( "searchByCLassName Handler for .click() called." +cName);
  window.location.href = "../../class/by-class-name/"+cName;
  }
});
