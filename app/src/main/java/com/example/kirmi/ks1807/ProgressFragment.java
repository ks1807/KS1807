package com.example.kirmi.ks1807;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ProgressFragment extends Fragment {

    String UserID = "";
    LineGraphSeries<DataPoint> series, series2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progressfrag, null);

        UserID = getArguments().getString("UserID");

//        Double y, x;
//        x = -0.5;

        GraphView graph = (GraphView)view.findViewById(R.id.graph);
//        series = new LineGraphSeries<DataPoint>();
        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(2,5),
                new DataPoint(3,1),
                new DataPoint(5,6),
                new DataPoint(8,3)
        });

        series2 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2),
                new DataPoint(2,3),
                new DataPoint(4,6),
                new DataPoint(6,7),
                new DataPoint(8,4)
        });
//        for (int i =0; i < 100; i++) {
//            x = x + 0.1;
//            y =  Math.sin(x);
//            series.appendData(new DataPoint(x, y), true, 100);
//        }
        graph.addSeries(series);
        graph.addSeries(series2);
        series.setColor(Color.WHITE);
        series.setThickness(2);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(90, 255, 255, 255));
        series.setDrawDataPoints(true);
        series.setTitle("Mood");

        series2.setColor(Color.BLUE);
        series2.setThickness(2);
        series2.setDrawBackground(true);
        series2.setBackgroundColor(Color.argb(90, 73, 130, 203));
        series2.setDrawDataPoints(true);
        series2.setTitle("Genre");

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        return view;
    }
}
