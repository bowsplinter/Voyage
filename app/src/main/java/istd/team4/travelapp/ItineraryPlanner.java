package istd.team4.travelapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItineraryPlanner extends Fragment {
    static Route rt;
    static String[] placeNames = {"Marina Bay Sands", "Singapore Flyer", "Vivo City", "Resorts World Sentosa", "Buddha Tooth Relic Temple", "Zoo"};

    public ItineraryPlanner() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double[][][] WALKraw = {{{}, {0,14}, {0,69}, {0,76}, {0,28}, {0,269}}, {{0,14}, {}, {0,81}, {0,88}, {0,39}, {0,264}}, {{0,69}, {0,81}, {}, {0,12}, {0,47}, {0,270}}, {{0,76}, {0,88}, {0,12}, {}, {0,55}, {0,285}}, {{0,28}, {0,39}, {0,47}, {0,55}, {}, {0,264}}, {{0,269}, {0,264}, {0,270}, {0,285}, {0,264}, {}}};
        double[][][] PTraw = {{{}, {0.83,17}, {1.18,26}, {4.03,35}, {0.88,19}, {1.96,84}}, {{0.83,17}, {}, {1.26,31}, {4.03,38}, {0.98,24}, {1.89,85}}, {{1.18,24}, {1.26,29}, {}, {2,10}, {0.98,18}, {1.99,85}}, {{1.18,33}, {1.26,38}, {0,10}, {}, {0.98,27}, {1.99,92}}, {{0.88,18}, {0.98,23}, {0.98,19}, {3.98,28}, {}, {1.91,83}}, {{1.88,86}, {1.96,87}, {2.11,86}, {4.99,96}, {1.91,84}, {}}};
        double[][][] TAXIraw = {{{}, {3.22,3}, {6.96,14}, {8.5,19}, {4.98,8}, {18.4,30}}, {{4.32,6}, {}, {7.84,13}, {9.38,18}, {4.76,8}, {18.18,29}}, {{8.3,12}, {7.96,14}, {}, {4.54,9}, {6.42,11}, {22.58,31}}, {{8.74,13}, {8.4,14}, {3.22,4}, {}, {6.64,12}, {22.8,32}}, {{5.32,7}, {4.76,8}, {4.98,9}, {6.52,14}, {}, {18.4,30}}, {{22.48,32}, {19.4,29}, {21.48,32}, {23.68,36}, {21.6,30}, {}}};
        rt = new Route(placeNames, WALKraw, PTraw, TAXIraw);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        v.findViewById(R.id.bestbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBestRoute();
            }
        });
        v.findViewById(R.id.approxbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApprox();
            }
        });
        return v;
    }

    public ArrayList getBestRoute() {
        ArrayList<String> chosenPlaces = new ArrayList<>();
        int[] checkboxes = {R.id.cbFlyer, R.id.cbVivo, R.id.cbRWS, R.id.cbBuddha, R.id.cbZoo};
        for (int i=0; i<checkboxes.length; i++) {
            CheckBox cb = (CheckBox) getView().findViewById(checkboxes[i]);
            if (cb.isChecked()) chosenPlaces.add(placeNames[i+1]);
        }
        EditText budgetInp = (EditText) getView().findViewById(R.id.budget);
        double budget = Double.parseDouble(budgetInp.getText().toString());

        TextView output = (TextView) getView().findViewById(R.id.textView);
        ArrayList bestroute = new ArrayList<>();
        if (budget <= 0) output.setText("Invalid budget");
        else if (chosenPlaces.size() == 0) output.setText("No places selected");
        else {
            bestroute = rt.getBestRoute(chosenPlaces,budget);
            ArrayList transport = (ArrayList) bestroute.get(2);
            ArrayList places = (ArrayList) bestroute.get(3);
            String displaystr = "";
            for (int i=0; i<transport.size(); i++) {
                displaystr += String.format("From %s, %s to %s\n", places.get(i), transport.get(i), places.get(i+1));
            }
            displaystr += String.format("\nTotal time: %.0fmin\n", bestroute.get(0));
            displaystr += String.format("Total cost: $%.2f", bestroute.get(1));
            output.setText(displaystr);
        }

        return bestroute;
    }

    public ArrayList getApprox() {
        ArrayList<String> chosenPlaces = new ArrayList<>();
        int[] checkboxes = {R.id.cbFlyer, R.id.cbVivo, R.id.cbRWS, R.id.cbBuddha, R.id.cbZoo};
        for (int i=0; i<checkboxes.length; i++) {
            CheckBox cb = (CheckBox) getView().findViewById(checkboxes[i]);
            if (cb.isChecked()) chosenPlaces.add(placeNames[i+1]);
        }
        EditText budgetInp = (EditText) getView().findViewById(R.id.budget);
        double budget = Double.parseDouble(budgetInp.getText().toString());

        TextView output = (TextView) getView().findViewById(R.id.textView);
        ArrayList bestroute = new ArrayList<>();
        if (budget <= 0) output.setText("Invalid budget");
        else if (chosenPlaces.size() == 0) output.setText("No places selected");
        else {
            bestroute = rt.getApproxBestRoute(chosenPlaces,budget);
            ArrayList transport = (ArrayList) bestroute.get(2);
            ArrayList places = (ArrayList) bestroute.get(3);
            String displaystr = "";
            for (int i=0; i<transport.size(); i++) {
                displaystr += String.format("From %s, %s to %s\n", places.get(i), transport.get(i), places.get(i+1));
            }
            displaystr += String.format("\nTotal time: %.0fmin\n", bestroute.get(0));
            displaystr += String.format("Total cost: $%.2f", bestroute.get(1));
            output.setText(displaystr);
        }

        return bestroute;
    }

}
