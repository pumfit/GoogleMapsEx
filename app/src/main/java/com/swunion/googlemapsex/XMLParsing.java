package com.swunion.googlemapsex;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParsing extends AsyncTask<Object, Object, List<BusbayPosition>> {
    private String URL;
    private Document doc = null;

    private static final String ROW = "row";
    private static final String NO = "STOP_NO";
    private static final String NAME = "STOP_NM";
    private static final String X = "XCODE";
    private static final String Y = "YCODE";

    public XMLParsing(String URL) {
        this.URL = URL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // 값을 가져오기 전에 하고 싶은 일 (프로그레스 바를 띄운다던가 등등)
    }

    @Override
    protected List<BusbayPosition> doInBackground(Object... params) {
        List<BusbayPosition> list = new ArrayList<>();
        try {
            java.net.URL url = new URL(URL);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
            doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
            doc.getDocumentElement().normalize();

            NodeList nodeListRow = doc.getElementsByTagName(ROW);
            System.out.println("길이"+nodeListRow.getLength());
            NodeList nodeListNo = doc.getElementsByTagName(NO);
            NodeList nodeListName = doc.getElementsByTagName(NAME);
            NodeList nodeListX = doc.getElementsByTagName(X);
            NodeList nodeListY = doc.getElementsByTagName(Y);

            for (int i = 0; i < nodeListRow.getLength(); i++) {
                String no = nodeListNo.item(i).getFirstChild().getNodeValue();
                String name = nodeListName.item(i).getFirstChild().getNodeValue();
                System.out.println("!!!!!name"+name);
                String x = nodeListX.item(i).getFirstChild().getNodeValue();
                String y = nodeListY.item(i).getFirstChild().getNodeValue();
                list.add(new BusbayPosition(no, name, x, y));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<BusbayPosition> BusbayPostions) {
        super.onPostExecute(BusbayPostions);
        // 값을 가져오고 나서 하는 일 (프로그레스바를 종료한다던가 등)
    }

}
