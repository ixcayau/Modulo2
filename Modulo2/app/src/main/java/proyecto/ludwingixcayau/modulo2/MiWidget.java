package proyecto.ludwingixcayau.modulo2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by angelchanquin on 21/03/2015.
 */
public class MiWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for(int i = 0; i < appWidgetIds.length; i++){
            int widgetId = appWidgetIds[i];
            actualizarWidget(context, appWidgetManager, widgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent){
        Log.i("", "On receive: " + intent.getAction());

        if(intent.getAction().equals("proyecto.ludwingixcayau.modulo2.ACTUALIZAR_WIDGET")){
            int widgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
            );

           Log.i("", String.valueOf(widgetId) );

            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

            if(widgetId != AppWidgetManager.INVALID_APPWIDGET_ID){
                actualizarWidget(context, widgetManager, widgetId);
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        SharedPreferences preferences = context.getSharedPreferences("WidgetPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for(int i = 0; i < appWidgetIds.length; i++){
            int widgetId = appWidgetIds[i];
            editor.remove("msg_" + widgetId);
        }

        editor.commit();

        super.onDeleted(context, appWidgetIds);
    }

    public static void actualizarWidget(Context context, AppWidgetManager appWidgetManager, int widgetId){
        SharedPreferences preferences = context.getSharedPreferences("WidgetPreferences", Context.MODE_PRIVATE);

        String mensaje = preferences.getString("msg_" + widgetId, "Hora actual:");

        RemoteViews controles = new RemoteViews(context.getPackageName(), R.layout.miwidget);

        Intent intent = new Intent("proyecto.ludwingixcayau.modulo2.ACTUALIZAR_WIDGET");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.BtnActualizar, pendingIntent);

        Intent intent2 = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, widgetId, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        controles.setOnClickPendingIntent(R.id.FrmWidget, pendingIntent2);
        controles.setTextViewText(R.id.LblMensaje, mensaje);

        Calendar calendar = new GregorianCalendar();
        String hora = calendar.getTime().toLocaleString();
        controles.setTextViewText(R.id.LblHora, hora);

        appWidgetManager.updateAppWidget(widgetId, controles);
    }
}
