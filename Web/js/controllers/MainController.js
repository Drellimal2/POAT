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
            type : 'date'

        },
        {
            label : 'End Date',
            name : 'courseEndDate',
            type : 'date'

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
            type : 'date'

        },
        {
            label : 'Due Date',
            name : 'assignmentDueDate',
            type : 'date'

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
	
	$scope.starttoday = function () {
		if ($scope.Type == 'Course') {
			$scope.newItem.courseStartDate = new Date();
		} else {
			$scope.newItem.assignmentGivenDate = new Date();
		}
	};
	$scope.starttoday();

	$scope.startclear = function () {
		if ($scope.Type == 'Course') {
			$scope.newItem.courseStartDate = null;
		} else {
			$scope.newItem.assignmentGivenDate = null;
		}
	};

	$scope.startopencal = function (newItem, $event) {
		$event.preventDefault();
		$event.stopPropagation();
		if ($scope.Type == 'Course') {
			if (newItem.courseStartDate == undefined) {
				newItem.courseStartDate = false;
			}
			newItem.courseStartDate = true;
			console.log("did it ");
		}
	};

	$scope.dateOptions = {
		formatYear: 'yy',
		startingDay: 1
	};

	$scope.format = 'dd-MMMM-yyyy';

	var tomorrow = new Date();
	tomorrow.setDate(tomorrow.getDate() + 1);
	var afterTomorrow = new Date();
	afterTomorrow.setDate(tomorrow.getDate() + 2);
	$scope.startevents = [{
		date: tomorrow,
		status: 'full'
	}, {
		date: afterTomorrow,
		status: 'partially'
	}];

	$scope.startgetDayClass = function (date, mode) {
        if (mode === 'day') {
			var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

			for (var i = 0;  i < $scope.startevents.length;  i++)  {
				var currentDay = new Date($scope.startevents[i].date).setHours(0, 0, 0, 0);

				if (dayToCheck === currentDay) {
					return $scope.startevents[i].status;
				}
			}
	}
	return '';
};
    
    $scope.add = function () {
        console.log($scope.newItem);
        $modalInstance.close({ data: $scope.newItem, type : $scope.Type.type
            });
        
    };
    
    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
    
});