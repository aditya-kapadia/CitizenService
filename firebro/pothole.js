var db=firebase.database();
var ref=firebase.database().ref().child("users");
ref.once('value',function(snapshot){
	var childArray=[];
	var test=0;
	snapshot.forEach(function(childSnapshot)
	{
		
		childArray.push(childSnapshot.key);
		var childKey=childSnapshot.key;
var rootRef=firebase.database().ref().child("users").child(childKey).child("potholes");
rootRef.on("child_added",snap=>{
	var url=snap.child("url").val();
	/*var urr=url.link(url);*/
	var add=snap.child("ImageLocation").val();
	var cord=snap.child("LocationValues").val();
	var wd=snap.child("widthheight").val();
	var category=snap.child("output").val();
	var date=snap.child("imagedate").val();
	var fuckme = rootRef.push().key;
	/*var potholeA=[];
	potholeA.push(snapshot.key);*/
	$("#table_body").append("<tr><td><a href='http://"+url+"' target = '_blank' </a>"+url+"</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td><button id = test>Completed</button></td></tr>");
var but = document.getElementById("test");
/*console.log(but.id);*/
but.id=test;
/*console.log(but.id);*/
$('#'+test).click(function(){
	/*console.log(test);*/
	console.log("Button chalra");
	console.log(but.id);
rootRef.once('value',function(snapshot){
	var potholekey=[];
	snapshot.forEach(function(childSnapshot){
		potholekey.push(childSnapshot.key);
		rootRef.child(potholekey[test]).update({completed:"REALTIME shit"});
	});
});
});
test++;

});
		var childData=childSnapshot.val();
/*		console.log(childArray.length);
		console.log(childKey);*/
	});
	
});