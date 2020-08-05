package com.swunion.googlemapsex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//http://data.seoul.go.kr/dataList/OA-15067/S/1/datasetView.do
public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<BusbayPosition> list;
    String key="705276555a6a6f6f3738416576656a"; //인증키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); //프래그먼트 생성
        mapFragment.getMapAsync(this);//맵을 불러옴

        TextView textView = findViewById(R.id.textView1);
        list = getData( "http://openapi.seoul.go.kr:8088/705276555a6a6f6f3738416576656a/xml/busStopLocationXyInfo/1/5/");
        String parsingList = "";
        for(int i=0;i<list.size();i++)
        {
            parsingList += list.get(i).getName();
            parsingList += "\n";
        }

        textView.setText(parsingList);

    }

    private List<BusbayPosition> getData(String URL) {
        XMLParsing xmlParsing = new XMLParsing(URL);
        List<BusbayPosition> list = new ArrayList<>();
        try {
            list = xmlParsing.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97); //위경도 좌표를 나타내는 클래스
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);//마커를 맵 객체에 추가함

        LatLng[] position = new LatLng[list.size()];
        MarkerOptions[] mo = new MarkerOptions[list.size()];
        for(int i=0;i<list.size();i++)
        {
            position[i] = new LatLng(Double.parseDouble(list.get(i).getY()), Double.parseDouble(list.get(i).getX()));
            mo[i] = new MarkerOptions();
            mo[i].position(position[i]);
            mo[i].title(list.get(i).getName());
            mo[i].snippet(list.get(i).getBusbayNo());
            System.out.println(mo[i].getTitle());
            mMap.addMarker(mo[i]);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));//카메라 설정
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

}


