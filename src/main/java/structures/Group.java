package structures;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Group {

    private int groupId;
    private String nameGroup;
    private String curator;
    private String speciality;

    Group() {

    }

    Group(ResultSet resultSet) throws SQLException {
        this.setGroupId(resultSet.getInt(1));
        this.setNameGroup(resultSet.getString(2));
        this.setCurator(resultSet.getString(3));
        this.setSpeciality(resultSet.getString(4));
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "structures.Group{" +
                "nameGroup='" + nameGroup + '\'' +
                '}';
    }

}
