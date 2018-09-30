package com.fileupload.wscalling;
/**
 * Created by SONI on 9/30/2018.
 */

public  interface WsResponse<T>  {

    public void successResponse(T response, int code);
    public void failureRespons(Throwable error, int code);


}
