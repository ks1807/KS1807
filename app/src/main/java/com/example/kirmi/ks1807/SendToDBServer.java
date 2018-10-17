package com.example.kirmi.ks1807;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.os.Handler;

public class SendToDBServer
{
    /*NOTE String[] SendToServerString
    Is designed to have each string that we need to send through the API so it might be
    SendToServerString[0] = FirstName
    SendToServerString[1] = LastName

    Then concatenate them together for the URL string.

    Something like that anyway
    */

    private static final String RemoteServerConnectionURL = "???";
    private static final String APIName = "MMH-API_V1";

    public static JSONObject GetJSON(Context context, String[] SendToServerString)
    {
        String StringToSend = "";

        //Get all the string data that we want to send into a single string.
        for(int i = 0; i < SendToServerString.length; i++)
        {
            StringToSend = StringToSend + "\n" + SendToServerString[i];
        }

        //TEST
        StringToSend = "TEST";

        try
        {
            URL url = new URL(String.format(RemoteServerConnectionURL, StringToSend));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestProperty("User-Agent", APIName);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";

            while((tmp=reader.readLine())!= null)
            {
                json.append(tmp).append("\n");
            }
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not successful.
            if(data.getInt("cod") != 200)
            {
                return null;
            }
            return data;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    //Maybe this needs to be in a Main Activity
    private void SendToServer(final Context context, final String[] SendToServerString)
    {
        new Thread()
        {
            public void run()
            {
                final JSONObject json = GetJSON(context, SendToServerString);
                if(json == null)
                {
                    /*Handler.post(new Runnable()
                    {
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/
                }
                else
                {
                    /*handler.post(new Runnable()
                    {
                        public void run()
                        {
                            renderWeather(json);
                        }
                    });*/
                }
            }
        }.start();
    }

    //Should maybe be somewhere else?
    public void ConnectToAPI()
    {
        AsyncTask.execute(new Runnable()
        {
            @Override
            public void run()
            {
                // All your networking logic
                // should be here
            }
        });
    }
}
