package timestream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.timestreamquery.AmazonTimestreamQuery;
import com.amazonaws.services.timestreamquery.AmazonTimestreamQueryClient;
import com.amazonaws.services.timestreamwrite.AmazonTimestreamWrite;
import com.amazonaws.services.timestreamwrite.AmazonTimestreamWriteClientBuilder;

/**
 * 获取ts的读写对象
 *
 * @author wangqianping
 * @date 2022-05-13
 */
public class ClientBuilder {


    /**
     * 获取一个时序数据库写对象
     *
     * @return
     */
    public static AmazonTimestreamWrite buildWriteClient() {
        final ClientConfiguration clientConfiguration = new ClientConfiguration().withMaxConnections(5000).withRequestTimeout(20 * 1000).withMaxErrorRetry(10);
        return AmazonTimestreamWriteClientBuilder.standard().withRegion("us-east-1").withClientConfiguration(clientConfiguration).build();
    }

    /**
     * 获取一个时序数据库读对象
     *
     * @return
     */
    public static AmazonTimestreamQuery buildQueryClient() {
        AmazonTimestreamQuery client = AmazonTimestreamQueryClient.builder().withRegion("us-east-1").build();
        return client;
    }


}
