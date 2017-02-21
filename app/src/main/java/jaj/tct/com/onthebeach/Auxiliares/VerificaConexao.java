package jaj.tct.com.onthebeach.Auxiliares;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Jocsa on 15/11/2016.
 */

public class VerificaConexao {

    private Context ctx;

    public VerificaConexao(Context ctx) {
        this.ctx = ctx;
    }

    public boolean existeConexao(){

        ConnectivityManager connectivity = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivity!=null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if(info==null){
                return false;
            }else{
                int tipoDeConexao = info.getType();
                if (tipoDeConexao == ConnectivityManager.TYPE_WIFI ||
                        tipoDeConexao == ConnectivityManager.TYPE_MOBILE) {
                    return info.isConnected();
                } else {
                    return false;
                }
            }
        }else{
            return false;
        }
    }
}
