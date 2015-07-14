app.controller('HomeCtrl', function ($scope) {
	
	var courses = {
		Feature: "Courses",
		Details: "View courses and assign assignments to them as well as keep your schedule.",

		imgUrl : "images/workhard.png"
	//imgUrl : "http://cdn2.knowyourmobile.com/sites/knowyourmobilecom/files/styles/article_main_wide_image/public/Array/samsung_galaxy_note_4-14_0.jpg?itok=HhjWHyGL"
	};
	
	var dynamicpriority  = {
		Feature: "Dynamic Priority",
		Details: "Custom algorithm uses due date and assignment completedness to set reccommended priorities.",
		
		imgUrl : "images/workhard.png"
	};
	//imgUrl : "http://cdn.cultofandroid.com/wp-content/uploads/2015/03/kv-phones.png"
	
	var subtask = {
		Feature: "Subtasks",
		Details: "Split large assignments into subtasks and set timelines to complete each.",
		imgUrl : "images/workhard.png"
		
	};
	//imgUrl : "http://r3.whistleout.com.au/public/images/blog/2012/05/htc_one_x_review_11.jpg"
	
	var features = {
		Feature: "More states for items ...",
		Details: "Complete, Started, Submitted ... Always know where you stand on a project.",
		
		imgUrl : "images/workhard.png"
	}
	//imgUrl : "http://i-cdn.phonearena.com/images/reviews/110579-image/HTC-One-X-vs-Apple-iPhone-4S-06.jpg"
	
	$scope.info = [ dynamicpriority, subtask, courses, features];
	
	$scope.myInterval = 4000;
	
	
	$scope.testimonials = 
		[{
			quote: "This app really helped me get back on track in school. My parents are finally proud of me.",
			person: "ThePrevBadStudent"
			
		},{
			quote: "I was able to finish my degree thanks to this app, now i can provide for my babymother and kids.",
			person: "TheCollegeDad"
			
		},{
			quote: "Aided my research on AIDS now we found a cure.",
			person: "TheAidsResearcher"
			 
		},{
			 quote: "This app got me so laid, was able of accomplishing my task and being with every girl on the volleyball team. #subtasks",
			person: "ThePlayer"
			 
		 }];
	
	
});