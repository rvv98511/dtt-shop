package com.example.dttshop.retrofit;

import com.example.dttshop.model.DanhSachMenuModel;
import com.example.dttshop.model.DonHangModel;
import com.example.dttshop.model.MessageModel;
import com.example.dttshop.model.SanPhamMoiModel;
import com.example.dttshop.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getDanhSachMenu.php")
    Observable<DanhSachMenuModel> getDSMenu();

    @GET("getSanPhamMoi.php")
    Observable<SanPhamMoiModel> getSPMoi();

    @POST("getLoaiSanPham.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getLoaiSP(
            @Field("page") int page,
            @Field("loai") String loai
    );

    @POST("dangKyUser.php")
    @FormUrlEncoded
    Observable<UserModel> getSignUp(
            @Field("username") String username,
            @Field("tenkh") String tenkh,
            @Field("sodienthoai") String sodienthoai,
            @Field("email") String email,
            @Field("matkhau") String matkhau
    );

    @POST("dangNhapUser.php")
    @FormUrlEncoded
    Observable<UserModel> getLogIn(
            @Field("username") String username,
            @Field("matkhau") String matkhau
    );

    @POST("taoDonHang.php")
    @FormUrlEncoded
    Observable<MessageModel> createOrder(
            @Field("username") String username,
            @Field("tenkh") String tenkh,
            @Field("sodienthoai") String sodienthoai,
            @Field("email") String email,
            @Field("diachi") String diachi,
            @Field("tongsoluong") int tongsoluong,
            @Field("tongtien") String tongtien,
            @Field("ngaydathang") String ngaydathang,
            @Field("thanhtoan") String thanhtoan,
            @Field("zalopaytoken") String zalopaytoken,
            @Field("chitiet") String chitiet
    );

    @POST("updateSoLuongSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> upDateSoLuongSP(
            @Field("username") String username
    );

    @POST("xemDonHang.php")
    @FormUrlEncoded
    Observable<DonHangModel> getOrder(
            @Field("username") String username
    );

    @POST("searchSanPham.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> searchSP(
            @Field("searchtext") String searchtext,
            @Field("searchtheoloai") String searchtheoloai,
            @Field("searchtheogia") int searchtheogia
    );

    @POST("updateTaiKhoanUser.php")
    @FormUrlEncoded
    Observable<UserModel> updateTaiKhoanKH(
            @Field("username") String username,
            @Field("tenkh") String tenkh,
            @Field("sodienthoai") String sodienthoai,
            @Field("email") String email,
            @Field("matkhau") String matkhau
    );
}
