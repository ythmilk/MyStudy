package com.example.yth.vollytest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    Button btn;
    Button btnXml;
    TextView tv;
    Button btnxml2;
    List<Person> personList = new ArrayList<Person>();
    XmlPullParser parser = Xml.newPullParser();
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.image);
        btn = (Button) findViewById(R.id.btn);
        btnXml = (Button) findViewById(R.id.btnxml);
        btnxml2 = (Button) findViewById(R.id.btnxml2);
        btn3 = (Button) findViewById(R.id.btn3);
        tv = (TextView) findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sss", "....");
                try {
                    InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("text.txt"));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    String result = "";

                    while ((line = bufReader.readLine()) != null) {
                        result += line;
                    }
                    tv.setText(result);
                    Log.i("xml", result);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("xml", "cuowu");
                }
            }
        });

        //xml解析
        btnXml.setOnClickListener(new View.OnClickListener() {

            Person person;

            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                try {
                    InputStream is = getResources().getAssets().open("test.xml");
                    //创建XmlPullParser实例

                    Log.i("XmlPullParser.START_DOCUMENT", XmlPullParser.START_DOCUMENT + "");
                    Log.i("XmlPullParser.END_DOCUMENT ", XmlPullParser.END_DOCUMENT + "");
                    Log.i("XmlPullParser.START_TAG", XmlPullParser.START_TAG + "");
                    Log.i("XmlPullParser.END_TAG", XmlPullParser.END_TAG + "");
                    //设置输入流，并指明字符编码
                    parser.setInput(is, "utf-8");
                    //产生第一个事件
                    int evenType = parser.getEventType();
                    while (evenType != XmlPullParser.END_DOCUMENT) {
                        switch (evenType) {
                            case XmlPullParser.START_TAG:
                                Log.i("XmlPullParser.START_TAG", XmlPullParser.START_TAG + "");
                                if (parser.getName().equals("person")) {
                                    String idN = parser.getAttributeName(0);
                                    String idV = parser.getAttributeValue(0);
                                    Log.i(idN, idV);
                                    person = new Person();
                                } else if (parser.getName().equals("name")) {
                                    evenType = parser.next();
                                    person.setName(parser.getText());
                                } else if (parser.getName().equals("age")) {
                                    evenType = parser.next();
                                    person.setAge(parser.getText());
                                } else if (parser.getName().equals("sex")) {
                                    evenType = parser.next();
                                    person.setSex(parser.getText());
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("person")) {
                                    personList.add(person);
                                    person = null;
                                }
                                break;
                        }
                        evenType = parser.next();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                String info = "";
                for (Person p : personList) {
                    Log.i("personInfo", p.toString());
                    info += p.toString();

                }
                tv.setText(info);
            }
        });
        //Volley解析xml
        btn3.setOnClickListener(this);

//测试哈哈
        //xml生成
//        btnxml2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    XmlSerializer parser1=Xml.newSerializer();
//
//                    //String xmlString = parser1.serialize(personList); // 序列化
//                    FileOutputStream fos = openFileOutput("books.xml",
//                            Context.MODE_PRIVATE);
//                   // fos.write(xmlString.getBytes("UTF-8"));
//                } catch (Exception e) {
//                    Log.e("Exception", e.getMessage());
//                }
//            }
//        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        RequestQueue mQuenue = Volley.newRequestQueue(this);
        StringRequest strQ = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);
            }
        });
        //加载图片
        ImageRequest imgReq = new ImageRequest("http://www.5068.com/u/faceimg/20140804114111.jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mQuenue.add(strQ);
        mQuenue.add(imgReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //监听函数的实现
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn3:
                RequestQueue mQueue = Volley.newRequestQueue(this);
                Log.i("xml解析","wwwww");
                final XmlRequest xmlrequest = new XmlRequest("http://flash.weather.com.cn/wmaps/xml/china.xml", new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser parser) {
                        Log.i("进来了","s");
                        try {
                            int evenType=parser.getEventType();
                            while (evenType!=XmlPullParser.END_DOCUMENT){
                              //  Log.i("开始解析","xxx");
                                switch (evenType){
//                                    case XmlPullParser.START_DOCUMENT:
//                                        break;
                                    case XmlPullParser.START_TAG:
                                        if (parser.getName().equals("city")){
                                            Log.i(parser.getAttributeName(0),parser.getAttributeValue(0));
                                        }
                                        break;

                                }
                                evenType=parser.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("TAG", volleyError.getMessage(), volleyError);
                    }
                });
                mQueue.add(xmlrequest);
                break;
        }
    }
}
