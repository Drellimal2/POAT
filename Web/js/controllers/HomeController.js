app.controller('HomeCtrl', function ($scope) {
	
	var courses = {
		Feature: "Courses",
		Details: "View courses and assign assignments to them as well as keep your schedule.",

		imgUrl : "images/POAT_blue_noback.png"
	//imgUrl : "http://cdn2.knowyourmobile.com/sites/knowyourmobilecom/files/styles/article_main_wide_image/public/Array/samsung_galaxy_note_4-14_0.jpg?itok=HhjWHyGL"
	};
	
	var dynamicpriority  = {
		Feature: "Dynamic Priority",
		Details: "Custom algorithm uses due date and assignment completedness to set reccommended priorities.",
		
		imgUrl : "images/POAT_blue_noback.png"
	};
	//imgUrl : "http://cdn.cultofandroid.com/wp-content/uploads/2015/03/kv-phones.png"
	
	var subtask = {
		Feature: "Subtasks",
		Details: "Split large assignments into subtasks and set timelines to complete each.",
		
		imgUrl : "images/POAT_blue_noback.png"
		
	};
	//imgUrl : "http://r3.whistleout.com.au/public/images/blog/2012/05/htc_one_x_review_11.jpg"
	
	var features = {
		Feature: "More states for items ...",
		Details: "Complete, Started, Submitted ... Always know where you stand on a project.",
		
		imgUrl : "images/POAT_blue_noback.png"
	}
	//imgUrl : "http://i-cdn.phonearena.com/images/reviews/110579-image/HTC-One-X-vs-Apple-iPhone-4S-06.jpg"
	
	$scope.info = [ dynamicpriority, subtask, courses, features];
	
	
	
	
});