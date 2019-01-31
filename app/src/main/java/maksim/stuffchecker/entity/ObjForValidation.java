package maksim.stuffchecker.entity;

public class ObjForValidation {

    private int id;
    private String name;
    private String requestGET;
    private String[] validatedPartOfResponse;
    private long time;
    private boolean isOk;

    public ObjForValidation(int id,
                            String name,
                            String requestGET,
                            String[] validatedPartOfResponse,
                            long time,
                            boolean isOk) {
        this.id = id;
        this.name = name;
        this.requestGET = requestGET;
        this.validatedPartOfResponse = validatedPartOfResponse;
        this.time = time;
        this.isOk = isOk;
    }

    public ObjForValidation(int id,
                            String name,
                            String requestGET,
                            String[] validatedPartOfResponse) {
        this.id = id;
        this.name = name;
        this.requestGET = requestGET;
        this.validatedPartOfResponse = validatedPartOfResponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestGET() {
        return requestGET;
    }

    public void setRequestGET(String requestGET) {
        this.requestGET = requestGET;
    }

    public String[] getValidatedPartOfResponse() {
        return validatedPartOfResponse;
    }

    public void setValidatedPartOfResponse(String[] validatedPartOfResponse) {
        this.validatedPartOfResponse = validatedPartOfResponse;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

    @Override
    public String toString() {
        return "ObjForValidation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", requestGET='" + requestGET + '\'' +
                ", time=" + time +
                ", isOk=" + isOk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjForValidation that = (ObjForValidation) o;
        return id == that.id &&
                isOk == that.isOk;
    }

}
