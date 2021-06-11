<?PHP
header('Content-Type:Application/json; charset="utf-8"');	

if($_SERVER["REQUEST_METHOD"]=="POST"){
    
    $dni = $_POST['dni'];
        
    require_once ('conexion.php');
    
    $cnx = new Conexion();
	
	$conexion = $cnx->AbrirConexion();
	
    
    $consulta = "call SP_MostrarPerfil('$dni')";
	
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