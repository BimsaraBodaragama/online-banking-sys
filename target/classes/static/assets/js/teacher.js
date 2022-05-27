$( "#searchByEmployeeNo" ).click(function() {
//TeacherEmployeeNo
  var tEmployeeNo = $('#teacherEmployeeNo').val();
  if(tEmployeeNo==""){alert("To Search By Employee No, Please Enter the Employee No First");
  }else{
  console.log( "searchByAdmissionNo Handler for .click() called." +tEmployeeNo);
  window.location.href = "../../teacher/by-employeeNo/"+tEmployeeNo;
  }
});


$( "#searchByName" ).click(function() {
//teacherName
  var teacherNameText = $('#teacherName').val();
  if(teacherNameText==""){alert("To Search By Teacher Name, Please Enter the Teacher Name First");
  }else{
  console.log( "searchByName Handler for .click() called." +teacherNameText);
  window.location.href = "../../teacher/by-name/"+teacherNameText;
  }
});