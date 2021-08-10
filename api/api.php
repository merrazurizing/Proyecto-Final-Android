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

            if($_GET['action']=='InsertOrUpdateFichaMedica'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->fecha_consulta)){
                        $this->response(200,"Error001","No se agrego la etiqueta fecha_consulta");
                        exit;
                    }
                    if($obj->fecha_consulta==''){
                        $this->response(200,"Error002","La etiqueta fecha consulta esta vacia");
                        exit;
                    }

                    if(!isset($obj->motivo)){
                        $this->response(200,"Error003","No se agrego la etiqueta motivo");
                        exit;
                    }
                    if($obj->motivo==''){
                        $this->response(200,"Error004","La etiqueta motivo esta vacia");
                        exit;
                    }

                    if(!isset($obj->plan)){
                        $this->response(200,"Error005","No se agrego la etiqueta plan");
                        exit;
                    }
                    if($obj->plan==''){
                        $this->response(200,"Error006","La etiqueta plan esta vacia");
                        exit;
                    }

                    if(!isset($obj->tratamiento)){
                        $this->response(200,"Error007","No se agrego la etiqueta tratamiento");
                        exit;
                    }
                    if($obj->tratamiento==''){
                        $this->response(200,"Error008","La etiqueta tratamiento esta vacia");
                        exit;
                    }

                    if(!isset($obj->diagnostico)){
                        $this->response(200,"Error009","No se agrego la etiqueta diagnostico");
                        exit;
                    }
                    if($obj->diagnostico==''){
                        $this->response(200,"Error0010","La etiqueta diagnostico esta vacia");
                        exit;
                    }

                    if(!isset($obj->lugar)){
                        $this->response(200,"Error0011","No se agrego la etiqueta lugar");
                        exit;
                    }
                    if($obj->lugar==''){
                        $this->response(200,"Error0012","La etiqueta lugar esta vacia");
                        exit;
                    }

                    if(!isset($obj->medico_run)){
                        $this->response(200,"Error0013","No se agrego la etiqueta medico_run");
                        exit;
                    }
                    if($obj->medico_run==''){
                        $this->response(200,"Error0014","La etiqueta medico_run esta vacia");
                        exit;
                    }
                    if(valida_rut($obj->medico_run)==false){
                        $this->response(200,"Error0015","El rut medico Ingresado no es Valido");
                        exit;
                    }
                    

                    if(!isset($obj->usuario_run)){
                        $this->response(200,"Error0016","No se agrego la etiqueta usuario_run");
                        exit;
                    }
                    if($obj->usuario_run==''){
                        $this->response(200,"Error0017","La etiqueta usuario_run esta vacia");
                        exit;
                    }
                    if(valida_rut($obj->usuario_run)==false){
                        $this->response(200,"Error0018","El rut usuario Ingresado no es Valido");
                        exit;
                    }
                    


                    $db = new bd();
                    $respuesta=$db->InsertOrUpdateFichaMedica($obj->fecha_consulta,$obj->motivo,$obj->plan,$obj->tratamiento,$obj->diagnostico,$obj->lugar,$obj->medico_run,$obj->usuario_run);

                    if($respuesta['Estado']=='success'){
                        $this->response(200,"success","Se inserto De Forma Correcta");
                    }else{
                        $this->response(200,"Error999",$respuesta['Response']);
                        exit;
                    }
                } 
                exit;
            }


            if($_GET['action']=='InsertOrUpdateAnamnesisRemota'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{
                    if(!isset($obj->antecedentes_morbidos)){
                        $this->response(200,"Error001","No se agrego la etiqueta antecedentes_morbidos");
                        exit;
                    }
                    if($obj->antecedentes_morbidos==''){
                        $this->response(200,"Error002","La etiqueta antecedentes_morbidos esta vacia");
                        exit;
                    }

                    if(!isset($obj->antecedentes_quirurgicos)){
                        $this->response(200,"Error001","No se agrego la etiqueta antecedentes_quirurgicos");
                        exit;
                    }
                    if($obj->antecedentes_quirurgicos==''){
                        $this->response(200,"Error002","La etiqueta antecedentes_quirurgicos esta vacia");
                        exit;
                    }
                    if(!isset($obj->hospitalizaciones)){
                        $this->response(200,"Error001","No se agrego la etiqueta hospitalizaciones");
                        exit;
                    }
                    if($obj->hospitalizaciones==''){
                        $this->response(200,"Error002","La etiqueta hospitalizaciones esta vacia");
                        exit;
                    }
                    if(!isset($obj->antecedentes_familiares)){
                        $this->response(200,"Error001","No se agrego la etiqueta antecedentes_familiares");
                        exit;
                    }
                    if($obj->antecedentes_familiares==''){
                        $this->response(200,"Error002","La etiqueta antecedentes_familiares esta vacia");
                        exit;
                    }
                    if(!isset($obj->alergias)){
                        $this->response(200,"Error001","No se agrego la etiqueta alergias");
                        exit;
                    }
                    if($obj->alergias==''){
                        $this->response(200,"Error002","La etiqueta alergias esta vacia");
                        exit;
                    }
                    if(!isset($obj->alimentacion)){
                        $this->response(200,"Error001","No se agrego la etiqueta alimentacion");
                        exit;
                    }
                    if($obj->alimentacion==''){
                        $this->response(200,"Error002","La etiqueta alimentacion esta vacia");
                        exit;
                    }
                    if(!isset($obj->tabaco)){
                        $this->response(200,"Error001","No se agrego la etiqueta tabaco");
                        exit;
                    }
                    if($obj->tabaco==''){
                        $this->response(200,"Error002","La etiqueta tabaco esta vacia");
                        exit;
                    }
                    if(!isset($obj->alcohol)){
                        $this->response(200,"Error001","No se agrego la etiqueta alcohol");
                        exit;
                    }
                    if($obj->alcohol==''){
                        $this->response(200,"Error002","La etiqueta alcohol esta vacia");
                        exit;
                    }
                    if(!isset($obj->drogas)){
                        $this->response(200,"Error001","No se agrego la etiqueta drogas");
                        exit;
                    }
                    if($obj->drogas==''){
                        $this->response(200,"Error002","La etiqueta drogas esta vacia");
                        exit;
                    }
                    if(!isset($obj->fichamedica_id)){
                        $this->response(200,"Error001","No se agrego la etiqueta fichamedica_id");
                        exit;
                    }
                    if($obj->fichamedica_id==''){
                        $this->response(200,"Error002","La etiqueta fichamedica_id esta vacia");
                        exit;
                    }


                    $db = new bd();
                    $respuesta=$db->InsertOrUpdateAnamnesisRemota($obj->antecedentes_morbidos,$obj->antecedentes_quirurgicos,$obj->hospitalizaciones,$obj->antecedentes_familiares,$obj->alergias,$obj->alimentacion,$obj->tabaco,$obj->alcohol,$obj->drogas,$obj->fichamedica_id);

                    if($respuesta['Estado']=='success'){
                        $this->response(200,"success","Se inserto De Forma Correcta");
                    }else{
                        $this->response(200,"Error999",$respuesta['Response']);
                        exit;
                    }
                } 
                exit;
            }

            if($_GET['action']=='InsertOrUpdateExamenFisico'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{
                    if(!isset($obj->segmentario)){
                        $this->response(200,"Error001","No se agrego la etiqueta segmentario");
                        exit;
                    }
                    if($obj->segmentario==''){
                        $this->response(200,"Error002","La etiqueta segmentario esta vacia");
                        exit;
                    }
                    if(!isset($obj->examen_imagen)){
                        $this->response(200,"Error001","No se agrego la etiqueta examen_imagen");
                        exit;
                    }
                    if($obj->examen_imagen==''){
                        $this->response(200,"Error002","La etiqueta examen_imagen esta vacia");
                        exit;
                    }
                    if(!isset($obj->examen_laboratorio)){
                        $this->response(200,"Error001","No se agrego la etiqueta examen_laboratorio");
                        exit;
                    }
                    if($obj->examen_laboratorio==''){
                        $this->response(200,"Error002","La etiqueta examen_laboratorio esta vacia");
                        exit;
                    }
                    if(!isset($obj->fichamedica_id)){
                        $this->response(200,"Error001","No se agrego la etiqueta fichamedica_id");
                        exit;
                    }
                    if($obj->fichamedica_id==''){
                        $this->response(200,"Error002","La etiqueta fichamedica_id esta vacia");
                        exit;
                    }


                    $db = new bd();
                    $respuesta=$db->InsertOrUpdateExamenFisico($obj->segmentario,$obj->examen_imagen,$obj->examen_laboratorio,$obj->fichamedica_id);

                    if($respuesta['Estado']=='success'){
                        $this->response(200,"success","Se inserto De Forma Correcta");
                    }else{
                        $this->response(200,"Error999",$respuesta['Response']);
                        exit;
                    }
                } 
                exit;
            }


            if($_GET['action']=='InsertOrUpdateGeneral'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{
                    if(!isset($obj->presion_arterial)){
                        $this->response(200,"Error001","No se agrego la etiqueta presion_arterial");
                        exit;
                    }
                    if($obj->presion_arterial==''){
                        $this->response(200,"Error002","La etiqueta presion_arterial esta vacia");
                        exit;
                    }
                    if(!isset($obj->frecuencia_cardiaca)){
                        $this->response(200,"Error001","No se agrego la etiqueta frecuencia_cardiaca");
                        exit;
                    }
                    if($obj->frecuencia_cardiaca==''){
                        $this->response(200,"Error002","La etiqueta frecuencia_cardiaca esta vacia");
                        exit;
                    }
                    if(!isset($obj->frecuencia_respiratoria)){
                        $this->response(200,"Error001","No se agrego la etiqueta frecuencia_respiratoria");
                        exit;
                    }
                    if($obj->frecuencia_respiratoria==''){
                        $this->response(200,"Error002","La etiqueta frecuencia_respiratoria esta vacia");
                        exit;
                    }
                    if(!isset($obj->temperatura)){
                        $this->response(200,"Error001","No se agrego la etiqueta temperatura");
                        exit;
                    }
                    if($obj->temperatura==''){
                        $this->response(200,"Error002","La etiqueta temperatura esta vacia");
                        exit;
                    }
                    if(!isset($obj->saturacion_oxigeno)){
                        $this->response(200,"Error001","No se agrego la etiqueta saturacion_oxigeno");
                        exit;
                    }
                    if($obj->saturacion_oxigeno==''){
                        $this->response(200,"Error002","La etiqueta saturacion_oxigeno esta vacia");
                        exit;
                    }
                    if(!isset($obj->examen_fisico_id)){
                        $this->response(200,"Error001","No se agrego la etiqueta examen_fisico_id");
                        exit;
                    }
                    if($obj->examen_fisico_id==''){
                        $this->response(200,"Error002","La etiqueta examen_fisico_id esta vacia");
                        exit;
                    }


                    $db = new bd();
                    $respuesta=$db->InsertOrUpdateGeneral($obj->presion_arterial,$obj->frecuencia_cardiaca,$obj->frecuencia_respiratoria,$obj->temperatura,$obj->saturacion_oxigeno,$obj->examen_fisico_id);

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

            if($_GET['action']=='DeleteFichaMedica'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                $db = new bd();
                $respuesta = $db->DeleteFichaMedica($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }
            if($_GET['action']=='DeleteAnamnesisRemota'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                $db = new bd();
                $respuesta = $db->DeleteAnamnesisRemota($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='DeleteExamenFisico'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                $db = new bd();
                $respuesta = $db->DeleteExamenFisico($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='DeleteGeneral'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                $db = new bd();
                $respuesta = $db->DeleteGeneral($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetMedico'){
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
                $respuesta = $db->GetMedico($obj->run_medico);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetPacientesMedico'){
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
                $respuesta = $db->GetPacientesMedico($obj->run_medico);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetPaciente'){
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
                $respuesta = $db->GetPaciente($obj->run_paciente);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }


            if($_GET['action']=='GetFichasPaciente'){
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
                $respuesta = $db->GetFichasPaciente($obj->run_paciente);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetFichaById'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                

                $db = new bd();
                $respuesta = $db->GetFichaById($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetAnamnesisRemotaById'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                

                $db = new bd();
                $respuesta = $db->GetAnamnesisRemotaById($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetExamenFisicoById'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                

                $db = new bd();
                $respuesta = $db->GetExamenFisicoById($obj->id);
                if($respuesta['Estado']=='success'){
                    $this->response(200,"success",$respuesta['Response']);
                }else{
                $this->response(200,"Error00",$respuesta['Response']);
                exit;
                }

                }
            exit;
            }

            if($_GET['action']=='GetGeneralById'){
                $obj = json_decode( file_get_contents('php://input') );
                $objArr = (array)$obj;
                if (empty($objArr)){
                    $this->response(200,"Error000","No se agrego JSON");
                }else{


                    if(!isset($obj->id)){
                        $this->response(200,"Error001","No se agrego la etiqueta id");
                        exit;
                    }
                    if($obj->id==''){
                        $this->response(200,"Error002","La etiqueta id esta vacia");
                        exit;
                    }

                

                $db = new bd();
                $respuesta = $db->GetGeneralById($obj->id);
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
            }
            else{
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

            if($_GET['action']=='GetAllFicha'){
                $db = new bd();
            $respuesta = $db->GetAllFicha();



            if($respuesta['Estado']=='success'){
                $this->response(200,"success",$respuesta['Response']);
            }else{
                $this->response(200,"Error999",$respuesta['Response']);
                exit;
            }
            }else{
                    $this->response(400);
            }

            if($_GET['action']=='GetAllAnamnesisRemota'){
                $db = new bd();
            $respuesta = $db->GetAllAnamnesisRemota();



            if($respuesta['Estado']=='success'){
                $this->response(200,"success",$respuesta['Response']);
            }else{
                $this->response(200,"Error999",$respuesta['Response']);
                exit;
            }
            }else{
                    $this->response(400);
            }

            if($_GET['action']=='GetAllExamenFisico'){
                $db = new bd();
                $respuesta = $db->GetAllExamenFisico();



            if($respuesta['Estado']=='success'){
                $this->response(200,"success",$respuesta['Response']);
            }else{
                $this->response(200,"Error999",$respuesta['Response']);
                exit;
                
            }
            }else{
                    $this->response(400);
            }

            if($_GET['action']=='GetAllGeneral'){
                $db = new bd();
                $respuesta = $db->GetAllGeneral();

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
