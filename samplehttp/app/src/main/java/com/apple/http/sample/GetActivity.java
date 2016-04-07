package com.apple.http.sample;

import com.apple.http.listener.HttpCallback;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


/**
 * get 请求数据
 *
 * @author hushaoping
 */
public class GetActivity extends AppCompatActivity {

    TextView txt_content;
    BaseParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        txt_content = (TextView) findViewById(R.id.txt_content);
    }


    /**
     * 普通参数请求方式
     * url 传入网络地址
     * put传入值
     * tag设置tag
     */
    public void getParam(View view) {
        BaseHttpClient.getBaseClient().newBuilder()
                .url("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                .put("appkey", "56065429")
                .setParse(UserBean.class)
                .put("sign", "AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB")
                .setTag("deals").build().execute(new HttpCallback() {
            @Override
            public void onSuccess(String content, BaseHttpClient object, Object parse) {
                Message msg = new Message();
                msg.obj = parse;
                msg.what = 1;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable error, BaseHttpClient client) {

            }
        });
    }

    /**
     * 普通参数请求方式
     * //通过可以设置tag或者addUrl添加url
     * put传入参数key:value
     * put("appkey", "56065429")
     */
    public void getParamXml(View view) {
        BaseHttpClient.getBaseClient().newBuilder()
                .url("http://sec.mobile.tiancity.com/server/mobilesecurity/version.xml").
                setTag("deals").build().execute(new HttpCallback() {
            @Override
            public void onSuccess(String content, BaseHttpClient object, Object parse) {
                Message msg = new Message();
                msg.obj = parse;
                msg.what =0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable error, BaseHttpClient object) {

            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //得到数据并去做解析类。
                    //    BaseEntity entity = JsonPaserFactory.paserObj(msg.obj.toString(), url);
                    //通知UI界面
//                    application/octet-stream
//                    multipart/form-data
                    txt_content.setText(msg.obj.toString() + "type===" );
                    break;
                case 1:
                    //得到数据并去做解析类。
                    //    BaseEntity entity = JsonPaserFactory.paserObj(msg.obj.toString(), url);
                    //通知UI界面
//                    application/octet-stream
//                    multipart/form-data
                    UserBean userBean=(UserBean)msg.obj;
                    txt_content.setText(userBean.getCities().get(0) + "type===" );

                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 初始化参数
     */
    protected void initParameter() {
        if (mParams == null) {
            mParams = new BaseParams();
        } else {
            mParams.clear();
        }
    }

}
