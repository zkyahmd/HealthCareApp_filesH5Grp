package com.example.healthcare_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    private String[][] doctor_details_one = {
            {"Doctor Name: Dr. Saman Perera", "Hospital Address: National Hospital, Colombo", "Experience: 10 years", "Contact No: 011-2691111","4000"},
            {"Doctor Name: Dr. Nisansala Fernando", "Hospital Address: Kandy Teaching Hospital, Kandy", "Experience: 8 years", "Contact No: 081-2204300","3000"},
            {"Doctor Name: Dr. Mohamed Rizvi", "Hospital Address: Karapitiya Teaching Hospital, Galle", "Experience: 12 years", "Contact No: 091-2232323","2000"},
            {"Doctor Name: Dr. Harsha Wijesinghe", "Hospital Address: Ragama Teaching Hospital, Ragama", "Experience: 15 years", "Contact No: 011-2958258","2000"},
            {"Doctor Name: Dr. Thilini Jayawardena", "Hospital Address: Jaffna Teaching Hospital, Jaffna", "Experience: 9 years", "Contact No: 021-2222266","2000"},
            {"Doctor Name: Dr. Lakmal de Silva", "Hospital Address: Kurunegala General Hospital, Kurunegala", "Experience: 7 years", "Contact No: 037-2222255","3000"},
            {"Doctor Name: Dr. Chathurika Senanayake", "Hospital Address: Kalutara General Hospital, Kalutara", "Experience: 6 years", "Contact No: 034-2231122","4500"}
    };
    private String[][] doctor_details_two = {
            {"Doctor Name: Dr. Anura Senaratne", "Hospital Address: Colombo South Teaching Hospital, Kalubowila", "Experience: 14 years", "Contact No: 011-2761026","4000"},
            {"Doctor Name: Dr. Ruwan Herath", "Hospital Address: Peradeniya Teaching Hospital, Peradeniya", "Experience: 11 years", "Contact No: 081-2388000","3000"},
            {"Doctor Name: Dr. Fathima Nazeer", "Hospital Address: Batticaloa Teaching Hospital, Batticaloa", "Experience: 10 years", "Contact No: 065-2222266","2000"},
            {"Doctor Name: Dr. Chamara Abeysinghe", "Hospital Address: Ratnapura General Hospital, Ratnapura", "Experience: 9 years", "Contact No: 045-2231123","1500"},
            {"Doctor Name: Dr. Indika Wijekoon", "Hospital Address: Matara General Hospital, Matara", "Experience: 13 years", "Contact No: 041-2222265","2000"},
            {"Doctor Name: Dr. Sanduni Wijesekera", "Hospital Address: Gampaha District General Hospital, Gampaha", "Experience: 7 years", "Contact No: 033-2231124","3500"},
            {"Doctor Name: Dr. Wasantha Karunaratne", "Hospital Address: Anuradhapura Teaching Hospital, Anuradhapura", "Experience: 12 years", "Contact No: 025-2222267","4500"}
    };

    private String[][] doctor_details_three = {
            {"Doctor Name: Dr. Dinesh Weerasinghe", "Hospital Address: Badulla General Hospital, Badulla", "Experience: 15 years", "Contact No: 055-2222255","4500"},
            {"Doctor Name: Dr. Asanka Rajapaksa", "Hospital Address: Polonnaruwa General Hospital, Polonnaruwa", "Experience: 10 years", "Contact No: 027-2222268","4500"},
            {"Doctor Name: Dr. Shanika Perera", "Hospital Address: Chilaw District General Hospital, Chilaw", "Experience: 8 years", "Contact No: 032-2221123","5000"},
            {"Doctor Name: Dr. Rukmal Liyanage", "Hospital Address: Monaragala General Hospital, Monaragala", "Experience: 9 years", "Contact No: 055-2231166","5000"},
            {"Doctor Name: Dr. Nadeesha Samarasinghe", "Hospital Address: Nuwara Eliya District General Hospital, Nuwara Eliya", "Experience: 11 years", "Contact No: 052-2222269","5000"},
            {"Doctor Name: Dr. Tharaka Fernando", "Hospital Address: Kegalle District General Hospital, Kegalle", "Experience: 6 years", "Contact No: 035-2231145","3500"},
            {"Doctor Name: Dr. Uditha Jayasundara", "Hospital Address: Vavuniya General Hospital, Vavuniya", "Experience: 13 years", "Contact No: 024-2222270","2500"}
    };

    private String[][] doctor_details_four = {
            {"Doctor Name: Dr. Kasun De Silva", "Hospital Address: Ampara General Hospital, Ampara", "Experience: 10 years", "Contact No: 063-2222256","2500"},
            {"Doctor Name: Dr. Sumudu Wijethunga", "Hospital Address: Trincomalee General Hospital, Trincomalee", "Experience: 12 years", "Contact No: 026-2222261","4500"},
            {"Doctor Name: Dr. Manjula Fernando", "Hospital Address: Puttalam District General Hospital, Puttalam", "Experience: 9 years", "Contact No: 032-2222272","5500"},
            {"Doctor Name: Dr. Harini Senarath", "Hospital Address: Hambantota General Hospital, Hambantota", "Experience: 7 years", "Contact No: 047-2222260","3000"},
            {"Doctor Name: Dr. Mahesh Karunarathna", "Hospital Address: Avissawella Base Hospital, Avissawella", "Experience: 8 years", "Contact No: 036-2222271","4000"},
            {"Doctor Name: Dr. Yohan Perera", "Hospital Address: Kalmunai North Base Hospital, Kalmunai", "Experience: 14 years", "Contact No: 067-2222257","5000"},
            {"Doctor Name: Dr. Rasika Jayawickrama", "Hospital Address: Warakapola Base Hospital, Warakapola", "Experience: 11 years", "Contact No: 035-2222263","6000"}
    };

    private String[][] doctor_details_five = {
            {"Doctor Name: Dr. Chamindi Jayasinghe", "Hospital Address: Balangoda Base Hospital, Balangoda", "Experience: 13 years", "Contact No: 045-2222258","4000"},
            {"Doctor Name: Dr. Nishantha Kumara", "Hospital Address: Mulleriyawa Base Hospital, Mulleriyawa", "Experience: 10 years", "Contact No: 011-2571234","5000"},
            {"Doctor Name: Dr. Sajith Weerakoon", "Hospital Address: Dambulla Base Hospital, Dambulla", "Experience: 7 years", "Contact No: 066-2222264","4000"},
            {"Doctor Name: Dr. Dinithi Gamage", "Hospital Address: Deniyaya Base Hospital, Deniyaya", "Experience: 9 years", "Contact No: 041-2222265","3000"},
            {"Doctor Name: Dr. Ruwanthi Ranasinghe", "Hospital Address: Kuliyapitiya Base Hospital, Kuliyapitiya", "Experience: 8 years", "Contact No: 037-2222259","2000"},
            {"Doctor Name: Dr. Suresh Fernando", "Hospital Address: Horana Base Hospital, Horana", "Experience: 12 years", "Contact No: 034-2222262","3500"},
            {"Doctor Name: Dr. Tharushi Ekanayake", "Hospital Address: Mahiyanganaya Base Hospital, Mahiyanganaya", "Experience: 11 years", "Contact No: 055-2222273","2500"}
    };
    TextView tv;
    Button btn;
    String[][] doctor_details={};
    HashMap<String,String>item;
    ArrayList list;
    SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv =findViewById(R.id.textViewHADTitle);
        btn = findViewById(R.id.buttonBMDAddToCart);
        Intent it= getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Family Physicians")==0)
            doctor_details = doctor_details_one;
        else
        if(title.compareTo("Dietitians")==0)
            doctor_details = doctor_details_two;
        else
        if(title.compareTo("Dentists")==0)
            doctor_details = doctor_details_three;
        else
        if(title.compareTo("Surgeons")==0)
            doctor_details = doctor_details_four;
        else
            doctor_details = doctor_details_five;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList();
        for (int i =0; i<doctor_details.length;i++){
            item = new HashMap<String,String>();
            item.put("lineOne",doctor_details[i][0]);
            item.put("lineTwo",doctor_details[i][1]);
            item.put("lineThree",doctor_details[i][2]);
            item.put("lineFour",doctor_details[i][3]);
            item.put("lineFive","Cons fees: "+ doctor_details[i][4]+"/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,R.layout.multi_lines,new String[]{"lineOne","lineTwo","lineThree","lineFour","lineFive"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});


        ListView lst = findViewById(R.id.listViewBM);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                        it.putExtra("text1",title);
                        it.putExtra("text2",doctor_details[i][0]);
                        it.putExtra("text3",doctor_details[i][1]);
                        it.putExtra("text4",doctor_details[i][3]);
                        it.putExtra("text5",doctor_details[i][4]);
                        startActivity(it);
                    }
                }
        );


    }
}