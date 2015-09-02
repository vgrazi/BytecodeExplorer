var bytecodeExplorerApp = angular.module('bytecodeExplorerApp', []);

    bytecodeExplorerApp.controller('BytecodeExplorerController', function ($scope, $timeout) {

        populateInstructionMaps();

        $scope.fileBytes = null;
        $scope.columnCount = 16;
        $scope.classFile = null;
        $scope.instruction = null;
        $scope.selectedByte = null;
        var selectedRow = -1;
        var selectedCol = -1;
        var timeoutPromise = null


        $scope.handleFiles = function() {

          var reader = new FileReader();

          reader.onload = function(e) {
              $scope.$apply(function() {
                  var binaryString = reader.result;
                  $scope.fileBytes = unpack(binaryString);
                  $scope.classFile = new ClassFile($scope.fileBytes);
              });
          };

          var fileInput = document.getElementById('fileInput');
          var file = fileInput.files[0];
          reader.readAsBinaryString(file);

        };

        $scope.rowCount = function() {
            var rowCount = 0;
            if($scope.fileBytes) {
                rowCount = Math.floor($scope.fileBytes.length / $scope.columnCount);
                if(rowCount * $scope.columnCount < $scope.fileBytes.length) {
                    rowCount++;
                }
            }
            return rowCount;
        }

        /**
         * Row index ranges from 0 to the last row
         */
        $scope.getRowBytes = function(rowIndex) {
            var startIndex = rowIndex * $scope.columnCount;
            return $scope.fileBytes.slice(startIndex, startIndex + $scope.columnCount);
        }

        $scope.convertToHex = function(intValue) {
            var h = ("0" + intValue.toString(16)).substr(-2).toUpperCase();
            return h;
        }

        /**
         * If preserve is true, this row, col will remain selected when mouse out of any cell
         */
        $scope.selected = function (row, col, preserve) {
            $scope.row = row;
            $scope.col = col;
            $scope.byteIndex = row* $scope.columnCount + col;
            if(preserve) {
                selectedRow = row;
                selectedCol = col;
            }
            $scope.endByteIndex = $scope.classFile.getEndByteIndex($scope.byteIndex);
            $scope.getBytecode(row, col);
        }

        $scope.selectPreserved = function() {
            if(selectedRow >= 0 && selectedCol >=0) {

                // prevent this from flickering by verifying that the mouse is not moving over the bytes
                var testRow = $scope.row;
                var testCol = $scope.col;
                // wait a short time
                if(timeoutPromise != null) {
                    $timeout.cancel(timeoutPromise);
                }
                timeoutPromise = $timeout(function() {
                    if(testRow == $scope.row && testCol == $scope.col) {
                        $scope.selected(selectedRow, selectedCol);
                        timeoutPromise = null;
                    }
                }, 150);
            }
        }

        /**
         *  Returns an array the size of the number of rows required to display our array
         */
        $scope.getRowCount = function() {
            return new Array($scope.rowCount());
        }

        /**
         *  Returns an array the size of the number of rows required to display our array
         */
        $scope.getColumnCount = function() {
            return new Array($scope.columnCount);
        }

        $scope.getColumnLabel = function(col) {
            if(col == 0) {
                return "Row";
            }
            else {
                return col;
            }
        }

        $scope.getBytecode = function(row, col) {
            if($scope.classFile) {
                var index = row * $scope.columnCount + col;
                $scope.instruction = $scope.classFile.getBytecode(index);
            }
        }

        $scope.formatAsHexString = formatAsHexString;
        $scope.formatAsOneByteHexString = formatAsOneByteHexString;

        $scope.getClass = function(row, col) {
            if($scope.classFile) {
                var index = row * $scope.columnCount + col;
                var section = $scope.classFile.getSelectedSection();
                if(section){
                    if(section.contains(index)) {
                        return "selected-section";
                    }
                }
            }

            return "default-section";

        }


        $scope.getClassXXX = function(row, col) {
            if($scope.classFile) {
                var index = row * $scope.columnCount + col;
                var section = $scope.classFile.getSelectedSection();
//                if(section){
////                    $scope.debug = section.getStartByteIndex() + "," + section.length();
//                    if(section.contains(index)) {
//                        if(row == selectedRow && col == selectedCol) {
////                            $scope.debug = "-3-";
//                            return "preserved-section";
//                        }
//                        else {
////                            $scope.debug = "-2-";
//                            return "selected-section";
//                        }
//                    }
//                }

                var cssClass = null;

                section = $scope.classFile.getSection(index)
                if(section != null) {
//                    try {
                        cssClass = section.getCssClass();
//                    }
//                    catch(error)  {
//                        console.log(section.getBytecode(index, $scope.classFile.getConstants()) + " has no getCssClassMethod");
//
//                    }
                    return cssClass;
                }

            }

//            $scope.debug = "-1-";
            return "default-section";

        }
    });



