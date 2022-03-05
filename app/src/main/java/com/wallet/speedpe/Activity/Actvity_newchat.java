package com.wallet.speedpe.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.wallet.speedpe.Adapter.RecyclerView_ChatMessage;
import com.wallet.speedpe.Adapter.RecyclerView_trash_category;
import com.wallet.speedpe.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Actvity_newchat extends AppCompatActivity implements View.OnClickListener {
    private AppCompatImageView message_img;
    private TextView txt_name;
    private EditText edt_mg;
    private ArrayList<String> chat = new ArrayList<>();
    private ArrayList<String> value = new ArrayList<>();
    private LinearLayout ivbackIcon,ivclose;
    private AppCompatImageButton btn_send;
    PopupWindow mypopupWindow;

    private ArrayList<String> category = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        ivbackIcon = findViewById(R.id.ivbackIcon);
        ivclose = findViewById(R.id.ivclose);

        ivbackIcon.setOnClickListener(this);
        ivclose.setOnClickListener(this);

        message_img = findViewById(R.id.message_img);
        txt_name = findViewById(R.id.txt_name);
        edt_mg = findViewById(R.id.edt_mg);

        btn_send =findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        Intent data = getIntent();
        String url  = data.getStringExtra("url");
        String name = data.getStringExtra("nam");
        String id = data.getStringExtra("id");

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(message_img);

        txt_name.setText(name);

        chat.add("It is a long established fact that a reader will be distracted by the");
        chat.add("It is a long established fact that a reader will be distracted by the");
        chat.add("It is a long established fact that a reader will be distracted by the");

        value.add("It is a long established fact that a reader will be distracted by the");
        value.add("It is a long established fact that a reader will be distracted by the");
        value.add("It is a long established fact that a reader will be distracted by the");

        category.add("I don't know");
        category.add("It's a spam or scam");
        category.add("It's inappropriate");
        category.add("It's harrasment");
        category.add("It's something else");

        if (id.equals("1")){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = findViewById(R.id.recycler_category );
            recyclerView.setLayoutManager(layoutManager);
            RecyclerView_ChatMessage adapter = new RecyclerView_ChatMessage(this,chat,value);
            recyclerView.setAdapter(adapter);
        }else{
            chat = new ArrayList<>();
            value = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerView = findViewById(R.id.recycler_category );
            recyclerView.setLayoutManager(layoutManager);
            RecyclerView_ChatMessage adapter = new RecyclerView_ChatMessage(this,chat,value);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.ivbackIcon:
                finish();
                break;

            case R.id.ivclose:
           /*     Intent gotochat = new Intent(Actvity_newchat.this,Activity_Message.class);
                startActivity(gotochat);*/
/*                mypopupWindow.showAsDropDown(view,-153,0);
                setPopUpWindow();*/

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.popup_menu, null);

                PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });

                RelativeLayout rel_archieve = popupView.findViewById(R.id.rel_archieve);
                RelativeLayout rel_trash = popupView.findViewById(R.id.rel_trash);
                RelativeLayout rel_report = popupView.findViewById(R.id.rel_report);

                rel_archieve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

                rel_trash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopUp_trash(view);
                    }
                });

                rel_report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopUp_report(view);
                    }
                });

                popupWindow.showAsDropDown(view);


          //      showPopupMenu(view, true, R.style.MyPopupOtherStyle);

                break;

            case R.id. btn_send:
/*                if (edt_mg.getText().toString().length()>0)
                {
                    chat.add(edt_mg.getText().toString());
                    edt_mg.setText("");
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    RecyclerView recyclerView = findViewById(R.id.recycler_category );
                    recyclerView.setLayoutManager(layoutManager);
                    RecyclerView_ChatMessage adapter = new RecyclerView_ChatMessage(this,chat,value);
                    recyclerView.setAdapter(adapter);
                }*/
                break;
        }
    }

    private void showPopUp_trash(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = layoutInflater.inflate(R.layout.custom_popup_trash_dialog, null);
        // reference the textview of custom_popup_dialog
        MaterialCardView tv = (MaterialCardView) customView.findViewById(R.id.btn_cancel);
        //this textview is from the adapter
        MaterialCardView text = (MaterialCardView) view.findViewById(R.id.btn_delete);

        builder.setView(customView);
        builder.create();
        builder.show();
    }

    private void showPopUp_report(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = layoutInflater.inflate(R.layout.custom_popup_report_dialog, null);

        LinearLayout ley_close = customView.findViewById(R.id.ley_close);

        ley_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = customView.findViewById(R.id.recycler_category );
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_trash_category adapter = new RecyclerView_trash_category(this,category);
        recyclerView.setAdapter(adapter);

        builder.setView(customView);
        builder.create();
        builder.show();
    }

    private void showPopupMenu(View anchor, boolean isWithIcons, int style) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(this, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);

        /*  The below code in try catch is responsible to display icons*/
        if (isWithIcons) {
            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //inflate menu
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_contact_us:
                        Toast.makeText(Actvity_newchat.this, "Contact us clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_terms_conditions:
                        Toast.makeText(Actvity_newchat.this, "Terms and Conditions clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_logout:
                        Toast.makeText(Actvity_newchat.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popup.show();

    }

    private void setPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_menu, null);
        mypopupWindow = new PopupWindow(view, 300, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
    }
}
