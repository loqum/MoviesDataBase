package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexionMySql {
    public String database = "Movies";
    public String url = "jdbc:mysql://moviedatabase.cskuigrbplma.us-east-2.rds.amazonaws.com:3306/"+database;
    public String user = "root";
    public String password = "12345678";
    
    public Connection conectar(){
        Connection link = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            link = DriverManager.getConnection(this.url,this.user,this.password);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error al conectar: "+ex
                    +"\nAsegúrese de que el servidor esté en marcha.","ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        return link;
    }
    
}
