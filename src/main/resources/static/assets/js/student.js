$( "#searchByAdmissionNo" ).click(function() {
//studentIid
  var sAdmissionNo = $('#studentAdmissionNo').val();
  if(sAdmissionNo==""){alert("To Search By Student Admission No, Please Enter the Student Admission No First");
  }else{
  console.log( "searchByAdmissionNo Handler for .click() called." +sAdmissionNo);
  window.location.href = "../../student/by-admissionNo/"+sAdmissionNo;
  }
});


$( "#searchByName" ).click(function() {
//studentName
  var studentNameText = $('#studentName').val();
  if(studentNameText==""){alert("To Search By Student Name, Please Enter the Student Name First");
  }else{
  console.log( "searchByName Handler for .click() called." +studentNameText);
  window.location.href = "../../student/by-name/"+studentNameText;
  }
});