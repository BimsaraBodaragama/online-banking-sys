function displayChart(data) {

var dataPoints = [];

for (var i = 0; i < data.length; i++) {
		dataPoints.push({
			x: new Date(data[i].x),
			y: data[i].y
		});
	}

var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	zoomEnabled: true,

	axisX: {
	    title: "Payment Date",
		crosshair: {
			enabled: true,
      snapToDataPoint: true,
			valueFormatString: "DD MMM YYYY"
		}
	},
	axisY: {
		title: "Payment Amount (LKR)",
		crosshair: {
			enabled: true,
			snapToDataPoint: true,
			valueFormatString: "LKR #,##0.00"
		}
	},
	data: [{
		type: "line",
		xValueFormatString: "DD MMM YY",
		yValueFormatString: "LKR #,##0.00",
		xValueType: "dateTime",
		dataPoints: dataPoints
	}]
});

	chart.render();

}

$( "#chartByPaymentDate" ).click(function() {
//PaymentDate
    var url = location.href;

    var paymentDateFrom = $('#paymentDateFromChart').val();
    paymentDateFrom = paymentDateFrom.replaceAll('/','-');
    var paymentDateTo = $('#paymentDateToChart').val();
    paymentDateTo = paymentDateTo.replaceAll('/','-');
    if(paymentDateFrom=="" || paymentDateTo==""){
    if(paymentDateFrom==""){alert("To Get Payment Chart, Please Enter The Payment Date From First");}
    if(paymentDateTo==""){alert("To Get Payment Chart, Please Enter The Payment Date Up to First");}
    }else{
        //rIssueDate = formatSetterYYtoYYYY(rIssueDate);
        //var date = setSearchPathVariable(rIssueDate);
        var date = paymentDateFrom+"="+paymentDateTo;
        console.log( "searchByIssueDate Handler for .click() called." + " from " + paymentDateFrom + " to " + paymentDateTo);

        var newUrl;
        if(url.split('/').length<=4){
            newUrl = url + "/paymentChart/"+date;
        }else{
            newUrl = "../paymentChart/"+date;
        }

        $.ajax
                    ({
                        type: "GET",
                        url: newUrl,
                        dataType: 'json',
                        contentType: 'application/json',


                        success: function (obj) {
                            console.log("Ajax passed!");

                            displayChart(obj);
                            console.log("Ajax was success")


                        },
                        error: function(xhr, ajaxOptions, thrownError) {
                            if(JSON.parse(xhr.responseText).sucess == false) {
                                window.location.href = "/error/error-404";
                            }
                        }
                    })

    }

});