package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			TextArea txtRegistros = new TextArea("Ordenando...");
			root.setTop(txtRegistros);

			VBox vbxProgreso = new VBox();

			HBox hbxAvance = new HBox();
			ProgressBar progressBar = new ProgressBar(0);
		    ProgressIndicator progressIndicator = new ProgressIndicator(0);
		    hbxAvance.getChildren().addAll(progressBar, progressIndicator);

			HBox hbxAvance2 = new HBox();
			ProgressBar progressBar2 = new ProgressBar(0);
			ProgressIndicator progressIndicator2 = new ProgressIndicator(0);
			hbxAvance2.getChildren().addAll(progressBar2, progressIndicator2);

			HBox hbxAvance3 = new HBox();
			ProgressBar progressBar3 = new ProgressBar(0);
			ProgressIndicator progressIndicator3 = new ProgressIndicator(0);
			hbxAvance3.getChildren().addAll(progressBar3, progressIndicator3);

			vbxProgreso.getChildren().addAll(hbxAvance, hbxAvance2, hbxAvance3);
		    root.setCenter(vbxProgreso);

			ServicioActualizaProgreso servAvance = new ServicioActualizaProgreso(txtRegistros,
					progressBar2, progressIndicator2);

			ScheduledServiceActualizaProgreso servProgramado = new ScheduledServiceActualizaProgreso(txtRegistros,
					progressBar3, progressIndicator3);

			servProgramado.setDelay(Duration.seconds(5));
			servProgramado.setPeriod(Duration.seconds(10));
			servProgramado.setMaximumFailureCount(10);

		    Button btnIniciar = new Button("Iniciar");
		    
		    //TODO: ¿Cómo se pone el tipo de evento
		    btnIniciar.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {	
					
					//try {
						/*Error de acceso a los componentes de la GUI desde otro hilo
						 * hiloOrdena hiloExterno = new hiloOrdena(txtRegistros, 
								progressBar, progressIndicator);
						hiloExterno.start();
						*/
						
						/* Sí permite que otro hilo actualice componentes GUI pero no en tiempo real
						Platform.runLater(new hiloOrdena(txtRegistros, 
								progressBar, progressIndicator));
						*/		

						/*
						tareaActualizaAvance tareaConcurrente = new tareaActualizaAvance(txtRegistros, 
								progressBar, progressIndicator);
						Thread hiloExterno = new Thread(tareaConcurrente);
						 */


						tareaActualizaAvance tareaConcurrente = new tareaActualizaAvance(txtRegistros,
								progressBar, progressIndicator);
						Thread hiloExterno = new Thread(tareaConcurrente);
						hiloExterno.setDaemon(true);
						hiloExterno.setPriority(1);
						hiloExterno.start();

/*
						tareaActualizaAvance tareaConcurrente2 = new tareaActualizaAvance(txtRegistros,
								progressBar2, progressIndicator2);
						Thread hiloExterno2 = new Thread(tareaConcurrente2);
						hiloExterno2.setPriority(5);
						hiloExterno2.setDaemon(true);
						hiloExterno2.start();

						tareaActualizaAvance tareaConcurrente3 = new tareaActualizaAvance(txtRegistros,
								progressBar3, progressIndicator3);
						Thread hiloExterno3 = new Thread(tareaConcurrente3);
						hiloExterno3.setPriority(10);
						hiloExterno3.setDaemon(true);
						hiloExterno3.start();
*/
						if (servAvance.isRunning() || (servAvance.getState() == Worker.State.SUCCEEDED))
							servAvance.restart();
						else
							if (servAvance.getState() == Worker.State.READY)
								servAvance.start();

						if (servProgramado.isRunning() || (servProgramado.getState() == Worker.State.SUCCEEDED))
							servProgramado.restart();
						else
							if (servProgramado.getState() == Worker.State.READY)
								servProgramado.start();

						//Thread.sleep(1000);
					//} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
				} 	
		    });
		    
		    root.setBottom(btnIniciar);
		    
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}