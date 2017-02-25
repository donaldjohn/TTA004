<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Configuración Guion de Pruebas</title>
<![CDATA[
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/generadorPruebas/configuracion/js/casosUsoPrevios.js"></script>	
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/content/generadorPruebas/configuracion/js/desconocido.js"></script>
]]>

</head>
<body>
	
	<s:actionmessage theme="jquery" />
	<s:actionerror theme="jquery" />
	<br />
	<h1>Gestión de entradas para el guion de prueba </h1>
	
	<p class="instrucciones">Las siguientes entradas se utilizarán para generar el guion de prueba del caso de uso. </p>
	<p class="instrucciones">Por favor, ingrese un archivo .txt con valores válidos para cada entrada.</p>
	<s:form autocomplete="off" id="frmActor" theme="simple"
		action="%{#pageContext.request.contextPath}/configuracion-entradas!anadirValoresEntradas" enctype="multipart/form-data" method="post">
		<s:hidden name="_method" value="put" />
		<div class="formulario">
			<div class="tituloFormulario">Configuración de las Entradas</div>
			<div class="seccion">
				<s:iterator value="ListaAtributo" var="t">
					<s:property value="'Entrada '+ #t.nombre"/>
					<p class="instrucciones">Ingrese el archivo de valores para esta entrada.</p>
					<p class="instrucciones">
					<!-- <input type="file" id="pic" name="pic" accept="file_extension"/>-->
					 <s:file name="upload" label="File"/>
					 <!--<s:url var="idAtributo" value="%{#t.id}"></s:url>-->
					 <!-- <input type="text" name="entrada" id="entrada" value="${idAtributo}"/>-->
					 <s:hidden name="idAtributo" value="%{#t.id}" />
					</p>
				</s:iterator>
			</div>
		</div>
		
		<br />
		<div align="center">
			
			<s:submit class="boton" value="Siguiente"/>
			
			<!--  <input class="boton" type="button"
				onclick="siguiente();"
				value="Siguiente" />-->
				
			<s:url var="urlGestionarCU"
				value="%{#pageContext.request.contextPath}/cu">
			</s:url>
			<input class="boton" type="button"
				onclick="location.href='${urlGestionarCU}'"
				value="Cancelar" />
				
		</div>
	</s:form>
	
</body>
	</html>
</jsp:root>