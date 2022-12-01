package common.entity;

import demo.common.TsBaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangqianping
 * @date 2022-05-13
 */
@Data
public class TestData extends TsBaseEntity {

    private String equipSn;

    private Integer status;


    @Override
    public String toString() {
        return "TestData{" + "equipSn='" + equipSn + '\'' + ", status=" + status + '\'' + ", time=" + super.getTime() + '\'' + ", measureName=" + super.getMeasureName() + '\'' + ", measureValue=" + super.getMeasureValue() + '}';
    }


    public static List<TestData> getDemoData() {
        List<TestData> testDataList = new ArrayList<>();
        TestData testData = new TestData();
        testData.setEquipSn("test-001");
        testData.setStatus(1);
        testDataList.add(testData);
        return testDataList;
    }
}
