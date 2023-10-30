package com.example.dttshopmanager.retrofit;

import com.example.dttshopmanager.model.DanhSachMenuModel;
import com.example.dttshopmanager.model.DonHangModel;
import com.example.dttshopmanager.model.MessageModel;
import com.example.dttshopmanager.model.SanPhamMoiModel;
import com.example.dttshopmanager.model.SanPhamTrangThaiDonHangModel;
import com.example.dttshopmanager.model.ThongKeDoanhThuModel;
import com.example.dttshopmanager.model.ThongKeSanPhamModel;
import com.example.dttshopmanager.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiQuanLyBanHang {
    @GET("getDanhSachMenuManager.php")
    Observable<DanhSachMenuModel> getDSMenu();

    @GET("getSanPhamMoi.php")
    Observable<SanPhamMoiModel> getSPMoi();

    @GET("xemDonHangManager.php")
    Observable<DonHangModel> getDonHang();

    @GET("thongKeSanPham.php")
    Observable<ThongKeSanPhamModel> getThongKeSP();

    @GET("thongKeDoanhThu.php")
    Observable<ThongKeDoanhThuModel> getThongKeDoanhThu();

    @GET("checkBeforeDeleteSanPham.php")
    Observable<SanPhamTrangThaiDonHangModel> getSPTrangThaiDonHang();

    @POST("getLoaiSanPham.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getLoaiSP(
            @Field("page") int page,
            @Field("loai") String loai
    );

    @POST("dangNhapManager.php")
    @FormUrlEncoded
    Observable<UserModel> getLogIn(
            @Field("username") String username,
            @Field("matkhau") String matkhau
    );

    @POST("searchSanPham.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> searchSP(
            @Field("searchtext") String searchtext,
            @Field("searchtheoloai") String searchtheoloai,
            @Field("searchtheogia") int searchtheogia
    );

    @POST("addSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> addSP(
            @Field("tensp") String tensp,
            @Field("giasp") String giasp,
            @Field("giakhuyenmai") String giakhuyenmai,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") String loai,
            @Field("soluong") int soluong
    );

    @Multipart
    @POST("uploadHinhAnh.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);

    @POST("deleteSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteSP(
            @Field("idsp") int idsp
    );

    @POST("editSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> editSP(
            @Field("idsp") int idsp,
            @Field("tensp") String tensp,
            @Field("giasp") String giasp,
            @Field("giakhuyenmai") String giakhuyenmai,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") String loai,
            @Field("soluong") int soluong
    );

    @POST("updateTrangThaiDonHang.php")
    @FormUrlEncoded
    Observable<MessageModel> updateDonHang(
            @Field("iddonhang") int iddonhang,
            @Field("trangthai") int trangthai
    );

    @POST("updateMatKhauManager.php")
    @FormUrlEncoded
    Observable<MessageModel> updatePassword(
            @Field("username") String username,
            @Field("matkhau") String matkhau
    );
}
