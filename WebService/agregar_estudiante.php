<?php
    if($_SERVER["REQUEST_METHOD"]=="POST"){
        $dni = $_POST['dni'];
        $nombres = $_POST['nombres'];
        $telefono = $_POST['telefono'];
        $usuario = $_POST['usuario'];
        $clave = $_POST['clave'];
        $imagen = $_POST['imagen'];
        
        require_once ('conexion.php');
        
        $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();
		$consulta = "call SP_AgregarEstudiante('$dni', '$nombres', '$telefono','$usuario', '$clave','$imagen')";
		
		$resultado = mysqli_query($conexion, $consulta);
		echo $resultado;
		
		$cnx->CerrarConexion($conexion);
    }
?>