package ca.jackfountain.emil_fishing.data;

public record Quantifier (String type, int percent) {
    public String toString(){
        return percent + " " + type;
    }
}
