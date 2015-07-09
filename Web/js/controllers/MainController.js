app.controller('MainCtrl', ['$scope', '$modal', function ($scope, $modal) {
    
    $scope.name = "Dane";
        
    $scope.tabs = [
        {
            heading: 'Course',
            template: 'templates/courseList.html',
            types : 'Course'


        },
        {
            heading : 'Assignments',
            template : 'templates/assignmentList.html',
            types : 'Assignment'
        }

    ];
	
	$scope.accordionGroups= [
		{
			heading: 'Courses',
			content:[{		
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
			heading: 'Assignments',
			content: [{
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
    $scope.selectedTab;
    $scope.selectedTable;
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

    $scope.setSelected = function (tab) {
        $scope.selectedTab = tab;
        $scope.selectedTable = tables[$scope.selectedTab.types];
    };
    
    var addItem = function (Item) {
        if (Item.type == 'Course') {
            $scope.courses.push(Item.data);
        } else if (Item.type == 'Assignment') {
            $scope.assignments.push(Item.data);
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
			console.log("start");
		} else {
			maxDate.startopened = true;	
		}
		console.log(newItem);
		
	};

	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};

	$scope.format = 'dd-MMMM-yyyy';

		
	//date end
    
    $scope.add = function () {
        console.log($scope.newItem);
        $modalInstance.close({ data: $scope.newItem, type : $scope.Type.type
            });
        
    };
    
    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
    
});