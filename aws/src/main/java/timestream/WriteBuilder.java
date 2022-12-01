package timestream;

import com.amazonaws.services.timestreamwrite.AmazonTimestreamWrite;
import com.amazonaws.services.timestreamwrite.model.*;
import software.amazon.awssdk.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangqianping
 * @date 2022-05-13
 */
public class WriteBuilder {

    public <T> void writeRecords(String databaseName, String tableName, Class<T> tClass, List<T> tList) {

        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        final long time = System.currentTimeMillis();
        List<Field> fields = Arrays.asList(tClass.getDeclaredFields());
        List<Record> records = new ArrayList<>();
        tList.forEach(entity -> {
            List<Dimension> dimensions = new ArrayList<>();
            fields.forEach(field -> {
                try {
                    String name = "get" + StringUtils.capitalize(field.getName());
                    Method method = tClass.getMethod(name);
                    Object invoke = method.invoke(entity);
                    Dimension dimension = new Dimension().withName(field.getName()).withValue(invoke == null ? null : invoke.toString());
                    dimensions.add(dimension);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            Record record = new Record().withDimensions(dimensions).withMeasureName("-").withMeasureValue("-").withMeasureValueType(MeasureValueType.VARCHAR).withTime(String.valueOf(time));
            records.add(record);
        });

        WriteRecordsRequest writeRecordsRequest = new WriteRecordsRequest().withDatabaseName(databaseName).withTableName(tableName).withRecords(records);
        try {
            WriteRecordsResult writeRecordsResult = amazonTimestreamWrite.writeRecords(writeRecordsRequest);
            System.out.println("WriteRecords Status: " + writeRecordsResult.getSdkHttpMetadata().getHttpStatusCode());
        } catch (RejectedRecordsException e) {
            System.out.println("RejectedRecords: " + e);
            for (RejectedRecord rejectedRecord : e.getRejectedRecords()) {
                System.out.println("Rejected Index " + rejectedRecord.getRecordIndex() + ": " + rejectedRecord.getReason());
            }
            System.out.println("Other records were written successfully. ");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    public void writeRecords() {

        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        final long time = System.currentTimeMillis();

        List<Dimension> dimensions = new ArrayList<>();
        final Dimension equipSn = new Dimension().withName("equipSn").withValue("test_01");
        final Dimension status = new Dimension().withName("status").withValue("1");

        dimensions.add(equipSn);
        dimensions.add(status);

        List<Record> records = new ArrayList<>();
        Record cpuUtilization = new Record().withDimensions(dimensions).withMeasureName("无效字段").withMeasureValue("测试数据").withMeasureValueType(MeasureValueType.VARCHAR).withTime(String.valueOf(time));
        records.add(cpuUtilization);

        WriteRecordsRequest writeRecordsRequest = new WriteRecordsRequest().withDatabaseName(DatabaseBuilder.DATABASE_NAME).withTableName(TableBuilder.TABLE_NAME).withRecords(records);

        try {
            WriteRecordsResult writeRecordsResult = amazonTimestreamWrite.writeRecords(writeRecordsRequest);
            System.out.println("WriteRecords Status: " + writeRecordsResult.getSdkHttpMetadata().getHttpStatusCode());
        } catch (RejectedRecordsException e) {
            System.out.println("RejectedRecords: " + e);
            for (RejectedRecord rejectedRecord : e.getRejectedRecords()) {
                System.out.println("Rejected Index " + rejectedRecord.getRecordIndex() + ": " + rejectedRecord.getReason());
            }
            System.out.println("Other records were written successfully. ");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    public void writeRecordsWithCommonAttributes() {
        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        List<Record> records = new ArrayList<>();
        final long time = System.currentTimeMillis();

        List<Dimension> dimensions = new ArrayList<>();
        final Dimension region = new Dimension().withName("region").withValue("us-east-1");
        final Dimension az = new Dimension().withName("az").withValue("az1");
        final Dimension hostname = new Dimension().withName("hostname").withValue("host1");
        final Dimension status = new Dimension().withName("status").withValue("test");

        dimensions.add(region);
        dimensions.add(az);
        dimensions.add(hostname);
        dimensions.add(status);

        Record commonAttributes = new Record().withDimensions(dimensions).withMeasureValueType(MeasureValueType.DOUBLE).withTime(String.valueOf(time));

        Record cpuUtilization = new Record().withMeasureName("cpu_utilization").withMeasureValue("13.5");
        Record memoryUtilization = new Record().withMeasureName("memory_utilization").withMeasureValue("40");
        records.add(cpuUtilization);
        records.add(memoryUtilization);
        WriteRecordsRequest writeRecordsRequest = new WriteRecordsRequest().withDatabaseName(DatabaseBuilder.DATABASE_NAME).withTableName(TableBuilder.TABLE_NAME).withCommonAttributes(commonAttributes).withRecords(records);

        try {
            WriteRecordsResult writeRecordsResult = amazonTimestreamWrite.writeRecords(writeRecordsRequest);
            System.out.println("writeRecordsWithCommonAttributes Status: " + writeRecordsResult.getSdkHttpMetadata().getHttpStatusCode());
        } catch (RejectedRecordsException e) {
            System.out.println("RejectedRecords: " + e);
            for (RejectedRecord rejectedRecord : e.getRejectedRecords()) {
                System.out.println("Rejected Index " + rejectedRecord.getRecordIndex() + ": " + rejectedRecord.getReason());
            }
            System.out.println("Other records were written successfully. ");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


}
