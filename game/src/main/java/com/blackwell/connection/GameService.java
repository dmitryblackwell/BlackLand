package com.blackwell.connection;

import com.blackwell.entity.Player;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface GameService {

    @POST("/update")
    Call<List<PlayerDTO>> update(@Body Player player);
}
