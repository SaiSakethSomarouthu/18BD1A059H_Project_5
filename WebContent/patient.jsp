<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Patient Portal</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<label for="level">Enter oxygen levels: &nbsp&nbsp&nbsp</label>
		<input class="form-control form-control-sm" type="text" name="level" id="level" style="display:inline-block; width:300px;margin-top:25px;" required>
		<br>
		<label for="temperature">Enter temperature: &nbsp&nbsp&nbsp&nbsp&nbsp</label>
		<input class="form-control form-control-sm" type="number" name="temperature" id="temperature" style="display:inline-block; width:300px;margin-top:25px;" required>
		<br>
		<label for="phonenumber">Enter phone number: &nbsp</label>
		<input class="form-control form-control-sm" type="number" name="phonenumber" id="phonenumber" style="display:inline-block; width:300px;margin-top:25px;" required>
		<br>
		<br>
		<button onclick="send()" class="btn btn-success btn-sm">Submit</button>
		<br>
		<br>
		<br>
		<table id="data" class="table">
			<thead>
				<tr>
					<th>Doctor</th>
					<th>Medicine</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<tr>
				</tr>
			</tbody>
		</table>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<script>
		var webSocket = new WebSocket("ws://localhost:8080/PatientMonitoring/EndPoint");
		webSocket.onmessage = function process(message){
			var jsonData = JSON.parse(message.data);
			if(jsonData.message != null){
				var details = jsonData.message.split(",");
				var row = document.getElementById("data").insertRow();
				if(details.length > 2){
					row.innerHTML = "<td>" + details[0] + "</td><td>" + details[1] + "</td><td>" + details[2] + "</td>";
				}
				else{
					alert("Doctor has summoned an ambulance");
					row.innerHTML = "<td>" + details[0] + "</td><td></td><td>" + details[1] + "</td>";
				}
			}
		}
		function send(){
			webSocket.send(level.value + "," + temperature.value + "," + phonenumber.value);
			level.value = "";
			temperature.value = "";
			phonenumber.value = "";
		}
	</script>
</body>
</html>