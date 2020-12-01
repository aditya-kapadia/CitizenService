var ref=firebase.database().ref().child("users");
var userid;
/*ref.once('value',function(snapshot){
	snapshot.forEach(function(childSnapshot)
	{
		userid = childSnapshot.key;
		$("#table_body").append("<button data-id="+userid+">"+userid+"</button><br>");
	

});
*/

ref.on("child_added",snap=>{
	userid = snap.key;
	var x,y,z;
	//var referenceemail = ref.child(userid).child("emailis").child("0");
	var referenceemail = ref.child(userid).child("images").child("potholes").child("1");
	referenceemail.once('value',function(snapshot){
		var x = snapshot.child("user_id").val();
		if(x==null){
			var referenceemail1 = ref.child(userid).child("images").child("sanitation").child("1");
			referenceemail1.once('value',function(snapshot){
			var x = snapshot.child("user_id").val();	
			$("#table_body").append("<br><button data-id="+userid+">"+x+"</button><br>");	
		});
		}
		else{
			$("#table_body").append("<br><button data-id="+userid+">"+x+"</button><br>");
		}
	/*var referenceemail = ref.child(userid).child("images").child("sanitation").child("1");
	referenceemail.once('value',function(snapshot){
		var y = snapshot.child("user_id").val();
		//console.log(y);
	});
	if(x==y)
	{
		z = x;
	}
	else if(x==null){
		z=y;
	}
	else if(y==null){
		z=x;
	}
	//console.log(z);*/
		
});
});
	$('#table_body').on('click','button[data-id]',function(e){
	var userkey = $(this).attr('data-id');
	console.log(userkey);
	alert(userkey);
	localStorage.setItem("userdata",userkey);
	window.location.href = "testingdata.php";
	
});