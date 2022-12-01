package timestream;

import com.amazonaws.services.timestreamquery.AmazonTimestreamQuery;
import com.amazonaws.services.timestreamquery.model.QueryRequest;
import com.amazonaws.services.timestreamquery.model.QueryResult;

/**
 * @author wangqianping
 * @date 2022-05-11
 */
public class QueryBuilder {

    public QueryResult runQuery(String queryString) {
        try {
            AmazonTimestreamQuery queryClient = ClientBuilder.buildQueryClient();
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.setQueryString(queryString);
            QueryResult queryResult = queryClient.query(queryRequest);
            return queryResult;
        } catch (Exception e) {
            // Some queries might fail with 500 if the result of a sequence function has more than 10000 entries
            e.printStackTrace();
        }
        return null;
    }


}
