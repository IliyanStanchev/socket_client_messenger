package bg.tu_varna.sit.si.socket_client_messenger.notifiers;

import android.content.Context;
import android.widget.Toast;

public class ToastNotifier {

    public static void notifyUser(Context context, String message){

        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
}
