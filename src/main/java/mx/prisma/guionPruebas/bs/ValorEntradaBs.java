package mx.prisma.guionPruebas.bs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mx.prisma.editor.dao.EntradaDAO;
import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Entrada;
import mx.prisma.editor.model.ReglaNegocio;
import mx.prisma.editor.model.Trayectoria;
import mx.prisma.generadorPruebas.dao.ValorDesconocidoDAO;
import mx.prisma.generadorPruebas.dao.ValorEntradaDAO;
import mx.prisma.generadorPruebas.model.ValorEntrada;
import mx.prisma.util.JsonUtil;
import net.sf.json.JSONObject;
import java.util.HashSet;

public class ValorEntradaBs {
	public static void guardarValores(List<File> entradas, List<String> idAtributos, int tipoEntrada,String jsonEntradasTabla,String jsonEntradasTabla2_1,CasoUso casoUso) throws IOException{
		 		
		 		Set<ValorEntrada> entradasSeleccionadas = new HashSet<ValorEntrada>(0);
		 		entradasSeleccionadas = JsonUtil.mapJSONToSet(
		 				jsonEntradasTabla, ValorEntrada.class);
		 		
		 		Set<Entrada> entradasSeleccionadas2 = new HashSet<Entrada>(0);
		 		entradasSeleccionadas2 = JsonUtil.mapJSONToSet(
		 				jsonEntradasTabla2_1, Entrada.class);
		 		
		 		//ANTES DE REGISTRAR ENTRADA, TENEMOS QUE BORRAR LAS QUE YA EXISTEN.
		 		/*ValorEntradaDAO edaox1 = new ValorEntradaDAO();
		 		edaox1.borrarValorEntrada();*/
		 		
		 		for(ValorEntrada x : entradasSeleccionadas){
		 			EntradaDAO edaox = new EntradaDAO();
		 			Entrada ex = edaox.obtenerEntrada(x.getId(),casoUso.getId());
		 			x.setEntrada(ex);
		 			x.setValido(true);
		 			x.setId(null);
		 			ValorEntradaDAO vedao = new ValorEntradaDAO();
		 			vedao.borrarValorEntrada(ex);
		 			ValorEntradaDAO vedao2 = new ValorEntradaDAO();
		 			vedao2.registrarValorEntrada(x);
		 			System.out.println(ex.getId());
		 			for(Entrada e : entradasSeleccionadas2){
			 			EntradaDAO edao = new EntradaDAO();
			 			try{
				 			Entrada ep = edao.consultarEntradaxAtributo(e.getId(),casoUso.getId(),ex.getId());
				 			ep.setNombreHTML(e.getNombreHTML());
				 			ep.setCasoUso(casoUso);
				 			EntradaDAO edao2 = new EntradaDAO();
				 			edao2.modificarEntrada(ep);
			 			}catch(Exception eop){}
			 		}
		 		}
		 		
		 		
			 		/*for(Entrada e : entradasSeleccionadas2){
			 			EntradaDAO edao = new EntradaDAO();
			 			Entrada ep = edao.consultarEntradaxAtributo(e.getId(),casoUso.getId());
			 			ep.setNombreHTML(e.getNombreHTML());
			 			ep.setCasoUso(casoUso);
			 			EntradaDAO edao2 = new EntradaDAO();
			 			edao2.modificarEntrada(ep);
			 		}*/

		 		
	}
	
	
	public static void crearArchivo(List<File> entradas, List<String> idAtributos, List<String> nombreArchivos) throws IOException{
		int i=0;
		////System.out.println("ARCHIVO: "+getUploadFileName());
		//System.out.println("TOTAL ENTRADAS: "+entradas.size());
		Entrada entrada_1 =new Entrada();
		List<Entrada> entradasBD = new ArrayList<Entrada>(); 
		for(String idAtributo : idAtributos){
			entrada_1= new ValorDesconocidoDAO().obtenerEntrada(idAtributo);
			//System.out.println("entrada_1: "+entrada_1.getId());
			entradasBD.add(entrada_1);
		}
		for(File entrada : entradas){
			//System.out.println("RUTA: "+entrada.getAbsolutePath());
			File fichero_entrada = new File("/Users/enyamartinez/Desktop/Mi PRISMA/AplicacionTTB064-master/src/main/webapp/resources/archivos/guion/"+nombreArchivos.get(i));
			InputStream in = new FileInputStream(entrada);
			FileOutputStream out = new FileOutputStream(fichero_entrada);
			
			byte[] buf = new byte[1024];
			int len;
			 
			while ((len = in.read(buf)) > 0) {
			  out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
			String nuevaRuta=fichero_entrada.getAbsolutePath();
			//System.out.println("Nueva ruta: "+nuevaRuta);
			//AQUÍ TENGO QUE LLAMAR AL DAO PARA INSERTAR LA NUEVA RUTA EN LA TABLA VALORDESCONOCIDO.
			if(new ValorDesconocidoDAO().consultarDesconocido(entradasBD.get(i))){//Si la entrada, ya está registrada, se borra.
				new ValorDesconocidoDAO().borrarRuta(entradasBD.get(i));
			}
			new ValorDesconocidoDAO().registraRuta(nuevaRuta,entradasBD.get(i),1);
			i++;
		}
	}

	public static void cambiarStatus(List<String> valor, CasoUso casoUso) {
		Set<Entrada> entradas = casoUso.getEntradas();
		int i=0;
		for(Entrada entrada : entradas){
			ValorEntradaDAO vedao = new ValorEntradaDAO();
			for(ValorEntrada ve : vedao.consultarValores(entrada)){
				//System.out.println(ve.getValor()+"-"+valor.get(i));
				if(ve.getValor().equals(valor.get(i))){
					ve.setSeleccionada(true);
				}else{
					ve.setSeleccionada(false);
				}
				vedao = new ValorEntradaDAO();
				vedao.registrarValorEntrada(ve);
			}
			i++;
		}
	}
}
