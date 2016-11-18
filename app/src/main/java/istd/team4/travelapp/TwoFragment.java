package istd.team4.travelapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Button getLoButton = (Button) view.findViewById(R.id.getlocation);
        getLoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        final Button clearButton = (Button) view.findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

    }

    public void openMap(){
        EditText editText = (EditText) getView().findViewById(R.id.locationtext);
        String input = editText.getText().toString();
        String location = correcter(input);
        Uri gmmIntentUri = Uri.parse("geo:11.35,103.82?q="+Uri.encode(location));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void clear() {
        EditText editText = (EditText) getView().findViewById(R.id.locationtext);
        editText.setText("");
    }

    /* Places included
     Abdul Gaffoor Mosque
     Al-Abrar Mosque
     ArtScience Museum
     Asian Civilisations Museum
     Bright Hill Temple (Khong Meng San Phor Kark See Temple)
     Buddha Tooth Relic Temple
     Bukit Timah Nature Reserve
     Cathedral of the Good Shepherd
     Central Catchment Nature Reserve
     Central Sikh Temple
     Changi Prison Chapel and Museum
     Chijmes
     Chinatown Heritage Centre
     Chinese and Japanese Gardens
     Chinese Methodist Church
     Church of St Gregory the Illuminator
     Crane Dance
     East Coast Park


    */
    public static String correcter(String entry) {
        int index = -1;

        ArrayList<Pattern> pList = new ArrayList<>();
        ArrayList<Matcher> mList = new ArrayList<>();

        String edit = entry.toLowerCase();
        String correction;

        String[] locationList = {"Abdul Gaffoor Mosque",
                "Al-Abrar Mosque",
                "ArtScience Museum",
                "Asian Civilisations Museum",
                "Bright Hill Temple (Kong Meng San Phor Kark See Temple)",
                "Buddha Tooth Relic Temple",
                "Bukit Timah Nature Reserve",
                "Cathedral of the Good Shepherd",
                "Central Catchment Nature Reserve",
                "Central Sikh Temple",
                "Singapore Zoo",
                "Marina Barrage",
                "Kusu Island"};


        Pattern p1 = Pattern.compile("ab\\w{3,7}\\sgaf\\.{0,6}"); //Abdul Gaffoor Mosque
        pList.add(p1);
        Pattern p2 = Pattern.compile("al\\w{0,5}(\\s|-)?abr\\w{2,6}"); //Al-Abrar Mosque
        pList.add(p2);
        Pattern p3 = Pattern.compile("ar\\w{1,5}\\s?sci\\w{3,8}"); //ArtScience Museum
        pList.add(p3);
        Pattern p4 = Pattern.compile("as\\w{3,6}\\s?ci\\w{0,2}v"); //Asian Civilisations Museum
        pList.add(p4);
        Pattern p5 = Pattern.compile("br\\w{2,6}\\sh\\w{2,5}|kh\\w{2,5}\\sme\\w{2,6}\\ss\\w{2,5}"); //Bright hill
        pList.add(p5);
        Pattern p6 = Pattern.compile("bu\\w{2,8}\\sto\\w{2,6}"); //Buddah Tooth Relic
        pList.add(p6);
        Pattern p7 = Pattern.compile("bu\\w{1,6}\\sti\\w{2,6}\\sna\\w{3,7}"); //Bukit Timah Nature Reserve
        pList.add(p7);
        Pattern p8 = Pattern.compile("go\\w{2,4}\\sshe\\w{3,6}"); //Cathedral of the Good Shepherd
        pList.add(p8);
        Pattern p9 = Pattern.compile("ce\\w{4,7}\\sca\\w{6,9}"); //Central Catchment
        pList.add(p9);
        Pattern p10 = Pattern.compile("ce\\w{4,7}\\ssi\\w{2,4}"); //Central Sikh
        pList.add(p10);
        Pattern p11 = Pattern.compile("z\\w{1,4}"); //Singapore Zoo
        pList.add(p11);
        Pattern p12 = Pattern.compile("b\\w{0,4}rr\\w{1,4}"); //Marina Barrage
        pList.add(p12);
        Pattern p13 = Pattern.compile("k\\w{1,2}s\\w{2,4}"); //Kusu Island
        pList.add(p13);


        for (Pattern p: pList) {
            mList.add(p.matcher(edit));
        }

        for (Matcher m: mList) {
            if (m.find()) index = mList.indexOf(m);
            System.out.println(index);

        }
        correction = index >= 0 ? locationList[index] : entry;

        return correction;

    }

}