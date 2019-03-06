package es.example.ale.mislibrosfavoritos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Boolean> deshacerHabilitado;
    private boolean chequear;

    public MainActivityViewModel(){
        deshacerHabilitado = new MutableLiveData<>();
        chequear = true;
    }

    public boolean isChequear() {
        return chequear;
    }

    public void setChequear(boolean chequear) {
        this.chequear = chequear;
    }

    public LiveData<Boolean> getDeshacer(){
        return deshacerHabilitado;
    }

    public void setDeshacer(Boolean valor){
        deshacerHabilitado.setValue(valor);
    }

}
