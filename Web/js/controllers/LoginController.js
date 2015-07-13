app.controller('LoginCtrl', function ($scope) {
	

	$scope.emailAddress;
	$scope.password;
	
	$scope.isnew = false;
	
	$scope.firstname;
	$scope.lastname;
	$scope.username;
	$scope.phase = 1;
	
	
	$scope.changePhase = function(){
		
		$scope.phase = 2;
				
	};
	
	$scope.setNew = function(){
		$scope.isnew =true;	
	};
	
	
	
});