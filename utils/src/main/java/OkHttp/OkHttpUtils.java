package OkHttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient.Builder;

/**
 * Project:           sxz
 * Author:            sxz
 * Company:           Big Player Group
 * Created Date:      2021/1/15
 * Description:   {类描述}
 * Copyright @ 2017-2021 BIGPLAYER.GROUP – Confidential and Proprietary
 */
@Slf4j
public class OkHttpUtils {

    private static final OkHttpClient OK_HTTP_CLIENT = new Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private OkHttpUtils() {
    }

    public static Request.Builder newRequestHttpBuilder(String url, Map<String, Object> params) {
        HttpUrl httpUrl = checkAndParseUrl(url);
        HttpUrl.Builder httpBuilder = httpUrl.newBuilder();

        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (Objects.nonNull(param.getValue())) {
                    httpBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));
                }
            }
        }
        return new Request.Builder()
                .url(httpBuilder.build())
                .get();
    }

    public static Request.Builder newRequestFormBuilder(String url, Map<String, Object> params) {
        checkAndParseUrl(url);
        FormBody.Builder formBuilder = new FormBody.Builder();

        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (Objects.nonNull(param.getValue())) {
                    formBuilder.add(param.getKey(), String.valueOf(param.getValue()));
                }
            }
        }

        return new Request.Builder()
                .url(url)
                .post(formBuilder.build());
    }

    public static Request.Builder newRequestJsonBuilder(String url, String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        return new Request.Builder()
                .url(url)
                .post(requestBody);
    }

    private static HttpUrl checkAndParseUrl(String url) {
        AssertUtils.isNull(url, "url不能为空");
        HttpUrl httpUrl = HttpUrl.parse(url);
        AssertUtils.isNull(httpUrl, "url解析错误");
        return httpUrl;
    }

    public static String doRequest(Request request) {
        return doRequest(request, null);
    }

    public static void doRequestAsync(Request request, Callback callback) {
        doRequest(request, callback);
    }

    private static String doRequest(Request request, Callback callback) {
        Call call = OK_HTTP_CLIENT.newCall(request);
        if (Objects.nonNull(callback)) {
            call.enqueue(callback);
            return null;
        }
        String string = null;
        Response response = null;
        long start = System.currentTimeMillis();
        try {
            response = call.execute();
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                string = body.string();
            }
            if (response.isSuccessful()) {
                return string;
            }
            return string;
        } catch (IOException e) {
            log.error("okHttp请求异常", e);
            return e.getMessage();
        } finally {
            log.info("耗时: <{}> ms" +
                            ", \n请求信息: <{}>" +
                            ", \n请求headers: <{}>" +
                            ", \n响应信息: <{}>",
                    System.currentTimeMillis() - start
                    , request.toString()
                    , request.headers()
                    , Objects.nonNull(response) ? response.toString() : null);
        }
    }


}
