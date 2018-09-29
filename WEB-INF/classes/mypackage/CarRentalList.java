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

import javax.servlet.http.HttpSession;

public class CarRentalList extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    
    String username = req.getParameter("userid");
    String passw = req.getParameter("password");

    String user= "sergio";
    String pass= "1";

    cont ++;
    
    if(username.equals(user) && pass.equals(passw)){
  		readJson(res);
    }else{
		out.println("<html><big>Hola Amigo "+ username + " Datos de acceso incorrrecto</big><br><br>"+
                cont + " Accesos desde su carga.</html>");
	}

    
  }

  public void readJson(HttpServletResponse res){
    
    JSONParser parser = new JSONParser();
    
    try {	

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	out.println("<html><big>Acceso correcto</big><br/><br>");
	
	Object obj = parser.parse(new FileReader("/home/pozidriv/Dropbox/UPC/FIB/Proyect de Tecnologias de la informacion (PTI)/Lab1-Tomcat-y-JSON/apache-tomcat-9.0.5/webapps/my_webapp/WEB-INF/classes/mypackage/car-rental.json"));

	JSONObject jsonObject = (JSONObject) obj;

    String modelo;
    String engine;
    String dias_alquiler;
    String num_vehi;
    String descuento;
	
	// loop array
	JSONArray cars = (JSONArray) jsonObject.get("rentals");
	Iterator<JSONObject> iterator = cars.iterator();
	
	int i=0;
	
	while (iterator.hasNext()) {

		JSONObject car = (JSONObject) iterator.next();
		modelo = (String) car.get("modelo");
		engine = (String) car.get("engine");
		dias_alquiler = (String) car.get("dias_alquiler");
		num_vehi = (String) car.get("num_vehi");
		descuento = (String) car.get("descuento");
		
		out.println("Modelo: "+modelo + "<br>Engine: "+engine +"<br>Días de alquiler: "+ dias_alquiler+"<br>Num Vehiculos: " + num_vehi+"<br>Descuento: " + descuento+"<br/><br/>");
		
	}
	
	out.println("<a href='carrental_home.html'>Home</a> </html>");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    doGet(req, res);
  }
}
