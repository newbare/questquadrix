var directoryApp = angular.module('provaApp', []);

directoryApp.controller('ProvaCtrl', function($scope, $http, $window) {
    
    // You can set the server ip in the url like this
    // http://localhost/path/index.html?ip=192.168.0.1
    var ip = QueryString()['ip'] ? QueryString()['ip'] : "localhost";
    var urlAll = "http://" + ip + ":8080/questquadrix/rest/prova/all";
    var urlContact = "http://" + ip + ":8080/questquadrix/rest/prova/id/";
    var urlAllBancas = "http://" + ip + ":8080/questquadrix/rest/banca/all";
    var urlAllInstituicoes = "http://" + ip + ":8080/questquadrix/rest/instituicao/all";
    var urlAllGraus = "http://" + ip + ":8080/questquadrix/rest/grau/all";
    // For Cross Domain request
    $http.defaults.useXDomain = true;

    $scope.prova = {};
   
    

    $scope.loadprovas = function() {

        $scope.filtreName = "";  // search reset 

        $http.get(urlAll)
        .success(function(data) {
            $scope.provas = data;

        })
        .error(function(data, status) {
            console.log(data, status);
        });                 
    };
    
    
    $scope.loadBancas = function() {

        $http.get(urlAllBancas)
        .success(function(data) {
            $scope.bancas = data;
            $scope.selected = $scope.bancas[0];

        })
        .error(function(data, status) {
            console.log(data, status);
        });                 
    };
    
    $scope.loadInstituicoes = function() {

        $http.get(urlAllInstituicoes)
        .success(function(data) {
            $scope.instituicoes = data;
            $scope.selectedInst = $scope.instituicoes[0];

        })
        .error(function(data, status) {
            console.log(data, status);
        });                 
    };
    
    
    

    /**
     * private int idProva;
	   private String nome;
	   private Banca banca;
	   private Grau grau;
	   private Instituicao instituicao;
	   private List<Questao> questaos;
     * 
     * @param int index
     * @param json contact
     */
    $scope.setFormContact = function(index, prova) {
        $scope.prova.id = index;
        $scope.prova = prova;
        
    };

    /**
     * Send a new contact or a modified to the server for proceed to a save
     * 
     * @param json contact
     */
    $scope.editContact = function(prova) {
        
        var urlForServer = urlContact + ($scope.prova.id >= 0  ? $scope.prova.id : "");
        var httpMethode = $scope.prova.id >= 0 ? 'POST' : 'PUT';
        
        $http({
            method: httpMethode,
            url: urlForServer,
            data: $scope.prova,
            headers: {'Content-Type': 'application/json'}
        })
        .success(function(data) {
            if(data == 1) {
                if(httpMethode == 'PUT') {
                    $scope.prova.id = $scope.provas.length;
                }

                // angular.copy allows you to copy data to avoid to keep a link with the scope
                $scope.provas[$scope.prova.id] = angular.copy($scope.prova);
            }
        });
    };
    
    $scope.removeContact = function(prova){
        
        var indexContactToKill = getIndexByContact(prova);
          
        var urlForPost = urlContact + indexContactToKill;
       
        $http({
            method: 'DELETE',
            url: urlForPost,
            headers: {'Content-Type': 'application/json'}
        })
        .success(function(data) {
            if(data == 1) {
                // remove a contact in the directory array
                $scope.provas.splice(indexContactToKill, 1);
            }
        });
    };

    /**
     * Find a contact in the directory and retrun his index
     * 
     * @param {json} contact
     * @returns int
     */
    var getIndexByContact = function(prova) {
        for(index in $scope.provas) {
            if($scope.provas[index] == prova) {
                return index;
            }
        }
    };
    
    // Init directory
    $scope.loadprovas();
    $scope.loadBancas();
    $scope.loadInstituicoes();
});