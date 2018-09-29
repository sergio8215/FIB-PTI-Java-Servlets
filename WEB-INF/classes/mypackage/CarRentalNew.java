	package mypackage;

	import java.io.*;
	import javax.servlet.*;
	import javax.servlet.http.*;

	import org.json.simple.JSONArray;
	import org.json.simple.JSONObject;
	import org.json.simple.parser.JSONParser;
	import org.json.simple.parser.ParseException;

	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.Iterator;

	import java.io.FileWriter;
	import java.io.IOException;

	public class CarRentalNew extends HttpServlet {

	  int cont = 0;
	 

	  public void CreateJson(String modelo, String engine, String dias_alquiler, String num_vehi, Double descuento)
	   throws ServletException, IOException {
		
		
		File file = new File("/home/pozidriv/Dropbox/UPC/FIB/Proyect de Tecnologias de la informacion (PTI)/Lab1-Tomcat-y-JSON/apache-tomcat-9.0.5/webapps/my_webapp/WEB-INF/classes/mypackage/car-rental.json");
		
		JSONObject rootObject = null;
		JSONObject nuevo_obj=null;
		JSONArray rentalsJSONArray = null;
		JSONArray list = new JSONArray();
		
		if(!file.exists() || file.isDirectory()) { 

			//El archivo NO existe
			rootObject = new JSONObject();
			rentalsJSONArray = new JSONArray();
			rootObject.put("rentals", rentalsJSONArray);
			
		}else{

			//El archivo existe
			JSONParser parser = new JSONParser();
			try {
			//JSONArray rentalsJSONArray;
				rootObject = (JSONObject) parser.parse(new FileReader("/home/pozidriv/Dropbox/UPC/FIB/Proyect de Tecnologias de la informacion (PTI)/Lab1-Tomcat-y-JSON/apache-tomcat-9.0.5/webapps/my_webapp/WEB-INF/classes/mypackage/car-rental.json"));
				rentalsJSONArray = (JSONArray) rootObject.get("rentals");
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		JSONObject rental = new JSONObject();
	
		rental.put("modelo",modelo);
		rental.put("engine",engine);
		rental.put("dias_alquiler",dias_alquiler);
		rental.put("num_vehi",num_vehi);
		rental.put("descuento",descuento);

		rentalsJSONArray.add(rental);
			
		
  		try  (FileWriter file2 = new FileWriter("/home/pozidriv/Dropbox/UPC/FIB/Proyect de Tecnologias de la informacion (PTI)/Lab1-Tomcat-y-JSON/apache-tomcat-9.0.5/webapps/my_webapp/WEB-INF/classes/mypackage/car-rental.json")){
		
            file2.write(rootObject.toJSONString());
			file2.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	  public void doGet(HttpServletRequest req, HttpServletResponse res)
	                    throws ServletException, IOException {
	    res.setContentType("text/html");
	    PrintWriter out = res.getWriter();
	    
	    String modelo = req.getParameter("model_vehicle");
	    String engine = req.getParameter("sub_model_vehicle");
	    String dias_alquiler = req.getParameter("dies_lloguer");
	    String num_vehi = req.getParameter("num_vehicles");
	    String descuento = req.getParameter("descompte");
	    
		double price;
		
		price = (Integer.parseInt(modelo)*Integer.parseInt(dias_alquiler)*
			Integer.parseInt(num_vehi))*(0.01*descuento);
		
	    cont ++;

		out.println("<html>modelo: "+modelo+" <br/>engine: "+engine+
			" <br/>dias_alquiler: "+dias_alquiler+" <br/>num_vehi: "+num_vehi+
			" <br/>descuento: "+descuento+"<br/><br/><strong>El precio total de alquiler será: "
			+price+"</strong><br><br><a href='carrental_home.html'>Home</a></html>");

	    CreateJson(modelo, engine, dias_alquiler,num_vehi,descuento);
				     
	  
	  }
	  


	  public void doPost(HttpServletRequest req, HttpServletResponse res)
	                    throws ServletException, IOException {
	    doGet(req, res);
	  }
	}



	        
	    

