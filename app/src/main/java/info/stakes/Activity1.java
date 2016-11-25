package info.stakes;

import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Activity1 extends AppCompatActivity {

    //Remaining Code Section
    TextView tv;
    RecyclerView rv;
    RecyclerView.LayoutManager llm;
    RecyclerView.Adapter adapter;
    ArrayList<MainModelactivity1> list = new ArrayList<>();

    Button add;
    Button deleteall;

    EditText percent;
    AutoCompleteTextView field;
    TextView total;
    int sum;
    Button done;

    ImageView next;
    ImageView previous;
    RelativeLayout rl;

    //Pie Chart Section

    LinearLayout mainLayout;
    PieChart mChart;
    static ArrayList<Float> yData1 = new ArrayList<>();
    static ArrayList<String> xData1 = new ArrayList<>();

    Float[] yData = null;
    String[] xData = null;

    DatabaseHelperSchedule db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);


        next = (ImageView) findViewById(R.id.circlelefta1);
        previous = (ImageView) findViewById(R.id.circlerighta1);
        rl = (RelativeLayout) findViewById(R.id.rla1);

        tv = (TextView) findViewById(R.id.tv);
        add = (Button) findViewById(R.id.add);
        total = (TextView) findViewById(R.id.total);
        deleteall = (Button) findViewById(R.id.deleteall);
        done = (Button) findViewById(R.id.btn);

        db = new DatabaseHelperSchedule(getApplicationContext());

        //TextView Adapter
        String array[] = {"Entertainment", "Travelling", "Food", "Household Works", "Sectional"};
        ArrayAdapter<String> adapt;

        tv.setText("Hey Guys, Here you need to enter the fields where you spend your money" +
                "on regular basis, also you need to specify the % of your expenditure on" +
                "that particular place ! ");

        rv = (RecyclerView) findViewById(R.id.rv);
        llm = new LinearLayoutManager(Activity1.this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        field = (AutoCompleteTextView) findViewById(R.id.et1);
        adapt = new ArrayAdapter<String>(Activity1.this,
                android.R.layout.simple_list_item_1, array);

        field.setAdapter(adapt);

        percent = (EditText) findViewById(R.id.et2);


        //Previous icon disabled
        previous.setEnabled(false);
        done.setEnabled(false);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //rl.setVisibility(View.INVISIBLE);

                    String s1 = field.getText().toString();
                    String s = percent.getText().toString();
                    int s2 = Integer.parseInt(s);


                    if (s2 < 100 && sum <= 100) {

                        boolean inserted = db.insertschedule(s1, s2, 0);

                        if (inserted) {

                            sum = sum + s2;
                            Float f = new Float(s2);
                            total.setText(" " + sum);


                            yData1.add(f);
                            xData1.add(s1);


                            list.add(new MainModelactivity1(s1, s2));

                            /*if (sum > 100) {
                                sum = sum - s2;
                                Toast.makeText(getApplicationContext(), "Add a proper number" +
                                        "", Toast.LENGTH_SHORT).show();
                            }*/

                            if (sum == 100) {
                                add.setEnabled(false);
                                done.setEnabled(true);
                                deleteall.setEnabled(false);
                            }


                            adapter = new MainAdapteractivity1(getApplicationContext(), list);
                            rv.setAdapter(adapter);
                        } else {

                            Toast.makeText(getApplicationContext(), "Can't Insert, Duplication ! ", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Issue with Sum or Number! ", Toast.LENGTH_LONG).show();
                    }


                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Add Elements", Toast.LENGTH_SHORT).show();
                }


            }
        });


       deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                add.setEnabled(true);

                //s1=" ";
                //s2=0;
                sum = 0;

                adapter=new MainAdapteractivity1(getApplicationContext(),list);
               //adapter.removeV();
                rv.setAdapter(adapter);
                ((ViewGroup)rv.getParent()).removeView(rv);

                Log.d("listall",""+list);
                Log.d("sumall",""+sum);
                Toast.makeText(getApplicationContext(), "Deleted all the Elements",
                        Toast.LENGTH_SHORT).show();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setEnabled(false);
                deleteall.setEnabled(false);
            }
        });



        Log.d("Outside Method", "" + yData1);
        Log.d("Outside Method", " " + xData1);


        try {


            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Inside Method", "" + yData1);
                    Log.d("Inside Method", " " + xData1);


                    yData = new Float[yData1.size()];
                    yData = yData1.toArray(yData);

                    xData = new String[xData1.size()];
                    xData = xData1.toArray(xData);

                    xData1 = new ArrayList<String>();
                    yData1 = new ArrayList<Float>();

                    mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                    mChart = (PieChart) findViewById(R.id.piechart);
                    //mainLayout.addView(mChart);
                    ViewPortHandler handler = mChart.getViewPortHandler();

                    float f1 = handler.contentWidth();

                    Log.d("width", " " + f1);

                    float f2 = handler.contentHeight();
                    Log.d("height", " " + f2);

                    //handler.setChartDimens(200f,500f);
                    Log.d("width", " " + handler.getChartHeight());
                    Log.d("height", " " + handler.getChartWidth());

                    mainLayout.setBackgroundColor(Color.parseColor("#55656C"));
                    mChart.setUsePercentValues(false);
                    mChart.setDescription("Your Daily Activity Chart");

                    mChart.setDrawHoleEnabled(true);
                    mChart.setHoleColor(Color.GRAY);
                    mChart.setHoleRadius(5);
                    mChart.setTransparentCircleRadius(10);

                    mChart.setRotationAngle(0);
                    mChart.setRotationEnabled(true);

                    mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                                                               @Override
                                                               public void onValueSelected(Entry entry, int i, Highlight highlight) {
                                                                   if (entry == null) {
                                                                       Toast.makeText(Activity1.this, xData[entry.getXIndex()] + "=" + entry.getVal() +
                                                                               "%", Toast.LENGTH_SHORT).show();
                                                                       return;
                                                                   }
                                                               }

                                                               @Override
                                                               public void onNothingSelected() {

                                                               }
                                                           }

                    );

                    addData();

                    Legend l = mChart.getLegend();
                    l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
                    l.setXEntrySpace(7);
                    l.setYEntrySpace(5);
                }

                private void addData() {
                    ArrayList<Entry> yVal1 = new ArrayList<Entry>();
                    for (int i = 0; i < yData.length; i++) {
                        yVal1.add(new Entry(yData[i], i));
                    }
                    ArrayList<String> vals = new ArrayList<String>();
                    for (int i = 0; i < xData.length; i++) {
                        vals.add(xData[i]);
                    }

                    PieDataSet dataSet = new PieDataSet(yVal1, "Share");

                    dataSet.setSliceSpace(3);
                    dataSet.setSelectionShift(5);

                    ArrayList<Integer> colors = new ArrayList<Integer>();

                    for (int c : ColorTemplate.VORDIPLOM_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.JOYFUL_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.COLORFUL_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.PASTEL_COLORS)
                        colors.add(c);

                    colors.add(ColorTemplate.getHoloBlue());
                    dataSet.setColors(colors);

                    PieData data = new PieData(vals, dataSet);
                    //data.setValueFormatter(new PercentFormatter());
                    dataSet.setValueTextSize(20f);
                    dataSet.setValueTextColor(Color.GRAY);

                    mChart.setData(data);
                    mChart.highlightValue(null);
                    mChart.invalidate();

                    rl.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity1.this, AskDailyActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getApplicationContext().deleteDatabase("ScheduleDatabase.db");
    }
}
