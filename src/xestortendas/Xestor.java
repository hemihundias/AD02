/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestortendas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import static xestortendas.Xestor.tendas;

/**
 *
 * @author Hemihundias
 */
public class Xestor {
    static List<Tenda> tendas = new ArrayList();
    static List<Cliente> clientes = new ArrayList(); 
    /**
     * @param args the command line arguments
     */    
    public static void main(String[] args) throws IOException {
        boolean menu = true;
                    
        String nome,cidade,id,descripcion,apelidos,mail;
        Double prezo;
        int cantidade,i;
        lerJson();        
        while(menu){      
            escribirJson();
                       
            Scanner teclado = new Scanner(System.in);
            
            System.out.println("\n1. Engadir unha tenda.");
            System.out.println("2. Eliminar unha tenda.");
            System.out.println("3. Engadir un producto á tenda.");
            System.out.println("4. Eliminiar un producto da tenda.");
            System.out.println("5. Engadir un empregado á tenda.");
            System.out.println("6. Eliminar un empregado da tenda.");
            System.out.println("7. Engadir un cliente.");
            System.out.println("8. Eliminar un cliente.");
            System.out.println("9. Crear unha copia de seguriadade.");
            System.out.println("10. Ler os titulares do periódico El País.");
            System.out.println("0. Sair do programa.\n");
            
            System.out.println("Por favor elixa unha opción:");
            
            String entrada = teclado.nextLine();
            
            boolean eliminado = false;
            switch(entrada){                        
                case "1":
                    System.out.println("Engada nome da nova tenda:");
                    nome = teclado.nextLine();
                    if(!tendas.isEmpty()){
                        for(Tenda t:tendas){
                            if(t.getNome().equalsIgnoreCase(nome)){
                                System.out.println("Ese nome xa está en uso, por favor elixa outro:");
                                nome = teclado.nextLine();
                            }
                        }
                    }
                    System.out.println("Engada cidade de emprazamento da tenda:");
                    cidade = teclado.nextLine();
                    tendas.add(new Tenda(nome,cidade));                  
                    break;

                case "2":
                    if(tendas.isEmpty()){
                        System.out.println("Non existen tendas para eliminar.\n");
                        break;
                    }
                    System.out.println("Por favor introduza a tenda a eliminar:");
                    nome = teclado.nextLine();

                    Tenda tendaEliminada = null;
                    for(Tenda t:tendas){
                        if(t.getNome().equalsIgnoreCase(nome)){
                            tendaEliminada = t;                            
                            eliminado = true;    
                            break;
                        }
                    } 
                    if(eliminado){
                        tendas.remove(tendaEliminada);
                        System.out.println("Tenda eliminada correctamente.\n");
                        break;
                    }
                    System.out.println("Non se atopa ningunha tenda para o valor engadido.\n");

                    break;

                case "3":
                    if(tendas.isEmpty()){
                        System.out.println("Non existen tendas as que engadir productos.\n");
                        break;
                    }
                    System.out.println("¿A que tenda quere engadir o producto?");
                    nome = teclado.nextLine();
                    for(Tenda t:tendas){
                        if(t.getNome().equalsIgnoreCase(nome)){  
                            System.out.println("Engada identificador do novo producto:");
                            id = teclado.nextLine();
                            if(!t.getProductos().isEmpty()){
                                for(i=0;i<t.getProductos().size();i++){
                                    if(t.getProductos().get(i).getId().equalsIgnoreCase(id)){
                                        System.out.println("Ese identificador xa existe.\n");
                                        eliminado = true;
                                        break;
                                    }
                                }                                   
                            }                                                                                   
                            if(eliminado){
                                break;
                            }
                            System.out.println("Engada descripción do producto:");
                            descripcion = teclado.nextLine();
                            System.out.println("Engada prezo do producto:");
                            prezo = teclado.nextDouble();
                            System.out.println("Engada cantidade do producto:");
                            cantidade = teclado.nextInt();
                            t.getProductos().add(new Producto(id,descripcion,prezo,cantidade)); 
                            eliminado = true;
                        }
                    }
                    if(eliminado){
                        break;
                    }
                    System.out.println("A tenda non existe.\n");
                    
                    break;

                case "4":
                    if(tendas.isEmpty()){
                        System.out.println("Non existen tendas para a eliminación de productos.\n");
                        break;
                    }
                    System.out.println("Por favor introduza a tenda da que quere eliminar o producto:\n");
                    nome = teclado.nextLine();
                       
                    for(Tenda t:tendas){
                        if(t.getNome().equalsIgnoreCase(nome) && !t.getProductos().isEmpty()){
                            System.out.println("¿Que producto desexa eliminar? Indique o identificador.\n");
                            id = teclado.nextLine();
                            for(i = 0;i<t.getProductos().size();i++){
                                if(t.getProductos().get(i).getId().equalsIgnoreCase(id)){
                                    t.getProductos().remove(i);
                                    System.out.println("Producto eliminado correctamente.\n");
                                    eliminado = true;
                                    break;
                                }
                            }                          
                           
                        }
                    } 
                    if(eliminado){
                        break;
                    }
                    System.out.println("Non se atopa o elemento desexado.\n");

                    break;

                case "5":
                    if(tendas.isEmpty()){
                        System.out.println("Non existen tendas ás que engadir empregados.\n");
                        break;
                    }
                    System.out.println("Por favor introduza a tenda na que se incluirá o novo empregado:\n");
                    nome = teclado.nextLine();
                                                            
                    for(Tenda t:tendas){
                        if(t.getNome().equalsIgnoreCase(nome)){
                            System.out.println("¿Cal é o nome do novo empregado?.\n");
                            nome = teclado.nextLine();
                            System.out.println("¿Cales son os apelidos do novo empregado?.\n");
                            apelidos = teclado.nextLine();
                            if(!t.getEmpregados().isEmpty()){
                                for(i = 0;i<t.getEmpregados().size();i++){
                                    if(!t.getEmpregados().get(i).getNome().equalsIgnoreCase(nome) || !t.getEmpregados().get(i).getApelidos().equalsIgnoreCase(apelidos)){
                                        t.getEmpregados().add(new Empregado(nome,apelidos));
                                        System.out.println("Empregado engadido correctamente.\n");
                                        eliminado = true;    
                                        break;
                                    }
                                }
                            }else{
                                t.getEmpregados().add(new Empregado(nome,apelidos));
                                eliminado = true;    
                                break;
                            }
                            
                        }
                    } 
                    if(eliminado){
                        break;
                    }
                    System.out.println("Non foi posible engadir o empregado.\n");

                    break;

                case "6":
                    if(tendas.isEmpty()){
                        System.out.println("Non existen tendas para a eliminación de empregados.\n");
                        break;
                    }
                    System.out.println("Por favor introduza a tenda da que quere dar de baixa o empregado:\n");
                    nome = teclado.nextLine();
                       
                    for(Tenda t:tendas){
                        if(t.getNome().equalsIgnoreCase(nome) && !t.getEmpregados().isEmpty()){
                            System.out.println("¿Que empregado desexa dar de baixa? Indique o nome:");
                            nome = teclado.nextLine();
                            System.out.println("Agora os apelidos:");
                            apelidos = teclado.nextLine();
                            for(i = 0;i<t.getEmpregados().size();i++){
                                if(t.getEmpregados().get(i).getNome().equalsIgnoreCase(nome) && t.getEmpregados().get(i).getApelidos().equalsIgnoreCase(apelidos)){
                                    t.getEmpregados().remove(i);
                                    System.out.println("Empregado dado de baixa correctamente.\n");
                                    eliminado = true;
                                    break;
                                }
                            }                          
                           
                        }
                    } 
                    if(eliminado){
                        break;
                    }
                    System.out.println("Non foi posible dar de baixa o empregado.\n");

                    break;

                case "7":                  
                    System.out.println("Engada nome do novo cliente:\n");
                    nome = teclado.nextLine();
                    System.out.println("Engada apelidos do cliente:\n");
                    apelidos = teclado.nextLine();
                    System.out.println("Engada correo electrónico do cliente:\n");
                    mail = teclado.nextLine();
                    for(Cliente c:clientes){
                        if(c.getMail().equalsIgnoreCase(mail)){
                            System.out.println("O cliente xa existe.");
                            eliminado = true;
                        }
                    }
                    if(eliminado){
                        System.out.println("O cliente xa existe.\n");
                    }else{
                        for(Tenda t:tendas){
                            t.getClientes().add(new Cliente(nome,apelidos,mail));
                        }
                        
                    }

                    break;

                case "8":
                    if(clientes.isEmpty()){
                        System.out.println("Non hai clientes para borrar.\n");
                        break;
                    }
                    System.out.println("Por favor introduza mail do cliente a eliminar:\n");
                    mail = teclado.nextLine();

                    for(Cliente c:clientes){
                        if(c.getMail().equalsIgnoreCase(mail)){
                            for(Tenda t:tendas){
                                t.getClientes().remove(t);
                            }                            
                            System.out.println("Cliente eliminado correctamente.\n");
                            eliminado = true;
                            break;
                        }

                    } 
                    if(eliminado){
                        break;
                    }
                    System.out.println("Non se atopa ningún cliente para o valor engadido.\n");

                    break;

                case "9":
                    File origin = new File("data.json");
                    File destination = new File("data.backup");
                    if (origin.exists()) {                       
                            FileReader fluxoDatos;
                            fluxoDatos = new FileReader(origin);

                            BufferedReader buferEntrada = new BufferedReader(fluxoDatos);
                           
                            String linea;
                                                        
                            FileWriter fluxoDato = new FileWriter(destination);
                            BufferedWriter buferSaida = new BufferedWriter(fluxoDato);
                            
                            while ((linea=buferEntrada.readLine()) != null) {
                                buferSaida.append(linea);
                            }
                            
                            buferEntrada.close();                                                                                    
                            buferSaida.close();                                                     
                           
                    }else{
                        System.out.println("Non existe ficheiro para facer copia de seguridade.\n");
                    }
                    break;
                    
                case "10":
                    XMLReader procesadorXML = null;
                    try {

                        //Creamos un parseador de texto e engadimoslle a nosa clase que vai parsear o texto
                        procesadorXML = XMLReaderFactory.createXMLReader();
                        TitularesXML titularesXML = new TitularesXML();
                        procesadorXML.setContentHandler(titularesXML);

                        //Indicamos o texto donde estan gardadas as persoas
                        InputSource arquivo = new InputSource("http://ep00.epimg.net/rss/elpais/portada.xml");
                        procesadorXML.parse(arquivo);

                        //Imprimimos os datos lidos no XML
                        ArrayList<Titular> titulares = titularesXML.getTitulares();
                        for(i=0;i<titulares.size();i++){
                            Titular tituloAux = titulares.get(i);
                            System.out.println("Titular: " + tituloAux.getTitular());
                        }

                    } catch (SAXException e) {
                        System.out.println("Ocurriu un erro ao ler o arquivo XML");
                    } catch (IOException e) {
                        System.out.println("Ocurriu un erro ao ler o arquivo XML");
                    }
                    System.out.println("\nPrema calquer tecla para saír da sección noticias.");
                    teclado.nextLine();
                    break;
                    
                case "0":
                    return;

                default:
                    System.out.println("O valor introducido debe ser un número comprendido entre o 0 e o 10, ambos inclusive.\n");
                                        
            }   
            
        }    
    }   
    public static void lerJson(){
        //Vamos comezar declarando o ficheiro
        File ficheiro = new File("data.json");
        if(ficheiro.exists()){
            try {
                //Creamos un fluxo de entrada para o arquivo
                FileReader fluxoDatos;
                fluxoDatos = new FileReader(ficheiro);

                //Creamos o bufer de entrada
                BufferedReader buferEntrada = new BufferedReader(fluxoDatos);

                //Imos lendo linea a linea
                StringBuilder jsonBuilder = new StringBuilder();
                String linea;

                while ((linea=buferEntrada.readLine()) != null) {
                    jsonBuilder.append(linea).append("\n");
                }

                //Temos que cerrar sempre o ficheiro
                buferEntrada.close();

                //Construimos o String con todalas lineas lidas
                String json = jsonBuilder.toString();

                //Pasamos o json a clase ca cal se corresponde
                Gson gson = new Gson();
                Tenda[] t = gson.fromJson(json, Tenda[].class);

            } catch (FileNotFoundException e) {
                System.out.println("Non se encontra o arquivo");
            } catch (IOException e) {
                System.out.println("Erro de entrada saída");
            }
        }    
        
    }
    
    public static void escribirJson(){
        //Pasamos a nosa clase a JSON utilizando a libreria GSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(tendas);
                
        //Vamos comezar declarando o ficheiro
        File ficheiro = new File("data.json");

        try {
            //Creamos o fluxo de saida
            FileWriter fluxoDatos = new FileWriter(ficheiro);
            BufferedWriter buferSaida = new BufferedWriter(fluxoDatos);

            buferSaida.write(json);
                        
            buferSaida.close();
        } catch (IOException e) {

        }                        
    }
}
