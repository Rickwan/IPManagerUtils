package cn.magicbeans.android.ipmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import cn.magicbeans.android.ipmanager.R;
import cn.magicbeans.android.ipmanager.adapter.MBIPAdapter;
import cn.magicbeans.android.ipmanager.utils.MBIPContant;

/**
 * IP历史记录
 */
public class MBIPActivity extends Activity implements View.OnClickListener {


    private ListView listView;

    private MBIPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        findViewById(R.id.mb_insert_tv).setOnClickListener(this);
        findViewById(R.id.back_iv).setOnClickListener(this);

        listView = (ListView) findViewById(R.id.ip_list_view);
        adapter = new MBIPAdapter(this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.mb_insert_tv) {
            Intent intent = new Intent(this, MBIPEditActivity.class);
            intent.putExtra(MBIPContant.TYPE, MBIPContant.OPERATE.INSERT.ordinal());
            startActivityForResult(intent, MBIPContant.REQUEST_CODE);
        } else if (v.getId() == R.id.back_iv) {
            finish();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MBIPContant.RESULT_CODE) {

            if (adapter != null) {
                adapter.refresh();
            }
        }
    }


}
