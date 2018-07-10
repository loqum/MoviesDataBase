package reportes;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import java.io.InputStream;
import java.sql.Connection;
import javax.swing.JFrame;

import connection.ConexionMySql;

public class Reporte {
    private String reporte;
    
    public Reporte(String reporte){
        this.reporte = reporte;
    }
    
    public void generarReporte(){
        ConexionMySql conexionMySql = new ConexionMySql();
        Connection conn = conexionMySql.conectar();
        try{
            InputStream dir = getClass().getResourceAsStream("/reportes/"+
                    this.reporte+".jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            
            JRViewer test = new JRViewer(jp);
            JFrame frame = new JFrame("Report");
            frame.getContentPane().add(test);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        }catch(Exception ex){
            System.out.println("Error al generar el reporte "+this.reporte+": "+ex);
        }
    }
    
}
