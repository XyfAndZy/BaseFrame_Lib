package com.xyf.baseframe_lib.manager;

import com.xyf.baseframe_lib.base.BaseResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Description: <br/>
 * Author:XYF<br/>
 * Date:2017/8/11 16:49
 */

public interface API {

    /**
     * 添加标签
     *
     * @param supplier_id
     * @param staff_id
     * @param name
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("memberservice/addMemberTag/suppliers/{supplier_id}/operator/{staff_id}")
    Observable<BaseResponse> addMemberTag(@Path("supplier_id") String supplier_id, @Path("staff_id") String staff_id,
                                            @Field("name") String name, @Field("type") String type, @Field("timestamp") String timestamp);

    /**
     * 删除标签（注意一定要写hasBody = true，否则delete不能使用body参数请求）
     *
     * @param supplier_id
     * @param operator_id
     * @return
     */
    @HTTP(method = "DELETE", path = "memberservice/delMemberTag/suppliers/{supplier_id}/operator/{operator_id}",
            hasBody = true)
    Observable<BaseResponse> deletMemberTag(@Path("supplier_id") String supplier_id,
                              @Path("operator_id") String operator_id,
                              @Body RequestBody content);

    /**
     * 修改标签
     *
     * @param supplier_id
     * @param operator_id
     * @param map
     * @return
     */
    @FormUrlEncoded
    @PUT("memberservice/updateMemberTag/suppliers/{supplier_id}/operator/{operator_id}")
    Observable<BaseResponse> updateMemberTag(@Path("supplier_id") String supplier_id,
                                             @Path("operator_id") String operator_id,
                                             @FieldMap Map<String, String> map);

    /**
     * 查询标签
     *
     * @param supplier_id
     * @param staff_id
     * @return
     */
    @GET("memberservice/queryStaffTag/suppliers/{supplier_id}/operator/{staff_id}")
    Observable<BaseResponse> queryMemberTag(@Path("supplier_id") String supplier_id,
                                            @Path("staff_id") String staff_id,
                                            @Query("timestamp") String timestamp);
}
