package com.oliveira.classificados.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveira.classificados.adapter.ListAdapter;
import com.oliveira.classificados.R;
import com.oliveira.classificados.database.model.Category;
import com.oliveira.classificados.database.model.ItemAd;
import com.oliveira.classificados.receiver.AlarmBroadcastReceiver;
import com.oliveira.classificados.task.LoadDataTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListActivity extends BaseActivity {

    private static final int REQUEST_PERMISSION_SMS = 1;
    private static final int REQUEST_PERMISSION_CALL_PHONE = 0;

    private RecyclerView mRvList;
    private ListAdapter mAdapter;
    private List<ItemAd> mItems;
    private ProgressBar mSpinner;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activiy_list);
        setupToolbar(R.string.list_activity_title);

        init();

        mItems = new ArrayList<>();
        mAdapter = new ListAdapter(this, mItems);
        mRvList.setAdapter(mAdapter);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new AddItemTask().execute("Novo Item");

            }
        });

        createAlarm();

        loadData();
    }

    private void loadData() {
        mItems.clear();

        mRvList.setVisibility(View.INVISIBLE);
        mSpinner.setVisibility(View.VISIBLE);

        final LoadDataTask loadDataTask = new LoadDataTask(mItems, mAdapter, this, mSpinner, mRvList, mTvProgress);
        loadDataTask.execute();
    }


    @Override
    protected void setupToolbar(@StringRes int title) {
        super.setupToolbar(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final Spinner spinner = (Spinner) findViewById(R.id.sp_category);

        final List<Category> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Category category = new Category(String.valueOf(i), String.format("Categoria %s", i));
            items.add(category);
        }

        final ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items);

        spinner.setAdapter(adapter);
    }

    private void init() {
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mSpinner = (ProgressBar) findViewById(R.id.spinner);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mTvProgress = (TextView) findViewById(R.id.tv_progress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Category category = (Category) data.getSerializableExtra(FilterActivity.CATEGORY_KEY);
        Toast.makeText(this, category.getDescription(), Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        //LoaderManager -> melhor maneira de atualizar os dados e comusumir os dados neste caso.

        loadData();
    }

    public void filter(View view) {
        final Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_view:
                if (mRvList.getLayoutManager() instanceof GridLayoutManager) {
                    mRvList.setLayoutManager(new LinearLayoutManager(this));
                } else {
                    mRvList.setLayoutManager(new GridLayoutManager(this, 2));
                }

                mRvList.getAdapter().notifyItemRangeChanged(0, mRvList.getAdapter().getItemCount());
                break;

            case R.id.action_toast:
                Toast.makeText(this, R.string.show_toast, Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_snackbar:
                final Snackbar snackbar = Snackbar.make(
                        mToolbar,
                        R.string.show_snackbar,
                        Snackbar.LENGTH_INDEFINITE);

                snackbar.setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog();
                    }
                }).show();

                break;
            case R.id.action_call:
                makeCall();
                break;

            case R.id.action_browser:
                final Intent intent2 =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(intent2);
                break;
            case R.id.action_request_sms:
                requestSms();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestSms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_DENIED
                ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                        == PackageManager.PERMISSION_DENIED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)
                    &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.RECEIVE_SMS)) {

                String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_SMS);
            }
        }
    }

    private void makeCall() {
        // Já tem a permissão
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            final Intent intent =
                    new Intent(Intent.ACTION_CALL, Uri.parse("tel:5505195848693"));
            startActivity(intent);
            // Android pedi a permissão para o usuário
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {

            String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CALL_PHONE);
        } else {
            Toast.makeText(this, R.string.request_permission, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name)
                .setMessage(R.string.my_mensage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults.length > 0)) {
            switch (requestCode) {
                case REQUEST_PERMISSION_CALL_PHONE:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        makeCall();
                    }

                    break;

                case REQUEST_PERMISSION_SMS:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, R.string.request_sms_enabled, Toast.LENGTH_SHORT).show();
                    break;

            }

        }

    }

    private void createAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.putExtra(AlarmBroadcastReceiver.MSG_KEY, "ALERT!!!");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 17);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 25);


        //RTC é o tempo corrente
        alarmManager.setRepeating(
                AlarmManager.RTC,
                calendar.getTimeInMillis(),
                60000,
                pendingIntent);
    }

    public void newItem(View view) {
        startActivity(new Intent(this, FormItemActivity.class));
    }

    class AddItemTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Thread.sleep(2 * 1000);// 2 segs

            } catch (InterruptedException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

            final String title = strings[0];
            mItems.add(0, new ItemAd(null, title, "Minha descrição " +
                    "do meu segundo item adicionado no meu layout da minha aplicação"));

            return Boolean.TRUE;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                mAdapter.notifyItemRangeChanged(0, mItems.size());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}
