<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
        $user = $_REQUEST['user'];
        $pwd = $_REQUEST['pwd'];
        
        require ('conexion.php');

        $json=array();

        $cnx = new Conexion();
		
        $conexion = $cnx->AbrirConexion();
		
        $consulta = "call SP_IngresarLogin('$user', '$pwd');";
        $resultado = mysqli_query($conexion,$consulta);
        
    	if($resultado){
                if($reg=mysqli_fetch_array($resultado)){
                        $json[]=$reg;
                        $cnx->CerrarConexion($conexion);
                        echo json_encode($json);
                }
                else{	
                        echo json_encode($json);
                }
        }
}
else{
       echo "Se requiere metodo post";
}
?>