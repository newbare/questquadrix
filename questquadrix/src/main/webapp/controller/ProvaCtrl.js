var directoryApp = angular.module('provaApp', []);

directoryApp.controller('ProvaCtrl', function($scope, $http, $window) {
    alert("Xuxa Aqui!!!")
    // You can set the server ip in the url like this
    // http://localhost/path/index.html?ip=192.168.0.1
    var ip = QueryString()['ip'] ? QueryString()['ip'] : "localhost";
    var urlAll = "http://" + ip + ":8080/questquadrix/dirapi/prova/all";
    var urlContact = "http://" + ip + ":8080/questquadrix/dirapi/prova/id/";

    // For Cross Domain request
    $http.defaults.useXDomain = true;

    $scope.formData = {};


    $scope.loadDirectory = function() {

        $scope.filtreName = "";  // search reset 

        $http.get(urlAll)
        .success(function(data) {
            $scope.directory = data;

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
        $scope.formData.prova.id = index;
        $scope.formData.prova.nome = prova.nome;
        $scope.formData.prova.banca.nomeBanca = prova.banca.nomeBanca;
        $scope.formData.prova.grau.txtNivel = prova.grau.txtNivel;
        $scope.formData.prova.instituicao.nomeInstituicao = prova.instituicao.nomeInstituicao;    
        
        alert(prova.instituicao.nomeInstituicao);
    };

    /**
     * Send a new contact or a modified to the server for proceed to a save
     * 
     * @param json contact
     */
    $scope.editContact = function(contact) {
        
        var urlForServer = urlContact + ($scope.formData.id >= 0  ? $scope.formData.id : "");
        var httpMethode = $scope.formData.id >= 0 ? 'POST' : 'PUT';
        
        $http({
            method: httpMethode,
            url: urlForServer,
            data: $scope.formData,
            headers: {'Content-Type': 'application/json'}
        })
        .success(function(data) {
            if(data == 1) {
                if(httpMethode == 'PUT') {
                    $scope.formData.id = $scope.directory.length;
                }

                // angular.copy allows you to copy data to avoid to keep a link with the scope
                $scope.directory[$scope.formData.id] = angular.copy($scope.formData);
            }
        });
    };
    
    $scope.removeContact = function(contact){
        
        var indexContactToKill = getIndexByContact(contact);
          
        var urlForPost = urlContact + indexContactToKill;
       
        $http({
            method: 'DELETE',
            url: urlForPost,
            headers: {'Content-Type': 'application/json'}
        })
        .success(function(data) {
            if(data == 1) {
                // remove a contact in the directory array
                $scope.directory.splice(indexContactToKill, 1);
            }
        });
    };

    /**
     * Find a contact in the directory and retrun his index
     * 
     * @param {json} contact
     * @returns int
     */
    var getIndexByContact = function(contact) {
        for(index in $scope.directory) {
            if($scope.directory[index] == contact) {
                return index;
            }
        }
    };
    
    // Init directory
    $scope.loadDirectory();
});