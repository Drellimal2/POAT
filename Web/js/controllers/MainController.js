app.controller('MainCtrl', ['$scope', '$modal', function ($scope, $modal) {
    
    $scope.name = "Dane";
        
    $scope.tabs = [
        {
            heading: 'Courses',
            template: 'templates/courseList.html',
            types : 'Course'


        },
        {
            heading : 'Assignments',
            template : 'templates/assignmentList.html',
            types : 'Assignment'
        }

    ];
	
	$scope.lists= [
		{
			label: 'Courses',
			filters:[{		
									name: 'All'
							},
					 		{
									name: 'Open'
							},
					 		{
									name: 'Archived'
							}
					]
		},
		{
			label: 'Assignments',
			filters: [{
										name: 'All'
								},
					  			{		
										name:'Due'
								},
					  			{
										name:'Completed'
								}
					  ]
			}
	];
        
    $scope.courseFields = [
        {
            label : 'Course Code',
            name : 'courseCode',
            type : 'text'

        },
        {
            label : 'Course Title',
            name : 'courseTitle',
            type : 'text'

        },
        {
            label : 'Course Description',
            name : 'courseDesc',
            type : 'text'

        },
        {
            label : 'Start Date',
            name : 'courseStartDate',
            type : 'date',
			dateType: 'start'

        },
        {
            label : 'End Date',
            name : 'courseEndDate',
            type : 'date',
			dateType: 'end'

        }
        
    ];
    
    $scope.assignmentFields = [
        {
            label : 'Course Code',
            name : 'assignmentCourseCode',
            type : 'select'

        },
        {
            label : 'Title',
            name : 'assignmentTitle',
            type : 'text'

        },
        {
            label : 'Description',
            name : 'assignmentDesc',
            type : 'text'

        },
        {
            label : 'Date Given',
            name : 'assignmentStartDate',
            type : 'date',
			dateType: 'start'

        },
        {
            label : 'Due Date',
            name : 'assignmentDueDate',
            type : 'date',
			dateType: 'end'

        },
        {
            label : 'Priority',
            name : 'assignmentPriority',
            type: 'select'

        }

    ];
    
    var tables = {
        Course : $scope.courseFields,
        Assignment : $scope.assignmentFields
        
    };
    
    $scope.courses = [];
    
    $scope.assignments = [];
	
    $scope.selectedTab = {types: 'Course'};
	
    $scope.selectedTable = null;
	
	var selectedNav = { parent:0 , child: 0};
    
	$scope.buttonPress = function () {
		console.log($scope.selectedTab);
        var modalInstance = $modal.open({
            animation: true,
            templateUrl: 'templates/modal.html',
            controller: 'ModalInstanceCtrl',
            resolve: {
                Type: function () {
                    return {
						type : $scope.selectedTab.types,
						fields : $scope.selectedTable
					};
                }
            }
        });
        modalInstance.result.then(function (selectedItem) {
			addItem(selectedItem);
		},
								  function () {
                console.log('Modal dismissed at: ' + new Date());
            });
	};
	
	$scope.checkSelected = function(index1, index2){
		return selectedNav.parent == index1 &&
		selectedNav.child == index2;
	}

    $scope.setSelected = function (types, index1, index2) {
		var x;
		console.log(index1 + " " + index2);
		selectedNav.parent = index1;
		selectedNav.child = index2;
		for (x=0; x< $scope.tabs.length ; x++){
			if($scope.tabs[x].heading == types.label){
				$scope.selectedTab = $scope.tabs[x];
				
				break;
			}
		}
        $scope.selectedTable = tables[$scope.selectedTab.types];
    };
    
    var addItem = function (Item) {
        if (Item.type == 'Course') {
            $scope.courses.push(Item.data);
        } else if (Item.type == 'Assignment') {
            $scope.assignments.push(Item.data);
        }
    };
	
	$scope.notifs = function() {
			if (window.webkitNotifications.checkPermission() == 0) {
		var myNotification = window.webkitNotifications.createNotification( null, 'New Content Available', 'Click to view');
		myNotification.onclick = function() {
			window.location = 'http://teamtreehouse.com/new/content';
		}
		myNotification.show();
		} else {
			window.webkitNotifications.requestPermission(function(){});
		}
	};
    
}]);





app.controller('ModalInstanceCtrl', function ($scope, $modalInstance, Type) {
    $scope.Type = Type;
    $scope.newItem = {};
	var minDate = { 
		value : new Date(),
		startopened : false
	};
	var maxDate = {
		 
		value : new Date(),
		startopened : false
	};
	
	$scope.sgte = true;
	
	
	//Date Start
	$scope.starttoday = function () {
		if ($scope.Type.type == 'Course') {
			$scope.newItem.courseStartDate = minDate;
			$scope.newItem.courseEndDate = maxDate;
		} else {
			$scope.newItem.assignmentGivenDate = minDate;
			$scope.newItem.assignmentDueDate = maxDate;
		}
		
	};
	
	$scope.starttoday();
	
	var isStart = function(datetype){
		return (datetype == 'start');	
	};

	$scope.startclear = function () {
		minDate.value = null;
		maxDate.value = null;
	};

	$scope.startopencal = function (datetype, newItem, $event) {
		$event.preventDefault();
		$event.stopPropagation();
		if(isStart(datetype)){
			minDate.startopened = true;
		} else {
			maxDate.startopened = true;	
		}

	};

	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};

	$scope.format = 'dd-MMMM-yyyy';
	
	$scope.compareDates = function(){
			$scope.dateAlert = {
																	hidden:false,
																	type: "danger",
																	msg: "Start date cannot be greater than end date."
			};
			if (minDate.value > maxDate.value) {
					$scope.dateAlert.hidden = false;
					return true;
			} else {
					$scope.dateAlert.hidden = true;

					return false;
			}
	};

		
	//date end
    
    $scope.add = function () {
        $modalInstance.close({ data: $scope.newItem, type : $scope.Type.type
            });
        
    };
    
    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
    
});