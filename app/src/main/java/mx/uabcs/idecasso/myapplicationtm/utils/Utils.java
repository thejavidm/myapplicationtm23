package mx.uabcs.idecasso.myapplicationtm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import mx.uabcs.idecasso.myapplicationtm.R;

import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.*;

public class Utils {

    public static void openActivity(Context context, Class clazz){
        Intent mIntent = new Intent(context, clazz);
        Bundle options = new Bundle();
        ActivityCompat.startActivity(context, mIntent, options);
    }

    public static void openActivityForResult(Activity activity, Class clazz, int requestCode){
        Intent mIntent = new Intent(activity, clazz);
        Bundle options = new Bundle();
        options.putInt(REQUEST_CODE, requestCode);
        ActivityCompat.startActivityForResult(activity, mIntent, requestCode, options);
    }

    public static void call(Context context, @NonNull String num){
        Intent mIntent = new Intent(Intent.ACTION_DIAL);
        mIntent.setData(Uri.parse("tel:" + num));
        Bundle options = new Bundle();
        ActivityCompat.startActivity(context, mIntent, options);
    }

    public static void openLink(Activity mActivity, String url){
        String urlNormal = url.replaceAll(Constants.HTTP, EMPTY_STRING);
        urlNormal = urlNormal.replaceAll(HTTPS, EMPTY_STRING);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                mActivity.getString(R.string.url, urlNormal)));
        Bundle options = new Bundle();
        ActivityCompat.startActivity(mActivity, intent, options);
    }

    public static void sendSMS(Activity mActivity, String phone, String message){

        Uri smsUri = Uri.parse(SMS_SMSTO + phone);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
        smsIntent.putExtra(SMS_BODY, message);
        Bundle options = new Bundle();
        ActivityCompat.startActivity(mActivity, smsIntent, options);
    }

    public static void sendWhatsapp(Activity mActivity, String phone, String message){
        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
        String uri = WHATSAPP_SEND_PHONE + WHATSAPP_COUNTRY_CODE + phone + WHATSAPP_TEXT + message;
        whatsappIntent.setData(Uri.parse(uri));
        Bundle options = new Bundle();
        try {
            ActivityCompat.startActivity(mActivity, whatsappIntent, options);
        }catch(Exception ex){
            Log.i("whatsapp", "Whatsapp no está instalado");
        }
    }
}
