package com.example.yth.vollytest;

import android.util.Log;
import android.util.Xml;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by yth on 2016/1/3.
 */
public class XmlRequest extends Request<XmlPullParser> {
    private final Response.Listener<XmlPullParser> mListener;
    public XmlRequest(int method,String url, Response.Listener<XmlPullParser> listener,
                      Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }
    public XmlRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener) {

        this(Method.GET, url, listener, errorListener);
        Log.i("XmlRequest", "XmlRequest");
    }
    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse networkResponse) {
        Log.i("parseNetworkResponse","parseNetworkResponse");
        try {
            String xmlString =new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xmlString));
            return Response.success(parser, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }
//这样就可以将服务器响应的数据进行回调了。
    @Override
    protected void deliverResponse(XmlPullParser xmlPullParser) {
        Log.i("deliverResponse","deliverResponse");
        mListener.onResponse(xmlPullParser);
    }
}
