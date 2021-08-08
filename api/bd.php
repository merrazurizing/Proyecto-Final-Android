<?php
error_reporting(E_ALL);
date_default_timezone_set("Chile/Continental");
class bd {

    protected $mysqli;
    const LOCALHOST = 'localhost';
    const USER = 'root';
    const PASSWORD = '';
    const DATABASE = 'kmedica';
    const PORT = 3306;

    public function __construct() {
        try{
            //conexión a base de datos
            $this->mysqli = new mysqli(self::LOCALHOST, self::USER, self::PASSWORD, self::DATABASE,self::PORT);
            $this->mysqli->set_charset("utf8");
        }catch (mysqli_sql_exception $e){
            //Si no se puede realizar la conexión
            http_response_code(500);
            exit;
        }
    }

    public function InsertOrUpdateMedico($run_medico,$nombre,$correo,$especialidad,$ubicacion,$contrasena) {
		$respuesta = Array();

		$query = "INSERT INTO medico(run,nombre,correo,especialidad,ubicacion_consulta,contrasena) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), correo = VALUES(correo), especialidad = VALUES(especialidad), ubicacion_consulta = VALUES(ubicacion_consulta), contrasena = VALUES(contrasena)";

		if($stmt = $this->mysqli->prepare($query)){
			$stmt->bind_param('ssssss',$run_medico,$nombre,$correo,$especialidad,$ubicacion,$contrasena);
			$r = $stmt->execute();

			$respuesta = array('Estado' =>"success",
							'Response'=>$r );
							$stmt->close();
		}else{
			$Error = "Error: ".$this->mysqli->error." query : ".$query;
			$respuesta = array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
		return $respuesta;
	}
	
	public function InsertOrUpdatePaciente($run_paciente,$nombre,$correo,$fecha_nacimiento,$direccion,$ocupacion,$previcion_salud) {
		$respuesta = Array();

		$query = "INSERT INTO usuario(run,nombre,correo,fecha_nacimiento,direccion,ocupacion,previcion_salud) VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE nombre = VALUES(nombre),correo = VALUES(correo), fecha_nacimiento = VALUES(fecha_nacimiento),ocupacion = VALUES(ocupacion), direccion = VALUES(direccion), previcion_salud = VALUES(previcion_salud)";

		if($stmt = $this->mysqli->prepare($query)){
			$stmt->bind_param('sssssss',$run_paciente,$nombre,$correo,$fecha_nacimiento,$direccion,$ocupacion,$previcion_salud);
			$r = $stmt->execute();

			$respuesta = array('Estado' =>"success",
							'Response'=>$r );
							$stmt->close();
		}else{
			$Error = "Error: ".$this->mysqli->error." query : ".$query;
			$respuesta = array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
		return $respuesta;
    }

	public function InsertOrUpdateFichaMedica($fecha_consulta,$motivo,$plan,$tratamiento,$diagnostico,$lugar,$medico_run,$usuario_run) {
		$respuesta = Array();

		$query = "INSERT INTO fichamedica(fecha_consulta,motivo,plan,tratamiento,diagnostico,lugar,medico_run,usuario_run) VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE fecha_consulta = VALUES(fecha_consulta),motivo = VALUES(motivo), plan = VALUES(plan),tratamiento = VALUES(tratamiento), diagnostico = VALUES(diagnostico), lugar = VALUES(lugar), medico_run = VALUES(medico_run), usuario_run = VALUES(usuario_run)";

		if($stmt = $this->mysqli->prepare($query)){
			$stmt->bind_param('ssssssss',$fecha_consulta,$motivo,$plan,$tratamiento,$diagnostico,$lugar,$medico_run,$usuario_run);
			$r = $stmt->execute();

			$respuesta = array('Estado' =>"success",
							'Response'=>$r );
							$stmt->close();
		}else{
			$Error = "Error: ".$this->mysqli->error." query : ".$query;
			$respuesta = array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
		return $respuesta;
    }

	public function InsertOrUpdateExamenFisico($segmentario,$examen_imagen,$examen_laboratorio,$fichamedica_id){
		$respuesta = Array();

		$query = "INSERT INTO examen_fisico(segmentario,examen_imagen,examen_laboratorio,fichamedica_id) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE segmentario = VALUES(segmentario),examen_imagen = VALUES(examen_imagen),examen_laboratorio = VALUES(examen_laboratorio), fichamedica_id = VALUES(fichamedica_id)";

		if($stmt = $this->mysqli->prepare($query)){
			$stmt->bind_param('ssss',$segmentario,$examen_imagen,$examen_laboratorio,$fichamedica_id);
			$r = $stmt->execute();

			$respuesta = array('Estado' =>"success",
							'Response'=>$r );
							$stmt->close();
		}else{
			$Error = "Error: ".$this->mysqli->error." query : ".$query;
			$respuesta = array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
		return $respuesta;
	}

	public function InsertOrUpdateGeneral($presion_arterial,$frecuencia_cardiaca,$frecuencia_respiratoria,$temperatura,$saturacion_oxigeno,$examen_fisico_id){
		$respuesta = Array();

		$query = "INSERT INTO general(presion_arterial,frecuencia_cardiaca,frecuencia_respiratoria,temperatura,saturacion_oxigeno,examen_fisico_id) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE presion_arterial = VALUES(presion_arterial),frecuencia_cardiaca = VALUES(frecuencia_cardiaca),frecuencia_respiratoria = VALUES(frecuencia_respiratoria), temperatura = VALUES(temperatura), saturacion_oxigeno = VALUES(saturacion_oxigeno), examen_fisico_id = VALUES(examen_fisico_id)";

		if($stmt = $this->mysqli->prepare($query)){
			$stmt->bind_param('ssssss',$presion_arterial,$frecuencia_cardiaca,$frecuencia_respiratoria,$temperatura,$saturacion_oxigeno,$examen_fisico_id);
			$r = $stmt->execute();

			$respuesta = array('Estado' =>"success",
							'Response'=>$r );
							$stmt->close();
		}else{
			$Error = "Error: ".$this->mysqli->error." query : ".$query;
			$respuesta = array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
		return $respuesta;

	}



	public function InsertOrUpdateAnamnesisRemota($antecedentes_morbidos,$antecedentes_quirurgicos,$hospitalizaciones,$antecedentes_familiares,$Alergias,$alimentacion,$tabaco,$alcohol,$drogas,$fichamedica_id) {
		$respuesta = Array();

		$query = "INSERT INTO anamnesis_remota(antecedentes_morbidos,antecedentes_quirurgicos,hospitalizaciones,antecedentes_familiares,Alergias,alimentacion,tabaco,alcohol,drogas,fichamedica_id) 
		VALUES (?,?,?,?,?,?,?,?,?,?) 
		ON DUPLICATE KEY UPDATE antecedentes_morbidos = VALUES(antecedentes_morbidos),antecedentes_quirurgicos = VALUES(antecedentes_quirurgicos), hospitalizaciones = VALUES(hospitalizaciones),antecedentes_familiares = VALUES(antecedentes_familiares), Alergias = VALUES(Alergias), alimentacion = VALUES(alimentacion), tabaco = VALUES(tabaco), alcohol = VALUES(alcohol), drogas = VALUES(drogas) , fichamedica_id = VALUES(fichamedica_id)";

		if($stmt = $this->mysqli->prepare($query)){
			$stmt->bind_param('ssssssssss',$antecedentes_morbidos,$antecedentes_quirurgicos,$hospitalizaciones,$antecedentes_familiares,$Alergias,$alimentacion,$tabaco,$alcohol,$drogas,$fichamedica_id);
			$r = $stmt->execute();

			$respuesta = array('Estado' =>"success",
							'Response'=>$r );
							$stmt->close();
		}else{
			$Error = "Error: ".$this->mysqli->error." query : ".$query;
			$respuesta = array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
		return $respuesta;
    }



	public function GetAllMedico() {

		$medico =  Array();
		$respuesta = Array();
			  $query="SELECT * FROM medico ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($run,$nombre,$correo,$especialidad,$ubicacion_consulta,$contrasena);
				  
					  while ( $stmt->fetch()){
					  
  
						$medico[] = array("rut"=>$run,
								  "nombre"=>$nombre,
								  "correo"=>$correo,
								  "especialidad"=>$especialidad,
								  "ubicacion_consulta"=>$ubicacion_consulta,
								  "contrasena"=>$contrasena
								  );
  
								  }
						  
  
				  if(count($medico)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$medico );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }


	  public function GetAllPaciente() {

		$paciente =  Array();
		$respuesta = Array();
			  $query="SELECT run,nombre,correo,fecha_nacimiento,direccion,ocupacion,previcion_salud FROM usuario ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($run,$nombre,$correo,$fecha_nacimiento,$direccion,$ocupacion,$previcion_salud);
				  
					  while ( $stmt->fetch()){
					  
  
						$paciente[] = array("rut"=>$run,
								  "nombre"=>$nombre,
								  "correo"=>$correo,
								  "fecha_nacimiento"=>$fecha_nacimiento,
								  "direccion"=>$direccion,
								  "ocupacion"=>$ocupacion,
								  "previcion_salud"=>$previcion_salud,

								  );
  
								  }
						  
  
				  if(count($paciente)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$paciente );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }

	  public function GetPaciente($run) {

		$paciente =  Array();
		$respuesta = Array();
			  $query="SELECT run,nombre,correo,fecha_nacimiento,direccion,ocupacion,previcion_salud FROM usuario WHERE run=?";
  
			  if($stmt = $this->mysqli->prepare($query)){

				$stmt->bind_param('s',$rut);
			  	$r = $stmt->execute();
  
  
				$stmt->bind_result($run,$nombre,$correo,$fecha_nacimiento,$direccion,$ocupacion,$previcion_salud);
				  
					  while ( $stmt->fetch()){
					  
  
						$paciente[] = array("rut"=>$run,
								  "nombre"=>$nombre,
								  "correo"=>$correo,
								  "fecha_nacimiento"=>$fecha_nacimiento,
								  "direccion"=>$direccion,
								  "ocupacion"=>$ocupacion,
								  "previcion_salud"=>$previcion_salud,

								  );
  
								  }
						  
  
				  if(count($paciente)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$paciente );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }


	  public function GetAllFicha() {

		$fichas =  Array();
		$respuesta = Array();
			  $query="SELECT * FROM fichamedica ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($id,$fecha_consulta,$motivo,$plan,$tratamiento,$diagnostico,$lugar,$medico_run,$usuario_run);
				  
					  while ( $stmt->fetch()){
					  
  
						$fichas[] = array("id"=>$id,
								  "fecha_consulta"=>$fecha_consulta,
								  "motivo"=>$motivo,
								  "plan"=>$plan,
								  "tratamiento"=>$tratamiento,
								  "diagnostico"=>$diagnostico,
								  "lugar"=>$lugar,
								  "medico_run"=>$medico_run,
								  "usuario_run"=>$usuario_run,

								  );
  
								  }
						  
  
				  if(count($fichas)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$fichas );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }
	  public function GetAllAnamnesisRemota() {

		$AnamnesisRemotas =  Array();
		$respuesta = Array();
			  $query="SELECT * FROM anamnesis_remota ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($id,$antecedentes_morbidos,$antecedentes_quirurgicos,$hospitalizaciones,$antecedentes_familiares,$Alergias,$alimentacion,$tabaco,$alcohol,$drogas,$fichamedica_id);
				  
					  while ( $stmt->fetch()){
					  
  
						$AnamnesisRemotas[] = array("id"=>$id,
								  "antecedentes_morbidos"=>$antecedentes_morbidos,
								  "antecedentes_quirurgicos"=>$antecedentes_quirurgicos,
								  "hospitalizaciones"=>$hospitalizaciones,
								  "antecedentes_familiares"=>$antecedentes_familiares,
								  "alimentacion"=>$alimentacion,
								  "tabaco"=>$tabaco,
								  "alcohol"=>$alcohol,
								  "drogas"=>$drogas,
								  "fichamedica_id"=>$fichamedica_id
								  );
  
								  }
						  
  
				  if(count($AnamnesisRemotas)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$AnamnesisRemotas );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }


	  public function GetAllExamenFisico() {

		$ExamenFisicos =  Array();
		$respuesta = Array();
			  $query="SELECT * FROM examen_fisico ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($id,$segmentario,$examen_imagen,$examen_laboratorio,$fichamedica_id);
				  
					  while ( $stmt->fetch()){
					  
  
						$ExamenFisicos[] = array("id"=>$id,
								  "segmentario"=>$segmentario,
								  "examen_imagen"=>$examen_imagen,
								  "examen_laboratorio"=>$examen_laboratorio,
								  "fichamedica_id"=>$fichamedica_id
								  );
  
								  }
						  
  
				  if(count($ExamenFisicos)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$ExamenFisicos );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }


	  public function GetAllGeneral() {

		$ExamenFisicos =  Array();
		$respuesta = Array();
			  $query="SELECT * FROM general ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($id,$presion_arterial,$frecuencia_cardiaca,$frecuencia_respiratoria,$temperatura,$saturacion_oxigeno,$examen_fisico_id);
				  
					  while ( $stmt->fetch()){
					  
  
						$ExamenFisicos[] = array("id"=>$id,
								  "presion_arterial"=>$presion_arterial,
								  "frecuencia_cardiaca"=>$frecuencia_cardiaca,
								  "frecuencia_respiratoria"=>$frecuencia_respiratoria,
								  "temperatura"=>$temperatura,
								  "saturacion_oxigeno"=>$saturacion_oxigeno,
								  "examen_fisico_id"=>$temperatura
								  );
  
								  }
						  
  
				  if(count($ExamenFisicos)>0){
					   $respuesta= array('Estado' =>"success" ,
					'Response'=>$ExamenFisicos );
				  }else{
						  $respuesta= array('Estado' =>"success" ,
										  'Response'=> "NoData");
				  };
				  $stmt->close();
  
			}else{
			  $Error = "Error: ".$this->mysqli->error." query : ".$query;
			  $respuesta= array('Estado' =>"Error" ,
								'Response'=>$Error );
			}
  
			  return $respuesta;
	  }


	public function DeleteMedico($rut) {

		$respuesta =Array();
  
		$query="DELETE FROM medico WHERE run=?";
  
  
		if( $stmt = $this->mysqli->prepare($query)){
	
		$stmt->bind_param('s',$rut);
		$r = $stmt->execute();
	
		$respuesta= array('Estado' =>"success" ,
							'Response'=>$r );
							$stmt->close();
		}else{
		$Error = "Error: ".$this->mysqli->error." query : ".$query;
		$respuesta= array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
	  return $respuesta;
	}

	public function DeletePaciente($rut) {

		$respuesta =Array();
  
		$query="DELETE FROM usuario WHERE run=?";
  
  
		if( $stmt = $this->mysqli->prepare($query)){
	
		$stmt->bind_param('s',$rut);
		$r = $stmt->execute();
	
		$respuesta= array('Estado' =>"success" ,
							'Response'=>$r );
							$stmt->close();
		}else{
		$Error = "Error: ".$this->mysqli->error." query : ".$query;
		$respuesta= array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
	  return $respuesta;
	}

	public function DeleteFichaMedica($id) {

		$respuesta =Array();
  
		$query="DELETE FROM fichamedica WHERE id=?";
  
  
		if( $stmt = $this->mysqli->prepare($query)){
	
		$stmt->bind_param('s',$id);
		$r = $stmt->execute();
	
		$respuesta= array('Estado' =>"success" ,
							'Response'=>$r );
							$stmt->close();
		}else{
		$Error = "Error: ".$this->mysqli->error." query : ".$query;
		$respuesta= array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
	  return $respuesta;
	}

	public function DeleteAnamnesisRemota($id) {

		$respuesta =Array();
  
		$query="DELETE FROM anamnesis_remota WHERE id=?";
  
  
		if( $stmt = $this->mysqli->prepare($query)){
	
		$stmt->bind_param('s',$id);
		$r = $stmt->execute();
	
		$respuesta= array('Estado' =>"success" ,
							'Response'=>$r );
							$stmt->close();
		}else{
		$Error = "Error: ".$this->mysqli->error." query : ".$query;
		$respuesta= array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
	  return $respuesta;
	}

	public function DeleteExamenFisico($id) {

		$respuesta =Array();
  
		$query="DELETE FROM examen_fisico WHERE id=?";
  
  
		if( $stmt = $this->mysqli->prepare($query)){
	
		$stmt->bind_param('s',$id);
		$r = $stmt->execute();
	
		$respuesta= array('Estado' =>"success" ,
							'Response'=>$r );
							$stmt->close();
		}else{
		$Error = "Error: ".$this->mysqli->error." query : ".$query;
		$respuesta= array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
	  return $respuesta;
	}

	public function DeleteGeneral($id) {

		$respuesta =Array();
  
		$query="DELETE FROM general WHERE id=?";
  
  
		if( $stmt = $this->mysqli->prepare($query)){
	
		$stmt->bind_param('s',$id);
		$r = $stmt->execute();
	
		$respuesta= array('Estado' =>"success" ,
							'Response'=>$r );
							$stmt->close();
		}else{
		$Error = "Error: ".$this->mysqli->error." query : ".$query;
		$respuesta= array('Estado' =>"Error" ,
							'Response'=>$Error );
		}
	  return $respuesta;
	}
	
}
?>
