package fuzzy;

public class Floor implements IField {
    private int contaminationLevel;

    public Floor(int contaminationLevel) {
        this.contaminationLevel = contaminationLevel;
    }

    public int getContaminationLevel() {
        return contaminationLevel;
    }

    public void setContaminationLevel(int contaminationLevel) {
        this.contaminationLevel = contaminationLevel;
    }
}
