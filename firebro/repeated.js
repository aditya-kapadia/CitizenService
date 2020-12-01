var ref=firebase.database().ref().child("users");
ref.once('value',function(snapshot){
	var childArray=[];
	snapshot.forEach(function(childSnapshot)
	{
		
		childArray.push(childSnapshot.key);
		var childKey=childSnapshot.key;
var rootRef=firebase.database().ref().child("users").child(childKey).child("repeated");
rootRef.on("child_added",snap=>{
	var url=snap.child("url").val();
	/*var urr=url.link(url);*/
	var add=snap.child("ImageLocation").val();
	var cord=snap.child("LocationValues").val();
	var wd=snap.child("widthheight").val();
	var category=snap.child("output").val();
	var date=snap.child("imagedate").val();

	$("#table_body").append("<tr><td><a href='http://"+url+"' target = '_blank' </a>"+url+"</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td><button>Completed</button></td></tr>");

});
		var childData=childSnapshot.val();
		console.log(childArray.length);
		console.log(childKey);
	});
	
});