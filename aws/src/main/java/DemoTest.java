import com.amazonaws.services.timestreamquery.model.QueryResult;

import common.entity.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import sqs.consumer.sdk.MessageHandler;
import sqs.consumer.sdk.SqsClientBuilder;
import timestream.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangqianping
 * @date 2022-05-11
 */
public class DemoTest {

    long start;
    DatabaseBuilder databaseBuilder;
    TableBuilder tableBuilder;
    WriteBuilder writeBuilder;
    QueryBuilder queryBuilder;
    ResultParser resultParser;
    MessageHandler messageHandler;


    @Before
    public void init() {
        start = System.currentTimeMillis();
        databaseBuilder = new DatabaseBuilder();
        tableBuilder = new TableBuilder();
        writeBuilder = new WriteBuilder();
        queryBuilder = new QueryBuilder();
        resultParser = new ResultParser();
        messageHandler = new MessageHandler();
    }

    @After
    public void printRunningTime() {
        System.out.println("代码执行结束，总耗时：" + BigDecimal.valueOf(System.currentTimeMillis() - start).divide(BigDecimal.valueOf(1000)) + "秒");
    }


    @Test
    public void test1() {
        databaseBuilder.createDatabase();
    }

    @Test
    public void test2() {
        databaseBuilder.listDatabases();
    }

    @Test
    public void test3() {
        databaseBuilder.deleteDatabase();
    }

    @Test
    public void test4() {
        tableBuilder.createTable();
    }

    @Test
    public void test5() {
        tableBuilder.deleteTable();
    }

    @Test
    public void test6() {
        writeBuilder.writeRecords();
    }

    @Test
    public void test7() {
        writeBuilder.writeRecordsWithCommonAttributes();
    }

    @Test
    public void test8() {
        String queryString = "select equipSn,status,measure_name AS measureName,time,measure_value::varchar AS measureValue from wqp_test.test_data";
        QueryResult queryResult = queryBuilder.runQuery(queryString);
        System.out.println(queryResult);
        List<TestData> testData = resultParser.parseQueryResult(queryResult, TestData.class);
        System.out.println(testData);
    }

    @Test
    public void test9() {
        List<TestData> demoData = TestData.getDemoData();
        writeBuilder.writeRecords(DatabaseBuilder.DATABASE_NAME, TableBuilder.TABLE_NAME, TestData.class, demoData);
    }

    @Test
    public void test10() {
        SqsClient sqsClient = SqsClientBuilder.buildSqsClient();
        List<Message> messages = messageHandler.receiveMessages(sqsClient, MessageHandler.QUEUE_URL);
        messages.forEach(message -> System.out.println(message));
    }



}
