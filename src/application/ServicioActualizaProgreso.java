package application;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

public class ServicioActualizaProgreso extends Service<String> {

    private TextArea atxMensaje;
    private ProgressBar barraProgreso;
    private ProgressIndicator indicadorProgreso;

    public ServicioActualizaProgreso(TextArea atxMensaje,
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

