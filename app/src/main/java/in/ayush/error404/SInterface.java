package in.ayush.error404;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SInterface {
    @POST("/")
    Call<GetBalanceResponse> retreiveBalance(
            @Body GetBalanceRequest request
    );

    @POST("/")
    Call<getAccoutnResponce> getAccountRes(
            @Body getAccountInfo request
    );



    class  getAccountInfo{
        public getAccountInfo(String jsonrpc, Integer id, String method, ArrayList<Object> params) {
            this.jsonrpc = jsonrpc;
            this.id = id;
            this.method = method;
            this.params = params;
        }
        @SerializedName("jsonrpc")
        String jsonrpc;
        @SerializedName("id")
        Integer id;
        @SerializedName("method")
        String method;
        @SerializedName("params")
        ArrayList<Object> params;
    }
    class getAccoutnResponce{
        public class Context{
            public int slot;
        }

        public class Result{
            public Context context;
            public Value value;
        }

        public class Root{
            public String jsonrpc;
            public Result result;
            public int id;
        }

        public class Value{
            public ArrayList<String> data;
            public boolean executable;
            public int lamports;
            public String owner;
            public int rentEpoch;
        }

    }


    class GetBalanceRequest {

        public  GetBalanceRequest(String jsonrpc, Integer id, String method, String[] params) {
            this.jsonrpc = jsonrpc;
            this.id = id;
            this.method = method;
            this.params = params;
        }

        @SerializedName("jsonrpc")
        String jsonrpc;
        @SerializedName("id")
        Integer id;
        @SerializedName("method")
        String method;
        @SerializedName("params")
        String[] params;
    }

    class GetBalanceResponse {

        class Result {
            class Context {
                @SerializedName("slot")
                Integer slot;
                @Override
                public String toString() {
                    return "Context{" +
                            "slot=" + slot +
                            '}';
                }
            }
            @SerializedName("context")
            SInterface.GetBalanceResponse.Result.Context context;
            @SerializedName("value")
            BigInteger value;

            @Override
            public String toString() {
                return "Result{" +
                        "context=" + context +
                        ", value=" + value +
                        '}';
            }
        }

        @SerializedName("jsonrpc")
        String jsonrpc;
        @SerializedName("id")
        Integer id;
        @SerializedName("result")
        SInterface.GetBalanceResponse.Result result;

        @Override
        public String toString() {
            return "GetBalanceResponse{" +
                    "jsonrpc='" + jsonrpc + '\'' +
                    ", id=" + id +
                    ", result=" + result +
                    '}';
        }
    }
}
