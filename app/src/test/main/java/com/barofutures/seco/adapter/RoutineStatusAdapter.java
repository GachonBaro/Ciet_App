package com.barofutures.seco.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.AuthorizeActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.model.RoutineStatus;
import com.skydoves.progressview.ProgressView;

public class RoutineStatusAdapter extends RecyclerView.Adapter<RoutineStatusAdapter.ViewHolder>{
    private Boolean isChallenge;

    // Constructor
    public RoutineStatusAdapter(boolean isChallenge){this.isChallenge =isChallenge;}

    // View Holder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, carbon, calories, condition;
        ProgressView progressView;
        Button submit, menuSelect;
        ImageView icon;

        public ViewHolder(View itemView){
            super(itemView);

            title=itemView.findViewById(R.id.routine_status_title_text);
            carbon=itemView.findViewById(R.id.routine_status_carbon_reduction);
            calories=itemView.findViewById(R.id.routine_status_calories);
            progressView=itemView.findViewById(R.id.routine_status_progressbar);
            condition=itemView.findViewById(R.id.routine_status_description_condition);
            submit=itemView.findViewById(R.id.routine_status_button_authorize);
            menuSelect=itemView.findViewById(R.id.routine_status_button_menu_select);
            icon=itemView.findViewById(R.id.routine_status_icon);
        }
    }

    @Override
    public RoutineStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_routine_status, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoutineStatusAdapter.ViewHolder holder, int position) {
        // 활동/식단 이름, 뱃지 조건, 탄소 저감량, 칼로리 소모량의 순서
        if(isChallenge){
            holder.title.setText(RoutineStatus.challengeRoutineStatusList[position][0]);
            holder.icon.setImageResource(holder.icon.getContext().getResources().getIdentifier(RoutineStatus.challengeRoutineStatusList[position][1], "drawable", holder.icon.getContext().getPackageName()));
            holder.condition.setText(RoutineStatus.challengeRoutineStatusList[position][2]);
            holder.carbon.setText(RoutineStatus.challengeRoutineStatusList[position][3]);
            holder.calories.setText(RoutineStatus.challengeRoutineStatusList[position][4]);
            if(Boolean.valueOf(RoutineStatus.challengeRoutineStatusList[position][5])){
                holder.menuSelect.setVisibility(View.VISIBLE);
                if(isChallenge){
                    holder.menuSelect.setText("활성화 하기");
                }
            }else{
                holder.menuSelect.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.title.setText(RoutineStatus.freeRoutineStatusList[position][0]);
            holder.icon.setImageResource(holder.icon.getContext().getResources().getIdentifier(RoutineStatus.freeRoutineStatusList[position][1], "drawable", holder.icon.getContext().getPackageName()));
            holder.condition.setText(RoutineStatus.freeRoutineStatusList[position][2]);
            holder.carbon.setText(RoutineStatus.freeRoutineStatusList[position][3]);
            holder.calories.setText(RoutineStatus.freeRoutineStatusList[position][4]);
            if(Boolean.valueOf(RoutineStatus.freeRoutineStatusList[position][5])){
                holder.menuSelect.setVisibility(View.VISIBLE);
                if(isChallenge){
                    holder.menuSelect.setText("활성화 하기");
                }
            }else{
                holder.menuSelect.setVisibility(View.INVISIBLE);
            }
        }


        // 인증버튼
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= v.getContext();
                Intent intent=new Intent(context, AuthorizeActivity.class);
                context.startActivity(intent);
            }
        });

        // 메뉴 선택 버튼
        holder.menuSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(v.getContext(), MainActivity.class);
//                v.getContext().startActivity(intent);
//                (fr)v.getContext()
//                Fragment fragment=new Fragment_Recipe();
//                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
//                FragmentTransaction transaction=manager.beginTransaction();
//                transaction.replace(R.id.main_layout, fragment);
//                transaction.commit();
//                UpdateUI updateUI=(UpdateUI) fragment.getActivity();
//                if(updateUI!=null){
//                    updateUI.setBottomNavButtonToRecipe();
//                }
            }
        });
    }
    
    public interface UpdateUI{
        void setBottomNavButtonToRecipe();
    }

    @Override
    public int getItemCount() {
        if (isChallenge) {
            return RoutineStatus.challengeRoutineStatusList.length;
        } else {
            return RoutineStatus.freeRoutineStatusList.length;
        }
    }
}
