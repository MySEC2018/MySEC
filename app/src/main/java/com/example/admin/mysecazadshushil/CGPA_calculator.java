package com.example.admin.mysecazadshushil;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.mysecazadshushil.Fragments.AlertDialogFragment;

public class CGPA_calculator extends AppCompatActivity {

    private Button newSemesterButton;
    private Button calculateCgpaButton;
    private Dialog mydialog;
    private LinearLayout linearLayoutOne, linearLayoutSingleTwo, linearLayoutSingleThree, linearLayoutSingleFour,
            linearLayoutSingleFive, linearLayoutSingleSix, linearLayoutSingleSeven, linearLayoutSingleEight,
            linearLayoutSingleNine, linearLayoutSingleTen, linearLayoutSingleEleven, linearLayoutSingleTwelve, linearLayoutSingleThirteen;
    private RelativeLayout relativeLayoutParent, relativeLayoutLast;
    RelativeLayout.LayoutParams layoutParams;
    private EditText creditOne, creditTwo, creditThree, creditFour, creditFive, creditSix, creditSeven, creditEight,
            creditNine, creditTen, creditEleven, creditTwelve;
    private EditText sgpaOne, sgpaTwo, sgpaThree, sgpaFour, sgpaFive, sgpaSix, sgpaSeven, sgpaEight,
            sgpaNine, sgpaTen, sgpaEleven, sgpaTwelve;
    int i = 2;
    int j = 1;
    double[] credits = new double[11];
    double[] sgpas = new double[11];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa_calculator);
        getSupportActionBar().setTitle("CGPA calculator");

        mydialog=new Dialog(this);
        init();
        for(int i=0; i<credits.length; i++)
        {
            credits[i]=0;
            sgpas[i]=0;
        }

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        newSemesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newButtonClicked();
            }
        });
        calculateCgpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((j==1 &&(creditOne.getText().toString().trim().isEmpty() || sgpaOne.getText().toString().trim().isEmpty() )))
                        || ((j==2 && (creditTwo.getText().toString().trim().isEmpty() || sgpaTwo.getText().toString().trim().isEmpty() )))
                        || ((j==3 && (creditThree.getText().toString().trim().isEmpty() || sgpaThree.getText().toString().trim().isEmpty() )))
                        || ((j==4 && (creditFour.getText().toString().trim().isEmpty() || sgpaFour.getText().toString().trim().isEmpty() )))
                        || ((j==5 && (creditFive.getText().toString().trim().isEmpty() || sgpaFive.getText().toString().trim().isEmpty() )))
                        || ((j==6 && (creditSix.getText().toString().trim().isEmpty() || sgpaSix.getText().toString().trim().isEmpty() )))
                        || ((j==7 && (creditSeven.getText().toString().trim().isEmpty() || sgpaSeven.getText().toString().trim().isEmpty() )))
                        || ((j==8 && (creditEight.getText().toString().trim().isEmpty() || sgpaEight.getText().toString().trim().isEmpty() )))
                        )
                {
                    Toast.makeText(CGPA_calculator.this, "Fields empty error", Toast.LENGTH_SHORT).show();
                }
                else if( (j==1 && Double.parseDouble(sgpaOne.getText().toString())>=4.1) || (j==2 &&Double.parseDouble(sgpaTwo.getText().toString())>=4.1)
                        || (j==3 && Double.parseDouble(sgpaThree.getText().toString())>=4.1) || (j==4 && Double.parseDouble(sgpaFour.getText().toString())>=4.1)
                        || (j==5 && Double.parseDouble(sgpaFive.getText().toString())>=4.1)|| (j==6 && Double.parseDouble(sgpaSix.getText().toString())>=4.1)
                        || (j==7 && Double.parseDouble(sgpaSeven.getText().toString())>=4.1) || (j==8 && Double.parseDouble(sgpaEight.getText().toString())>=4.1))
                {
                    Toast.makeText(CGPA_calculator.this, "CGPA can't be more than 4.0", Toast.LENGTH_SHORT).show();
                }
                else {
                    calculateCgpa();
                }
            }
        });
    }
    private void addingsection()
    {
        if (j == 1) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
        } else if (j == 2) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
        } else if (j == 3) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
            credits[2] = Double.parseDouble(creditThree.getText().toString());
            sgpas[2] = Double.parseDouble(sgpaThree.getText().toString());
        } else if (j == 4) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
            credits[2] = Double.parseDouble(creditThree.getText().toString());
            sgpas[2] = Double.parseDouble(sgpaThree.getText().toString());
            credits[3] = Double.parseDouble(creditFour.getText().toString());
            sgpas[3] = Double.parseDouble(sgpaFour.getText().toString());
        } else if (j == 5) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
            credits[2] = Double.parseDouble(creditThree.getText().toString());
            sgpas[2] = Double.parseDouble(sgpaThree.getText().toString());
            credits[3] = Double.parseDouble(creditFour.getText().toString());
            sgpas[3] = Double.parseDouble(sgpaFour.getText().toString());
            credits[4] = Double.parseDouble(creditFive.getText().toString());
            sgpas[4] = Double.parseDouble(sgpaFive.getText().toString());
        } else if (j == 6) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
            credits[2] = Double.parseDouble(creditThree.getText().toString());
            sgpas[2] = Double.parseDouble(sgpaThree.getText().toString());
            credits[3] = Double.parseDouble(creditFour.getText().toString());
            sgpas[3] = Double.parseDouble(sgpaFour.getText().toString());
            credits[4] = Double.parseDouble(creditFive.getText().toString());
            sgpas[4] = Double.parseDouble(sgpaFive.getText().toString());
            credits[5] = Double.parseDouble(creditSix.getText().toString());
            sgpas[5] = Double.parseDouble(sgpaSix.getText().toString());
        } else if (j == 7) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
            credits[2] = Double.parseDouble(creditThree.getText().toString());
            sgpas[2] = Double.parseDouble(sgpaThree.getText().toString());
            credits[3] = Double.parseDouble(creditFour.getText().toString());
            sgpas[3] = Double.parseDouble(sgpaFour.getText().toString());
            credits[4] = Double.parseDouble(creditFive.getText().toString());
            sgpas[4] = Double.parseDouble(sgpaFive.getText().toString());
            credits[5] = Double.parseDouble(creditSix.getText().toString());
            sgpas[5] = Double.parseDouble(sgpaSix.getText().toString());
            credits[6] = Double.parseDouble(creditSeven.getText().toString());
            sgpas[6] = Double.parseDouble(sgpaSeven.getText().toString());
        } else if (j == 8) {
            credits[0] = Double.parseDouble(creditOne.getText().toString());
            sgpas[0] = Double.parseDouble(sgpaOne.getText().toString());
            credits[1] = Double.parseDouble(creditTwo.getText().toString());
            sgpas[1] = Double.parseDouble(sgpaTwo.getText().toString());
            credits[2] = Double.parseDouble(creditThree.getText().toString());
            sgpas[2] = Double.parseDouble(sgpaThree.getText().toString());
            credits[3] = Double.parseDouble(creditFour.getText().toString());
            sgpas[3] = Double.parseDouble(sgpaFour.getText().toString());
            credits[4] = Double.parseDouble(creditFive.getText().toString());
            sgpas[4] = Double.parseDouble(sgpaFive.getText().toString());
            credits[5] = Double.parseDouble(creditSix.getText().toString());
            sgpas[5] = Double.parseDouble(sgpaSix.getText().toString());
            credits[6] = Double.parseDouble(creditSeven.getText().toString());
            sgpas[6] = Double.parseDouble(sgpaSeven.getText().toString());
            credits[7] = Double.parseDouble(creditEight.getText().toString());
            sgpas[7] = Double.parseDouble(sgpaEight.getText().toString());
        }
    }
    private void calculateCgpa() {
        addingsection();
        double totalCredit = 0.0;
        double finalCgpa = 0.0;
        double temp = 0.0;
        double totalSgpa = 0.0;
        int totalSemester = 0;

       /* if(j==1 && creditOne.getText().toString().trim().isEmpty() && sgpaOne.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Fields empty error", Toast.LENGTH_SHORT).show();
        }*/
        for (int k = 0; k < credits.length; k++) {
            if(credits[k] != 0.0 && sgpas[k] != 0.0){
                temp += (double) (credits[k] * sgpas[k]);
                totalCredit += credits[k];
                totalSgpa += sgpas[k];
                totalSemester += 1;
            }
        }
        finalCgpa = (temp / totalCredit);

        //mydialog.setContentView(R.layout.layout_custom_dialog_cgpa);
        //mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mydialog.show();


        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putDouble("totalSgpa", totalSgpa);
        args.putDouble("totalCgpa", finalCgpa);
        args.putDouble("totalCredit", totalCredit);
        args.putInt("totalSemester", totalSemester);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog");
        //dialogFragment.getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // Toast.makeText(this, String.valueOf(finalCgpa), Toast.LENGTH_SHORT).show();
    }

    private void init() {
        relativeLayoutLast = findViewById(R.id.relativeLayoutLast);
        linearLayoutOne = findViewById(R.id.linearLayoutOne);
        linearLayoutSingleTwo = findViewById(R.id.linearLayoutSingleTwo);
        linearLayoutSingleThree = findViewById(R.id.linearLayoutSingleThree);
        linearLayoutSingleFour = findViewById(R.id.linearLayoutSingleFour);
        linearLayoutSingleFive = findViewById(R.id.linearLayoutSingleFive);
        linearLayoutSingleSix = findViewById(R.id.linearLayoutSingleSix);
        linearLayoutSingleSeven = findViewById(R.id.linearLayoutSingleSeven);
        linearLayoutSingleEight = findViewById(R.id.linearLayoutSingleEight);
        linearLayoutSingleNine = findViewById(R.id.linearLayoutSingleNine);
        linearLayoutSingleTen = findViewById(R.id.linearLayoutSingleTen);
        linearLayoutSingleEleven = findViewById(R.id.linearLayoutSingleEleven);
        linearLayoutSingleTwelve = findViewById(R.id.linearLayoutSingleTwelve);
        linearLayoutSingleThirteen = findViewById(R.id.linearLayoutSingleThirteen);
        newSemesterButton = findViewById(R.id.newSemesterButton);
        calculateCgpaButton = findViewById(R.id.calculateCgpaButton);
        relativeLayoutParent = findViewById(R.id.relativeLayoutParent);

        creditOne = findViewById(R.id.creditOne);
        creditTwo = findViewById(R.id.creditTwo);
        creditThree = findViewById(R.id.creditThree);
        creditFour = findViewById(R.id.creditFour);
        creditFive = findViewById(R.id.creditFive);
        creditSix = findViewById(R.id.creditSix);
        creditSeven = findViewById(R.id.creditSeven);
        creditEight = findViewById(R.id.creditEight);
        creditNine = findViewById(R.id.creditNine);
        creditTen = findViewById(R.id.creditTen);
        creditEleven = findViewById(R.id.creditEleven);
        creditTwelve = findViewById(R.id.creditTwelve);

        sgpaOne = findViewById(R.id.sgpaOne);
        sgpaTwo = findViewById(R.id.sgpaTwo);
        sgpaThree = findViewById(R.id.sgpaThree);
        sgpaFour = findViewById(R.id.sgpaFour);
        sgpaFive = findViewById(R.id.sgpaFive);
        sgpaSix = findViewById(R.id.sgpaSix);
        sgpaSeven = findViewById(R.id.sgpaSeven);
        sgpaEight = findViewById(R.id.sgpaEight);
        sgpaNine = findViewById(R.id.sgpaNine);
        sgpaTen = findViewById(R.id.sgpaTen);
        sgpaEleven = findViewById(R.id.sgpaEleven);
        sgpaTwelve = findViewById(R.id.sgpaTwelve);
    }

    private void newButtonClicked() {
        if (i > 9) {
            Toast.makeText(getApplicationContext(), "Semester can't be more than 8", Toast.LENGTH_SHORT).show();
        } else if (i == 2) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleThree);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleThree.setVisibility(View.VISIBLE);
            i++;
        } else if (i == 3) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleFour);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleFour.setVisibility(View.VISIBLE);
            i++;
        } else if (i == 4) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleFive);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleFive.setVisibility(View.VISIBLE);
            i++;
        } else if (i == 5) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleSix);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleSix.setVisibility(View.VISIBLE);
            i++;
        } else if (i == 6) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleSeven);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleSeven.setVisibility(View.VISIBLE);
            i++;
        } else if (i == 7) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleEight);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleEight.setVisibility(View.VISIBLE);
            i++;
        } else if (i == 8) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSingleNine);
            relativeLayoutLast.setLayoutParams(layoutParams);
            linearLayoutSingleNine.setVisibility(View.VISIBLE);
            i++;
        }
        j++;
    }
}
