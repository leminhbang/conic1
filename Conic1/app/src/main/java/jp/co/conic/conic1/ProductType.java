package jp.co.conic.conic1;

import java.util.ArrayList;
import java.util.List;

public class ProductType {

    private String tooling_type;
    private float diameter;

    List<Float> amada;
    List<String> amada_station;
    List<Float> murata;
    List<String> murata_station;
    List<Float> trumpf;
    List<String> trumpf_station;

    public ProductType(String tooling_type,
                       float diameter) {
        this.tooling_type = tooling_type;
        this.diameter = diameter;

        amada = new ArrayList<Float>();
        amada.add((float) 1);
        amada.add((float) 12.7);
        amada.add((float) 31.7);
        amada.add((float) 50.8);
        amada.add((float) 88.9);
        amada.add((float) 114.3);
        amada_station = new ArrayList<String>();
        amada_station.add("A");
        amada_station.add("B");
        amada_station.add("C");
        amada_station.add("D");
        amada_station.add("E");
        murata = new ArrayList<Float>();
        murata.add((float) 1);
        murata.add((float) 12.7);
        murata.add((float) 25);
        murata.add((float) 38);
        murata.add((float) 50);
        murata.add((float) 64);
        murata.add((float) 75);
        murata.add((float) 89);
        murata.add((float) 105);
        murata.add((float) 120);
        murata_station = new ArrayList<String>();
        murata_station.add("A/X");
        murata_station.add("B");
        murata_station.add("C");
        murata_station.add("D");
        murata_station.add("E");
        murata_station.add("F");
        murata_station.add("G");
        murata_station.add("H");
        murata_station.add("J");
        trumpf = new ArrayList<Float>();
        trumpf.add((float) 1);
        trumpf.add((float) 2);
        trumpf.add((float) 30);
        trumpf.add((float) 76.2);
        trumpf_station = new ArrayList<String>();
        trumpf_station.add("Type I / M+");
        trumpf_station.add("Type I");
        trumpf_station.add("Type II");
    }

    public String getStation(String tooling_type, float diameter) {
        if(tooling_type == "Amada") {
            if(diameter >= 1 || diameter <= 12.7) {
                amada_station.get(0);
            }
            for (int i = 2; i <= amada.size(); i++) {
                if(diameter > amada.get(i - 1) || diameter <= amada.get(i)) {
                    amada_station.get(i - 1);
                }
            }
        }
        if(tooling_type == "Murata") {
            if(diameter >= 1 || diameter <= 12.7) {
                murata_station.get(0);
            }
            for (int i = 2; i <= murata.size(); i++) {
                if(diameter > murata.get(i - 1) || diameter <= murata.get(i)) {
                    murata_station.get(i - 1);
                }
            }
        }
        if(tooling_type == "Trumpf") {
            if(diameter >= 1 || diameter <= 2) {
                trumpf_station.get(0);
            }
            for (int i = 2; i <= trumpf.size(); i++) {
                if(diameter > trumpf.get(i - 1) || diameter <= trumpf.get(i)) {
                    trumpf_station.get(i - 1);
                }
            }
        }
        return "NaN";
    }
}
