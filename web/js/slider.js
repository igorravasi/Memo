//1. set ul width 
//2. image when click prev/next button
var ul;
var liItems;
var imageNumber;
var imageWidth;
var prev, next;
var currentPostion = 0;
var currentImage = 0;


function resizeSlider(){
	
	liItems = ul.children;
	imageNumber = liItems.length;
	for (var i = 0; i < imageNumber; i++) {
		liItems[i].children[0].width=ul.parentNode.clientWidth;
	}
	
	//Il timeoutè necessario, perchè clientHeight (come clientWidth) può non essere sincroinizzata con la realtà subito dopo
	//a modifiche e in questo caso risulterebbe errato il posizionamento.
	window.setTimeout(
			function(){moveNavigator();}
			, 50)

	imageWidth = liItems[0].children[0].clientWidth;
	ul.style.width = parseInt(imageWidth * imageNumber) + 'px';
}

function moveNavigator(){
	var sliderHeight = ul.parentNode.clientHeight;
	
	var navigators = document.getElementsByClassName("nvgt");
	for (var i = 0; i < navigators.length; i++) {
		navigators[i].style.top = parseInt( ( sliderHeight - navigators[i].clientHeight ) / 2) + 'px';
	}
	
	
}


function init(){
	
	ul = document.getElementById('image_slider');
	
	display(ul.parentNode.id,true);
	
	resizeSlider();
	
	prev = document.getElementById("prev");
	next = document.getElementById("next");
	generatePager(imageNumber);	
	prev.onclick = function(){slide(-1);};
	next.onclick = function(){slide(1);};
	prev.onclick = function(){ onClickPrev();};
	next.onclick = function(){ onClickNext();};
}

function animate(opts){
	var start = new Date;
	var id = setInterval(function(){
		var timePassed = new Date - start;
		var progress = timePassed / opts.duration;
		if (progress > 1){
			progress = 1;
		}
		var delta = opts.delta(progress);
		opts.step(delta);
		if (progress == 1){
			clearInterval(id);
			opts.callback();
		}
	}, opts.delay || 17);
	//return id;
}

function slideTo(imageToGo){
	var direction;
	var numOfImageToGo = Math.abs(imageToGo - currentImage);
	// slide toward left

	direction = currentImage > imageToGo ? 1 : -1;
	currentPostion = -1 * currentImage * imageWidth;
	var opts = {
		duration:1000,
		delta:function(p){return p;},
		step:function(delta){
			ul.style.left = parseInt(currentPostion + direction * delta * imageWidth * numOfImageToGo) + 'px';
		},
		callback:function(){currentImage = imageToGo;}	
	};
	animate(opts);
}

function onClickPrev(){
	if (currentImage == 0){
		slideTo(imageNumber - 1);
	} 		
	else{
		slideTo(currentImage - 1);
	}		
}

function onClickNext(){
	if (currentImage == imageNumber - 1){
		slideTo(0);
	}		
	else{
		slideTo(currentImage + 1);
	}		
}

function generatePager(imageNumber){	
	var pageNumber;
	var pagerDiv = document.getElementById('pager');
	for (i = 0; i < imageNumber; i++){
		var li = document.createElement('li');
		pageNumber = document.createTextNode(parseInt(i + 1));
		li.appendChild(pageNumber);
		pagerDiv.appendChild(li);
		// add event inside a loop, closure issue.
		li.onclick = function(i){
			return function(){
				slideTo(i);
			}
		}(i);
	}	
	var computedStyle = document.defaultView.getComputedStyle(li, null);
	//border width 1px; offsetWidth = 22
	var liWidth = parseInt(li.offsetWidth);
	var liMargin = parseInt(computedStyle.margin.replace('px',""));
	pagerDiv.style.width = parseInt((liWidth + liMargin * 2) * imageNumber) + 'px';
}
window.onload = init;
window.onresize = resizeSlider;
