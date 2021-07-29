<?php
require_once 'bd.php';
require_once 'Utilidades.php';

class apiKmedica {
    public function api(){
        header('Content-Type: application/JSON');
        $method = $_SERVER['REQUEST_METHOD'];
        switch ($method) {
        case 'GET':
          $this->get();
        break;
        case 'POST':
          $this->post();
        break;
        case 'PUT':
        echo 'PUT';
        break;
        case 'DELETE':
        echo 'DELETE';
        break;
        default:
        echo 'METODO NO SOPORTADO';
        break;
    }
}


function response($code=200, $status="", $message="") {
    http_response_code($code);
    if( !empty($status) && !empty($message) ){
        $response = array("status" => $status ,"mensaje"=>$message);
        echo json_encode($response,JSON_PRETTY_PRINT);
    }
}

function post() {

    if($_GET['action']=='InsertOrUpdateMedico'){
        $obj = json_decode( file_get_contents('php://input') );
        $objArr = (array)$obj;
        if (empty($objArr)){
            $this->response(200,"Error000","No se agrego JSON");
        }else{
            if(!isset($obj->run_medico)){
                $this->response(200,"Error001","No se agrego la etiqueta rut");
                exit;
            }
            if($obj->run_medico==''){
                $this->response(200,"Error002","La etiqueta rut esta vacia");
                exit;
            }
            if(!isset($obj->nombre)){
                $this->response(200,"Error004","No se agrego la etiqueta nombre");
                exit;
            }
            if($obj->nombre==''){
                $this->response(200,"Error005","La etiqueta nombre esta vacia");
                exit;
            }
            if(!isset($obj->correo)){
                $this->response(200,"Error006","No se agrego la etiqueta correo");
                exit;
            }
            if($obj->correo==''){
                $this->response(200,"Error007","La etiqueta correo esta vacia");
                exit;
            }
            if(!isset($obj->especialidad)){
                $this->response(200,"Error006","No se agrego la etiqueta especialidad");
                exit;
            }
            if($obj->especialidad==''){
                $this->response(200,"Error007","La etiqueta especialidad esta vacia");
                exit;
            }
            if(!isset($obj->ubicacion)){
                $this->response(200,"Error006","No se agrego la etiqueta ubicacion");
                exit;
            }
            if($obj->ubicacion==''){
                $this->response(200,"Error007","La etiqueta ubicacion esta vacia");
                exit;
            }
            if(!isset($obj->contrasena)){
                $this->response(200,"Error006","No se agrego la etiqueta contrasena");
                exit;
            }
            if($obj->contrasena==''){
                $this->response(200,"Error007","La etiqueta contrasena esta vacia");
                exit;
            }

            $db = new bd();
            $respuesta=$db->InsertOrUpdateMedico($obj->run_medico,$obj->nombre,$obj->correo,$obj->especialidad,$obj->ubicacion,$obj->contrasena);

            if($respuesta['Estado']=='success'){
                $this->response(200,"success","Se inserto De Forma Correcta");
            }else{
                $this->response(200,"Error999",$respuesta['Response']);
                exit;
            }
        } 
        exit;
    }

    if($_GET['action']=='InsertOrUpdatePaciente'){
        $obj = json_decode( file_get_contents('php://input') );
        $objArr = (array)$obj;
        if (empty($objArr)){
            $this->response(200,"Error000","No se agrego JSON");
        }else{
            if(!isset($obj->run_paciente)){
                $this->response(200,"Error001","No se agrego la etiqueta rut");
                exit;
            }
            if($obj->run_paciente==''){
                $this->response(200,"Error002","La etiqueta rut esta vacia");
                exit;
            }
            if(!isset($obj->nombre)){
                $this->response(200,"Error004","No se agrego la etiqueta nombre");
                exit;
            }
            if($obj->nombre==''){
                $this->response(200,"Error005","La etiqueta nombre esta vacia");
                exit;
            }
            if(!isset($obj->correo)){
                $this->response(200,"Error006","No se agrego la etiqueta correo");
                exit;
            }
            if($obj->correo==''){
                $this->response(200,"Error007","La etiqueta correo esta vacia");
                exit;
            }
            if(!isset($obj->fecha_nacimiento)){
                $this->response(200,"Error006","No se agrego la etiqueta fecha nacimiento");
                exit;
            }
            if($obj->fecha_nacimiento==''){
                $this->response(200,"Error007","La etiqueta fecha nacimiento esta vacia");
                exit;
            }
            if(!isset($obj->direccion)){
                $this->response(200,"Error006","No se agrego la etiqueta direccion");
                exit;
            }
            if($obj->direccion==''){
                $this->response(200,"Error007","La etiqueta direccion esta vacia");
                exit;
            }    
            if(!isset($obj->ocupacion)){
                $this->response(200,"Error006","No se agrego la etiqueta ocupacion");
                exit;
            }
            if($obj->ocupacion==''){
                $this->response(200,"Error007","La etiqueta ocupacion esta vacia");
                exit;
            }
            if(!isset($obj->previcion_salud)){
                $this->response(200,"Error006","No se agrego la etiqueta previcion_salud");
                exit;
            }
            if($obj->previcion_salud==''){
                $this->response(200,"Error007","La etiqueta previcion_salud esta vacia");
                exit;
            }

            $db = new bd();
            $respuesta=$db->InsertOrUpdatePaciente($obj->run_paciente,$obj->nombre,$obj->correo,$obj->fecha_nacimiento,$obj->direccion,$obj->ocupacion,$obj->previcion_salud);

            if($respuesta['Estado']=='success'){
                $this->response(200,"success","Se inserto De Forma Correcta");
            }else{
                $this->response(200,"Error999",$respuesta['Response']);
                exit;
            }
        } 
        exit;
    }

    if($_GET['action']=='DeleteMedico'){
        $obj = json_decode( file_get_contents('php://input') );
        $objArr = (array)$obj;
        if (empty($objArr)){
            $this->response(200,"Error000","No se agrego JSON");
        }else{


            if(!isset($obj->run_medico)){
                $this->response(200,"Error001","No se agrego la etiqueta run_medico");
                exit;
            }
            if($obj->run_medico==''){
                $this->response(200,"Error002","La etiqueta run_medico esta vacia");
                exit;
            }
            if(valida_rut($obj->run_medico)==false){
                $this->response(200,"Error003","El rut Ingresado no es Valido");
                exit;
            }
            


        $db = new bd();
        $respuesta = $db->DeleteMedico($obj->run_medico);
        if($respuesta['Estado']=='success'){
            $this->response(200,"success",$respuesta['Response']);
        }else{
          $this->response(200,"Error00",$respuesta['Response']);
          exit;
        }

        }
    exit;
    }

    if($_GET['action']=='DeletePaciente'){
        $obj = json_decode( file_get_contents('php://input') );
        $objArr = (array)$obj;
        if (empty($objArr)){
            $this->response(200,"Error000","No se agrego JSON");
        }else{


            if(!isset($obj->run_paciente)){
                $this->response(200,"Error001","No se agrego la etiqueta run_paciente");
                exit;
            }
            if($obj->run_paciente==''){
                $this->response(200,"Error002","La etiqueta run_paciente esta vacia");
                exit;
            }
            if(valida_rut($obj->run_paciente)==false){
                $this->response(200,"Error003","El rut Ingresado no es Valido");
                exit;
            }
            


        $db = new bd();
        $respuesta = $db->DeletePaciente($obj->run_paciente);
        if($respuesta['Estado']=='success'){
            $this->response(200,"success",$respuesta['Response']);
        }else{
          $this->response(200,"Error00",$respuesta['Response']);
          exit;
        }

        }
    exit;
    }



    $this->response(400);
}//CIERRA post

function get(){
    
    if($_GET['action']=='GetAllPaciente'){
        $db = new bd();
       $respuesta = $db->GetAllPaciente();



       if($respuesta['Estado']=='success'){
           $this->response(200,"success",$respuesta['Response']);
       }else{
         $this->response(200,"Error999",$respuesta['Response']);
         exit;
       }
    }else{
            $this->response(400);
    }

    if($_GET['action']=='GetAllMedico'){
        $db = new bd();
       $respuesta = $db->GetAllMedico();



       if($respuesta['Estado']=='success'){
           $this->response(200,"success",$respuesta['Response']);
       }else{
         $this->response(200,"Error999",$respuesta['Response']);
         exit;
       }
    }else{
            $this->response(400);
    }

     
}

}//CIERRA bd
?>
