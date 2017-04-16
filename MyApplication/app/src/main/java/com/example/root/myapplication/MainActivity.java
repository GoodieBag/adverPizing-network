package com.example.root.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    TextView piIpAddress;
    Button getData, setData;

    String BROADCAST_IP = "255.255.255.255";
    int UDP_SEND_PORT = 9000;
    int UDP_RECEIVE_PORT = 12345;

    String ipFromPi = "";

    public MainActivity() throws UnknownHostException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        piIpAddress = (TextView)findViewById(R.id.piIpAddr);
        getData = (Button) findViewById(R.id.getipaddr);
        setData = (Button) findViewById(R.id.clear);

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res;
                res = udpSendAndReceive();
                if(res == 0) {
                    Toast.makeText(MainActivity.this, "Received Pi IP address!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Did not receive Pi IP address!", Toast.LENGTH_LONG).show();
                }

            }
        });

        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                piIpAddress.setText("");
            }
        });
    }


    private int udpSendAndReceive()  {
        int result = 0;
        String Msg = "Hello Pi! Whats your IP?";
        DatagramSocket tx = null, rx = null;
        try {
            //Broadcast message
            tx = new DatagramSocket();
            tx.setBroadcast(true);
            DatagramPacket dp;
            InetAddress broadcastIp = InetAddress.getByName(BROADCAST_IP);
            dp = new DatagramPacket(Msg.getBytes(), Msg.length(), broadcastIp, UDP_SEND_PORT);
            tx.send(dp);

            //Receive from PiZing
            byte[] messageFromServer = new byte[1024];
            rx = new DatagramSocket(UDP_RECEIVE_PORT);
            rx.setSoTimeout(10000);
            DatagramPacket packet;
            packet = new DatagramPacket(messageFromServer, messageFromServer.length);
            rx.receive(packet);
            ipFromPi = new String(messageFromServer, 0, packet.getLength());
            Log.d("Received text", ipFromPi);
            piIpAddress.setText(ipFromPi);
        } catch (SocketException e) {
            e.printStackTrace();
            result = -1;
        }catch (UnknownHostException e) {
            e.printStackTrace();
            result = -1;
        } catch (IOException e) {
            e.printStackTrace();
            result = -1;
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }finally {
            if (tx != null) {
                tx.close();
            }
            if (rx != null) {
                rx.close();
            }
        }

        return result;
    }

}
