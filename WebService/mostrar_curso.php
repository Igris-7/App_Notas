<?PHP
header('Content-Type:Application/json; charset="utf-8"');	

if($_SERVER["REQUEST_METHOD"]=="POST"){
    
    $dni_est = $_POST['dni_est'];
        
    require_once ('conexion.php');
    
    $cnx = new Conexion();
	
	$conexion = $cnx->AbrirConexion();
	
    
    $consulta = "call SP_MostrarCursos($dni_est)";
	
	$result = mysqli_query($conexion, $consulta);
 	$cantidad = mysqli_num_rows($result);
 	
 	$temp_array = array();
 	
 	if($cantidad > 0){
 		while($row = mysqli_fetch_assoc($result)){
 			$temp_array[] = $row;
 		}
 	}
   
 	echo json_encode($temp_array);
	$cnx->CerrarConexion($conexion);
}

?>