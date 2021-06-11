<?php
    if($_SERVER["REQUEST_METHOD"]=="POST"){
        $nombre = $_POST['nombre'];
        $docente = $_POST['docente'];
        $ciclo = $_POST['ciclo'];
        $creditos = $_POST['creditos'];
        $dni_est = $_POST['dni_est'];
       
        
        require_once ('conexion.php');
        
        $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();
		$consulta = "call SP_AgregarCurso('$nombre', '$docente','$ciclo', '$creditos', '$dni_est')";
		
		$resultado = mysqli_query($conexion, $consulta);
		echo $resultado;
		
		$cnx->CerrarConexion($conexion);
    }
?>