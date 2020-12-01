var potholecount,wastecount;
var upvotesRef = firebase.database().ref().child("ReportedPotholeIssues");
var upvotesRef1 = firebase.database().ref().child("ReportedSanitationIssues");
upvotesRef.once('value',function(snapshot){
		var potholecount = snapshot.numChildren();
		console.log(potholecount);
    var el = document.getElementById('graph'); // get canvas

var options = {
    percent:  el.getAttribute('data-percent') || 25,
    size: el.getAttribute('data-size') || 220,
    lineWidth: el.getAttribute('data-line') || 15,
    rotate: el.getAttribute('data-rotate') || 0
}

var canvas = document.createElement('canvas');
var span = document.createElement('span');
span.textContent = potholecount;
    
if (typeof(G_vmlCanvasManager) !== 'undefined') {
    G_vmlCanvasManager.initElement(canvas);
}

var ctx = canvas.getContext('2d');
canvas.width = canvas.height = options.size;

el.appendChild(span);
el.appendChild(canvas);

ctx.translate(options.size / 2, options.size / 2); // change center
ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI); // rotate -90 deg

//imd = ctx.getImageData(0, 0, 240, 240);
var radius = (options.size - options.lineWidth) / 2;

var drawCircle = function(color, lineWidth, percent) {
    percent = Math.min(Math.max(0, percent || 1), 1);
    ctx.beginPath();
    ctx.arc(0, 0, radius, 0, Math.PI * 2 * percent, false);
    ctx.strokeStyle = color;
        ctx.lineCap = 'round'; // butt, round or square
    ctx.lineWidth = lineWidth
    ctx.stroke();
};

drawCircle('#efefef', options.lineWidth, 100 / 100);
drawCircle('#555555', options.lineWidth, options.percent / 100);
var handle = setInterval(function() {
  options.percent = potholecount;
var digit = potholecount.toString()[0];
   if(options.percent > 100){
    options.percent = potholecount-(digit*100);
   }
 /* if(options.percent > 100) {
     options.percent = testme-(digit*100); 
  }*/
  drawCircle('#0000ff', options.lineWidth, options.percent / 100);
  span.textContent = 'Potholes: '+potholecount;
}, 100);
});





upvotesRef1.once('value',function(snapshot){
		var wastecount = snapshot.numChildren();
var el = document.getElementById('graph1'); // get canvas

var options = {
    percent:  el.getAttribute('data-percent') || 25,
    size: el.getAttribute('data-size') || 220,
    lineWidth: el.getAttribute('data-line') || 15,
    rotate: el.getAttribute('data-rotate') || 0
}

var canvas = document.createElement('canvas');
var span = document.createElement('span');
span.textContent = wastecount;
    
if (typeof(G_vmlCanvasManager) !== 'undefined') {
    G_vmlCanvasManager.initElement(canvas);
}

var ctx = canvas.getContext('2d');
canvas.width = canvas.height = options.size;

el.appendChild(span);
el.appendChild(canvas);

ctx.translate(options.size / 2, options.size / 2); // change center
ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI); // rotate -90 deg

//imd = ctx.getImageData(0, 0, 240, 240);
var radius = (options.size - options.lineWidth) / 2;

var drawCircle = function(color, lineWidth, percent) {
    percent = Math.min(Math.max(0, percent || 1), 1);
    ctx.beginPath();
    ctx.arc(0, 0, radius, 0, Math.PI * 2 * percent, false);
    ctx.strokeStyle = color;
        ctx.lineCap = 'round'; // butt, round or square
    ctx.lineWidth = lineWidth
    ctx.stroke();
};

drawCircle('#efefef', options.lineWidth, 100 / 100);
drawCircle('#555555', options.lineWidth, options.percent / 100);
var handle = setInterval(function() {
  options.percent = wastecount;
var digit = wastecount.toString()[0];
   if(options.percent > 100){
    options.percent = wastecount-(digit*100);
   }
 /* if(options.percent > 100) {
     options.percent = testme-(digit*100); 
  }*/
  drawCircle('#ff0000', options.lineWidth, options.percent / 100);
  span.textContent = "Sanitation:"+wastecount;
}, 100);



});