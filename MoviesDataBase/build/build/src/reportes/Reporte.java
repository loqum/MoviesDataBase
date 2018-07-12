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
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;

public class Reporte {
    private String reporte;

    public Reporte(String reporte) {
	this.reporte = reporte;
    }

    public void generarReporte() {
	ConexionMySql conexionMySql = new ConexionMySql();
	Connection conn = conexionMySql.conectar();
	try {
	    InputStream reportFile = getClass().getResourceAsStream("/reportes/"+
                    this.reporte+".jrxml");
	    JasperReport jr = (JasperReport) JasperCompileManager.compileReport(reportFile);
	    JasperPrint print = JasperFillManager.fillReport(jr, null, conn);	 

	    JRViewer viewer = new JRViewer(print);
	    JFrame frame = new JFrame("reporte");
	    frame.getContentPane().add(viewer);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.pack();
	    frame.setVisible(true);
	    
	} catch (Exception ex) {
	    System.out.println(this.reporte + ": " + ex);
//	    Alert alert = new Alert(AlertType.WARNING);
//	    alert.setTitle("MoviesDataBase");
//	    alert.setHeaderText("Error al generar el reporte:");
//	    alert.setContentText(this.reporte + ": " + ex);
//
//	    alert.showAndWait();
	    
	    //PROBAR ERROR REPORTE
	}
    }

}
