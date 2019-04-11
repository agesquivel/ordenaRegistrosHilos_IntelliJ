package application;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

public class ScheduledServiceActualizaProgreso
        extends ScheduledService<String> {

    private TextArea atxMensaje;
    private ProgressBar barraProgreso;
    private ProgressIndicator indicadorProgreso;
    int numFallo;

    @Override
    protected void failed() {
        super.failed();
        System.out.println("Fallo núm. " + numFallo++);
    }


    public ScheduledServiceActualizaProgreso(TextArea atxMensaje,
                                     ProgressBar barraProgreso,
                                     ProgressIndicator indicadorProgreso) {
        this.atxMensaje = atxMensaje;
        this.barraProgreso = barraProgreso;
        this.indicadorProgreso = indicadorProgreso;
    }

    @Override
    protected Task<String> createTask()
    {
        return new tareaActualizaAvance(this.atxMensaje,
                this.barraProgreso,
                this.indicadorProgreso);
    }
}
