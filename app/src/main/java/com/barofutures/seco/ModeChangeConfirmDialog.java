package com.barofutures.seco;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.barofutures.seco.fragments.Fragment_Home;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 *
 */
public class ModeChangeConfirmDialog {
    private Context context;

    public ModeChangeConfirmDialog(Context context) {
        this.context = context;
    }

    public interface UpdateUI{
        void activateChallengeMode();
        void deactivateChallengeMode();
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(boolean isChallenge, Context mainContext) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_modechangeconfirm);

        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        Button okButton = (Button) dlg.findViewById(R.id.dialog_modechangeconfirm_button_accept);
        Button cancelButton = (Button) dlg.findViewById(R.id.dialog_modechangeconfirm_button_cancel);
        TextView activateQ=(TextView)dlg.findViewById(R.id.dialog_modechangeconfirm_question_activate);
        TextView deactivateQ=(TextView)dlg.findViewById(R.id.dialog_modechangeconfirm_question_deactivate);
        TextView activateDesc=(TextView)dlg.findViewById(R.id.dialog_modechangeconfirm_description_activate);
        TextView deactivateDesc=(TextView)dlg.findViewById(R.id.dialog_modechangeconfirm_description_deactivate);

        if(isChallenge){
            deactivateQ.setVisibility(View.VISIBLE);
            deactivateDesc.setVisibility(View.VISIBLE);

            activateQ.setVisibility(View.INVISIBLE);
            activateDesc.setVisibility(View.INVISIBLE);
        }else{
            activateQ.setVisibility(View.VISIBLE);
            activateDesc.setVisibility(View.VISIBLE);

            deactivateQ.setVisibility(View.INVISIBLE);
            deactivateDesc.setVisibility(View.INVISIBLE);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UpdateUI updateUI= (UpdateUI) mainContext.getApplicationContext();
                if(isChallenge){
//                    modeText.setText("챌린지 모드 비활성화");
//                    updateUI.deactivateChallengeMode();
                    Fragment_Home.isChallenge=false;
                    Fragment_Home.update=true;

                }else{
//                    modeText.setText("챌린지 모드 활성화");
//                    updateUI.activateChallengeMode();
                    Fragment_Home.isChallenge=true;
                    Fragment_Home.update=true;
                }
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();
    }
}
