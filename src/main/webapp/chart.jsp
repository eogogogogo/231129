<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
//let serverData;
$(function(){
		$.ajax({
			method: 'get',  //type
			url : "chartfrm",
			dataType : 'json',
			success : function(data) {
				console.log(data);
				let laneData=data.laneList;
				let champData=data.champList;
				let lane=$('#lane');
				$(laneData).each(function(idx,value) {
					$('<option value='+value+'>').text(value).appendTo(lane);
				});
				let cham=$('#cham');
				$(champData).each(function(idx,value) {
					$('<option value='+champData[idx].cid+'>').text(champData[idx].cname).appendTo(cham);
				});
			},
			error : function(err) {
				console.log(err);
			}
		}); //ajax End

	$('#btn').click(function() {
		$.ajax({
			url : 'winrate', // ?lane='$('#lane').val()+"&cid=100",
			data: {lane :$('#lane').val()},
			dataType : 'json', //html(text), json, // xml, jsonp
			success : function(winRate) {
				console.log("1: ",winRate);
				showChart(winRate);
			},
			error : function(err) {
				console.log(err);
			}
		}); //ajax End
		
	});//click End
}); //ready End	
//구글 챠트 라이브러리
function showChart(winRate){
	google.charts.load('current', {'packages' : [ 'corechart' ]	});
	
	google.charts.setOnLoadCallback(drawVisualization);
		
	function drawVisualization() {
				
			/* let arr=[
		         ['챔피언이름', '승률',{ role: 'style'}, {role:'annotation'}],
		         ['자야', 66.7, 'color:yellow', '66.7%'],            // RGB value
		         ['칸드레', 61.5,'blue','61.5%'],            // English color name
		         ['자보', 60, 'green','60%'],
	         ]; */	
	         
	         const color=['yellow','blue','green'];  //챠트 색깔
	         let arr=[];
	         arr.push(['챔피언이름', '승률',{ role: 'style'}, {role:'annotation'}]);
	          for(let elem of winRate){
	        	  elem[1]=parseFloat(elem[1]);  //"66.7"-->66.7
	        	            //elem[2]  , elem[3] 에 추가    
	        	  elem.push(color.pop(), elem[1]+'%'); //'yellow', '66.7%' 
	        	  arr.push(elem);   //배열요소를 arr에 추가
	        	  console.log(elem);
	           }
	          console.log("2: ", arr);
			let data = google.visualization.arrayToDataTable(arr);
			let options = {
				title : '승률이 높은 챔피언',
				vAxis : {
					title : '승률'
				},
				hAxis : {
					title : '챔피언이름'
				},
				seriesType : 'bars'
				//is3D: true,    //PieChart 일때
				//series: {5: {type: 'line'}}
			};
			let chart = new google.visualization.ComboChart(document.getElementById('chart_div'));

			chart.draw(data, options);
	}  //drawVisualization End
}


</script>

</head>
<body>
	<select id="lane" name="lane"></select>
	<select id="cham" name="cham"></select>
	<button id="btn" type="button">전송</button>
	<div id="chart_div" style="width: 900px; height: 900px;"></div>
	
	</body>
</html>