<?php
error_reporting(E_ALL);
date_default_timezone_set("Chile/Continental");
class bd {

    protected $mysqli;
    const LOCALHOST = 'abascur.cl';
    const USER = 'abascur_root5';
    const PASSWORD = 'R*!yES+cA3[E';
    const DATABASE = 'abascur_android_05';
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


	public function GetAllMedico() {

		$medico =  Array();
		$respuesta = Array();
			  $query="SELECT run,nombre,correo,especialidad,ubicacion_consulta FROM medico ";
  
			  if($stmt = $this->mysqli->prepare($query)){
  
			  $r = $stmt->execute();
  
  
				  $stmt->bind_result($run,$nombre,$correo,$especialidad,$ubicacion_consulta);
				  
					  while ( $stmt->fetch()){
					  
  
						$medico[] = array("rut"=>$run,
								  "nombre"=>$nombre,
								  "correo"=>$correo,
								  "especialidad"=>$especialidad,
								  "ubicacion_consulta"=>$ubicacion_consulta
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
	
}
?>
