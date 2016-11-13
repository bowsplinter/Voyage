package istd.team4.travelapp;

/*
 *  class for ItineraryPlanner
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Route {
    HashMap<String, HashMap<String, double[]>> walkdata = new HashMap<>();
    HashMap<String, HashMap<String, double[]>> taxidata = new HashMap<>();
    HashMap<String, HashMap<String, double[]>> ptdata = new HashMap<>();

    Route(String[] places, double[][][] walkraw, double[][][] ptraw, double[][][] taxiraw) {
        this.walkdata = processData(places, walkraw);
        this.ptdata = processData(places, ptraw);
        this.taxidata = processData(places, taxiraw);
    }

    public static HashMap<String, HashMap<String, double[]>> processData(String[] places, double[][][] rawData) {
        HashMap<String, HashMap<String, double[]>> output = new HashMap<>();
        for (int i=0; i<places.length; i++) {
            output.put(places[i], new HashMap<String, double[]>());
            for (int j=0; j<places.length; j++) {
                if (rawData[i][j].length != 0) {
                    output.get(places[i]).put(places[j], rawData[i][j]);
                }
            }
        }
        return output;
    }

    public static double getCost(String p1, String p2, Map<String, HashMap<String, double[]>> data) {
        return data.get(p1).get(p2)[0];
    }

    public static double getTime(String p1, String p2, Map<String, HashMap<String, double[]>> data) {
        return data.get(p1).get(p2)[1];
    }

    private static ArrayList<ArrayList> permutePlaces(ArrayList<String> prefix, ArrayList<String> str, ArrayList<ArrayList> op) {
        int n = str.size();
        if (n == 0) {
            prefix.add("Marina Bay Sands");
            op.add(prefix);
        }
        else {
            for (int i = 0; i < n; i++) {
                ArrayList<String> newprefix = new ArrayList<String>(prefix);
                newprefix.add(str.get(i));
                ArrayList<String> newstr = new ArrayList<String>(str);
                newstr.remove(i);
                permutePlaces(newprefix, newstr, op);
            }
        }
        return op;
    }

    private ArrayList permuteTransport(double budget, ArrayList<String> prefix, ArrayList<String> route, double time, double cost, ArrayList bestroute) {
        int n = route.size();
        if (n == 1) {
            if (cost <= budget & time < (double) bestroute.get(0)) {
                bestroute.clear();
                bestroute.add(time);
                bestroute.add(cost);
                ArrayList<String> prefixcopy = new ArrayList<String>(prefix);
                bestroute.add(prefixcopy);
            }
        } else {
            ArrayList newroute = new ArrayList(route);
            newroute.remove(0);
            prefix.add("walk");
            double newtime = time + getTime(route.get(0), route.get(1), this.walkdata);
            double newcost = cost + getCost(route.get(0), route.get(1), this.walkdata);
            bestroute = permuteTransport(budget, prefix, newroute, newtime, newcost, bestroute);

            prefix.remove(prefix.size()-1);
            prefix.add("take public transport");
            newtime = time + getTime(route.get(0), route.get(1), this.ptdata);
            newcost = cost + getCost(route.get(0), route.get(1), this.ptdata);
            bestroute = permuteTransport(budget, prefix, newroute, newtime, newcost, bestroute);

            prefix.remove(prefix.size()-1);
            prefix.add("take taxi");
            newtime = time + getTime(route.get(0), route.get(1), this.taxidata);
            newcost = cost + getCost(route.get(0), route.get(1), this.taxidata);
            bestroute = permuteTransport(budget, prefix, newroute, newtime, newcost, bestroute);
            prefix.remove(prefix.size()-1);
        }
        return bestroute;
    }

    public ArrayList getBestRoute(ArrayList<String> chosenPlaces, double budget) {
//        generate all permutations of places
        ArrayList<String> p = new ArrayList<>();
        p.add("Marina Bay Sands");
        ArrayList allroutes = permutePlaces(p, chosenPlaces, new ArrayList());
//        initialise empty best route and best transport
        ArrayList bestroute = new ArrayList();
        bestroute.add(1000.0);
        ArrayList initbesttrans = new ArrayList();
//        for each sequence of places, generate the best transport route within the budget
        for (Object route : allroutes) {
            initbesttrans.clear();
            initbesttrans.add(1000.0);
            ArrayList besttrans = this.permuteTransport(budget, new ArrayList<String>(), (ArrayList<String>) route, 0, 0, initbesttrans);
//            if the best transport route uses a shorter time than the current bestroute, replace bestroute
            if ((double) besttrans.get(0) < (double) bestroute.get(0)) {
                bestroute.clear();
                bestroute.add(besttrans.get(0));
                bestroute.add(besttrans.get(1));
                bestroute.add(besttrans.get(2));
                bestroute.add(route);
            }
        }
        return bestroute;
    }

    public ArrayList getApproxBestRoute(ArrayList<String> chosenPlaces, double budget) {
//        arrange places of interest roughly in order since we know their relative positions
        String[] orderedplaces = {"Singapore Flyer", "Buddha Tooth Relic Temple", "Zoo", "Vivo City", "Resorts World Sentosa"};
        ArrayList<String> route = new ArrayList<String>();
        route.add("Marina Bay Sands");
        for (String place : orderedplaces) {
            if (chosenPlaces.contains(place)) route.add(place);
        }
        route.add("Marina Bay Sands");
        int nTransport = route.size()-1;
        double cost = 0;
        double time = 0;
        ArrayList<String> transport = new ArrayList<>();
        for (int i=0; i<nTransport; i++) {
            double taxicost = getCost(route.get(i), route.get(i+1), this.taxidata);
            if (cost + taxicost <= budget*(i+1)/nTransport) {
                transport.add("take taxi");
                cost += taxicost;
                time += getTime(route.get(i), route.get(i+1), this.taxidata);
            }
            else {
                transport.add("take public transport");
                cost += getCost(route.get(i), route.get(i+1), this.ptdata);
                time += getTime(route.get(i), route.get(i+1), this.ptdata);
            }
        }
        ArrayList bestroute = new ArrayList();
        bestroute.add(time);
        bestroute.add(cost);
        bestroute.add(transport);
        bestroute.add(route);
        return bestroute;
    }
}
