package structures;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Speciality {

    private int specialityID;
    private String specialityName;

    Speciality(ResultSet resultSet) throws SQLException {
        this.specialityID = resultSet.getInt(1);
        this.specialityName = resultSet.getString(2);
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "specialityID=" + specialityID +
                ", specialityName='" + specialityName + '\'' +
                '}';
    }

    public int getSpecialityID() {
        return specialityID;
    }

    public String getSpecialityName() {
        return specialityName;
    }
}
