app.controller('HomeCtrl', function ($scope) {
	
	var what = {
		Question : "What is Poat?",
		Answer: "Poat is a student companion, and assignment Tracker.",
		Special : "text"
	};
	
	var why  = {
		Question : "Why Poat and not another ToDo?",
		Answer: "Firstly Poat is more than another ToDo List, it's a student companion and as such has special feautures that would better suit a student than another todo list.",
		Special : "text"
	};
	
	var whyname = {
		Question : "What does Poat mean?",
		Answer: "The name of the app was originally ProjectOpus:Assignment Tracker but that was too wordy and as such we shortened it and hope that when it gets popular the name will stick.",
		Special : "text"
		
	};
	
	var features = {
		Question: "What are some of the features?",
		Answer: "Alot",
		Special : "text"
		
		
	}
	
	$scope.info = [ what, why, whyname, features];
	
});