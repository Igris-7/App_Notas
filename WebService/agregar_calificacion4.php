<?php
    if($_SERVER["REQUEST_METHOD"]=="POST"){
        $curso_id = $_POST['curso_id'];
        $ev1 = $_POST['ev1'];
        $ev2 = $_POST['ev2'];
        $ev3 = $_POST['ev3'];
        $ev4 = $_POST['ev4'];
        $prom = $_POST['prom'];

       
        
        require_once ('conexion.php');
        
        $cnx = new Conexion();
		
		$conexion = $cnx->AbrirConexion();
		$consulta = "call SP_AgregarCalificacion4($curso_id,$ev1, $ev2, $ev3,$ev4,$prom)";
		
		$resultado = mysqli_query($conexion, $consulta);
		echo $resultado;
		
		$cnx->CerrarConexion($conexion);
    }
?>