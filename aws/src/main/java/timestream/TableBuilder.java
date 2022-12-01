package timestream;

import com.amazonaws.services.timestreamwrite.AmazonTimestreamWrite;
import com.amazonaws.services.timestreamwrite.model.*;

/**
 * @author wangqianping
 * @date 2022-05-13
 */
public class TableBuilder {

    public static final String TABLE_NAME = "test_data";
    public static final long HT_TTL_HOURS = 24L;
    public static final long CT_TTL_DAYS = 7L;

    /**
     * Creating table
     */
    public void createTable() {

        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        CreateTableRequest createTableRequest = new CreateTableRequest();
        createTableRequest.setDatabaseName(DatabaseBuilder.DATABASE_NAME);
        createTableRequest.setTableName(TABLE_NAME);
        final RetentionProperties retentionProperties = new RetentionProperties().withMemoryStoreRetentionPeriodInHours(HT_TTL_HOURS).withMagneticStoreRetentionPeriodInDays(CT_TTL_DAYS);
        createTableRequest.setRetentionProperties(retentionProperties);

        try {
            amazonTimestreamWrite.createTable(createTableRequest);
            System.out.println("Table [" + TABLE_NAME + "] successfully created.");
        } catch (ConflictException e) {
            System.out.println("Table [" + TABLE_NAME + "] exists on database [" + DatabaseBuilder.DATABASE_NAME + "] . Skipping database creation");
        }
    }

    /**
     * Deleting table
     */
    public void deleteTable() {
        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        final DeleteTableRequest deleteTableRequest = new DeleteTableRequest();
        deleteTableRequest.setDatabaseName(DatabaseBuilder.DATABASE_NAME);
        deleteTableRequest.setTableName(TABLE_NAME);
        try {
            DeleteTableResult result = amazonTimestreamWrite.deleteTable(deleteTableRequest);
            System.out.println("Delete table status: " + result.getSdkHttpMetadata().getHttpStatusCode());
        } catch (final ResourceNotFoundException e) {
            System.out.println("Table " + TABLE_NAME + " doesn't exist = " + e);
            throw e;
        } catch (final Exception e) {
            System.out.println("Could not delete table " + TABLE_NAME + " = " + e);
            throw e;
        }
    }


}
