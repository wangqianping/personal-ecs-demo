package timestream;

import com.amazonaws.services.timestreamwrite.AmazonTimestreamWrite;
import com.amazonaws.services.timestreamwrite.model.*;

import java.util.List;

/**
 * @author wangqianping
 * @date 2022-05-13
 */
public class DatabaseBuilder {

    public static final String DATABASE_NAME = "wqp_test";

    /**
     * 查询所有数据库
     */
    public void listDatabases() {
        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        ListDatabasesRequest request = new ListDatabasesRequest();
        ListDatabasesResult result = amazonTimestreamWrite.listDatabases(request);
        final List<Database> databases = result.getDatabases();
        for (Database db : databases) {
            System.out.println(db.getDatabaseName());
        }
    }

    /**
     * create database
     */
    public void createDatabase() {
        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        CreateDatabaseRequest request = new CreateDatabaseRequest();
        request.setDatabaseName(DATABASE_NAME);
        try {
            amazonTimestreamWrite.createDatabase(request);
            System.out.println("Database [" + DATABASE_NAME + "] created successfully");
        } catch (ConflictException e) {
            System.out.println("Database [" + DATABASE_NAME + "] exists. Skipping database creation");
        }
    }

    /**
     * Deleting database
     */
    public void deleteDatabase() {
        AmazonTimestreamWrite amazonTimestreamWrite = ClientBuilder.buildWriteClient();
        final DeleteDatabaseRequest deleteDatabaseRequest = new DeleteDatabaseRequest();
        deleteDatabaseRequest.setDatabaseName(DATABASE_NAME);
        try {
            DeleteDatabaseResult result = amazonTimestreamWrite.deleteDatabase(deleteDatabaseRequest);
            System.out.println("Delete database status: " + result.getSdkHttpMetadata().getHttpStatusCode());
        } catch (final ResourceNotFoundException e) {
            System.out.println("Database " + DATABASE_NAME + " doesn't exist = " + e);
            throw e;
        } catch (final Exception e) {
            System.out.println("Could not delete Database " + DATABASE_NAME + " = " + e);
            throw e;
        }
    }


}
