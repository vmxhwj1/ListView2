package pk.co.kr.a0515listview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ListView listView;

    EditText edit;
    Button add, delete;


    //키보드를 제어할 수 있는 변수
    InputMethodManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.listView);
        add = (Button) findViewById(R.id.add);
        delete = (Button) findViewById(R.id.delete);
        edit = (EditText) findViewById(R.id.edit);

        //키보드 제어 객체 생성
        manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);


        listView.setOnTouchListener(new LinearLayout.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //키보드 숨기기
                manager.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                return false;

            }

        });


        //ListView에 출력할 데이터 생성
        list = new ArrayList<>();
        list.add("Oracle");
        list.add("MySQL");
        list.add("MSSQL");
        list.add("MongoDB");

        //하나의 항목을 선택할 수 있는 어댑터 만들기
        adapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_multiple_choice, list);

        //listView에 어댑터를 연결해서 출력
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        //ListView에서 항목을 선택했을 때의 이벤트 처리
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){
            //첫번째 매개변수는 ListView
            //두번째 매개변수는 선택한 항목 뷰
            //세번째 매개변수는 선택한 인덱스
            //네번쨰 매개 변수는 선택한 항목 뷰의 아이디
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //선택한 데이터 가져오기
                 String content = list.get(position);
                 //Toast(작읍알림 창)로 출력
                Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG).show();
            }
        });

        //추가버튼을 클릭했을 때의 이벤트 처리
        add.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //입력된 내용 가져오기
                String item = edit.getText().toString();
                //데이터 추가
                list.add(item);
                //ListView에게 데이터가 변경되었다고 알려주기 - ListView가 다시 출력함
                adapter.notifyDataSetChanged();
                edit.setText("");
            }
        });

        //삭제 버튼을 눌렀을 때의 이벤트 처리
        delete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //다중 선택 모드 처리
                //모튼 항목의 선택여부를 sb에 저장
                SparseBooleanArray sb = listView.getCheckedItemPositions();
                //맨 뒤에서부터 앞쪽으로 이동하면서 선택된 항목만 삭제
                for(int i = list.size()-1; i>=0; i=i-1){
                    if(sb.get(i) == true){
                        list.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                listView.clearChoices();


                /*
                //ListView에서 선택된 항목의 인덱스 가져오기
                int pos = listView.getCheckedItemPosition();
                //선택 된 경우만
                if(pos >= 0 && pos < list.size()){
                    //list에서 데이터 삭제
                    list.remove(pos);
                    //데이터를 다시 출력
                    adapter.notifyDataSetChanged();
                    //기존 선택을 해제
                    listView.clearChoices();
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "선택된 데이터가 없습니다.", Toast.LENGTH_LONG).show();
                }
                */
            }

        });

    }
}
