package com.example.otaku;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//리사이틀러뷰에 표시할 아이템 뷰 생성
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MainHolder> {
    private String[] main_text, main_text2, main_text3;


    MainHolder mainHolder;

    //각 아이템 뷰에 들어갈 변수
    public CustomAdapter(String[] main_text, String[] main_text2, String[] cal){
        this.main_text = main_text;
        this.main_text2 = main_text2;
        this.main_text3 = cal;

    }

    //화면에 표시할 아이템 뷰 저장
    public static class MainHolder extends  RecyclerView.ViewHolder{
        public TextView main_text, main_text2, main_text3;
        public MainHolder(View view){
            super(view);
            //뷰객체에 대한 참조
            this.main_text = view.findViewById(R.id.text1);
            this.main_text2 = view.findViewById(R.id.text2);
            this.main_text3 = view.findViewById(R.id.text3);

        }
    }
    @NonNull
    @Override
    //아이템뷰를 위한 뷰홀더 객체를 생성하여 리턴
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        mainHolder = new MainHolder(holderView);
        return mainHolder;
    }

    @Override
    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        mainHolder.main_text.setText(this.main_text[i]);
        mainHolder.main_text2.setText(this.main_text2[i]);
        mainHolder.main_text3.setText(this.main_text3[i]);
    }
    @Override
    //전체 아이템의 갯수 리턴
    public int getItemCount() {
        return main_text.length;
    }
}
